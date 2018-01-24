package controllers;

/**
 * Created by mainserver on 3/22/2017
 */

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
import views.MyStudentListViewCell;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

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
import views.MyStudentListViewCell;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mainserver on 3/11/2017
 */
@SuppressWarnings("unchecked")
public class StudentController implements Initializable {

    @FXML
    public TableView<Deadline> deadlineTable;

    private ObservableList<Deadline> mDeadlineObservableList;

    public StudentController() {
        mDeadlineObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        List<Deadline> deadlines = DbHelper.getDeadlines(AppContext.getInstance().getLoggedInUserId());
        if (deadlines != null) {
            mDeadlineObservableList.addAll(deadlines.toArray(new Deadline[0]));
        }
    }

}
