package accountant.HomeAccountant.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private  Long id;

    private LocalDate date;
    private String type;        //TODO item
    private String category;    //TODO item
    private int sum;
    private String currency;    //TODO item
    private String comment;

    public Record() {}

    public Record(LocalDate date, String type, String category, int sum, String currency, String comment) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.sum = sum;
        this.currency = currency;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
