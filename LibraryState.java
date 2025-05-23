import java.util.List;
import java.util.Map;

public class LibraryState extends State{
    public LibraryState() {
        super("Wat is je favoriete plek om te studeren?", Map.of(
            1, "Thuis",
            2, "Bibliotheek",
            3, "Café",
            4,"Café om te zuipen"
        ),"multi","images/Wat is je favoriete plek om te studeren.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        if (keuze == 1) {
            stats.academischeImpact += 1;
            statChange.setAcademischeImpactChange(1);
        } else if (keuze == 2) {
            stats.academischeImpact += 1;
            statChange.setAcademischeImpactChange(1);
        } else if (keuze == 3) {
            stats.academischeImpact += 1;
            statChange.setAcademischeImpactChange(1);
        } else if (keuze == 4) {
            stats.academischeImpact -= 3;
            stats.financieleImpact -= 0.76*3;
            statChange.setAcademischeImpactChange(-3);
            statChange.setFinancieleImpactChange(-0.76*3);
        }
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new FinancialState();
    }
}
