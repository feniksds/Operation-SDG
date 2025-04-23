import java.util.Map;

public class BookState extends State {
    public BookState() {
        super("Hoe koop je je studieboeken?", Map.of(
            1, "In digitale vorm",
            2, "Nieuw",
            3, "Tweedehands"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if(keuze == 1) {
            //niks
            stats.academischeImpact+= 1;
        }else if (keuze == 2) {
            //nieuw
            stats.co2Uitstoot+= 7.2;
            stats.financieleImpact-= Main.monthToWeekly(10);
            stats.academischeImpact+= 2;
        }else if(keuze == 3){
            //2de hands
            stats.financieleImpact-= Main.monthToWeekly(5);
            stats.academischeImpact-= 1;
        }
        return new JobState();
    }
}
