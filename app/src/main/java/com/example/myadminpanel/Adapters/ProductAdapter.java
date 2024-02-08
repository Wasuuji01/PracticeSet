package com.example.myadminpanel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadminpanel.Models.RetriveProductModel;
import com.example.myadminpanel.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<RetriveProductModel> retriveProductModelList;

    public ProductAdapter(Context context, List<RetriveProductModel> retriveProductModelList) {
        this.context = context;
        this.retriveProductModelList = retriveProductModelList;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        // design product card connectivity....
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        RetriveProductModel retriveProductModel = retriveProductModelList.get(position);
        holder.productname.setText("" + retriveProductModel.getProductname());
        holder.productdesp.setText("" + retriveProductModel.getProductdesp());
        holder.price.setText("Rs. " + retriveProductModel.getProductprice());
        holder.sellingprice.setText("Rs. " + retriveProductModel.getProductSprice());

    }

    @Override
    public int getItemCount() {
        return retriveProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // here declare design..
        TextView productname,productdesp,price,sellingprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productname);
            productdesp = itemView.findViewById(R.id.productdescription);
            price = itemView.findViewById(R.id.price);
            sellingprice = itemView.findViewById(R.id.sellingprice);
        }
    }
}
