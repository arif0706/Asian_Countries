package com.example.asiancountries.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asiancountries.Adapter.MainActivityAdapter;
import com.example.asiancountries.Controller.MainActivityController;
import com.example.asiancountries.Model.Country;
import com.example.asiancountries.Network.InternetReceiver;
import com.example.asiancountries.R;
import com.example.asiancountries.Room.AppDatabase;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivityView, MainActivityAdapter.ListItemClickListener,InternetReceiver.getConnection{

    RecyclerView recyclerView;
    MainActivityController controller;

    AppDatabase appDatabase;

    InternetReceiver internetReceiver;

    LinearLayout internet_layout;
    TextView internet_check;

    boolean internet_connection;

    boolean isDataRetrieved=false;
    MainActivityAdapter adapter;

    LinearProgressIndicator progressIndicator;

    Button delete_room_data;

    TextView empty_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        controller=new MainActivityController(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressIndicator.setVisibility(View.VISIBLE);
        appDatabase=AppDatabase.getInstance(this);
        internetReceiver=new InternetReceiver(this);
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver,intentFilter);


        delete_room_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase.databaseDao().deleteData();
                controller.getCountriesFromRoom(MainActivity.this);
            }
        });

    }
    void init(){
        recyclerView=findViewById(R.id.recycler_view);
        internet_layout=findViewById(R.id.internet_layout);
        internet_check=findViewById(R.id.internet_check);
        progressIndicator=findViewById(R.id.progress_horizontal);
        delete_room_data=findViewById(R.id.delete_room);
        empty_message=findViewById(R.id.empty_message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainactivity_search_menu,menu);

        SearchView searchView= (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null)
                    adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(Country country) {
        Intent intent=new Intent(this,CountryDetails.class);
        intent.putExtra("country",new Gson().toJson(country));
        startActivity(intent);
    }

    @Override
    public void onGettingList(List<Country> countries) {

        setRecyclerView(countries);

        for(Country country:countries)
            appDatabase.databaseDao().InsertCountry(country);

    }

    @Override
    public void onGettingListFromRoom(List<Country> countries) {

        setRecyclerView(countries);
    }

    void setRecyclerView(List<Country> countries){
        progressIndicator.setVisibility(View.GONE);
        if(countries.isEmpty()){
            empty_message.setVisibility(View.VISIBLE);
        }
         else {
             empty_message.setVisibility(View.GONE);
             Collections.sort(countries,Country.sortByNameAsc);
         }

        adapter = new MainActivityAdapter(this, countries, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getNoConnectionValue(String text) {
        progressIndicator.setVisibility(View.GONE);
        delete_room_data.setVisibility(View.VISIBLE);

        TransitionManager.beginDelayedTransition(findViewById(R.id.main_layout),new AutoTransition());
        TransitionManager.beginDelayedTransition(internet_layout,new AutoTransition());

        internet_layout.setVisibility(View.VISIBLE);
        internet_layout.setBackgroundColor(Color.RED);
        internet_check.setTextColor(Color.WHITE);
        internet_check.setText(text);

        internet_connection=false;

        if(!isDataRetrieved){
            controller.getCountriesFromRoom(MainActivity.this);
        }


    }

    @Override
    public void getYesConnectionValue(String online, String text) {
        delete_room_data.setVisibility(View.GONE);
        progressIndicator.setVisibility(View.VISIBLE);


        internet_layout.setBackgroundColor(Color.parseColor("#4b8b3b"));
        internet_check.setTextColor(Color.WHITE);
        internet_check.setText(online);

        Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time=1500;
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(internet_layout,new Slide(Gravity.BOTTOM));
                        internet_layout.setVisibility(View.GONE);
                    }
                });

            }
        }).start();

        controller.getCountries();
        isDataRetrieved=true;

        internet_connection=true;
    }
}