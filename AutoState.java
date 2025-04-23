import java.util.Map;
//TODO stats veranderingen aanvullen+ vraag fixen
public class AutoState extends State {
    public AutoState() {
        super("Is de auto elektrisch, hybride, \ndiesel of benzine?", Map.of(
            1, "Elektrisch",
            2, "Diesel",
            3, "Hybride",
            4, "Benzine"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //verblijfvraag
        } else if (keuze == 2) {
            //
        }else if(keuze == 3){
            //
        }else if(keuze == 4){
            //
        }
        return new EtenStartState();
    }
}
