import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken  RADI
public class FastFoodState extends State {
    public FastFoodState() {
        super("Hoe vaak per week eet je fastfood", Map.of(),"input","images/Hoe vaak per week eet je fastfood (zoals McDonald's, Quick...).png"); // geen standaardopties
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍔 Hoe vaak per week eet je fastfood (zoals McDonald's, Quick...)? ");
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

        // Voorbeeldimpact per fastfoodmaaltijd
        stats.financieleImpact += aantalKeer *9.95 ;       // bv. €9 per maaltijd
        stats.co2Uitstoot += aantalKeer * (2.35+0.15+0.255);            // bv. 2.1 kg CO₂ per maaltijd

         */
        StatChange statChange = new StatChange();
        stats.financieleImpact -= _unused *9.95 ;       // bv. €9 per maaltijd
        stats.co2Uitstoot += _unused * (2.35+0.15+0.255);// bv. 2.1 kg CO₂ per maaltijd
        statChange.setFinancieleImpactChange(- _unused *9.95);
        statChange.setCo2UitstootChange(_unused * (2.35+0.15+0.255));
        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));
        return new TakeawayState(); // volgende state
    }
}
