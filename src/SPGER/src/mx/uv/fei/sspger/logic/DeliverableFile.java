package mx.uv.fei.sspger.logic;

public class DeliverableFile {

    private int id;
    private String name;
    private String path;
    private String extension;

    private final String GENERAL_REGEX = ".{1,5000}";

    public DeliverableFile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        validateName(name);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        validatePath(path);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
        validateExtension(extension);
    }

    private void validatePath(String path) {
        if (!path.matches(GENERAL_REGEX)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateName(String name) {
        if (!name.matches(GENERAL_REGEX)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateExtension(String extension) {
        if (!extension.matches(GENERAL_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final DeliverableFile other = (DeliverableFile)object;
        return (this.name == null ? other.name == null : this.name.equals(other.name))
                && this.path == null ? other.path == null : this.path.equals(other.path)
                && this.extension == null ? other.extension == null : this.extension.equals(other.extension);
    }

}
