package com.example.a201612221434.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends Activity {

    JSONObject jsonObject;
    JSONArray jsonArrayItems;
    LinearLayout lyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_main);

        findViewById(R.id.preview_1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                startActivity(new Intent(ResultActivity.this,MainActivity.class));


            }
        });

        Intent intent = getIntent();

        String jsonString = intent.getStringExtra("jsonObject");

        try {
            jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            jsonArrayItems = jsonObject1.getJSONArray("listings");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        lyContainer = (LinearLayout)findViewById(R.id.lycontainer);
        presentData();

    }

    void presentData() {
        for(int i = 0; i < jsonArrayItems.length(); i ++) {
            try {
                final JSONObject jsonObject = jsonArrayItems.getJSONObject(i);

                LayoutInflater inflater = LayoutInflater.from(this);
                View viewResult = inflater.inflate(R.layout.cell_result, null);

                ImageView imgvResult = (ImageView) viewResult.findViewById(R.id.imgvResult);
                TextView tvPrice = (TextView)viewResult.findViewById(R.id.tvPrice);
                TextView tvAddress = (TextView)viewResult.findViewById(R.id.tvAddress);

                String strImageURL = jsonObject.getString("img_url");
                String strPrice = jsonObject.getString("price_formatted");
                String strAddress = jsonObject.getString("title");

                tvPrice.setText(strPrice);
                tvAddress.setText(strAddress);

                viewResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultActivity.this, DetailActivity.class);
                        intent.putExtra("jsonObject", jsonObject.toString());
                        startActivity(intent);
                    }
                });

                lyContainer.addView(viewResult);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
