package com.example.dicable.Graphics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.dicable.Answer;
import com.example.dicable.MainActivity;
import com.example.dicable.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.example.dicable.GroupAnswer.AllAnswersActivity;
import com.example.dicable.GroupAnswer.FixedAnswersActivity;
import com.example.dicable.GroupAnswer.WatchedAnswersActivity;
import im.dacer.androidcharts.BarView;

public class StatisticActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    ArrayList<Answer> answers = new ArrayList<>();
    HashMap<String, Integer> collect_ans = new HashMap<String, Integer>();

    EditText lim;
    int limit = 10;

    List<Integer> fixed = new ArrayList<>();
    List<Integer> watched = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dicable-default-rtdb.firebaseio.com/");
        lim = (EditText) findViewById(R.id.limit);

        lim.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    limit = Integer.parseInt(lim.getText().toString());
                    BarView barView = (BarView)findViewById(R.id.bar_view);

                    Set<String> keySet = collect_ans.keySet();
                    ArrayList<String> ips = new ArrayList<String>(keySet);
                    Collection<Integer> values = collect_ans.values();
                    ArrayList<Integer> key_values = new ArrayList<>(values);

                    barView.setBottomTextList(ips);
                    barView.setBackgroundColor(getResources().getColor(R.color.black));
                    barView.setDataList(key_values,limit);
                    return true;
                }
                return false;
            }
        }
        );

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                answers = new ArrayList<>();
                collect(snapshot);

                for (DataSnapshot s : snapshot.child("basic").getChildren()) {
                    Answer db_answer = s.getValue(Answer.class);
                    if (!fixed.contains(db_answer.general.id)) {
                        answers.add(db_answer);
                    }
                }

                collect_ip(answers);
                BarView barView = (BarView)findViewById(R.id.bar_view);

                Set<String> keySet = collect_ans.keySet();
                ArrayList<String> ips = new ArrayList<String>(keySet);
                Collection<Integer> values = collect_ans.values();
                ArrayList<Integer> key_values = new ArrayList<>(values);

                barView.setBottomTextList(ips);
                barView.setBackgroundColor(getResources().getColor(R.color.black));
                barView.setDataList(key_values,limit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mytag", "Failed to read value");
            }
        });
    }

    public LineGraphSeries<DataPoint> getSerials() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        series.setColor(R.color.purple_200);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);

        int i =0;
        for ( String key : collect_ans.keySet()) {
            series.appendData(new DataPoint(i, collect_ans.get(key)), true, 20);
            i++;
        }
        return series;
    }


    public void collect_ip(ArrayList<Answer> ans) {
        for (Answer a: ans) {
            if (!collect_ans.containsKey(a.general.ip)) {
                collect_ans.put(a.general.ip, 1);
            } else {
                Integer num = collect_ans.get(a.general.ip)+1;
                collect_ans.put(a.general.ip, num);
            }
        }

    }

    public void collect(DataSnapshot snapshot) {
        fixed = new ArrayList<>();
        for (DataSnapshot s : snapshot.child("fixed").getChildren()) {
            fixed.add(Integer.parseInt(s.getKey()));
        }
        watched = new ArrayList<>();
        for (DataSnapshot s : snapshot.child("watched").getChildren()) {
            watched.add(Integer.parseInt(s.getKey()));
        }
    }

    public void goToMain(View v) {
        Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToAll(View v) {
        Intent intent = new Intent(StatisticActivity.this, AllAnswersActivity.class);
        startActivity(intent);
    }

    public void goToFixed(View v) {
        Intent intent = new Intent(StatisticActivity.this, FixedAnswersActivity.class);
        startActivity(intent);
    }

    public void goToWatched(View v) {
        Intent intent = new Intent(StatisticActivity.this, WatchedAnswersActivity.class);
        startActivity(intent);
    }
}