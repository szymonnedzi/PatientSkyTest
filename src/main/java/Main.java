import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        CalendarService calendarService = new CalendarService();
        List<Calendar> allCalendars = calendarService.getAllCalendars();
        List<UUID> calendarsToCheck = calendarService.generateCalendarUUIDsToCheck();
    }

    //findAvailableTime(array<Uuid> calendarIds, Integer duration, Iso8601TimeInterval periodToSearch [,Uuid timeSlotType]);
}
