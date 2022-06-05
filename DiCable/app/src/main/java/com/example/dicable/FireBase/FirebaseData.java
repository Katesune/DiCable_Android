package com.example.dicable.FireBase;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseData {
    List<Integer> fixed = new ArrayList<>();
    List<Integer> watched = new ArrayList<>();


    public void collect(DataSnapshot snapshot) {
        for (DataSnapshot s : snapshot.child("fixed").getChildren()) {
            fixed.add(Integer.parseInt(s.getKey()));
        }
        for (DataSnapshot s : snapshot.child("watched").getChildren()) {
            watched.add(Integer.parseInt(s.getKey()));
        }
    }



}
