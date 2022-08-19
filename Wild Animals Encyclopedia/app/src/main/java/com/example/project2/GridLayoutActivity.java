package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class GridLayoutActivity extends AppCompatActivity {
    protected static final String EXTRA_RES_ID = "POS";

    private ArrayList<Integer> mThumbIdsAnimals = new ArrayList<Integer>(
            Arrays.asList(R.drawable.image1, R.drawable.image2,
                    R.drawable.image3, R.drawable.image4, R.drawable.image5,
                    R.drawable.image6, R.drawable.image7, R.drawable.image8,
                    R.drawable.image9, R.drawable.image10, R.drawable.image11,
                    R.drawable.image12));
    public static ArrayList<String> mThumbNameAnimals = new ArrayList<String>(
            Arrays.asList("Lion", "Tiger" ,"Monkey","Giraffe","Camel","Zebra",
                    "Crocodile","Elephant","Leapord","Alligator","Snake","Panda" ));
    private ArrayList<String> mWikiList = new ArrayList<String>(
            Arrays.asList("https://en.wikipedia.org/wiki/Lion", "https://en.wikipedia.org/wiki/Tiger" ,
                    "https://en.wikipedia.org/wiki/Money","https://en.wikipedia.org/wiki/Giraffe",
                    "https://en.wikipedia.org/wiki/Camel","https://en.wikipedia.org/wiki/Zebra",
                    "https://en.wikipedia.org/wiki/Crocodile","https://en.wikipedia.org/wiki/Elephant",
                    "https://en.wikipedia.org/wiki/Leopard","https://en.wikipedia.org/wiki/Alligator",
                    "https://en.wikipedia.org/wiki/Snake","https://en.wikipedia.org/wiki/Giant_panda" ));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        // Create a new ImageAdapter and set it as the Adapter for this GridView
        // Android will automatically create grids
        // and it will extract the total number of grid objects by ImageAdapter.getCount() method from the adapter
        gridview.setAdapter(new ImageAdapter(this, mThumbIdsAnimals, mThumbNameAnimals));

        //register the view for context menu
        registerForContextMenu(gridview);

        // Set an setOnItemClickListener on the GridView
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Create an Intent to start the ImageViewActivity
                Intent intent = new Intent(GridLayoutActivity.this,
                        ImageViewActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                intent.putExtra(EXTRA_RES_ID, (int) id);
                intent.putExtra("Position", (int) position);


                // Start the ImageViewActivity
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list,menu);  // inflating the menu_list xml file

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            // case to jump to ImageViewActivity
            case R.id.view:
                Intent intent = new Intent(GridLayoutActivity.this,
                        ImageViewActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                intent.putExtra(EXTRA_RES_ID, (int) info.id);
                intent.putExtra("Position", (int) info.position);


                // Start the ImageViewActivity
                startActivity(intent);
                return true;
            // case to view facts i.e. jump to Facts activity
            case R.id.facts:
                Intent facts=new Intent(GridLayoutActivity.this, Facts.class);
                facts.putExtra("Position",info.position);

                startActivity(facts);
                return true;
            // case to vew wikipedia pages
            case R.id.wiki:
                Intent wikiintent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWikiList.get(info.position)));
                startActivity(wikiintent);
                return true;
            // default case
            default:
                return super.onContextItemSelected(item);

        }
    }
}
