package a4;

import java.util.Random;

public class Player {
    int[] die;
    int[] pieces;
    int delta;

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
            }

            if (number == 6 && pieces[i] == 0 && takenPlace(1) == -1) {
                pieces[i] = 1;
                return false;
            }
            
            if (pieces[i] > farthestPiece) {
                farthestPiece = pieces[i];
                farthestPieceIndex = i;
            }
        }
        if (farthestPiece > 0 && takenPlace(pieces[farthestPieceIndex] + number) == -1) {
            pieces[farthestPieceIndex] += number;
        }
        return true;
    }

    public int takenPlace(int place){
        for (int i = 0; i < pieces.length; i++) {
            if(pieces[i] == place){
                System.out.println("hallo");
                return i;
            }
        }
        return -1;
    }
}
