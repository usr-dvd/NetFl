import java.util.Date;

public class Episode extends Content {
    private String description;
    private Date realiseDate = new Date();


    public Date getRealiseDate() {
        return realiseDate;
    }

    public Episode(String name, String description) {
        super(name);
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRealiseDate(Date realiseDate) {
        this.realiseDate = realiseDate;
    }
}
