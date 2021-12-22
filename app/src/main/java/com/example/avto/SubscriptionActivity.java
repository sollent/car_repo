package com.example.avto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.avto.Adapter.SubscriptionsAdapter;
import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.Network.TokenService;
import com.example.avto.Service.GetDataService;
import com.example.avto.Service.Model.SubscriptionResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends AppCompatActivity {

    FloatingActionButton createSubscriptionButton;

    private RecyclerView recyclerView;
    private SubscriptionsAdapter adapter;
    ProgressDialog progressDialog;



    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        service = RetrofitClientInstance.getRetrofitIntance().create(GetDataService.class);

        enableDialog();

        createSubscriptionButton = (FloatingActionButton) findViewById(R.id.createSubscription);

        createSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSubscriptionActivity.class);
                startActivity(intent);
            }
        });

        loadSubscriptions();
    }


    public void enableDialog() {
        progressDialog = new ProgressDialog(SubscriptionActivity.this);
        progressDialog.setMessage("Загрузка....");
        progressDialog.show();
    }

    public void createToast(String message) {
        Toast.makeText(SubscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void loadSubscriptions() {
        Call<List<SubscriptionResponse>> call = service.getSubscriptions("Bearer " + TokenService.getToken(getApplicationContext()));
        call.enqueue(new Callback<List<SubscriptionResponse>>() {
            @Override
            public void onResponse(Call<List<SubscriptionResponse>> call, Response<List<SubscriptionResponse>> response) {
                progressDialog.dismiss();
                generationSubscriptionsList(response.body());
            }

            @Override
            public void onFailure(Call<List<SubscriptionResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generationSubscriptionsList(List<SubscriptionResponse> subscriptions) {
        recyclerView = findViewById(R.id.subscriptions_view);
        adapter = new SubscriptionsAdapter(this, subscriptions);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }
}
