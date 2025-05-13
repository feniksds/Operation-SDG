import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SubscribeState extends State {

    public SubscribeState() {
        // Geef een lege opties-map mee zodat er niets wordt weergegeven in de standaard toonOpties()
        super("Streaminggebruik", Map.of(),"input","images/Hoeveel uur stream je gemiddeld per week.png");
    }

    @Override
    public void toonOpties() {
        // Override de standaard tekst om rechtstreeks je vraag te stellen
         System.out.print("\n🎥 Hoeveel uur stream je gemiddeld per week? ");
    }

    @Override
    public State verwerkKeuze(int _unused, StudentStats stats, List<LogEntry> logEntries) {
        /*
        Scanner scanner = new Scanner(System.in);
        double aantalUren = -1;

        while (aantalUren < 0) {
            try {
                String input = scanner.nextLine();
                aantalUren = Double.parseDouble(input);

                if (aantalUren < 0) {
                    System.out.print("❌ Geef een positief getal: ");
                }

            } catch (NumberFormatException e) {
                System.out.print("❌ Ongeldige invoer. Geef een getal in: ");
            }
        }

        stats.financieleImpact += 15; // bv. 15 euro voor Netflix
        stats.co2Uitstoot += aantalUren * 0.015; // 15g CO₂ per uur streaming
        stats.toonStats();

         */


        stats.financieleImpact += 15; // bv. 15 euro voor Netflix
        stats.co2Uitstoot += _unused * 0.015; // 15g CO₂ per uur streaming
        stats.toonStats();

        StatChange statChange = new StatChange();
        statChange.setCo2UitstootChange( _unused * 0.015);
        statChange.setFinancieleImpactChange(15);

        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));

        return null; // of de volgende echte state
    }
}
