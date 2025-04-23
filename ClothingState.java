import java.util.Map;
//TODO stats veranderingen aanvullen
public class ClothingState extends State {
    public ClothingState() {
        super("Wat voor soort kleren koop je meestal?", Map.of(
            1, "Nieuw",
            2, "Tweedehands"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stat
        } else if (keuze == 2) {
            //actie stat
        }
        return new SubscribeState();
    }
}
