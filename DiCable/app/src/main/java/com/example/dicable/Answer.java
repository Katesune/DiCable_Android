package com.example.dicable;

import com.example.dicable.AnswerContent.CableDiagAction;
import com.example.dicable.AnswerContent.PairsState;
import com.example.dicable.AnswerContent.PortState;
import com.example.dicable.AnswerContent.Values.Collections;
import com.example.dicable.AnswerContent.Values.General;

public class Answer {
//    String errorIndication;
    String ip;
    Integer port;
    String date;

    public PairsState pairs_state;
    public PortState port_state;
    public CableDiagAction cable_diag_action;
    public General general;

    public Collections collections;

    public void compileGeneral() {
        general.ip = ip;
        general.port = port;
        general.date = date;
    }

    public void setFix() {
        collections.fixed = true;
    }

    public void setWatch() {
        collections.watched = true;
    }

    public void setNoFix() {
        collections.fixed = false;
    }

    public void setNoWatch() {
        collections.watched = false;
    }
}
