package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected Button button1;
    protected Button button2;

    protected int isInput;
    protected String phone_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking Button objects with xml files
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);

        //linking button 1 with its listner
        button1.setOnClickListener(button1Listner);

        //linking button 2 with its listner
        button2.setOnClickListener(button2Listner);

        if(savedInstanceState!=null){

        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Phone_no",phone_no);
        outState.putInt("isInput",isInput);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phone_no=savedInstanceState.getString("Phone_no");
        isInput=savedInstanceState.getInt("isInput");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
            if(intent!=null) {      //it will be null when back button pressed from activity 2
                isInput = resultCode;
                phone_no = intent.getStringExtra("Phone_No");
            }
    }

    //listner for button1
    View.OnClickListener button1Listner=new View.OnClickListener(){
        @Override
        public void onClick(View V){
            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
            startActivityForResult(intent,1);

        }
    };

    //listner for button2
    View.OnClickListener button2Listner=new View.OnClickListener(){
        @Override
        public void onClick(View V){
            if(isInput==RESULT_OK) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_no));
                startActivity(intent);
            }
            else{
                Context context = getApplicationContext();
                CharSequence text;
                if (phone_no==null)
                    text = "No Phone Number Entered";
                else
                    text = "Wrong Phone Number Entered!:"+phone_no;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    };
}
