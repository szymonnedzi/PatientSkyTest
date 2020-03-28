import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;


public class CalendarService {

    private static Logger logger = LogManager.getLogger(CalendarService.class);

    List<Calendar> getAllCalendars() {
        //Config data fetching
        List<Calendar> calendarList = new ArrayList<>();
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        addOneCalendar(calendarList, objectMapper, calendarTypeReference, "Danny Boy");
        addOneCalendar(calendarList, objectMapper, calendarTypeReference, "Emma Win");
        addOneCalendar(calendarList, objectMapper, calendarTypeReference, "Joanna Hef");

        return calendarList;
    }

    private void addOneCalendar(List<Calendar> calendars,
                                ObjectMapper objectMapper,
                                TypeReference<Calendar> calendarTypeReference,
                                String calendarOwner) {
        try {
            Path projectDir = Paths.get("");
            Path path = Paths.get(projectDir + "src/main/resources/CalendarJsons/" + calendarOwner + ".json");
            File file = new File(String.valueOf(path));
            Calendar calendar = objectMapper.readValue(file, calendarTypeReference);
            calendar.setCalendarID(createCalendarUUID(calendarOwner));
            calendars.add(calendar);
            logger.info("Calendar added successfully");
        } catch (Exception e) {
            logger.error("Error processing .json calendar file for " + calendarOwner);
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