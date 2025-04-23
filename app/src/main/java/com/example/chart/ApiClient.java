package com.example.chart;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ApiClient {
   //https://br.astrologico.org/software/api#examples

   //Cria o client, onde o uso do static e final é para garantir a criação apenas uma vez, para evitar múltipas conexões TCP
   private static final OkHttpClient client = new OkHttpClient();
   // Callback é uma interface java com dois métodos, o onResponse e onFailure
   public static void fazerRequisicaoGET(String url, Callback callback) {
      //Por padrão cria um metodo GET, caso necessário é possível alterar para POST, PUT...
      //Para usar POST por exemplo seria necessário criar um body usando JSON e passar com o POST. "post(body)"
      Request request = new Request.Builder()
              .url(url)
              .build();

      //newCall prepara a requisição HTTP, e enqueue é o metodo que executa ded forma assíncrona, passando o callback para receber uma resposta/erro
      client.newCall(request).enqueue(callback);
   }
}
