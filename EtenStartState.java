import java.util.Map;
//TODO stats veranderingen aanvullen+ Radi checken
public class EtenStartState extends State {
    public EtenStartState() {
        super("Hoe groot is je huishouden?", Map.of(
            1, "Alleenstaand",
            2, "Huishouden van 3 personen",
            3, "Huishouden van 4 personen",
            4, "Huishouden van 5 personen"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie
        }else if (keuze == 2) {
            //actie
        }else if(keuze == 3){
            //actie
        }else if(keuze == 4){
            //actie
        }
        return new KokenState();
    }

}
