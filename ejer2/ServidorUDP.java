package ejer2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServidorUDP {
    public static void main(String[] args) throws IOException {
        // CREACION DEL SOCKET SERVIDOR
        DatagramSocket socketServidor = new DatagramSocket(9876);

        // BUFFER PARA RECIBIR LOS DATOS DE LOS CLIENTES
        byte[] datos = new byte[1024];

        // VARIABLE PARA ENVIAR DATOS AL CLIENTE
        byte[] enviar;

        System.out.println("Servidor esta escuchando en el puerto 9876...");

        while (true) {
            // Crear un paquete UDP para recibir datos de n clientes
            DatagramPacket paqueteRec = new DatagramPacket(datos, datos.length);

            // Recibir el paquete del cliente
            socketServidor.receive(paqueteRec);

            // Convertir los datos en una cadena de texto
            String mensaje = new String(paqueteRec.getData(), 0, paqueteRec.getLength());

            // Verificar si el mensaje es un número
            int numero;
            try {
                numero = Integer.parseInt(mensaje);
            } catch (NumberFormatException e) {
                // Enviar mensaje de error si no es un número
                String errorMsg = "Por favor, ingrese un numero valido.";
                enviar = errorMsg.getBytes();
                InetAddress dirCliente = paqueteRec.getAddress();
                int puertoCliente = paqueteRec.getPort();
                DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, dirCliente, puertoCliente);
                socketServidor.send(paqueteEnviar);
                continue;
            }

            // Verificar si el número es primo
            if (esPrimo(numero)) {
                List<Integer> primos = listaPrimosHasta(numero);
                int suma = primos.stream().mapToInt(Integer::intValue).sum();
                String resp = "El numero " + numero + " es primo y la suma de todos los primos desde 2 a " + numero + " es: " + suma
                        + "\nLa lista de numeros es: " + primos.toString();
                enviar = resp.getBytes();
            } else {
                String resp = "El numero " + numero + " no es primo.";
                enviar = resp.getBytes();
            }

            // Obtener la IP y el puerto del cliente
            InetAddress dirCliente = paqueteRec.getAddress();
            int puertoCliente = paqueteRec.getPort();

            // Creacion de un paquete UDP y envio al cliente
            DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, dirCliente, puertoCliente);
            socketServidor.send(paqueteEnviar);
        }
    }

    // Funcion para verificar si un numero es primo
    public static boolean esPrimo(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Funcion para generar la lista de numeros primos hasta n
    public static List<Integer> listaPrimosHasta(int n) {
        List<Integer> primos = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (esPrimo(i)) {
                primos.add(i);
            }
        }
        return primos;
    }
}
