package com.example.asiancountries.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asiancountries.Controller.CountryDetailsController;
import com.example.asiancountries.Model.Country;
import com.example.asiancountries.Network.InternetReceiver;
import com.example.asiancountries.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.google.gson.Gson;


public class CountryDetails extends AppCompatActivity implements ICountryDetailsView, InternetReceiver.getConnection {
    PhotoView photoView;
    TextView country_name;
    TextView capital_name;
    TextView region_name;
    TextView sub_region_name;
    TextView population_number;
    TextView borders_name;
    TextView languages_name;
    CountryDetailsController controller;
    InternetReceiver internetReceiver;

    boolean internet_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        init();

         controller=new CountryDetailsController(this);

        Country country=new Gson().fromJson(getIntent().getStringExtra("country"),Country.class);

        if(country!=null)
            setDetails(country);
        else{
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            finish();
        }

        internetReceiver=new InternetReceiver(this);
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver,intentFilter);


    }

    void setDetails(Country country) {
        GlideToVectorYou.init().with(this)
                .withListener(new GlideToVectorYouListener() {
                    @Override
                    public void onLoadFailed() {
                        Toast.makeText(CountryDetails.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResourceReady() {

                    }
                })
                .load(Uri.parse(country.getFlag()),photoView);
        country_name.setText(country.getName());
        if(!country.getCapital().isEmpty())
            capital_name.setText(country.getCapital());
        else
            capital_name.setText("No Capital");
        region_name.setText(country.getRegion());
        sub_region_name.setText(country.getSubregion());



        double compute_result=compute(country.getPopulation(),1000000.0);
        if(compute_result<1000.0) {
            population_number.setText(String.format("%.2f M", compute_result));
        }
        else{
            compute_result=compute(country.getPopulation(),1000000000.0);
            population_number.setText(String.format("%.3f B",compute_result));
        }


        LinearLayout view=findViewById(R.id.borders_texts);
        if(country.getBorders().length>0) {
            borders_name.setVisibility(View.GONE);
            for (int i = 0; i < country.getBorders().length; i++) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,0,0,0);
                textView.setLayoutParams(layoutParams);
                textView.append(Html.fromHtml("<u>"+country.getBorders()[i]+"</u>"));
                textView.setPadding(2,2,2,2);
                textView.setTextSize(18f);
                view.addView(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(internet_connection)
                            controller.getCountryWithCode(textView.getText().toString());
                        else
                            controller.getCountryWithCodeFromRoom(textView.getText().toString(),CountryDetails.this);
                    }
                });
            }
        }
        else
            borders_name.append("No borders");
        for(int i=0;i<country.getLanguages().length;i++){
            languages_name.append((i+1)+". "+country.getLanguages()[i].name+"\n");
        }
    }

    double compute(String population,double div){
        return Integer.parseInt(population)/div;
    }
    void init(){
        photoView=findViewById(R.id.photo_view);
        country_name=findViewById(R.id.country_name);
        capital_name=findViewById(R.id.capital_name);
        region_name=findViewById(R.id.region_name);
        sub_region_name=findViewById(R.id.sub_region_name);
        population_number=findViewById(R.id.population_number);
        borders_name=findViewById(R.id.borders_name);
        languages_name=findViewById(R.id.languages_name);

    }

    @Override
    public void onGettingCountry(Country country) {
        Intent intent=new Intent(this,CountryDetails.class);
        intent.putExtra("country",new Gson().toJson(country));
        startActivity(intent);
    }

    @Override
    public void getNoConnectionValue(String text) {

        internet_connection=false;


    }

    @Override
    public void getYesConnectionValue(String online, String text) {

        internet_connection=true;
    }
}