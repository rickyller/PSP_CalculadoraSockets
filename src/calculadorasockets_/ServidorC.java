/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadorasockets_;


/**
 *
 * @author slorenzorodriguez
 */

    import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author slorenzorodriguez
 */
public class ServidorC  extends Thread {

    private Socket clientSocket;

    public ServidorC(Socket socket) {
        clientSocket = socket;
    }

    public void run() {

        try {
            String unusedLoop=""; //Loop necesario para poder hacer + de una operación.
            while(unusedLoop.equals("")){
            System.out.println("Thread started.");

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            
            String message = "First number: ";
            os.write(message.getBytes());

            
            byte[] number = new byte[25];
            is.read(number);
            System.out.println("Message : " + new String(number));

            
            message = "Choose: + | - | / | * |";
            os.write(message.getBytes());

            
            byte[] operator = new byte[1];
            is.read(operator);
            System.out.println("Message received : " + new String(operator));

            
            String message1 = "Second number: ";
            os.write(message1.getBytes());

            
            byte[] number2 = new byte[25];
            is.read(number2);
            System.out.println("Message received : " + new String(number2));

            
            float result = operation(new Float(new String(number)), new Float(new String(number2)), new String(operator));
            message = String.valueOf(result);
            os.write(message.getBytes());

            System.out.println("Succesfull.");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("Making server socket ");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Setting Bind");

            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            serverSocket.bind(addr);

            System.out.println("Getting conections");

            while (serverSocket != null) {
                Socket newSocket = serverSocket.accept();
                System.out.println("Successful Connection");

                ServidorC hilo = new ServidorC(newSocket);
                hilo.start();
            }

            System.out.println("Connection received");
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


