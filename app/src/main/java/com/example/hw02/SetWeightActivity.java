package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hw02.databinding.ActivitySetWeightBinding;

public class SetWeightActivity extends AppCompatActivity {

    ActivitySetWeightBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivitySetWeightBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        setTitle(R.string.set_weight_screen_title);

        binder.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (binder.editWeightLbs.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(SetWeightActivity.this, "Please enter your weight.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userGender = binder.genderRadioGroup.getCheckedRadioButtonId() == R.id.femaleRadio ? "Female" : "Male";
                double userWeight = Double.parseDouble(binder.editWeightLbs.getText().toString());
                double valueR = userGender.equals("Male") ? 0.73 : 0.66;

                Profile userProfile = new Profile(userGender, userWeight, valueR);
                Intent intentToMain = new Intent(SetWeightActivity.this, MainActivity.class);
                intentToMain.putExtra(MainActivity.WEIGHT_KEY, userProfile);
                setResult(RESULT_OK, intentToMain);
                finish();
            }
        });
        binder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}