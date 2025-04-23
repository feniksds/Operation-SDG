import java.util.Map;
//TODO stats veranderingen  + vraag aan Thomas die 1200 kilo afval wa voor soort is da?

public class FinancialState extends State {
    public FinancialState() {
        super("Welke laptop kies je voor je studies?", Map.of(
            1, "De beste en duurste laptop",
            2, "Een laptop met net genoeg \n specificaties voor schoolwerk",
            3, "Een tweedehands laptop"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        if (keuze == 1) {
            //actie stat Duurste laptop
            //waste zie Thomas
        } else if (keuze == 2) {
            //actie stat net genoeg laptop
        }else if(keuze == 3){
            //actie stat tweedehands laptop
        }
        return new SchoolState();
    }
}
