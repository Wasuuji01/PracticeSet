package com.example.myadminpanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddProduct extends AppCompatActivity {

    // here upload data in firebase database..
    FirebaseDatabase mDatabase;
    DatabaseReference Ref;
    FirebaseStorage mStorage;
    ImageView coverphoto;
    EditText productname,productdesp,productprice,productSprice;
    AppCompatButton UploadPdBtn;
    private static final int Gellery_code=1;
    Uri ImageUrl=null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // for uploading data
        coverphoto = findViewById(R.id.coverphoto);
        productname = findViewById(R.id.productname);
        productdesp = findViewById(R.id.productdescription);
        productprice = findViewById(R.id.productprice);
        productSprice = findViewById(R.id.sellingpprice);
        UploadPdBtn = findViewById(R.id.UploadPdBtn);

        mDatabase = FirebaseDatabase.getInstance();
        Ref = mDatabase.getReference().child("Products");
        mStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        coverphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gellery = new Intent(Intent.ACTION_GET_CONTENT);
                gellery.setType("image/*");
                startActivityForResult(gellery,Gellery_code);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gellery_code && resultCode==RESULT_OK){
            ImageUrl=data.getData();
            coverphoto.setImageURI(ImageUrl);

        }

        UploadPdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=productname.getText().toString().trim();
                String desp=productdesp.getText().toString().trim();
                long price = Long.parseLong(productprice.getText().toString().trim());
                long Sprice = Long.parseLong(productSprice.getText().toString().trim());

                if (!(name.isEmpty() && desp.isEmpty() && ImageUrl!=null)){

                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference filepath = mStorage.getReference().child("Product Images").child(Objects.requireNonNull(ImageUrl.getLastPathSegment()));
                    filepath.putFile(ImageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    String t = task.getResult().toString();

                                    DatabaseReference newProduct = Ref.push();

                                    newProduct.child("Productname").setValue(name);
                                    newProduct.child("Productdesp").setValue(desp);
                                    newProduct.child("Productprice").setValue(price);
                                    newProduct.child("ProductSprice").setValue(Sprice);
                                    newProduct.child("Productimg").setValue(task.getResult().toString());
                                    progressDialog.dismiss();

                                    Intent next = new Intent(AddProduct.this,DeleteProduct.class);
                                    startActivity(next);


                                }
                            });
                        }
                    });

                }

            }
        });

    }
}