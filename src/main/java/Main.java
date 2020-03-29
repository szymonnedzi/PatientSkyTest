import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        CalendarService calendarService = new CalendarService();
        List<Calendar> allCalendars = calendarService.getAllCalendars();
        List<UUID> calendarsToCheck = calendarService.generateCalendarUUIDsToCheck();
        List<Calendar> foundCalendars = new ArrayList<>();
        for (Calendar calendar : allCalendars) {
            if (calendarsToCheck.contains(calendar.calendarID)) {
                foundCalendars.add(calendar);
            }
        }

        List<Appointment> appointments = new ArrayList<>();
        List<Timeslot> timeslots = new ArrayList<>();
        //Let's divide the working time between 08:00 and 20:15 into 15-minute slots for a day.
        //That gives us 49 timeslots to work with (8:00 - 8:15, 8:15 - 8:30 etc.
        //Creating an appointment will lead to blocking of a slot.
        //Codes: 0 - available, 1 - unavailable.
        //Integer[] availableSlots = new Integer[96];

/*
        for (Calendar calendar : foundCalendars) {
            System.out.println(calendar.getCalendarID());
            appointments = calendar.getAppointments();
            for (Appointment appointment : appointments) {
                System.out.println("Appointment:" + appointment.getStart() + " - " + appointment.getEnd());
            }
            timeslots = calendar.getTimeslots();
            for (Timeslot timeslot : timeslots) {
                // System.out.println("Timeslot:" + timeslot.getStart() + " - " + timeslot.getEnd());
            }
        }
*/

        Integer duration = 15;
        // Date() takes year as year 1900 + given year.
        Date startDate = new Date(119, 3, 23, 11, 0, 0);
        Date endDate = new Date(119, 3, 23, 17, 0, 0);
        calendarService.findAvailableTime(foundCalendars, duration, startDate, endDate);


    }
}
