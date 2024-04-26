package mx.uv.fei.sspger.logic;


public class Feedback {
    private int id;
    private Submission submission = new Submission();
    private String comment;
    
    private final String COMMENT_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,1000}$";

    public Feedback() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        validateComment(comment);
        this.comment = comment;
    }
    
    private void validateComment(String comment){
        if (!comment.matches(COMMENT_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Feedback other = (Feedback)object;
        return (this.comment == null ? other.comment == null : this.comment.equals(other.comment))
                && (this.id == other.id);
    }
    
}
