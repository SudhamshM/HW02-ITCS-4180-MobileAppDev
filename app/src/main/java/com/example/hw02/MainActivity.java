package com.example.hw02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hw02.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binder;
    public static final String WEIGHT_KEY = "WEIGHT";
    public static final String ADD_DRINKS = "ADD_DRINKS";
    public static final String VIEW_DRINKS = "VIEW_DRINKS";
    Profile userMain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setTitle(R.string.bac_screen_title);

        if (userMain == null)
        {
            binder.viewDrinksBtn.setEnabled(false);
            binder.addDrinkBtn.setEnabled(false);
        }

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        // returning from adding weight
                        if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null)
                        {
                            if (result.getData().getSerializableExtra(WEIGHT_KEY) != null)
                            {
                                userMain = (Profile) result.getData().getSerializableExtra(WEIGHT_KEY);
                                binder.weightValueText.setText(userMain.getWeight() + " lbs ("
                                        + userMain.getGender() + ")");
                                binder.bacLevelValueText.setText(R.string.default_bac_level);
                                binder.drinksValueText.setText(R.string.default_num_drinks);
                                binder.bacStatusText.setText(R.string.safe_label);
                                binder.bacStatusText.setBackgroundResource(R.color.safe_color);
                                binder.viewDrinksBtn.setEnabled(true);
                                binder.addDrinkBtn.setEnabled(true);
                            }
                            // returning from adding drink
                            else if (result.getData().getSerializableExtra(ADD_DRINKS) != null)
                            {
                                Drink newDrink = (Drink) result.getData().getSerializableExtra(ADD_DRINKS);
                                newDrink.setPersonWhoDrank(userMain);
                                userMain.addDrink(newDrink);
                                Log.d("hw02", "onClick: bacDrink: " + newDrink.getBacLevel());
                                binder.drinksValueText.setText(String.valueOf(userMain.drinksList.size()));
                                double totalBac = userMain.getTotalBac();
                                binder.bacLevelValueText.setText(String.format("%.3f", totalBac));
                                processBacStatus(totalBac);

                            }
                            else if (result.getData().getSerializableExtra(VIEW_DRINKS) != null)
                            {
                                ArrayList<Drink> newDrinkList = (ArrayList<Drink>) result.getData().getSerializableExtra(VIEW_DRINKS);
                                userMain.setDrinksList(newDrinkList);
                                binder.drinksValueText.setText(String.valueOf(newDrinkList.size()));
                                binder.bacLevelValueText.setText(String.format("%.3f", userMain.getTotalBac()));
                                processBacStatus(userMain.getTotalBac());
                            }
                        }
                    }
                }
        );

        binder.setWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intentToSetWeight = new Intent(MainActivity.this, SetWeightActivity.class);
                startForResult.launch(intentToSetWeight);
            }
        });

        binder.viewDrinksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (userMain.drinksList.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please add drinks to view them.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentToViewDrinks = new Intent(MainActivity.this, ViewDrinksActivity.class);
                intentToViewDrinks.putExtra(VIEW_DRINKS, userMain.drinksList);
                startForResult.launch(intentToViewDrinks);
            }
        });

        binder.addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (userMain == null)
                {
                    Toast.makeText(MainActivity.this, "Please set your weight before proceeding.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentToAdd = new Intent(MainActivity.this, AddDrinksActivity.class);
                startForResult.launch(intentToAdd);
            }
        });

        binder.resetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                userMain = null;
                binder.viewDrinksBtn.setEnabled(false);
                binder.addDrinkBtn.setEnabled(false);

                binder.weightValueText.setText(R.string.default_weight_label);
                binder.drinksValueText.setText(R.string.default_num_drinks);
                binder.bacLevelValueText.setText(R.string.default_bac_level);
                binder.bacStatusText.setText(R.string.safe_label);
                binder.bacStatusText.setBackgroundColor(getResources().getColor(R.color.safe_color));
            }
        });


    }
    public void processBacStatus(double totalBac)
    {
        if (totalBac >= 0 && totalBac <= 0.08)
        {
            binder.bacStatusText.setBackgroundResource(R.color.safe_color);
            binder.bacStatusText.setText(R.string.safe_label);
        }
        else if (totalBac > 0.08 && totalBac <= 0.2)
        {
            binder.bacStatusText.setBackgroundResource(R.color.careful_color);
            binder.bacStatusText.setText(R.string.careful_label);
        }
        else if (totalBac > 0.2)
        {
            binder.bacStatusText.setText(R.string.over_limit_label);
            binder.bacStatusText.setBackgroundResource(R.color.over_limit_color);
            if (totalBac > 0.25)
            {
                binder.addDrinkBtn.setEnabled(false);
                Toast.makeText(MainActivity.this, "No more drinks for you!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        binder.addDrinkBtn.setEnabled(true);
    }
}