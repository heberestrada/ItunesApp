package com.example.itunesapp;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RockAdapterData extends RecyclerView.Adapter<RockAdapterData.ViewHolderData> {

    ArrayList<DataListRock> rockList;

    public RockAdapterData(ArrayList<DataListRock> rockList) {
        this.rockList = rockList;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_songsr_layout,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderData dataHolder, int position) {


        if(rockList.get(position).getSongImage()!="empty"){
            Picasso.with(dataHolder.itemView.getContext())
                    .load(rockList.get(position).getSongImage())
                    .into(dataHolder.songImage);
        }else  if(rockList.get(position).getSongImage()=="empty"){
        }
        dataHolder.songName.setText(rockList.get(position).getSongName());
        dataHolder.songArtist.setText(rockList.get(position).getSongArtist());
        dataHolder.songPrice.setText(rockList.get(position).getSongPrice()+" "+rockList.get(position).getCurrency());
    }

    @Override
    public int getItemCount() {
        return rockList.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView songName,songArtist,songPrice;
        ImageView songImage;

        public ViewHolderData(View itemView) {
            super(itemView);
            songImage=itemView.findViewById(R.id.songImage);
            songName=itemView.findViewById(R.id.songName);
            songArtist=itemView.findViewById(R.id.songArtist);
            songPrice=itemView.findViewById(R.id.songPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(String.valueOf(rockList.get(getAdapterPosition()).getPreview())), "audio/*");
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}