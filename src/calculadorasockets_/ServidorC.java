package calculadorasockets_;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorC  extends Thread {

    private Socket clientSocket;

    public ServidorC(Socket socket) {
        clientSocket = socket;
    }

    public void run() {

        try {
            String unusedLoop=""; //Loop necesario para poder hacer + de una operación.
            while(unusedLoop.equals("")){
            System.out.println("Hilo inicializado.");

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            
            String message = "Primer numero: ";
            os.write(message.getBytes());

            
            byte[] number = new byte[25];
            is.read(number);
            System.out.println("Mensaje : " + new String(number));

            
            message = "Elige: + | - | / | * |";
            os.write(message.getBytes());

            
            byte[] operator = new byte[1];
            is.read(operator);
            System.out.println("Operador : " + new String(operator));

            
            String message1 = "Segundo numero: ";
            os.write(message1.getBytes());

            
            byte[] number2 = new byte[25];
            is.read(number2);
            System.out.println("Numero recibido : " + new String(number2));

            
            float result = operation(new Float(new String(number)), new Float(new String(number2)), new String(operator));
            message = String.valueOf(result);
            os.write(message.getBytes());

            System.out.println("Exitoso.");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("Haciendo el socket");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Estableciendo el puerto");

            InetSocketAddress addr = new InetSocketAddress("192.168.1.11", 5555);
            serverSocket.bind(addr);

            System.out.println("Obteniendo la conexión");

            while (serverSocket != null) {
                Socket newSocket = serverSocket.accept();
                System.out.println("Conexión recibida");

                ServidorC hilo = new ServidorC(newSocket);
                hilo.start();
            }

            System.out.println("Conexión terminada");
        } catch (IOException ex) {
            Logger.getLogger(ServidorC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static float operation(float number, float number2, String operator) {
        float result = 0;

        switch (operator) {
            case "*":
                result = number * number2;
                break;

            case "/":
                result = number / number2;
                break;

            case "+":
                result = number + number2;
                break;

            case "-":
                result = number - number2;
                break;

        }
        return result;
    }
}


