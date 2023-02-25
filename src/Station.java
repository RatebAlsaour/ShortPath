public class Station {
    String name;
    Integer timeWaitBus = null;
    Integer timeWaitTaxi = null;

    public Station(String name, Integer timeWaitBus, Integer timeWaitTaxi) {
        this.name = name;
        this.timeWaitBus = timeWaitBus;
        this.timeWaitTaxi = timeWaitTaxi;
    }
    public Station(String name, Integer timeWaitBus) {
        this.name = name;
        this.timeWaitBus = timeWaitBus;
    }
    public Station(String name) {
        this.name = name;
    }
}
