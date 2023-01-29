package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hw02.databinding.ActivityAddDrinksBinding;
import com.example.hw02.databinding.ActivityViewDrinksBinding;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {
    ActivityViewDrinksBinding binder;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivityViewDrinksBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setTitle(R.string.view_drinks_screen);

        ArrayList<Drink> drinklist = (ArrayList<Drink>) getIntent().getSerializableExtra(MainActivity.VIEW_DRINKS);
        updateDrinkData(0, drinklist);

        binder.buttonViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intentToReturn = new Intent();
                intentToReturn.putExtra(MainActivity.VIEW_DRINKS, drinklist);
                setResult(RESULT_OK, intentToReturn);
                finish();
            }
        });

        binder.drinkRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (currentIndex == drinklist.size() - 1)
                {
                    updateDrinkData(0, drinklist);
                }
                else
                {
                    updateDrinkData(currentIndex+1, drinklist);
                }
            }
        });

        binder.drinkLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (currentIndex == 0)
                {
                    updateDrinkData(drinklist.size() - 1,drinklist);
                }
                else
                {
                    updateDrinkData(currentIndex - 1, drinklist);
                }
            }
        });

        binder.drinkDeleteDrashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (drinklist.size() == 1)
                {
                    drinklist.remove(0);
                    Toast.makeText(ViewDrinksActivity.this, "All drinks are removed.", Toast.LENGTH_SHORT).show();
                    Intent intentCompleted = new Intent();
                    intentCompleted.putExtra(MainActivity.VIEW_DRINKS, drinklist);
                    setResult(RESULT_OK, intentCompleted);
                    finish();
                }
                else
                {
                    drinklist.remove(currentIndex);
                    if (currentIndex == 0)
                    {
                        updateDrinkData(currentIndex, drinklist);
                    }
                    else
                    {
                        updateDrinkData(currentIndex-1, drinklist);
                    }

                }

            }
        });

    }

    public void updateDrinkData(int indexNum, ArrayList<Drink> listDrinks)
    {
        Drink currentDrink = listDrinks.get(indexNum);
        binder.viewDrinkOz.setText(currentDrink.drinkSizeOz + " oz");
        binder.viewDrinkListSize.setText(String.valueOf(listDrinks.size()));
        binder.viewDrinkAlcPercent.setText(currentDrink.getAlcoholPercent() + "% Alcohol");
        binder.viewDrinkNum.setText(String.valueOf(indexNum + 1));
        binder.viewDrinkAddedDate.setText(getString(R.string.view_drink_added) + " " + currentDrink.drankDate.toString());
        currentIndex = indexNum;
    }
}