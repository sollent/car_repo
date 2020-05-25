package com.example.avto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.avto.CarPostActivity;
import com.example.avto.Interface.MainOnClick;
import com.example.avto.MainActivity;
import com.example.avto.Model.CarPost;
import com.example.avto.R;
import com.google.android.material.button.MaterialButton;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<CarPost> dataList;
    private Context context;
    private final MainOnClick clickListener;

    public CustomAdapter(Context context, List<CarPost> dataList, MainOnClick clickListener){
        this.context = context;
        this.dataList = dataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        final CarPost carPost = dataList.get(position);

        final String mileageName;
        final String engineType;
        final String transmission;

        final String currencyPrefix;
        final String usdPrice;
        final String usdPrefix;

        mileageName = carPost.getCarInfo().getMileageMeasure() != null ? carPost.getCarInfo().getMileageMeasure().getName() : "";
        engineType = carPost.getCarInfo().getEngine().getType() != null ? carPost.getCarInfo().getEngine().getType().getName().toLowerCase() : "";
        transmission = carPost.getCarInfo().getTransmission() != null ? carPost.getCarInfo().getTransmission().getName().toLowerCase() : "";


        usdPrice = carPost.getCarInfo().getPrice().getUsd() != null ? carPost.getCarInfo().getPrice().getUsd().toString() : "";
        currencyPrefix = carPost.getCarInfo().getPrice().getUsd() != null ? " / " : "";
        usdPrefix = carPost.getCarInfo().getPrice().getUsd() != null ? " USD" : "";

        String descriptionString = carPost.getCarInfo().getYear().toString() + "г.в., " +
                carPost.getCarInfo().getMileage().toString() + " " +
                mileageName + ", " +
                engineType + " " +
                carPost.getCarInfo().getEngine().getEngineCapacity() + " " +
                carPost.getCarInfo().getEngine().getEngineCapacityHint() + ", " +
                transmission;

        holder.txtTitle.setText(carPost.getTitle());
        holder.txtDescription.setText(descriptionString);
        holder.price.setText(carPost.getCarInfo().getPrice().getByn().toString() + " BYN" + currencyPrefix + usdPrice + usdPrefix);

        holder.moreAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onOpenClick(position, carPost);
            }
        });

        Glide
                .with(holder.itemView)
                .load("http://82.146.40.7/" + carPost.getPreviewImage())
                .centerCrop()
                .placeholder((R.drawable.image_spinner_animation))
                .into(holder.coverImage);

//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load("http://82.146.40.7/" + carPost.getPreviewImage())
//                .placeholder((R.drawable.image_spinner_animation))
//                .error(R.drawable.def)
//                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txtTitle, txtDescription, price;
        MaterialButton moreAbout;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            moreAbout = mView.findViewById(R.id.moreAbout);
            txtTitle = mView.findViewById(R.id.title);
            txtDescription = mView.findViewById(R.id.description);
            price = mView.findViewById(R.id.price);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }
}
