package reversi.players;

import static game.core.GameResult.DRAWN;
import static game.core.GameResult.win;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.IPieceProvider;
import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.core.moves.PassMove;
import game.players.PutPiecePlayer;

/**
 * Базовый класс для всех игроков в реверси.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract 
public class ReversiPlayer extends PutPiecePlayer {

	public ReversiPlayer(IPieceProvider pieceProvider) {
		super(pieceProvider);
	}

	/**
	 * Находится ли клетка на границе доски.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	protected boolean isBorder(Square s) {
		Board b = s.getBoard();
		
		return (s.v == 0) || 
			   (s.h == 0) || 
			   (s.v == b.nV-1) ||
			   (s.h == b.nH-1) ;
	}

	/**
	 * Находится ли клетка в углу доски.
	 * @param s - проверяемая клетка.
	 * @return
	 */
	protected boolean isCorner(Square s) {
		Board b = s.getBoard();
		
		if ((s.v == 0) && (s.h == 0)) return true;
		if ((s.v == 0) && (s.h == b.nH-1)) return true;
		if ((s.v == b.nV-1) && (s.h == 0)) return true;
		if ((s.v == b.nV-1) && (s.h == b.nH-1)) return true;
	
		return false;
	}

	@Override
	public void doMove(Board board, PieceColor color) throws GameOver {
		PieceColor enemyColor = Board.getEnemyColor(color);
		List<Piece> enemies = board.getPieces(enemyColor);
		
		if (enemies.isEmpty()) {
            // Окружать некого. Пропускаем ход.
            if (board.history.getLastMove() instanceof PassMove)
                stopGame(board, new PassMove()); // У обоих игроков нет корретных ходов.
            
            continueGame(board, new PassMove()); // Передаем ход противнику.
            return;
		}
		
		List<Move> correctMoves = getCorrectMoves(board, color);
		
		if (correctMoves.isEmpty()){
            continueGame(board, new PassMove()); // Передаем ход противнику.
			return;
		}
		
		Collections.shuffle(correctMoves);
		
		correctMoves.sort( getComparator() );
		Move bestMove = correctMoves.get(0);
		
        if (bestMove instanceof PassMove && board.history.getLastMove() instanceof PassMove)
            stopGame(board, bestMove); // У обоих игроков нет корректных ходов.
	
		try { bestMove.doMove(); } 
		catch (GameOver e) {
			board.history.setResult(e.result);
			stopGame(board, bestMove);
		}
		
		continueGame(board, bestMove);
	}
	
    private void continueGame(Board board, Move bestMove) throws GameOver {
        board.history.addMove(bestMove);

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged();

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        // Сохраняем в истории игры последний сделанный ход
        // и результат игры.
        if (board.history.getMoves().size() > 200)
            stopGame(board, bestMove);
    }
    
    private void stopGame(Board board, Move lastMove) throws GameOver {
        board.history.addMove(lastMove);

        int blacks = board.getPieces(PieceColor.BLACK).size();
        int whites = board.getPieces(PieceColor.WHITE).size();

       GameResult result = blacks > whites ? win(PieceColor.BLACK) : ( blacks < whites ? win(PieceColor.WHITE) : DRAWN);
            
        board.history.setResult(result);

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged();

        throw new GameOver(result);
    }


	abstract public Comparator<? super Move> getComparator();
}