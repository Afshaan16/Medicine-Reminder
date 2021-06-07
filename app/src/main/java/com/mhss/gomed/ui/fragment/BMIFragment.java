package com.mhss.gomed.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mhss.gomed.MainActivity;
import com.mhss.gomed.R;
import com.mhss.gomed.preferences.Preferences;

public class BMIFragment extends Fragment {

    EditText weight, height;
    Button calculate;
    Context mContext;
    TextView result;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bmi, container, false);

        mContext = root.getContext();

        weight = (EditText) root.findViewById(R.id.input_weight);
        height = (EditText) root.findViewById(R.id.input_height);
        calculate = (Button) root.findViewById(R.id.btn_calculate);
        result = (TextView) root.findViewById(R.id.result);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weight.getText() != null && weight.getText().length() > 0) {
                    if (height.getText() != null && height.getText().length() > 0) {
                        String str1 = weight.getText().toString();
                        String str2 = height.getText().toString();
                        //Get the user values from the widget reference
                        float weight = Float.parseFloat(str1);
                        float height = Float.parseFloat(str2) / 100;

                        //Calculate BMI value
                        float bmiValue = calculateBMI(weight, height);

                        //Define the meaning of the bmi value
                        String bmiInterpretation = interpretBMI(bmiValue);

                        result.setText(String.valueOf(bmiValue + " - " + bmiInterpretation));

                    } else {
                        Toast.makeText(mContext, "Please Enter Height ( CM )", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please Enter Weight ( KG )", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    //Calculate BMI
    private float calculateBMI(float weight, float height) {
        return (float) (weight / (height * height));
    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }


}