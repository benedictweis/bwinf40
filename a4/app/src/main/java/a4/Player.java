package a4;

import java.util.Random;

public class Player {
    int[] die;
    int[] pieces;
    int delta;
    Player opponent;

    public Player(int[] die, int delta) {
        this.die = die;
        this.pieces = new int[] { 0, 0, 0, 0 };
        this.delta = delta;
    }

    public void movePiece(int pieceNumber, int amount) {
        if (pieceNumber < 0 || pieceNumber > 3) {
            return;
        }
        pieces[pieceNumber] += amount;
    }

    public int rollDice() {
        Random randy = new Random();
        int randomNum = randy.nextInt(die.length);
        return die[randomNum];
    }

    public boolean takeTurn() {
        int number = rollDice();
        int farthestPiece = -1;
        int farthestPieceIndex = 0;
        
        for (int i = 0; i < pieces.length; i++) {
            if (number == 6 && pieces[i] == 1 && takenPlace(pieces[i] + number) == -1) {
                pieces[i] += number;
                kickOutOpponent(pieces[i]);
                takeTurn();
                return false;
            }

            if (number == 6 && pieces[i] == 0 && takenPlace(1) == -1) {
                pieces[i] = 1;
                kickOutOpponent(pieces[i]);
                takeTurn();
                return false;
            }
            
            if (pieces[i] > farthestPiece && pieces[i] + number < 53 && takenPlace(pieces[i] + number) == -1) {
                farthestPiece = pieces[i];
                farthestPieceIndex = i;
            }
        }
        if (farthestPiece > 0) {
            pieces[farthestPieceIndex] += number;
            kickOutOpponent(pieces[farthestPieceIndex]);
        }
        if (number == 6 && !gameWon()) takeTurn();
        // hier ist ein Problem
        return gameWon();
    }

    public int takenPlace(int place){
        for (int i = 0; i < pieces.length; i++) {
            if(pieces[i] == place){
                return i;
            }
        }
        return -1;
    }

    public boolean gameWon(){
        for (int i: pieces){
            if (i<41) return false;
        }
        return true;
    }

    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    public void kickOutOpponent(int position){
        for (int i = 0; i < opponent.pieces.length; i++) {
            if (opponent.pieces[i] != 0 && opponent.pieces[i] < 41 && opponent.pieces[i] + delta == position){
                opponent.pieces[i] = 0;
            }
        }
    }
}
