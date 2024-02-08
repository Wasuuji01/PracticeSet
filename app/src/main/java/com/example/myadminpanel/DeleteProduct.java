package com.example.myadminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadminpanel.Adapters.ProductAdapter;
import com.example.myadminpanel.Models.RetriveProductModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends AppCompatActivity {

    TextView addproductbtn;

    // here we will retrive data from firebase database...
    FirebaseDatabase rDatabase;
    DatabaseReference rRef;
    FirebaseStorage rStorage;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<RetriveProductModel> retriveProductModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        // for retriving data
        rDatabase = FirebaseDatabase.getInstance();
        rRef = rDatabase.getReference().child("Products");
        rStorage = FirebaseStorage.getInstance();
        recyclerView = findViewById(R.id.productlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retriveProductModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(DeleteProduct.this,retriveProductModelList);
        recyclerView.setAdapter(productAdapter);

        rRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RetriveProductModel retriveProductModel = snapshot.getValue(RetriveProductModel.class);
                retriveProductModelList.add(retriveProductModel);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addproductbtn = findViewById(R.id.clickBtn);

        addproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(DeleteProduct.this,AddProduct.class);
                startActivity(add);
            }
        });

    }
}