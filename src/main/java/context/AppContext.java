package context;

import javafx.stage.Stage;

/**
 * Created by mainserver on 3/22/2017
 */
public class AppContext {

    private static final AppContext instance = new AppContext();
    private Stage currentStage;
    private int selectedStudentId;
    private int loggedInUserId;

    private AppContext() {
    }

    public static AppContext getInstance() {
        return instance;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public int getSelectedStudentId() {
        return selectedStudentId;
    }

    public void setSelectedStudentId(int selectedStudentId) {
        this.selectedStudentId = selectedStudentId;
    }
}
