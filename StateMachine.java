import java.util.Scanner;

public class StateMachine {
    private State huidigeState;
    private Scanner scanner;

    public StateMachine(State startState) {
        this.huidigeState = startState;
        this.scanner = new Scanner(System.in);
    }

    // Toon de huidige status
    public void toonStatus() {
        huidigeState.toonStatus();
    }

    // Maak een keuze en ga naar de volgende staat
    public void maakKeuze() {
        if (huidigeState.naam.equals("Start")) {
            keuzeEnergie();
        } else if (huidigeState.naam.equals("Energie")) {
            keuzeGrondstoffen();
        } else if (huidigeState.naam.equals("Grondstoffen")) {
            keuzeWerknemers();
        } else {
            eindResultaat();
        }
    }

    // Keuze voor energie-opwekking
    private void keuzeEnergie() {
        System.out.println("\nKeuze 1: Hoe genereer je energie?");
        System.out.println("1. Zonnepanelen (Duurzaam maar duur)");
        System.out.println("2. Kerncentrale (Goedkoper, maar risico)");
        System.out.println("3. Steenkoolcentrale (Goedkoop, maar vervuilend)");
        System.out.print("Maak een keuze (1, 2, 3): ");
        int keuze = scanner.nextInt();

        // Update statistieken en ga naar de volgende staat
        if (keuze == 1) {
            huidigeState.winst -= 200;
            huidigeState.duurzaamheid += 20;
            huidigeState.reputatie += 10;
            huidigeState = new State("Grondstoffen", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        } else if (keuze == 2) {
            huidigeState.winst += 150;
            huidigeState.duurzaamheid -= 10;
            huidigeState.co2 += 50;
            huidigeState = new State("Grondstoffen", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        } else if (keuze == 3) {
            huidigeState.winst += 100;
            huidigeState.co2 += 100;
            huidigeState.reputatie -= 20;
            huidigeState = new State("Grondstoffen", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        }
    }

    // Keuze voor grondstoffen en duurzaam inkopen
    private void keuzeGrondstoffen() {
        System.out.println("\nKeuze 2: Grondstoffen en Duurzaam Inkopen?");
        System.out.println("1. Duurzame materialen (Hogere kosten, maar goed voor het milieu)");
        System.out.println("2. Goedkopere materialen (Lagere kosten, maar slechter voor het milieu)");
        System.out.print("Maak een keuze (1, 2): ");
        int keuze = scanner.nextInt();

        if (keuze == 1) {
            huidigeState.winst -= 100;
            huidigeState.duurzaamheid += 10;
            huidigeState.reputatie += 10;
            huidigeState = new State("Werknemers", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        } else if (keuze == 2) {
            huidigeState.winst += 100;
            huidigeState.co2 += 50;
            huidigeState.reputatie -= 10;
            huidigeState = new State("Werknemers", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        }
    }

    // Keuze voor arbeidsomstandigheden
    private void keuzeWerknemers() {
        System.out.println("\nKeuze 3: Arbeidsomstandigheden?");
        System.out.println("1. Goede arbeidsomstandigheden (Hogere loonkosten, maar tevreden werknemers)");
        System.out.println("2. Lage lonen (Lagere kosten, maar ontevreden werknemers)");
        System.out.print("Maak een keuze (1, 2): ");
        int keuze = scanner.nextInt();

        if (keuze == 1) {
            huidigeState.winst -= 150;
            huidigeState.werknemers += 10;
            huidigeState.reputatie += 15;
            huidigeState = new State("Eind", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        } else if (keuze == 2) {
            huidigeState.winst += 150;
            huidigeState.werknemers -= 10;
            huidigeState.reputatie -= 20;
            huidigeState = new State("Eind", huidigeState.winst, huidigeState.duurzaamheid, huidigeState.reputatie, huidigeState.werknemers, huidigeState.co2);
        }
    }

    // Eindresultaat
    private void eindResultaat() {
        System.out.println("\nEindstatus:");
        huidigeState.toonStatus();
        if (huidigeState.winst < 0) {
            System.out.println("\nJe bedrijf heeft grote verliezen geleden door slechte keuzes.");
        } else if (huidigeState.co2 > 200) {
            System.out.println("\nJe bedrijf heeft enorme milieuschade veroorzaakt.");
        } else {
            System.out.println("\nJe bedrijf heeft een balans gevonden tussen winst en duurzaamheid.");
        }
    }
}
