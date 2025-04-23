import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi checken
public class TakeawayState extends State {
    public TakeawayState() {
        super("Bestel je Takeaway?", Map.of(
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
        return new HelloFreshState();
    }
}
