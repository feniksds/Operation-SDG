public class Main {
    public static void main(String[] args) {
        State startState = new State("Start", 1000, 50, 50, 100, 0);
        StateMachine stateMachine = new StateMachine(startState);

        // Speel het spel
        stateMachine.toonStatus();
        stateMachine.maakKeuze();
        stateMachine.toonStatus();
        stateMachine.maakKeuze();
        stateMachine.toonStatus();
        stateMachine.maakKeuze();
    }
}
