package breakthrough.players;

import game.core.*;
import breakthrough.moves.*;
import game.core.moves.ITransferMove;

import java.util.List;

/**
 * <a href=
 * "https://ru.wikipedia.org/wiki/%D0%90%D1%81%D0%BA%D0%BB%D0%B5%D0%BF%D0%B8%D0%B9">
 * Аскле́пий </a>
 */
public class Aesculapius extends BreakThroughPlayer {
	@Override
	public String getName() {
		return "Аскле́пий";
	}

	@Override
	public String getAuthorName() {
		return "Пришлецов Сергей";
	}

	protected int getWeight(Move m) {
		int weight = 0;
		ITransferMove transfer = (ITransferMove) m;
		Square source = transfer.getSource();
		Board board = source.getBoard();
		/*chem blizhe vrazheskaya figura, tem vazhnee ee skushat BEGIN*/
		int minH = m.getPiece().getColor() == PieceColor.WHITE ? 0 : 5;
		List<Move> correctMoves = getCorrectMoves(board, m.getPiece().getColor());
		for(Move mm : correctMoves){
			if(mm instanceof Capture){
				if(
						(m.getPiece().getColor() == PieceColor.WHITE
						&& ((Capture) mm).getSource().h > minH)
						||
						(m.getPiece().getColor() != PieceColor.WHITE
						&& ((Capture) mm).getSource().h < minH)
				) minH = ((Capture) mm).getSource().h;
			}
		}
		if(
				minH == ((ITransferMove)m).getSource().h
				&& m instanceof Capture
		){
			weight+=1;
		}
		/*chem blizhe vrazheskaya figura, tem vazhnee ee skushat END*/

		//esli bidem kushat, to davayte kushat
		if(m instanceof Capture){
			weight+=1;
		}
		return weight;
	}
}
