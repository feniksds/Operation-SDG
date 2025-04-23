import java.util.Map;
//TODO stats veranderingen aanvullen
public class AutoState extends State {
    public AutoState() {
        super("Welk type voertuig gebruik je?", Map.of(
            1, "Elektrisch",
            2, "Diesel",
            3, "Hybride",
            4, "Benzine"
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
