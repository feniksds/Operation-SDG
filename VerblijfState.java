import java.util.Map;

public class VerblijfState extends State {
    public VerblijfState() {
        super("Waar woon je, thuis of op kot?", Map.of(
            1, "Thuis",
            2, "Op kot"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            stats.RitFactor = 10; //als als week gerekend wordt
        } else if (keuze == 2) {
            stats.RitFactor = 2; //als als week gerekend wordt anders negeren en per dag
        }
        return new VervoerState();

    }

}
