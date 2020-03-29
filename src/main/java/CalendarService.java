import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;


public class CalendarService {

    private static Logger logger = LogManager.getLogger(CalendarService.class);

    List<Calendar> getAllCalendars() {
        List<Calendar> calendarList = new ArrayList<>();
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };
        ObjectMapper objectMapper = new ObjectMapper().configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //This should be taking an array of UUIDs but can be refactored later
        calendarList.add(getOneCalendar(objectMapper, calendarTypeReference, "Danny Boy"));
        calendarList.add(getOneCalendar(objectMapper, calendarTypeReference, "Emma Win"));
        calendarList.add(getOneCalendar(objectMapper, calendarTypeReference, "Joanna Hef"));

        return calendarList;
    }

    private Calendar getOneCalendar(ObjectMapper objectMapper,
                                    TypeReference<Calendar> calendarTypeReference,
                                    String calendarOwner) {
        Calendar calendar = null;
        try {
            Path projectDir = Paths.get("");
            Path path = Paths.get(projectDir + "src/main/resources/CalendarJsons/" + calendarOwner + ".json");
            File file = new File(String.valueOf(path));
            calendar = objectMapper.readValue(file, calendarTypeReference);
            calendar.setCalendarID(createCalendarUUID(calendarOwner));
            logger.debug("Calendar fetched successfully");
        } catch (Exception e) {
            logger.error("Error processing .json calendar file for " + calendarOwner);
            e.printStackTrace();
        }
        return calendar;
    }

    static UUID createCalendarUUID(String calendarOwner) {
        if (calendarOwner.equals("Danny Boy")) {
            return UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9");
        }
        if (calendarOwner.equals("Emma Win")) {
            return UUID.fromString("452dccfc-975e-11e5-bfa5-c8e0eb18c1e9");
        }
        if (calendarOwner.equals("Joanna Hef")) {
            return UUID.fromString("48644c7a-975e-11e5-a090-c8e0eb18c1e9");
        } else return UUID.fromString(UUID.nameUUIDFromBytes(calendarOwner.getBytes()).toString());
    }

    List<UUID> generateCalendarUUIDsToCheck() {
        List<UUID> calendarsToCheck = new ArrayList<>();
        calendarsToCheck.add(UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9"));
        //calendarsToCheck.add(UUID.fromString("452dccfc-975e-11e5-bfa5-c8e0eb18c1e9"));
        //calendarsToCheck.add(UUID.fromString("48644c7a-975e-11e5-a090-c8e0eb18c1e9"));
        return calendarsToCheck;
    }

    public void findAvailableTime(List<Calendar> calendars,
                                  Integer duration,
                                  Date startOfPeriodToSearch,
                                  Date endOfPeriodToSearch) {
        if (isInvalidDuration(duration)) {
            return;
        }


        //From all timeslots select just the possible timeslots in the given time period
        List<Timeslot> foundTimeslots = findAllTimeslotsInCalendar
                (calendars, startOfPeriodToSearch, endOfPeriodToSearch);

        //Find the existing appointments to detect collisions
        List<Appointment> appointments = findAppointmentsInCalendar
                (calendars, startOfPeriodToSearch, endOfPeriodToSearch);

        System.out.println("Commence removing of collisions");

        List<Timeslot> availableTimeslots = removeCollisions(foundTimeslots, appointments);

        System.out.println("All available timeslots:");
        for (Timeslot timeslot : availableTimeslots) {
            System.out.println("Timeslot:"
                    + timeslot.getStart() + " - " + timeslot.getEnd());
        }

    }

    private List<Timeslot> removeCollisions(List<Timeslot> foundTimeslots, List<Appointment> appointments) {
        List<Timeslot> availableTimeslots = new ArrayList<>(foundTimeslots);
        for (Appointment appointment : appointments) {
            for (Iterator<Timeslot> iter = availableTimeslots.iterator(); iter.hasNext(); ) {
                Timeslot timeslot = iter.next();
                if (timeslot.getStart().equals(appointment.getStart())) {
                    iter.remove();
                }
            }
        }
        return availableTimeslots;
    }

    private boolean isInvalidDuration(Integer duration) {
        HashSet<Integer> availableDurations = new HashSet<>(Arrays.asList(15, 30));
        if (!availableDurations.contains(duration)) {
            System.out.println("Invalid duration");
            return true;
        }
        return false;
    }

    private List<Timeslot> findAllTimeslotsInCalendar(List<Calendar> calendars, Date startOfPeriodToSearch, Date endOfPeriodToSearch) {
        List<Timeslot> allTimeslots;
        List<Timeslot> foundTimeslots = new ArrayList<>();
        for (Calendar calendar : calendars) {
            allTimeslots = calendar.getTimeslots();
            for (Timeslot timeslot : allTimeslots) {
                if (timeslot.getStart().after(startOfPeriodToSearch)
                        && timeslot.getEnd().before(endOfPeriodToSearch)) {
                    System.out.println("Timeslot:" + timeslot.getStart() + " - " + timeslot.getEnd());
                    foundTimeslots.add(timeslot);
                }
            }
        }
        return foundTimeslots;
    }

    private List<Appointment> findAppointmentsInCalendar(List<Calendar> calendars, Date startOfPeriodToSearch, Date endOfPeriodToSearch) {
        List<Appointment> appointments = new ArrayList<>();
        //Existing appointments in the selected time period.
        for (Calendar calendar : calendars) {
            for (Appointment appointment : calendar.getAppointments()) {
                if (appointment.getStart().after(startOfPeriodToSearch)
                        && appointment.getEnd().before(endOfPeriodToSearch)) {
                    System.out.println("Appointment: " + appointment.getStart() + " - " + appointment.getEnd());
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }
}