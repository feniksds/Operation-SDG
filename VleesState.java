import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken Radi
public class VleesState extends State {
    public VleesState() {
        super("🥩 Vleesconsumptie", Map.of(),"input","images/Vleesconsumptie.png"); // lege map zodat standaard opties niet worden getoond
    }

    @Override
    public void toonOpties() {
        System.out.print("\n Hoe vaak per week eet je vlees? ");
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


        // Voorbeeld-impact van vleesconsumptie
        stats.co2Uitstoot += aantalKeer * 22.6*0.15;
         */

        stats.co2Uitstoot += _unused * 22.6*0.15;

        stats.toonStats();

        StatChange statChange = new StatChange();
        statChange.setEetCO2Change(_unused * 22.6*0.15);
        logEntries.add(new LogEntry("Vleesconsumptie",Integer.toString(_unused),statChange));

        return new VeganState(); // volgende state
    }
}
