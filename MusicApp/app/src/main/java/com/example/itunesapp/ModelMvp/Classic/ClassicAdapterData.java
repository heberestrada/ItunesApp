package com.example.itunesapp.ModelMvp.Classic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itunesapp.ModelMvp.Views.ViewControllerClassic;
import com.example.itunesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.itunesapp.ModelMvp.Classic.ClassicFragment.adapterC;
import static com.example.itunesapp.ModelMvp.Classic.ClassicFragment.classicSongsInScreen;

public class ClassicAdapterData extends RecyclerView.Adapter<ClassicAdapterData.ViewHolderData> implements ViewControllerClassic {


    public static ArrayList<DataListClassic> classicList;
    public String Base_Url="https://itunes.apple.com";

    public ClassicAdapterData(ArrayList<DataListClassic> classicList) {
        this.classicList = classicList;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_songsc_layout,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderData dataHolder, int position) {
        Picasso.with(dataHolder.itemView.getContext())
                .load(classicList.get(position).getSongImage())
                .into(dataHolder.songImage);
        dataHolder.songName.setText(classicList.get(position).getSongName());
        dataHolder.songArtist.setText(classicList.get(position).getSongArtist());
        dataHolder.songPrice.setText(classicList.get(position).getSongPrice()+" "+classicList.get(position).getCurrency());
    }

    @Override
    public int getItemCount() {
        return classicList.size();
    }

    @Override
    public void PopulateData(String songImage, String songName, String songArtist, String songPrice, String currency, String preview) {
        classicSongsInScreen.setAdapter(adapterC);
    }

    @Override
    public String getBaseUrl() {
        return Base_Url;
    }

    @Override
    public void showError(String errorMessage) {

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
                    intent.setDataAndType(Uri.parse(String.valueOf(classicList.get(getAdapterPosition()).getPreview())), "audio/*");
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}