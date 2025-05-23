import java.util.List;
import java.util.Map;

public class SchoolState extends State{
    public SchoolState() {
        super("Gebruik je een tekentablet of papier om te tekenen?", Map.of(
            1, "Tekentablet",
            2, "Papier"
        ),"multi","images/Gebruik je een tekentablet of papier om te tekenen.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        if (keuze == 1) {
            //tablet
            stats.co2Uitstoot+=3.269;
            stats.financieleImpact-=Main.monthToWeekly(8.68);
            statChange.setCo2UitstootChange(3.269);
            statChange.setFinancieleImpactChange(- Main.monthToWeekly(8.68));
        } else if (keuze == 2) {
            //papier
            stats.co2Uitstoot+=2.814;
            stats.financieleImpact-=Main.monthToWeekly(0.94);
            statChange.setCo2UitstootChange(2.814);
            statChange.setFinancieleImpactChange(- Main.monthToWeekly(0.94));
        }
        stats.toonStats();
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new BookState();
    }
}
