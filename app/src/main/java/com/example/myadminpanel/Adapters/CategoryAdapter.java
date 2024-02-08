package com.example.myadminpanel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadminpanel.Models.CategoryModel;
import com.example.myadminpanel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        // category_item connectivity here...
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        // here we will bind it...
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.categoryname.setText(categoryModel.getCategoryname());

        String imageUri=null;
        imageUri=categoryModel.getCategoryimg();
        Picasso.get().load(imageUri).into(holder.categoryimg);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // here declare design
        CircleImageView categoryimg;
        TextView categoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryimg=itemView.findViewById(R.id.categoryimg);
            categoryname=itemView.findViewById(R.id.categoryname);

        }
    }
}
