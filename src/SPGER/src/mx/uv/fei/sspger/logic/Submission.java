package mx.uv.fei.sspger.logic;

import java.util.Date;

public class Submission {

    private int id;
    private String description;
    private Date deliveryDate;
    private DeliverableFile file = new DeliverableFile();
    private Assignment assignment = new Assignment();

    private final String DESCRIPTION_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}\n]{1,1000}$";

    public Submission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        validateDescription(description);
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public DeliverableFile getFile() {
        return file;
    }

    public void setFile(DeliverableFile file) {
        this.file = file;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    private void validateDescription(String description) {
        if (!description.matches(DESCRIPTION_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Submission other = (Submission)object;
        return (this.description == null ? other.description == null : this.description.equals(other.description))
                && (this.id == other.id);
    }

}
