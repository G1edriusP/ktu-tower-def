package game.facade;

import game.builder.LevelBuilder;
import game.command.SoldiersBarracks;
import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import game.level.Level;
import game.net.ISubject;
import game.net.Image;
import game.net.Session;
import game.singleton.ImageStore;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameFacade {

    private Group group;
    private SoldiersBarracks barracks;

    public void setGroup(Group group) {
        this.group = group;
    }

    public void gameStart(Stage stage) {
        stage.setTitle("Tower Defense | " + (Session.getInstance().isRed() ? "Red" : "Blue"));
        if (Session.getInstance().isRed()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Level level = new LevelBuilder().newGrasslands().level3();
        AbstractSoldierFactory soldierFactory = level.getFriendlyTower().getAbstractSoldierFactory();
        barracks = new SoldiersBarracks(soldierFactory, group);

        group.getChildren().addAll(
                level.getTiles().stream().map(Image::getImageView).collect(Collectors.toList())
        );
        group.getChildren().addAll(
                level.getFriendlyTower().getImageView(),
                level.getEnemyTower().getImageView()
        );

        addButtons();

        Thread gameLoopThread = new Thread(this::gameLoop);

        gameLoopThread.start();
    }

    private void gameLoop() {
        Session session = Session.getInstance();

        while(true) {
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
                    System.out.println((session.isRed()? "Red" :"Blue") + " won");
                    System.exit(0);
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
