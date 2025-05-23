import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken Radi
public class VeganState extends State {
    public VeganState() {
        super("Hoe vaak per week eet je vegan of vegetarisch", Map.of(),"input","images/Hoe vaak per week eet je vegan of vegetarisch.png"); // geen standaardopties
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🌱 Hoe vaak per week eet je vegan of vegetarisch? ");
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

        // Voorbeeldimpact: veganistisch eten bespaart CO₂-uitstoot en afval
        stats.co2Uitstoot -= aantalKeer * 1.67*0.25;

        stats.toonStats();

         */

        // Voorbeeldimpact: veganistisch eten bespaart CO₂-uitstoot en afval
        stats.co2Uitstoot -= _unused * 1.67*0.25;

        stats.toonStats();
        StatChange statChange = new StatChange();
        statChange.setEetCO2Change(- _unused * 1.67*0.25);
        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));

        return new FruitState(); // volgende state
    }
}
