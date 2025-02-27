public class State {
    String naam;
    int winst;
    int duurzaamheid;
    int reputatie;
    int werknemers;
    int co2;

    public State(String naam, int winst, int duurzaamheid, int reputatie, int werknemers, int co2) {
        this.naam = naam;
        this.winst = winst;
        this.duurzaamheid = duurzaamheid;
        this.reputatie = reputatie;
        this.werknemers = werknemers;
        this.co2 = co2;
    }

    // Toon de huidige status van het bedrijf
    public void toonStatus() {
        System.out.println("\n" + naam + " status:");
        System.out.println("Winst: " + winst);
        System.out.println("Duurzaamheid: " + duurzaamheid);
        System.out.println("Reputatie: " + reputatie);
        System.out.println("Werknemers: " + werknemers);
        System.out.println("CO2-uitstoot: " + co2);
    }
}
