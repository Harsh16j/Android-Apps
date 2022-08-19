package com.example.application2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // declaring Strings for defining Actions for receiving broadcast
    public static final String Action_Restaurants="Action_Restaurants";  //For receiving broadcast Restaurants Action
    public static final String Action_Attractions="Action_Attractions";  //For receiving broadcast Attractions Action

    //Declaring the broadcast receiver and intent filter
    protected AttractionsBroadcastReceiver attractionsReceiver;
    protected RestaurantsBroadcastReceiver restaurantsReceiver;

    //declaring String arrays to be displayed in the title fragments
    public static String[] attractionsTitleArray;
    public static String[] restaurantsTitleArray;

    //declaring String arrays containing the web link for the web view
    public static String[] attractionsWeblinkArray;
    public static String[] restaurantsWeblinkArray;


    //Creating an activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the strings from the strings.xml file
        attractionsTitleArray= getResources().getStringArray(R.array.attractionsArray);
        restaurantsTitleArray= getResources().getStringArray(R.array.restaurantsArray);

        attractionsWeblinkArray= getResources().getStringArray(R.array.attractionsWeblinkArray);
        restaurantsWeblinkArray= getResources().getStringArray(R.array.restaurantsWeblinkArray);

        //Toolbar
        Toolbar mToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        //Creating an instance of the receiver
        attractionsReceiver=new AttractionsBroadcastReceiver();
        restaurantsReceiver=new RestaurantsBroadcastReceiver();

        // Registering the receiver with its filters
        registerReceiver(attractionsReceiver,new IntentFilter(Action_Attractions));
        registerReceiver(restaurantsReceiver,new IntentFilter(Action_Restaurants));


    }

    ///////////////////////////////////////////////////////////////////////
    //Options menu
    // Overriding onCreateOptionsMenu to provide create custom Options menu
    // when overrides without the custom toolbar it is added to the default app bar
    // the name that displays on the app bar can be changed in the manifest.xml file
    // called by the OS once
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflatar= getMenuInflater();
        inflatar.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // called by the OS when the any option in the options menu selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.attractions:
                startActivity(
                        new Intent(getApplicationContext(), Attractions_Activity.class));
                break;
            case R.id.restaurants:
                startActivity(new Intent(getApplicationContext(),Restaurants_Activity.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    // if Receiver is registered in onCreate() it should be unregistered in onDestroy()
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(attractionsReceiver);
        unregisterReceiver(restaurantsReceiver);
    }
}