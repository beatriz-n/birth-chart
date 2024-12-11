package com.example.chart;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Calendar;

public class Principal extends Activity {
    EditText inputDate, inputTime;
    ImageView iconDate, iconTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_form);

        inputDate = findViewById(R.id.inputDate);
        inputTime = findViewById(R.id.inputTime);
        iconDate = findViewById(R.id.iconDate);
        iconTime = findViewById(R.id.iconTime);

        // Abrir diálogo de seleção de data
        View.OnClickListener dateClickListener = v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(Principal.this, (view, year1, month1, dayOfMonth) -> {
                inputDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
            }, year, month, day).show();
        };

        inputDate.setOnClickListener(dateClickListener);
        iconDate.setOnClickListener(dateClickListener);

        // Abrir diálogo de seleção de hora
        View.OnClickListener timeClickListener = v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(Principal.this, (view, hourOfDay, minute1) -> {
                inputTime.setText(String.format("%02d:%02d", hourOfDay, minute1));
            }, hour, minute, true).show();
        };

        inputTime.setOnClickListener(timeClickListener);
        iconTime.setOnClickListener(timeClickListener);
    }
}
