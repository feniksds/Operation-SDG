import java.util.Map;
//TODO stats veranderingen aanvullen
public class VervoerState  extends State {
    public VervoerState() {
        super("Neem je de trein,de auto\n of het openbaar vervoer?", Map.of(
            1, "Auto",
            2, "Trein",
            3, "Openbaar vervoer"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //stats here
            return new AutoState() ; //verblijfvraag
        } else if (keuze == 2) {
            //stats here
            return new TreinState();
        }else if(keuze == 3){
            //stats here
            return new EtenStartState();
        }
        return this;
    }

}
