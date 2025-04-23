import java.util.Map;
//TODO stats veranderingen aanvullen+ vraag fixen
public class VervoerState  extends State {
    public VervoerState() {
        super("Trein, auto of openbaar vervoer?", Map.of(
            1, "Auto",
            2, "Trein",
            3, "Openbaar vervoer"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            return new AutoState() ; //verblijfvraag
        } else if (keuze == 2) {
            return new TreinState();
        }else if(keuze == 3){
            return new EtenStartState();
        }
        return this;
    }

}
