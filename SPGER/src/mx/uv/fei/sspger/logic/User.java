package mx.uv.fei.sspger.logic;


public class User {
    protected String email;
    protected String password;
    protected String name;
    protected String lastName;
    protected int status;
    
    public static final String EMAIL_REGEX = "^(?=.{1,320}$)[^\\s@]+@(?:uv\\.mx|estudiantes\\.uv\\.mx|gmail\\.com|hotmail\\.com|outlook\\.com|edu\\.mx)$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]{8,45}$";
    public static final String NAME_REGEX = "^[\\p{L}\\p{M}\\s]{1,60}$";

    
    public User(){
        
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
        isEMailValid(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        isPasswordValid(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int userStatus) {
        this.status = userStatus;
    }
    
    private void isPasswordValid(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("La contraseña debe contener las siguientes características:\n"
                    + "1.- Debe contener de 8 a 45 caractéres\n"
                    + "2. Al menos un digito"
                    + "3. Combinación de mayúsculas y minúsculas"
                    + "4. Caracteres especiales");
        }
    }
    
    private void isEMailValid(String eMail) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("El email debe contener las siguientes características:\n"
                    + "1.- No debe contener espacios en blanco\n"
                    + "2.- Solo los siguientes dominios son permitidos: (@uv.mx, @estudiantes.uv.mx, @gmail.com, @hotmail.com, @outlook.com, @edu.mx)\n");
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
   