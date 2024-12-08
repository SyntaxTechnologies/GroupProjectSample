package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public class EmployeeListPage extends CommonMethods {

    @FindBy(id = "empsearch_id")
    public WebElement idEmployee;

    @FindBy(id = "searchBtn")
    public WebElement searchButton;

    @FindBy(id = "empsearch_employee_name_empName")
    public WebElement employeeNameField;

    @FindBy(xpath = "//table[@id='resultTable']/tbody/tr")
    public List<WebElement> employeeInfoTable;

    @FindBy(xpath = "(//table[@id='resultTable']/tbody/tr/td[2])[1]/a")
    public WebElement idInTable;

    @FindBy(xpath = "//table[@id='resultTable']/tbody/tr/td[1]")
    public  WebElement checkboxEmployeeDetails;

    @FindBy(id="btnDelete")
    public WebElement deleteButton;
    @FindBy(id="dialogDeleteBtn")
    public WebElement employeeDeleteModal;
    public EmployeeListPage() {

        PageFactory.initElements(driver, this);
    }
}
