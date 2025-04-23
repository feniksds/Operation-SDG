import java.util.Map;
import java.util.Scanner;
//TODO checken Radi
public class VleesState extends State {
    public VleesState() {
        super("🥩 Vleesconsumptie", Map.of()); // lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🥩 Hoe vaak per week eet je vlees? ");
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

        // Voorbeeld-impact van vleesconsumptie
        stats.co2Uitstoot += aantalKeer * 22.6*0.15;

        return new VeganState(); // volgende state
    }
}
