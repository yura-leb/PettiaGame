package chess.pgn;

import static game.core.GamesConfig.pgnRoot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import chess.Chess;
import game.core.Board;
import game.core.Move;
import game.core.PieceColor;

/**
 * Класс для чтения игр из текстового файла в стандартной шахматной нотации
 * Portable Game Notation (PGN).
 * 
 * https://docs.oracle.com/javase/7/docs/api/java/io/StreamTokenizer.html
 */
public class PGNReader {
	static PrintStream out = System.out;

	public static void main(String[] args) {
		try {
			read(pgnRoot + "/Carlsen.pgn");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileName файл с расширением *.pgn
	 * @return список партий из шахмат.
	 * @throws FileNotFoundException
	 */
	static public List<Chess> read(String fileName) throws IOException {
		out = new PrintStream(fileName + ".txt");

		FileReader fr = new FileReader(fileName);
		Reader br = new BufferedReader(fr);

		StreamTokenizer streamTokenizer = new StreamTokenizer(br);
		streamTokenizer.slashSlashComments(false);
		streamTokenizer.slashStarComments(false);

		// Символ / - это обычный символ (это не начало комментария).
		streamTokenizer.ordinaryChar('/');

		// Символ - - это обычный символ (это не минус перед числом).
		streamTokenizer.ordinaryChar('-');
		streamTokenizer.ordinaryChar('+');

		streamTokenizer.wordChars('-', '-');
		streamTokenizer.wordChars('+', '+');
		streamTokenizer.wordChars('=', '=');

		List<Chess> games = new ArrayList<Chess>();

		Chess chess = new Chess();
		while (readGame(streamTokenizer, chess)) {
			games.add(chess);
			chess = new Chess();
		}

		out.close();
		return games;
	}

	private static boolean readGame(StreamTokenizer streamTokenizer, Chess chess) throws IOException {
		int currentToken = streamTokenizer.nextToken();
		while (currentToken != StreamTokenizer.TT_EOF) {
			switch (streamTokenizer.ttype) {
			case '[':
				readTag(streamTokenizer, chess);
				break;

			case StreamTokenizer.TT_NUMBER:
				streamTokenizer.pushBack();
				readMoves(streamTokenizer, chess);
				return true;
			}

			currentToken = streamTokenizer.nextToken();
		}
		return false;
	}

	private static void readMoves(StreamTokenizer streamTokenizer, Chess chess) throws IOException {
		int currentToken = streamTokenizer.nextToken();

		while (currentToken != StreamTokenizer.TT_EOF) {
			switch (streamTokenizer.ttype) {
			case '[':
				streamTokenizer.pushBack();
				out.println();
				return;

			case StreamTokenizer.TT_WORD:
				Move move = getChessMove(chess, streamTokenizer.sval);
				chess.board.history.addMove(move);
				break;

			case StreamTokenizer.TT_NUMBER:
				if (isGameResult(streamTokenizer)) {
					out.println();
					out.println();
					return;
				}
				out.print("\n" + (int) streamTokenizer.nval + ".");
				break;

			default:
				out.print((char) currentToken);
				break;
			}

			currentToken = streamTokenizer.nextToken();
		}
	}

	@SuppressWarnings("unused")
	private static Move getChessMove(Chess chess, String moveText) {
		// TODO распознать ход по тексту, проверить его корректность
		// и вернуть его как результат функции.
		Board board = chess.board;

		out.print(" " + moveText);
		char piece = moveText.charAt(0);
		switch (piece) {
			case 'K': // Король.
			case 'Q': // Король.
			case 'B': // Король.
			case 'N': // Король.
			case 'R': // Король.
			case 'O': // Король - рокировка.
			default:  // Пешка
				return doPawnMove(chess, moveText);
		}

	}

	@SuppressWarnings("unused")
	private static Move doPawnMove(Chess chess, String moveText) {
		char vSource = 'a';
		Board board = chess.board;

		int len = moveText.length();

		switch (len) {
			case 2: // цель (e4) или взятие (cd)
				PieceColor color = chess.board.getMoveColor();
				int sv = 0;
				int sh = 0;
				int tv = 0;
				int th = 0;
				if (Character.isDigit(moveText.charAt(1)))
					// Это простой ход пешкой
					return null;

			default:
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static boolean isGameResult(StreamTokenizer streamTokenizer) throws IOException {
		int currentToken = streamTokenizer.nextToken();

		switch (streamTokenizer.ttype) {
		case '-': // 1-0 или 0-1
			currentToken = streamTokenizer.nextToken(); // 0 или 1
			return true;
		case '/': // 1/2-1/2
			currentToken = streamTokenizer.nextToken(); // 2
			currentToken = streamTokenizer.nextToken(); // -
			currentToken = streamTokenizer.nextToken(); // 1
			currentToken = streamTokenizer.nextToken(); // /
			currentToken = streamTokenizer.nextToken(); // 2
			return true;
		case StreamTokenizer.TT_NUMBER:
			currentToken = streamTokenizer.nextToken();  
			if (!streamTokenizer.sval.equals("-")) {
				streamTokenizer.pushBack();
				return false;
			}
			currentToken = streamTokenizer.nextToken();  
			return true;
		default:
			if (streamTokenizer.sval.equals("-1") || streamTokenizer.sval.equals("-0"))
				return true;
			streamTokenizer.pushBack();
			return false;
		}
	}

	static private void readTag(StreamTokenizer streamTokenizer, Chess chess) throws IOException {
		int currentToken = streamTokenizer.nextToken();
		String tagName = null, tagValue;

		while (currentToken != StreamTokenizer.TT_EOF) {
			switch (streamTokenizer.ttype) {
			case ']':
				out.println();
				return;

			case StreamTokenizer.TT_WORD:
				tagName = streamTokenizer.sval;
				out.print(streamTokenizer.sval + "=");
				break;

			default:
				tagValue = streamTokenizer.sval;
				out.print(tagValue);
				chess.tags.put(tagName, tagValue);
				break;
			}

			currentToken = streamTokenizer.nextToken();
		}
	}

}
