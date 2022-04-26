package week9;
import java.util.ArrayList; // 引入 ArrayList 类

public class Stu_Cour {
    private String id;
    private String name;
    private String gender;
    private String course_name;
    private double grade;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}
    public String getCourse_name() {return course_name;}
    public void setCourse_name(String course_name) {this.course_name = course_name;}
    public double getGrade() {return grade;}
    public void setGrade(double grade) {this.grade = grade;}


    public Stu_Cour(String id, String name, String gender, String course_name, double grade) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.course_name = course_name;
        this.grade = grade;
    }
    public Stu_Cour() {
    }

    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", course_name='" + course_name + '\'' +
                ", grade=" + grade ;
    }
}
