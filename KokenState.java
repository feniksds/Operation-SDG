import java.util.List;
import java.util.Map;
//TODO Radi checken
public class KokenState extends State{
    public KokenState() {
        super("Kook je zelf of doet iemand anders dat voor jou?", Map.of(
            1, "Ik kook zelf",
            2, "Iemand anders kookt"
        ),"multi","images/Kook je zelf of doet iemand anders dat voor jou.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();
        if (keuze == 1) {
            //zelf
            stats.prijsVoedsel+= 7.57 * stats.eetFactor;
            statChange.setPrijsVoedselChange(7.57 * stats.eetFactor);
        } else if (keuze == 2) {
            //iemand anders
            //niks volgens Radi.
        }

        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new VleesState();
    }
}
