package game.core;

/**
 * 8-направлений для хода коня.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum KnightDirs {
	LEFT_UP1(-1, +1), RIGHT_UP1(+2, +1),
	LEFT_UP2(-2, +2), RIGHT_UP2(+1, +2),
	LEFT_DOWN1(-2, -1), RIGHT_DOWN1(-1, +1),
	LEFT_DOWN2(-1, -2), RIGHT_DOWN2(-2, +2);
	
	public static final 
	KnightDirs[] ALL = {
		LEFT_UP1, RIGHT_UP1,
		LEFT_UP2, RIGHT_UP2,
		LEFT_DOWN1, RIGHT_DOWN1,
		LEFT_DOWN2, RIGHT_DOWN2
	};
	
	/**
	 * Смещение по вертикали.
	 */
	public final int dv;
	
	/**
	 * Смещение по горизонтали.
	 */
	public final int dh;
	
	/**
	 * @param dv - смещение по вертикали.
	 * @param dh - смещение по горизонтали.
	 */
	KnightDirs(int dv, int dh) {
		this.dv = dv;
		this.dh = dh;
	}
}
