public class StudentStats {
    public int co2Uitstoot = 0;
    public int energieVerbruik = 0;
    public int afvalProductie = 0;

    public void toonStats() {
        System.out.println("\n📊 Huidige statistieken:");
        System.out.println("🌍 CO₂-uitstoot: " + co2Uitstoot);
        System.out.println("⚡ Energieverbruik: " + energieVerbruik);
        System.out.println("🗑️ Afvalproductie: " + afvalProductie);
    }
}
