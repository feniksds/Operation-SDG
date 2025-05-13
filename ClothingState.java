import java.util.List;
import java.util.Map;

public class ClothingState extends State {
    public ClothingState() {
        super("Wat voor soort kleren koop je meestal?", Map.of(
            1, "Nieuw",
            2, "Tweedehands"
        ),"multi","images/Wat voor soort kleren koop je meestal.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        String keuzeString = "";
        if (keuze == 1) {
            //nieuw
            stats.co2Uitstoot+=5.192;
            stats.financieleImpact-=  60;
            stats.eenmaligeAankopen-= 60;
            statChange.setCo2UitstootChange(5.192);
            statChange.setFinancieleImpactChange(-60);
            statChange.setEenmaligeAankopenChange(-60);
            keuzeString = "Nieuw";
        } else if (keuze == 2) {
            //2dehands
            stats.financieleImpact-= 20;
            stats.eenmaligeAankopen-= 20;
            statChange.setFinancieleImpactChange(-20);
            statChange.setEenmaligeAankopenChange(-20);
            keuzeString = "Tweedehands";
        }
        logEntries.add(new LogEntry("Wat voor soort kleren koop je meestal??",keuzeString,statChange));
        stats.toonStats();
        return new SubscribeState();
    }
}
