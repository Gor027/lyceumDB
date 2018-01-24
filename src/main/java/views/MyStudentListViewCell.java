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
 * Created by mainserver on 3/22/2017
 */
public class MyStudentListViewCell extends ListCell<StudentInfo> {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

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
                mLLoader = new FXMLLoader(getClass().getResource("/myStudentListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            label1.setText(String.valueOf(student.getUserId()));
            label2.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));

            setText(null);
            setGraphic(gridPane);
        }

    }
}
