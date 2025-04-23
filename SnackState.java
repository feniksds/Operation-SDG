import java.util.Map;

public class SnackState extends State {
    public SnackState() {
        super("Welke snacks eet je doorgaans tijdens het studeren?", Map.of(
            1, "Geen snacks",
            2, "Appel",
            3, "Suikerwafel",
            4, "Chips"
        ));
    }

    @Override
    public State verwerkKeuze(int keuze, StudentStats stats) {
        Map<String, Double> map =stats.afvalProductie;
        if(keuze == 1) {
            //geen actie
        }else if (keuze == 2) {
            //appel
            stats.co2Uitstoot+= 0.066*5;
            stats.financieleImpact-=1.60*5;
            stats.academischeImpact+= 1;
        }else if(keuze == 3){
            //suikerwafel
            stats.co2Uitstoot+= 1.1655*5;
            stats.financieleImpact-=0.19*5;
            stats.academischeImpact+= 2;
            map.put("Plastic", map.getOrDefault("Plastic", 0.0) + 0.006*5);
        }else if(keuze == 4){
            //actie stat
            //chips
            stats.co2Uitstoot+=69.3*5;
            stats.financieleImpact-=0.70*5;
            stats.academischeImpact+= 2;
            map.put("Plastic", map.getOrDefault("Plastic", 0.0) + 0.0025*5);
        }
        return new LibraryState();
    }
}
