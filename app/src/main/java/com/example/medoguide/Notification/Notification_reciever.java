package com.example.medoguide.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.example.medoguide.MedicineActivity;
import com.example.medoguide.R;

public class Notification_reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicineName");
        String noOfDoses = intent.getStringExtra("no_of_doses");

        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000};
        mVibrator.vibrate(pattern, 0);

        MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.cuco_sound);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent medicineListIntent = new Intent(context, MedicineActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,801,medicineListIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.doctor_icon)
                .setContentTitle("Medicine Alert")
                .setContentText("Take " + noOfDoses + " of " + medicineName)
                .setPriority(1)
                .setAutoCancel(true);
        notificationManager.notify(801,builder.build());
    }
}
