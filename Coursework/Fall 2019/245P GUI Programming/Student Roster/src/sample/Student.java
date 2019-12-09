package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;

public class Student {

    private SimpleStringProperty studentID;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty major;
    private SimpleStringProperty grade;
    private SimpleStringProperty gradeOption;
    private SimpleStringProperty honorStatus;
    private SimpleBooleanProperty status;
    private SimpleStringProperty notes;
    private SimpleStringProperty img;

//    private final StringProperty studentIDProp = new SimpleStringProperty();
//    private final StringProperty lastNameProp = new SimpleStringProperty();
//    private final StringProperty firstNameProp = new SimpleStringProperty();
//    private final StringProperty majorProp = new SimpleStringProperty();
//    private final StringProperty gradeProp = new SimpleStringProperty();
//    private final StringProperty gradeOptionProp = new SimpleStringProperty();
//    private final StringProperty honorStatusProp = new SimpleStringProperty();
//    private final StringProperty notesProp = new SimpleStringProperty();
//    private final StringProperty imgProp = new SimpleStringProperty();

    public Student(){
        this.studentID.set(getStudentID());
        this.lastName.set(getLastName());
        this.firstName.set(getFirstName());
        this.major.set(getMajor());
        this.grade.set(getGrade());
        this.gradeOption.set(getGradeOption());
        this.honorStatus.set(getHonorStatus());
        this.status.set(getStatus());
        this.notes.set(getNotes());
        this.img.set(getImg());
    }

    public Student(String studentID, String lastName, String firstName, String major, String grade, String gradeOption, String honorStatus, Boolean status, String notes, String img){
        this.studentID = new SimpleStringProperty(studentID);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty (firstName);
        this.major = new SimpleStringProperty (major);
        this.grade = new SimpleStringProperty (grade);
        this.gradeOption = new SimpleStringProperty (gradeOption);
        this.honorStatus = new SimpleStringProperty (honorStatus);
        this.status = new SimpleBooleanProperty (status);
        this.notes = new SimpleStringProperty (notes);
        this.img = new SimpleStringProperty (img);
    }

    // setter
    public void setStudentID(String id) { studentID.set(id);}

    public void setLastName(String ln) { lastName.set(ln);}

    public void setFirstName(String fn) { firstName.set(fn);}

    public void setMajor(String m) { major.set(m);}

    public void setGrade(String g) { grade.set(g);}

    public void setGradeOption(String go) { gradeOption.set(go);}

    public void setHonorStatus(String hs) { honorStatus.set(hs);}

    public void setStatus(Boolean s) { status.set(s);}

    public void setNotes(String n) { notes.set(n);}

    public void setimgs(String i) {img.set(i);}

    // getter
    public String getStudentID() { return studentID.get();}

    public String getLastName() { return lastName.get();}

    public String getFirstName() { return firstName.get();}

    public String getMajor() { return major.get();}

    public String getGrade() { return grade.get();}

    public String getGradeOption() { return gradeOption.get();}

    public String getHonorStatus() { return honorStatus.get();}

    public Boolean getStatus() { return status.get(); }

    public String getNotes() { return notes.get();}

    public String getImg() { return img.get();}


    // methods
    public StringProperty studentIDProperty() { return this.studentID; }

    public StringProperty lastNameProperty() { return this.lastName; }

    public StringProperty firstNameProperty() { return this.firstName; }

    public StringProperty majorProperty() { return this.major; }

    public StringProperty gradeProperty() { return this.grade; }

    public StringProperty gradeOptionProperty() { return this.gradeOption; }

    public StringProperty honorStatusProperty() { return this.honorStatus; }

    public BooleanProperty statusProperty() { return this.status; }

    public StringProperty notesProperty() { return this.notes; }

    public StringProperty imgProperty() { return this.img; }


}
