package network;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;

import network.client.Client;
import network.ressource.Ressource;

import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkNode {

    private final int identifier;                                           // value for Server
    private final int tcpPort;                                              // tcpPort where the Server is reachable
    private final String gatewayIP;                                         // the Ip of the gateway
    private final int gatewayPort;                                          // the port of the gateway

    private HashMap<String, Integer> ressources;                  // List of all Ressources of the servers
    private List<Client> clients = new ArrayList<>();


    public NetworkNode(int identifier, int tcpPort, String gatewayIP, int gatewayPort, HashMap<String, Integer> ressources) {
        this.identifier = identifier;                                            // Assigns the server number to the current created object.
        this.tcpPort = tcpPort;                                                  // Assigns the server tcpPort to the current created object.
        this.gatewayIP = gatewayIP;                                              // Assigns the server gatewayIP to the current created object.
        this.gatewayPort = gatewayPort;                                          // Assigns the gatewayPort number to the current created object.
        this.ressources = ressources;                                            // Assigns the server ressources to the current created object.
    }


    public void startServer() {                                                      // starts the server now
        ServerSocket server;
        try {
            System.out.println("Listening at port " + tcpPort);                      // Returns the tcpPort of the this server
            server = new ServerSocket(tcpPort);                                      // creates a server with this tcpPort
            while (true) {
                Socket socket;
                try {
                    socket = server.accept();                                         // accept the socket connection
                    System.out.println("New connection: " + socket.getInetAddress() + ":" + socket.getPort());                // Returns the connection
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));                // makes a new BufferedReader Object, which reads every interaction with this server
                    PrintStream output = new PrintStream(socket.getOutputStream());                                           // makes a new PrintStream Object, which prints every interaction
                    String message = input.readLine();
                    System.out.println(message);

                    boolean error = false;                                                                        // Looks for errors, which are in connection with the server
                    StringBuilder response = new StringBuilder("ALLOCATED ");

                    Client client = new Client(Integer.parseInt(message.split(" ")[0]), new HashMap<>());   // creates a new Client with a number a Hashmap of ressources.
                    HashMap<String, Integer> availableRessources = ressources;                                    // if everything is correct, the ressources are occupied
                    if(message.split(" ")[1].equals("terminate")){                                          // if the keyword terminate is somewhere the connection breaks
                        output.println("TERMINATED");
                        output.flush();

                    }else {
                        for (int i = 1; i < message.split(" ").length; i++) {                              // the commands were split
                            String[] req = message.split(" ")[i].split(":");
                            String name = req[0];
                            int ressourceSize = Integer.parseInt(req[1]);

                            if (availableRessources.get(name) != null && availableRessources.get(name) >= ressourceSize) {          // the ressources are the return value
                                response.append(name).append(":").append(availableRessources.get(name)).append(" ");                // every ressource is connected with a client
                                availableRessources.put(name, (availableRessources.get(name) - ressourceSize));
                                client.getRessources().put(name, ressourceSize);                                                    // the ressources were put in the client object
                            } else {
                                error = true;
                            }
                            if (message.split(" ").length == i && !error) {
                                clients.add(client);
                                this.ressources = availableRessources;
                            }
                        }
                        if (error) {
                            output.println("FAILED");
                        } else {
                            response.append(server.getLocalSocketAddress());
                            output.println(response);
                        }
                        output.flush();                                        // everything close
                        output.close();                                        // everything close
                        input.close();                                         // everything close
                        socket.close();                                        // everything close
                    }

                } catch (IOException e) {                               // A try catch for exceptions
                    e.printStackTrace();                                // if something went wrong a printStackTrace() is printed
                }
            }
        } catch (IOException e) {                                       // A try catch for exceptions
            e.printStackTrace();                                        // if something went wrong a printStackTrace() is printed
        }
    }
}
