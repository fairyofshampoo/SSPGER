package mx.uv.fei.sspger.logic;

import java.sql.Date;

public class Semester {

    private int semesterId;
    private Date startDate;
    private Date deadline;

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || (object.getClass() != this.getClass())) {
            return false;
        }
        final Semester other = (Semester) object;
        return (this.semesterId == 0 ? other.semesterId == 0 : this.semesterId == other.semesterId);
    }
}
