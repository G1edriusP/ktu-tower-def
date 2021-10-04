package game;

import java.net.URISyntaxException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

import game.entity.Soldier;
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
import game.net.ServersideImage;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import game.net.Serverside;
import game.net.Session;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Client extends Application {
    final Group group = new Group();
    final Scene scene = new Scene(group, 1200, 800);
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

        this.addSoldierButtons(group);

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

    public void addSoldierButtons(Group group) throws IOException {
        Image barbarian = new Image("/images/barbarian.png");
        ImageView barbarianButtonView = new ImageView(barbarian);
        Image ghost = new Image("/images/ghost.png");
        ImageView ghostButtonView = new ImageView(ghost);
        Image archer = new Image("/images/archer.png");
        ImageView archerButtonView = new ImageView(archer);
        Image zombie = new Image("/images/zombie.png");
        ImageView zombieButtonView = new ImageView(zombie);

        barbarianButtonView.setX(600);
        barbarianButtonView.setY(650);
        ghostButtonView.setX(720);
        ghostButtonView.setY(650);
        archerButtonView.setX(840);
        archerButtonView.setY(650);
        zombieButtonView.setX(960);
        zombieButtonView.setY(650);

        barbarianButtonView.setFitHeight(100);
        barbarianButtonView.setFitWidth(100);
        ghostButtonView.setFitHeight(100);
        ghostButtonView.setFitWidth(100);
        archerButtonView.setFitHeight(100);
        archerButtonView.setFitWidth(100);
        zombieButtonView.setFitHeight(100);
        zombieButtonView.setFitWidth(100);

        barbarianButtonView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isControlDown()) {
                    System.out.println("Barbaras");
                }
            }
        });;

        group.getChildren().add(barbarianButtonView);
        group.getChildren().add(ghostButtonView);
        group.getChildren().add(archerButtonView);
        group.getChildren().add(zombieButtonView);
    }

    public void addToGroup(Node object) {
        group.getChildren().add(object);
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
