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

    /**
     * Move a specified piece by a specified amount.
     * 
     * @param pieceNumber index of the piece in pieces
     * @param amount      number of spaces to move the piece
     */
    public void movePiece(int pieceNumber, int amount) {
        if (pieceNumber < 0 || pieceNumber > 3) {
            return;
        }
        pieces[pieceNumber] += amount;
    }

    /**
     * Generates a random dice roll.
     * 
     * @return value of the dice roll
     */
    public int rollDice() {
        Random randy = new Random();
        int randomNum = randy.nextInt(die.length);
        return die[randomNum];
    }

    /**
     * Takes a turn within the game.
     * 
     * @return true if the game is won, false if not
     */
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
        if (number == 6 && !gameWon())
            takeTurn();
        return gameWon();
    }

    /**
     * Determines which piece of this player is in a specified place.
     * 
     * @param place place on the board to check
     * @return index of the piece on this field, if there is none then -1
     */
    public int takenPlace(int place) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == place) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determines whether this Player has won the game
     * 
     * @return true if the game is won, false if it isn't
     */
    public boolean gameWon() {
        for (int i : pieces) {
            if (i < 41)
                return false;
        }
        return true;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * brings the opponents piece back to the start
     * 
     * @param position position of piece to be brought back to start, relative to
     *                 this player
     */
    public void kickOutOpponent(int position) {
        for (int i = 0; i < opponent.pieces.length; i++) {
            if (opponent.pieces[i] != 0 && opponent.pieces[i] < 41 && opponent.pieces[i] + delta == position) {
                opponent.pieces[i] = 0;
            }
        }
    }

    /**
     * Determines whether a dice is playable (i.e. at least one six, not all faces
     * six)
     * 
     * @return true, if the dice does not work, false if it does work
     */
    public boolean faultyDice() {
        boolean containsSix = false;
        for (int i : die) {
            if (i == 6)
                containsSix = true;
        }
        if (!containsSix)
            return true;
        for (int i : die) {
            if (i != 6)
                return false;
        }
        return true;
    }

    /**
     * Determines whether a player can still make any move.
     * 
     * @return false, if there is no move a player can make, true if there is at
     *         least one move the player can make.
     */
    public boolean possibleWin() {
        for (int piece : pieces) {
            for (int value : die) {
                if (piece + value < 44 && takenPlace(piece + value) == -1) {
                    return true;
                }
            }
        }
        return false;
    }
}
