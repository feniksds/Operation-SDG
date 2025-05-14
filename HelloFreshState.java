import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken  RADI
public class HelloFreshState extends State {
    public HelloFreshState() {
        super("Hoe vaak per week eet je kant-en-klare maaltijden", Map.of(),"input","images/Hoe vaak per week eet je kant-en-klare maaltijden.png"); // lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍽️ Hoe vaak per week eet je kant-en-klare maaltijden (zoals HelloFresh, Mealhero...)? ");
    }

    @Override
    public State verwerkKeuze(int _unused, StudentStats stats, List<LogEntry> logEntries) {
        Map<String, Double> map =stats.afvalProductie;

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


        // voorbeeldimpact per maaltijd
        stats.financieleImpact += aantalKeer * 11.98;   // bv. 10 euro per maaltijd
        stats.co2Uitstoot += aantalKeer * 8.1;       // bv. 700g CO₂ per maaltijd
        map.put("Plastic", map.getOrDefault("Plastic", 0.0) + 0.038);
        map.put("Karton", map.getOrDefault("Karton", 0.0) + 0.097);
         */

        // voorbeeldimpact per maaltijd
        StatChange statChange = new StatChange();
        stats.financieleImpact += _unused * 11.98;   // bv. 10 euro per maaltijd
        stats.co2Uitstoot += _unused * 8.1;       // bv. 700g CO₂ per maaltijd
        map.put("Plastic", map.getOrDefault("Plastic", 0.0) + 0.038* _unused);
        map.put("Karton", map.getOrDefault("Karton", 0.0) + 0.097 * _unused);
        statChange.setFinancieleImpactChange(_unused * 11.98);
        statChange.setCo2UitstootChange(_unused * 8.1);

        Map<String, Double> afvalDelta = new HashMap<>();
        afvalDelta.put("Plastic", 0.038 * _unused);
        afvalDelta.put("Karton", 0.097 * _unused);
        statChange.setAfvalProductieChange(afvalDelta);

        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));
        return new StudyState();
    }
}
