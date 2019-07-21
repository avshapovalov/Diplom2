package com.andrognito.notesfordiplom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class PinCodeScreen extends AppCompatActivity {

    public static final String TAG = Keys.Log.getKey();

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private int pinCode;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {

            Keystore keystore = new Keystore();
            pinCode = keystore.getPin(PinCodeScreen.this);
            Log.d(TAG, R.string.pin_code_screen_pin_complete + pin);

            if (Integer.parseInt(pin) == pinCode) {
                Intent notesIntent = new Intent(PinCodeScreen.this, Notes.class);
                startActivity(notesIntent);
                finish();
            } else {
                Intent settingIntent = new Intent(PinCodeScreen.this, SettingsActivity.class);
                startActivity(settingIntent);
                finish();
            }

            Log.d(TAG, R.string.pin_code_screen_pin_complete + pin);

        }

        @Override
        public void onEmpty() {
            Log.d(TAG, getString(R.string.pin_code_screen_pin_empty));
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, R.string.pin_code_screen_pin_changed_new_length + pinLength + R.string.pin_code_screen_with_intermediate_pin + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Keystore keystore = new Keystore();
        pinCode = keystore.getPin(PinCodeScreen.this);

        if (pinCode == 0) {
            Intent settingIntent = new Intent(PinCodeScreen.this, SettingsActivity.class);
            startActivity(settingIntent);
            finish();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sample);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }
}
