import java.util.Map;

public class SchoolState extends State{
    public SchoolState() {
        super("Gebruik je een tekentablet of papier om te tekenen?", Map.of(
            1, "Tekentablet",
            2, "Papier"
        ),"multi","images/Gebruik je een tekentablet of papier om te tekenen.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //tablet
            stats.co2Uitstoot+=3.269;
            stats.financieleImpact-=Main.monthToWeekly(8.68);
        } else if (keuze == 2) {
            //papier
            stats.co2Uitstoot+=2.814;
            stats.financieleImpact-=Main.monthToWeekly(0.94);
        }
        stats.toonStats();

        return new BookState();
    }
}
