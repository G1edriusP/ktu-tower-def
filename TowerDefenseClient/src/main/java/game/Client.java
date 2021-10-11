package game;

import java.net.URISyntaxException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

import game.entity.*;
import game.entity.blue.BlueArcher;
import game.factory.AbstractSoldierFactory;
import game.factory.Creator;
import game.factory.TowerCreator;
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
import game.net.Serverside;
import game.net.Session;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.*;

public class Client extends Application {
    private Stage stage;
    final Group group = new Group();
    final Scene scene = new Scene(group, 1152, 768);
    private Tower tower;
//    private static final int KEYBOARD_MOVEMENT_DELTA = 10;
//    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.20);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final boolean[] once = {true};
        Map<UUID, ServersideImage> drawnObjects = new HashMap<>();
        Session session = Session.getInstance();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    if (once[0] && session.isStarted()) {
                        once[0] = false;
                            gameStart();
                    }

                    for (Map.Entry<UUID, Serverside> entry : session.getObjects().entrySet()) {
                        if(drawnObjects.containsKey(entry.getKey())) {
                            continue;
                        }
                        ServersideImage serversideImage = (ServersideImage) entry.getValue();
                        serversideImage.addToGroup(group);
                        drawnObjects.put(entry.getKey(), serversideImage);
                    }

                } catch (URISyntaxException | InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    private void gameStart() throws URISyntaxException, InterruptedException, IOException {
        Creator creator = new TowerCreator();
        tower = creator.createTower();
        tower.register();
        if(Session.getInstance().isRed()) {
            tower.setX(64);
            tower.setY(10 * 64);
        } else {
            tower.setX(16 * 64);
            tower.setY(64);
        }
        tower.send();

        // Title change
        if(Session.getInstance().isRed()) {
            this.stage.setTitle("Tower Defense | Red opponent");
        } else {
            this.stage.setTitle("Tower Defense | Blue opponent");
        }

        final ImageView img = tower.getImageView();
//        final TranslateTransition transition = createTranslateTransition(img);

//        moveCircleOnKeyPress(scene, img);
//        moveCircleOnMousePress(scene, img, transition);

        this.generateDummyMap();
        this.addSoldierButtons(group);
    }

    private void updateTroopPosition(double x, double y, ImageView img) {
        tower.setX(x);
        tower.setY(y);
        tower.send();
    }

    public void addSoldierButtons(Group group) throws IOException {
        ImageView barbarianButtonView = new ImageView("images/barbarian.png");
        ImageView ghostButtonView = new ImageView("images/ghost.png");
        ImageView archerButtonView = new ImageView("images/archer.png");
        ImageView skeletonButtonView = new ImageView("images/skeleton.png");

        barbarianButtonView.setX(600);
        barbarianButtonView.setY(650);
        ghostButtonView.setX(720);
        ghostButtonView.setY(650);
        archerButtonView.setX(840);
        archerButtonView.setY(650);
        skeletonButtonView.setX(960);
        skeletonButtonView.setY(650);

        barbarianButtonView.setFitHeight(100);
        barbarianButtonView.setFitWidth(100);
        ghostButtonView.setFitHeight(100);
        ghostButtonView.setFitWidth(100);
        archerButtonView.setFitHeight(100);
        archerButtonView.setFitWidth(100);
        skeletonButtonView.setFitHeight(100);
        skeletonButtonView.setFitWidth(100);

        barbarianButtonView.setOnMouseClicked(button -> {
            Barbarian barbarian = tower.getAbstractSoldierFactory().createBarbarian();
            addSoldierToMap(barbarianButtonView, barbarian);
        });

        skeletonButtonView.setOnMouseClicked(button -> {
            Skeleton skeleton = tower.getAbstractSoldierFactory().createSkeleton();
            addSoldierToMap(skeletonButtonView, skeleton);
        });

        ghostButtonView.setOnMouseClicked(button -> {
            Ghost ghost = tower.getAbstractSoldierFactory().createGhost();
            addSoldierToMap(ghostButtonView, ghost);
        });

        archerButtonView.setOnMouseClicked(button -> {
            Archer archer = tower.getAbstractSoldierFactory().createArcher();
            addSoldierToMap(archerButtonView, archer);
        });

        group.getChildren().add(barbarianButtonView);
        group.getChildren().add(ghostButtonView);
        group.getChildren().add(archerButtonView);
        group.getChildren().add(skeletonButtonView);
    }

    public void addSoldierToMap(ImageView button, Soldier soldier) {
        Random random = new Random();
        try {
            if(Session.getInstance().isRed()) {
                soldier.setY(random.nextInt(150) + 500);
                soldier.setX(random.nextInt(150) + 100);
            } else {
                soldier.setY(random.nextInt(150) + 100);
                soldier.setX(random.nextInt(150) + 900);
            }
            soldier.register();
        } catch (URISyntaxException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
        soldier.send();
    }

    public void addToGroup(Node object) {
        group.getChildren().add(object);
    }

//    private TranslateTransition createTranslateTransition(final ImageView img) {
//        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, img);
//        transition.setOnFinished(t -> {
//            img.setX(img.getTranslateX() + img.getX());
//            img.setY(img.getTranslateY() + img.getY());
//            img.setTranslateX(0);
//            img.setTranslateY(0);
//        });
//        return transition;
//    }

    private void generateDummyMap() {
        int[][] map = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1},
                {1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1},
                {1, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1},
                {1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
                {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int x = 0;
        int y = 0;
        for (int[] line: map) {
            for (int tile: line) {
                addTile(tile, x, y);
                x += 64;
            }
            x = 0;
            y += 64;
        }
    }

    private void addTile(int type, int x, int y) {
        ImageView tile = new ImageView("tiles/grass.jpg");
        if (type == 2) {
            tile = new ImageView("tiles/dirt.jpg");
        }

        tile.setX(x);
        tile.setY(y);

        tile.setFitHeight(64);
        tile.setFitWidth(64);

        group.getChildren().add(tile);
    }

//    private void moveCircleOnKeyPress(Scene scene, final ImageView img) {
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                switch (event.getCode()) {
//                    case UP, W:
//                        updateTroopPosition(img.getX(), img.getY() - KEYBOARD_MOVEMENT_DELTA, img);
//                        break;
//                    case RIGHT, D:
//                        updateTroopPosition(img.getX() + KEYBOARD_MOVEMENT_DELTA, img.getY(), img);
//                        break;
//                    case DOWN, S:
//                        updateTroopPosition(img.getX(), img.getY() + KEYBOARD_MOVEMENT_DELTA, img);
//                        break;
//                    case LEFT, A:
//                        updateTroopPosition(img.getX() - KEYBOARD_MOVEMENT_DELTA, img.getY(), img);
//                        break;
//                }
//            }
//        });
//    }

//    private void moveCircleOnMousePress(Scene scene, final ImageView img, final TranslateTransition transition) {
//        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (!event.isControlDown()) {
//                    updateTroopPosition(event.getSceneX(), event.getSceneY(), img);
//                } else {
//                    transition.setToX(event.getSceneX() - img.getX());
//                    transition.setToY(event.getSceneY() - img.getY());
//                    transition.playFromStart();
//                }
//            }
//        });
//    }
}
