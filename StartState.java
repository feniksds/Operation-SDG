import java.util.Map;

public class StartState extends State {
    public StartState() {
        super("Waar ga je naar school?", Map.of(
            1, "Ver",
            2, "Dichtbij"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //geen stat aanpassingen
            stats.afstandsFactor = true; //ver
            return new VerblijfState();
        } else if (keuze == 2) {
            //geen stat aanpassingen
            return new VervoerState();
        }
        return this;
    }
}
