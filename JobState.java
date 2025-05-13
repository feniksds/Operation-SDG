import java.util.List;
import java.util.Map;

public class JobState extends State{
    public JobState() {
        super("Heb je een studentenjob?", Map.of(
            1, "Nee",
            2, "Weekendjob",
            3, "Parttime",
            4, "Fulltime"
        ),"multi","images/Heb je een studentenjob.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        if(keuze == 1) {
            //nee
            stats.academischeImpact += 2;
            statChange.setAcademischeImpactChange(2);
        }else if(keuze == 2) {
            //weekend
            stats.financieleImpact += Main.monthToWeekly(227);
            stats.academischeImpact -=1;
            statChange.setFinancieleImpactChange(Main.monthToWeekly(227));
            statChange.setAcademischeImpactChange(-1);
        }else if(keuze == 3) {
            //part
            stats.financieleImpact += Main.monthToWeekly(737.75);
            stats.academischeImpact -= 2;
            statChange.setFinancieleImpactChange(Main.monthToWeekly(737.75));
            statChange.setAcademischeImpactChange(-2);
        }else if(keuze == 4) {
            //full
            stats.financieleImpact += Main.monthToWeekly(2156.5);
            stats.academischeImpact -=3;
            statChange.setFinancieleImpactChange(Main.monthToWeekly(2156.5));
            statChange.setAcademischeImpactChange(-3);
        }
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new ClothingState();
    }
}
