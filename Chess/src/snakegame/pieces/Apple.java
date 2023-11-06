package snakegame.pieces;

import java.util.Random;

public class Apple {
    public Square square;
    public boolean isAlive = true;

    public Apple(Board board){
        Random random = new Random();
        square = new Square(board, random.nextInt(30), random.nextInt(30));
    }
}
