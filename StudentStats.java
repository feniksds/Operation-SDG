import java.util.HashMap;
import java.util.Map;

//TODO  deftig coherent maken statistieken en tonen
public class StudentStats {
    public double co2Uitstoot = 0;
    public double academischeImpact = 0;
    public double FinancieleImpact = 0;
    public int treinFactor = 0;
    public Map<String, Double> afvalProductie = new HashMap<>();


    public void toonStats() {
        System.out.println("\n📊 Huidige statistieken:");
        System.out.println("🌍 CO₂-uitstoot: " + co2Uitstoot);
        for(Map.Entry<String, Double> entry : afvalProductie.entrySet()) {
            System.out.println("🗑️ Afvalproductie van " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("📚 Academische impact: " + academischeImpact);
        System.out.println("💰 Financiële impact: " + FinancieleImpact);
    }
}
