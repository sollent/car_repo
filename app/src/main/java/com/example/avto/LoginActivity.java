package com.example.avto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.Network.TokenService;
import com.example.avto.Service.AuthService;
import com.example.avto.Service.Model.LoginUser;
import com.example.avto.Service.Model.TokenModel;
import com.example.avto.Service.Model.UserModel;
import com.google.android.gms.common.util.Strings;
import com.pusher.pushnotifications.PushNotifications;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;

    AuthService service;
    AuthService authService;


    Button button, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        clearToken();

        service = RetrofitClientInstance.getRetrofitIntance().create(AuthService.class);
        authService = RetrofitClientInstance.getRetrofitIntance().create(AuthService.class);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        button = findViewById(R.id.login);

        token = findViewById(R.id.token);

        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, TokenService.getToken(getApplicationContext()), Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate(new LoginUser(username.getText().toString(), password.getText().toString()));
                if (!Strings.isEmptyOrWhitespace(username.getText().toString()) &&
                    !Strings.isEmptyOrWhitespace(password.getText().toString())) {
                    authenticate(new LoginUser(username.getText().toString(), password.getText().toString()));
                } else {
                    Toast.makeText(LoginActivity.this, "Введите username и пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void authenticate(LoginUser loginUser) {
        Call<TokenModel> call = service.login(loginUser);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.body() != null) {
                    Toast.makeText(LoginActivity.this, response.body().getToken().toString(), Toast.LENGTH_SHORT).show();
                    saveToken(response.body().getToken().toString());
                    PushNotifications.start(getApplicationContext(), "58f8860e-65e3-4e17-9bd8-6419b9ae97f4");
                    initialDeviceInterest();
                    Intent intent = new Intent(getApplicationContext(), SubscriptionActivity.class);
                    startActivity(intent);
                }
                if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Неправилный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialDeviceInterest() {
        Call<UserModel> call = authService.getUser("Bearer " + TokenService.getToken(getApplicationContext()));
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    PushNotifications.addDeviceInterest(response.body().getUsername().toString());
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ошибка с Pusher", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.commit();
    }

    private void clearToken() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("token");
        editor.commit();
    }
}
