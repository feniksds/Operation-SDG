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
            stats.co2Uitstoot+= Main.yeartoWeekly(5.304);
            stats.academischeImpact+= 1;
        }else if(keuze == 3){
            //vaak
            stats.co2Uitstoot+=Main.yeartoWeekly(10.61);
            stats.academischeImpact+= 2;
        }
        return new SnackState();
    }
}
