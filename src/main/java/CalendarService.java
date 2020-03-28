import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalendarService {

    List<Calendar> getAllCalendars() throws IOException {
        //Config data fetching
        List<Calendar> calendars = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };
        Path projectDir = Paths.get("");

        fetchOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Danny Boy");
        fetchOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Emma Win");
        fetchOneCalendar(calendars, objectMapper, calendarTypeReference, projectDir, "Joanna Hef");

        return calendars;
    }

    private void fetchOneCalendar(List<Calendar> calendars, ObjectMapper objectMapper, TypeReference<Calendar> calendarTypeReference, Path projectDir, String calendarOwner) throws IOException {
        Path path = Paths.get(projectDir + "src/main/resources/CalendarJsons/" + calendarOwner + ".json");
        File file = new File(String.valueOf(path));
        Calendar calendar = objectMapper.readValue(file, calendarTypeReference);
        calendar.setCalendarID(createCalendarUUID(calendarOwner));
        calendars.add(calendar);
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
