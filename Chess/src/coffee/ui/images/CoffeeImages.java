package coffee.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class CoffeeImages {
    public static Image imageBeanWhiteVertical;
    public static Image imageBeanWhiteHorizontal;
    public static Image imageBeanBlackVertical;
    public static Image imageBeanBlackHorizontal;

    static {
        load( Display.getCurrent() );
    }

    private static void load(final Display display) {
        imageBeanWhiteVertical  = new Image(display, CoffeeImages.class.getResourceAsStream("BeanWhiteVertical.png"));
        imageBeanWhiteHorizontal  = new Image(display, CoffeeImages.class.getResourceAsStream("BeanWhiteHorizontal.png"));
        imageBeanBlackVertical  = new Image(display, CoffeeImages.class.getResourceAsStream("BeanBlackVertical.png"));
        imageBeanBlackHorizontal  = new Image(display, CoffeeImages.class.getResourceAsStream("BeanBlackHorizontal.png"));
    }
}
