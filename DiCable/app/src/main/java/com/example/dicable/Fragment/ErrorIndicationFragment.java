package com.example.dicable.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dicable.Answer;
import com.example.dicable.R;

public class ErrorIndicationFragment extends Fragment {
    String errInd;

    public ErrorIndicationFragment(String ErrorIndication) {
        super(R.layout.error_indication);
        errInd = ErrorIndication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.error_indication, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView view_err = view.findViewById(R.id.errorIndication_val);
        view_err.setText(errInd);
    }

}
