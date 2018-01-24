package controllers;

import context.AppContext;
import db.DbHelper;
import dto.StudentInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import views.StudentListViewCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mainserver on 3/16/2017
 */
public class StudentListController implements Initializable {

    @FXML
    public Button btnSelect;
    @FXML
    public Button btnCancel;

    @FXML
    private ListView<StudentInfo> lvStudents;

    private ObservableList<StudentInfo> studentObservableList;

    public StudentListController() {
        studentObservableList = FXCollections.observableArrayList();

        List<StudentInfo> studentList = DbHelper.getStudentList();

        if (studentList != null) {
            studentObservableList.addAll(studentList.toArray(new StudentInfo[0]));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvStudents.setItems(studentObservableList);
        lvStudents.setCellFactory(studentListView -> new StudentListViewCell());

        btnSelect.setOnAction(event -> {

            StudentInfo selectedStudent = lvStudents.getSelectionModel().getSelectedItem();

            if (selectedStudent == null) {
                return;
            }

            int studentId = selectedStudent.getUserId();
            AppContext.getInstance().setSelectedStudentId(studentId);
            AppContext.getInstance().getCurrentStage().getScene().getWindow().hide();
        });

        btnCancel.setOnAction(event -> {
            AppContext.getInstance().setSelectedStudentId(-1);
            AppContext.getInstance().getCurrentStage().getScene().getWindow().hide();
        });

    }
}