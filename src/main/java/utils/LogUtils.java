package utils;

import org.apache.log4j.Logger;
import org.zaproxy.clientapi.core.Alert;

import java.util.List;
import java.util.stream.Collectors;

public class LogUtils {

    static Logger log = Logger.getLogger(LogUtils.class.getName());

    public static List<Alert> filterAlerts(List<Alert> alerts) {
        return alerts
                .stream()
                .filter(LogUtils::isHighRiskAndNotLowConfidence)
                .collect(Collectors.toList());
    }

    public static void logAlerts(List<Alert> alerts) {
        for (Alert alert : alerts) {
            log.info("Alert: " + alert.getAlert() + " at URL: " + alert.getUrl() +
                    " Parameter: " + alert.getParam() + " CWE ID: " + alert.getCweId());
        }
    }

    private static boolean isHighRiskAndNotLowConfidence(Alert alert) {
        return alert.getRisk().equals(Alert.Risk.High) && alert.getConfidence() != Alert.Confidence.Low;
    }
}
