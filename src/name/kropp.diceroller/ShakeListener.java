package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener {
    private OnShakeListener myShakeListener;

    private SensorManager mySensorManager;
    private float myAccel = 0f; // acceleration apart from gravity
    private float myAccelCurrent = SensorManager.GRAVITY_EARTH; // current acceleration including gravity
    private float myAccelLast = SensorManager.GRAVITY_EARTH; // last acceleration including gravity
    private long myLastShake;

    public interface OnShakeListener {
        public void onShake();
    }

    public ShakeListener(Context context) {
        mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        resume();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        myShakeListener = listener;
    }


    public void resume() {
        mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause() {
        mySensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent se) {
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        myAccelLast = myAccelCurrent;
        myAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = myAccelCurrent - myAccelLast;
        myAccel = myAccel * 0.9f + delta; // perform low-cut filter

        if (myAccel > 1.8 && myShakeListener != null && (System.currentTimeMillis() - myLastShake > 1000)) {
            myLastShake = System.currentTimeMillis();
            myShakeListener.onShake();
        }
    }
}