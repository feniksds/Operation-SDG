import java.util.Map;
//TODO stats veranderingen aanvullen
public class SnackState extends State {
    public SnackState() {
        super("Welke snacks eet je doorgaans tijdens het studeren?", Map.of(
            1, "Geen snacks",
            2, "Appel",
            3, "Suikerwafel",
            4, "Chips"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if(keuze == 1) {
            //actie stat
        }else if (keuze == 2) {
            //actie stat
        }else if(keuze == 3){
            //actie stat
        }else if(keuze == 4){
            //actie stat
        }
        return new LibraryState();
    }
}
