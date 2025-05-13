//DEPRECATED
//DEPRECATED
//DEPRECATED
//DEPRECATED
//DEPRECATED
import java.util.List;
import java.util.Map;

public class ReizenNaarGentState extends State {
    public ReizenNaarGentState() {
        super("Hoe reis je naar Gent?", Map.of(
            1, "Met de auto (CO₂ + filevorming)",
            2, "Met de trein (lage CO₂, maar druk)"
        ),"multi","images/Hoe reis je naar Gent.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        Map<String, Double> map =stats.afvalProductie;
        if (keuze == 1) {
            stats.co2Uitstoot += 30;
            map.put("Industrieel afval", map.getOrDefault("Industrieel afval", 0.0) + 1);
        } else if (keuze == 2) {
            stats.co2Uitstoot += 5;
        }

        stats.toonStats();
        return null;
    }
}
