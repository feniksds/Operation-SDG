import java.util.Map;
import java.util.*;
//TODO GUI?
public abstract class State {
    protected String beschrijving;
    protected Map<Integer, String> opties;



    public State(String beschrijving, Map<Integer, String> opties) {
        this.beschrijving = beschrijving;
        this.opties = new TreeMap<>(opties);
    }

    public void toonOpties() {
        System.out.println("\n" + beschrijving);
        for (Map.Entry<Integer, String> entry : opties.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        System.out.print("Maak een keuze: ");
    }

    public abstract State verwerkKeuze(int keuze, StudentStats stats);
}


