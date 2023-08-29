package com.example.homerental.tenants;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homerental.R;

import java.util.ArrayList;

public class ListHouseAdapterTenants extends RecyclerView.Adapter<ListHouseAdapterTenants.MyViewHolder>{

    ArrayList<ListHouseTenants> listHouses;
    Context context;
    //private ItemSelectedListener itemSelectedListener;
    private ItemClickListener mItemListener;

    public ListHouseAdapterTenants(Context context, ArrayList<ListHouseTenants> listHouses, ItemClickListener itemClickListener) {
        this.context = context;
        this.listHouses = listHouses;
        this.mItemListener = itemClickListener;
    }

   public ListHouseAdapterTenants(Context context, ArrayList<ListHouseTenants> listHouses ) {
        this.context = context;
        this.listHouses = listHouses;

    }

    @NonNull
    @Override
    public ListHouseAdapterTenants.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_house_tenants, parent, false);

        return new ListHouseAdapterTenants.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ListHouseAdapterTenants.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // assigning values to the views we created in the recycler_view_row layout file
        // based on the position of the recycler view
       // holder.tvPropertyName.setText(listHouses.get(position).getPropertyName());
        holder.tvPrice.setText(listHouses.get(position).getPrice());
        holder.tvAddress.setText(listHouses.get(position).getAddress());
        holder.cardViewTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(listHouses.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        // the recycler view just want to know the number of items you want displayed
        return listHouses.size();
    }

    public interface ItemClickListener{
        void onItemClick (ListHouseTenants listHouseTenants);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // grabbing the views from our recycler_view_row layout file
        // like in the onCreate method
        ImageView imageView;
        TextView tvPrice, tvAddress;
        CardView cardViewTenants;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageHouse);
            //tvPropertyName = itemView.findViewById(R.id.propertyName);
            tvPrice = itemView.findViewById(R.id.HPrice);
            tvAddress = itemView.findViewById(R.id.HAdress);
            cardViewTenants = itemView.findViewById(R.id.cardView2);
        }

        @Override
        public void onClick(View v) {
        }
    }
}

