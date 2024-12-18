package com.example.chart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ChartActivityHistory extends Activity {

    TextView chartDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chart); // Usa o layout_chart.xml

        // Referência ao TextView no layout
        chartDataView = findViewById(R.id.chartResult);

        // Recuperar os dados do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MapaAstralPrefs", MODE_PRIVATE);
        String savedData = sharedPreferences.getString("parsedData", "Nenhum histórico encontrado.");

        String header = "----------------------------------\n" +
                "🕰️ Histórico\n" +
                "----------------------------------\n\n";

        String fullMessage = header + savedData;

        // Exibir os dados no TextView
        chartDataView.setText(fullMessage);
    }
}
