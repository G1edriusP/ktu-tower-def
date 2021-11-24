package game.facade;

import game.builder.LevelBuilder;
import game.command.SoldiersBarracks;
import game.composite.CompositeTile;
import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import game.level.Level;
import game.net.ISubject;
import game.net.Image;
import game.net.Session;
import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameFacade {

    private boolean running;
    private Group group;
    private SoldiersBarracks barracks;

    public GameFacade() {
        this.running = false;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void gameStart(Stage stage) {
        Session session = Session.getInstance();

        Level level = new LevelBuilder().newGrasslands().level3();
        AbstractSoldierFactory soldierFactory = level.getFriendlyTower().getAbstractSoldierFactory();
        barracks = new SoldiersBarracks(soldierFactory, group);

        if (session.isRed()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean teamRed = session.isRed();
        this.running = true;
        Thread gameLoopThread = new Thread(this::gameLoop);
        gameLoopThread.start();

        Platform.runLater(() -> {
            stage.setTitle("Tower Defense | " + (teamRed ? "Red" : "Blue"));
            level.getTiles().forEach(tile -> tile.addToGroup(group));
            group.getChildren().addAll(
                level.getFriendlyTower().getImageView(),
                level.getEnemyTower().getImageView()
            );
            addButtons();
        });
    }

    public void displayWinner(boolean red) {
        if (!this.running)
            return;
        this.running = false;
        String url = (red ? "images/red-won.png" : "images/blue-won.png");
        ImageView image = new ImageView(ImageStore.getInstance().getImage(url));
        Platform.runLater(() -> group.getChildren().add(image));
    }

    private void gameLoop() {
        Session session = Session.getInstance();

        while(this.running) {
            List<ISubject> toDelete = new ArrayList<>();

            session.getObjects().forEach((uuid, subject) -> {
                if (!(subject instanceof Soldier soldier))
                    return;

                if (!soldier.isOurControlled())
                    return;

                Soldier target = soldier.getTarget();
                if (target != null) {
                    boolean killed = soldier.attack(target);
                    if (killed)
                        toDelete.add(target);
                    return;
                }

                if (!soldier.move()) {
                    session.send("{\"action\":\"winner\",\"red\":"+(session.isRed()?"true":"false")+"}");
                    displayWinner(session.isRed());
                }
            });

            toDelete.forEach(ISubject::sendDelete);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addButtons() {
        addButton("images/barbarian.png", 600, 650, button -> this.barracks.makeDeferredBarbarian(600, 650));
        addButton("images/ghost.png", 720, 650, button -> this.barracks.makeDeferredGhost(720, 650));
        addButton("images/archer.png", 840, 650, button -> this.barracks.makeDeferredArcher(840, 650));
        addButton("images/skeleton.png", 960, 650, button -> this.barracks.makeDeferredSkeleton(960, 650));
    }


    private void addButton(String url, double x, double y,  EventHandler<? super MouseEvent> onClick) {
        ImageView button = new ImageView(ImageStore.getInstance().getImage(url));
        button.setX(x);
        button.setY(y);
        button.setFitHeight(100);
        button.setFitWidth(100);
        button.setOnMouseClicked(onClick);
        group.getChildren().add(button);
    }
}
