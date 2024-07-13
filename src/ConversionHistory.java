import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {

    private final List<String> history = new ArrayList<>();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void addRecord(String record) {
        String timestamp = LocalDateTime.now().format(formatter);
        history.add(timestamp + " - " + record);
    }

    public void showHistory() {
        System.out.println("\nHistorial de conversiones:");
        if (history.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            for (String record : history) {
                System.out.println(record);
            }
        }
    }

    public void dddshowHistory() {
    }

}