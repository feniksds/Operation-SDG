import java.util.Map;

public class JobState extends State{
    public JobState() {
        super("Heb je een studentenjob?", Map.of(
            1, "Nee",
            2, "Weekendjob",
            3, "Parttime",
            4, "Fulltime"
        ),"multi","images/Heb je een studentenjob.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if(keuze == 1) {
            //nee
            stats.academischeImpact += 2;
        }else if(keuze == 2) {
            //weekend
            stats.financieleImpact += Main.monthToWeekly(227);
            stats.academischeImpact -=1;
        }else if(keuze == 3) {
            //part
            stats.financieleImpact += Main.monthToWeekly(737.75);
            stats.academischeImpact -= 2;
        }else if(keuze == 4) {
            //full
            stats.financieleImpact += Main.monthToWeekly(2156.5);
            stats.academischeImpact -=3;
        }
        return new ClothingState();
    }
}
