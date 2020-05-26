package com.example.avto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avto.Network.RetrofitClientInstance;
import com.example.avto.R;
import com.example.avto.Service.GetDataService;
import com.example.avto.Service.Model.ResultResponse;
import com.example.avto.Service.Model.SubscriptionResponse;
import com.example.avto.SubscriptionActivity;
import com.google.android.material.button.MaterialButton;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.CustomViewHolder> {
    private List<SubscriptionResponse> dataList;
    private Context context;

    private GetDataService service;

    public SubscriptionsAdapter(Context context,List<SubscriptionResponse> dataList){
        service = RetrofitClientInstance.getRetrofitIntance().create(GetDataService.class);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SubscriptionsAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subscriptions_row, parent, false);
        return new SubscriptionsAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionsAdapter.CustomViewHolder holder, final int position) {
        String model = dataList.get(position).getModel() != null ? dataList.get(position).getModel().getName() : "";
        String generation = dataList.get(position).getGeneration() != null ? dataList.get(position).getGeneration().getName() : "";

        holder.mark.setText(dataList.get(position).getMark().getName());
        holder.model.setText(model);
        holder.generation.setText(generation);

//        holder.remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Call<ResultResponse> call = service.removeSubscription(dataList.get(position).getId());
//                call.enqueue(new Callback<ResultResponse>() {
//                    @Override
//                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
//                        SubscriptionActivity activity = (SubscriptionActivity) context;
//                        activity.enableDialog();
////                        activity.loadSubscriptions();
//                        activity.createToast("Подписка успешно удалена");
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResultResponse> call, Throwable t) {
//                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView mark, model, generation;
        MaterialButton edit, remove;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mark = mView.findViewById(R.id.mark);
            model = mView.findViewById(R.id.model);
            generation = mView.findViewById(R.id.generation);
        }
    }

}
