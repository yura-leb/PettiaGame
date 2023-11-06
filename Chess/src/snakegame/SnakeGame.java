package snakegame;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import snakegame.ui.SnakeBoard;
import snakegame.ui.SnakeImages;


public class SnakeGame {
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        final Display display = new Display();
        SnakeImages.load(display);

        final Shell shell = new Shell(display);

        shell.setSize(600, 500);
        shell.setText("Snake Game");
        shell.setImage(SnakeImages.iconSnakeNotebook);

        FillLayout layout = new FillLayout();
        shell.setLayout(layout);

        SnakeBoard sb = new SnakeBoard(shell);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
