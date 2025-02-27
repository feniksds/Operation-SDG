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
        if (keuze == 1) {
            stats.afvalProductie += 1;
        } else if (keuze == 2) {
            stats.afvalProductie += 5;
        } else if (keuze == 3) {
            stats.afvalProductie += 10;
            stats.co2Uitstoot += 20;
        }
        return new AvondActiviteitState();
    }
}
