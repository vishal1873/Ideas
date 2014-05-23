package com.specialkid.main;



import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	//TODO: To look at optimizing this.
    	
        ImageView imageView;
        if (convertView == null) {  
            imageView = new ImageView(mContext);
            
            //The  height and width are defined in the dimensions file
            //They are defined in dp.
            imageView.setLayoutParams(new GridView.LayoutParams(
            		(int)mContext.getResources().getDimension(R.dimen.width),
            		(int)mContext.getResources().getDimension(R.dimen.height)));
            
            
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.test,  R.drawable.test,
            R.drawable.test,    R.drawable.test,
            R.drawable.test,    R.drawable.test,
            R.drawable.test,    R.drawable.test,
            R.drawable.test,    R.drawable.test,
            R.drawable.test,    R.drawable.test,
            R.drawable.test,    R.drawable.test
           
    };
}