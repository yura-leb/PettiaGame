package snakegame.pieces;

abstract public class Piece {
    private Square square;

    public Piece(Square square) {
        setSquare(square);
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
        square.piece = this;
    }
}
