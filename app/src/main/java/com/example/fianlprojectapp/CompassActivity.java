package com.example.fianlprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView compassImage;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] gravity;
    private float[] geomagnetic;
    float latitude;
    float longitude;
    float orbitalPosition;
    float azimuth;
    float elevation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compassactivity);

        latitude=getIntent().getExtras().getFloat("Latitude");
        longitude=getIntent().getExtras().getFloat("Longitude");
        orbitalPosition=getIntent().getExtras().getFloat("OrbitalPosition");
        azimuth=getIntent().getExtras().getFloat("AzimuthAngle");
        elevation=getIntent().getExtras().getFloat("ElevationAngle");
        compassImage=findViewById(R.id.compass);


        //Initialize sensors

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener((SensorEventListener) this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener((SensorEventListener) this);
    }


    public void onSensorChanged(SensorEvent event) {
        //Get the device's current orientation
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;
        if (gravity != null && geomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0]; // azimuth
                elevation = orientation[1]; // elevation
                orbitalPosition = orientation[2]; // orbital position
            }
        }

        //Update the UI
        updateUI();
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do nothing
    }

    private void updateUI() {
        //Update compass image
        int rotation = (int) (azimuth * 360 / (2 * Math.PI));
        compassImage.setRotation(-rotation);


    }
    public void backbtn(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
