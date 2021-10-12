package a4;

import java.util.Random;

public class Player {
    int[] die;
    int[] pieces;
    int delta;

    public Player(int[] die, int delta){
        this.die = die;
        this.pieces = new int[]{0,0,0,0};
        this.delta = delta;
    }

    public void movePiece(int pieceNumber, int amount){
        if (pieceNumber<0 || pieceNumber>3){
            return;
        }
        pieces[pieceNumber] += amount;
    }

    public int rollDice(){
        Random randy= new Random();
        int randomNum = randy.nextInt(die.length);
        return die[randomNum];
    }

    public boolean takeTurn(){
        rollDice();
        return true;
    }
}
