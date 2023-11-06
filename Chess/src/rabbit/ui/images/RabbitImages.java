package rabbit.ui.images;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class RabbitImages {
    public static Image icoRabbit;
    public static Image rabbitImage;
    public static Image wolfImage;

    static {
        Device display = Display.getDefault();
        icoRabbit = new Image(display, RabbitImages.class.getResourceAsStream("icoRabbit.png"));

        wolfImage = new Image(display, RabbitImages.class.getResourceAsStream("wolf.png"));
        rabbitImage = new Image(display, RabbitImages.class.getResourceAsStream("rabbit.png"));
    }
}
