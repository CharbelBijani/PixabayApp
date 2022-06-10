package com.dam.pixabayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        itemArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void newSearch() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemArrayList.clear();
                search = etSearch.getText().toString().trim();
                Log.i(TAG, "newSearch: " + search);
                parseJSON(search);
            }
        });
    }

    private void parseJSON(String search) {
        String pixabayApiKey = "27952912-584e25ee0e8d5d38da63ce60d";

        //https://pixabay.com/api/
        // ?key=27952912-584e25ee0e8d5d38da63ce60d
        // &q=yellow+flowers
        // &image_type=photo

        String urlJSONFile =
                "https://pixabay.com/api/"
                        + "?key="
                        + pixabayApiKey
                        + "&q="
                        + search
                        + "&image_type=photo"
                        + "&pretty=true";

        Log.i(TAG, "parseJSON: " + urlJSONFile);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlJSONFile, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creator = hit.getString("user");
                                int likes = hit.getInt("likes");
                                String imageUrl = hit.getString("webformatURL");

                                itemArrayList.add(new ModelItem(imageUrl, creator, likes));

                            }

                            adapterItem = new AdapterItem(MainActivity.this, itemArrayList);

                            recyclerView.setAdapter(adapterItem);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        newSearch();

        parseJSON(search);

    }


}