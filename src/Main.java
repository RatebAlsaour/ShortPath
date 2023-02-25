public class Main {

    public static final String RESET = "\033[0m"; // Text Reset
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String BLUE = "\033[0;34m"; // BLUE
    public static final String PURPLE = "\033[0;35m"; // PURPLE
    public static final String YELLOW = "\033[0;33m"; // YELLOW



    public static void main(String[] args)
    {
        Structure.init();
        Structure S = new Structure(Structure.startStationIndex, null, null, 0, Structure.energy, Structure.money, transport.OnFoot,0,-1);
        Logic L = new Logic();
        long startTime = System.nanoTime();
        L.a_star(S,typeCost.All);
        long endTime =System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(GREEN+"\nThe time spent for A* : "+RESET+YELLOW+duration+" ms\n"+RESET);

    }
}




