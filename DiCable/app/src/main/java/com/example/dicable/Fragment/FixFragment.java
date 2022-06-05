package com.example.dicable.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicable.Answer;
import com.example.dicable.R;

public class FixFragment extends FunkFragment {
    Answer a;

    public FixFragment(Answer answer) {
        super(R.layout.fragment_fixed, answer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(layout, container, false);
        return myView;
    }
}

