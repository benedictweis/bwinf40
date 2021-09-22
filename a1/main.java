import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main
{
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String finalSolution[];
    String straight[];
    String parallel[];
    /**
     *  fills the straight- & parallel-car arrays with the content of the .txt file   
     **/
    public main()
    {
        /* Reading File */
        File file = new File("parkplatz1.txt");
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
        /* Filling array with the straight-cars */
        for(int i=0;i<alphabet.length;i++){
            if(alphabet[i].equalsIgnoreCase(firstln[1])){
                straight=new String[i+1];
                parallel=new String[i+1];
                for(int j=0;j<straight.length;j++){
                    straight[j]=alphabet[j];
                }
            }
        }
        /* Filling array with the parallel-cars */
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
        finalSolution=new String[straight.length];
    }

    /**
     *  goes through all straight cars and calls the "findPath" function on them
     **/
    public void AutosAusparken()
    {   
        for(int i=0;i<straight.length;i++){
            if(straight[i]==null){
                break;
            }
            findPath(i);
            resetCars();
        }
        printResult();
    }

    /**
     *  moves the selected car to its selected position(one or two positions to the right)
     *  @param index: location of the parallel car
     *  @param distance: how many positions the parallel car shall be moved
     *  @param parallelIndex: to which car the operations should be assosiated with
     **/
    public boolean moveRight(int parallelIndex, int distance, int straightIndex){
        /* Returncodes: false-> error, true->done */
        try{
            String parallelName = parallel[parallelIndex];
            if(parallelName==null){
                return false;
            }
            if(distance==1){    //car shall be moved one position
                if((parallelIndex - 1)<0){
                }
                else if(parallel[parallelIndex-1]==parallelName){   //second char is to the left
                    if(parallel[parallelIndex+1]==null){    //path is empty
                        parallel[parallelIndex+1]=parallelName;
                        parallel[parallelIndex-1]=null;
                        calcSolution(parallelIndex+1, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+1]!=null){   //path is blocked
                        if(!moveRight(parallelIndex+1, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex+1] = parallelName;
                        parallel[parallelIndex-1] = null;
                        calcSolution(parallelIndex+1, distance, straightIndex, 1);
                        return true;
                    }
                }
                if((parallelIndex + 2)>parallel.length){
                }
                else if(parallel[parallelIndex+1]==parallelName){   //second char is to the right
                    if(parallel[parallelIndex+2]==null){    //path is empty
                        parallel[parallelIndex + 2]=parallelName;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex+1, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+2]!=null){   //path is blocked
                        if(!moveRight(parallelIndex+2, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex + 2]=parallelName;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex+1, distance, straightIndex, 1);
                        return true;
                    }
                }
            }
            else if(distance==2){   //car shall be moved two positions
                if((parallelIndex - 1)<0){
                }
                else if(parallel[parallelIndex-1]==parallelName){   //second char is to the left
                    if(parallel[parallelIndex+2]==null){    //path is empty
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex-1]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+1]!=null){   //if path is blocked by other car at near right index
                        if(!moveRight(parallelIndex+1, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex-1]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+2]!=null){   //path is blocked by other car at far right index
                        if(!moveRight(parallelIndex+2, distance-1, straightIndex)){
                            return false;
                        }
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex-1]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                }
                if((parallelIndex + 2)>parallel.length){
                }
                else if(parallel[parallelIndex+1]==parallelName){   //second char is to the right
                    if(parallel[parallelIndex+3]==null){    //path is empty
                        parallel[parallelIndex+3]=parallelName;
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+2]!=null){   //path is blocked by other car at near right index
                        if(!moveRight(parallelIndex+2, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex+3]=parallelName;
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                    else if(parallel[parallelIndex+3]!=null){   //path is blocked by other car at far right index
                        if(!moveRight(parallelIndex+3, distance-1, straightIndex)){
                            return false;
                        }
                        parallel[parallelIndex+3]=parallelName;
                        parallel[parallelIndex+2]=parallelName;
                        parallel[parallelIndex+1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex+2, distance, straightIndex, 1);
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     *  moves the selected car to its selected position(one or two positions to the left)
     *  @param index: location of the parallel car
     *  @param distance: how many positions the parallel car shall be moved
     *  @param parallelIndex: to which car the operations should be assosiated with
     **/
    public boolean moveLeft(int parallelIndex, int distance, int straightIndex){
        /* Returncodes: false-> error, true->done */
        try{
            String parallelName=parallel[parallelIndex];
            if(parallelName==null){ //if there isnt a car at index returns error
                return false;
            }
            if(distance==1){
                if((parallelIndex - 2)<0){      
                }
                else if(parallel[parallelIndex - 1]==parallelName){
                    if(parallel[parallelIndex-2]==null){
                        parallel[parallelIndex - 2]=parallelName;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex-1, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-2]!=null){
                        if(!moveLeft(parallelIndex-2, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex-2] = parallelName;
                        parallel[parallelIndex] = null;
                        calcSolution(parallelIndex-1, distance, straightIndex, -1);
                        return true;
                    }
                }
                if((parallelIndex + 1)>parallel.length){
                }
                else if(parallel[parallelIndex + 1]==parallelName){
                    if(parallel[parallelIndex-1]==null){
                        parallel[parallelIndex - 1]=parallelName;
                        parallel[parallelIndex + 1]=null;
                        calcSolution(parallelIndex-1, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-1]!=null){
                        if(!moveLeft(parallelIndex-1, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex-1] = parallelName;
                        parallel[parallelIndex+1] = null;
                        calcSolution(parallelIndex-1, distance, straightIndex, -1);
                        return true;
                    }
                }
            }
            else if(distance==2){
            
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
        finally{
            calcSolution(parallelIndex, distance, straightIndex, -1);
        }
    }

    /**
     *  resets the parallel cars to their start position
     **/
    public void resetCars(){
        for(int i=0;i<parallel.length;i++){
            parallel[i]=null;
        }
        for(int i=2;i<lines.size();i++){
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }

    /**
     *  prints the final results to the console  
     **/
    public void printResult(){
        for(int x=0;x<straight.length;x++){
            if(finalSolution[x]==null){
                break;
            }
            else{
                System.out.println(finalSolution[x]);
            }
        }
    }
    
    public void calcSolution(int parallelIndex, int distance, int straightIndex, int direction){
        if(direction==1){
            if(finalSolution[straightIndex]==null){
                finalSolution[straightIndex]=straight[straightIndex]+": "+parallel[parallelIndex]+" "+distance+" right";
            }
            else if(finalSolution[straightIndex]!=null){
                finalSolution[straightIndex]+=", "+parallel[parallelIndex]+" "+distance+" right";
            }
        }
        else if(direction== -1){
            if(finalSolution[straightIndex]==null){
                finalSolution[straightIndex]=straight[straightIndex]+": "+parallel[parallelIndex]+" "+distance+" left";
            }
            else if(finalSolution[straightIndex]!=null){
                finalSolution[straightIndex]+=", "+parallel[parallelIndex]+" "+distance+" left";
            }
        }
        System.out.println(parallelIndex);
        System.out.println(distance);
        System.out.println(straightIndex);
    }

    /**
     *  chooses the optimal way to move out the selected car
     *  @param index: location of the straight car the shall be moved out
     **/
    public void findPath(int straightIndex){
        String parallelName=parallel[straightIndex];
        try{
            if(parallel[straightIndex]==null){
            }
            else{
                if((straightIndex - 1)<0){
                    return;
                }
                else if(parallel[straightIndex - 1]==parallelName){
                    if((straightIndex-2)<0){
                    }
                    else if(parallel[straightIndex-2]==null){
                        moveLeft(straightIndex, 1, straightIndex);
                        return;
                    }
                    if((straightIndex+2)>parallel.length){
                    }
                    else if(parallel[straightIndex+1]==null&&parallel[straightIndex+2]==null){
                        moveRight(straightIndex, 2, straightIndex);
                        return;
                    }
                }
                if((straightIndex + 1)>=parallel.length){
                }
                else if(parallel[straightIndex + 1]==parallelName){
                    if((straightIndex+2)>parallel.length){
                    }
                    else if(parallel[straightIndex+2]==null){
                        moveRight(straightIndex, 1 , straightIndex);
                        return;
                    }
                    if((straightIndex-2)<0){
                    }
                    else if(parallel[straightIndex-1]==null&&parallel[straightIndex-2]==null){
                        moveLeft(straightIndex, 2, straightIndex);
                        return;
                    }
                }
            }
        }
        catch(Exception e){
            return;
        }
    }
}
