package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;          // This will have to be passed to the ImageView
    private List<Integer> mThumbIds;   // Adapter must store AdapterView's items
    private List<String> mThumbName;    // Adapter must store AdapterView's thumbnails names

    // Save the list of image IDs and the context
    public ImageAdapter(Context c, List<Integer> ids, List<String> name) {
        mContext = c;
        this.mThumbIds = ids;
        this.mThumbName=name;
    }

    // Now the methods inherited from abstract superclass BaseAdapter

    // Return the number of items in the Adapter
    @Override
    public int getCount() {
        return mThumbIds.size();
    }

    // Return the data item at position
    @Override
    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    // Will get called to provide the ID that
    // is passed to OnItemClickListener.onItemClick()
    @Override
    public long getItemId(int position) {
        return mThumbIds.get(position);
    }

    // Return an ImageView for each item referenced by the Adapter, method will be called by the OS
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid_item=convertView;

        // if gridview is not recycled
        if(grid_item==null){
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // inflating the grid_item.xml into grid_item or convertView
            // Here we are defining how the grid view will look
            grid_item=inflater.inflate(R.layout.grid_item,null);

            //extracting ImageView
            ImageView imageView=(ImageView) grid_item.findViewById(R.id.grid_item_image);

            //extracting TextView
            TextView textView=(TextView) grid_item.findViewById(R.id.grid_item_text);

            //setting image and text view
            imageView.setImageResource(mThumbIds.get(position));
            textView.setText(mThumbName.get(position));
        }

        return grid_item;
    }


}
