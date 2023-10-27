package com.example.apidata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle=findViewById(R.id.recycle);

        listingdata();
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);
    }

    private void listingdata() {
        ApiInterface apiInterface=Retrofit.getRetrofit().create(ApiInterface.class);
        Call<Pojo> listingdata=apiInterface.getData();
        listingdata.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
               if(response.isSuccessful())
               {
                RecycleAdapter adapter=new RecycleAdapter(response.body().getData());
                recycle.setAdapter(adapter);
               }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>
    {
        List<Pojo.Datum> list;
        public RecycleAdapter(List<Pojo.Datum> list){
            this.list = list;
        }

        @NonNull
        @Override
        public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
            RecycleAdapter.MyViewHolder viewHolder = new RecycleAdapter.MyViewHolder(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {

        holder.email.setText(list.get(position).getEmail());
        holder.lstname.setText(list.get(position).getLastName());
        holder.fstname.setText(list.get(position).getFirstName());

            Picasso.with(getApplicationContext())
                    .load(list.get(position).getAvatar())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.imgs);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView fstname,userid,lstname,email;
            ImageView imgs;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                fstname=itemView.findViewById(R.id.fstname);
                userid=itemView.findViewById(R.id.userid);
                lstname=itemView.findViewById(R.id.lstname);
                email=itemView.findViewById(R.id.email);
                imgs=itemView.findViewById(R.id.imageView);
            }
        }
    }
}