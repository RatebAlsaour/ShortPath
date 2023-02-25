import java.util.ArrayList;
import java.util.LinkedList;


public class Logic {
    void a_star(Structure S, typeCost type){
        boolean success = false;
        int num =0;
        ArrayList<Structure> next_States = S.getNextStates(type);
        LinkedList<Structure> queue =new LinkedList<Structure>();

        for (int i=0; i < next_States.size(); i++) {
            if (next_States.get(i).currentEnergy > 0 && next_States.get(i).currentMoney >= 0) {
                insertionSortByF(queue, next_States.get(i));
            }
        }

        while (queue.size() != 0){
            Structure S1 = queue.poll();
            num++;

            next_States = S1.getNextStates(type);
            for (int i=0; i < next_States.size(); i++) {
                if (next_States.get(i).currentEnergy > 0 && next_States.get(i).currentMoney >= 0) {
                    insertionSortByF(queue, next_States.get(i));
                }
            }
            if(S1.isFinal()){
                success = true;
                System.out.println(Main.RED+"A* arrived the goal"+Main.RESET);
                S1.path.add(S1);
                System.out.println("Type Cost ["+type.name()+"]: "+S1.cost);
                S1.printPath();
               break;
            }
        }
        if (!success)
            System.out.println("A* failed to access the goal");
        System.out.println("\nThe number of Structure which had visited :"+num);
    }

//---------------------------------------insertionSortByF----------------------------------------------
    static void insertionSortByF(LinkedList<Structure> S, Structure e) {
        if (S.size() == 0) {
            S.add(e);
        } else {
            int y = S.size();
            for (int i = 0; i < y; i++) {
                if (e.cost + e.H < S.get(i).cost + S.get(i).H) {
                    S.add(i, e);
                    break;
                } else if (e.cost + e.H == S.get(i).cost + S.get(i).H) {
                    if (e.H < S.get(i).H) {
                        S.add(i, e);
                        break;
                    }
                }
            }
            if (e.cost + e.H > S.getLast().cost + S.getLast().H) {
                S.addLast(e);
            }
        }
    }
}
