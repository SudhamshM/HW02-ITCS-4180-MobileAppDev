package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.example.hw02.databinding.ActivityAddDrinksBinding;

public class AddDrinksActivity extends AppCompatActivity {

    ActivityAddDrinksBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivityAddDrinksBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setTitle(R.string.add_drinks_screen_title);

        binder.alcoholPctSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
            {
                binder.alcPctValue.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        binder.buttonCancelAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binder.buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                double drinkSize = 0, alcoholPercent;
                switch (binder.radioGroup.getCheckedRadioButtonId())
                {
                    case (R.id.radio1Oz):
                        drinkSize = 1;
                        Log.d("hw02", "onClick: selected 1 oz");
                        break;
                    case (R.id.radio5Oz):
                        drinkSize = 5;
                        Log.d("hw02", "onClick: selected 5 oz");
                        break;
                    case (R.id.radio12Oz):
                        drinkSize = 12;
                        Log.d("hw02", "onClick: selected 12 oz");
                        break;
                }
                alcoholPercent = binder.alcoholPctSeekbar.getProgress();
                Log.d("hw02", "onClick: percent: " + alcoholPercent);
                Drink newDrink = new Drink(drinkSize, alcoholPercent);
                Intent intentReturnToAdd = new Intent();
                intentReturnToAdd.putExtra(MainActivity.ADD_DRINKS, newDrink);
                Log.d("hw02", "onClick: add drink" + newDrink.toString());
                setResult(RESULT_OK, intentReturnToAdd);
                finish();

            }
        });

    }
}