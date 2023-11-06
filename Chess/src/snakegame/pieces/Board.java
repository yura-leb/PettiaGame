package snakegame.pieces;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Board extends Observable {
    private final int X;
    private final int Y;

    private final Square[][] squares;
    public Apple apple;

    public Board(int nV, int nH) {
        this.X = nV;
        this.Y = nH;
        squares = new Square[nV][nH];
        for (int v = 0; v < nV; v++)
            for (int h = 0; h < nH; h++)
                squares[v][h] = new Square(this, v, h);
    }

    public boolean isEmpty(int v, int h) {
        return getSquare(v, h).piece == null;
    }

    public Square getSquare(int v, int h) {
        return squares[v][h];
    }

    public int getXLength() {
        return X;
    }

    public int getYLength() {
        return Y;
    }

    public void setBoardChanged() {
        super.setChanged();
        super.notifyObservers();
    }

    public void startGame(Snake snake) throws InterruptedException {
        snake.move();
        System.out.println(snake.body.get(0).v + " " + snake.body.get(0).h);
        setBoardChanged();
    }
}
