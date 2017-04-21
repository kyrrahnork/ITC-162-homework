package com.murach.tempconverter;


import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.text.DecimalFormat;


public class TempConverterActivity extends Activity implements TextView.OnEditorActionListener{

	//define variables for widgets
	private EditText fahrenheitEditText;
	private TextView celsiusTextView;

	//define shared preferences object
	private SharedPreferences savedValues;

	//define instance variables to be saved
	private String fahrenheitDegreeString = "";
	private float celsiusDegrees;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_converter);

		//get references to widgets
		fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
		celsiusTextView = (TextView) findViewById(R.id.celciusTextView);

		//set listeners
		fahrenheitEditText.setOnEditorActionListener(this);

		//get shared preferences
		savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
	}

	@Override
	public void onPause(){
		//save the instance variables
		Editor editor = savedValues.edit();
		editor.putString("fahrenheitDegreeString", fahrenheitEditText.getText().toString());
		editor.putFloat("celsiusDegrees", celsiusDegrees);
		editor.commit();

		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();

		//get instance variables
		fahrenheitEditText.setText(savedValues.getString("fahrenheitDegreeString", "0"));
		celsiusDegrees = savedValues.getFloat("celsiusDegrees", celsiusDegrees);

		//set celsius degrees on its widget

		celsiusTextView.setText(fahrenheitDegreeString);

		//calculate and display
		calculateAndDisplay();
	}

	public void calculateAndDisplay(){
		//get fahrenheit amount
		fahrenheitDegreeString = fahrenheitEditText.getText().toString();
		float fahrenheitDegrees = Float.parseFloat(fahrenheitDegreeString);

		//calculate celsius
		celsiusDegrees = ((fahrenheitDegrees - 32) * (5f/9f));
		DecimalFormat df = new DecimalFormat("#.##");
		celsiusTextView.setText(df.format(celsiusDegrees));

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
		if (actionId == EditorInfo.IME_ACTION_DONE ||
				actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
			calculateAndDisplay();
		}
		return false;
	}



}