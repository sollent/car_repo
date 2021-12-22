package com.example.avto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.avto.Adapter.ViewPagerAdapter;
import com.squareup.picasso.Picasso;

public class CarPostActivity extends AppCompatActivity {

    TextView title, shortDesc, description;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_post);

        handleIncomingIntent();
    }

    private void handleIncomingIntent() {
        Integer id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String mileageName = getIntent().getStringExtra("mileageName");
        String mileage = getIntent().getStringExtra("mileage");
        String year = getIntent().getStringExtra("year");
        String engineType = getIntent().getStringExtra("engineType");
        String engineCapacity = getIntent().getStringExtra("engineCapacity");
        String engineCapacityHint = getIntent().getStringExtra("engineCapacityHint");
        String transmission = getIntent().getStringExtra("transmission");
        String currencyPrefix = getIntent().getStringExtra("currencyPrefix");
        String usdPrice = getIntent().getStringExtra("usdPrice");
        String usdPrefix = getIntent().getStringExtra("usdPrefix");

        this.title = (TextView) findViewById(R.id.title);
        this.shortDesc = (TextView) findViewById(R.id.shortDesc);
        this.description = (TextView) findViewById(R.id.description);
//        this.image = (ImageView) findViewById(R.id.image);

        this.title.setText(title);
        this.shortDesc.setText(year + "г.в., " + mileage + " " + mileageName + "," + engineType + " " + engineCapacity + " " + engineCapacityHint + ", " + transmission);
        this.description.setText(description);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        String[] images = getIntent().getStringArrayExtra("images");

        if (images != null) {
            ViewPager viewPager = findViewById(R.id.view_pager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getApplicationContext(), images);
            viewPager.setAdapter(adapter);
        }

//        Glide.with(this)
//                .load(imageUrl)
//                .placeholder(R.drawable.image_spinner_animation)
//                .into(image);
    }
}
