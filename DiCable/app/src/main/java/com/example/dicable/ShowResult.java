package com.example.dicable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ShowResult {
    LayoutInflater ltInflater;
    ConstraintLayout cl;

    public ShowResult(LayoutInflater ltInflater, ConstraintLayout cl) {
        this.ltInflater = ltInflater;
        this.cl = cl;
    }

    public View show(Answer answer, Boolean w, Boolean f) {
        View switch_item;

        if (w && f) {
            switch_item = ltInflater.inflate(R.layout.fix_and_watch_switch_item, cl, false);
        } else if (w) {
            switch_item = ltInflater.inflate(R.layout.watch_switch_item, cl, false);
        } else if (f) {
            switch_item = ltInflater.inflate(R.layout.fix_switch_item, cl, false);
        } else switch_item = ltInflater.inflate(R.layout.basic_switch_item, cl, false);

        TextView ip = switch_item.findViewById(R.id.ip);
        TextView port = switch_item.findViewById(R.id.port);
        TextView date = switch_item.findViewById(R.id.date);

        RelativeLayout state_port_container = switch_item.findViewById(R.id.state_port_container);
        RelativeLayout diag_container = switch_item.findViewById(R.id.diag_action_container);
        RelativeLayout pairs_container = switch_item.findViewById(R.id.pairs_container);

        View errorsIndication = ltInflater.inflate(R.layout.error_indication, cl, false);
        View errorsStatus = ltInflater.inflate(R.layout.error_status, cl, false);

        TextView error_indication = errorsIndication.findViewById(R.id.errorIndication_val);
        TextView error_status = errorsStatus.findViewById(R.id.errorStatus_val);
        TextView error_index = errorsStatus.findViewById(R.id.errorIndex_val);

        RelativeLayout state_errors_container = switch_item.findViewById(R.id.state_errors_container);
        RelativeLayout diag_errors_container = switch_item.findViewById(R.id.diag_errors_container);
        RelativeLayout pairs_errors_container = switch_item.findViewById(R.id.pairs_errors_container);

        if (answer.port_state.errorIndication != null) {
            error_indication.setText(answer.port_state.errorIndication);
            state_errors_container.addView(errorsIndication);
            state_port_container.setVisibility(View.INVISIBLE);

        } else if (answer.port_state.errorStatus != null){
            error_status.setText(answer.port_state.errorStatus);
            error_index.setText(answer.port_state.errorIndex);
            state_errors_container.addView(errorsStatus);
            state_port_container.setVisibility(View.INVISIBLE);

        } else {
            TextView state_port = switch_item.findViewById(R.id.state_port_val);
            TextView port_speed = switch_item.findViewById(R.id.port_speed_val);
            TextView enabled_port = switch_item.findViewById(R.id.enabled_port_val);

            state_port.setText(answer.port_state.port_state_values.link_state);
            port_speed.setText(answer.port_state.port_state_values.port_speed);
            enabled_port.setText(answer.port_state.port_state_values.enabled_port);
        }



        if (answer.cable_diag_action.errorIndication != null) {
            error_indication.setText(answer.cable_diag_action.errorIndication);
            diag_errors_container.addView(errorsIndication);
            diag_container.setVisibility(View.INVISIBLE);

        } else if (answer.cable_diag_action.errorStatus != null){
            error_status.setText(answer.cable_diag_action.errorStatus);
            error_index.setText(answer.cable_diag_action.errorIndex);
            diag_errors_container.addView(errorsStatus);
            diag_container.setVisibility(View.INVISIBLE);

        } else {
            TextView diag_action = switch_item.findViewById(R.id.diag_action_val);
            diag_action.setText(answer.cable_diag_action.diag_action_values.port_diag_action);
        }



        if (answer.pairs_state.errorIndication != null) {
            error_indication.setText(answer.pairs_state.errorIndication);
            pairs_errors_container.addView(errorsIndication);
            pairs_container.setVisibility(View.INVISIBLE);

        } else if (answer.pairs_state.errorStatus != null){
            error_status.setText(answer.pairs_state.errorStatus);
            error_index.setText(answer.pairs_state.errorIndex);
            pairs_errors_container.addView(errorsStatus);
            pairs_container.setVisibility(View.INVISIBLE);

        } else {
            TextView pair1_length = switch_item.findViewById(R.id.pair1_length_val);
            TextView pair1_status = switch_item.findViewById(R.id.pair1_status_val);
            TextView pair2_length = switch_item.findViewById(R.id.pair2_length_val);
            TextView pair2_status = switch_item.findViewById(R.id.pair2_status_val);

            pair1_status.setText(answer.pairs_state.pairs_state_values.pair1_status);
            pair1_length.setText(answer.pairs_state.pairs_state_values.pair1_length);
            pair2_status.setText(answer.pairs_state.pairs_state_values.pair2_status);
            pair2_length.setText(answer.pairs_state.pairs_state_values.pair2_length);
        }


//        if (answer.port_state.errorIndication != null) {
//            switch_item.findViewById(R.id.state_errorIndication_container).setVisibility(View.VISIBLE);
//            state_port_container.setVisibility(View.INVISIBLE);
//
//            TextView errorIndication = switch_item.findViewById(R.id.state_errorIndication_val);
//            errorIndication.setText(answer.port_state.errorIndication);
//        } else if (answer.port_state.errorStatus != null) {
//            switch_item.findViewById(R.id.state_errorStatus_container).setVisibility(View.VISIBLE);
//            state_port_container.setVisibility(View.INVISIBLE);
//
//            TextView errorStatus = switch_item.findViewById(R.id.state_errorStatus_val);
//            errorStatus.setText(answer.port_state.errorStatus);
//            TextView errorIndex = switch_item.findViewById(R.id.state_errorIndex_val);
//            errorIndex.setText(answer.port_state.errorIndex);
//        } else {
//            TextView state_port = switch_item.findViewById(R.id.state_port_val);
//            TextView port_speed = switch_item.findViewById(R.id.port_speed_val);
//            TextView enabled_port = switch_item.findViewById(R.id.enabled_port_val);
//
//            state_port.setText(answer.port_state.port_state_values.link_state);
//            port_speed.setText(answer.port_state.port_state_values.port_speed);
//            enabled_port.setText(answer.port_state.port_state_values.enabled_port);
//        }



//        RelativeLayout diag_action_container = switch_item.findViewById(R.id.diag_action_container);
//
//        TextView diag_action = switch_item.findViewById(R.id.diag_action_val);
//
//        RelativeLayout pairs_container = switch_item.findViewById(R.id.pairs_container);
//
//        TextView pair1_length = switch_item.findViewById(R.id.pair1_length_val);
//        TextView pair1_status = switch_item.findViewById(R.id.pair1_status_val);
//        TextView pair2_length = switch_item.findViewById(R.id.pair2_length_val);
//        TextView pair2_status = switch_item.findViewById(R.id.pair2_status_val);

        ImageButton butt_fix = switch_item.findViewById(R.id.fixed_button);
        butt_fix.setId(answer.general.id);
        ImageButton butt_watch = switch_item.findViewById(R.id.watch_button);
        butt_watch.setId(answer.general.id);
        ImageButton butt_expand = switch_item.findViewById(R.id.expand_button);
        butt_expand.setId(answer.general.id);

        ip.setText(answer.general.ip);
        port.setText(Integer.toString(answer.general.port));
        date.setText(answer.general.date);

//        diag_action.setText(answer.cable_diag_action.diag_action_values.port_diag_action);
//
//        pair1_status.setText(answer.pairs_state.pairs_state_values.pair1_status);
//        pair1_length.setText(answer.pairs_state.pairs_state_values.pair1_length);
//        pair2_status.setText(answer.pairs_state.pairs_state_values.pair2_status);
//        pair2_length.setText(answer.pairs_state.pairs_state_values.pair2_length);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        int last_el_id = cl.getChildAt(cl.getChildCount()-1).getId();
        layoutParams.topToBottom = last_el_id;

        switch_item.setLayoutParams(layoutParams);
        switch_item.setId(answer.general.id);

        return switch_item;
    }

    public View getviewIndication(String errIndication) {
        View errorsIndication = ltInflater.inflate(R.layout.error_indication, cl, false);
        TextView error_indication = errorsIndication.findViewById(R.id.errorIndication_val);
        error_indication.setText(errIndication);
        return errorsIndication;
    }

    public View getviewStatus(String errStatus, String errIndex) {
        View errorsStatus = ltInflater.inflate(R.layout.error_status, cl, false);
        TextView error_status = errorsStatus.findViewById(R.id.errorStatus_val);
        TextView error_index = errorsStatus.findViewById(R.id.errorIndex_val);
        error_status.setText(errStatus);
        error_index.setText(errIndex);
        return errorsStatus;
    }

    public View showMain(Answer answer, Boolean w, Boolean f) {
        View switch_item;

        if (w && f) {
            switch_item = ltInflater.inflate(R.layout.fix_and_watch_switch_item, cl, false);
        } else if (w) {
            switch_item = ltInflater.inflate(R.layout.watch_switch_item, cl, false);
        } else if (f) {
            switch_item = ltInflater.inflate(R.layout.fix_switch_item, cl, false);
        } else switch_item = ltInflater.inflate(R.layout.basic_switch_item, cl, false);

        RelativeLayout state_port_container = switch_item.findViewById(R.id.state_port_container);
        RelativeLayout diag_container = switch_item.findViewById(R.id.diag_action_container);
        RelativeLayout pairs_container = switch_item.findViewById(R.id.pairs_container);

        RelativeLayout state_errors_container = switch_item.findViewById(R.id.state_errors_container);
        RelativeLayout diag_errors_container = switch_item.findViewById(R.id.diag_errors_container);
        RelativeLayout pairs_errors_container = switch_item.findViewById(R.id.pairs_errors_container);

        if ((answer.port_state.errorIndication != null) && (!answer.port_state.errorIndication.equals("None"))) {
                View er_view = getviewIndication(answer.port_state.errorIndication);
                state_errors_container.addView(er_view);
                state_port_container.setVisibility(View.GONE);
        } else if ((answer.port_state.errorStatus != null) && (!answer.port_state.errorStatus.equals("None"))){
                View er_view = getviewStatus(answer.port_state.errorStatus, answer.port_state.errorIndex);
                state_errors_container.addView(er_view);
                state_port_container.setVisibility(View.GONE);
        } else {
            TextView state_port = switch_item.findViewById(R.id.state_port_val);
            TextView port_speed = switch_item.findViewById(R.id.port_speed_val);
            TextView enabled_port = switch_item.findViewById(R.id.enabled_port_val);

            state_port.setText(answer.port_state.port_state_values.link_state);
            port_speed.setText(answer.port_state.port_state_values.port_speed);
            enabled_port.setText(answer.port_state.port_state_values.enabled_port);
        }

        if ((answer.cable_diag_action.errorIndication!=null) && (!answer.cable_diag_action.errorIndication.equals("None"))) {
                View er_view = getviewIndication(answer.cable_diag_action.errorIndication);
                diag_errors_container.addView(er_view);
                diag_container.setVisibility(View.GONE);
        } else if ((answer.cable_diag_action.errorStatus!=null) && (!answer.cable_diag_action.errorStatus.equals("None")) ){
                View er_view = getviewStatus(answer.cable_diag_action.errorStatus, answer.cable_diag_action.errorIndex);
                diag_errors_container.addView(er_view);
                diag_container.setVisibility(View.GONE);
        } else {
            TextView diag_action = switch_item.findViewById(R.id.diag_action_val);
            diag_action.setText(answer.cable_diag_action.diag_action_values.port_diag_action);
        }

        if ((answer.pairs_state.errorIndication!=null) && (!answer.pairs_state.errorIndication.equals("None"))) {
                View er_view = getviewIndication(answer.pairs_state.errorIndication);
                pairs_errors_container.addView(er_view);
                pairs_container.setVisibility(View.GONE);
        } else if ((answer.pairs_state.errorStatus!=null) && (!answer.pairs_state.errorStatus.equals("None"))) {
                View er_view = getviewStatus(answer.pairs_state.errorStatus, answer.pairs_state.errorIndex);
                pairs_errors_container.addView(er_view);
                pairs_container.setVisibility(View.GONE);
        } else {
            TextView pair1_length = switch_item.findViewById(R.id.pair1_length_val);
            TextView pair1_status = switch_item.findViewById(R.id.pair1_status_val);
            TextView pair2_length = switch_item.findViewById(R.id.pair2_length_val);
            TextView pair2_status = switch_item.findViewById(R.id.pair2_status_val);

            pair1_status.setText(answer.pairs_state.pairs_state_values.pair1_status);
            pair1_length.setText(answer.pairs_state.pairs_state_values.pair1_length);
            pair2_status.setText(answer.pairs_state.pairs_state_values.pair2_status);
            pair2_length.setText(answer.pairs_state.pairs_state_values.pair2_length);
        }

        TextView ip = switch_item.findViewById(R.id.ip);
        TextView port = switch_item.findViewById(R.id.port);
        TextView date = switch_item.findViewById(R.id.date);

        ImageButton butt_fix = switch_item.findViewById(R.id.fixed_button);
        butt_fix.setId(answer.general.id);
        ImageButton butt_watch = switch_item.findViewById(R.id.watch_button);
        butt_watch.setId(answer.general.id);

        ip.setText(answer.general.ip);
        port.setText(Integer.toString(answer.general.port));
        date.setText(answer.general.date);

        switch_item.setId(answer.general.id);

        ConstraintLayout.LayoutParams layoutParamsNewItem = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        layoutParamsNewItem.topToBottom = R.id.switch_item;
        if (cl.getChildCount() > 2) {

            ConstraintLayout.LayoutParams layoutParamsOldItem = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            layoutParamsOldItem.topToBottom = answer.general.id;
            View old_item = cl.getViewById(answer.general.id-1);
            old_item.setLayoutParams(layoutParamsOldItem);
        }

        switch_item.setLayoutParams(layoutParamsNewItem);

        return switch_item;
    }


}
