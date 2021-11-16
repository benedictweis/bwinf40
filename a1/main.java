public class main {
    // CODE REVIEW: This method has a constructor name, might be problematic
    public static void main(String[] args) {
        String[] cases;
        if(args.length==0){
            System.out.println("ERROR: Keine Parameter gefunden");
            return;
        }
        if (args[0].equals("all")) {
            cases = new String[10];
            for (int i = 0; i < 10; i++) {
                cases[i] = "./examples/parkplatz" + String.valueOf(i) + ".txt";
            }
        } else {
            cases = args;
        }
        for (int i = 0; i < cases.length; i++) {
            String fileName = cases[i];
            new SchiebeParkplatz(fileName);
        }
    }
}