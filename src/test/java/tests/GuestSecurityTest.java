package tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.Alert;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static utils.LogUtils.filterAlerts;
import static utils.LogUtils.logAlerts;
import static utils.ZapUtils.scanWithZap;
import static utils.ZapUtils.setAlertAndAttackStrength;
import static utils.ZapUtils.spiderWithZap;

public class GuestSecurityTest extends BaseTest {

    static Logger log = Logger.getLogger(GuestSecurityTest.class.getName());

    @Test (priority = 1)
    public void guestPerformsAdvancedSearch() {
        navigationPage.performAdvancedSearch();

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
    public void guestAddsProductToCart() {
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
}
