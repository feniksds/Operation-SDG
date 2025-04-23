import java.util.Map;
//TODO stats veranderingen aanvullen+Radi checken
public class KokenState extends State{
    public KokenState() {
        super("Kook je zelf of doet iemand anders dat voor jou?", Map.of(
            1, "Ik kook zelf",
            2, "Iemand anders kookt"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stats
        } else if (keuze == 2) {
            //actie stats
        }
        return new VleesState();
    }
}
