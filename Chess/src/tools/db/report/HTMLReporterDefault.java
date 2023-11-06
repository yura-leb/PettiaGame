package tools.db.report;

public class HTMLReporterDefault extends HTMLReporter {

	public HTMLReporterDefault(String reportDir) {
		super(reportDir);
	}

	@Override
	protected void reportStoredProcedures() {
	}

	@Override
	protected void reportTriggers() {
	}
}
