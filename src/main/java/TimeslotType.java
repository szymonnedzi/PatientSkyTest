import java.util.Date;
import java.util.UUID;

public class TimeslotType {

    UUID id;
    UUID calendar_id;
    Date start;
    Date end;
    UUID type_id;
    Boolean public_bookable;
    Boolean out_of_office;

}
