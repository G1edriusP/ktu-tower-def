package game.facade;

import game.builder.LevelBuilder;
import game.entity.Archer;
import game.entity.Barbarian;
import game.entity.Ghost;
import game.entity.Skeleton;
import game.factory.AbstractSoldierFactory;
import game.level.Level;
import game.net.Image;
import game.net.Session;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class GameFacade {

    private Level level;
    private AbstractSoldierFactory soldierFactory;

    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

    public void gameStart(Stage stage) {
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
