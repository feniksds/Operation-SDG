import java.util.Map;
//TODO stats veranderingen aanvullen
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
            //stat aanpassingen
            return new VerblijfState();//VervoerInGentState(); //verblijfvraag
        } else if (keuze == 2) {
            //stat aanpassingen
            return new VervoerState(); //ReizenNaarGentState();
        }
        return this;
    }
}
