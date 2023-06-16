package mx.uv.fei.sspger.logic;


public class User {
    protected String email;
    protected String password;
    protected String name;
    protected String lastName;
    protected int status;
    
    private final String EMAIL_REGEX = "^[a-zA-Z][\\w+]+@(?:uv\\.mx|estudiantes\\.uv\\.mx)$";
    private final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,45}$";
    private final String NAME_REGEX = "^(?=.{1,60}$)([a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+(?: [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*)$";
    private final String LAST_NAME_REGEX = "^(?=.{1,60}$)([a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+(?: [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*)$";

    
    public User(){
        
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
        validateEMail(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        validatePassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        validateName(name);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        validateLastName(lastName);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int userStatus) {
        this.status = userStatus;
    }
    
    private void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("La contraseña debe contener las siguientes características:\n"
                    + "1.- Debe contener de 8 a 45 caractéres\n"
                    + "2.- Al menos un digito\n"
                    + "3.- Combinación de mayúsculas y minúsculas");
        }
    }
    
    private void validateEMail(String eMail) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("El email debe contener las siguientes características:\n"
                    + "1.- No debe contener espacios en blanco\n"
                    + "2.- Solo los siguientes dominios son permitidos: @uv.mx, @estudiantes.uv.mx \n");
        }
    }
    
    private void validateName(String name){
        if (!name.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("El nombre debe contener las siguientes características:\n"
                    + "1. La longitud del nombre está entre 1 y 60 caracteres. \n"
                    + "2. El nombre puede consistir en una sola palabra o varias palabras separadas por espacios. \n"
                    + "3. Cada palabra del nombre puede contener letras en mayúscula o minúscula, y caracteres acentuados (á, é, í, ó, ú, Á, É, Í, Ó, Ú), la ü, la Ü, la ñ y la Ñ. \n");
        }
    }
    
    private void validateLastName(String lastName){
        if (!lastName.matches(LAST_NAME_REGEX)) {
            throw new IllegalArgumentException("El apellido debe contener las siguientes características:\n"
                    + "1. La longitud del nombre está entre 1 y 60 caracteres. \n"
                    + "2. El apellido puede consistir en una sola palabra o varias palabras separadas por espacios. \n"
                    + "3. Cada palabra del apellido puede contener letras en mayúscula o minúscula, y caracteres acentuados (á, é, í, ó, ú, Á, É, Í, Ó, Ú), la ü, la Ü, la ñ y la Ñ. \n");
        }
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final User other = (User)object;
        return (this.email == null ? other.email == null: this.email.equals(other.email));
    }
    
    
}
   
