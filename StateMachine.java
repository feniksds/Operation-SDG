import java.util.*;

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
            int keuze = scanner.nextInt();
            huidigeState = huidigeState.verwerkKeuze(keuze, stats);
        }
        stats.toonStats();
        System.out.println("\n📌 Dag afgelopen! Dit was jouw impact op de wereld.");
    }

    public void evalueer() {

    }
}