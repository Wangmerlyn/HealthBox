package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                MyViewModel myViewModel;
                myViewModel= ViewModelProviders.of(this).get(MyViewModel.class);
                Bitmap bitmap= BitmapFactory.decodeFile(myViewModel.PathToFile);
                Toast.makeText(getApplicationContext(),myViewModel.PathToFile,Toast.LENGTH_LONG).show();
                myViewModel.myImageView.setImageBitmap(bitmap);
            }
        }
    }
}
