import java.util.Map;
//TODO stats veranderingen aanvullen
public class SchoolState extends State{
    public SchoolState() {
        super("Gebruik je een tekentablet of papier om te tekenen?", Map.of(
            1, "Tekentablet",
            2, "Papier"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stat
        } else if (keuze == 2) {
            //actie stat
        }
        return new BookState();
    }
}
