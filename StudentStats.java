import java.util.HashMap;
import java.util.Map;

public class StudentStats {
    public int co2Uitstoot = 0;
    public int academischeImpact = 0;
    public int FinancieleImpact = 0;
    public Map<String, Integer> afvalProductie = new HashMap<>();


    public void toonStats() {
        System.out.println("\n📊 Huidige statistieken:");
        System.out.println("🌍 CO₂-uitstoot: " + co2Uitstoot);
        for(Map.Entry<String, Integer> entry : afvalProductie.entrySet()) {
            System.out.println("🗑️ Afvalproductie van " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("📚 Academische impact: " + academischeImpact);
        System.out.println("💰 Financiële impact: " + FinancieleImpact);
    }
}
