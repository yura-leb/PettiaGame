package tools.db.report;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * HTMLReporter makes dump for system tables of database.
 */
public abstract class HTMLReporter {
	private String catalog = null;
	private String schemaPattern = null;
	private String tableNamePattern = "%";
	private String types[] = { "TABLE", "VIEW", "SYSTEM TABLE", "SYSTEM VIEW" };

	protected Connection con = null;

	/** Formated output stream. */
	protected FileGenerator gen;

	/** Output path for the dump. */
	protected String reportDir;

	public HTMLReporter(String reportDir) {
		this.reportDir = reportDir;
		
		new File(reportDir).mkdirs();
	}

	/**
	 * Make report of data source (the database has been registered).
	 * 
	 * @param dataSource
	 *            name of data source
	 * @param uid
	 *            user identifier
	 * @param pwd
	 *            user password
	 */
	public void reportDataSource(String dataSource, String uid, String pwd) {
		// Data base with DSN (Data Source Name): dbSource.
		String url = "jdbc:odbc:" + dataSource;

		// Sun jdbc-odbc bridge driver.
		String drv = "sun.jdbc.odbc.JdbcOdbcDriver";

		reportDataBase(drv, url, uid, pwd);
	} 

	/**
	 * Report the data base content.
	 */
	public void reportDataBase(String drv, String url, String uid, String pwd) {
		String dataSource = url.replace(":", "_");
		dataSource = dataSource.replace("/", "_");
		dataSource = dataSource.replace("~", "_");
		
		gen = new FileGenerator(reportDir, dataSource + ".html");
		
		outHTMLHeader("Dump of data source: " + dataSource);
		
		// ====================
		// = Load the driver. =
		// ====================
		try {
			Class.forName(drv);
			Locale.setDefault(Locale.ENGLISH);
		} catch (ClassNotFoundException e) {
			outError("Error of driver loading: " + drv, e);
			outHTMLTail();
			return;
		}

		// ========================
		// = Connect to database. =
		// ========================
		try {
			con = DriverManager.getConnection(url, uid, pwd);
		} catch (SQLException e) {
			String msg = String.format("DriverManager.getConnection(%s, %s, %s);", 
					url, uid, pwd);
			outError(msg, e);
			outHTMLTail();
			return;
		}

		// -------------------------------
		gen.outln("<h1>" + url + "</h1>");
		// -------------------------------

		String catalog = null;
		try {
			catalog = con.getCatalog();
			gen.outln("<p>-- Catalog = " + catalog + "</p>");
		} catch (SQLException e) {
			outError("con.getCatalog();", e);
		}

		// ==========================
		// = Get database metadata. =
		// ==========================
		DatabaseMetaData dbmd = null;
		try {
			dbmd = con.getMetaData();
		} catch (SQLException e) {
			outError("con.getMetaData();", e);
			outHTMLTail();
			return;
		}

		try {
			gen.outln("<h2>DatabaseProductName:</h2>");
			gen.outln(dbmd.getDatabaseProductName());

			gen.outln("<h2>DatabaseProductVersion:</h2>");
			gen.outln(dbmd.getDatabaseProductVersion());

			gen.outln("<h2>DriverName:</h2>");
			gen.outln(dbmd.getDriverName());

			gen.outln("<h2>DriverVersion:</h2>");
			gen.outln(dbmd.getDriverVersion());

			gen.outln("<h2>Additive SQL keywords:</h2>");
			gen.outln(dbmd.getSQLKeywords());

			gen.outln("<h2>Numeric Functions:</h2>");
			gen.outln(dbmd.getNumericFunctions());

			gen.outln("<h2>System Functions:</h2>");
			gen.outln(dbmd.getSystemFunctions());

			gen.outln("<h2>TimeDate Functions:</h2>");
			gen.outln(dbmd.getTimeDateFunctions());

		} catch (SQLException e) {
			outError("con.getCatalog();", e);
		}

		reportTables(con, dbmd);

		try {
			con.close();
		} catch (SQLException e) {
			outError("con.close();", e);
		}
		
		outHTMLTail();
	}  

	protected void reportTables(Connection con, DatabaseMetaData dbmd) {
		ResultSet rs;

		try {// =========================
			// = Get database schemas. =
			// =========================
			rs = dbmd.getSchemas();
			outResultSet("Database schemas.", rs);
			rs.close();
		} catch (SQLException e) {
			outError("dbmd.getSchemas();", e);
		}

		try {// =========================
			// = Get database types. =
			// =========================
			rs = dbmd.getTypeInfo();
			outResultSet("Database types.", rs);
			rs.close();
		} catch (SQLException e) {
			outError("dbmd.getSchemas();", e);
		}

		try {// =============================
			// = Get database table types. =
			// =============================
			rs = dbmd.getTableTypes();
			outResultSet("Table types.", rs);

			rs = dbmd.getTableTypes();
			ArrayList<String> at = new ArrayList<String>();
			while (rs.next()) {
				String tableType = rs.getString(1);
				at.add(tableType);
			}
			types = at.toArray(new String[at.size()]);
			rs.close();
		} catch (SQLException e) {
			outError("dbmd.getTableTypes();", e);
		}

		// ============================
		// = Get the database tables. =
		// ============================

		// =========================================
		// = Output all tables of the data source. =
		// =========================================
		try {
			rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);
			outResultSet("Tables.", rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getTables(%s, %s, %s, %s);", 
					catalog, schemaPattern, tableNamePattern, types.toString());
			outError(msg, e);
		} // catch

		// =========================================
		// = Output each table of the data source. =
		// =========================================
		try {
			rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (rs.next()) {
				String tableName = rs.getString(3);
				String tableKind = rs.getString(4);
				
				System.out.format("Table: %s kind: %s %n", 
						tableName, tableKind);
				
				outTable(dbmd, tableName);
			}
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getTables(%s, %s, %s, %s);", 
					catalog, schemaPattern, tableNamePattern, types.toString());
			outError(msg, e);
		}

		// =============================================
		// = Output all procedures of the data source. =
		// =============================================
		String procedureNamePattern = "%";

		try {
			rs = dbmd.getProcedures(catalog, schemaPattern,
					procedureNamePattern);
			outResultSet("Procedures", rs);

			rs = dbmd.getProcedures(catalog, schemaPattern,
					procedureNamePattern);
			while (rs.next()) {
				String procedureName = rs.getString(3);
				gen.outln("<h2>Procedure: " + procedureName + "</h2>");

				outProcedure(dbmd, procedureName);
			} // while
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getProcedures(%s, %s, %s, %s);", 
					catalog, schemaPattern, procedureNamePattern, types.toString());
			outError(msg, e);
		}

		reportStoredProcedures();
		reportTriggers();
	} 

	/**
	 * Output content of table to HTML file.
	 * 
	 * @param dbmd
	 *            the database metadata.
	 * @param tableName
	 *            name of table to output.
	 */
	protected void outTable(DatabaseMetaData dbmd, String tableName) {
		ResultSet rs;

		String catalog = null;
		String schemaPattern = null; // "DBA";

		gen.outln("<h2>Table: " + tableName + "</h2>");

		// ======================
		// = Get table columns. =
		// ======================
		try {
			rs = dbmd.getColumns(catalog, schemaPattern, tableName, "%");
			outResultSet("Columns of: " + tableName, rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getColumns(%s, %s, %s, %s);", 
					catalog, schemaPattern, tableName, "%");
			outError(msg, e);
		}

		// ===========================
		// = Get table primary keys. =
		// ===========================
		try {
			rs = dbmd.getPrimaryKeys(catalog, schemaPattern, tableName);
			outResultSet("Primary keys of: " + tableName, rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getPrimaryKeys(%s, %s, %s);", 
					catalog, schemaPattern, tableName);
			outError(msg, e);
		}

		// ============================
		// = Get table imported keys. =
		// ============================
		try {
			rs = dbmd.getImportedKeys(catalog, schemaPattern, tableName);
			outResultSet("Imported keys of: " + tableName, rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getImportedKeys(%s, %s, %s);", 
					catalog, schemaPattern, tableName);
			outError(msg, e);
		}

		// ============================
		// = Get table exported keys. =
		// ============================
		try {
			rs = dbmd.getExportedKeys(catalog, schemaPattern, tableName);
			outResultSet("Exported keys of: " + tableName, rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getImportedKeys(%s, %s, %s);", 
					catalog, schemaPattern, tableName);
			outError(msg, e);
		}
	}  
	
	/**
	 * Output content of SQL procedure to HTML file.
	 * 
	 * @param dbmd
	 *            the database metadata.
	 * @param procedureName
	 *            name of SQL procedure to output.
	 */
	protected void outProcedure(DatabaseMetaData dbmd, String procedureName) {
		ResultSet rs;

		String catalog = null;
		String schemaPattern = null;

		// ======================
		// = Get table columns. =
		// ======================
		try {
			rs = dbmd.getProcedureColumns(catalog, schemaPattern,
					procedureName, "%");
			outResultSet("Columns of procedure: " + procedureName, rs);
			rs.close();
		} catch (SQLException e) {
			String msg = String.format("dbmd.getImportedKeys(%s, %s, %s, %s);", 
					catalog, schemaPattern, procedureName, "%");
			outError(msg, e);
		}
	} 

	/**
	 * Output result set of any query to HTML file.
	 * 
	 * @param message
	 *            comment for the result set.
	 * @param rs
	 *            the result set.
	 */
	protected void outResultSet(String message, ResultSet rs) {
		gen.outln("<h3> " + message + " </h3>");
		gen.outln("<table>");

		ResultSetMetaData rsmd = null;

		try {
			rsmd = rs.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			// =============================
			// = Output result set header. =
			// =============================
			gen.outln("<tr class =\"trTitle\">");
			for (int i = 1; i <= numberOfColumns; i++) {
				String columnName = rsmd.getColumnLabel(i);

				gen.outln("  <td>");
				gen.out(columnName);
				gen.outln("  </td>");
			} // for
			gen.outln("</tr>");

			// ==============================
			// = Output result set content. =
			// ==============================
			while (rs.next()) {
				gen.outln("<tr>");
				for (int k = 1; k <= numberOfColumns; k++) {
					String columnValue = rs.getString(k);
					if (columnValue == null)
						columnValue = "";
					
					columnValue = columnValue.replace("<", "&lt;");
					columnValue = columnValue.replace(">", "&gt;");

					gen.outln("  <td>");
					gen.out("" + columnValue);
					gen.outln("  </td>");
				} // for
				gen.outln("</tr>");
			} // while
		} // try
		catch (SQLException e) {
			gen.outln("</tr></table><br><br>");
			outError("outResultSet(...);", e);
		}
		gen.outln("</table><br><br>");
	} 

	/**
	 * Output header of HTML file.
	 * 
	 * @param title
	 *            of the file.
	 */
	private void outHTMLHeader(String title) {
		gen.outln("<html>");
		gen.outln("<head>");
		gen.outln("<meta ");
		gen.inc();
		gen.outln("http-equiv=\"Content-Type\" ");
		gen.outln(" content=\"text/html; charset=windows-1251\" ");
		gen.dec();
		gen.outln(">");
		gen.outln("<title>" + title + "</title>");

		gen.outln("<style>");
		gen.outln("<!--");
		generateStyles(gen);
		gen.outln("-->");
		gen.outln("</style>");

		gen.outln("</head>");
		gen.outln("<body>");
	}  

	private void outHTMLTail() {
		gen.outln("</body>");
		gen.outln("</html>");
	} 

	protected void outError(String where, Object what) {
		gen.outln("<br><table border=\"1\" cellspacing=\"0\">");
		gen.outln("  <tr><td bgcolor=\"#000000\">");
		gen.outln("       <font color=\"#FF0000\"> Error!! </font>");
		gen.outln("  </td></tr>");
		gen.outln("  <tr><td>" + where + "</td></tr>");
		gen.outln("  <tr><td>" + what + "</td></tr>");
		gen.outln("</table><br>");
	}  
	
	protected void generateStyles(FileGenerator gen) {
		gen.outln("body { ");
		gen.outln("   background-color: #ccccff;");
		gen.outln("}");
		gen.nl();

		gen.outln("h1 { ");
		gen.outln("   color: red;");
		gen.outln("   font-size: 14pt;");
		gen.outln("   text-align: center;");
		gen.outln("}");
		gen.nl();

		gen.outln("h2 { ");
		gen.outln("   color: navy;");
		gen.outln("   font-size: 12pt;");
		gen.outln("   text-align: center;");
		gen.outln("}");
		gen.nl();

		gen.outln("h3 { ");
		gen.outln("   color: maroon;");
		gen.outln("   font-size: 11pt;");
		gen.outln("}");
		gen.nl();

		gen.outln("table { ");
		gen.outln("   color: navy;");
		gen.outln("   background-color: gold;");

		gen.outln("   border-color:  black;");
		gen.outln("   border-left:   solid;");
		gen.outln("   border-right:  solid;");
		gen.outln("   border-top:    solid;");
		gen.outln("   border-bottom: solid;");
		gen.outln("   border-left-width:   thin;");
		gen.outln("   border-right-width:  thin;");
		gen.outln("   border-top-width:    thin;");
		gen.outln("   border-bottom-width: thin;");
		gen.outln("}");
		gen.nl();

		gen.outln("tr { ");
		gen.outln("   background-color: LightYellow;");
		gen.outln("}");
		gen.nl();

		gen.outln(".trTitle { ");
		gen.outln("   color: Yellow;");
		gen.outln("   background-color: olive;");
		gen.outln("   border-color:  black;");
		gen.outln("   border-right:  solid;");
		gen.outln("   border-right-width:  thin;");
		gen.outln("}");
		gen.nl();
	} 

	protected abstract void reportStoredProcedures();
	protected abstract void reportTriggers();
} 
