import java.util.Map;

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
            //nieuw
            stats.co2Uitstoot+=270;
            stats.financieleImpact-=  60;
        } else if (keuze == 2) {
            //2dehands
            stats.financieleImpact-= 20;
        }
        return new SubscribeState();
    }
}
