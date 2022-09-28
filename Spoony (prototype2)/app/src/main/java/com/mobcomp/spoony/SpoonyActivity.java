package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;

import android.os.Bundle;

import com.mobcomp.spoony.databinding.ActivityMainBinding;

public class SpoonyActivity extends AppCompatActivity implements SensorEventListener {

    private static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL; // 200ms? the interval between sensor reports
    private static final float TABLE_THRESHOLD = -20.f; // y-bearing above which the phone is considered 'on the table'
    private static final float VIEW_DISTANCE = 60.0f; // degrees from player position that counts as being in their 'view'

    private AppBarConfiguration _appBarConfiguration;
    private ActivityMainBinding _binding;

    // fake state machine
    private SpoonyState _state = SpoonyState.DEFAULT;

    // orientation sensors
    private SensorManager _sensorManager;
    private final float[] _accelReading = new float[3];
    private final float[] _magReading = new float[3];

    // orientation calculation
    private final float[] _rotationMatrix = new float[9];
    private final float[] _deviceOrientationRadians = new float[3];
    public float[] deviceOrientation = new float[3];

    // player positions
    private SharedPreferences _data;
    private float _p1Position = 0.0f;
    private float _p2Position = 180.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        _data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);

        _p1Position = _data.getFloat(Key.P1_POSITION, 0.0f);
        _p2Position = _data.getFloat(Key.P2_POSITION, 0.0f);
    }

    // this gets called on creation (later than onCreate), or when the user returns to the app after minimising it
    @Override
    protected void onResume() {
        super.onResume();

        Sensor accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetometer = _sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (accelerometer != null) {
            _sensorManager.registerListener(this, accelerometer, SENSOR_DELAY, SENSOR_DELAY);
        }

        if (magnetometer != null) {
            _sensorManager.registerListener(this, magnetometer, SENSOR_DELAY, SENSOR_DELAY);
        }
    }

    // this is called on app close or on minimise, to turn off sensing when not required
    @Override
    protected void onPause() {
        super.onPause();

        // this unsubscribes from ALL sensors
        _sensorManager.unregisterListener(this);
    }

    // this gets called when EITHER sensor changes - do we want to update the values every time?
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // check which sensor has been updated
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // accelerometer update
            System.arraycopy(sensorEvent.values, 0 , _accelReading, 0, _accelReading.length);

        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // magnetometer update
            System.arraycopy(sensorEvent.values, 0 , _magReading, 0, _magReading.length);

            // only update on mag change
            SensorManager.getRotationMatrix(_rotationMatrix, null, _accelReading, _magReading); // uses gravity and geo readings to calculate orientation vectors
            SensorManager.getOrientation(_rotationMatrix, _deviceOrientationRadians); // convert orientation vectors into radians
            deviceOrientation[0] = (float) Math.toDegrees(_deviceOrientationRadians[0]);
            deviceOrientation[1] = (float) Math.toDegrees(_deviceOrientationRadians[1]);
            deviceOrientation[2] = (float) Math.toDegrees(_deviceOrientationRadians[2]);

            // TODO: just use radians in state calculation?

            setState(deviceOrientation);
            update();
        }
    }

    private void setState(float[] orientation) {

        // phone is within table surface rotation limit
        if (orientation[1] > TABLE_THRESHOLD) {
            if (_state != SpoonyState.TABLE) {
                exitState();
                _state = SpoonyState.TABLE;
                onEnterTable();
            }
        }

        // phone is within VIEW_DISTANCE degrees of player 1's position
        else if (rotationDistance(orientation[0], _p1Position) < VIEW_DISTANCE) {
            if (_state != SpoonyState.P1_VIEW) {
                exitState();
                _state = SpoonyState.P1_VIEW;
                onEnterP1View();
            }
        }

        // phone is within VIEW_DISTANCE degrees of player 2's position
        else if (rotationDistance(orientation[0], _p2Position) < VIEW_DISTANCE) {
            if (_state != SpoonyState.P2_VIEW) {
                exitState();
                _state = SpoonyState.P2_VIEW;
                onEnterP2View();
            }
        }

        // none of the above, drop back to default
        else {
            exitState();
            _state = SpoonyState.DEFAULT;
            onEnterDefault();
        }
    }

    // calculates distance between two angles
    private float rotationDistance(float a, float b) {
        return 180 - Math.abs((Math.abs(a - b) % 360) - 180);
    }

    private void exitState() {
        switch (_state) {
            case TABLE:
                onExitTable();
            case P1_VIEW:
                onExitP1View();
            case P2_VIEW:
                onExitP2View();
            default:
                onExitDefault();
        }
    }

    private void update() {

        updateAlways();

        switch (_state) {
            case TABLE:
                updateTable();
            case P1_VIEW:
                updateP1View();
            case P2_VIEW:
                updateP2View();
            default:
                updateDefault();
        }
    }

    public SpoonyState getState() {
        return _state;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {} // do nothing?

    protected void onEnterP1View() {}
    protected void updateP1View() {}
    protected void onExitP1View() {} // exit methods possibly unnecessary

    protected void onEnterP2View() {}
    protected void updateP2View() {}
    protected void onExitP2View() {}

    protected void onEnterTable() {}
    protected void updateTable() {}
    protected void onExitTable() {}

    protected void onEnterDefault() {}
    protected void updateDefault() {}
    protected void onExitDefault() {}

    protected void updateAlways() {}
}