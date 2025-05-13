import java.util.Map;
import java.util.*;
//TODO GUI?
public abstract class State {
    protected String beschrijving;
    protected Map<Integer, String> opties;
    private String imagePath;
    private String inputTye;

    public State(String beschrijving, Map<Integer, String> opties, String inputType, String imagePath) {
        this.beschrijving = beschrijving;
        this.opties = new TreeMap<>(opties);
        this.inputTye = inputType;
        this.imagePath = imagePath;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public Map<Integer, String> getOpties() {
        return opties;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getInputType() {
        return inputTye;
    }

    public void toonOpties() {
        System.out.println("\n" + beschrijving);
        for (Map.Entry<Integer, String> entry : opties.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        System.out.print("Maak een keuze: ");
    }

    public abstract State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries);
}


