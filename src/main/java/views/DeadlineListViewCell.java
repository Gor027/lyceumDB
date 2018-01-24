package views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.Deadline;
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
public class DeadlineListViewCell extends ListCell<Deadline> {

    @FXML
    private Label lblDueDate;
    @FXML
    private Label lblHomework;
    @FXML
    private Label lblInternal;
    @FXML
    private Label lblMidterm;
    @FXML
    private Label lblSpecObj;
    @FXML
    private Label lblSubject;

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @Override
    protected void updateItem(Deadline deadline, boolean empty) {
        super.updateItem(deadline, empty);

        if(empty || deadline == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/deadlineListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            lblDueDate.setText(String.valueOf(deadline.getDueDate()));
            lblHomework.setText(String.valueOf(deadline.getHomework()));
            lblInternal.setText(String.valueOf(deadline.getInternal()));
            lblMidterm.setText(String.valueOf(deadline.getMidterm()));
            lblSpecObj.setText(String.valueOf(deadline.getSpecObj()));
            lblSubject.setText(String.valueOf(deadline.getSubject()));

            setText(null);
            setGraphic(gridPane);
        }

    }
}