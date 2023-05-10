package com.example.fianlprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import androidx.core.content.ContextCompat;

import android.location.Location;
import android.location.LocationListener;

import java.text.DecimalFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private EditText longi;
    private EditText lati;
    public EditText op;
    private Button LocationButton;
    private LocationManager locationManager;
    private Button Lookangle;
    public EditText azimuthangle;
    public EditText elevationangle;
    public Button submitdata;
    public Button getCompass;
    public Databasehelper databasehelper;
    private static final double PI = Math.PI;
    private static final double TWO_PI = 2 * PI;
    private static final double RADIUS_EARTH = 6371; // in km


    String[] satellite = {"CMS-01","GSAT-30","GSAT-6A","GSAT-6","GSAT-16","GSAT-10","INSAT-4A","INSAT-3B","INSAT-2E","GSAT-31","GSAT-19","INSAT-4CR","GSAT-7A","GSAT-11 Mission","GSAT-18","GSAT-14","GSAT-7","EDUSAT","KALPANA-1","INSAT-2C","INSAT-2A","INSAT-2B","INSAT-1B","GSAT-29","GSAT-8","INSAT-3E","GSAT-17","GSAT-15","INSAT-3A","INSAT-3C","INSAT-1D","GSAT-12","GSAT-2"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter <String> adapter=new ArrayAdapter<>(this,R.layout.drop_down_item,satellite);
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.spinner);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,autoCompleteTextView.getText().toString(),Toast.LENGTH_SHORT).show();
                String  selectedItem = satellite[i];
                if (selectedItem==satellite[0]||selectedItem==satellite[1]||selectedItem==satellite[2]||selectedItem==satellite[3]||selectedItem==satellite[4]||selectedItem==satellite[5]||selectedItem==satellite[6]||selectedItem==satellite[7]||selectedItem==satellite[8]) {
                    op.setText("83");
                }
                else if(selectedItem==satellite[9]||selectedItem==satellite[10]||selectedItem==satellite[11]){
                    op.setText("47.9");
                } else if (selectedItem==satellite[12]) {
                    op.setText("38");
                } else if (selectedItem==satellite[13]||selectedItem==satellite[14]||selectedItem==satellite[15]||selectedItem==satellite[16]||selectedItem==satellite[17]||selectedItem==satellite[18]||selectedItem==satellite[19]||selectedItem==satellite[20]||selectedItem==satellite[21]||selectedItem==satellite[22]) {
                    op.setText("74");
                } else if (selectedItem==satellite[23]||selectedItem==satellite[24]||selectedItem==satellite[25]) {
                    op.setText("55");
                } else if (selectedItem==satellite[26]||selectedItem==satellite[27]||selectedItem==satellite[28]||selectedItem==satellite[29]||selectedItem==satellite[30]) {
                    op.setText("93.5");
                } else if (selectedItem==satellite[31]||selectedItem==satellite[32]) {
                    op.setText("48");
                }

            }
        });


        databasehelper =new Databasehelper(getApplicationContext());

        LocationButton = findViewById(R.id.locationButton);
        lati = findViewById(R.id.lati);
        longi = findViewById(R.id.longi);
        op=(EditText) findViewById(R.id.orbital);
        Lookangle=(Button) findViewById(R.id.lookangles);
        azimuthangle=(EditText) findViewById(R.id.azimuth);
        elevationangle=(EditText) findViewById(R.id.elevation);
        submitdata=findViewById(R.id.Submit);
        getCompass=findViewById(R.id.compass);

        submitdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude=lati.getText().toString();
                String longitude=longi.getText().toString();
                String orbitalposition=op.getText().toString();
                String azimuth=azimuthangle.getText().toString();
                String elevation=elevationangle.getText().toString();
                databasehelper.insertData(new Data(latitude,longitude,orbitalposition,azimuth,elevation));
                Toast.makeText(MainActivity.this, "Submitted", Toast.LENGTH_SHORT).show();

            }
        });

        getCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CompassActivity.class);
                float latitude = Float.valueOf(lati.getText().toString());
                intent.putExtra("Latitude",latitude);
                float longitude=Float.valueOf(longi.getText().toString());
                intent.putExtra("Longitude",longitude);
                float orbital=Float.valueOf(op.getText().toString());
                intent.putExtra("OrbitalPosition",orbital);
                float azimuth=Float.valueOf(azimuthangle.getText().toString());
                intent.putExtra("AzimuthAngle",azimuth);
                float elevation=Float.valueOf(elevationangle.getText().toString());
                intent.putExtra("ElevationAngle",elevation);
                startActivity(intent);
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });

        Lookangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String orbitalLongitude = op.getText().toString();
                    String userLatitude = lati.getText().toString();
                    String userLongitude = longi.getText().toString();


                    double ol = DecimalFormat.getNumberInstance().parse(orbitalLongitude).doubleValue();
                    double ula = DecimalFormat.getNumberInstance().parse(userLatitude).doubleValue();
                    double ulo = DecimalFormat.getNumberInstance().parse(userLongitude).doubleValue();
                    double earthRadius = 6371;


                    double orbitalRadians = Math.toRadians(ol);
                    double userLatRadians = Math.toRadians(ula);
                    double userLongRadians = Math.toRadians(ulo);

                    double x = earthRadius * Math.cos(userLatRadians) * Math.cos(userLongRadians);
                    double y = earthRadius * Math.cos(userLatRadians) * Math.sin(userLongRadians);
                    double z = earthRadius * Math.sin(userLatRadians);

                    double xprime = x * Math.cos(orbitalRadians) + y * Math.sin(orbitalRadians);
                    double yprime = -x * Math.sin(orbitalRadians) + y * Math.cos(orbitalRadians);
                    double zprime = z;

                    double r = Math.sqrt(xprime * xprime + yprime * yprime + zprime * zprime);
                    double elevationRadians = Math.asin(zprime / r);
                    double azimuthRadians = Math.atan2(yprime, xprime);

                    double elevationDegrees = Math.toDegrees(elevationRadians);
                    double azimuthDegrees = Math.toDegrees(azimuthRadians);

                    azimuthangle.setText(String.valueOf(azimuthDegrees));
                    elevationangle.setText(String.valueOf(elevationDegrees));

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
                    // Calculates the azimuth and elevation angles for a satellite at a given orbital position and user location
        });


    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MainActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude=location.getLatitude();
        double longitude=location.getLongitude();

        lati.setText(String.valueOf(latitude));
        longi.setText(String.valueOf(longitude));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void compassbtn(View view) {
        Intent intent=new Intent(this,CompassActivity.class);
        startActivity(intent);

        getCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitudec=lati.getText().toString();
                String longitudec=longi.getText().toString();
                String orbitalpositionc=op.getText().toString();
                String azimuthc=azimuthangle.getText().toString();
                String elevationc=elevationangle.getText().toString();

            }
        });

    }
}
