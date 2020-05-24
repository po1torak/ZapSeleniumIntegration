package tests;

import org.apache.log4j.Logger;
import org.testng.annotations.*;
import org.zaproxy.clientapi.core.Alert;
import pages.LoginPage;
import pages.NavigationPage;
import pages.RegistrationPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static utils.Constants.DEFAULT_PASSWORD;
import static utils.Constants.DEFAULT_USERNAME;
import static utils.LogUtils.filterAlerts;
import static utils.LogUtils.logAlerts;
import static utils.ZapUtils.*;

public class UserSecurityTest extends BaseTest {

    static Logger log = Logger.getLogger(UserSecurityTest.class.getName());

    @Test (priority = 1)
    public void userAddsProductToCart() {
        registrationPage.openRegistrationPage();
        registrationPage.registerUser(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        loginPage.openLoginPage();
        loginPage.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        navigationPage.addProductToCart();

        log.info("Spidering...");
        spiderWithZap(zapSpider);
        log.info("Spider done.");

        setAlertAndAttackStrength(zapScanner);
        zapScanner.setEnablePassiveScan(true);
        scanWithZap(zapScanner);

        List<Alert> alerts = filterAlerts(zapScanner.getAlerts());
        logAlerts(alerts);
        assertThat("Vulnerabilities amount: " + alerts.size(),
                alerts.size(), equalTo(0));
    }

    @Test (priority = 2)
    public void userPerformsAdvancedSearch() {
        loginPage.openLoginPage();
        loginPage.login(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        navigationPage.performAdvancedSearch();

        log.info("Spidering...");
        spiderWithZap(zapSpider);
        log.info("Spider done.");

        setAlertAndAttackStrength(zapScanner);
        zapScanner.setEnablePassiveScan(true);
        scanWithZap(zapScanner);

        List<Alert> alerts = filterAlerts(zapScanner.getAlerts());
        logAlerts(alerts);
        assertThat(alerts.size(), equalTo(0));
    }
}
