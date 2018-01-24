package dto;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by mainserver on 3/16/2017
 */
public class Deadline {
    private final int studentId;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty homework;
    private final SimpleStringProperty internal;
    private final SimpleStringProperty midterm;
    private final SimpleStringProperty specObj;
    private final SimpleStringProperty subject;

    public Deadline(int studentId, String dueDate, String homework, String internal, String midterm, String specObj, String subject) {

        this.studentId = studentId;
        this.dueDate = new SimpleStringProperty(dueDate);
        this.homework = new SimpleStringProperty(homework);
        this.internal = new SimpleStringProperty(internal);
        this.midterm = new SimpleStringProperty(midterm);
        this.specObj = new SimpleStringProperty(specObj);
        this.subject = new SimpleStringProperty(subject);
    }

    public int getStudentId() {
        return studentId;
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public String getHomework() {
        return homework.get();
    }

    public String getInternal() {
        return internal.get();
    }

    public String getMidterm() {
        return midterm.get();
    }

    public String getSpecObj() {
        return specObj.get();
    }

    public String getSubject() {
        return subject.get();
    }
}
