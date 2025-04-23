import java.util.*;
//TODO GUI?
public class StateMachine {
    private State huidigeState;
    private Scanner scanner;
    private StudentStats stats;

    public StateMachine() {
        this.scanner = new Scanner(System.in);
        this.stats = new StudentStats();
        this.huidigeState = new StartState();
    }

    public void start() {
    while (huidigeState != null) {
        huidigeState.toonOpties();
        int keuze = -1;


        if(!huidigeState.opties.isEmpty()){
            // input correct inlezen en valideren
            while (true) {
                try {
                    String input = scanner.nextLine();
                    keuze = Integer.parseInt(input);

                    if (!huidigeState.opties.containsKey(keuze)) {
                        System.out.println("❌ Ongeldige keuze. Kies een nummer uit de lijst.");
                        continue;
                    }
                    break; // geldige keuze
                }catch (NumberFormatException e) {
                    System.out.println("❌ Ongeldige invoer. Geef een getal in.");
                }
            }
        }

        huidigeState = huidigeState.verwerkKeuze(keuze, stats);
    }

    stats.toonStats();
    System.out.println("\n📌 Week afgelopen! Dit was jouw impact op de wereld.");
}

    public void evalueer() {

    }
}