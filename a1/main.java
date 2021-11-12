public class main {
    // CODE REVIEW: This method has a constructor name, might be problematic
    public static void main(String[] args) {
        // CODE REVIEW: what is largs supposed to mean?
        String[] largs;
        // CODE REVIEW: this throws an exception if you give no arguments, include
        // default case!
        if (args[0].equals("all")) {
            largs = new String[10];
            for (int i = 0; i < 10; i++) {
                largs[i] = "./examples/parkplatz" + String.valueOf(i) + ".txt";
            }
        } else {
            largs = args;
        }
        for (int i = 0; i < largs.length; i++) {
            String fileName = largs[i];
            new SchiebeParkplatz(fileName);
        }
    }
}