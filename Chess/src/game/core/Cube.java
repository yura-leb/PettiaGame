package game.core;

/**
 * Кубик с 6 гранями.
 * Дает случайное число от 1 до 6.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Cube implements IRandomGenerator {
	private int N = 6;

	private int value;
	public Cube(int n) {
		value = n;
	}
	
	public Cube() {
		drop();
	}

	@Override
	public void drop() {
		value = 1 + (int)(Math.random() * ((N - 1) + 1));
	}

	@Override
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString() {
		return "" + getValue();
	}
}