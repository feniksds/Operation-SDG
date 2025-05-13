import java.util.List;
import java.util.Map;

public class AutoState extends State {
    public AutoState() {
        super("Welk type voertuig gebruik je?", Map.of(
                        1, "Elektrisch",
                        2, "Diesel",
                        3, "Hybride",
                        4, "Benzine"
                ), "multi",
                "images/Welk type voertuig gebruik je.png");
    }

    ;

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        double afstand = 0;
        if (stats.ritFactor == 10) {
            afstand = 62.1;
        } else {
            afstand = 3.9;
        }

        StatChange statChange = new StatChange();
        String keuzeString = "";

        if (keuze == 1) {
            //elektriek
            stats.co2Uitstoot += afstand * 0.058;
            stats.financieleImpact -= Main.monthToWeekly(940);
            statChange.setCo2UitstootChange(afstand * 0.058);
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(940));
            keuzeString = "Elektrisch";
        } else if (keuze == 2) {
            //diesel
            stats.co2Uitstoot += afstand * 0.192;
            stats.financieleImpact -= Main.monthToWeekly(1001);
            statChange.setCo2UitstootChange(afstand * 0.192);
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(1001));
            keuzeString = "Diesel";
        } else if (keuze == 3) {
            //hybride
            stats.co2Uitstoot += afstand * 0.186;
            stats.financieleImpact -= Main.monthToWeekly(1173);
            statChange.setCo2UitstootChange(afstand * 0.186);
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(1173));
            keuzeString = "Hybride";
        } else if (keuze == 4) {
            //benzine
            stats.co2Uitstoot += afstand * 0.205;
            stats.financieleImpact -= Main.monthToWeekly(1002);
            statChange.setCo2UitstootChange(afstand * 0.205);
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(1002));
            keuzeString = "Benzine";
        }


        
        logEntries.add(new LogEntry("Welk type voertuig gebruik je?",keuzeString,statChange));
        return new EtenStartState();
    }
}
