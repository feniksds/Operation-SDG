import java.util.List;
import java.util.Map;
import java.util.Scanner;
//TODO checken Radi
public class FruitState extends State {
    public FruitState() {
        super("Hoe vaak per week eet je fruit", Map.of(),"input","images/Hoe vaak per week eet je fruit.png"); // geen standaardopties
    }

    @Override
    public void toonOpties() {
        System.out.print("\n🍎 Hoe vaak per week eet je fruit? ");
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

        stats.co2Uitstoot += aantalKeer * 3.40*0.13;

         */
        StatChange statChange = new StatChange();
        stats.co2Uitstoot += _unused * 3.40*0.13;
        statChange.setCo2UitstootChange(_unused * 3.40*0.13);
        logEntries.add(new LogEntry(this.beschrijving,Integer.toString(_unused),statChange));
        return new AmountState(); // volgende state
    }
}
