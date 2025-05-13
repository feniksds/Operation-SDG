import java.util.List;
import java.util.Map;

public class TreinState extends State {
    public TreinState() {
        super("Hoe verplaats je je van thuis naar het station \nof van campus naar je kot?", Map.of(
            1, "Fiets",
            2, "Bus",
            3, "Te voet",
            4, "Tram"
        ),"multi","images/Hoe verplaats je je van thuis naar het station of van campus naar je kot.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();

        if (keuze == 1) {
             stats.financieleImpact -= Main.monthToWeekly(11);
             statChange.setFinancieleImpactChange(- Main.monthToWeekly(11));
        } else if (keuze == 2) {
            //bus
            stats.co2Uitstoot +=0.106*3.9;
            stats.financieleImpact -= Main.yeartoWeekly(215);
            statChange.setFinancieleImpactChange(- Main.monthToWeekly(215));
            statChange.setCo2UitstootChange(0.106*3.9);
        }else if(keuze == 3){
            //niks
        }else if(keuze == 4) {
            //metro
            stats.co2Uitstoot += 0.041*3.9;
            stats.financieleImpact -= Main.yeartoWeekly(215);
            statChange.setFinancieleImpactChange(- Main.monthToWeekly(215));
            statChange.setCo2UitstootChange(0.041*3.9);

        }
        stats.toonStats();

        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));

        return new EtenStartState();
    }
}
