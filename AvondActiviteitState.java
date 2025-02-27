import java.util.Map;

public class AvondActiviteitState extends State {
    public AvondActiviteitState() {
        super("Wat doe je 's avonds?", Map.of(
            1, "Uitgaan (alcoholproductie = CO₂-uitstoot)",
            2, "Gamen (hoog elektriciteitsverbruik)",
            3, "Sporten in de fitness (materiaalimpact)",
            4, "Rustig thuis (laagste impact)"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            stats.co2Uitstoot += 15;
        } else if (keuze == 2) {
            stats.energieVerbruik += 30;
        } else if (keuze == 3) {
            stats.afvalProductie += 5;
        }
        return null;
    }
}
