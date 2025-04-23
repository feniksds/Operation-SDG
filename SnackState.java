import java.util.Map;

public class SnackState extends State {
    public SnackState() {
        super("Aantal mensen in huis", Map.of(
            1, "Enkel huishouden",
            2, "Drie persoons huishouden",
            3, "Vier persoons huishouden",
            4, "Vijf persoons huishouden"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie
        } else if (keuze == 2) {
            //actie
        }
        return new LibraryState();
    }
}
