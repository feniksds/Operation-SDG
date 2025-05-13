import java.util.List;
import java.util.Map;
//TODO Radi checken
public class EtenStartState extends State {
    public EtenStartState() {
        super("Hoe groot is je huishouden?", Map.of(
            1, "Alleenstaand",
            2, "Huishouden van 3 personen",
            3, "Huishouden van 4 personen",
            4, "Huishouden van 5 personen"
        ),"multi","images/Hoe groot is je huishouden.png");
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats, List<LogEntry> logEntries) {
        StatChange statChange = new StatChange();

        if (keuze == 1) {
            //alleenstaand
            stats.eetFactor = 1;
            statChange.setEetFactorChange(1);
        }else if (keuze == 2) {
            stats.eetFactor = 0.85;
            statChange.setEetFactorChange(0.85);
        }else if(keuze == 3){
            //huishouden 4 personen
            stats.eetFactor = 0.75;
            statChange.setEetFactorChange(0.75);
        }else if(keuze == 4){
            //actie 5 personen
            stats.eetFactor = 0.70;
            statChange.setEetFactorChange(0.70);
        }

        logEntries.add(new LogEntry(this.beschrijving,opties.get(keuze),statChange));
        return new GasState();
    }

}
