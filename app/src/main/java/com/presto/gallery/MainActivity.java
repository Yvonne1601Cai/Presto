package com.presto.gallery;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    int currentPage = 1, totalPages;
    int BUFFER_MAX_SIZE = 20;
    boolean scrollUp = false, intialLoad = true;
    String NUMBER_OF_PIC_PER_PAGE = "10";
    List<Photo> photos = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.imageGallery);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);


        loadPictures();
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getApplicationContext(), photos);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    System.out.println("bottom");
                    scrollUp = false;
                }
                int firstVisibleItem =((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                if(firstVisibleItem == 0){
                    // your code
                    System.out.println("top");
                    scrollUp = true;

                }
                loadPictures();
                MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getApplicationContext(), photos);
                recyclerView.setAdapter(adapter);
            }

        });

    }

    private void loadPictures(){
        try {
            if(scrollUp){
                if(currentPage > 1){
                    System.out.println("up");
                    currentPage--;
                    GetPictures getPictures = new GetPictures();
                    List output = getPictures.execute(NUMBER_OF_PIC_PER_PAGE,String.valueOf(currentPage)).get();
                    if(output.size()==3) {
                        currentPage = Integer.parseInt(output.get(0).toString());
                        totalPages = Integer.parseInt(output.get(1).toString());
                        List<Photo>newPhotos = (List<Photo>) output.get(2);

                        if(photos.size() + newPhotos.size() < BUFFER_MAX_SIZE){
                            photos.addAll(newPhotos);
                        }else{
                            newPhotos.addAll(photos.subList(0,photos.size()-newPhotos.size()));
                            photos = newPhotos;
                        }
                    }
                    for(int i = 0; i< photos.size();i++){
                        final Photo photo = photos.get(i);
                        GetSizes getSizes = new GetSizes();
                        String[] photoParams = getSizes.execute(photo.id).get();
                        photo.smallImageUrl = photoParams[0];
                        photo.width = photoParams[1];
                        photo.height = photoParams[2];
                        photo.fullImageUrl = photoParams[3];
                    }
                }
            }else{
                if(currentPage < totalPages || intialLoad){
                    intialLoad = false;
                    currentPage++;
                    GetPictures getPictures = new GetPictures();
                    List output = getPictures.execute(NUMBER_OF_PIC_PER_PAGE,String.valueOf(currentPage)).get();
                    if(output.size()==3) {
                        currentPage = Integer.parseInt(output.get(0).toString());
                        totalPages = Integer.parseInt(output.get(1).toString());
                        List<Photo>newPhotos = (List<Photo>) output.get(2);

                        if(photos.size() + newPhotos.size() < BUFFER_MAX_SIZE){
                            photos.addAll(newPhotos);
                        }else{
                            List<Photo> newList = photos.subList(photos.size()-newPhotos.size(),photos.size());
                            newList.addAll(newPhotos);
                            photos = newList;
                        }
                    }
                    for(int i = 0; i< photos.size();i++){
                        final Photo photo = photos.get(i);
                        GetSizes getSizes = new GetSizes();
                        String[] photoParams = getSizes.execute(photo.id).get();
                        photo.smallImageUrl = photoParams[0];
                        photo.width = photoParams[1];
                        photo.height = photoParams[2];
                        photo.fullImageUrl = photoParams[3];
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
