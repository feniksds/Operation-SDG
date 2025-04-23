import java.util.Map;
//TODO stats veranderingen aanvullen
public class JobState extends State{
    public JobState() {
        super("Heb je een studentenjob?", Map.of(
            1, "Nee",
            2, "Weekendjob",
            3, "Parttime",
            4, "Fulltime"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if(keuze == 1) {
            //actie stat
        }else if(keuze == 2) {
            //actie stat
        }else if(keuze == 3) {
            //actie stat
        }else if(keuze == 4) {
            //actie stat
        }
        return new ClothingState();
    }
}
