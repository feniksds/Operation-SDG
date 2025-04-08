import java.util.Map;

public class LunchState extends State {
    public LunchState() {
        super("Wat eet je op school?", Map.of(
            1, "Zelf meegenomen (weinig afval)",
            2, "Betaalde maaltijd (meer verpakking)",
            3, "Fastfood (veel afval en CO₂-uitstoot)"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        Map<String, Integer> map = stats.afvalProductie;
        if (keuze == 1) {
            map.put("Biologisch afval", map.getOrDefault("Biologisch afval", 0) + 1);
        } else if (keuze == 2) {
            map.put("Rest afval", map.getOrDefault("Rest afval", 0) + 5);
        } else if (keuze == 3) {
            map.put("Rest afval", map.getOrDefault("Rest afval", 0) + 10);
            stats.co2Uitstoot += 20;
        }
        return new AvondActiviteitState();
    }
}
