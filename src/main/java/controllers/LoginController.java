package controllers;

import context.AppContext;
import db.DbHelper;
import dto.LoginInfo;
import dto.StudentInfo;
import dto.TeacherInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mainserver on 3/11/2017
 */
public class LoginController implements Initializable {

    @FXML
    public PasswordField txtPassword;

    @FXML
    public Button btnLogin;

    @FXML
    public TextField txtLogin;

    @FXML
    public Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogin.setOnAction(e -> {
            String login = txtLogin.getText();
            String password = txtPassword.getText();
            LoginInfo info = DbHelper.login(login, password);

            if (info != null) {
                if (info.getRoleId() == 1) {
                    try {
                        AppContext.getInstance().setLoggedInUserId(((StudentInfo) info).getUserId());
                        openStudentPage();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else if (info.getRoleId() == 0) {
                    try {
                        AppContext.getInstance().setLoggedInUserId(((TeacherInfo) info).getUserId());
                        openTeacherPage();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid login/password");
                alert.showAndWait();
            }
        });
    }

    private void openStudentPage() throws IOException{
        pane.getScene().getWindow().hide();
        Pane pane = FXMLLoader.load(getClass().getResource("/student.fxml"));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Lyceum Database");
        stage.setScene(scene);
        stage.setResizable(false);
        AppContext.getInstance().setCurrentStage(stage);
        stage.show();
    }

    private void openTeacherPage() throws IOException {
        pane.getScene().getWindow().hide();
        SplitPane pane = FXMLLoader.load(getClass().getResource("/teacher.fxml"));
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Lyceum Database");
        stage.setScene(scene);
        stage.setResizable(false);
        AppContext.getInstance().setCurrentStage(stage);
        stage.show();
    }
}
