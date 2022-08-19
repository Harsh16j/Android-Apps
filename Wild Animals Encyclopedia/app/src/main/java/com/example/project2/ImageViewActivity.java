package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent used to start this Activity
        Intent intent = getIntent();

        // Make a new ImageView
        // Example of programmatic layout definition
        ImageView imageView = new ImageView(getApplicationContext());

        // Get the ID of the image to display and set it as the image for this ImageView
        imageView.setImageResource(intent.getIntExtra(GridLayoutActivity.EXTRA_RES_ID, 0));

        //set the content view through the image view object
        setContentView(imageView);

        // defining on click listener for the image
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //creating intent to jump to the Facts activity
                Intent facts=new Intent(ImageViewActivity.this, Facts.class);

                // putting the position of the grid object as an extra inside the intent
                facts.putExtra("Position",intent.getIntExtra("Position",0));

                startActivity(facts);
            }
        });
    }
}

