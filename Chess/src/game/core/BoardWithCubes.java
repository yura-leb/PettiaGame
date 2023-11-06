package game.core;

import game.players.IPlayer;

public class BoardWithCubes extends Board {
	public Cube cube1 = new Cube();
	public Cube cube2 = new Cube();
	public Cube cube3 = new Cube(0);
	public Cube cube4 = new Cube(0);

	public BoardWithCubes() {
		super();
	}

	/**
	 * Бросаем два кубика.
	 */
	public void dropCubes() {
		cube1.drop();
		cube2.drop();
		
		if (cube1.getValue() == cube2.getValue()) {
			cube3.setValue(cube1.getValue());
			cube4.setValue(cube2.getValue());
		} else {
			cube3.setValue(0);
			cube4.setValue(0);
		}
	}

	/**
	 * Бросаем два кубика для начала игры.
	 */
	public void dropCubes4Start() {
		do { dropCubes(); }
		while (cube1.getValue() == cube2.getValue());
	}

	@Override
	public void startGame() {
		moveColor = PieceColor.WHITE;
		
		for (;;) {
			IPlayer player = players.get(moveColor);
	
			// Бросаем кубики для хода очередного игрока.
			dropCubes();
			setBoardChanged();
	
			if (player == IPlayer.HOMO_SAPIENCE) 
				break; // Ход сделает человек.
			
			try { player.doMove(this, moveColor); }			
			catch (GameOver e) 
				{ break; }
	
			moveColor = getNextColor(moveColor);
		}
	
	}
}