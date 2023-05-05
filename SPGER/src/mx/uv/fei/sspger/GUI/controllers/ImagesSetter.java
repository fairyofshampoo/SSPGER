package mx.uv.fei.sspger.GUI.controllers;


import javafx.scene.image.Image;


public class ImagesSetter {
    private static final Image homeImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/25694.png"));
    private static final Image searchBarImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/searchIcon.png"));
    private static final Image academicBodyImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/academicBody.png"));
    private static final Image coursesImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/addCourse.png"));
    private static final Image usersImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/addUsers.png"));
    private static final Image userIconImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/userIcon.png"));
    private static final Image returnImage = new Image(ImagesSetter.class.getResourceAsStream("/mx/uv/fei/sspger/GUI/resources/returnIcon.png"));
    
    public static Image getHomeImage(){
        return homeImage;
    }

    public static Image getSearchBarImage(){
        return searchBarImage;
    }

    public static Image getAcademicBodyImage(){
        return academicBodyImage;
    }

    public static Image getCoursesImage(){
        return coursesImage;
    }

    public static Image getUsersImage(){
        return usersImage;
    }

    public static Image getUserIconImage(){
        return userIconImage;
    }
    
    public static Image getReturnImage(){
        return returnImage;
    }
}
