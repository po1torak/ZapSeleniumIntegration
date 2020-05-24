package utils;

import me.tongfei.progressbar.ProgressBar;
import net.continuumsecurity.proxy.ScanningProxy;
import net.continuumsecurity.proxy.Spider;
import org.apache.log4j.Logger;

import static utils.Constants.BASE_URL;
import static utils.Constants.LOGOUT_URL;

public class ZapUtils {

    static Logger log = Logger.getLogger(ZapUtils.class.getName());

    private final static String MEDIUM = "MEDIUM";

    private final static String HIGH = "HIGH";

    private final static String[] policyNames = {"directory-browsing", "cross-site-scripting", "sql-injection",
                                                 "path-traversal", "remote-file-inclusion", "server-side-include",
                                                 "script-active-scan-rules", "server-side-code-injection", "external-redirect",
                                                 "crlf-injection"};

    public static void setAlertAndAttackStrength(ScanningProxy zapScanner) {
        for (String policyName : policyNames) {
            String ids = enableZapPolicy(policyName, zapScanner);
            for (String id : ids.split(",")) {
                zapScanner.setScannerAlertThreshold(id,MEDIUM);
                zapScanner.setScannerAttackStrength(id,HIGH);
            }
        }
    }

    private static String enableZapPolicy(String policyName, ScanningProxy zapScanner) {
        String scannerIds = null;
        switch (policyName.toLowerCase()) {
            case "directory-browsing":
                scannerIds = "0";
                break;
            case "cross-site-scripting":
                scannerIds = "40012,40014,40016,40017";
                break;
            case "sql-injection":
                scannerIds = "40018";
                break;
            case "path-traversal":
                scannerIds = "6";
                break;
            case "remote-file-inclusion":
                scannerIds = "7";
                break;
            case "server-side-include":
                scannerIds = "40009";
                break;
            case "script-active-scan-rules":
                scannerIds = "50000";
                break;
            case "server-side-code-injection":
                scannerIds = "90019";
                break;
            case "remote-os-command-injection":
                scannerIds = "90020";
                break;
            case "external-redirect":
                scannerIds = "20019";
                break;
            case "crlf-injection":
                scannerIds = "40003";
                break;
            case "source-code-disclosure":
                scannerIds = "42,10045,20017";
                break;
            case "shell-shock":
                scannerIds = "10048";
                break;
            case "remote-code-execution":
                scannerIds = "20018";
                break;
            case "ldap-injection":
                scannerIds = "40015";
                break;
            case "xpath-injection":
                scannerIds = "90021";
                break;
            case "xml-external-entity":
                scannerIds = "90023";
                break;
            case "padding-oracle":
                scannerIds = "90024";
                break;
            case "el-injection":
                scannerIds = "90025";
                break;
            case "insecure-http-methods":
                scannerIds = "90028";
                break;
            case "parameter-pollution":
                scannerIds = "20014";
                break;
            default :
                throw new RuntimeException("No policy found for: " + policyName);
        }
        zapScanner.setEnableScanners(scannerIds, true);
        return scannerIds;
    }

    public static void scanWithZap(ScanningProxy zapScanner) {
       zapScanner.scan(BASE_URL);

        try (ProgressBar pb = new ProgressBar("Scanning:", 100)) {
            int currentScanID = zapScanner.getLastScannerScanId();
            int complete = 0;
            while (complete < 100) {
                complete = zapScanner.getScanProgress(currentScanID);
                pb.stepTo(complete);
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pb.stop();
        }
        log.info("Scanning done.");
    }


    public static void spiderWithZap(Spider zapSpider) {
        zapSpider.excludeFromSpider(LOGOUT_URL);
        zapSpider.setThreadCount(5);
        zapSpider.setMaxDepth(5);
        zapSpider.setPostForms(false);
        zapSpider.spider(BASE_URL);

        int spiderID = zapSpider.getLastSpiderScanId();
        int complete = 0;
        while (complete < 100) {
            complete = zapSpider.getSpiderProgress(spiderID);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (String url : zapSpider.getSpiderResults(spiderID)) {
            log.info("Found URL: " + url);
        }
    }
}
