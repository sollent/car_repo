package com.example.avto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.avto.Adapter.CarGenerationSpinnerAdapter;
import com.example.avto.Adapter.CarMarkSpinnerAdapter;
import com.example.avto.Adapter.CarModelSpinnerAdapter;
import com.example.avto.Model.CarModels.CarGeneration;
import com.example.avto.Model.CarModels.CarMark;
import com.example.avto.Model.CarModels.CarModel;
import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.Network.TokenService;
import com.example.avto.Service.GetDataService;
import com.example.avto.Service.Model.Subscription;
import com.example.avto.Service.Model.SubscriptionResponse;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSubscriptionActivity extends AppCompatActivity {

    GetDataService service;

    Subscription subscription = new Subscription();

    MaterialSpinner spinnerMark, spinnerModel, spinnerGeneration;

    Button createButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subscription);

        service = RetrofitClientInstance.getRetrofitIntance().create(GetDataService.class);

        spinnerMark = (MaterialSpinner) findViewById(R.id.spinner_mark);
        spinnerModel = (MaterialSpinner) findViewById(R.id.spinner_model);
        spinnerGeneration = (MaterialSpinner) findViewById(R.id.spinner_generations);

        createButton = (Button) findViewById(R.id.create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SubscriptionResponse> call = service.createSubscription("Bearer " + TokenService.getToken(getApplicationContext()), subscription);
                call.enqueue(new Callback<SubscriptionResponse>() {
                    @Override
                    public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                        Toast.makeText(CreateSubscriptionActivity.this, "Подписка успешно создана", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), SubscriptionActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                        Toast.makeText(CreateSubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        prepareFilters();
    }

    private void prepareFilters() {
        Call<List<CarMark>> call = service.getAllCarMarks();
        call.enqueue(new Callback<List<CarMark>>() {
            @Override
            public void onResponse(Call<List<CarMark>> call, Response<List<CarMark>> response) {
                if (response.body() != null) {
                    handleMarks(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CarMark>> call, Throwable t) {
                Toast.makeText(CreateSubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleMarks(List<CarMark> marks)
    {
        CarMark[] carMarks = new CarMark[marks.size()];

        for (int i = 0; i < marks.size(); i++) {
            carMarks[i] = marks.get(i);
        }

        CarMarkSpinnerAdapter adapter = new CarMarkSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, carMarks);
        spinnerMark.setAdapter(adapter);

        spinnerMark.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<CarMark>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, CarMark item) {
                Snackbar.make(view, "Clicked " + item.getName(), Snackbar.LENGTH_LONG).show();

                subscription.setMark(item.getId());

                clearChildSpinners();

                Call<List<CarModel>> call = service.getAllCarModels(item.getId());
                call.enqueue(new Callback<List<CarModel>>() {
                    @Override
                    public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                        if (response.body() != null) {
                            handleModels(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CarModel>> call, Throwable t) {
                        Toast.makeText(CreateSubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void handleModels(List<CarModel> models)
    {
        CarModel[] carModels = new CarModel[models.size()];

        for (int i = 0; i < models.size(); i++) {
            carModels[i] = models.get(i);
        }

        CarModelSpinnerAdapter adapter = new CarModelSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, carModels);
        spinnerModel.setAdapter(adapter);

        spinnerModel.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<CarModel>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, CarModel item) {
                Snackbar.make(view, "Clicked " + item.getName(), Snackbar.LENGTH_LONG).show();

                subscription.setModel(item.getId());

                Call<List<CarGeneration>> call = service.getAllCarGenerations(item.getId());
                call.enqueue(new Callback<List<CarGeneration>>() {
                    @Override
                    public void onResponse(Call<List<CarGeneration>> call, Response<List<CarGeneration>> response) {
                        if (response.body() != null) {
                            handleGenerations(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CarGeneration>> call, Throwable t) {
                        Toast.makeText(CreateSubscriptionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void handleGenerations(List<CarGeneration> generations)
    {
        CarGeneration[] carGenerations = new CarGeneration[generations.size()];

        for (int i = 0; i < generations.size(); i++) {
            carGenerations[i] = generations.get(i);
        }

        CarGenerationSpinnerAdapter adapter = new CarGenerationSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, carGenerations);
        spinnerGeneration.setAdapter(adapter);

        spinnerGeneration.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<CarGeneration>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, CarGeneration item) {
                Snackbar.make(view, "Clicked " + item.getName(), Snackbar.LENGTH_LONG).show();

                subscription.setGeneration(item.getId());
            }
        });
    }

    private void clearChildSpinners()
    {
        spinnerModel.setAdapter(new CarModelSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, new CarModel[0]));
        spinnerGeneration.setAdapter(new CarGenerationSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, new CarGeneration[0]));
    }
}
