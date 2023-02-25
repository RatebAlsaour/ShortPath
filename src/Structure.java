import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

enum typeCost { Time, Energy, Money, All}
enum transport { Taxi, Bus, OnFoot }
public class Structure {

///////////////////////---static---////////////////////
    public static Station[] stations;
    public static Road[][] roads;
    public static Map<String,int[]> lineBuse = new HashMap<String,int[]>();
    public static final int money = 10000;
    public static final double onFootSpeed = 5.5 / 60;
    public static final int onFootMoney = 0;
    public static final int taxiMoneyByKM = 1000;
    public static final int BusMoney = 400;
    public static final int energy = 100;
    public static final int changEnergyByOnFootByKM = -10;
    public static final int changEnergyByTAxiByKM = +5;
    public static final int changEnergyByBusByKM = -5;
    public static final int startStationIndex = 0;
    public static final int goalStationIndex = 3;

    public static void init(){
       stations = new Station[]{
               new Station("Hamak", 15, 5),
               new Station("Babtoma", 10),
               new Station("Karagat", 12, 7),
               new Station("Douma", 30, 18),
               new Station("Barameka", 10, 6),
               new Station("Thoura", 8),
               new Station("Douilaa")
       };
       roads = new Road[][]{
               {
                   null,
                   new Road(2,true,70.0, new String[]{"Handasa"},new Double[]{50.0},0,1),
                   new Road(7,true,65.0, new String[]{"Handasa"},new Double[]{43.0},0,2),
                   new Road(17,true,90.0, new String[]{"Tulab"},new Double[]{60.0},0,3),
                   new Road(5,true,40.0, new String[]{"Senaha"},new Double[]{35.0},0,4),
                   new Road(6,true,40.0,0,5),
                   new Road(1,0,6),
               },
               {
                       new Road(2,1,0),
                       null,
                       new Road(5, new String[]{"Handasa","Philastin","Zaihra","Douilaa"},new Double[]{40.0,35.0,37.0,38.0},1,2),
                       new Road(12,1,3),
                       new Road(7,1,4),
                       new Road(8,1,5),
                       new Road(1, new String[]{"Douilaa"},new Double[]{40.0},1,6),
               },
               {
                       new Road(7,true,50.0, new String[]{"Handasa"},new Double[]{55.0},2,0),
                       new Road(5,true,50.0, new String[]{"Handasa","Philastin","Zaihra","Douilaa"},new Double[]{55.0,35.0,37.0,38.0},2,1),
                       null,
                       new Road(15,true,85.0, new String[]{"Douma"},new Double[]{70.0},2,3),
                       new Road(8,true,45.0,  new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},2,4),
                       new Road(7,true,45.0,  new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},2,5),
                       new Road(6, new String[]{"Douilaa"},new Double[]{30.0},2,6),
               },
               {
                       new Road(17,true,90.0, new String[]{"Tulab"},new Double[]{60.0},3,0),
                       new Road(21,true,85.0,3,1),
                       new Road(15,true,85.0, new String[]{"Douma"},new Double[]{50.0},3,2),
                       null,
                       new Road(20,true,65.0,3,4),
                       new Road(19,true,65.0,3,5),
                       new Road(22,3,6),
               },
               {
                       new Road(5,true,40.0, new String[]{"Senaha"},new Double[]{35.0},4,0),
                       new Road(7,true,40.0,4,1),
                       new Road(8,true,45.0, new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},4,2),
                       new Road(20,true,65.0,4,3),
                       null,
                       new Road(1,true,45.0, new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},4,5),
                       new Road(8,4,6),
               },
               {
                       new Road(6,5,0),
                       new Road(8,5,1),
                       new Road(7, new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},5,2),
                       new Road(19,5,3),
                       new Road(1, new String[]{"Maza","Barameka"},new Double[]{35.0,38.0},5,4),
                       null,
                       new Road(9,5,6),
               },
               {
                       new Road(1,6,0),
                       new Road(1,6,1),
                       new Road(6,6,2),
                       new Road(16,6,3),
                       new Road(6,6,4),
                       new Road(7,6,5),
                       null,
               },
       };

        lineBuse.put("Handasa", new int[]{2,3,10,15,16});
        lineBuse.put("Tulab", new int[]{4,22});
        lineBuse.put("Senaha", new int[]{5,29});
        lineBuse.put("Philastin", new int[]{10,16});
        lineBuse.put("Zaihra", new int[]{10,16});
        lineBuse.put("Douilaa", new int[]{10,16,14,21});
        lineBuse.put("Maza", new int[]{19,20,31,34,38,40});
        lineBuse.put("Barameka", new int[]{19,20,31,34,38,40});
        lineBuse.put("Douma", new int[]{18,24});

    }


////////////////////////---nonstatic---////////////////////

    int currentSite;                 //it is index of array station

    ArrayList<Structure> path = new ArrayList<Structure>();
    Structure parent;

    int spendTime;
    int currentEnergy;
    int currentMoney;
    transport typeTransport = transport.Bus;
    String nameLineBus = null;
    int cost;
    int H;                              //Heuristic

    public Structure(Structure S) {
        this.currentSite = S.currentSite;
        this.path = S.path;
        this.parent = S.parent;
        this.spendTime = S.spendTime;
        this.currentEnergy = S.currentEnergy;
        this.currentMoney = S.currentMoney;
        this.typeTransport = S.typeTransport;
        this.cost = S.cost;
        this.H = S.H;
    }

    public Structure(int currentSite, ArrayList<Structure> path, Structure parent, int spendTime, int currentEnergy, int currentMoney, transport typeTransport, int cost, int H) {
        this.currentSite = currentSite;
        this.path = path;
        this.parent = parent;
        this.spendTime = spendTime;
        this.currentEnergy = currentEnergy;
        this.currentMoney = currentMoney;
        this.typeTransport = typeTransport;
        this.cost = cost;
        this.H = H;
    }
    public Structure(int currentSite, ArrayList<Structure> path, Structure parent, int spendTime, int currentEnergy, int currentMoney, String nameLineBus, int cost, int H) {
        this.currentSite = currentSite;
        this.path = path;
        this.parent = parent;
        this.spendTime = spendTime;
        this.currentEnergy = currentEnergy;
        this.currentMoney = currentMoney;
        this.nameLineBus = nameLineBus;
        this.cost = cost;
        this.H = H;
    }

//------------------------------isFinal------------------------------------
    boolean isFinal(){
        if(this.currentSite == goalStationIndex)
            return true;
        else
            return false;
    }

//------------------------------getNextStates-------------------------
    ArrayList<Structure> getNextStates(typeCost type) {
        ArrayList<Structure> Next_States = new ArrayList<Structure>();

        for (int j=0 ; j < roads[this.currentSite].length; j++) {
            if (roads[this.currentSite][j] == null) {
                continue;
            }else {
                int H = calculateH(this,type);
                int G;
                ArrayList<Structure> path = new ArrayList<Structure>();

                if (type == typeCost.Time) {
                    G = this.calculateGByTime(roads[currentSite][j], null, transport.OnFoot);
                } else if (type == typeCost.Energy) {
                    G = this.calculateGByEnergy(roads[currentSite][j], null, transport.OnFoot);
                } else if (type == typeCost.Money) {
                    G = this.calculateGByMoney(roads[currentSite][j], null, transport.OnFoot);
                }else {
                    G = this.calculateGByAll(roads[currentSite][j], null, transport.OnFoot);
                }
                int[] t = this.calculate3Attributes(roads[currentSite][j], null, transport.OnFoot);
                Structure S = new Structure(j, path, this, t[0], t[1], t[2], transport.OnFoot, G+this.cost, H);
                S.copyPath(this);
                S.path.add(new Structure(this));
                Next_States.add(S);

                if (roads[currentSite][j].taxiSpeed != null) {
                    ArrayList<Structure> path2 = new ArrayList<Structure>();
                    if (type == typeCost.Time) {
                        G = this.calculateGByTime(roads[currentSite][j], null, transport.Taxi);
                    } else if (type == typeCost.Energy) {
                        G = this.calculateGByEnergy(roads[currentSite][j], null, transport.Taxi);
                    } else if (type == typeCost.Money) {
                        G = this.calculateGByMoney(roads[currentSite][j], null, transport.Taxi);
                    } else {
                        G = this.calculateGByAll(roads[currentSite][j], null, transport.Taxi);
                    }
                    t = this.calculate3Attributes(roads[currentSite][j], null, transport.Taxi);
                    S = new Structure(j, path2, this, t[0], t[1], t[2], transport.Taxi, G+this.cost, H);
                    S.copyPath(this);
                    S.path.add(new Structure(this));
                    Next_States.add(S);
                }

                if (roads[currentSite][j].linesBusesValid != null) {
                    for (int k = 0; k < roads[currentSite][j].linesBusesValid.length; k++) {
                        ArrayList<Structure> path3 = new ArrayList<Structure>();
                        if (type == typeCost.Time) {
                            G = this.calculateGByTime(roads[currentSite][j], k, transport.Bus);
                        } else if (type == typeCost.Energy) {
                            G = this.calculateGByEnergy(roads[currentSite][j], k, transport.Bus);
                        } else if (type == typeCost.Money) {
                            G = this.calculateGByMoney(roads[currentSite][j], k, transport.Bus);
                        } else {
                            G = this.calculateGByAll(roads[currentSite][j], k, transport.Bus);
                        }
                        t = this.calculate3Attributes(roads[currentSite][j], k, transport.Bus);
                        S = new Structure(j, path3, this, t[0], t[1], t[2], roads[currentSite][j].linesBusesValid[k], G+this.cost, H);
                        S.copyPath(this);
                        S.path.add(new Structure(this));
                        Next_States.add(S);

                    }
                }
            }
        }
        return Next_States;
    }

//-------------------------------copyPath-----------------------------------------------------
    void copyPath(Structure S){
        if (S.path !=null){
            for(int i=0; i < S.path.size(); i++){
                Structure p = new Structure(S.path.get(i));
                this.path.add(p);
            }
        }
    }

//------------------------------calculate3Attributes----------------------
    int[] calculate3Attributes(Road road, Integer indexBus, transport typeTransport){
        int[] t = new int[3];
        if (typeTransport == transport.OnFoot) {
            t[0] = (int) (this.spendTime + (road.distance / onFootSpeed));
            t[1] = this.currentEnergy - (10 * road.distance);
            t[2] = this.currentMoney;
        } else if (typeTransport == transport.Taxi) {
            t[0] = (int) (this.spendTime + (road.distance / road.taxiSpeed) + stations[this.currentSite].timeWaitTaxi);
            t[1] = this.currentEnergy == 100 ? this.currentEnergy : this.currentEnergy + (5 * road.distance);
            t[2] = this.currentMoney - (1000 * road.distance);
        }else {
            if (this.nameLineBus != null && this.nameLineBus == road.linesBusesValid[indexBus]) {
                t[0] = (int) (this.spendTime + (road.distance / road.busSpeed[indexBus]));
                t[2] = this.currentMoney;
            }else {
                t[0] = (int) (this.spendTime + (road.distance / road.busSpeed[indexBus]) + stations[this.currentSite].timeWaitBus);
                t[2] = this.currentMoney - 400;
            }
                t[1] = this.currentEnergy - (5 * road.distance);
        }

        return t;
    }

//-----------------------------------calculateGByTime------------------------------------------------
    int calculateGByTime(Road road, Integer indexBus, transport typeTransport) {
        int G;
        if (typeTransport == transport.OnFoot) {
            G = (int) (road.distance / onFootSpeed);
        } else if (typeTransport == transport.Taxi) {
            G = (int) ((road.distance / road.taxiSpeed) + stations[this.currentSite].timeWaitTaxi);
        }else {
            if (this.nameLineBus == road.linesBusesValid[indexBus]) {
                G = (int) (road.distance / road.busSpeed[indexBus]);
            }else {
                G = (int) ((road.distance / road.busSpeed[indexBus]) + stations[this.currentSite].timeWaitBus);
            }
        }
        return G;
    }
//-----------------------------------calculateGByMoney------------------------------------------------
    int calculateGByMoney(Road road, Integer indexBus, transport typeTransport) {
        int G;
        if (typeTransport == transport.OnFoot) {
            G = 0;
        } else if (typeTransport == transport.Taxi) {
            G = 1000 * road.distance;
        }else {
            if (this.nameLineBus == road.linesBusesValid[indexBus]) {
               G = 0;
            }else {
                G = 400;
            }
        }
        return G;
    }
//-----------------------------------calculateGByEnergy------------------------------------------------
    int calculateGByEnergy(Road road, Integer indexBus, transport typeTransport) {
        int G;
        if (typeTransport == transport.OnFoot) {
            G = 10 * road.distance;
        } else if (typeTransport == transport.Taxi) {
            G = 1000 * road.distance;
        }else {
            G = 5 * road.distance;
        }
        return G;
    }
//-----------------------------------calculateGByAll------------------------------------------------
    int calculateGByAll(Road road, Integer indexBus, transport typeTransport) {
        int G = ((calculateGByTime(road, indexBus, typeTransport) * 33) / 100) +
                ((calculateGByMoney(road, indexBus, typeTransport) * 33) / 100) +
                ((calculateGByEnergy(road, indexBus, typeTransport) * 34) / 100);
        return G;
    }
//-----------------------------------calculateH------------------------------------------------
    int calculateH(Structure S , typeCost t) {
        int H = 0;
        int indexBus;
        transport type;
        LinkedList<Road> queue = new LinkedList<Road>();
        Road road = null;
        for (int i = 0; i < roads.length; i++) {
            if(S.currentSite == i){
                continue;
            }
            if (roads[i][goalStationIndex] != null) {
                SortForH(queue, roads[i][goalStationIndex]);
            }
        }
        for (int i = 0; i < queue.size(); i++) {
            road = queue.pop();
            if( roads[S.currentSite][goalStationIndex] == null){
                if(roads[S.currentSite][road.i] != null){
                    break;
                }
            }else if (roads[S.currentSite][road.i] != null && road.distance+roads[S.currentSite][road.i].distance < roads[S.currentSite][goalStationIndex].distance) {
                break;
            }
            road = null;
        }
        if(road == null){
            road = roads[S.currentSite][goalStationIndex];
        }
        if (t == typeCost.Time || t == typeCost.Energy || t == typeCost.All ) {
            if (S.currentMoney >= road.distance * 1000 && road.taxiValid) {
                type = transport.Taxi;
                if (t == typeCost.Time) {
                    H = this.calculateGByTime(road, null, type);
                } else if (t == typeCost.Energy) {
                    H = this.calculateGByEnergy(road, null, type);
                } else if (t == typeCost.Money) {
                    H = this.calculateGByMoney(road, null, type);
                } else {
                    H = this.calculateGByAll(road, null, type);
                }
            } else if (road.linesBusesValid != null) {
                if (S.currentMoney >= 400 && road.linesBusesValid.length != 0) {
                    type = transport.Bus;
                    Double speedBus = road.busSpeed[0];
                    indexBus = 0;
                    for (int i = 0; i < road.busSpeed.length; i++) {
                        if (speedBus < road.busSpeed[i]) {
                            speedBus = road.busSpeed[i];
                            indexBus = i;
                        }
                    }
                    if (t == typeCost.Time) {
                        H = this.calculateGByTime(road, indexBus, type);
                    } else if (t == typeCost.Energy) {
                        H = this.calculateGByEnergy(road, indexBus, type);
                    } else if (t == typeCost.Money) {
                        H = this.calculateGByMoney(road, indexBus, type);
                    } else {
                        H = this.calculateGByAll(road, indexBus, type);
                    }
                }

            }else{
                type = transport.OnFoot;
                if (t == typeCost.Time) {
                    H = this.calculateGByTime(road, null, type);
                } else if (t == typeCost.Energy) {
                    H = this.calculateGByEnergy(road, null, type);
                } else if (t == typeCost.Money) {
                    H = this.calculateGByMoney(road, null, type);
                } else {
                    H = this.calculateGByAll(road, null, type);
                }
            }
        }
        return H;
    }

//---------------------------------------SortForH----------------------------------------------
    static void SortForH(LinkedList<Road> S, Road e){
        if (S.size() == 0) {
            S.add(e);
        }else {
            int y = S.size();
            for (int i=0; i < y; i++) {
                if (e.distance < S.get(i).distance) {
                    S.add(i,e);
                    break;
                }
            }
            if (e.distance >= S.getLast().distance) {
                S.addLast(e);
            }
        }
    }

//--------------------------------printPath---------------------------------------------
    void printPath(){
        System.out.print(Main.BLUE+"The path is :"+Main.RESET);
        if (this.path.size() == 0)
            System.out.println("Empty");
        else{
            for (int i=0 ; i < this.path.size()-1; i++){
                System.out.print("[ Station: "+stations[this.path.get(i).currentSite].name+", Transport: "+this.path.get(i).typeTransport.name()+", Time: "+this.path.get(i).spendTime+", Money: "+this.path.get(i).currentMoney+", Energy: "+this.path.get(i).currentEnergy+" ]"+Main.BLUE+" => "+Main.RESET);
            }
            System.out.print("[ Station: "+stations[this.path.get(this.path.size()-1).currentSite].name+", Transport: "+this.path.get(this.path.size()-1).typeTransport.name()+", Time: "+this.path.get(this.path.size()-1).spendTime+", Money: "+this.path.get(this.path.size()-1).currentMoney+", Energy: "+this.path.get(this.path.size()-1).currentEnergy+" ]");
        }
    }


}
