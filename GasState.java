import java.util.Map;
//TODO Radi checken
public class GasState extends State {
    public GasState() {
        super("Kook je thuis op gas of inductie?", Map.of(
            1, "Gas",
            2, "Inductie"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //Gas
            stats.eetCO2 += Main.yearToDaily(80);
        } else if (keuze == 2) {
            //Inductie
            stats.eetCO2 += Main.yearToDaily(60);
        }
        return new KokenState();
    }
}
