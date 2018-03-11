package com.example.dam.geoloc;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etFecha;
    private int dia, mes, anio;
    private String permission;
    static final int PERMISSION_REQUEST_LOCATION = 1;

    private void events(){
        findViewById(R.id.btSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (dayOfMonth < 10 && monthOfYear < 10) {
                            etFecha.setText("0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                        } else if (dayOfMonth < 10 ) {
                            etFecha.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        } else if (monthOfYear < 10) {
                            etFecha.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            etFecha.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        findViewById(R.id.btMapa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = etFecha.getText().toString();
                if(fecha.equals("")){
                    Toast.makeText(MainActivity.this, "Seleccione una fecha", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("fecha", fecha);
                    startActivity(intent);
                }
            }
        });
    }

    private void init(){
        etFecha = findViewById(R.id.etFecha);
        etFecha.setEnabled(false);
        controlPermission();
        events();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void controlPermission(){
        permission = Manifest.permission.ACCESS_FINE_LOCATION;
        checkPermission();
    }

    private void checkPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                permission);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Ha aceptado
        } else {
            // Ha denegado o es la primera vez que se le pregunta
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // No se le ha preguntado aún
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_LOCATION);
            } else {
                // Ha denegado
                Toast.makeText(this, "Please, enable the request permission", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Estamos en el caso del teléfono
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(permission)) {
                    // Comprobar si ha sido aceptado o denegado la petición de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // Concedió su permiso
                    } else {
                        // No concendió su permiso
                        Toast.makeText(this, "You declined the access", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}