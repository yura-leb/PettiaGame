package tools.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import tools.db.report.HTMLReporter;
import tools.db.report.HTMLReporterDefault;
import tools.db.resource.PGNLoader;

public class ConsoleDBStart {
    private static Logger log = Logger.getLogger(ConsoleDBStart.class.toString());

    public static void main(String[] args) {
        log.info("ConsoleDBStart start");

        final String ROOT = new File(".").getAbsolutePath();
        log.info("Current dir: " + ROOT);

        createTable();
        try {
            Files.walk(Paths.get("./Chess/src/tools/db/resource/"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(i -> {
                        final String path = i.getAbsolutePath();
                        try {
                            if (path.endsWith(".zip")) {
                                ZipFile zipFile = new ZipFile(path);
                                ZipInputStream zin = new ZipInputStream(new FileInputStream(path));
                                
                                ZipEntry entry;
                                while ((entry = zin.getNextEntry()) != null)
									if (entry.getName().endsWith(".pgn")) {
										InputStream is = zipFile.getInputStream(entry);
										InputStreamReader pgn = new InputStreamReader(is);
										PGNLoader.writePGNtoDb(pgn);
									}
                                
                                zin.close();
                                zipFile.close();
                            }
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                        if (path.endsWith(".pgn")) {
                            try {
                                PGNLoader.writePGNtoDb(new FileReader(new File(path)));
                            } catch (SQLException | FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("ConsoleDBStart end");
    }

    private static void createTable() {
        try {
            PGNLoader.createTable();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void dbHtmlReportDemo() {
        File htmlReports = new File("./html_reports");
        htmlReports.mkdirs();

        dbHtmlReportDemoH2(htmlReports);
        dbHtmlReportDemoHSQLDB(htmlReports);
//		dbHtmlReportDemoOracle(htmlReports);
    }

    static void dbHtmlReportDemoH2(File htmlReports) {
        String htmlReportsPath = htmlReports.getAbsolutePath();
        HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);

        String drv = "org.h2.Driver";
        String url = "jdbc:h2:~/test";
        String uid = "sa";
        String pwd = "";
        r.reportDataBase(drv, url, uid, pwd);
    }

    static void dbHtmlReportDemoHSQLDB(File htmlReports) {
        String htmlReportsPath = htmlReports.getAbsolutePath();
        HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);

        String drv = "org.hsqldb.jdbc.JDBCDriver";
//		String url = "jdbc:hsqldb:file:/opt/db/testdb";
        String url = "jdbc:hsqldb:mem:mymemdb";
        String uid = "SA";
        String pwd = "";
        r.reportDataBase(drv, url, uid, pwd);
    }

    static void dbHtmlReportDemoOracle(File htmlReports) {
        String htmlReportsPath = htmlReports.getAbsolutePath();
        HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);

        String drv = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//localhost:1521/XE";
        String uid = "SYSTEM";
        String pwd = "sql";
        r.reportDataBase(drv, url, uid, pwd);
    }
}