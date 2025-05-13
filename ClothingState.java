import java.util.Map;

public class ClothingState extends State {
    public ClothingState() {
        super("Wat voor soort kleren koop je meestal?", Map.of(
            1, "Nieuw",
            2, "Tweedehands"
        ),"multi","images/Wat voor soort kleren koop je meestal.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //nieuw
            stats.co2Uitstoot+=5.192;
            stats.financieleImpact-=  60;
            stats.eenmaligeAankopen-= 60;
        } else if (keuze == 2) {
            //2dehands
            stats.financieleImpact-= 20;
            stats.eenmaligeAankopen-= 20;
        }

        stats.toonStats();
        return new SubscribeState();
    }
}
