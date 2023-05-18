package com.example.dealrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import models.Deal;

public class DealPage extends AppCompatActivity {

    TextView dealTitle, bAddress, dDescription;
    Button getDeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_page);

        Deal deal = (Deal)getIntent().getSerializableExtra("dealsObject");
        ImageView Image = findViewById(R.id.deal_banner);

        dealTitle = findViewById(R.id.titleText);
        bAddress = findViewById(R.id.textView3);
        dDescription = findViewById(R.id.textView4);
        getDeal = findViewById(R.id.get_deal_button);

        dealTitle.setText(deal.getTitle());
        bAddress.setText(deal.getAddress());
        dDescription.setText(deal.getDescription());

        if (deal.getCategory().equalsIgnoreCase("cinema")) {
            Image.setImageResource(R.drawable.cinplex_logo);
        }
        if (deal.getCategory().equalsIgnoreCase("food")) {
            Image.setImageResource(R.drawable.pizaa_pizza);
        }

        getDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeal.setText("Coupon ID : X3241D");
            }
        });
    }
}