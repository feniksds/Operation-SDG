import java.util.HashMap;
import java.util.Map;

//TODO checken
public class StudentStats {
    public double co2Uitstoot = 0; //in kg
    public double academischeImpact = 0; //in punten
    public double financieleImpact = 0; //in euro
    public int ritFactor = 0; //factor voor treinreizen
    public double eetFactor=1;
    public double prijsVoedsel=0;
    public double eetCO2=0;
    public Map<String, Double> afvalProductie = new HashMap<>();  //ook in kg
    //voorlopig 2 soorten Plastic  en  Allerlei

    public void toonStats() {
        System.out.println("\n📊 Huidige statistieken:");
        System.out.println("🌍 CO₂-uitstoot: " + co2Uitstoot+ " kg");
        for(Map.Entry<String, Double> entry : afvalProductie.entrySet()) {
            System.out.println("🗑️ Afvalproductie van " + entry.getKey() + ": " + entry.getValue()+ " kg");
        }
        System.out.println("📚 Academische impact: " + academischeImpact+ " (een hogere score wijst op betere studieresultaten)");
        System.out.println("💰 Financiële impact: " + financieleImpact+ " euro");
    }
}
