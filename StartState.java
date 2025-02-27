import java.util.Map;

public class StartState extends State {
    public StartState() {
        super("Waar woont de student?", Map.of(
            1, "Op kot in Gent",
            2, "Thuis en reist naar Gent"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            return new VervoerInGentState();
        } else if (keuze == 2) {
            return new ReizenNaarGentState();
        }
        return this;
    }
}
