import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.Soldier;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import net.Serverside;
import net.ServersideImage;
import net.Session;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Client extends Application {
    final Group group = new Group();
    final Scene scene = new Scene(group, 800, 600);
    private Soldier troop1;
    private static final int KEYBOARD_MOVEMENT_DELTA = 5;
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        troop1 = createTroop(50, 50);

        final ImageView img = troop1.getImageView();
        final TranslateTransition transition = createTranslateTransition(img);

        moveCircleOnKeyPress(scene, img);
        moveCircleOnMousePress(scene, img, transition);

        Map<UUID, ServersideImage> drawnObjects = new HashMap<>();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    for (Map.Entry<UUID, Serverside> entry : Session.getInstance().getObjects().entrySet()) {
                        if(drawnObjects.containsKey(entry.getKey())) {
                            continue;
                        }
                        ServersideImage serversideImage = (ServersideImage) entry.getValue();
                        serversideImage.addToGroup(group);
                        drawnObjects.put(entry.getKey(), serversideImage);
                    }
                } catch (URISyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        stage.setScene(scene);
        stage.setTitle("Tower Defense | 505");
        stage.show();
    }

    private void updateTroopPosition(double x, double y, ImageView img) {
        troop1.setX(x);
        troop1.setY(y);
        troop1.send();
    }

    public Soldier createTroop(double x, double y) throws URISyntaxException, InterruptedException, JsonProcessingException {
        Soldier soldier = new Soldier();
        soldier.register();
        soldier.setX(x);
        soldier.setY(y);
        soldier.send();
        return soldier;
    }

    private TranslateTransition createTranslateTransition(final ImageView img) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, img);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                img.setX(img.getTranslateX() + img.getX());
                img.setY(img.getTranslateY() + img.getY());
                img.setTranslateX(0);
                img.setTranslateY(0);
            }
        });
        return transition;
    }

    private void moveCircleOnKeyPress(Scene scene, final ImageView img) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP, W:
                        updateTroopPosition(img.getX(), img.getY() - KEYBOARD_MOVEMENT_DELTA, img);
                        break;
                    case RIGHT, D:
                        updateTroopPosition(img.getX() + KEYBOARD_MOVEMENT_DELTA, img.getY(), img);
                        break;
                    case DOWN, S:
                        updateTroopPosition(img.getX(), img.getY() + KEYBOARD_MOVEMENT_DELTA, img);
                        break;
                    case LEFT, A:
                        updateTroopPosition(img.getX() - KEYBOARD_MOVEMENT_DELTA, img.getY(), img);
                        break;
                }
            }
        });
    }

    private void moveCircleOnMousePress(Scene scene, final ImageView img, final TranslateTransition transition) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isControlDown()) {
                    updateTroopPosition(event.getSceneX(), event.getSceneY(), img);
                } else {
                    transition.setToX(event.getSceneX() - img.getX());
                    transition.setToY(event.getSceneY() - img.getY());
                    transition.playFromStart();
                }
            }
        });
    }
}
