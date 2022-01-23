package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient {

    private final String ip;                                                            // ip of the client
    private final int port;                                                             // port with which the client connects
    private final int identifier;                                                       // identifier of the server (nubmer of the server)
    private String command;                                                             // ressources


    public NetworkClient(String ip, int port, int identifier, String command) {
        this.ip = ip;                                               // Assigns the server IP to the current created object.
        this.port = port;                                           // Assigns the Port number to the current created object.
        this.identifier = identifier;                               // Assigns the server number to the current created object.
        this.command = command;                                     // Assigns the server ressources to the current created object.
        System.out.println("IP: " + ip + " Port:" + port + " Ident:" + identifier + " CMD:" + command);                 // Message of the connection

    }
    
    
    public void connect(){
        Socket netSocket;            // netSocket
        PrintWriter out;             // out
        BufferedReader in;           // in
        try {
            System.out.println("Connecting with: " + ip + " at port " + port);
            netSocket = new Socket(ip, port);
            out = new PrintWriter(netSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(netSocket.getInputStream()));
            System.out.println("Connected");

            if (!"TERMINATE".equals(command)) {                       // if not terminate the process is going on
                command = identifier + " " + command;
            }
            System.out.println("Sending: " + command);               // prints sending command(ressources and amount)
            out.println(command);                                    // prints the sending command
            // Read and print out the response
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }

            // Terminate - close all the streams and the socket
            out.close();                                             // everything close
            in.close();                                              // everything close
            netSocket.close();                                       // everything close
            System.exit(0);                                   // everything close
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + ip + ".");         // Error is printed
            System.exit(1);                                   // exit the system
        } catch (IOException e) {
            System.err.println("No connection with " + ip + ".");    // no connection is available
            System.exit(1);                                   // exit the system
        }

    }
}
