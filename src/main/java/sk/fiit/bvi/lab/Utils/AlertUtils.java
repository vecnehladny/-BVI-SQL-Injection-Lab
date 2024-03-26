package sk.fiit.bvi.lab.Utils;

import org.springframework.ui.Model;
import sk.fiit.bvi.lab.Wrapper.Alert;

import java.util.Collections;
import java.util.List;

public class AlertUtils {

    public static List<Alert> createAlert(String message, AlertType type) {
        return Collections.singletonList(Alert.builder()
                                              .message(message)
                                              .type(type.getValue())
                                              .build());
    }

    public static void addAlertToModel(Model model, String message, AlertType type) {
        List<Alert> alerts = createAlert(message, type);
        model.addAttribute("alerts", alerts);
    }

    public enum AlertType {
        PRIMARY("primary"),
        SECONDARY("secondary"),
        SUCCESS("success"),
        DANGER("danger"),
        WARNING("warning"),
        INFO("info");

        private final String value;

        AlertType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static AlertType fromString(String text) {
            for (AlertType alertType : AlertType.values()) {
                if (alertType.value.equalsIgnoreCase(text)) {
                    return alertType;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
}
