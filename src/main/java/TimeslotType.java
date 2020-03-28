import java.util.UUID;

public class TimeslotType {
    UUID id;
    String name;
    Integer slot_size;
    Boolean public_bookable;
    String color;
    String icon;
    String clinic_id;
    String deleted;
    Boolean out_of_office;
    Boolean enabled;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSlot_size() {
        return slot_size;
    }

    public void setSlot_size(Integer slot_size) {
        this.slot_size = slot_size;
    }

    public Boolean getPublic_bookable() {
        return public_bookable;
    }

    public void setPublic_bookable(Boolean public_bookable) {
        this.public_bookable = public_bookable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Boolean getOut_of_office() {
        return out_of_office;
    }

    public void setOut_of_office(Boolean out_of_office) {
        this.out_of_office = out_of_office;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
