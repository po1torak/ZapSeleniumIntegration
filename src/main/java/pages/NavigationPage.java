package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationPage extends BasePage {

    @FindBy(linkText = "Home")
    private WebElement homeMenu;

    @FindBy(linkText = "Doodahs")
    private WebElement doodahsMenu;

    @FindBy(linkText = "Search")
    private WebElement searchMenu;

    @FindBy(linkText = "Zip a dee doo dah")
    private WebElement zipOption;

    @FindBy(id = "submit")
    private WebElement addToCartButton;

    @FindBy(linkText = "Advanced Search")
    private WebElement advancedSearchButton;

    @FindBy(id = "product")
    private WebElement productField;

    @FindBy(id = "desc")
    private WebElement descField;

    @FindBy(id = "type")
    private WebElement typeField;

    @FindBy(id = "price")
    private WebElement priceField;

    @FindBy(css = "input[type='submit']")
    private WebElement submitSearchButton;

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart() {
        doodahsMenu.click();
        zipOption.click();
        addToCartButton.click();
    }

    public void performAdvancedSearch() {
        searchMenu.click();
        advancedSearchButton.click();
        productField.sendKeys("test");
        descField.sendKeys("test");
        typeField.sendKeys("test");
        priceField.sendKeys("test");
        submitSearchButton.click();
        verifyTextPresent("Results Found");
    }
}
