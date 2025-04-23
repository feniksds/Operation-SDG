import java.util.Map;

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
            //NIKS
        }else if (keuze == 2) {
            //af en toe
            stats.co2Uitstoot+= 5.304;
        }else if(keuze == 3){
            //vaak
            stats.co2Uitstoot+=10.61;
        }
        return new SnackState();
    }
}
