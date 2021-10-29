import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main{
    public static void main(String[]args){
        for(int i=0;i<args.length;i++){
            String fileName="./examples/parkplatz"+args[i]+".txt";
            File file=new File(args[i]);
            if(file==null){
            }else {
                new SchiebeParkplatz(fileName);   
            }
        }
    }
}