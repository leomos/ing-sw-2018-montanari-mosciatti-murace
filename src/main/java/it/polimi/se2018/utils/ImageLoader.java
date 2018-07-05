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
        //return getImage(Paths.get("/","images","toolcards", "ToolCard"+id+".jpg").toString());
    }

    public Image getPrivateObjective(int id) {
        return getImage("/images/privateobjectives/PrivateObjective"+id+".png");
        //return getImage(Paths.get("/","images", "privateobjectives", "PrivateObjective"+id+".png").toString());
    }

    public Image getPublicObjective(int id) {
        return getImage("/images/publicobjectives/PublicObjective"+id+".JPG");
        //return getImage(Paths.get("/","images", "publicobjectives", "PublicObjective"+id+".JPG").toString());
    }

    public Image getSagradaCover() {
        return getImage("/images/Sagrada.jpg");
        //return getImage(Paths.get("/","images", "Sagrada.jpg").toString());
    }
}