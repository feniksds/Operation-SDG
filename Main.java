public class Main {
    static double yearToDaily(double getal) {
        return getal/365;
    }

    static double monthToDaily(double getal) {
        return getal/30;
    }

    static double yeartoWeekly(double getal) {
        return getal/52;
    }

    static double monthToWeekly(double getal) {
        return getal/4;
    }

    public static void main(String[] args) {
        StateMachine spel = new StateMachine();
        spel.start();
        spel.evalueer();
    }
}
