package com.example.application2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

//List fragment for the Attractions_Fragment_Actvity
public class AttractionsTitlesFragment extends ListFragment {
    private ListViewModel model;  // View model for sharing data between fragments

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To keep fragment around, call setRetainInstance(true) in fragment’s class
        //onCreate() method
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //requireActivity() a method that returns the non-null activity instance to fragment or throws an exception.
        // If you are 100% sure that in your fragment's lifecycle, activity is not null, use requireActivity() as it needs no !!
        //the below line of code gets the model object from the ListViewModel class
        //ViewModelProvider provides the view model from the specified ViewModel Class
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        setListAdapter(new ArrayAdapter<>(getActivity(),R.layout.title_item,MainActivity.attractionsTitleArray));
        // Set List mode to single choice
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // set item as checked
        getListView().setItemChecked(position,true);

        //Updating this change in ViewModel
        model.selectItem(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // To check if the fragment is getting destroyed or not
        Log.i("Destroyed","Titles Destroyed");
    }
}
