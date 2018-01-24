package controllers;

import context.AppContext;
import db.DbHelper;
import dto.Deadline;
import dto.StudentInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.*;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mainserver on 3/11/2017
 */
@SuppressWarnings("unchecked")
public class TeacherController implements Initializable {

    @FXML
    public Button btnAddStudent;

    @FXML
    public ListView<StudentInfo> lvStudents;

    @FXML
    public TableView<Deadline> deadlineTable;

    @FXML
    public Button btnAddDeadline;
    @FXML
    public TextField txtHomework;
    @FXML
    public TextField txtMidterm;
    @FXML
    public TextField txtInternal;
    @FXML
    public TextField txtSpecObj;
    @FXML
    public TextField txtSubject;
    @FXML
    public DatePicker dpDueDate;

    private Integer selectedStudentId;

    private ObservableList<StudentInfo> mStudentsObservableList;
    private ObservableList<Deadline> mDeadlineObservableList;

    public TeacherController() {
        mDeadlineObservableList = FXCollections.observableArrayList();
        mStudentsObservableList = FXCollections.observableArrayList();

        List<StudentInfo> studentList = DbHelper.getAssignedStudents(AppContext.getInstance().getLoggedInUserId());

        if (studentList != null) {
            mStudentsObservableList.addAll(studentList.toArray(new StudentInfo[0]));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvStudents.setItems(mStudentsObservableList);
        lvStudents.setCellFactory(studentListView -> new MyStudentListViewCell());

        TableColumn dueDateColumn = new TableColumn("Due date");
        dueDateColumn.setMinWidth(100);
        dueDateColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("dueDate"));

        TableColumn homeworkColumn = new TableColumn("Homework");
        homeworkColumn.setMinWidth(100);
        homeworkColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("homework"));

        TableColumn internalColumn = new TableColumn("Internal");
        internalColumn.setMinWidth(100);
        internalColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("internal"));

        TableColumn midtermColumn = new TableColumn("Midterm");
        midtermColumn.setMinWidth(100);
        midtermColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("midterm"));

        TableColumn specObjColumn = new TableColumn("Spec-obj");
        specObjColumn.setMinWidth(100);
        specObjColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("specObj"));

        TableColumn subjectColumn = new TableColumn("Subject");
        subjectColumn.setMinWidth(100);
        subjectColumn.setCellValueFactory(
                new PropertyValueFactory<Deadline, String>("subject"));

        deadlineTable.setItems(mDeadlineObservableList);

        deadlineTable.getColumns().addAll(dueDateColumn, homeworkColumn, internalColumn, midtermColumn, specObjColumn, subjectColumn);

        btnAddStudent.setOnAction(event -> {
            try {
                showStudentList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        lvStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedStudentId = newValue.getUserId();
            mDeadlineObservableList.clear();
            List<Deadline> deadlines = DbHelper.getDeadlines(newValue.getUserId());
            if (deadlines != null) {
                mDeadlineObservableList.addAll(deadlines.toArray(new Deadline[0]));
            }
        });

        btnAddDeadline.setOnAction(event -> {
            if(selectedStudentId == null){
                return;
            }

            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dueDate = dtFormatter.format(dpDueDate.getValue());
            String homework = txtHomework.getText();
            String internal = txtInternal.getText();
            String midterm = txtMidterm.getText();
            String specObj = txtSpecObj.getText();
            String subject = txtSubject.getText();

            Deadline deadline = new Deadline(selectedStudentId, dueDate, homework, internal, midterm, specObj, subject);
            mDeadlineObservableList.add(deadline);
            DbHelper.addDeadline(selectedStudentId, deadline);

            txtHomework.clear();
            txtInternal.clear();
            txtMidterm.clear();
            txtSpecObj.clear();
            txtSubject.clear();
        });

    }

    private void showStudentList() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/studentList.fxml"));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initOwner(AppContext.getInstance().getCurrentStage());
        stage.initModality(Modality.APPLICATION_MODAL);

        Stage currentStage = AppContext.getInstance().getCurrentStage();
        AppContext.getInstance().setCurrentStage(stage);
        stage.showAndWait();
        AppContext.getInstance().setCurrentStage(currentStage);

        int studentId = AppContext.getInstance().getSelectedStudentId();

        if (studentId != -1) {
            addStudentToMyStudents(studentId);
        }
    }

    private void addStudentToMyStudents(int studentId) {
        if (mStudentsObservableList.stream().anyMatch(t -> t.getUserId() == studentId))
            return;

        StudentInfo studentInfo = DbHelper.getStudentInfo(studentId);
        mStudentsObservableList.add(studentInfo);
        DbHelper.assignStudentToTeacher(AppContext.getInstance().getLoggedInUserId(), studentId);
    }
}
