package com.example.homerental.landlords;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homerental.R;
import com.example.homerental.tenants.ListHouseAdapterTenants;
import com.example.homerental.tenants.ListHouseTenants;

import java.util.ArrayList;

public class ListTenantsAdapter extends RecyclerView.Adapter<ListTenantsAdapter.MyViewHolder> {

    ArrayList<ListTenants> listTenants;
    Context context;
    private ItemClickListener mItemListener;

    public ListTenantsAdapter(Context context, ArrayList<ListTenants> listTenants, ListTenantsAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.listTenants = listTenants;
        this.mItemListener = itemClickListener;
    }

    public ListTenantsAdapter(Context context, ArrayList<ListTenants> listTenants) {
        this.context = context;
        this.listTenants = listTenants;

    }

    @NonNull
    @Override
    public ListTenantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_list_tenants, parent, false);

        return new ListTenantsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTenantsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText((listTenants.get(position).getStatus()));
        holder.tvStatus.setText(listTenants.get(position).getStatus());
        holder.cardViewTenantsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(listTenants.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        // the recycler view just want to know the number of items you want displayed
        return listTenants.size();
    }

    public interface ItemClickListener{
        void onItemClick (ListTenants listTenants);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // grabbing the views from our recycler_view_row layout file
        // like in the onCreate method

        TextView tvName, tvStatus;
        CardView cardViewTenantsUser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.imageHouse);
            //tvPropertyName = itemView.findViewById(R.id.propertyName);
            tvName = itemView.findViewById(R.id.nameTenants);
            tvStatus = itemView.findViewById(R.id.statusTenants);
            cardViewTenantsUser = itemView.findViewById(R.id.cardView2);
        }

        @Override
        public void onClick(View v) {
        }
    }
}

