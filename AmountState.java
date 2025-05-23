import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken  RADI
public class AmountState extends State {
    public AmountState() {
        super("Hoe vaak per week wordt er gekookt",
                Map.of(),
                "input",
                "images/Hoe vaak per week wordt er door jou of iemand anders thuis of op kot gekookt.png");; // Lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍳 Hoe vaak per week wordt er door jou of iemand anders thuis of op kot gekookt? ");
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

        // Impact als je zelf kookt
        stats.financieleImpact += aantalKeer*stats.prijsVoedsel; // prijs per maaltijd maal aantal keren
        stats.co2Uitstoot += aantalKeer*stats.eetCO2; ;

         */

        stats.financieleImpact += _unused*stats.prijsVoedsel; // prijs per maaltijd maal aantal keren
        stats.co2Uitstoot += _unused*stats.eetCO2;
        //List<LogEntry> logEntries;
        StatChange statChange = new StatChange();
        statChange.setFinancieleImpactChange(_unused*stats.prijsVoedsel);
        statChange.setCo2UitstootChange(_unused*stats.eetCO2);
        logEntries.add(new LogEntry("🍳 Zelf koken",Integer.toString(_unused),statChange));
        return new FastFoodState(); // volgende logische state
    }
}
