import java.util.Map;

public class FinancialState extends State {
    public FinancialState() {
        super("Welke laptop kies je voor je studies?", Map.of(
            1, "Nieuwe laptop",
            2, "Een tweedehands laptop"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        Map<String, Double> map =stats.afvalProductie;
        if (keuze == 1) {
            //actie stat Duurste laptop
            //waste zie Thomas
            stats.co2Uitstoot+=280;
            stats.financieleImpact-=1200;
            stats.academischeImpact+=2;
            map.put("industrieel afval", map.getOrDefault("industrieel afval", 0.0) + 1200);
        } else if (keuze == 2) {
            //actie stat net genoeg laptop
            stats.co2Uitstoot+=56;
            stats.financieleImpact-=1000;
            stats.academischeImpact+=1;
        }
        return new SchoolState();
    }
}
