public class Road {
    int distance;
    boolean taxiValid = false;
    Double taxiSpeed = null;
    String[] linesBusesValid = null;
    Double[] busSpeed = null;
    int i;
    int j;

    public Road(int distance, boolean taxiValid, Double taxiSpeed , String[] linesBusesValid, Double[] busSpeed, int i , int j) {
        this.distance = distance;
        this.taxiValid = taxiValid;
        this.taxiSpeed = taxiSpeed / 60;
        this.linesBusesValid = linesBusesValid;


        for (int k=0; k < busSpeed.length; k++) {
            busSpeed[k] /= 60;
        }

        this.busSpeed = busSpeed;
        this.i = i;
        this.j = j;
    }
    public Road(int distance, boolean taxiValid, Double taxiSpeed, int i , int j) {
        this.distance = distance;
        this.taxiValid = taxiValid;
        this.taxiSpeed = taxiSpeed / 60;
        this.i = i;
        this.j = j;
    }
    public Road(int distance, String[] linesBusesValid, Double[] busSpeed, int i , int j) {
        this.distance = distance;
        this.linesBusesValid = linesBusesValid;


        for (int k=0; k < busSpeed.length; k++) {
            busSpeed[k] /= 60;
        }

        this.busSpeed = busSpeed;
        this.i = i;
        this.j = j;
    }
    public Road(int distance, int i , int j) {
        this.distance = distance;
        this.i = i;
        this.j = j;
    }
}
