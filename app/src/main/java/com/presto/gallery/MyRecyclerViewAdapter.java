package com.presto.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yvonne on 2018-09-22.
 */

public class MyRecyclerViewAdapter  extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{
    private List<Photo> galleryList;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<Photo> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(galleryList.get(i).title);
        viewHolder.dimension.setText(galleryList.get(i).dimemsion);
        String size = galleryList.get(i).width + '/' + galleryList.get(i).height;
        viewHolder.size.setText(size);

        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource((galleryList.get(i).));
        Glide.with(context).load(galleryList.get(i).smallImageUrl).thumbnail(0.5f).into( viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,size,dimension;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img =  view.findViewById(R.id.img);
            title = view.findViewById(R.id.title);
            size = view.findViewById(R.id.size);
            dimension = view.findViewById(R.id.dimension);

        }
    }
}
