import java.util.Map;
//TODO stats veranderingen aanvullen
public class BookState extends State {
    public BookState() {
        super("Hoe koop je je studieboeken?", Map.of(
            1, "In digitale vorm",
            2, "Nieuw",
            3, "Tweedehands"
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
        }
        return new JobState();
    }
}
