package com.example.project;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPage extends Fragment {
    private String imgFilePath;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");


    public WorkPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView_User_Name;
        textView_User_Name=getActivity().findViewById(R.id.textView_User_Name);
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String HelloWord;
        if(hour>=6&&hour<11){
            HelloWord=getActivity().getResources().getString(R.string.GoodMorning);
        }
        else if(hour>=11&&hour<=13){
            HelloWord=getActivity().getResources().getString(R.string.GoodNoon);
        }
        else if(hour>13&&hour<=17){
            HelloWord=getActivity().getResources().getString(R.string.GoodAfternoon);
        }
        else if(hour>17&&hour<=23){
            HelloWord=getActivity().getResources().getString(R.string.GoodEvening);
        }
        else {
            HelloWord=getActivity().getResources().getString(R.string.LateNight);
        }
        MyViewModel myViewModel;
        myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        myViewModel.myImageView=getActivity().findViewById(R.id.textView_Preview);
        textView_User_Name.setText(HelloWord+myViewModel.User_Name);
        Button button_Analyze,button_TakePicture;
        button_Analyze=getActivity().findViewById(R.id.button_Analyze);
        button_TakePicture=getActivity().findViewById(R.id.button_TakePicture);
        button_TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED||
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                    PackageManager.PERMISSION_DENIED){
                        String[] Permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(Permission,2);
                    }
                }
                dispatchPictureTakerAction();
               /* NavController navController;
                navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_workPage_to_takePicture);*/
            }
        });
    }

    private void dispatchPictureTakerAction(){
        Intent takePic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getActivity().getPackageManager())!=null){
            File PhotoFile=null;
            PhotoFile=createPhotoFile();
            if(PhotoFile!=null){
                MyViewModel myViewModel;
                myViewModel= ViewModelProviders.of(getActivity()).get(MyViewModel.class);
                myViewModel.PathToFile=PhotoFile.getAbsolutePath();
                Uri PhotoUri= FileProvider.getUriForFile(getActivity(),"com.example.project",PhotoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,PhotoUri);
                getActivity().startActivityForResult(takePic,1);
            }
        }

    }
    private File createPhotoFile(){
        String Name=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir=getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image=null;
        try {
            image=File.createTempFile(Name,".jpg",storageDir);
        }catch (IOException e){
            Log.i("mylog","Excep:"+e.toString());
        }
        return image;
    }




}
