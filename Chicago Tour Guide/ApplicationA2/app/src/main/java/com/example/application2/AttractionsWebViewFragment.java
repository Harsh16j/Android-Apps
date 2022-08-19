package com.example.application2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

// Webview fragment for the Attractions_Fragment_Activity
public class AttractionsWebViewFragment extends Fragment {

    public View attractionsWebView;
    public WebView attractionsWebViewLayout;

    private String url;

    private ListViewModel model;  // View model for sharing data between fragments



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        attractionsWebView = inflater.inflate(R.layout.attractions_fragment_web_view, container, false);
        attractionsWebViewLayout = (WebView) attractionsWebView.findViewById(R.id.webView);

        //enable javascript
        attractionsWebViewLayout.getSettings().setJavaScriptEnabled(true);

        //model=new ViewModelProvider(this).get(ListViewModel.class);

        return attractionsWebView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getting ViewModel
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // creating observe to change the web view on changes in clicks
        model.getSelectedItem().observe(getViewLifecycleOwner(),item->{
            url=MainActivity.attractionsWeblinkArray[item];
            attractionsWebViewLayout.loadUrl(url);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // To check if the fragment is getting destroyed or not
        Log.i("Destroyed","WebView Destroyed");
    }
}



 /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //requireActivity() a method that returns the non-null activity instance to fragment or throws an exception.
        // If you are 100% sure that in your fragment's lifecycle, activity is not null, use requireActivity() as it needs no !!
        //the below line of code gets the model object from the ListViewModel class
        //ViewModelProvider provides the view model from the specified ViewModel Class

        /*
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // storing the position of the title selected in the position variable
        position=model.getSelectedItem().getValue();

        //View v= inflater.inflate(R.layout.fragment_web_view, container, false);

        attractionsWebView = (WebView) attractionsWebView.findViewById(R.id.attractionsWebview); //from fragment_web_view.xml
        attractionsWebView.loadUrl(MainActivity.attractionsWeblinkArray[0]); //loading url corresponding to position index of String array

        // Enable Javascript
            WebSettings webSettings = attractionsWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Force links and redirects to open in the WebView instead of in a browser
            attractionsWebView.setWebViewClient(new WebViewClient());

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_web_view, container, false);

        //references: https://stackoverflow.com/questions/31159149/using-webview-in-fragment/31159185

        */
// Inflate the layout for this fragment
        /*
        View attractionsWebView = inflater.inflate(R.layout.fragment_web_view, container, false);

        model=new ViewModelProvider(this).get(ListViewModel.class);
        model.selectItem(0);
        position = model.getSelectedItem().getValue();

        //String url = MainActivity.attractionsWeblinkArray[position];
        String url="https://www.google.com";
        WebView attractionsWebViewLayout = (WebView) attractionsWebView.findViewById(R.id.webView);

        //enable javascript
        attractionsWebViewLayout.getSettings().setJavaScriptEnabled(true);

        attractionsWebViewLayout.loadUrl(url);

        return attractionsWebView ;
    }
    */
