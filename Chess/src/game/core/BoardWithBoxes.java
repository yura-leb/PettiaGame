package game.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Доска для ящиками для фигур. Может использоваться для:
 * <ul>
 *     <li>игр в которых фигуры не сразу ставятся на доску (королевские шахматы)</li>
 *     <li>игр в которых фигуры снимаются с доски и ставятся на доску (японские шахматы)</li>
 *     <li>редакторе начальных позиций игр</li>
 * </ul>
 */
public class BoardWithBoxes extends Board {
    /**
     * Верхний ящик фигур.
     */
    public List<Piece> topBox = new ArrayList<>();

    /**
     * Нижний ящик фигур.
     */
    public List<Piece> bottomBox = new ArrayList<>();
    
    @Override
	public void startGame() {
		topBox.clear();
		bottomBox.clear();
		setBoardChanged();
		
		super.startGame();
	}
}
