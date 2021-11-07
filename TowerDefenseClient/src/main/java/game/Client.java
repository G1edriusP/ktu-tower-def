package game;

import game.builder.LevelBuilder;
import game.entity.*;
import game.factory.AbstractSoldierFactory;
import game.level.Level;
import game.net.ISubject;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import game.net.Image;
import game.net.Session;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.*;
import java.util.stream.Collectors;

public class Client extends Application {
    private Level level;

    final Group group = new Group();
    final Scene scene = new Scene(group, 1152, 768);
    private AbstractSoldierFactory soldierFactory;

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

        session.getStarted().addListener(observable -> Platform.runLater(() -> Client.this.gameStart(stage)));

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

    private void gameStart(Stage stage) {
        stage.setTitle("Tower Defense | " + (Session.getInstance().isRed() ? "Red" : "Blue"));

        level = new LevelBuilder().newSavannah().level1();
        soldierFactory = level.getFriendlyTower().getAbstractSoldierFactory();

        group.getChildren().addAll(
            level.getTiles().stream().map(Image::getImageView).collect(Collectors.toList())
        );
        group.getChildren().addAll(
            level.getFriendlyTower().getImageView(),
            level.getEnemyTower().getImageView()
        );

        addButtons();
    }

    private void addButtons() {
        addButton("images/barbarian.png", 600, 650, button -> {
            Barbarian barbarian = soldierFactory.createBarbarian();
            barbarian.register();
            barbarian.send();
        });

        addButton("images/ghost.png", 720, 650, button -> {
            Ghost ghost = soldierFactory.createGhost();
            ghost.register();
            ghost.send();
        });

        addButton("images/archer.png", 840, 650, button -> {
            Archer archer = soldierFactory.createArcher();
            archer.register();
            archer.send();
        });

        addButton("images/skeleton.png", 960, 650, button -> {
            Skeleton skeleton = soldierFactory.createSkeleton();
            skeleton.register();
            skeleton.send();
        });
    }

    private void addButton(String url, double x, double y,  EventHandler<? super MouseEvent> onClick) {
        ImageView button = new ImageView(url);
        button.setX(x);
        button.setY(y);
        button.setFitHeight(100);
        button.setFitWidth(100);
        button.setOnMouseClicked(onClick);
        group.getChildren().add(button);
    }
}
