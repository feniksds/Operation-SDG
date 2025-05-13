import java.util.List;
import java.util.Map;
//TODO Radi checken
public class GasState extends State {
    public GasState() {
        super("Kook je thuis op gas of inductie?", Map.of(
            1, "Gas",
            2, "Inductie"
        ),"multi","images/Kook je thuis op gas of inductie.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        if (keuze == 1) {
            //Gas
            stats.eetCO2 += Main.yearToDaily(80);
            statChange.setEetCO2Change(Main.yearToDaily(80));
        } else if (keuze == 2) {
            //Inductie
            stats.eetCO2 += Main.yearToDaily(60);
            statChange.setEetCO2Change(Main.yearToDaily(60));
        }

        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new KokenState();
    }
}
