import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialState extends State {
    public FinancialState() {
        super("Welke laptop kies je voor je studies?", Map.of(
            1, "Nieuwe laptop",
            2, "Een tweedehands laptop"
        ),"multi","images/Welke laptop kies je voor je studies.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        Map<String, Double> map =stats.afvalProductie;
        if (keuze == 1) {
            //actie stat Duurste laptop
            //waste zie Thomas
            stats.co2Uitstoot+=14.56;
            stats.financieleImpact-=6.25;
            stats.eenmaligeAankopen-=1200;
            stats.academischeImpact+=2;
            map.put("industrieel afval", map.getOrDefault("industrieel afval", 0.0) + 1200);
            statChange.setCo2UitstootChange(14.56);
            statChange.setFinancieleImpactChange(-6.25);
            statChange.setEenmaligeAankopenChange(-1200);
            statChange.setAcademischeImpactChange(2);
            Map<String, Double> afvalDelta = new HashMap<>();
            afvalDelta.put("industrieel afval", 1200.0);  // alleen de verandering
            statChange.setAfvalProductieChange(afvalDelta);
        } else if (keuze == 2) {
            //actie stat net genoeg laptop
            stats.co2Uitstoot+=1.077;
            stats.financieleImpact-=5.2;
            stats.eenmaligeAankopen-=1000;
            stats.academischeImpact+=1;
            statChange.setCo2UitstootChange(1.077);
            statChange.setFinancieleImpactChange(-5.2);
            statChange.setEenmaligeAankopenChange(-1000);
            statChange.setAcademischeImpactChange(1);
        }
        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new SchoolState();
    }
}
