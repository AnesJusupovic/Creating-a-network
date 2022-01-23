package network.ressource;


    public class Ressource {

        private String name;    // name
        private int count;      // count

        public Ressource(String name, int count) {
            this.name = name;          // name
            this.count = count;        // count
        }

        public String getName() {                     // return name
            return name;
        }

        public int getCount() {                       // return count
            return count;
        }
    }


