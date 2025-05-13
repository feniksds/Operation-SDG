import java.util.List;
import java.util.Map;

public class BookState extends State {
    public BookState() {
        super("Hoe koop je je studieboeken?", Map.of(
            1, "In digitale vorm",
            2, "Nieuw",
            3, "Tweedehands"
        ),"multi","images/Hoe koop je je studieboeken.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        String keuzeString = "";
        if(keuze == 1) {
            //niks
            stats.academischeImpact+= 1;
            statChange.setAcademischeImpactChange(1);
            keuzeString = "Digitaal";
        }else if (keuze == 2) {
            //nieuw
            stats.co2Uitstoot+= 7.2;
            stats.financieleImpact-= Main.monthToWeekly(10);
            stats.academischeImpact+= 2;
            statChange.setCo2UitstootChange(7.2);
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(10));
            statChange.setAcademischeImpactChange(2);
            keuzeString = "Nieuw";
        }else if(keuze == 3){
            //2de hands
            stats.financieleImpact-= Main.monthToWeekly(5);
            stats.academischeImpact-= 1;
            statChange.setFinancieleImpactChange(-Main.monthToWeekly(5));
            statChange.setAcademischeImpactChange(-1);
            keuzeString = "Tweedehands";
        }
        logEntries.add(new LogEntry("Hoe koop je je studieboeken?",keuzeString,statChange));
        return new JobState();
    }
}
