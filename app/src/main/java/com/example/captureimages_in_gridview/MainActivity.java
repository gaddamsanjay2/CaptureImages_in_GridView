package com.example.captureimages_in_gridview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton camera;
    private static final int pic_id = 123;
    Context context;

    AlertDialog alertDialog;
    GridView gridView;
    ImageAdapter imageAdapter;
    ArrayList<Bitmap> imageModelArrayList=new ArrayList<>();
    Bitmap bitmap;
    ImageModel imageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = findViewById(R.id.button);
        gridView=findViewById(R.id.grid_view);


        imageModelArrayList = new ArrayList<>();
        imageModel = new ImageModel(this);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();

            }
        });

    }
    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, pic_id);
        } else {
            openCamera();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == pic_id) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();

            } else {
                Toast.makeText(this, "Camera permission to Required", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, pic_id);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == RESULT_OK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View root = getLayoutInflater().inflate(R.layout.custom_dialog, null);
            builder.setView(root);
            builder.setCancelable(false);

            Button cancel = root.findViewById(R.id.button_cancel);
            Button save = root.findViewById(R.id.continue_button);
            ImageView dialogImage =root.findViewById(R.id.dialog_image);

            bitmap= (Bitmap) data.getExtras().get("data");
            dialogImage.setImageBitmap(bitmap);
            imageModel.setImage(bitmap);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                    Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();


                }
            });

            save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    bitmap= (Bitmap) data.getExtras().get("data");
                    imageModel.setImage(bitmap);
                    imageModelArrayList.add(bitmap);

                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();

                    loadData();


                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View root= getLayoutInflater().inflate(R.layout.image_dialog,null);
                ImageView dialogImage=root.findViewById(R.id.imagedialog);
                dialogImage.setImageBitmap(imageModelArrayList.get(i));

               builder.setView(root);
                builder.setCancelable(true);
                alertDialog=builder.create();
                alertDialog.show();

            }
        });

    }

    private void loadData() {
            imageAdapter=new ImageAdapter(getApplicationContext(),imageModelArrayList);
            gridView.setAdapter(imageAdapter);
    }

}