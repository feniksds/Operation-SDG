import java.util.Map;
//TODO stats veranderingen aanvullen+ Thomas checken
public class SubscribeState extends State{
    public SubscribeState() {
        super("Heb je een abbonement op een streaming dienst?", Map.of(
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
        return null;
    }
}
