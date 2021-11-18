public class Main {
    public static void main(String[] args) {
        Marktwaage m;
        if (args.length == 0) {
            System.out.println("Fehler: unzulässige Argumente");
            System.out.println("Benutze standardmaessig gewichtsstuecke1.txt");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            m = new Marktwaage("beispiele/gewichtsstuecke0.txt");
        } else if (args[0].equals("all")) {
            Marktwaage a = new Marktwaage("beispiele/gewichtsstuecke0.txt");
            Marktwaage b = new Marktwaage("beispiele/gewichtsstuecke1.txt");
            Marktwaage c = new Marktwaage("beispiele/gewichtsstuecke2.txt");
            Marktwaage d = new Marktwaage("beispiele/gewichtsstuecke3.txt");
            Marktwaage e = new Marktwaage("beispiele/gewichtsstuecke4.txt");
            m = new Marktwaage("beispiele/gewichtsstuecke5.txt");
            a.alleberechnen();
            a.umrechnen();
            a.ausgabe();
            b.alleberechnen();
            b.umrechnen();
            b.ausgabe();
            c.alleberechnen();
            c.umrechnen();
            c.ausgabe();
            d.alleberechnen();
            d.umrechnen();
            d.ausgabe();
            e.alleberechnen();
            e.umrechnen();
            e.ausgabe();
        }else{
            m=new Marktwaage(args[0]);
        }
        m.alleberechnen();
        m.umrechnen();
        m.ausgabe();
    }
}
