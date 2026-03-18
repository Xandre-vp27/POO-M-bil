package com.example.pt10_notificacions;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.annotation.NonNull;
import android.util.Log;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("VibNoti", "Mensaje recibido");

        if (remoteMessage.getNotification() != null) {
            Log.d("VibNoti", "Titol: " + remoteMessage.getNotification().getTitle());
            Log.d("VibNoti", "Cos: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("FCM", "Nou token: " + token);
    }
}
