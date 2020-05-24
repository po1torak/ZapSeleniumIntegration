package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Constants.BASE_URL;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        driver.get(BASE_URL + "login.jsp");
    }

    public void login(String login, String password) {
        usernameField.sendKeys(login);
        passwordField.sendKeys(password);
        submitButton.click();
        verifyTextPresent("successfully");
    }
}
