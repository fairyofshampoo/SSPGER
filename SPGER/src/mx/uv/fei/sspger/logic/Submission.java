package mx.uv.fei.sspger.logic;

import java.sql.Date;

public class Submission {
    private int id;
    private String description;
    private Date deliveryDate;
    private int idDeliverableFile;

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
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getIdDeliverableFile() {
        return idDeliverableFile;
    }

    public void setIdDeliverableFile(int idDeliverableFile) {
        this.idDeliverableFile = idDeliverableFile;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Submission other = (Submission)object;
        return (this.description == null ? other.description == null : this.description.equals(other.description)) &&
            (this.id == other.id) &&
            (this.idDeliverableFile == other.idDeliverableFile);
    }
    
}
