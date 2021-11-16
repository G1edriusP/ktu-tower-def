package game;

import game.facade.GameFacade;
import game.net.ISubject;
import game.net.Image;
import game.net.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.UUID;

public class Client extends Application {
    final Group group = new Group();
    final Scene scene = new Scene(group, 1152, 768);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    @Override
    public void start(Stage stage)  {
        Session session = Session.getInstance();

        GameFacade gameFacade = new GameFacade();
        gameFacade.setGroup(group);

        session.getStarted().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("listener: start");
                gameFacade.gameStart(stage);
            } else {
                System.out.println("listener: stop");
                gameFacade.displayWinner(session.isRed());
            }
        });
        session.getStarted().addListener(observable -> {
            Platform.runLater(() -> gameFacade.gameStart(stage));
        });

        session.getObjects().addListener((MapChangeListener<UUID, ISubject>) change -> {
            if (change.wasAdded()) {
                Image image = (Image) change.getValueAdded();
                Platform.runLater(() -> group.getChildren().add(image.getImageView()));
            }
            if (change.wasRemoved()) {
                Image image = (Image) change.getValueRemoved();
                Platform.runLater(() -> group.getChildren().remove(image.getImageView()));
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
