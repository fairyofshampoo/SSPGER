/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;


public enum CourseStates{
    
    AVAILABLE("Disponible"), 
    UNAVAILABLE("No disponible");

    private final String courseState;

    CourseStates(String courseState) {
        this.courseState = courseState;
    }

    public String getCourseState() {
        return courseState;
    }
}
