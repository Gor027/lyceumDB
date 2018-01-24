package views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.StudentInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by mainserver on 3/16/2017
 */
public class StudentListViewCell extends ListCell<StudentInfo> {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private FontAwesomeIconView fxIconGender;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(StudentInfo student, boolean empty) {
        super.updateItem(student, empty);

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/studentListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            label1.setText(String.valueOf(student.getUserId()));
            label2.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));

            if(student.getGender().equals(StudentInfo.GENDER.MALE)) {
                fxIconGender.setIcon(FontAwesomeIcon.MARS);
            } else if(student.getGender().equals(StudentInfo.GENDER.FEMALE)) {
                fxIconGender.setIcon(FontAwesomeIcon.VENUS);
            } else {
                fxIconGender.setIcon(FontAwesomeIcon.GENDERLESS);
            }

            setText(null);
            setGraphic(gridPane);
        }

    }
}