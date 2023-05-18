package com.example.dealrunner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import utilities.FirebaseHandlers;

public class DatabaseListenerService extends Service {
    private DatabaseReference dealsRef;
    private Context context;
    private long currentDeals = 0;
    private int NOTIFICAION_ID = 123;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        dealsRef = FirebaseHandlers.getDealsReference();
        startListeningToDBChange();
        return START_STICKY;
    }

    public void startListeningToDBChange(){
        dealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > currentDeals) {
                    createNotificationChannel();
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.new_deal_notify_small);
                    Intent intent = new Intent(context, BarNavigationBottom.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, getString(R.string.channel_id))
                            .setSmallIcon(R.drawable.ic_logo_blue)
                            .setContentIntent(pendingIntent)
                            .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                            .setCustomContentView(notificationLayout)
                            .setAutoCancel(true);
                    notificationManager.notify(NOTIFICAION_ID, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
