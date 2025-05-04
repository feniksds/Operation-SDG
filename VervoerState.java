import java.util.Map;

public class VervoerState  extends State {
    public VervoerState() {
        super("Neem je de trein,de auto\n of het openbaar vervoer?", Map.of(
            1, "Auto",
            2, "Trein",
            3, "Ander vervoer"
        ),"multi","images/Neem je de trein,de auto of het openbaar vervoer.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            return new AutoState() ; //verblijfvraag
        } else if (keuze == 2) {
            //stats here
            //de trein  gebruik ritfactor
            //33.70 kùm   1 rit 6.70
            stats.co2Uitstoot += stats.ritFactor* 33.70*0.017;
            stats.financieleImpact -= stats.ritFactor*6.70;
            return new TreinState();
        }else if(keuze == 3){
            //stats here
            return new TreinState();
        }
        stats.toonStats();

        return this;
    }

}
