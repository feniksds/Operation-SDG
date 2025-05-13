import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TODO checken  RADI
public class TakeawayState extends State {
    public TakeawayState() {
        super("🍱 Afhaalmaaltijden", Map.of(),"input", "images/Hoe vaak per week bestel je Takeaway (zoals Deliveroo, Uber Eats, lokale restaurants...).png"); // geen standaardopties
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍱 Hoe vaak per week bestel je Takeaway (zoals Deliveroo, Uber Eats, lokale restaurants...)? ");
    }

    @Override
    public State verwerkKeuze(int _unused, StudentStats stats, List<LogEntry> logEntries) {
        /*
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

        // Voorbeeldimpact per bestelling
        stats.financieleImpact += aantalKeer * 10;           // bv. €13 per bestelling
        stats.co2Uitstoot += aantalKeer * (0.06 + 0.72 + 0.34) / 3;                // bv. 2.8 kg CO₂ per maaltijd

        stats.toonStats();

         */

        // Voorbeeldimpact per bestelling
        stats.financieleImpact += _unused * 10;           // bv. €13 per bestelling
        stats.co2Uitstoot += _unused * (0.06 + 0.72 + 0.34) / 3;                // bv. 2.8 kg CO₂ per maaltijd

        stats.toonStats();

        StatChange statChange = new StatChange();
        statChange.setEetCO2Change(_unused * (0.06 + 0.72 + 0.34) / 3);
        statChange.setCo2UitstootChange(_unused * 10);

        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));
        return new HelloFreshState(); // volgende state
    }
}
