package com.example.dicable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dicable.FireBase.AuthActivity;
import com.example.dicable.FireBase.GoogleAuth;
import com.example.dicable.Graphics.StatisticActivity;
import com.example.dicable.AnswerContent.Values.General;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import com.example.dicable.GroupAnswer.AllAnswersActivity;
import com.example.dicable.GroupAnswer.FixedAnswersActivity;
import com.example.dicable.GroupAnswer.WatchedAnswersActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference dbRef;
    String api_key = "7b5b67df869fd1d345b5a3a5f5b6646028803056e239c77792417c3347b243c5";

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    ConstraintLayout cl;

    EditText user_ip;
    EditText user_port;

    List<Integer> fixed = new ArrayList<>();
    List<Integer> watched = new ArrayList<>();

    Boolean fix_request = false;
    Boolean watch_request = false;

    int retrofit_count=0;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = (ConstraintLayout) findViewById(R.id.main);
        user_ip = (EditText) findViewById(R.id.user_ip);
        user_port = (EditText) findViewById(R.id.user_port);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dicable-default-rtdb.firebaseio.com/");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id =  (int)snapshot.child("basic").getChildrenCount()+1;
                Intent intent = getIntent();
                retrofit_count +=1;
                collect(snapshot);

                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    setQueryAnswers(query, snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mytag", "Failed to read value");
            }
        });
    }

    public void setQueryAnswers(String q, DataSnapshot s) {
        ArrayList<Answer> q_answers = new ArrayList<>();
        for (int i = id-1 ; i >id-1-retrofit_count; i--) {
            Answer a = s.child("basic").child(Integer.toString(i)).getValue(Answer.class);
            if (q.equals(a.general.ip)) q_answers.add(a);
        }

        for (Answer aa: q_answers) {
            ShowResult sr = new ShowResult(getLayoutInflater(), cl);

            View switch_item = sr.showMain(aa, watched.contains(aa.general.id), fixed.contains(aa.general.id));
            cl.addView(switch_item);
        }
    }

    public void search(View v) {
        String ip = user_ip.getText().toString();
        String port = user_port.getText().toString();

        if (ip.isEmpty() || port.isEmpty()) {
            Toast.makeText(MainActivity.this, "Данные не были введены", Toast.LENGTH_SHORT).show();
        } else {
            request();
        }
    }

    public void request() {
        Call<Answer> getAnswer = getSearch();

        Callback<Answer> answer_callback = new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, retrofit2.Response<Answer> response) {
                Answer a = response.body();
                if (a!=null) {
                    show(a);
                } else Toast.makeText(MainActivity.this, "Результаты не получены", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Log.d("mytag", "fail:" + t.getLocalizedMessage());
            }
        };
        getAnswer.enqueue(answer_callback);
    }

    public Call<Answer> getSearch() {
        String url = "http://192.168.0.2:5000/";

        String ip = user_ip.getText().toString();
        String port = user_port.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API api = retrofit.create(API.class);
        Call<Answer> getAnswer=null;

        getAnswer = api.search(ip, port);

        return getAnswer;
    }

    public void show(Answer answer) {
        answer.general = new General();
        answer.compileGeneral();
        answer.general.id = id;

        ShowResult sr = new ShowResult(getLayoutInflater(), cl);
        View switch_item = sr.showMain(answer, watch_request, fix_request);
        cl.addView(switch_item);

        if (watch_request) {
            dbRef.child("watched").child(String.valueOf(id)).setValue(true);
        }
        if (fix_request) {
            dbRef.child("fixed").child(String.valueOf(id)).setValue(true);
        }

        dbRef.child("basic").child(String.valueOf(id)).setValue(answer);
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

    public void watchRequest(View v) {
        if(v.getBackground().getConstantState().equals(
                getResources().getDrawable(R.drawable.vision_active).getConstantState())) {
            v.setBackground(getResources().getDrawable(R.drawable.vision_no_active));
            watch_request = false;
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.vision_active));
            watch_request = true;
        }
    }

    public void fixRequest(View v) {
        if(v.getBackground().getConstantState().equals(
                getResources().getDrawable(R.drawable.repair_active).getConstantState())) {
            v.setBackground(getResources().getDrawable(R.drawable.repair_no_active));
            fix_request = false;
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.repair_active));
            fix_request = true;
        }
    }

    public void WatchedSwitch(View v) {
        if(v.getBackground().getConstantState().equals(
                getResources().getDrawable(R.drawable.vision_active_switch).getConstantState())) {
            v.setBackground(getResources().getDrawable(R.drawable.vision_no_active_switch));
            dbRef.child("watched").child(String.valueOf(v.getId())).removeValue();
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.vision_active_switch));
            if (!watched.contains(v.getId())) {
                dbRef.child("watched").child(String.valueOf(v.getId())).setValue(true);
            }
        }
    }

    public void Fixed(View v) {
        if(v.getBackground().getConstantState().equals(
                getResources().getDrawable(R.drawable.repair_active).getConstantState())) {
            v.setBackground(getResources().getDrawable(R.drawable.repair_no_active));
            dbRef.child("fixed").child(String.valueOf(v.getId())).removeValue();

        } else {
            v.setBackground(getResources().getDrawable(R.drawable.repair_active));
            if (!fixed.contains(v.getId())) {
                dbRef.child("fixed").child(String.valueOf(v.getId())).setValue(true);
            }
        }
    }

    public void goToAll(View v) {
        Intent intent = new Intent(MainActivity.this, AllAnswersActivity.class);
        startActivity(intent);
    }

    public void goToFixed(View v) {
        Intent intent = new Intent(MainActivity.this, FixedAnswersActivity.class);
        startActivity(intent);
    }

    public void goToWatched(View v) {
        Intent intent = new Intent(MainActivity.this, WatchedAnswersActivity.class);
        startActivity(intent);
    }

    public void goToStatistic(View v) {
        Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Logout();
                return true;
            case R.id.app_bar_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Logout() {
        FirebaseAuth.getInstance().signOut();

        mGoogleSignInClient = new GoogleAuth().googleAuthentification(this);
        mGoogleSignInClient.signOut();

        Intent intent = new Intent(MainActivity.this,AuthActivity.class);
        startActivity(intent);
    }

}