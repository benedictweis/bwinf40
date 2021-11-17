public class Main {
    public static void main(String[] args) {
        Marktwaage m;
        if (args.length == 1) {
            m = new Marktwaage(args[0]);
        } else {
            System.out.println("Fehler: unzulässige Argumente");
            System.out.println("Benutze standardmaessig gewichtsstuecke1.txt");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            m = new Marktwaage("gewichtsstuecke0.txt");
        }
        m.alleberechnen();
        m.umrechnen();
        m.ausgabe();
    }
}
