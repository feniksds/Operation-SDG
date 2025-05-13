import java.util.HashMap;
import java.util.Map;

public class StatChange {
    private double co2UitstootChange; //houd CO2 uitstoot bij
    private double academischeImpactChange; //houd academische impact bij
    private double financieleImpactChange; //houd financiele impact bij
    private int ritFactorChange; //ritFactor is een factor die andere factoren aanpast naargelang van de gevolgen van het voertuig
    private double eetFactorChange; //eetFactor is een factor die andere factoren aanpast naargelang van de gevolgen van het voedsel
    private double prijsVoedselChange; //prijsVoedsel is een factor die andere factoren aanpast naargelang van de gevolgen van het voedsel
    private double eetCO2Change; //eetCO2 is een factor die andere factoren aanpast naargelang van de gevolgen van het voedsel ( CO2  voor 1 maaltijd ofzo en die dan voor de algemene in rekening gebracht wordt maal aantal dagen)
    private double eenmaligeAankopenChange; // absoluut aantal euro  dat eenmalig aangekocht wordt (is dus een subdeel van financiele impact
    private Map<String, Double> afvalProductieChange;   // bevat de afvalproductie per soort afval (bijv. papier, plastic, etc.) en de bijbehorende verandering in productie

    // Default constructor
    public StatChange() {
        afvalProductieChange = new HashMap<>();
    }

    // Setters to update each value
    public void setCo2UitstootChange(double co2UitstootChange) {
        this.co2UitstootChange = co2UitstootChange;
    }

    public void setAcademischeImpactChange(double academischeImpactChange) {
        this.academischeImpactChange = academischeImpactChange;
    }

    public void setFinancieleImpactChange(double financieleImpactChange) {
        this.financieleImpactChange = financieleImpactChange;
    }

    public void setRitFactorChange(int ritFactorChange) {
        this.ritFactorChange = ritFactorChange;
    }

    public void setEetFactorChange(double eetFactorChange) {
        this.eetFactorChange = eetFactorChange;
    }

    public void setPrijsVoedselChange(double prijsVoedselChange) {
        this.prijsVoedselChange = prijsVoedselChange;
    }

    public void setEetCO2Change(double eetCO2Change) {
        this.eetCO2Change = eetCO2Change;
    }

    public void setEenmaligeAankopenChange(double eenmaligeAankopenChange) {
        this.eenmaligeAankopenChange = eenmaligeAankopenChange;
    }

    public void setAfvalProductieChange(Map<String, Double> afvalProductieChange) {
        this.afvalProductieChange = afvalProductieChange;
    }

    // Getters to retrieve values (optional)
    public double getCo2UitstootChange() {
        return co2UitstootChange;
    }

    public double getAcademischeImpactChange() {
        return academischeImpactChange;
    }

    public double getFinancieleImpactChange() {
        return financieleImpactChange;
    }

    public int getRitFactorChange() {
        return ritFactorChange;
    }

    public double getEetFactorChange() {
        return eetFactorChange;
    }

    public double getPrijsVoedselChange() {
        return prijsVoedselChange;
    }

    public double getEetCO2Change() {
        return eetCO2Change;
    }

    public double getEenmaligeAankopenChange() {
        return eenmaligeAankopenChange;
    }

    public Map<String, Double> getAfvalProductieChange() {
        return afvalProductieChange;
    }
}


