package com.example.myadminpanel;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadminpanel.Adapters.CategoryAdapter;
import com.example.myadminpanel.Models.CategoryImageModel;
import com.example.myadminpanel.Models.CategoryModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {

    // here retrieve data from firebase database.........
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorasge;
    DatabaseReference mRef;
    RecyclerView recyclerView;
    List<CategoryModel> categoryModelList;
    CategoryAdapter categoryAdapter;
    // here upload data in firebase realtime database........
    StorageReference reference=FirebaseStorage.getInstance().getReference("Category Image");
    DatabaseReference root=FirebaseDatabase.getInstance().getReference("Categories");
    Uri imageUri;
    AppCompatButton uploadcategory;
    ImageView categoryimage;
    EditText cagtegoryname;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        //  Retrieve data from database in recycleview....

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Categories");
        mStorasge=FirebaseStorage.getInstance();
        recyclerView=findViewById(R.id.categorylist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryModelList=new ArrayList<CategoryModel>();
        categoryAdapter=new CategoryAdapter(AddCategory.this,categoryModelList);
        recyclerView.setAdapter(categoryAdapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              CategoryModel categoryModel = snapshot.getValue(CategoryModel.class);
                categoryModelList.add(categoryModel);
                categoryAdapter.notifyDataSetChanged();
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



        categoryimage= findViewById(R.id.categoryimg);
        cagtegoryname = findViewById(R.id.categoryname);
        uploadcategory = findViewById(R.id.categorybtn);
        progressBar = findViewById(R.id.progressbar);


        categoryimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);

            }

        });

        uploadcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri!=null){
                    uploadToFirebase(imageUri);
                    uploadcategory.setEnabled(false);
                }else {
                    Toast.makeText(AddCategory.this, "Please select Image....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cagtegoryname.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String name = cagtegoryname.getText().toString().trim();
            uploadcategory.setEnabled(!name.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void uploadToFirebase(Uri uri) {

        final StorageReference fileRef=reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CategoryImageModel categoryImageModel = new CategoryImageModel(uri.toString(),cagtegoryname.getText().toString());
                        String modelId=root.push().getKey();
                        root.child(modelId).setValue(categoryImageModel);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddCategory.this, "Upload Successfully...", Toast.LENGTH_SHORT).show();
                        cagtegoryname.getText().clear();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2 && resultCode==RESULT_OK && data != null);
        imageUri= data != null ? data.getData() : null;
        categoryimage.setImageURI(imageUri);
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }

}