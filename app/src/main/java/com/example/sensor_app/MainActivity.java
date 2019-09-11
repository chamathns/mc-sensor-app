package com.example.sensor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.hardware.SensorManager.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;

    TextView xValue, yValue, zValue;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = (TextView)findViewById(R.id.xValue);
        yValue = (TextView)findViewById(R.id.yValue);
        zValue = (TextView)findViewById(R.id.zValue);

        button = (Button)findViewById(R.id.button);
        button.setTag(1);
        button.setText("stop");

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,accelerometer, SENSOR_DELAY_NORMAL);

        button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                final int state = (Integer)view.getTag();
                if (state == 1) {
                    sensorManager.unregisterListener(MainActivity.this, accelerometer);
                    Log.d(TAG, "onClick: Unregistered accelerometer listener");
                    button.setText("start");
                    view.setTag(0);
                }
                else {
                    sensorManager.registerListener(MainActivity.this,accelerometer, SENSOR_DELAY_NORMAL);
                    Log.d(TAG, "onClick: Registered accelerometer listener");
                    button.setText("stop");
                    view.setTag(1);
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] +
                " Y: " + sensorEvent.values[1] + " Z: "+ sensorEvent.values[2]);

        xValue.setText("xValue: " + sensorEvent.values[0]);
        yValue.setText("yValue: " + sensorEvent.values[1]);
        zValue.setText("zValue: " + sensorEvent.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
