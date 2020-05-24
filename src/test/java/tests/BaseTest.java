package tests;

import net.continuumsecurity.proxy.ScanningProxy;
import net.continuumsecurity.proxy.Spider;
import net.continuumsecurity.proxy.ZAProxyScanner;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.NavigationPage;
import pages.RegistrationPage;
import utils.DriverUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static utils.Constants.*;

public class BaseTest {

    static Logger log = Logger.getLogger(BaseTest.class.getName());

    protected ScanningProxy zapScanner;

    protected Spider zapSpider;

    protected WebDriver driver;

    protected RegistrationPage registrationPage;

    protected LoginPage loginPage;

    protected NavigationPage navigationPage;

    @BeforeSuite
    public void setupZap() {
        zapScanner = new ZAProxyScanner(ZAP_PROXY_HOST, ZAP_PROXY_PORT, ZAP_API_KEY);
        zapScanner.clear();
        zapSpider = (Spider) zapScanner;
    }

    @BeforeMethod
    public void setupBrowser() {
        log.info("Created client to ZAP API");
        driver = DriverUtils.createChromeDriver();
        initPagesForTest();
    }

    @AfterMethod
    public void after(ITestResult result) {
        driver.quit();

        if (result.getStatus() == FAILED_TEST_STATUS_ID) {
            generateReport(result.getMethod().getMethodName());
        }
    }

    private void generateReport(String testName) {
        String report = new String(zapScanner.getHtmlReport(), StandardCharsets.UTF_8);

        File file;
        FileWriter fWriter;
        BufferedWriter writer;
        try {
            file = new File("target/security-report/" + testName + HTML_EXTENSION);
            file.getParentFile().mkdirs();
            fWriter = new FileWriter(file);
            writer = new BufferedWriter(fWriter);
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPagesForTest() {
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        navigationPage = new NavigationPage(driver);
    }
}
