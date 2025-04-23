import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi checken
public class VleesState extends State {
    public VleesState() {
        super("Eet je vlees?", Map.of(
            1, "Ja",
            2, "Nee"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie
        } else if (keuze == 2) {
            //actie
        }
        return new VeganState();
    }
}
