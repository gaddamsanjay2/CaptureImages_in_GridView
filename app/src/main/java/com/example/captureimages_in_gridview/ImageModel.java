package com.example.captureimages_in_gridview;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageModel {
    Bitmap image;
    Context context;

    public ImageModel(Context context) {
        this.context = context;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
