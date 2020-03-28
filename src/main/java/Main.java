import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        CalendarService calendarService = new CalendarService();
        List<Calendar> calendars = calendarService.getAllCalendars();

        for (Calendar calendar : calendars) {
            System.out.println(calendar.getCalendarID());
        }
    }


    //findAvailableTime(array<Uuid> calendarIds, Integer duration, Iso8601TimeInterval periodToSearch [,Uuid timeSlotType]);
}
