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
            System.out.println("Gewichtsstuecke 0:");
            a.alleberechnen();
            a.umrechnen();
            a.ausgabe();
            System.out.println();
            System.out.println();
            System.out.println("Gewichtsstuecke 1:");
            System.out.println();
            b.alleberechnen();
            b.umrechnen();
            b.ausgabe();
            System.out.println();
            System.out.println();
            System.out.println("Gewichtsstuecke 2:");
            System.out.println();
            c.alleberechnen();
            c.umrechnen();
            c.ausgabe();
            System.out.println();
            System.out.println();
            System.out.println("Gewichtsstuecke 3:");
            System.out.println();
            d.alleberechnen();
            d.umrechnen();
            d.ausgabe();
            System.out.println();
            System.out.println();
            System.out.println("Gewichtsstuecke 4:");
            System.out.println();
            e.alleberechnen();
            e.umrechnen();
            e.ausgabe();
            System.out.println();
            System.out.println();
            System.out.println("Gewichtsstuecke 5:");
            System.out.println();
        }else{
            m=new Marktwaage(args[0]);
        }
        m.alleberechnen();
        m.umrechnen();
        m.ausgabe();
    }
}
