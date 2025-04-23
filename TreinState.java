import java.util.Map;
//TODO stats veranderingen aanvullen
public class TreinState extends State {
    public TreinState() {
        super("Hoe verplaats je je van thuis naar het station \nof van campus naar je kot?", Map.of(
            1, "Fiets",
            2, "Bus",
            3, "Te voet",
            4, "E-step"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
             //actie stats
        } else if (keuze == 2) {
            //actie stats
        }else if(keuze == 3){
            //actie stats
        }else if(keuze == 4){
            //actie stats
        }
        return new EtenStartState();
    }
}
