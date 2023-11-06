package game.core;

/**
 * Генератор случайных челых чисел.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IRandomGenerator {
	/**
	 * Поллучить случайное челое число.
	 */
	void drop();
	
	/**
	 * @return текущее значение генератора.
	 */
	int getValue();
}
