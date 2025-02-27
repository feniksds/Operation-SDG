import java.util.Map;

public class ReizenNaarGentState extends State {
    public ReizenNaarGentState() {
        super("Hoe reis je naar Gent?", Map.of(
            1, "Met de auto (CO₂ + filevorming)",
            2, "Met de trein (lage CO₂, maar druk)"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            stats.co2Uitstoot += 30;
            stats.afvalProductie += 1;
        } else if (keuze == 2) {
            stats.co2Uitstoot += 5;
        }
        return new LunchState();
    }
}
