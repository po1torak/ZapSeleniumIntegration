package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Constants.BASE_URL;

public class RegistrationPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password1")
    private WebElement passwordField;

    @FindBy(id = "password2")
    private WebElement confirmPasswordField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void openRegistrationPage() {
        driver.get(BASE_URL + "register.jsp");
    }

    public void registerUser(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        submitButton.click();
    }
}
