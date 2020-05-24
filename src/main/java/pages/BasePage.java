package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static utils.Constants.BASE_URL;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get(BASE_URL);
    }

    protected void verifyTextPresent(String text) {
        if (!this.driver.getPageSource().contains(text)) {
            throw new RuntimeException("Expected text: ["+text+"] was not found.");
        }
    }
}
