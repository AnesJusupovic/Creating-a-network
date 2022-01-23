import network.NetworkClient;
import network.NetworkNode;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("NetworkClient")) {         // looks if the command starts with NetworkClient
            String ip = "";                            // ip is ""
            int port = 0;                              // port is 0
            int ident = 0;                             // ident is 0
            String command = null;
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "-gateway":                                       // looks if keyword gateway is there
                        String[] address = args[++i].split(":");    // if yes split the the two commands after it
                        ip = address[0];                                  // ip comes after this
                        port = Integer.parseInt(address[1]);              // port comes after this
                        break;
                    case "-ident":                                        // looks if keyword ident is there
                        ident = Integer.parseInt(args[++i]);              // reads the ident
                        break;
                    case "terminate":                                      // looks if keyword terminate is there
                        command = "terminate";                             // command is terminate
                        break;
                    default:
                        if (command == null) {                            // looks if command is null
                            command = args[i];
                        } else if (!"TERMINATE".equals(command)) {        // if no there is created the command with a longer length
                            command += " " + args[i];
                        }
                        break;
                }
            }
            new NetworkClient(ip, port, ident, command).connect();

        } else if (args[0].equals("NetworkNode")) {                              // looks if the command starts with NetworkNode
            int tcpPort = 0;                                          // tcpPort is 0
            int ident = 0;                                            // ident is 0
            String gatewayIP = null;                                  // gatewayIP is null
            int gatewayPort = 0;                                      // gatewayPort is 0
            HashMap<String, Integer> ressources = new HashMap<>();
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "-gateway":                                   // looks if keyword gateway is there
                        String[] address = args[++i].split(":");      // splits the parts after this
                        gatewayIP = address[0];                              // gatewayIP is initialized
                        gatewayPort = Integer.parseInt(address[1]);          // gatewayPort is initialized
                        break;
                    case "-ident":                               // looks if keyword ident is there
                        ident = Integer.parseInt(args[++i]);     // ident is initialized
                        break;
                    case "-tcpport":                             // looks if keyword tcpport is there
                        tcpPort = Integer.parseInt(args[++i]);   // tcpPort is initialized
                        break;
                    default:
                        String[] arg = args[i].split(":");       // default
                        ressources.put(arg[0] , Integer.parseInt(arg[1]));
                }
            }
            new NetworkNode(ident, tcpPort, gatewayIP, gatewayPort, ressources).startServer();     // the NetworkNode with this data is started
        }
    }
}
