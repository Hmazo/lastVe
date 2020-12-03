package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VisitProfil extends AppCompatActivity {
    private TextView profilName, posteCount, abonneCount, abonnementCount;
    private User currentUser;
    private ImageView profilImage;
    private Button message;

    DatabaseReference myRef;
    FirebaseAuth auth;
    private RecyclerView posts;
    private ArrayList<ModelPost> postList;
    Adapter_Post adapter_post;
    String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profil);



        profilName = findViewById(R.id.ProfilFullName);
        posteCount = findViewById(R.id.PorfilPosteCount);
        abonneCount = findViewById(R.id.PorfilAbonneCount);
        abonnementCount = findViewById(R.id.PorfilAbonnementsCount);
        profilImage = findViewById(R.id.ProfilPicture1);
        message = findViewById(R.id.MessageButton);
        posts = findViewById(R.id.visitprofilRecycler);
        postList = new ArrayList<>();

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        //show newest post first, for this laod from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recycleview
        posts.setLayoutManager(layoutManager);

        Intent intent = getIntent();
       userID = intent.getStringExtra("userID");

        profilImage.setImageResource(R.drawable.account_icon);
        myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userID);

        loadPosts();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);


                profilName.setText(currentUser.getName());
                posteCount.setText(String.valueOf(currentUser.getPubNumber()));
                abonneCount.setText(String.valueOf(currentUser.getAbonne()));
                abonnementCount.setText(String.valueOf(currentUser.getAbonnements()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisitProfil.this, ChatRoom.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });


    }
    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all data from database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    ModelPost modelPost= ds.getValue(ModelPost.class);
                    if(modelPost.getUid().equals(userID)) {
                        postList.add(modelPost);
                    }
                    //adapter
                    adapter_post = new Adapter_Post(VisitProfil.this, postList);
                    // set adapter to recycleview
                    posts.setAdapter(adapter_post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VisitProfil.this, "erreor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}