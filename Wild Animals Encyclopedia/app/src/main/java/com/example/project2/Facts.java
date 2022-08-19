package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Facts extends AppCompatActivity {

    private ArrayList<String> mAvgLifeSpan = new ArrayList<String>(
            Arrays.asList("10 – 15 years (Adult, In the wild), 8 – 10 years (Adult, In the wild)",
                    "8 – 10 years (In the wild)","40 years",
                    "26 years","40 years","25 years","50-70 years","48-70 years",
                    "12-17 years","30-50 years","10-15 years","20 years"));
    private ArrayList<String> mAvgWeight = new ArrayList<String>(
            Arrays.asList("420 lbs (Adult)",
                    "200 – 680 lbs (Adult)","15-30+ pounds",
                    "2600-4200 pounds","660-1300 lbs","620-880 lbs","900-1200 lbs","8800-13000 lbs",
                    "50-70 lbs","500 lbs","25-45+ kgs","150-280 lbs"));

    private ArrayList<String> mHabitat = new ArrayList<String>(
            Arrays.asList("Open Plains To Thick Brush And Dry Thorn Forest",
                    "Rain Forests, Grasslands, Savannas And Even Mangrove Swamps",
                    "Tropical Rainforests Of Asia, Africa, And Central And South America, Or The Savannas Of Africa",
                    "Semi-Arid Savannah And Savannah Woodlands In Africa",
                    "Desert, Prairie Or Steppe",
                    "Treeless Grasslands And Savanna Woodlands",
                    "Tropical Habitats Of Africa, Asia, Australia And The Americas",
                    "Wetlands, Forest, Grassland, Savanna And Desert Across 37 Countries In Southern, Eastern, Western And Central Africa",
                    "Tropical Forests, Grassland Plains, Deserts, And Alpine Areas",
                    "Southern Florida And Includes The Everglades",
                    "Forests, Swamps, Grasslands, Deserts And In Both Fresh And Salt Water",
                    "Temperate Forests High In The Mountains Of Southwest China"));
    private ArrayList<String> mFeedingHabits = new ArrayList<String>(
            Arrays.asList("Carnivores","Carnivores","Omnivores",
                    "Herbivores","Herbivores","Herbivores","Carnivores","Herbivores",
                    "Carnivores","Carnivores","Carnivores","Herbivores"));
    private ArrayList<String> mEndangered = new ArrayList<String>(
            Arrays.asList("Yes","Yes","No","No","No","No","No","No","No","No","No","No"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        // Get the Intent used to start this Activity
        Intent intent=getIntent();
        int position= intent.getIntExtra("Position",0);

        // Name of the animal
        TextView textView= (TextView) findViewById(R.id.textView2);
        textView.setText(GridLayoutActivity.mThumbNameAnimals.get(position));

        // Average LifeSpan
        textView= (TextView) findViewById(R.id.textView4);
        textView.setText(mAvgLifeSpan.get(position));

        // Average Average Weight
        textView= (TextView) findViewById(R.id.textView6);
        textView.setText(mAvgWeight.get(position));

        // Habitat
        textView= (TextView) findViewById(R.id.textView8);
        textView.setText(mHabitat.get(position));

        // Feeding Habit
        textView= (TextView) findViewById(R.id.textView10);
        textView.setText(mFeedingHabits.get(position));

        // Endangered
        textView= (TextView) findViewById(R.id.textView12);
        textView.setText(mEndangered.get(position));

    }
}