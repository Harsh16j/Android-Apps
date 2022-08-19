package com.example.applicationa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // declaring Strings for defining Actions for broadcasting
    public static final String Action_Restaurants="Action_Restaurants";  //For broadcasting Restaurants Action
    public static final String Action_Attractions="Action_Attractions";  //For broadcasting Attractions Action


    // Declaration of the objects of button class
    protected Button Attractions_button;
    protected Button Restaurants_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // creating view from the activity_main XML file
        setContentView(R.layout.activity_main);

        // extracting button objects from the xml file
        Attractions_button= (Button) findViewById(R.id.attractions);
        Restaurants_button= (Button) findViewById(R.id.restaurants);

        //Linking the button objects with there listeners
        Attractions_button.setOnClickListener(attractionsListener);
        Restaurants_button.setOnClickListener(restaurantsListener);

    }


    //Listeners definition

    //Attractions button listener
    View.OnClickListener attractionsListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent attractionsIntent=new Intent(Action_Attractions);  // Action_Attractions="Action_Attractions"

            //Toast message (0 means short toast message)
            Toast.makeText(getApplicationContext(),"Attractions loading", Toast.LENGTH_SHORT).show();

            sendBroadcast(attractionsIntent);
        }
    };

    //Restaurants button listener
    View.OnClickListener restaurantsListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent restaurantsIntent=new Intent(Action_Restaurants);  // Action_Restaurants="Action_Restaurants"

            //Toast message (0 means short toast message)
            Toast.makeText(getApplicationContext(),"Restaurants loading", Toast.LENGTH_SHORT).show();

            sendBroadcast(restaurantsIntent);
        }
    };
}