package com.example.dealrunner;

import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import models.Deal;
import utilities.DistanceCalculator;

public class DealsAdapter extends FirebaseRecyclerAdapter<Deal, DealsAdapter.DealViewHolder> {
    Location userCurrentLocation;

    public static class DealViewHolder extends RecyclerView.ViewHolder {

        protected TextView dealName;
        protected TextView dealDiscount;
        protected TextView dealDistance;
        protected TextView dealExpiry;
        protected ImageView dealImage;
        protected TextView dealDescription;
        protected LinearLayout dealConsumerItem;

        public DealViewHolder(@NonNull View dealView) {
            super(dealView);
            dealName = dealView.findViewById(R.id.deal_name);
            dealDiscount = dealView.findViewById(R.id.deals_discount);
            dealDistance = dealView.findViewById(R.id.location);
            dealExpiry = dealView.findViewById(R.id.deal_expire);
            dealImage = dealView.findViewById(R.id.deals_image);
            dealDescription = dealView.findViewById(R.id.deal_description);
            dealConsumerItem = dealView.findViewById(R.id.deal_consumer_item);
        }

        public TextView getDealName() {
            return dealName;
        }

        public TextView getDealDiscount() {
            return dealDiscount;
        }

        public TextView getDealDistance() {
            return dealDistance;
        }

        public TextView getDealDescription() {
            return dealDescription;
        }

        public TextView getDealExpiry() {
            return dealExpiry;
        }

        public ImageView getDealImage() {
            return dealImage;
        }
    }

    public DealsAdapter(FirebaseRecyclerOptions<Deal> dealsRecyclerOptions) {
        super(dealsRecyclerOptions);

    }
    public void setUserCurrentLocation(Location userCurrentLocation){
        this.userCurrentLocation = userCurrentLocation;
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newDealView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_consumer_item, parent, false);
        return new DealViewHolder(newDealView);
    }

    @Override
    protected void onBindViewHolder(@NonNull DealViewHolder holder, int position, @NonNull Deal newDeal) {
        holder.getDealName().setText(newDeal.getTitle());
        holder.getDealDiscount().setText(String.valueOf(newDeal.getDiscount()));
        holder.getDealDescription().setText(newDeal.getDescription());

        String dist = DistanceCalculator.distance(newDeal, userCurrentLocation);
        holder.getDealDistance().setText(dist);

        if (newDeal.getCategory().equalsIgnoreCase("cinema")) {
            holder.getDealImage().setImageResource(R.drawable.cinplex_logo);
        }


        holder.getDealExpiry().setText(newDeal.getExpiry());
        holder.getDealExpiry().setText(newDeal.getExpiry());
        SimpleDateFormat sdf= new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = sdf.parse(newDeal.getExpiry());
            Calendar closingTime = Calendar.getInstance();
            closingTime.setTime(date);
            closingTimeStamp(closingTime,holder.getDealExpiry());
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        //onclicklistener for deal description page
        holder.dealConsumerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "On Click Listener working", Toast.LENGTH_LONG).show();
                String id_deal = newDeal.getDealID();
                Intent intent = new Intent(view.getContext(),DealPage.class);
                intent.putExtra("dealsObject",newDeal);
                view.getContext().startActivity(intent);
            }
        });

    }
    public void closingTimeStamp(Calendar closingTime, TextView dealExpire) {
        Calendar currentTime = Calendar.getInstance();

        if (currentTime.before(closingTime)) {
            LocalTime localClosingTime = closingTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            LocalTime localCurrentTime = currentTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            Date localClosingDate = closingTime.getTime();
            Date localCurrentDate = currentTime.getTime();
            Duration duration = Duration.between(localClosingTime,localCurrentTime);

            long s = localClosingDate.getTime()-localCurrentDate.getTime();
            s = s/1000;
            String readableDuration = String.format("%d:%02d:%02d", (s/3600), (s%3600)/60, (s%60));
            dealExpire.setText("Expires in "+readableDuration);
        }
        else {

            dealExpire.setTextColor(ContextCompat.getColor(dealExpire.getContext(),R.color.deal_expired));
            dealExpire.setText(R.string.expire);
        }

    }
}
