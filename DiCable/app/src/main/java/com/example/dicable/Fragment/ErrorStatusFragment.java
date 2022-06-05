package com.example.dicable.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dicable.R;

public class ErrorStatusFragment extends Fragment {
    String errSt;
    String errIn;

    public ErrorStatusFragment(String errorStatus, String errorIndex) {
        super(R.layout.error_status);
        errSt = errorStatus;
        errIn = errorIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.error_status, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView view_err_status = view.findViewById(R.id.errorStatus_val);
        TextView view_err_index = view.findViewById(R.id.errorIndex_val);
        view_err_status.setText(errSt);
        view_err_index.setText(errIn);
    }
}
