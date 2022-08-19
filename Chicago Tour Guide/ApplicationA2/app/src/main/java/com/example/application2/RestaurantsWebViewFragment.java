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

// Webview fragment for the Restaurants_Fragment_Activity
public class RestaurantsWebViewFragment extends Fragment {

    public View restaurantsWebView;
    public WebView restaurantsWebViewLayout;

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
        restaurantsWebView = inflater.inflate(R.layout.restaurants_fragment_web_view, container, false);
        restaurantsWebViewLayout = (WebView) restaurantsWebView.findViewById(R.id.webView);

        //enable javascript
        restaurantsWebViewLayout.getSettings().setJavaScriptEnabled(true);

        //model=new ViewModelProvider(this).get(ListViewModel.class);

        return restaurantsWebView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getting ViewModel
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // creating observe to change the web view on changes in clicks
        model.getSelectedItem().observe(getViewLifecycleOwner(),item->{
            url=MainActivity.restaurantsWeblinkArray[item];
            restaurantsWebViewLayout.loadUrl(url);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // To check if the fragment is getting destroyed or not
        Log.i("Destroyed","WebView Destroyed");
    }
}