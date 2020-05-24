package utils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static utils.Constants.ZAP_PROXY_HOST;
import static utils.Constants.ZAP_PROXY_PORT;

public class DriverUtils {

    private final static String CHROME_DRIVER_PATH = "drivers/chromedriver.exe";

    public static WebDriver createChromeDriver() {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
        proxy.setSslProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("proxy", proxy);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        return new ChromeDriver(capabilities);
    }
}
