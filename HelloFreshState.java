import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi Checken
public class HelloFreshState extends State {
    public HelloFreshState() {
        super("Bestel je kant en klare maaltijd?", Map.of(
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
        return new BoodschappenState();
    }
}
