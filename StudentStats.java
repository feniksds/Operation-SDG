import java.util.HashMap;
import java.util.Map;

//TODO  deftig coherent maken statistieken en tonen
public class StudentStats {
    public double co2Uitstoot = 0; //in kg
    public double academischeImpact = 0; //in punten
    public double FinancieleImpact = 0; //in euro
    public int treinFactor = 0; //factor voor treinreizen
    public boolean afstandsFactor = false; //true als ver
    public Map<String, Double> afvalProductie = new HashMap<>();  //ook in kg
    //voorlopig 2 soorten Plastic  en  Allerlei

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
