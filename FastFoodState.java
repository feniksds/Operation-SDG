import java.util.Map;
//TODO stats veranderingen aanvullen+ vraag fixen
public class FastFoodState extends State{
    public FastFoodState() {
        super("Eet je fastfood?", Map.of(
            1, "Ja",
            2, "Nee"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stats
        } else if (keuze == 2) {
            //actie stats
        }
        return new TakeawayState();
    }
}
