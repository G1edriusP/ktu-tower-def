package game.entity.blue;

import game.entity.Skeleton;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueSkeleton extends Skeleton {
    public BlueSkeleton() {
        this(UUID.randomUUID());
    }
    public BlueSkeleton(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("blue/skeleton.png")));
    }

    @Override
    public String getType() {
        return "blue-skeleton";
    }
}
