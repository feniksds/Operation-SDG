import java.util.Map;

public class StartState extends State {
    public StartState() {
        super("Waar naar school?", Map.of(
            1, "Ver",
            2, "Dichtbij"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            return new VerblijfState();//VervoerInGentState(); //verblijfvraag
        } else if (keuze == 2) {
            return new VervoerState(); //ReizenNaarGentState();
        }
        return this;
    }
}
