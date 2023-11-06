package snakegame.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import snakegame.pieces.Apple;
import snakegame.pieces.Board;
import snakegame.pieces.Directions;
import snakegame.pieces.Snake;

@SuppressWarnings("deprecation")
public class SnakeBoard extends Canvas implements PaintListener, KeyListener, Observer {
    protected Board board;
    protected Snake snake;

    protected int v_cells;
    protected int h_cells;

    private boolean isGameStarted = false;

    public SnakeBoard(Composite parent) {
        super(parent, SWT.NONE);
        setBackground(new Color(null, 0, 0, 255));

        board = new Board(30, 30);
        snake = new Snake(board);
        board.apple = new Apple(board);

        addPaintListener(this);
        addKeyListener(this);
        board.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        redraw();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.keyCode == SWT.SPACE) {
            if (!isGameStarted) {
                gameProcess(getParent().getDisplay());
                isGameStarted = true;
            }
            if (!snake.IsAlive) {
                gameProcess(this.getParent().getDisplay());
                snake = new Snake(board);
                board.apple = new Apple(board);
                snake.IsAlive = true;
            }
        }

        switch (e.keyCode) {
            case SWT.ARROW_UP:
                System.out.println("UP");
                snake.CurrentDirection = Directions.UP;
                break;
            case SWT.ARROW_DOWN:
                System.out.println("DOWN");
                snake.CurrentDirection = Directions.DOWN;
                break;
            case SWT.ARROW_LEFT:
                System.out.println("LEFT");
                snake.CurrentDirection = Directions.LEFT;
                break;
            case SWT.ARROW_RIGHT:
                System.out.println("RIGHT");
                snake.CurrentDirection = Directions.RIGHT;
                break;
        }
    }

    public void gameProcess(Display display) {
        Thread thread = new Thread(() -> {
            while (snake.IsAlive) {
                try {
                    display.asyncExec(runnable);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public Runnable runnable = () -> {
        try {
            board.startGame(snake);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    };

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void paintControl(PaintEvent e) {
        v_cells = board.getXLength();//
        h_cells = board.getYLength();//

        e.gc.drawText("Score " + snake.Score, getClientArea().width - 70, 5);
        drawSnake(e, snake);
        drawApple(e);
    }

    protected void drawSquare(GC gc, int i, int j, int sw, int sh, Color color) {
        int sx = i * sw;
        int sy = j * sh;
        gc.setBackground(color);
        gc.fillRectangle(sx, sy, sw, sh);
        //gc.drawRectangle(sx, sy, sw, sh);
    }

    protected void drawSnake(PaintEvent e, Snake snake) {
        int squareWidth = (getClientArea().width) / board.getXLength();
        int squareHeight = (getClientArea().height) / board.getYLength();

        for (int i = 0; i < snake.body.size(); i++) {
            Color clr = new Color(getDisplay(), 0, 150, 0);
            drawSquare(e.gc, snake.body.get(i).v, snake.body.get(i).h, squareWidth, squareHeight, clr);
        }
    }

    protected void drawApple(PaintEvent e) {
        int squareWidth = (getClientArea().width) / board.getXLength();
        int squareHeight = (getClientArea().height) / board.getYLength();
        Color clr = new Color(getDisplay(), 200, 0, 0);

        if (board.apple.isAlive) {
            drawSquare(e.gc, board.apple.square.v, board.apple.square.h, squareWidth, squareHeight, clr);
        } else {
            for (; ; ) {
                Apple app = new Apple(board);

                if (snake.body.contains(app.square)) {
                    continue;
                } else {
                    board.apple.square.v = app.square.v;
                    board.apple.square.h = app.square.h;
                    break;
                }
            }
            drawSquare(e.gc, board.apple.square.v, board.apple.square.h, squareWidth, squareHeight, clr);
            board.apple.isAlive = true;
        }
    }
}
