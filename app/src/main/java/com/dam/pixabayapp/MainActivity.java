package com.dam.pixabayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* VARS GLOBALES */
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ArrayList<ModelItem> itemArrayList;
    private AdapterItem adapterItem;
    private RequestQueue requestQueue; // Pour Volley

    private EditText etSearch;
    private Button btnSearch;
    private String search;

    private void initUI() {
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
    }

    private void newSearch(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = etSearch.getText().toString().trim();
                Log.i(TAG, "newSearch: " + search);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        newSearch();

    }



}