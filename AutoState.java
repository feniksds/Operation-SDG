import java.util.Map;

public class AutoState extends State {
    public AutoState() {
        super("Welk type voertuig gebruik je?", Map.of(
            1, "Elektrisch",
            2, "Diesel",
            3, "Hybride",
            4, "Benzine"
        ));
    }
    ;

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        double afstand=0;
        if(stats.ritFactor == 10){
            afstand= 62.1;
        }else{
            afstand= 3.9;
        }

        if (keuze == 1) {
            //elektriek
            stats.co2Uitstoot += afstand * 0.058;
            stats.financieleImpact += Main.monthToWeekly(940);
        } else if (keuze == 2) {
            //diesel
            stats.co2Uitstoot += afstand * 0.192;
            stats.financieleImpact += Main.monthToWeekly(1001);
        }else if(keuze == 3){
            //hybride
            stats.co2Uitstoot += afstand * 0.186;
            stats.financieleImpact += Main.monthToWeekly(1173);
        }else if(keuze == 4){
            //benzine
            stats.co2Uitstoot += afstand * 0.205;
            stats.financieleImpact += Main.monthToWeekly(1002);
        }
        return new EtenStartState();
    }
}
