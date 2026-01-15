package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.Map;

public class Utils {
  public static String replaceVariables(String templateString, Map<String, String> variables) {
    String result = templateString;
    for (Map.Entry<String, String> entry : variables.entrySet()) {
      result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }
    return result;
  }
}
