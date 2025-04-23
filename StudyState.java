import java.util.Map;
//TODO stats veranderingen aanvullen
public class StudyState extends State{
    public StudyState() {
        super("Hoe vaak gebruik je AI-tools tijdens het studeren?", Map.of(
            1, "Nooit",
            2, "Af en toe",
            3, "Vaak"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stat
        }else if (keuze == 2) {
            //actie stat
        }else if(keuze == 3){
            //actie stat
        }
        return new SnackState();
    }
}
