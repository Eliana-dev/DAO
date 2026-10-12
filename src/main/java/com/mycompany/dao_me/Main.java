/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao_me;

import Dao.JugadorDAO;
import Model.Jugador;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eliana
 */
public class Main {
    public static void main(String[] args) {
    }
    public static void prueba(){
       try {
           JugadorDAO dao = new JugadorDAO();
           
           List<Jugador> jugadores = dao.display();
           for (Jugador jugador : jugadores){
               System.out.println(jugador);
           }
       } catch (IOException e){
           e.printStackTrace();
           System.out.println("Fallo la apertura del archivo json");
           
       }
    }
}
