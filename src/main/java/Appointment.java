import java.util.Date;
import java.util.UUID;

public class Appointment {

    UUID id;
    UUID patient_id;
    UUID calendar_id;
    Date startTime;
    Date endTime;
    String patient_comment;
    String note;
    UUID time_slot_type_id;
    Integer state;
    String out_of_office_location;
    Boolean out_of_office;
    Boolean complete;
    Boolean is_scheduled;
}
