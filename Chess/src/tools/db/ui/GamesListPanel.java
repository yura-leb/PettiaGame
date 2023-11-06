package tools.db.ui;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class GamesListPanel extends Composite {
	private static final Color COLOR_BLACK = new Color(null,   0,   0,   0);
	private static final Color COLOR_GREEN = new Color(null,   0, 100,   0);
	private static final Color COLOR_WHITE = new Color(null, 255, 255, 255);
	
	private Table table;
	private TableViewer viewer;

	public GamesListPanel(Composite parent) {
		super(parent, SWT.NONE);

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginBottom = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);

		//
		// Панель для показа списка найденных в БД партий.
		//
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 260;
		data.heightHint = 25;

		//
		// Таблица найденных партий.
		//
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(33, true));
		tableLayout.addColumnData(new ColumnWeightData(33, true));
		tableLayout.addColumnData(new ColumnWeightData(33, true));

		viewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);

		table = viewer.getTable();
		table.setLayout(tableLayout);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(data);

		table.setBackground(COLOR_WHITE);
		table.setForeground(COLOR_GREEN);
		table.setHeaderBackground(COLOR_GREEN);
		table.setHeaderForeground(COLOR_WHITE);

		TableColumn white = new TableColumn(table, SWT.CENTER);
		white.setText("Белые");

		TableColumn black = new TableColumn(table, SWT.CENTER);
		black.setText("Черные");

		TableColumn date = new TableColumn(table, SWT.CENTER);
		date.setText("Дата");

		fillTable();
	}

	private void fillTable() {
		for (int i = 0; i < 128; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "Алехин");
			item.setText(1, "Капабланка");
			item.setText(2, "" + (1914 + i));

			item.setForeground(COLOR_BLACK);
			item.setBackground(COLOR_WHITE);
		}

		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumn(i).pack();
	}
}