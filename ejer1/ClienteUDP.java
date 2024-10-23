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
import java.util.*;

public class ClienteUDP{
	public static void main (String[] args) throws IOException{
		// CREACION DEL SOCKET SERVIDOR
		DatagramSocket socketCliente = new DatagramSocket();
		InetAddress direccionServidor = InetAddress.getByName("localhost");
		int puerto = 9876;

		// BUFFER PARA RECIBIR LOS DATOS DE LOS CLIENTES
		byte[] recibirDatos = new byte[1024];

		// VARIABLE PARA ENVIAR DATOS AL CLIENTE
		byte[] enviarDatos;

		Scanner sc = new Scanner(System.in);

		while(true){
			// Lectura de datos del cliente
			System.out.print("Cliente: ");
			String mensaje = sc.nextLine();

			// Enviar datos al servidor
			enviarDatos = mensaje.getBytes();

			// Creacion de un paquete UDP para enviuarlo al servidor
			DatagramPacket paqueteEnviar = new DatagramPacket(enviarDatos, enviarDatos.length, direccionServidor, puerto);

			socketCliente.send(paqueteEnviar);
			if(mensaje.equals("ADIOS"))
			{
				break;
			}

			// Creacion del paquete que se ha recibido desde el servidor
			DatagramPacket paqueteRecibido = new DatagramPacket(recibirDatos, recibirDatos.length);
			socketCliente.receive(paqueteRecibido);

			// Convertir los datos en una cadena de texto
                        String mensajeServidor = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());

			System.out.println(mensajeServidor);

		}
		socketCliente.close();
	}
}

