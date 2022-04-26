package com.example.olioohjelmointifinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.OutputStreamWriter;
import java.io.IOException;
import android.content.Context;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        System.out.println(context.getFilesDir());
        //Writing headline for saved firms file
        /*try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("savedFirms.csv", context.MODE_APPEND));
            ows.append("Y-tunnus;verovelvollisen nimi;verotuskunta;verotettava tulo;maksuunpannut verot yhteensä\n");
            ows.close();
            System.out.println("Headline writen");

        } catch (IOException e){
            Log.e("IOException", "Error in WriteFile");
        }*/
        readTaxData();

    }
    private List<TaxSample> TaxSamples = new ArrayList<>();

    private void readTaxData() {
        InputStream is = getResources().openRawResource(R.raw.verotiedot);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        // Get rid of tittle line
        try {
            String extra = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        try {
            while ((line = reader.readLine()) != null){
                // Split by ','
                String[] tokens = line.split(";");

                // Read the data
                TaxSample sample = new TaxSample();
                sample.setID(tokens[1]);
                sample.setName(tokens[2]);
                sample.setLocation(tokens[3]);
                sample.setTaxedIncome(String.valueOf((tokens[4])));
                sample.setPayedTax(String.valueOf((tokens[5])));
                TaxSamples.add(sample);

                Log.d("MyActivity", "Just created "+ sample);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data on line " + line, e);
            e.printStackTrace();
        }
    }


    //Writes saved firms data to csv
    public void writeFile(View v){
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("savedFirms2.csv", context.MODE_APPEND));
            ows.append(TaxSamples.get(1).getID() + ";" + TaxSamples.get(1).getName() + ";" + TaxSamples.get(1).getLocation() + ";" + TaxSamples.get(1).getTaxedIncome() + ";" + TaxSamples.get(1).getPayedTax() + "\n");
            ows.close();
            System.out.println("Writen line to csv");

        } catch (IOException e){
            Log.e("IOException", "Error in WriteFile");
        }
    }
}