import java.util.Map;

public class VerblijfState extends State {
    public VerblijfState() {
        super("Waar woon je thuis of op kot?", Map.of(
            1, "Thuis",
            2, "Op kot"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie
        } else if (keuze == 2) {
            //actieee
        }
        return new VervoerState();

    }

}
