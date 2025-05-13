//deprecated
//Deprecated
import java.util.List;
import java.util.Map;

public class VervoerInGentState extends State {
    public VervoerInGentState() {
        super("Hoe ga je naar school?", Map.of(
            1, "Fiets (geen vervuiling, gezonde optie)",
            2, "Elektrische fiets (verbruik verhoogt licht)",
            3, "Tram (verkeer + CO₂-uitstoot)",
            4, "Te voet (0 impact)"
        ),"multi","images/Hoe ga je naar school.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
                StatChange statChange = new StatChange();
        if (keuze == 1) {
            stats.co2Uitstoot += 0;
        } else if (keuze == 2) {

        } else if (keuze == 3) {
            stats.co2Uitstoot += 10;
            statChange.setCo2UitstootChange(10);

        } else if (keuze == 4) {
            stats.co2Uitstoot += 0;
        }


        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        stats.toonStats();

        return null;
    }
}
