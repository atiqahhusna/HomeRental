package com.example.homerental.landlords;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.homerental.R;
import com.example.homerental.landlords.fragment.EditListHouseFragment;
import com.example.homerental.landlords.fragment.ProfileLandlordFragment;

import java.util.ArrayList;

public class ListHouseAdapterLandlords extends RecyclerView.Adapter<ListHouseAdapterLandlords.MyViewHolder>{

    ArrayList<ListHouseLandlords> listHousesLandlords;
    Context context;
   // ItemSelectedListener itemSelectedListener;

    public ListHouseAdapterLandlords(Context context, ArrayList<ListHouseLandlords> listHousesLandlords) {
        this.context = context;
        this.listHousesLandlords = listHousesLandlords;
       // this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public ListHouseAdapterLandlords.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_house_landlords, parent, false);

        return new ListHouseAdapterLandlords.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.tvPrice.setText(listHousesLandlords.get(position).getPrice());
        holder.tvAddress.setText(listHousesLandlords.get(position).getAddress());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemSelectedListener.onItemSelectedListener(listHousesLandlords.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        // the recycler view just want to know the number of items you want displayed
        return listHousesLandlords.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // grabbing the views from our recycler_view_row layout file
        // Like in the onCreate method

        ImageView imageView;
        TextView tvPrice, tvAddress;// textView;
        Button EditBtn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            tvPrice = itemView.findViewById(R.id.HPrice);
            tvAddress = itemView.findViewById(R.id.HAddress);
            EditBtn = itemView.findViewById(R.id.editBtn);
            cardView = itemView.findViewById(R.id.cardView2);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
