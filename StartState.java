import java.util.List;
import java.util.Map;

public class StartState extends State {
    public StartState() {
        super("Waar ga je naar school?", Map.of(
                1, "Ver",
                2, "Dichtbij"
        ),"multi", "images/waar ga je naar school.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        if (keuze == 1) {
            //geen stat aanpassingen
            return new VerblijfState();
        } else if (keuze == 2) {
            //geen stat aanpassingen
            return new TreinState();
        }
        stats.toonStats();

        return this;
    }
}
