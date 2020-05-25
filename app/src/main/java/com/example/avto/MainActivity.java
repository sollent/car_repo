package com.example.avto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.avto.Adapter.CustomAdapter;
import com.example.avto.Interface.MainOnClick;
import com.example.avto.Model.CarPost;
import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.Service.GetDataService;
import com.example.avto.Service.Model.SimpleFilterModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.pusher.pushnotifications.PushNotifications;


public class MainActivity extends AppCompatActivity implements MainOnClick {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    GetDataService service;

    FloatingActionButton updateBtn, filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = RetrofitClientInstance.getRetrofitIntance().create(GetDataService.class);

        PushNotifications.start(getApplicationContext(), "58f8860e-65e3-4e17-9bd8-6419b9ae97f4");
        PushNotifications.addDeviceInterest("sollent");

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Загрузка....");
        progressDialog.show();

        updateBtn = findViewById(R.id.updateBtn);
        filterButton = findViewById(R.id.filterButton);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                loadCarPosts();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

        if (getIntent().getSerializableExtra("filter") != null) {
            SimpleFilterModel filterModel = (SimpleFilterModel) getIntent().getSerializableExtra("filter");
            filterLoad(filterModel);
        } else {
            loadCarPosts();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_register:

                return true;
            case R.id.action_subscriptions:
                Intent subscriptionIntent = new Intent(getApplicationContext(), SubscriptionActivity.class);
                startActivity(subscriptionIntent);
                return true;
            case R.id.action_account:
                Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(accountIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterLoad(SimpleFilterModel filterModel) {
        Call<List<CarPost>> call = service.filter(filterModel);
        call.enqueue(new Callback<List<CarPost>>() {
            @Override
            public void onResponse(Call<List<CarPost>> call, Response<List<CarPost>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<CarPost>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCarPosts()
    {
        Call<List<CarPost>> call = service.getAllCarPosts();
        call.enqueue(new Callback<List<CarPost>>() {
            @Override
            public void onResponse(Call<List<CarPost>> call, Response<List<CarPost>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<CarPost>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<CarPost> photoList) {
        recyclerView = findViewById(R.id.tweets_recycler_view);
        adapter = new CustomAdapter(this,photoList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOpenClick(int position, CarPost carPost) {
        final String mileageName = carPost.getCarInfo().getMileageMeasure() != null ? carPost.getCarInfo().getMileageMeasure().getName() : "";
        final String engineType = carPost.getCarInfo().getEngine().getType() != null ? carPost.getCarInfo().getEngine().getType().getName().toLowerCase() : "";
        final String transmission = carPost.getCarInfo().getTransmission() != null ? carPost.getCarInfo().getTransmission().getName().toLowerCase() : "";

        final String usdPrice = carPost.getCarInfo().getPrice().getUsd() != null ? carPost.getCarInfo().getPrice().getUsd().toString() : "";
        final String currencyPrefix = carPost.getCarInfo().getPrice().getUsd() != null ? " / " : "";
        final String usdPrefix = carPost.getCarInfo().getPrice().getUsd() != null ? " USD" : "";



        Intent intent = new Intent(getApplicationContext(), CarPostActivity.class);
        intent.putExtra("id", carPost.getId());
        intent.putExtra("title", carPost.getTitle());
        intent.putExtra("description", carPost.getDescription());
        intent.putExtra("imageUrl", "http://82.146.40.7/" + carPost.getPreviewImage());
        intent.putExtra("mileageName", mileageName);
        intent.putExtra("mileage", carPost.getCarInfo().getMileage().toString());
        intent.putExtra("year", carPost.getCarInfo().getYear().toString());
        intent.putExtra("engineType", engineType);
        intent.putExtra("engineCapacity", carPost.getCarInfo().getEngine().getEngineCapacity().toString());
        intent.putExtra("engineCapacityHint", carPost.getCarInfo().getEngine().getEngineCapacityHint());
        intent.putExtra("transmission", transmission);
        intent.putExtra("currencyPrefix", currencyPrefix);
        intent.putExtra("usdPrice", usdPrice);
        intent.putExtra("usdPrefix", usdPrefix);
        startActivity(intent);
    }
}
