public class LogEntry {
    private String vraag;
    private String gemaakteKeuze;
    private StatChange statChange;

    // Constructor
    public LogEntry(String vraag, String gemaakteKeuze, StatChange statChange) {
        this.vraag = vraag;
        this.gemaakteKeuze = gemaakteKeuze;
        this.statChange = statChange;
    }

    // Getters
    public String getVraag() {
        return vraag;
    }

    public String getGemaakteKeuze() {
        return gemaakteKeuze;
    }

    public StatChange getStatChange() {
        return statChange;
    }

    // Optioneel: een toString methode voor gemakkelijke log-output
    @Override
    public String toString() {
        return "LogEntry{" +
                "vraag='" + vraag + '\'' +
                ", gemaakteKeuze='" + gemaakteKeuze + '\'' +
                ", statChange=" + statChange +
                '}';
    }
}

