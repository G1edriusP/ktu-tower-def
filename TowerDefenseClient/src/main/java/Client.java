import java.awt.*;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.Troop;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import net.Serverside;
import net.Session;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Client extends Application {
    final Group group = new Group();
    final Scene scene = new Scene(group, 800, 600);
    private Troop troop1;
    private static final int KEYBOARD_MOVEMENT_DELTA = 5;
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        troop1 = createTroop(50, 50);

        final Circle circle = troop1.getObject();
        final TranslateTransition transition = createTranslateTransition(circle);

        moveCircleOnKeyPress(scene, circle);
        moveCircleOnMousePress(scene, circle, transition);

        Map<UUID, Troop> drawnObjects = new HashMap<>();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    for (Map.Entry<UUID, Serverside> entry : Session.getInstance().getObjects().entrySet()) {
                        if(drawnObjects.containsKey(entry.getKey())) {
                            continue;
                        }
                        Troop troop = ((Troop)entry.getValue());
                        group.getChildren().add(troop.getObject());
                        drawnObjects.put(entry.getKey(), troop);
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

    private void updateTroopPosition(double x, double y, Circle circle) {
        troop1.setX(x);
        troop1.setY(y);
        troop1.send();
    }

    public Troop createTroop(double x, double y) throws URISyntaxException, InterruptedException, JsonProcessingException {
        Troop troop = new Troop();
        Session.getInstance().register(troop);
        troop.setX(x);
        troop.setY(y);
        troop.send();
        return troop;
    }

    public void addToGroup(Node object) {
        group.getChildren().add(object);
    }

    private TranslateTransition createTranslateTransition(final Circle circle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
                circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
                circle.setTranslateX(0);
                circle.setTranslateY(0);
            }
        });
        return transition;
    }

    private void moveCircleOnKeyPress(Scene scene, final Circle circle) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP, W:
                        updateTroopPosition(circle.getCenterX(), circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA, circle);
                        break;
                    case RIGHT, D:
                        updateTroopPosition(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA, circle.getCenterY(), circle);
                        break;
                    case DOWN, S:
                        updateTroopPosition(circle.getCenterX(), circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA, circle);
                        break;
                    case LEFT, A:
                        updateTroopPosition(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA, circle.getCenterY(), circle);
                        break;
                }
            }
        });
    }

    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isControlDown()) {
                    updateTroopPosition(event.getSceneX(), event.getSceneY(), circle);
                } else {
                    transition.setToX(event.getSceneX() - circle.getCenterX());
                    transition.setToY(event.getSceneY() - circle.getCenterY());
                    transition.playFromStart();
                }
            }
        });
    }
}
