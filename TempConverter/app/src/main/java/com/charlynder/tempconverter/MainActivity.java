package com.charlynder.tempconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    // declare fahrenheit & Celsius values
    private EditText fahrenheitIn;
    private TextView celsiusOut;

    // create sharedPreferences obj
    private SharedPreferences savedValues;

    // create a string & float
    private String fahrenheitInString = "";
    private float celsiusOutFloat = .5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        fahrenheitIn = (EditText) findViewById(R.id.txtFahrenheitIn);
        celsiusOut = (TextView) findViewById(R.id.lblCelsiusOut);

        // set listener
        fahrenheitIn.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    public void setCelsius() {
        fahrenheitInString = fahrenheitIn.getText().toString();
        float fahren;
        if (fahrenheitInString.equals("")) {
            fahren = 0;
        } else {
            fahren = Float.parseFloat(fahrenheitInString);
        }
        float converted = convCelsius(fahren);

        celsiusOut.setText("" + converted);
    }

    public float convCelsius(float fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    @Override
    public void onPause() {
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitIn", fahrenheitInString);
        editor.putFloat("celsiusOut", celsiusOutFloat);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        fahrenheitInString = savedValues.getString("fahrenheitInString", "");
        celsiusOutFloat = savedValues.getFloat("celsiusOutFloat", 0.5f);

        super.onResume();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            setCelsius();
        }
        return false;
    }
}