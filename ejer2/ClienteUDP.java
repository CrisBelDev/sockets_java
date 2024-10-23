/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejer2;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) throws IOException {
        // CREACION DEL SOCKET CLIENTE
        DatagramSocket socketCliente = new DatagramSocket();
        InetAddress direccionServidor = InetAddress.getByName("localhost"); // Cambia "localhost" por la IP del servidor si es necesario
        int puerto = 9876;

        // BUFFER PARA RECIBIR LOS DATOS DEL SERVIDOR
        byte[] recibirDatos = new byte[1024];

        // VARIABLE PARA ENVIAR DATOS AL SERVIDOR
        byte[] enviarDatos;

        Scanner sc = new Scanner(System.in);

        while (true) {
            // Lectura de datos del cliente
            System.out.print("Ingrese un numero: ");
            String mensaje = sc.nextLine();

            try {
                // Verifica si es un numero entero
                int numero = Integer.parseInt(mensaje);
                
                // Enviar el numero al servidor
                enviarDatos = mensaje.getBytes();
                
                // Creacion de un paquete UDP para enviarlo al servidor
                DatagramPacket paqueteEnviar = new DatagramPacket(enviarDatos, enviarDatos.length, direccionServidor, puerto);
                
                // Enviar el paquete al servidor
                socketCliente.send(paqueteEnviar);

                // Creacion del paquete para recibir datos del servidor
                DatagramPacket paqueteRecibido = new DatagramPacket(recibirDatos, recibirDatos.length);
                socketCliente.receive(paqueteRecibido);

                // Convertir los datos recibidos en una cadena de texto
                String mensajeServidor = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());

                // Mostrar la respuesta del servidor
                System.out.println("Servidor: " + mensajeServidor);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido.");
            }
        }
    }
}
