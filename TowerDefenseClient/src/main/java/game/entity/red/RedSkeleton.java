package game.entity.red;

import game.entity.Skeleton;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class RedSkeleton extends Skeleton {
    public RedSkeleton() {
        this(UUID.randomUUID());
    }

    public RedSkeleton(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("red/skeleton.png")));
    }

    @Override
    public String getType() {
        return "red-skeleton";
    }
}
