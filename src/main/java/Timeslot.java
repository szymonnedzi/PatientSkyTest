import java.util.Date;
import java.util.UUID;

public class Timeslot {

    UUID id;
    UUID calendar_id;
    Date start;
    Date end;
    UUID type_id;
    Boolean public_bookable;
    Boolean out_of_office;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(UUID calendar_id) {
        this.calendar_id = calendar_id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public UUID getType_id() {
        return type_id;
    }

    public void setType_id(UUID type_id) {
        this.type_id = type_id;
    }

    public Boolean getPublic_bookable() {
        return public_bookable;
    }

    public void setPublic_bookable(Boolean public_bookable) {
        this.public_bookable = public_bookable;
    }

    public Boolean getOut_of_office() {
        return out_of_office;
    }

    public void setOut_of_office(Boolean out_of_office) {
        this.out_of_office = out_of_office;
    }
}
