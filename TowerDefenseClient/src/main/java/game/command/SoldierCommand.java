package game.command;

import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import game.singleton.ImageStore;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

abstract public class SoldierCommand implements ICommand  {

    protected AbstractSoldierFactory soldierFactory;
    private ImageView undoBtn;
    private Timer timer;
    private Group group;
    private int x;
    private int y;
    private int timeToSpawn;

    public SoldierCommand(AbstractSoldierFactory soldierFactory, Group group, int x, int y, int timeToSpawn) {
        this.soldierFactory = soldierFactory;
        this.group = group;
        this.x = x;
        this.y = y;
        this.timeToSpawn = timeToSpawn;
    }

    abstract protected Soldier makeSoldier();

    @Override
    public void exec() {
        makeSoldierTimer();
        makeUndoBtn();
    }

    @Override
    public void undo() {
        if(timer == null) {
            return;
        }
        timer.cancel();
        Platform.runLater(() -> group.getChildren().remove(undoBtn));
    }

    protected void makeUndoBtn() {
        undoBtn = new ImageView(ImageStore.getInstance().getImage("images/wait.png"));
        undoBtn.setX(this.x);
        undoBtn.setY(this.y);
        undoBtn.setFitHeight(100);
        undoBtn.setFitWidth(100);
        undoBtn.setOnMouseClicked(e -> undo());
        this.group.getChildren().add(undoBtn);
    }

    protected void makeSoldierTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Soldier soldier = makeSoldier();
                soldier.register();
                soldier.send();
                Platform.runLater(() -> group.getChildren().remove(undoBtn));
            }
        }, this.timeToSpawn);
    }
}
