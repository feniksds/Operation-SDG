import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi checken
public class BoodschappenState extends State {
    public BoodschappenState() {
        super("Koop je meestal biologische,\n merk- of huismerkproducten?", Map.of(
            1, "Biologisch (bio)",
            2, "Merkproducten",
            3, "Huismerkproducten"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stats
        } else if (keuze == 2) {
            //actie stats
        }
        return new StudyState();
    }
}
