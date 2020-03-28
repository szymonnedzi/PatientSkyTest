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

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        TypeReference<List<Appointment>> appointmentTypeReference = new TypeReference<List<Appointment>>() {
        };

        Path projectDir = Paths.get("");

        /*
        Path dannyDir = Paths.get(projectDir + "src/main/resources/CalendarJsons/Danny boy appointments.json");
        File file = new File(String.valueOf(dannyDir));
        List<Appointment> appointments = objectMapper.readValue(file, appointmentTypeReference);
        */
        TypeReference<Calendar> calendarTypeReference = new TypeReference<Calendar>() {
        };

        //Prepare the data - json locations
        Path dannyPath = Paths.get(projectDir + "src/main/resources/CalendarJsons/Danny boy.json");
        Path emmaPath = Paths.get(projectDir + "src/main/resources/CalendarJsons/Emma Win.json");
        Path joannaPath = Paths.get(projectDir + "src/main/resources/CalendarJsons/Joanna Hef.json");

        //File fetching
        File file2 = new File(String.valueOf(dannyPath));
        File file3 = new File(String.valueOf(emmaPath));
        File file4 = new File(String.valueOf(joannaPath));


        //Calendar mapping
        Calendar calendar2 = objectMapper.readValue(file2, calendarTypeReference);
        calendar2.setCalendarID(createCalendarUUID("Danny boy"));
        Calendar calendar3 = objectMapper.readValue(file3, calendarTypeReference);
        calendar3.setCalendarID(createCalendarUUID("Emma Win"));
        Calendar calendar4 = objectMapper.readValue(file4, calendarTypeReference);
        calendar4.setCalendarID(createCalendarUUID("Joanna Hef"));

        //Generate list of calendars.
        List<Calendar> calendars = new ArrayList<>();
        calendars.add(calendar2);
        calendars.add(calendar3);
        calendars.add(calendar4);

        for (Calendar calendar : calendars) {
            System.out.println(calendar.getCalendarID());
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

    //findAvailableTime(array<Uuid> calendarIds, Integer duration, Iso8601TimeInterval periodToSearch [,Uuid timeSlotType]);
}
