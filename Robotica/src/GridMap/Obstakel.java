package GridMap;

public enum Obstakel {
    l{
        public String toString() {
            return " ";
        }
    },
    P{
        public String toString() {
            return "P";
        }
    },
    T{
        public String toString() {
            return "-";
        }
    },
    N{
        public String toString() {
            return "N";
        }
    },
    R{
        public String toString() {
            return "R";
        }
    },
    O{
        public String toString() {
            return "O";
        }
    };
    
}
