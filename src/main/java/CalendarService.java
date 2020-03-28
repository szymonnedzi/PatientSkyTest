import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CalendarService {

    private static Logger logger = LogManager.getLogger(CalendarService.class);

    List<Calendar> getAllCalendars() throws Exception {
        //Config data fetching
        List<Calendar> calendars = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };
        Path projectDir = Paths.get("");

        addOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Danny Boy");
        addOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Emma Win");
        addOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Joanna Hef");

        return calendars;
    }

    private void addOneCalendar(List<Calendar> calendars, ObjectMapper objectMapper, TypeReference<Calendar> calendarTypeReference, Path projectDir, String calendarOwner) throws Exception {
        Calendar calendar = null;
        try {
            Path path = Paths.get(projectDir + "src/main/resources/CalendarJsons/" + calendarOwner + ".json");
            File file = new File(String.valueOf(path));
            calendar = objectMapper.readValue(file, calendarTypeReference);
            calendar.setCalendarID(createCalendarUUID(calendarOwner));
            calendars.add(calendar);
            logger.info("Calendar added successfully");
        } catch (Exception e) {
            logger.error("Error processing .json calendar file");
            e.printStackTrace();
        }
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

}
