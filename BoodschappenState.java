import java.util.Map;
//TODO Radi checken  ("KGa dit deprecaten want geen data ofzo" Feniks )
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
        return this;
    }
}
