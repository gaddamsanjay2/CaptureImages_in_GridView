package com.example.captureimages_in_gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> imageModelArrayList=new ArrayList<>();
    AlertDialog alertDialog;
    LayoutInflater layoutInflater;

    public ImageAdapter(Context context, ArrayList<Bitmap> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.custom_grid, null);
        ImageView imageView=root.findViewById(R.id.grid_image);

        imageView.setImageBitmap(imageModelArrayList.get(i));

        return root;
    }
}
