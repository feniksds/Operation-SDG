import java.util.Map;
import java.util.Scanner;

public class AmountState extends State {
    public AmountState() {
        super("🍳 Zelf koken", Map.of()); // Lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍳 Hoe vaak per week kook je zelf thuis of op kot? ");
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

        // Impact als je zelf kookt
        stats.financieleImpact += aantalKeer*stats.prijsVoedsel; // prijs per maaltijd maal aantal keren
        stats.co2Uitstoot += aantalKeer*stats.eetCO2; ; // voorbeeld: zelf koken vereist planning

        return new FastFoodState(); // volgende logische state
    }
}
