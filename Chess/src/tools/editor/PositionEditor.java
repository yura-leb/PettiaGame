package tools.editor;

import game.core.*;
import game.ui.GameBoard;
import game.ui.GamePanel;

/**
 * Редактор начальных позиций игр.
 */
public class PositionEditor {
    private final Board board;
    private final GameBoard boardPanel;

    public PositionEditor(GamePanel gamePanel) {
        boardPanel = gamePanel.gameBoard;

        Game game = gamePanel.game;
        board = game.board;

        // Создаем для редактора доску с ящиками для фигур.
        // В ящиках фигуры подготовленные для расстановки на доске.
        BoardWithBoxes editorBoard = new BoardWithBoxes();
        editorBoard.reset(game.board.nV, game.board.nV);
        editorBoard.topBox = game.getPieces(PieceColor.BLACK);
        editorBoard.bottomBox = game.getPieces(PieceColor.WHITE);
        game.board = editorBoard;
        gamePanel.gameBoard.board = editorBoard;

        // Задаем слушателя нажатий мыши на клетки доски.
        boardPanel.listener = new EditorListener();

        // Показываем фигуры в панели инструментов редактора.
        gamePanel.adorned.updatePieceBoxes();
    }

    /**
     * Слушатель нажатий мыши на клетки доски.
     */
    private class EditorListener implements game.ui.listeners.IGameListner {
        /**
         * Фигура выбранная в панели инструментов редактора позиции.
         */
        private Piece piece;

        /**
         * Задать фигуру для слушателя.
         * @param p - фигура.
         */
        @Override
        public void setPiece(Piece p) {
            piece = p;

            // Зададим изображение курсора такое как избражение у фигуры.
            boardPanel.pieceToCursor(piece);

            // Пусть слушатели изменений на доске
            // нарисуют новое состояние доски.
            board.setBoardChanged();
            boardPanel.redraw();
        }

        @Override
        public void mouseDown(Square s, int button) {
        }

        @Override
        public void mouseUp(Square s, int button) {
        }
    }
}