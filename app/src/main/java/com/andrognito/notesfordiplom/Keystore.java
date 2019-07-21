package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Keystore {

    private int pinCode;
    private Context context;

    public Keystore() {
    }

    public void savePin(Context context, int pin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Keys.SecretDirectory.getKey(), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Keys.Pin.getKey(), pin);
        editor.apply();
        Toast.makeText(context,R.string.pin_code_saved, Toast.LENGTH_SHORT).show();
    }

    public int getPin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Keys.SecretDirectory.getKey(), MODE_PRIVATE);
        int pinCode = sharedPreferences.getInt(Keys.Pin.getKey(), 0000);
        return pinCode;
    }
}
