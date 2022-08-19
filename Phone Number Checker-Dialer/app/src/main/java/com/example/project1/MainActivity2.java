package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
    protected EditText textField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // linking numberText with xml
        textField= (EditText) findViewById(R.id.editTextTextPersonName);

        // associating textField with its listner
        textField.setOnEditorActionListener(numberTextListner);


    }

    TextView.OnEditorActionListener numberTextListner= new TextView.OnEditorActionListener(){
        @Override
        //actionId will be 0(or EditorInfo.IME_NULL) whenever enter will be pressed
        //actionId will return 6 (or EditorInfo.IME_ACTION_DONE) whenever done button on the keyboard will be pressed

        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            //this function will start running when actionId i.e. enter key or done on the keyboard will be pressed
            // all the lines below will start executing after user presses done on the keyboard or presses enter key
            // consider imaginary first line which just takes input and stores it in textField.text
            Intent intent=new Intent();
            Log.i("Value of text field",textView.getText().toString());

            String input=textView.getText().toString();
            Log.i("Value of actionId:",Integer.toString(actionId));

            intent.putExtra("Phone_No",input);
            boolean isInput=checkInput(input);
            if (isInput)
                setResult(RESULT_OK,intent);
            else
                setResult(RESULT_CANCELED,intent);



            finish(); // if finish is not there this method will run in an infinite loop because,
            // after pressing enter again our action on the text view will be setOnEditorActionListner
            // which will lead to that function to run again
            Log.i("Value check string",Boolean.toString(isInput));
            return false;

        }
    };
    // function for checking whether the phone number is valid or not
    boolean checkInput(String input) {
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{3})$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}