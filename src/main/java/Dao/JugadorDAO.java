/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Jugador;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author eliana
 */
public class JugadorDAO {
    private final String archivo = "/home/eliana/NetBeansProjects/Dao_Me/src/main/java/Data/Jugadores.json";

    private final ObjectMapper mapper;

    public JugadorDAO() {
        mapper = new ObjectMapper();
    }

    public List<Jugador> display() throws IOException {

        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("ERROR!!");
            return new ArrayList<>();
        }
        return mapper.readValue(
                file,
                new TypeReference<List<Jugador>>() {
        }
        );
    }
    private void save(List<Jugador> jugadores) throws IOException {
        mapper.writerWithDefaultPrettyPrinter() 
            .writeValue(new File(archivo), jugadores);
    }
    public void create(Jugador jugador) throws IOException {
        List<Jugador> jugadores = display();
        jugadores.add(jugador);
        save(jugadores);
    }
}
