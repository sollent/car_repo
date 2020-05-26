package com.example.avto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.avto.Interface.MainOnClick;
import com.example.avto.Model.CarPost;
import com.example.avto.R;
import com.google.android.material.button.MaterialButton;

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

        String[] images = new String[1];
        boolean existImage = true;

        if (carPost.getImages() != null) {
            images = new String[carPost.getImages().length + 1];
            images[0] = carPost.getPreviewImage();

            for (int i = 1; i < images.length; i++) {
                images[i] = carPost.getImages()[i - 1];
            }
        } else if (carPost.getPreviewImage() != null) {
            images[0] = carPost.getPreviewImage();
        } else {
            existImage = false;
        }

        if (existImage) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(context, images);
            holder.viewPager.setAdapter(adapter);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onOpenClick(position, carPost);
            }
        });
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

        ViewPager viewPager;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtDescription = mView.findViewById(R.id.description);
            price = mView.findViewById(R.id.price);

            viewPager = mView.findViewById(R.id.view_pager);
        }
    }
}
