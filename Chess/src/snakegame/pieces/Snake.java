package snakegame.pieces;

import java.util.ArrayList;

public class Snake {
    public ArrayList<Square> body = new ArrayList<>();
    public Directions CurrentDirection = Directions.RIGHT;
    public Board board;
    public boolean IsAlive = true;
    public int Score;

    public Snake(Board board) {
        this.board = board;
        for (int i = 0; i < 3; i++)
            body.add(new Square(board, board.getXLength() / 2 - i, board.getYLength() / 2));

        Score = 0;
    }

    public void move() {
        int prev_x = body.get(0).v;
        int prev_y = body.get(0).h;
        body.remove(body.size() - 1);

        if (CurrentDirection == Directions.UP) {
            if (prev_y < 0) {
                Square sq = new Square(board, prev_x, 30);
                lookForApple(sq);
                moveSnake(sq);
            } else if (prev_y > 30) {
                Square sq = new Square(board, prev_x, 0);
                lookForApple(sq);
                moveSnake(sq);
            } else {
                Square sq = new Square(board, prev_x, prev_y - 1);
                lookForApple(sq);
                moveSnake(sq);
            }
        }

        if (CurrentDirection == Directions.DOWN) {
            if (prev_y < 0) {
                Square sq = new Square(board, prev_x, 30);
                lookForApple(sq);
                moveSnake(sq);
            } else if (prev_y > 30) {
                Square sq = new Square(board, prev_x, 0);
                lookForApple(sq);
                moveSnake(sq);
            } else {
                Square sq = new Square(board, prev_x, prev_y + 1);
                lookForApple(sq);
                moveSnake(sq);
            }
        }

        if (this.CurrentDirection == Directions.LEFT) {
            if (prev_x < 0) {
                Square sq = new Square(board, 30, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            } else if (prev_x > 30) {
                Square sq = new Square(board, 0, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            } else {
                Square sq = new Square(board, prev_x - 1, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
        }

        if (this.CurrentDirection == Directions.RIGHT) {
            if (prev_x < 0) {
                Square sq = new Square(board, 30, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            } else if (prev_x > 30) {
                Square sq = new Square(board, 0, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            } else {
                Square sq = new Square(board, prev_x + 1, prev_y);
                lookForApple(sq);
                moveSnake(sq);
            }
        }
    }

    private void lookForApple(Square sq) {
        if (sq.equals(board.apple.square)) {
            board.apple.isAlive = false;

            int tail_x = body.get(body.size() - 1).h;
            int tail_y = body.get(body.size() - 1).v;
            Square tail = new Square(board, tail_x, tail_y);

            switch (CurrentDirection) {
                case UP:
                    tail.h = tail_x;
                    tail.v = tail_y - 1;
                    break;
                case DOWN:
                    tail.h = tail_x;
                    tail.v = tail_y + 1;
                    break;
                case RIGHT:
                    tail.h = tail_x + 1;
                    tail.v = tail_y;
                    break;
                case LEFT:
                    tail.h = tail_x - 1;
                    tail.v = tail_y;
                    break;
            }
            Score += 1;
            body.add(body.size(), tail);
        }
    }

    private void moveSnake(Square sq) {
        if (checkCollision(sq)) IsAlive = false;
        else body.add(0, sq);
    }

    private boolean checkCollision(Square square) {
        return body.contains(square);
    }
}
