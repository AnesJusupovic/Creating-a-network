package network.client;

import java.util.HashMap;

public class Client {
    public final int indentifier;           // identifier
    public final HashMap<String , Integer> ressources;       // ressources

    public Client(int indentifier, HashMap<String, Integer> ressources) {
        this.indentifier = indentifier; // identifier
        this.ressources = ressources;   // ressources
    }

    public HashMap<String, Integer> getRessources() {
        return ressources;
    }      // return ressources

    public int getIndentifier() {
        return indentifier;
    }      // retunr identifier
}
