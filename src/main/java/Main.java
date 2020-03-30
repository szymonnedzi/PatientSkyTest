import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        CalendarService calendarService = new CalendarService();
        List<Calendar> allCalendars = calendarService.getAllCalendars();
        List<UUID> generatedCalendarUUIDs = calendarService.generateCalendarUUIDs();
        List<Calendar> foundCalendars = calendarService.getCalendarsByUUID(allCalendars, generatedCalendarUUIDs);

        // Date() takes year as year 1900 + given year.
        Date startDate = new Date(119, 3, 23, 8, 0, 0);
        Date endDate = new Date(119, 3, 28, 22, 0, 0);
        Integer duration = 15;
        calendarService.findAvailableTime(foundCalendars, duration, startDate, endDate);
    }
}
