import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi checken
public class VeganState extends State {
    public VeganState() {
        super("Eet je Vegan/vegetarisch?", Map.of(
            1, "Ja",
            2, "Nee"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stats
        } else if (keuze == 2) {
            //actie stats
        }
        return new FastFoodState();
    }
}
