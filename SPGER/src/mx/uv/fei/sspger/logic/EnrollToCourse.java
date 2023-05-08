package mx.uv.fei.sspger.logic;


public class EnrollToCourse {
    private int coursesId;
    private String courseId;
    private int studentId;
    
    public int getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(int coursesId) {
        this.coursesId = coursesId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final EnrollToCourse other = (EnrollToCourse)object;
         return this.courseId.equals(other.courseId) && this.studentId == (other.studentId);
    }
}
