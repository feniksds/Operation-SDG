import java.util.Map;
//TODO stats veranderingen aanvullen
public class LibraryState extends State{
    public LibraryState() {
        super("Wat is je favoriete plek om te studeren?", Map.of(
            1, "Thuis",
            2, "Bibliotheek",
            3, "Café"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stat
        } else if (keuze == 2) {
            //actie stat
        }else if(keuze == 3){
            //actie stat
        }
        return new FinancialState();
    }
}
