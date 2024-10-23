/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejer1;

/**
 *
 * @author Cristian Abel
 */
import java.io.*;
import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) throws IOException {
        // CREACION DEL SOCKET SERVIDOR
        DatagramSocket socketServidor = new DatagramSocket(9876);

        // BUFFER PARA RECIBIR LOS DATOS DE LOS CLIENTES
        byte[] datos = new byte[1024];

        // VARIABLE PARA ENVIAR DATOS AL CLIENTE
        byte[] enviar;

        System.out.println("Servidor está escuchando en el puerto 9876...");

        while (true) {
            // Crear un paquete UDP para recibir datos de n clientes
            DatagramPacket paqueteRec = new DatagramPacket(datos, datos.length);

            // Recibir el paquete del cliente
            socketServidor.receive(paqueteRec);

            // Convertir los datos en una cadena de texto
            String mensaje = new String(paqueteRec.getData(), 0, paqueteRec.getLength());

            // Mostrar el mensaje recibido del cliente
            System.out.println("El cliente envía: " + mensaje);

            // Obtener la IP y el puerto del cliente
            InetAddress dirCliente = paqueteRec.getAddress();
            int puertoCliente = paqueteRec.getPort();

            // Verificar si el cliente quiere finalizar su conexión
            if (mensaje.equalsIgnoreCase("ADIOS")) {
                System.out.println("El cliente " + dirCliente + ":" + puertoCliente + " finalizó la conexión.");
                // Enviar una respuesta al cliente indicando que se cerrará la conexión
                String respDespedida = "Servidor dice: ¡Adiós, cliente!";
                enviar = respDespedida.getBytes();
                DatagramPacket paqueteDespedida = new DatagramPacket(enviar, enviar.length, dirCliente, puertoCliente);
                socketServidor.send(paqueteDespedida);
                // Continuar aceptando otros clientes sin cerrar el servidor
                continue;  // Volver al inicio del bucle para aceptar otros clientes
            }

            // Preparar la respuesta del servidor
            String resp = "Servidor dice: " + mensaje;

            // Transformar la cadena resp en bytes y almacenarlo en enviar
            enviar = resp.getBytes();

            // Creación de un paquete UDP y envío al cliente
            DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, dirCliente, puertoCliente);

            // Enviar la respuesta al cliente
            socketServidor.send(paqueteEnviar);
        }

        // El servidor ya no se cerrará aquí, ya que el bucle es infinito
    }
}
