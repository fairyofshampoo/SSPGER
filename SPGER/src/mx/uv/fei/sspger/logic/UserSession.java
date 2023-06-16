package mx.uv.fei.sspger.logic;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private String userType;
    private int privileges;

    /*
     * The reason the constructor is private is to prevent it from being 
     * instantiated outside of the class. All this to follow the singleton 
     * design pattern.
    */
    
    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void cleanUserSession(){
        userId = -1;
        userType = "Estudiante";
        privileges = 0;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public void setPrivileges(int privileges){
        this.privileges = privileges;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }
    
    public int getPrivileges(){
        return privileges;
    }
}
