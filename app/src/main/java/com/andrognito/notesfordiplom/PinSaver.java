package com.andrognito.notesfordiplom;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class PinSaver {

    private int pinCode;
    private Context context;

    public PinSaver() {
    }

    public void savePin(Context context, int pin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("secretDirectory", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pin", pin);
        editor.apply();
        Toast.makeText(context, "Пин-код сохранен", Toast.LENGTH_SHORT).show();
    }

    public int getPin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("secretDirectory", MODE_PRIVATE);
        int pinCode = sharedPreferences.getInt("pin", 0000);
        return pinCode;
    }
}
