import java.util.List;
import java.util.Map;

public class VerblijfState extends State {
    public VerblijfState() {
        super("Waar woon je, thuis of op kot?", Map.of(
            1, "Thuis",
            2, "Op kot"
        ),"multi","images/Waar woon je, thuis of op kot.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        if (keuze == 1) {
            stats.ritFactor = 10; //als als week gerekend wordt
        } else if (keuze == 2) {
            stats.ritFactor = 2; //als als week gerekend wordt anders negeren en per dag
        }
        stats.toonStats();

        StatChange statChange = new StatChange();
        statChange.setFinancieleImpactChange(-stats.ritFactor*6.70);
        statChange.setCo2UitstootChange(stats.ritFactor* 33.70*0.017);
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));

        return new VervoerState();

    }

}
