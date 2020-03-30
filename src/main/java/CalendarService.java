import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;


public class CalendarService {

    private static Logger logger = LogManager.getLogger(CalendarService.class);
    private AppointmentService appointmentService = new AppointmentService();
    private TimeslotService timeslotService = new TimeslotService();

    List<Calendar> getAllCalendars() {
        List<Calendar> calendarList = new ArrayList<>();
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };
        ObjectMapper objectMapper = new ObjectMapper().configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //This should be taking an array of UUIDs but can be refactored later.
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


    List<Calendar> getCalendarsByUUID(List<Calendar> allCalendars, List<UUID> generatedCalendarUUIDsToCheck) {
        List<Calendar> foundCalendars = new ArrayList<>();
        for (Calendar calendar : allCalendars) {
            if (generatedCalendarUUIDsToCheck.contains(calendar.calendarID)) {
                foundCalendars.add(calendar);
            }
        }
        return foundCalendars;
    }

    List<UUID> generateCalendarUUIDs() {
        List<UUID> calendarsToCheck = new ArrayList<>();
        calendarsToCheck.add(UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9"));
        calendarsToCheck.add(UUID.fromString("452dccfc-975e-11e5-bfa5-c8e0eb18c1e9"));
        calendarsToCheck.add(UUID.fromString("48644c7a-975e-11e5-a090-c8e0eb18c1e9"));
        return calendarsToCheck;
    }

    public void findAvailableTime(List<Calendar> calendars, Integer duration,
                                  Date startPeriod, Date endPeriod) {
        if (!isValidDuration(duration)) {
            return;
        }
        for (Calendar calendar : calendars) {
            findAvailableTime(calendar, startPeriod, endPeriod);
        }

    }


    public void findAvailableTime(Calendar calendar,
                                  Date startPeriod,
                                  Date endPeriod) {
        System.out.println("Calendar ID: " + calendar.getCalendarID());

        //From all timeslots select just the possible timeslots in the given time period
        List<Timeslot> foundTimeslots = timeslotService.findAllTimeslotsInCalendar
                (calendar, startPeriod, endPeriod);

        //Find the existing appointments to detect collisions
        List<Appointment> appointments = appointmentService.findAppointmentsInCalendar
                (calendar, startPeriod, endPeriod);

        List<Timeslot> availableTimeslots = removeCollisions(foundTimeslots, appointments);

        System.out.println("Available timeslots for Calendar ID: " + calendar.getCalendarID());
        for (Timeslot timeslot : availableTimeslots) {
            System.out.println("Timeslot: " + timeslot.getStart() + " - " + timeslot.getEnd()
                    + " duration[min]: " + calculateDuration(timeslot));
        }

    }

    private long calculateDuration(Timeslot timeslot) {
        LocalDateTime startDate = timeslot.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = timeslot.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return ChronoUnit.MINUTES.between(startDate, endDate);
    }

    private List<Timeslot> removeCollisions(List<Timeslot> foundTimeslots, List<Appointment> appointments) {
        // The assumption here is that no appointment is existing in 2 timeslots, like:
        // Slot1 14:00 - 14:30, Slot2 14:30 - 15:00, Appointment1: 14:15 - 14:45.
        // Presumably some split/merge functionality would exist for those cases, but it's outside of scope right now.
        List<Timeslot> availableTimeslots = new ArrayList<>(foundTimeslots);
        for (Appointment appointment : appointments) {
            for (Iterator<Timeslot> iter = availableTimeslots.iterator(); iter.hasNext(); ) {
                Timeslot timeslot = iter.next();
                if (timeslot.getStart().equals(appointment.getStart())
                        && timeslot.getEnd().equals(appointment.getEnd())) {
                    //System.out.println("Collision due to preexisting appointment: " + appointment.getStart() + " - " + appointment.getEnd() + ", timeslot removed");
                    iter.remove();
                }
            }
        }
        return availableTimeslots;
    }

    private boolean isValidDuration(Integer duration) {
        HashSet<Integer> availableDurations = new HashSet<>(Arrays.asList(15, 30));
        if (!availableDurations.contains(duration)) {
            System.out.println("Invalid duration");
            return false;
        }
        return true;
    }
}