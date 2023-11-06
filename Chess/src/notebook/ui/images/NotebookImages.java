package notebook.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Класс для доступа к уникальным изображениям блокнота игр.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class NotebookImages {
	public static Image iconNotebook;
	
	static {
		load( Display.getCurrent() );
	}

	static private void load(final Display display) {
		iconNotebook = new Image(display, NotebookImages.class.getResourceAsStream("GameNotebook.ico"));
	}
}
