package com.example.chart;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Principal extends Activity {
    EditText inputDate, inputTime, inputName, inputCity;
    ImageView iconDate, iconTime;
    Button buttonGenerate;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_form);

        inputDate = findViewById(R.id.inputDate);
        inputTime = findViewById(R.id.inputTime);
        inputName = findViewById(R.id.inputName);
        inputCity = findViewById(R.id.inputCity);
        iconDate = findViewById(R.id.iconDate);
        iconTime = findViewById(R.id.iconTime);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        textResult = findViewById(R.id.textResult);

        // Abrir diálogo de seleção de data
        View.OnClickListener dateClickListener = v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(Principal.this, (view, year1, month1, dayOfMonth) -> {
                inputDate.setText(dayOfMonth + "|" + (month1 + 1) + "|" + year1);
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
                inputTime.setText(String.format("%02d|%02d", hourOfDay, minute1));
            }, hour, minute, true).show();
        };

        inputTime.setOnClickListener(timeClickListener);
        iconTime.setOnClickListener(timeClickListener);

        // Configurar botão para fazer a requisição e exibir o resultado
        buttonGenerate.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String city = inputCity.getText().toString();
            String date = inputDate.getText().toString();
            String time = inputTime.getText().toString();

            // Verificar se todos os campos estão preenchidos
            if (name.isEmpty() || city.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(Principal.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Montar a URL com os parâmetros
            String url = "https://api.astrologico.org/v1/chart?localdate=" + date +"|"+ time + "&querylocation=" + city + "&houses=15&key=4a49c78861a5d1aff676183d7483fc66fd18bbb3186186a18931640c";
            fazerRequisicaoApi(url);
            Toast.makeText(this, "Olá, mundo! " + url, Toast.LENGTH_SHORT).show();
        });
    }

    private void fazerRequisicaoApi(String url) {
        ApiClient.fazerRequisicaoGET(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(Principal.this, "Falha na requisição", Toast.LENGTH_SHORT).show()
                );
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String resposta = response.body().string();
                    runOnUiThread(() -> {
                        // Iniciar ChartActivity e passar o JSON como extra
                        Intent intent = new Intent(Principal.this, ChartActivity.class);
                        intent.putExtra("json_data", resposta);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(Principal.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show()
                    );
                }
            }

        });
    }
}
