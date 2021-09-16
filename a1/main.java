import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String nf[];
    String qf[];
    public main()
    {
    }

    public void AutosAusparken()
    {   
        /* Reading File */
        File file = new File("parkplatz0.txt");
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println(lines);
        /* Getting each char, line by line  */
        String firstln[]=lines.get(0).split(" ");
        String thirdln[]=lines.get(2).split(" ");

        /* Filling array with the normal-cars */
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i].equalsIgnoreCase(firstln[1])){
                nf=new String[i+1];
                qf=new String[i+1];
                for(int j=0;j<nf.length;j++){
                    nf[j]=alphabet[j];
                }
            }
        }

        /* Filling array with the sideways-cars */
        for(int i=2;i<lines.size();i++){
            String qfln[]=lines.get(i).split(" ");
            qf[Integer.parseInt(qfln[1])]=qfln[0];
            qf[Integer.parseInt(qfln[1])+1]=qfln[0];
        }
        /* Cars with no sidewys-car behind them gets moved out first */
        for(int i=0;i<nf.length;i++){}
    }

    public int moveRight(int index){
        String carName = qf[index];
        if(carName==null||((index + 2)>=qf.length)&&qf[qf.length]==carName){
            return -9999;
        }
        else if(index==0){
            qf[index + 2]=carName;
            qf[index]=null;
            return 0;
        }
        else{
            if(qf[index - 1]==carName){
                qf[index+1]=carName;
                qf[index-1]=null;
            }
            else if(qf[index + 1]==carName){
                qf[index + 2]=carName;
                qf[index]=null; 
            }
            
            return 0;
        }
    }

    public int moveLeft(int index){
        String carName=qf[index];
        if(carName==null||(index - 1)<0){
            return -9999;
        }
        else if(index==qf.length){
            qf[index - 2]=carName;
            qf[index]=null;
            return 0;
        }
        else {
            if(qf[index + 1]==carName){
                qf[index - 1]=carName;
                qf[index + 1]=null;
            }
            else if(qf[index - 1]==carName){
                qf[index - 2]=carName;
                qf[index]=null;
            }
            return 0;
        }
    }

    public void resetQF(){
        for(int i=0;i<qf.length;i++){
            qf[i]=null;
        }
        for(int i=2;i<lines.size();i++){
            String qfln[]=lines.get(i).split(" ");
            qf[Integer.parseInt(qfln[1])]=qfln[0];
            qf[Integer.parseInt(qfln[1])+1]=qfln[0];
        }
    }
}
