package una.examengrupo3;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.FlowController;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AppContext.getInstance();
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain();
        FlowController.getInstance().goView("MainMenu");
    }

    public static void main(String[] args) {
        launch();
    }

}
