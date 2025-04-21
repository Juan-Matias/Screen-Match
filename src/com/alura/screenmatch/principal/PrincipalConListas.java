package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.Pelicula;
import com.alura.screenmatch.modelos.Serie;
import com.alura.screenmatch.modelos.Titulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrincipalConListas
{
    public static void main(String[] args) {
        Pelicula miPelicula = new Pelicula("Encanto",2021);
        miPelicula.evalua(9);

        Pelicula otraPelicula = new Pelicula("Avatar",2023);
        otraPelicula.evalua(9);

        var peliculaDeBruno = new Pelicula("El señor de los anillos",2002);
        peliculaDeBruno.evalua(9);

        Serie lost = new Serie("Lost",2000);
        lost.evalua(9);

        ArrayList<Titulo> lista = new ArrayList<>();
        lista.add(miPelicula);
        lista.add(otraPelicula);
        lista.add(peliculaDeBruno);
        lista.add(lost);


        for (Titulo item:lista) {
            System.out.println(item.getNombre());
            if (item instanceof Pelicula pelicula && pelicula.getClasificacion() >2) {
                System.out.println(pelicula.getClasificacion());
            }
        }

        /*
        lista.forEach(item -> {
            if (item instanceof Pelicula pelicula) {
                System.out.println(pelicula.getClasificacion());
            }
        });
        */

        ArrayList<String> listaDeArtistas = new ArrayList<>();
        listaDeArtistas.add("Penélope Cruz");
        listaDeArtistas.add("Antonio Banderas");
        listaDeArtistas.add("Armando Barreras");
        listaDeArtistas.add("Ricardo Darin");

        Collections.sort(listaDeArtistas);
        System.out.println("Lista ordenada por artista:"+ listaDeArtistas);

        Collections.sort(lista);
        System.out.println("Lista ordenada por titulo:"+ lista);

        lista.sort(Comparator.comparing(Titulo::getFechaDeLanzamiento));
        System.out.println("Lista ordenada por fecha"+ lista);

    }
}
