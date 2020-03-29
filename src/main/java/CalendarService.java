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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

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
        HashSet<Integer> availableDurations = new HashSet<>(Arrays.asList(15, 30));
        if (!availableDurations.contains(duration)) {
            System.out.println("no time found");
            return;
        }
        List<Timeslot> timeslots = new ArrayList<>();

        for (Calendar calendar : calendars) {
            timeslots = calendar.getTimeslots();
            for (Timeslot timeslot : timeslots) {
                if (timeslot.getStart().after(startOfPeriodToSearch)
                        && timeslot.getEnd().before(endOfPeriodToSearch)) {
                    System.out.println("Timeslot:" + timeslot.getStart() + " - " + timeslot.getEnd());
                }
            }
        }
    }
}