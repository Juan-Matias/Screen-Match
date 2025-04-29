package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda
{
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Escribe el nombre de una pelicula : ");
        var busqueda = lectura.nextLine();
        String direccion = "https://www.omdbapi.com/?t="+busqueda+"&apikey=42818475";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());


        String json = response.body( );

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        TituloOmdb mitituloOmdb = gson.fromJson(json, TituloOmdb.class);

        try {
            Titulo mititulo = new Titulo(mitituloOmdb);
            System.out.println(mitituloOmdb);
        }catch (NumberFormatException e){
            System.out.println("Ocurrio un error: ");
            System.out.println(e.getMessage());
        }
        System.out.println("Finalizo la ejecucion del programa !");
    }
}
