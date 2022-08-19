package com.example.application2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Attractions_Activity extends AppCompatActivity {

    //declaring object instances of the fragment classes
    private AttractionsTitlesFragment attractionsTitlesFragment;
    private AttractionsWebViewFragment attractionsWebViewFragment;

    //declaring fragment manager
    FragmentManager attractionsFragmentManager;

    //declaring framelayout objects to be extracted from the layout.xml file
    private FrameLayout attractionsTitleFrameLayout,attractionsWebviewFrameLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    //declaring ViewModel for communicating with fragments
    private ListViewModel attractionsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_fragment);

        //Toolbar
        Toolbar mToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        // getting frame layouts
        attractionsTitleFrameLayout=(FrameLayout) findViewById(R.id.attractions_title_fragment_container);
        attractionsWebviewFrameLayout= (FrameLayout) findViewById(R.id.attractions_webview_fragment_container);
        //getting fragment manager
        attractionsFragmentManager=getSupportFragmentManager();


        //retaining fragments if already created
        // ATF - attractions titles fragment
        attractionsTitlesFragment=(AttractionsTitlesFragment) attractionsFragmentManager.findFragmentByTag("ATF_Tag");
        // AWF - attracions webview fragment
        attractionsWebViewFragment= (AttractionsWebViewFragment) attractionsFragmentManager.findFragmentByTag("AWF_Tag");


        if(attractionsTitlesFragment==null){
            attractionsTitlesFragment=new AttractionsTitlesFragment();
        }
        else{
            //to check if the fragment is destroyed or not
            Log.i("NotDestroyed","Titles NotDestroyed");
        }
        if(attractionsWebViewFragment==null){
            attractionsWebViewFragment= new AttractionsWebViewFragment();
        }
        else{
            //to check if the fragment is destroyed or not
            Log.i("NotDestroyed","WebView NotDestroyed");
        }

        //Starting a new transaction
        final FragmentTransaction fragmentTransaction= attractionsFragmentManager.beginTransaction();

        // Adding the AttractionsTitlesFragment to the layout
        // replacing the fragment box defined in the xml file with the instance of our AttractionsTitlesFragment object
        fragmentTransaction.replace(
                R.id.attractions_title_fragment_container,
                attractionsTitlesFragment,"ATF_Tag");


        // to check if web view is working or not
        //fragmentTransaction.replace(R.id.attractions_webview_fragment_container,attractionsWebViewFragment);


        //commit the Fragment transaction
        fragmentTransaction.commit();

        //Adding a OnBackStackChangedListener to reset the layout when the back stack changes
        attractionsFragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setLayout(); // set Layout defined below to in the code
                    }
                }
        );

        // MutableLiveData observer
        attractionsModel=new ViewModelProvider(this).get(ListViewModel.class);

        attractionsModel.getSelectedItem().observe(this,item->{
            Log.i("CheckCheck","inside observe"+item.toString());
            if(!attractionsWebViewFragment.isAdded()){
                FragmentTransaction fragmentTransaction2=attractionsFragmentManager.beginTransaction();

                fragmentTransaction2.replace(
                        R.id.attractions_webview_fragment_container,
                        attractionsWebViewFragment,"AWF_Tag");
                //adding transaction to backstack
                fragmentTransaction2.addToBackStack(null);


                //commit the fragment transaction
                fragmentTransaction2.commit();

                // force android to execute the committed FragmentTransaction
                attractionsFragmentManager.executePendingTransactions();
            }
            setLayout();
        });

        setLayout();
    }
    //defining setLayout function
    private void setLayout(){
        // if web view has not been added
        if(!attractionsWebViewFragment.isAdded()){
            // make title fragment layout occupy the entire layout
            attractionsTitleFrameLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT)
            );
        }
        // when web view in display
        else{
            // title will occupy 1/3rd of the layout
            attractionsTitleFrameLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(0,MATCH_PARENT,1f));// width will be determined by the weight(third parameter)
            attractionsWebviewFrameLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(0,MATCH_PARENT,2f));
        }
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
}