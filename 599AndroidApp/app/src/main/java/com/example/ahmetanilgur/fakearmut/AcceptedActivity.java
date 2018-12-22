package com.example.ahmetanilgur.fakearmut;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AcceptedActivity extends AppCompatActivity {
    String[] userArray = {"Anıl","Emir C","Elif", "Alara"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.single_item_listview, userArray);
        final ListView listView = findViewById(R.id.user_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                String selected =((TextView) view.findViewById(R.id.single_user_textview)).getText().toString();
                finish();
            }
        });

    }

}
