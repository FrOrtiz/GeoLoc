package com.example.dam.geoloc;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class LocationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent i=new Intent(this, LocationService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification.Builder constructorNotificacion = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Tracking...")
                .setContentText("Recogiendo tu ubicación")
                .setContentIntent(PendingIntent.getActivity(this, 0, i, 0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startForeground(1, constructorNotificacion.build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Double latitud = intent.getDoubleExtra("latitud", 0);
        Double longitud = intent.getDoubleExtra("longitud", 0);
        String fecha = intent.getStringExtra("fecha");
        String ruta = intent.getStringExtra("ruta");

        Localizacion location = new Localizacion(new LatLng(latitud, longitud), fecha);

        DB4o db4o = new DB4o();
        db4o.addTracking(location, ruta);

        Toast.makeText(this, latitud + "\n" + longitud + "\n" + fecha, Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }
}
