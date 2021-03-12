package com.example.myfirstapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class driverOptionsAdapter extends RecyclerView.Adapter<driverOptionsAdapter.driverOptionsViewHolder> {
    private ArrayList<driverOptionItem> mDriverOptionList;

    public static class driverOptionsViewHolder extends RecyclerView.ViewHolder{
        public ImageView distanceImage;
        public ImageView timeElapsedImage;
        public ImageView arrivalTimeImage;
        public TextView distanceText;
        public TextView timeElapsedText;
        public TextView arrivalTimeText;


        public driverOptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            distanceImage = itemView.findViewById(R.id.cardItem1Image);
            timeElapsedImage = itemView.findViewById(R.id.cardItem2Image);
            arrivalTimeImage = itemView.findViewById(R.id.cardItem3Image);
            distanceText = itemView.findViewById(R.id.cardItem1Text);
            timeElapsedText = itemView.findViewById(R.id.cardItem2Text);
            arrivalTimeText = itemView.findViewById(R.id.cardItem3Text);


        }
    }

    public driverOptionsAdapter(ArrayList<driverOptionItem> driverOptionList){
        mDriverOptionList = driverOptionList;
    }

    @NonNull
    @Override
    public driverOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.driveritem, parent, false);
        driverOptionsViewHolder doh = new driverOptionsViewHolder(v);
        return doh;
    }

    @Override
    public void onBindViewHolder(@NonNull driverOptionsViewHolder holder, int position) {
        driverOptionItem currentItem = mDriverOptionList.get(position);

        holder.distanceImage.setImageResource(currentItem.getmDistanceImage());
        holder.timeElapsedImage.setImageResource(currentItem.getmTimeElapsedImage());
        holder.arrivalTimeImage.setImageResource(currentItem.getmTimeArrivalImage());
        holder.distanceText.setText(currentItem.getmDistanceText());
        holder.timeElapsedText.setText(currentItem.getmTimeElapsedText());
        holder.arrivalTimeText.setText(currentItem.getmTimeArrivalText());

    }

    @Override
    public int getItemCount() {
        return mDriverOptionList.size();
    }
}
