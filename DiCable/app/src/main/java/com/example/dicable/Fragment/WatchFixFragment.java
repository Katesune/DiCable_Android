package com.example.dicable.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicable.Answer;
import com.example.dicable.R;

public class WatchFixFragment extends FunkFragment {
    Answer a;

    public WatchFixFragment(Answer answer) {
        super(R.layout.fragment_watch_fix, answer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(layout, container, false);
        return myView;
    }
}
