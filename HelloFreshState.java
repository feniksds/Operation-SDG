import java.util.Map;
import java.util.Scanner;

public class HelloFreshState extends State {
    public HelloFreshState() {
        super("🍽️ Maaltijdbezorging", Map.of()); // lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍽️ Hoe vaak per week eet je kant-en-klare maaltijden (zoals HelloFresh, Mealhero...)? ");
    }

    @Override
    public State verwerkKeuze(int _unused, StudentStats stats) {
        Map<String, Double> map =stats.afvalProductie;
        Scanner scanner = new Scanner(System.in);
        int aantalKeer = -1;

        while (aantalKeer < 0) {
            try {
                String input = scanner.nextLine();
                aantalKeer = Integer.parseInt(input);

                if (aantalKeer < 0) {
                    System.out.print("❌ Geef een positief getal: ");
                }

            } catch (NumberFormatException e) {
                System.out.print("❌ Ongeldige invoer. Geef een geheel getal in: ");
            }
        }

        // voorbeeldimpact per maaltijd
        stats.financieleImpact += aantalKeer * 11.98;   // bv. 10 euro per maaltijd
        stats.co2Uitstoot += aantalKeer * 8.1;       // bv. 700g CO₂ per maaltijd
        map.put("Plastic", map.getOrDefault("Plastic", 0.0) + 0.038);
        map.put("Karton", map.getOrDefault("Karton", 0.0) + 0.097);

        return new BoodschappenState(); // of een andere logische volgende state
    }
}
