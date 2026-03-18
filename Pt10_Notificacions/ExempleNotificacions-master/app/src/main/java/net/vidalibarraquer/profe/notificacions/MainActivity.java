package net.vidalibarraquer.profe.notificacions;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

@SuppressLint("MissingPermission")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = findViewById(R.id.textView);
        infoTextView.setText("Benvingut a l'exemple de notificacions");

        // creem un canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        Button subscribeButton = findViewById(R.id.btnSubscript);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubscripcioActivity.class);
                startActivity(intent);
            }
        });

        Button logTokenButton = findViewById(R.id.btnToken);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistreActivity.class);
                startActivity(intent);
            }
        });

        askNotificationPermission();
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    infoTextView.setText("Permís de notificacions concedit");
                    Toast.makeText(this, "Permís de notificacions concedit", Toast.LENGTH_SHORT).show();
                } else {
                    infoTextView.setText("L'aplicació no mostrarà notificacions");
                    Toast.makeText(this, "L'aplicació no mostrarà notificacions", Toast.LENGTH_SHORT).show();
                }
            });

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                infoTextView.setText("Permís de notificacions ja concedit");
            } else if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                new AlertDialog.Builder(this)
                        .setTitle("Notificacions")
                        .setMessage(R.string.notification_permission_rationale)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            requestPermissionLauncher.launch(POST_NOTIFICATIONS);
                        })
                        .setNegativeButton(R.string.no_thanks, (dialog, which) -> {
                            infoTextView.setText("Has rebutjat les notificacions");
                        })
                        .show();
            } else {
                requestPermissionLauncher.launch(POST_NOTIFICATIONS);
            }
        }
    }
}
