package steps;

import pages.*;

public class PageInitializer {

	// -------------------- Page Objects --------------------
	public static LoginPage loginPage;
	public static DashboardPage dash;
	public static AddEmployeePage addEmployee;
	public static EmployeeListPage employeeListPage;
	public static AddLicensePage addEmpLicense;
	public static DependantsPage dependant;
	public static EmergencyContactsPage emergencyContact;
	public static JobPage job;
	public static LanguagesPage language;
	public static MembershipsPage membership;
	public static PersonalDetailsPage pDetails;
	public static QualificationsPage qualification;
	public static SkillsPage skills;

	// -------------------- Initialize Method --------------------

	/**
	 * Initializes all page object instances.
	 */
	public static void initializePageObjects() {
		loginPage = new LoginPage();
		dash = new DashboardPage();
		addEmployee = new AddEmployeePage();
		employeeListPage = new EmployeeListPage();
		addEmpLicense = new AddLicensePage();
		dependant = new DependantsPage();
		emergencyContact = new EmergencyContactsPage();
		job = new JobPage();
		language = new LanguagesPage();
		membership = new MembershipsPage();
		pDetails = new PersonalDetailsPage();
		qualification = new QualificationsPage();
		skills = new SkillsPage();
	}


}
