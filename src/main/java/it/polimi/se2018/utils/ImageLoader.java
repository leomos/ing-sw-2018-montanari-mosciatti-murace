package it.polimi.se2018.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    private Image getImage(String resourcePath) {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Image getToolCard(int id) {
        return getImage("/images/toolcards/ToolCard"+id+".jpg");
    }

    public Image getPrivateObjective(int id) {
        return getImage("/images/privateobjectives/PrivateObjective"+id+".png");
    }

    public Image getPublicObjective(int id) {
        return getImage("/images/publicobjectives/PublicObjective"+id+".JPG");
    }

    public Image getSagradaCover() {
        return getImage("/images/Sagrada.jpg");
    }
}
