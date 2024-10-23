package practica2;

import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000); // Cambiado a puerto 9000

        // Leer datos de entrada del servidor
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Enviar datos hacia el servidor
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Leer dos números del usuario
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese dos números separados por un espacio:");
        String inputLine = userInput.readLine(); // Leer la entrada del usuario
        out.writeUTF(inputLine); // Enviar la entrada del usuario al servidor

        // Leer y mostrar las respuestas del servidor
        try {
            for (int i = 0; i < 4; i++) { // Esperar 4 respuestas
                String respuesta = in.readUTF();
                System.out.println("El servidor dice: " + respuesta);
            }
        } catch (EOFException e) {
            System.out.println("El servidor ha cerrado la conexión.");
        }

        // Cerrar el socket
        socket.close();
    }
}
