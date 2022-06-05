package com.example.dicable.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dicable.Answer;
import com.example.dicable.R;

public class FunkFragment extends Fragment {
    Answer a;
    int layout;

    public FunkFragment(int fragment, Answer answer) {
        super(fragment);
        a = answer;
        layout = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(layout, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView ip = view.findViewById(R.id.ip);
        TextView port = view.findViewById(R.id.port);
        TextView date = view.findViewById(R.id.date);

        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        RelativeLayout port_state_view = view.findViewById(R.id.state_port_container);
        RelativeLayout diag_view = view.findViewById(R.id.diag_container);
        RelativeLayout pairs_view = view.findViewById(R.id.pairs_container);

        if ((a.port_state.errorIndication != null) && (!a.port_state.errorIndication.equals("None"))) {
            transaction.add(R.id.state_errors_container,  new ErrorIndicationFragment(a.port_state.errorIndication));
            port_state_view.setVisibility(View.GONE);
        } else if ((a.port_state.errorStatus != null) && (!a.port_state.errorStatus.equals("None"))) {
            transaction.add(R.id.state_errors_container,  new ErrorStatusFragment(a.port_state.errorStatus,a.port_state.errorIndex));
            port_state_view.setVisibility(View.GONE);
        } else {
            TextView state_port = view.findViewById(R.id.state_port_val);
            TextView port_speed = view.findViewById(R.id.port_speed_val);
            TextView enabled_port = view.findViewById(R.id.enabled_port_val);

            state_port.setText(a.port_state.port_state_values.link_state);
            port_speed.setText(a.port_state.port_state_values.port_speed);
            enabled_port.setText(a.port_state.port_state_values.enabled_port);
        }

        if ((a.cable_diag_action.errorIndication!=null) && (!a.cable_diag_action.errorIndication.equals("None"))) {
            transaction.add(R.id.diag_errors_container,  new ErrorIndicationFragment(a.cable_diag_action.errorIndication));
            diag_view.setVisibility(View.GONE);
        } else if ((a.cable_diag_action.errorStatus!=null) && (!a.cable_diag_action.errorStatus.equals("None"))){
            transaction.add(R.id.diag_errors_container,  new ErrorStatusFragment(a.cable_diag_action.errorStatus,a.cable_diag_action.errorIndex));
            diag_view.setVisibility(View.GONE);
        } else {
            TextView diag_action = view.findViewById(R.id.diag_action_val);
            diag_action.setText(a.cable_diag_action.diag_action_values.port_diag_action);
        }

        if ((a.pairs_state.errorIndication!=null) && (!a.pairs_state.errorIndication.equals("None"))) {
            transaction.add(R.id.pairs_errors_container,  new ErrorIndicationFragment(a.pairs_state.errorIndication));
            pairs_view.setVisibility(View.GONE);
        } else if ((a.pairs_state.errorStatus!=null) && (!a.pairs_state.errorStatus.equals("None"))) {
            transaction.add(R.id.pairs_errors_container,  new ErrorStatusFragment(a.pairs_state.errorStatus,a.pairs_state.errorIndex));
            pairs_view.setVisibility(View.GONE);
        } else {
            TextView pair1_length = view.findViewById(R.id.pair1_length_val);
            TextView pair1_status = view.findViewById(R.id.pair1_status_val);
            TextView pair2_length = view.findViewById(R.id.pair2_length_val);
            TextView pair2_status = view.findViewById(R.id.pair2_status_val);

            pair1_status.setText(a.pairs_state.pairs_state_values.pair1_status);
            pair1_length.setText(a.pairs_state.pairs_state_values.pair1_length);
            pair2_status.setText(a.pairs_state.pairs_state_values.pair2_status);
            pair2_length.setText(a.pairs_state.pairs_state_values.pair2_length);
        }

        transaction.commitAllowingStateLoss();

        ip.setText(a.general.ip);
        port.setText(Integer.toString(a.general.port));
        date.setText(a.general.date);

        ImageButton butt_fix = view.findViewById(R.id.fixed_button);
        butt_fix.setId(a.general.id);
        ImageButton butt_watch = view.findViewById(R.id.watch_button);
        butt_watch.setId(a.general.id);

        ImageButton butt_expand = view.findViewById(R.id.expand_button);
        butt_expand.setId(a.general.id);
        RelativeLayout data = view.findViewById(R.id.visible);

        butt_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getVisibility()==View.VISIBLE) {
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                            (item_view.getWidth(), item_view.getHeight() - 1122);
//                    item_view.setLayoutParams(layoutParams);

                    data.setVisibility(View.GONE);
                } else {
                    data.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
