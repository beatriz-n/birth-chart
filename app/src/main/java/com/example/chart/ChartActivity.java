package com.example.chart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ChartActivity extends Activity {
    TextView chartDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chart);

        chartDataView = findViewById(R.id.chartResult);

        // Receber o JSON passado pelo Intent
        String jsonString = getIntent().getStringExtra("json_data");

        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String parsedData = parseJson(jsonObject);
                chartDataView.setText(parsedData);
            } catch (JSONException e) {
                Toast.makeText(this, "Erro ao carregar JSON", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Nenhum dado recebido", Toast.LENGTH_SHORT).show();
        }
    }

    private String parseJson(JSONObject jsonObject) throws JSONException {
        StringBuilder result = new StringBuilder();

        result.append("🌌 Seu Mapa Astral\n\n");
        // Extrair Metadados
        JSONObject metadata = jsonObject.getJSONObject("metadata");
        JSONObject location = metadata.getJSONObject("location");
        result.append("📍 Informações da Localização\n")
                .append("    Localização: ").append(location.getString("queryResult")).append("\n")
                .append("    Latitude: ").append(location.getDouble("latitude")).append("\n")
                .append("    Longitude: ").append(location.getDouble("longitude")).append("\n\n");

        // Data e Hora
        JSONObject date = metadata.getJSONObject("date");
        result.append("🗓️ Informações da Data e Hora\n")
                .append("    Data Local: ").append(date.getJSONObject("localDate").getString("day"))
                .append("/").append(date.getJSONObject("localDate").getString("month"))
                .append("/").append(date.getJSONObject("localDate").getString("year")).append("\n")
                .append("    Hora Local: ").append(date.getJSONObject("localDate").getString("hour"))
                .append(":").append(date.getJSONObject("localDate").getString("minute")).append("\n")
                .append("    Fuso Horário: Brasília Standard Time (-03:00)\n")
                .append("    Calendário: Gregoriano\n\n");

        // Posições Astrológicas
        JSONObject planets = jsonObject.getJSONObject("planets");
        result.append("🌞 Posições Astrológicas Principais\n");
        result.append("    Sol: ").append(planets.getJSONObject("P0").getDouble("longitude")).append("°\n");
        result.append("    Lua: ").append(planets.getJSONObject("P1").getDouble("longitude")).append("°\n");
        result.append("    Ascendente: ").append(planets.getJSONObject("P2").getDouble("longitude")).append("°\n");
        result.append("    Descendente: ").append(planets.getJSONObject("P7").getDouble("longitude")).append("°\n\n");

        // Casas Astrológicas
        JSONObject houses = jsonObject.getJSONObject("houses").getJSONObject("houses");
        result.append("🏠 Casas Astrológicas\n");
        for (int i = 1; i <= 12; i++) {
            JSONObject house = houses.getJSONObject("house" + i);
            result.append("Casa ").append(i).append(" - Longitude: ").append(house.getDouble("longitude")).append("\n");
        }

        return result.toString();
    }

    // Função para formatar títulos
    private String formatTitle(String title) {
        return "\n" + title + ":\n" + "-------------------------";
    }
}
