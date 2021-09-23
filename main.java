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
    String fileName = "parkplatz1.txt";
    /**
     *  fills the straight- & parallel-car arrays with the content of the .txt file   
     **/
    public main()
    {
        String visualizeStraight = "";
        String visualizeParallel = "";
        /* Reading File */
        File file = new File(fileName);
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
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

        for(int i = 0; i<straight.length; i++){
            visualizeStraight += straight[i]+" ";
        }

        for(int i = 0; i<parallel.length; i++){
            if(parallel[i]!=null){
                visualizeParallel += parallel[i]+" ";
            } else {
                visualizeParallel += "# ";
            }
        }
        finalSolution=new String[straight.length];
        System.out.println("Searching solution of: \""+fileName+"\" ☺\n");
        System.out.println("straight cars: \t"+visualizeStraight);
        System.out.println("parallel cars: \t"+visualizeParallel);
        AutosAusparken();
    }

    /**
     *  goes through all straight cars and calls the "findPath" function on them
     **/
    public void AutosAusparken()
    {   
        System.out.println("\n"+"Solution:");
        for(int straightIndex=0;straightIndex<straight.length;straightIndex++){
            findPath(straightIndex);
            resetCars();
            System.out.println(finalSolution[straightIndex]);
        }
    }

    /**
     *  moves the selected parallel car one or two positions to the right
     *  index: location of the car
     *  distance: how many positions the car shall be moved
     *  parallelIndex: to which car the operations should be assosiated with
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
     *  moves the selected parallel car one or two positions to the left
     *  index: location of the car
     *  distance: how many positions the car shall be moved
     *  parallelIndex: to which car the operations should be assosiated with
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
                    if(parallel[parallelIndex-2]==null){    //path is empty
                        parallel[parallelIndex - 2]=parallelName;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex-1, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-2]!=null){   //path is blocked
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
                if((parallelIndex - 1)<0){
                }
                else if(parallel[parallelIndex-1]==parallelName){   //second char is to the left
                    if(parallel[parallelIndex-3]==null){    //path is empty
                        parallel[parallelIndex-3]=parallelName;
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-2]!=null){   //if path is blocked by other car at left right index
                        if(!moveRight(parallelIndex-2, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex-3]=parallelName;
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-3]!=null){   //path is blocked by other car at far left index
                        if(!moveRight(parallelIndex-3, distance-1, straightIndex)){
                            return false;
                        }
                        parallel[parallelIndex-3]=parallelName;
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=null;
                        parallel[parallelIndex]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                }
                if((parallelIndex + 2)>parallel.length){
                }
                else if(parallel[parallelIndex+1]==parallelName){   //second char is to the right
                    if(parallel[parallelIndex-2]==null){    //path is empty
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex+1]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-1]!=null){   //path is blocked by other car at near left index
                        if(!moveRight(parallelIndex-1, distance, straightIndex)) {
                            return false;
                        }
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex+1]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                    else if(parallel[parallelIndex-2]!=null){   //path is blocked by other car at far left index
                        if(!moveLeft(parallelIndex-2, distance-1, straightIndex)){
                            return false;
                        }
                        parallel[parallelIndex-2]=parallelName;
                        parallel[parallelIndex-1]=parallelName;
                        parallel[parallelIndex]=null;
                        parallel[parallelIndex+1]=null;
                        calcSolution(parallelIndex-2, distance, straightIndex, -1);
                        return true;
                    }
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     *  resets the parallel cars to their start position
     **/
    public void resetCars(){
        for(int i=0;i<parallel.length;i++){ //overwriting the array with "null"
            parallel[i]=null;
        }
        for(int i=2;i<lines.size();i++){    //refilling it with the data from the .txt file
            String parallelln[]=lines.get(i).split(" ");
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
        }
    }

    /**
     *  adds the moved cars to the solution
     **/
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
    }

    /**
     *  chooses the optimal way to move out the selected car
     *  index: location of the straight car the shall be moved out
     **/
    public void findPath(int straightIndex){
        String parallelName=parallel[straightIndex];
        try{
            if(parallel[straightIndex]==null){  //exit path is empty
                finalSolution[straightIndex]=straight[straightIndex]+": ";
            }
            else{
                if((straightIndex - 1)<0){
                }
                else if(parallel[straightIndex - 1]==parallelName){ //second char is to the left
                    if((straightIndex-2)<0){
                    }
                    else if(parallel[straightIndex-2]==null){   //free space to the right side
                        if(moveLeft(straightIndex, 1, straightIndex)){
                            return;
                        }
                    }
                    if((straightIndex+2)>parallel.length){
                    }
                    else if(parallel[straightIndex+1]==null){   // empty space to the left side
                        if(moveRight(straightIndex, 2, straightIndex)){
                            return;
                        }
                    }
                    //no free space at all
                    if(!moveLeft(straightIndex, 1, straightIndex)){
                        moveRight(straightIndex, 2, straightIndex);
                    }
                    return;
                }
                if((straightIndex + 1)>parallel.length){
                }
                else if(parallel[straightIndex+1]==parallelName){ //second char is to the right
                    if((straightIndex+2)>=parallel.length){
                    }
                    else if(parallel[straightIndex+2]==null){   //empty space to right side
                        moveRight(straightIndex, 1 , straightIndex);
                        return;
                    }
                    if((straightIndex-2)<0){
                    }
                    else if(parallel[straightIndex-1]==null){
                        moveLeft(straightIndex, 2, straightIndex);
                        return;
                    }
                    if(!moveRight(straightIndex, 1, straightIndex)){
                        moveLeft(straightIndex, 2, straightIndex);
                    }
                    return;
                }
            }
            return;
        }
        catch(Exception e){
            System.out.print(e);
            e.printStackTrace();
            return;
        }
    }
}