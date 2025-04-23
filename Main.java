public class Main {
    static int yearToDaily(int getal) {
        return getal * 365;
    }

    static int monthToDaily(int getal) {
        return getal * 30;
    }

    public static void main(String[] args) {
        //TODO alles doorverbinden
        //TODO juiste stats updaten bij overgang
        StateMachine spel = new StateMachine();
        spel.start();
        spel.evalueer();
    }
}
