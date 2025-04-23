import java.util.Map;
import java.util.Scanner;

public class VeganState extends State {
    public VeganState() {
        super("🌱 Vegan/vegetarisch", Map.of()); // geen standaardopties
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🌱 Hoe vaak per week eet je vegan of vegetarisch? ");
    }

    @Override
    public State verwerkKeuze(int _unused, StudentStats stats) {
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

        // Voorbeeldimpact: veganistisch eten bespaart CO₂-uitstoot en afval
        stats.co2Uitstoot -= aantalKeer * 1.67*0.25;

        return new FruitState(); // volgende state
    }
}
