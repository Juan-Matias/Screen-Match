package com.alura.screenmatch.principal;

import com.alura.screenmatch.excpecion.ErrorEnConversionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        List<Titulo> titulos = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setPrettyPrinting()
                .create();

        while (true) {
            System.out.println("Escribe el nombre de una película (o 'salir' para terminar): ");
            String busqueda = lectura.nextLine();

            if (busqueda.equalsIgnoreCase("salir"))
                break;

            try {
                String busquedaCodificada = URLEncoder.encode(busqueda, StandardCharsets.UTF_8);
                String direccion = "https://www.omdbapi.com/?t=" + busquedaCodificada + "&apikey=42818475";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

                if (jsonObject.has("Response") && jsonObject.get("Response").getAsString().equalsIgnoreCase("False")) {
                    System.out.println("Película no encontrada: " + jsonObject.get("Error").getAsString());
                    continue;
                }

                TituloOmdb mitituloOmdb = gson.fromJson(json, TituloOmdb.class);
                Titulo mititulo = new Titulo(mitituloOmdb);

                System.out.println("Título convertido: " + mititulo);
                titulos.add(mititulo);

            } catch (IOException | InterruptedException e) {
                System.out.println("Error de red o interrupción durante la solicitud HTTP.");
                System.out.println("Detalle: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir números en la respuesta.");
                System.out.println("Detalle: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error en la URI. Verifique la dirección.");
                System.out.println("Detalle: " + e.getMessage());
            } catch (ErrorEnConversionException e) {
                System.out.println(e.getMensaje());
                System.out.println("Detalle: " + e.getMessage());
            }
        }

        // Escritura del archivo JSON
        try (FileWriter escritura = new FileWriter("titulos.json")) {
            escritura.write(gson.toJson(titulos));
            System.out.println("Archivo 'titulos.json' guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON.");
            System.out.println("Detalle: " + e.getMessage());
        }

        System.out.println("Finalizó la ejecución del programa.");
    }
}
