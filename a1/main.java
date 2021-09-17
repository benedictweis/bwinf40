import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String solution[][]=new String[alphabet.length][alphabet.length];
    String nf[];
    String qf[];
    public main()
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
    }
    public void AutosAusparken()
    {   
        /* Cars with no sidewys-car behind them gets moved out first */
        for(int i=0;i<nf.length;i++){
            String carName=nf[i];
            if(qf[i]==null){
                solution[i][0]=carName+":";
            }
        }
        printResult();
    }
    public int moveRight(int index){
        int status=9999;
        try{
            String carName = qf[index];
            if(carName==null){
                return -9999;
            }
            if((index - 1)<0){
                status=(-9999);
            }
            else if(qf[index - 1]==carName){
                if(qf[index+1]==null){
                    qf[index+1]=carName;
                    qf[index-1]=null;
                    status=0;
                }
                else if(qf[index+1]!=null&&qf[index+1]!=carName){
                    return 1;
                }
            }
            if(status==0){
                return status;
            }
            if((index + 2)>qf.length){
                status=(-9999);
            }
            else if(qf[index + 1]==carName){
                if(qf[index+2]==null){
                    qf[index + 2]=carName;
                    qf[index]=null;
                    status=0;
                }
                else if(qf[index+2]!=null&&qf[index+2]!=carName){
                    return 1;
            }
            }
            if(status==0){
                return status;
            }
            return status;
        }
        catch (Exception e){
            return -9999;
        }
    }
    public int moveLeft(int index){
        /* If moving the car fails, an error code is returned */
        /* Returncodes: -9999-> array-data-error, -1-> there is a car to the left, 0-> all went well, 1-> there is a car to the right, 9999-> code didnt work */
        int status=9999;
        try{
            String carName=qf[index];
            if(carName==null){
                return -9999;
            }
            if((index - 2)<0){      
                status=(-9999);
            }
            else if(qf[index - 1]==carName){
                if(qf[index-2]==null){
                    qf[index - 2]=carName;
                    qf[index]=null;
                    status=0;
                }
                else if(qf[index-2]!=null&&qf[index-2]!=carName){
                    return -1;
            }
            }
            if(status==0){
                return status;
            }
            if((index + 1)>qf.length){
                status=(-9999);
            }
            else if(qf[index + 1]==carName){
                if(qf[index-1]==null){
                    qf[index - 1]=carName;
                    qf[index + 1]=null;
                    status=0;
                }
                else if(qf[index-1]!=null&&qf[index-1]!=carName){
                    return -1;
            }
            }
            if(status==0){
                return status;
            }
            return status;
        }
        catch(Exception e){
            return -9999;
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
    public void printResult(){
        int x=0;
        int y=0;
        for(;x<solution.length;x++){
            y=0;
            for(;y<solution[x].length;y++){
                if(solution[x][y]==null){
                    break;
                }
                else{
                    System.out.println(solution[x][y]);
                }
            }
        }
    }
}
