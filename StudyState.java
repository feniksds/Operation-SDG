import java.util.List;
import java.util.Map;

public class StudyState extends State{
    public StudyState() {
        super("Hoe vaak gebruik je AI-tools tijdens het studeren?", Map.of(
            1, "Nooit",
            2, "Af en toe",
            3, "Vaak"
        ),"multi","images/Hoe vaak gebruik je AI-tools tijdens het studeren.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();

        if (keuze == 1) {
            //actie stat
            //NIKS
        }else if (keuze == 2) {
            //af en toe
            stats.co2Uitstoot+= Main.yeartoWeekly(5.304);
            stats.academischeImpact+= 1;

            statChange.setCo2UitstootChange(Main.yeartoWeekly(5.304));
            statChange.setAcademischeImpactChange(1);
        }else if(keuze == 3){
            //vaak
            stats.co2Uitstoot+=Main.yeartoWeekly(10.61);
            stats.academischeImpact+= 2;

            statChange.setCo2UitstootChange(Main.yeartoWeekly(10.61));
            statChange.setAcademischeImpactChange(2);
        }

        stats.toonStats();
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));

        return new SnackState();
    }
}
