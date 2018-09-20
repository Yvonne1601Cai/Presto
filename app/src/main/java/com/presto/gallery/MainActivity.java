package com.presto.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    int currentPage = 0, totalPages;
    String NUMBER_OF_PIC_PER_PAGE = "10";
    ArrayList<Photo> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.image);
/*
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPictures getPictures = new GetPictures(new GetPictures.AsyncResponse() {
                    @Override
                    public void processFinish(List output) {

                        if(output.size()==3){
                            currentPage = Integer.parseInt(output.get(0).toString());
                            totalPages = Integer.parseInt(output.get(1).toString());
                            photos = (ArrayList<Photo>) output.get(2);
                        }
                    }
                });
                getPictures.execute(NUMBER_OF_PIC_PER_PAGE,String.valueOf(currentPage+1));
            }
        });*/
    }


}
