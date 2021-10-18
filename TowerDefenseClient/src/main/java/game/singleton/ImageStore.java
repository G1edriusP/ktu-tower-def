package game.singleton;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageStore {
    private static ImageStore instance = null;
    private Map<String, Image> images;

    private ImageStore() {
        this.images = new HashMap<>();
    }

    public static synchronized ImageStore getInstance() {
        if (instance == null)
            instance = new ImageStore();
        return instance;
    }

    public synchronized Image getImage(String url) {
        if (!this.images.containsKey(url)) {
            this.images.put(url, new Image(url));
        }
        return this.images.get(url);
    }
}
