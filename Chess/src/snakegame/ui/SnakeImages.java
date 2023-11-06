package snakegame.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class SnakeImages {
    public static Image iconSnakeNotebook;

    static {
        load(Display.getCurrent());
    }

    public static void load(Display display) {
        iconSnakeNotebook = new Image(display, SnakeImages.class.getResourceAsStream("icoSnake.png"));
    }
}
