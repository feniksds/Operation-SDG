import java.util.Map;
//TODO stats veranderingen aanvullen+ vraag fixen
public class TreinState extends State {
    public TreinState() {
        super("Welk vervoer neem je van huis naar station \nof van campus naar kot?", Map.of(
            1, "Fiets",
            2, "Bus",
            3, "Te voet",
            4, "E-step"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
             //actie
        } else if (keuze == 2) {
            //actie
        }else if(keuze == 3){
            //actie
        }else if(keuze == 4){
            //actie
        }
        return new EtenStartState();
    }
}
