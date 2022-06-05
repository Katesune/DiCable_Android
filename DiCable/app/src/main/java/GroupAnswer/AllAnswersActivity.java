package GroupAnswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.dicable.Answer;
import com.example.dicable.FireBase.AuthActivity;
import com.example.dicable.FireBase.GoogleAuth;
import com.example.dicable.Fragment.BasicFragment;
import com.example.dicable.Fragment.FixFragment;
import com.example.dicable.Fragment.WatchFixFragment;
import com.example.dicable.Fragment.WatchFragment;
import com.example.dicable.Graphics.StatisticActivity;
import com.example.dicable.MainActivity;
import com.example.dicable.R;
import com.example.dicable.ShowResult;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class AllAnswersActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference dbRef;
    ArrayList<Answer> answers = new ArrayList<>();

    int count = 0;
    LinearLayout ll;

    List<Integer> fixed = new ArrayList<>();
    List<Integer> watched = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_answers);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dicable-default-rtdb.firebaseio.com/");
        ll = (LinearLayout) findViewById(R.id.frame_container);


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                answers = new ArrayList<>();
                count =  (int)snapshot.child("basic").getChildrenCount()+1;
                collect(snapshot);

                Intent intent = getIntent();

                for (DataSnapshot s : snapshot.child("basic").getChildren()) {
                    Answer db_answer = s.getValue(Answer.class);
                    if (!fixed.contains(db_answer.general.id) && !watched.contains(db_answer.general.id)) {
                        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                            String query = intent.getStringExtra(SearchManager.QUERY);
                            setQueryAnswers(query, db_answer);
                        } else answers.add(db_answer);
                    }
                }

                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mytag", "Failed to read value");
            }
        });
    }

    public void setQueryAnswers(String q, Answer a) {
        if (q.equals(a.general.ip)) {
            answers.add(a);
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

    public void updateUI() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int elem_id = answers.size(); elem_id > 0; elem_id--) {
            if (ll.findViewById(answers.get(elem_id-1).general.id) == null) {
                Boolean w = watched.contains(answers.get(elem_id - 1).general.id);
                Boolean f = fixed.contains(answers.get(elem_id - 1).general.id);

                if (w && f) {
                    transaction.add(R.id.frame_container,  new WatchFixFragment(answers.get(elem_id - 1)));
                } else if (w) {
                    transaction.add(R.id.frame_container,  new WatchFragment(answers.get(elem_id - 1)));
                } else if (f) {
                    transaction.add(R.id.frame_container,  new FixFragment(answers.get(elem_id - 1)));
                } else transaction.add(R.id.frame_container,  new BasicFragment(answers.get(elem_id - 1)));
            } else
                ((ViewGroup)(ll)).removeView(ll.findViewById(answers.get(elem_id-1).general.id));
        }

        transaction.commitAllowingStateLoss();
    }

    public void WatchedSwitch(View v) {
        if (watched.contains(v.getId())) {
            v.setBackground(getResources().getDrawable(R.drawable.vision_no_active_switch));
            dbRef.child("watched").child(String.valueOf(v.getId())).removeValue();
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.vision_active_switch));
            dbRef.child("watched").child(String.valueOf(v.getId())).setValue(true);
        }
    }

    public void Fixed(View v) {
        if (fixed.contains(v.getId())) {
            v.setBackground(getResources().getDrawable(R.drawable.repair_no_active));
            dbRef.child("fixed").child(String.valueOf(v.getId())).removeValue();
        } else {
            v.setBackground(getResources().getDrawable(R.drawable.repair_active));
            dbRef.child("fixed").child(String.valueOf(v.getId())).setValue(true);
        }
    }

    public void goMain(View v) {
        Intent intent = new Intent(AllAnswersActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToWatched(View v) {
        Intent intent = new Intent(AllAnswersActivity.this, WatchedAnswersActivity.class);
        startActivity(intent);
    }

    public void goToFixed(View v) {
        Intent intent = new Intent(AllAnswersActivity.this, FixedAnswersActivity.class);
        startActivity(intent);
    }

    public void goToStatistic(View v) {
        Intent intent = new Intent(AllAnswersActivity.this, StatisticActivity.class);
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

        Intent intent = new Intent(AllAnswersActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}