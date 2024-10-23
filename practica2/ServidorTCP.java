package practica2;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        // Creación del objeto ServerSocket en el puerto 9000
        ServerSocket servidor = new ServerSocket(9000);
        System.out.println("Servidor TCP está escuchando en el puerto 9000");

        while (true) {
            // Conexión inicial, conexión con cada cliente
            Socket socket = servidor.accept();

            // Leer datos de entrada
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Enviar datos a los clientes
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Leer los datos enviados por el cliente
            String mensaje = in.readUTF();
            System.out.println("El cliente manda: " + mensaje);

            // Procesar los números recibidos
            String[] numeros = mensaje.split(" ");
            if (numeros.length == 2) {
                try {
                    double num1 = Double.parseDouble(numeros[0]);
                    double num2 = Double.parseDouble(numeros[1]);

                    // Calcular operaciones
                    double suma = num1 + num2;
                    double resta = num1 - num2;
                    double multiplicacion = num1 * num2;
                    double division = (num2 != 0) ? num1 / num2 : Double.NaN; // Manejo de división por cero

                    // Enviar las respuestas al cliente
                    out.writeUTF("Suma: " + suma);
                    out.writeUTF("Resta: " + resta);
                    out.writeUTF("Multiplicación: " + multiplicacion);
                    out.writeUTF("División: " + (division != Double.NaN ? division : "Error: División por cero"));
                } catch (NumberFormatException e) {
                    out.writeUTF("Error: Entrada no válida. Asegúrese de enviar dos números.");
                }
            } else {
                out.writeUTF("Error: Se requieren dos números.");
            }

            // CERRAR LAS CONEXIONES 
            socket.close();
        }
        // servidor.close(); // No es necesario aquí, ya que el servidor sigue escuchando
    }
}
