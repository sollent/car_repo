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
import com.example.avto.Network.ApiBaseUrl;
import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.Network.TokenService;
import com.example.avto.Service.AuthService;
import com.example.avto.Service.GetDataService;
import com.example.avto.Service.Model.SimpleFilterModel;
import com.example.avto.Service.Model.UserModel;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pusher.pushnotifications.PushNotifications;


public class MainActivity extends AppCompatActivity implements MainOnClick {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    GetDataService service;
    AuthService authService;

    ChipNavigationBar menu;

    FloatingActionButton updateBtn, filterButton, subscriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = RetrofitClientInstance.getRetrofitIntance().create(GetDataService.class);
        authService = RetrofitClientInstance.getRetrofitIntance().create(AuthService.class);

        PushNotifications.start(getApplicationContext(), "58f8860e-65e3-4e17-9bd8-6419b9ae97f4");
        initialDeviceInterest();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Загрузка....");
        progressDialog.show();

        menu = findViewById(R.id.menu);
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:

                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.subscription:
                        if (Strings.isEmptyOrWhitespace(TokenService.getToken(getApplicationContext()))) {
                            Toast.makeText(MainActivity.this, "Необходимо авторизоваться", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Intent intent1 = new Intent(getApplicationContext(), SubscriptionActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });

        updateBtn = findViewById(R.id.updateBtn);
        filterButton = findViewById(R.id.filterButton);
//        subscriptionButton = findViewById(R.id.subscriptionButton);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                loadCarPosts();
            }
        });


//        subscriptionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SubscriptionActivity.class);
//                startActivity(intent);
//            }
//        });

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

    private void initialDeviceInterest() {
        Call<UserModel> call = authService.getUser("Bearer " + TokenService.getToken(getApplicationContext()));
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    PushNotifications.addDeviceInterest(response.body().getUsername().toString());
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка с Pusher", Toast.LENGTH_SHORT).show();
            }
        });
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
        intent.putExtra("imageUrl", ApiBaseUrl.BASE_URL + carPost.getPreviewImage());
//        intent.putExtra("imageUrl", "http://10.0.2.2:8000/" + carPost.getPreviewImage());
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

        String[] images = new String[1];
        boolean existImage = true;

        if (carPost.getImages() != null) {
            images = new String[carPost.getImages().length + 1];
            images[0] = carPost.getPreviewImage();

            for (int i = 1; i < images.length; i++) {
                images[i] = carPost.getImages()[i - 1];
            }
        } else if (carPost.getPreviewImage() != null){
            images[0] = carPost.getPreviewImage();
        } else {
            existImage = false;
        }

        if (existImage) {
            intent.putExtra("images", images);
        }

        startActivity(intent);
    }
}
