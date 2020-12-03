package com.example.mentalhealth.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentalhealth.R;
import com.example.mentalhealth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgretFragment extends Fragment {
        private TextView average,worst,best;
        FirebaseUser user;
        User cUser;
        DatabaseReference myRef;


    public ProgretFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progret, container, false);
        average = view.findViewById(R.id.textView4);
        worst = view.findViewById(R.id.textView6);
        best = view.findViewById(R.id.textView8);

        getCurrentUser();
        return view;
    }
    private void getCurrentUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cUser = snapshot.getValue(User.class);
                average.setText(String.valueOf(getAverage(cUser.getProgres()))+"/5");
                worst.setText(String.valueOf(getMin(cUser.getProgres()))+"/5");
                best.setText(String.valueOf(getMax(cUser.getProgres()))+"/5");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private int getMin(ArrayList<Integer> list){
        int min = list.get(0);
        for(int i = 1; i<list.size();i++){
            if(min>list.get(i)){
                min = list.get(i);
            }
        }
        return min;
    }
    private int getMax(ArrayList<Integer> list){
        int max = list.get(0);
        for(int i = 1; i<list.size();i++){
            if(max<list.get(i)){
                max = list.get(i);
            }
        }
        return max;
    }
    private int getAverage(ArrayList<Integer> list){
        int average = 0;
        for(int i = 0; i<list.size();i++){

            average += list.get(i);

        }
        average = (int)(average/list.size());
        return average;
    }
}