import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class main
{
    Settings settings = new Settings();
    String alphabet[]="ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    ArrayList<String> lines=new ArrayList<String>();
    String finalSolution[], straight[], parallel[];
    String selectSolution[]= new String[2];
    int iterations[]={0, 0};
    /**
     *  fills the straight- & parallel-car arrays with the content of the .txt file
     *  and doing some visuialization stuff for better understanding of the solution
     **/
    public main(int fileIndex)
    {   
        //Clearing the console
        if(settings.getSettings()[0]){
            System.out.print('\u000C');
        }
        //Initializing variables for the visualization
        String fileName="parkplatz"+fileIndex+".txt";
        String visualizeStraight = "";
        String visualizeParallel = "";
        /* Reading File */
        try{
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            System.out.println("ERROR: Invalid filename");            
            return;
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
            parallel[Integer.parseInt(parallelln[1])+1]=parallelln[0];
            parallel[Integer.parseInt(parallelln[1])]=parallelln[0];
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
        // Visualization of the two arrays for better understanding of the solution
        System.out.println("Searching solution of: \""+fileName+"\" ☺\n");
        System.out.println("straight cars: \t"+visualizeStraight);
        System.out.println("parallel cars: \t"+visualizeParallel);
        if(canSolve()==0){
            System.out.println("\nNote:\nBased on the composition of the parallel cars, no Solution for the straight cars exist");
        } else if(settings.getSettings()[1]){
            AutosAusparken();
        }
    }

    public void AutosAusparken()
    {   
        if(canSolve()==0.5){
            System.out.println("\nNote:\nBased on the composition of the parallel cars, not all of the straight cars have a Solution");
            System.out.println("\n\n"+"Solution: \n"); 
        } else if(canSolve()==1){
            System.out.println("\n\n"+"Solution: \n");
        }
        for(int straightIndex=0;straightIndex<straight.length;straightIndex++){
            if(imagineBruteforce(straightIndex)){
                System.out.println(finalSolution[straightIndex]);
            } else {
                System.out.println(straight[straightIndex]+": No solution for this car");
            }
            resetCars();
        }
    }

    /**
     *  moves the selected parallel car one or two positions to the right
     *  index: location of the car
     *  distance: how many positions the car shall be moved
     *  parallelIndex: to which car the operations should be assosiated with
     **/
    public boolean moveDirection(int parallelIndex, int distance, int straightIndex, int direction){
        int secondChar=calcSecondChar(parallelIndex);
        int freePath=calcFreeSpace(parallelIndex)[direction];
        try{
            if((noArrayBoundaries(parallelIndex, distance)[direction])&&
            (freePath>=distance)){      
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                iterations[direction]+=distance;
                return true;
            } else if((noArrayBoundaries(parallelIndex, distance)[direction])&&
            (freePath<distance)){     
                int newParallelIndex=(parallelIndex-(2-secondChar)-freePath)*(1-direction)+(parallelIndex+secondChar+1+freePath)*direction;
                int newDistance=1;
                if(distance==2){
                    newDistance=2;
                    newDistance-=freePath;
                }
                if(!moveDirection(newParallelIndex, newDistance, straightIndex, direction)) {
                    return false;
                }
                calcMovement(parallelIndex, distance, direction);
                calcSolution(parallelIndex, distance, straightIndex, direction);
                iterations[direction]+=distance;
                return true;
            }
            return false;
        }
        catch (Exception e){
            iterations[direction]=0;
            return false;
        }
    }

    /**
     *  resets the parallel cars to their original position
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

    public void calcSolution(int parallelIndex, int distance, int straightIndex, int direction){
        String[] directions= {"left","right"};
        if(direction==0){
            parallelIndex-=distance;
        } else {
            parallelIndex+=distance;
        }
        if(selectSolution[direction]==null){
            selectSolution[direction]=straight[straightIndex]+": "+parallel[parallelIndex]+" "+distance+" "+directions[direction];
        } else if(selectSolution[direction]!=null){
            selectSolution[direction]+=", "+parallel[parallelIndex]+" "+distance+" "+directions[direction];
        }
    }

    public void calcMovement(int parallelIndex, int distance, int direction){
        String parallelName=parallel[parallelIndex];
        int secondChar=calcSecondChar(parallelIndex);
        if((distance==1)&&
        (direction==1)){
            parallel[parallelIndex+(1+secondChar)]=parallelName;
            parallel[parallelIndex-(1-secondChar)]=null;
        } else if((distance==2)&&
        (direction==1)){
            parallel[parallelIndex+(2+secondChar)]=parallelName;
            parallel[parallelIndex+(1+secondChar)]=parallelName;
            parallel[parallelIndex+secondChar]=null;
            parallel[parallelIndex-(1-secondChar)]=null;
        } else if((distance==1)&&
        (direction==0)){
            parallel[parallelIndex-(2-secondChar)]=parallelName;
            parallel[parallelIndex+(secondChar)]=null;
        } else if((distance==2)&&
        (direction==0)){
            parallel[parallelIndex-(3-secondChar)]=parallelName;
            parallel[parallelIndex-(2-secondChar)]=parallelName;
            parallel[parallelIndex-(1-secondChar)]=null;
            parallel[parallelIndex+secondChar]=null;
        }
    }

    public int calcParallelLimit(int parallelIndex, int limit){
        /* Returncodes: -1*limit -> outside left from array; limit -> outside right from array; 0 -> inside of array */
        if(parallelIndex-limit<0){
            return -limit;
        } else if(parallelIndex+limit>=parallel.length){
            return limit;
        }
        return 0;
    }

    public int calcSecondChar(int parallelIndex){
        //Returncodes: 0-> secondChar is left; 1-> secondChar is right
        int limit=calcParallelLimit(parallelIndex, 1);
        if(limit==1){   
            return 0;
        } else if(limit== -1){    
            return 1;
        } else{
            if(parallel[parallelIndex]==parallel[parallelIndex+1]){
                return 1;
            }
            return 0;
        }  
    }

    public boolean[] noArrayBoundaries(int parallelIndex, int distance){
        int limit=calcParallelLimit(parallelIndex, distance);
        int secondChar=calcSecondChar(parallelIndex);
        boolean moveLR[]={true, true};
        if(limit == -distance){
            moveLR[0]=false;
        }
        if(limit == distance){
            moveLR[1]=false;
        }
        return moveLR;
    }

    public boolean[] noCarBoundaries(int parallelIndex, int distance){
        //Basicly the same as "noArrayBoundaries" but also includes other cars in its judgement
        int limit[]=calcFreeSpace(parallelIndex);
        boolean moveLR[]={false, false};
        if(limit[1]>=distance){
            moveLR[1]=true;
        }
        if(limit[0]>=distance){
            moveLR[0]=true;
        }
        return moveLR;
    }

    public int[] calcFreeSpace(int parallelIndex){
        int[] freeSpace={-1, -1};
        int secondChar=calcSecondChar(parallelIndex);
        if(parallelIndex<0||parallelIndex>=parallel.length){
            return freeSpace;
        }
        String parallelName=parallel[parallelIndex];
        for(int R=parallelIndex;R<parallel.length;R++){
            if((R==parallel.length-1)&&(parallel[R]==null)){
                freeSpace[1]=R-parallelIndex;
                break;
            } else if((parallel[R]!=null)&&(parallel[R]!=parallelName)){
                freeSpace[1]=R-parallelIndex-1;
                break;
            }
            freeSpace[1]=0;
        }
        for(int L=parallelIndex;L>=0;L--){
            if((L==0)&&(parallel[L]==null)){
                freeSpace[0]=parallelIndex;
                break;
            } else if((parallel[L]!=null)&&(parallel[L]!=parallelName)){
                freeSpace[0]=parallelIndex-L-1;
                break;
            }
            freeSpace[0]=0;
        }
        if((secondChar==1)&&(freeSpace[1]!=0)){
            freeSpace[1]--;
        } else if((secondChar==0)&&(freeSpace[0]!=0)){
            freeSpace[0]--;
        }
        return freeSpace;
    }

    public double canSolve(){
        int j=0;
        for(int i=0;i<parallel.length;i++){
            if(parallel[i]==null){
                j++;
            }
        }
        if(j==0){   //nicht lösbar
            return 0;
        } else if(j==1){    //nur die hälfte lösbar
            return 0.5;
        } else if(j>=2){    //sollte lösbar sein
            return 1;
        }
        return -1;
    }

    public boolean imagineBruteforce(int straightIndex){
        iterations[1]=0;
        iterations[0]=0;
        selectSolution[0]=null;
        selectSolution[1]=null;
        int secondChar=calcSecondChar(straightIndex);
        if(parallel[straightIndex]==null){  //exit path is empty
            finalSolution[straightIndex]=straight[straightIndex]+": ";
            return true;
        } else{
            for(int i=1;i>=0;i--){// for-loop to get both directions
                moveDirection(straightIndex, (1+secondChar)*(1-i)+(2-secondChar)*i, straightIndex, i);
                resetCars();
            }
            if(selectSolution[1]==null && selectSolution[0]==null){
                return false;
            } else if(iterations[0]==0 || selectSolution[0]==null){
                finalSolution[straightIndex]=selectSolution[1];
            } else if(iterations[1]==0 || selectSolution[1]==null){
                finalSolution[straightIndex]=selectSolution[0];
            } else if(iterations[1]<=iterations[0]){
                finalSolution[straightIndex]=selectSolution[1];
            } else if(iterations[1]>iterations[0]){
                finalSolution[straightIndex]=selectSolution[0];
            }
            return true;
        }
    }
}