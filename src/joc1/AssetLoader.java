package joc1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AssetLoader {
    private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    public static BufferedImage menuBG;
    public static BufferedImage BGlayer1;
    public static BufferedImage BGlayer2;
    public static BufferedImage BGlayerDust;
    public static BufferedImage BGlayerPlanets;
    public static BufferedImage playerShip1;
    public static BufferedImage gem1;
    public static BufferedImage enemyBattlecruiser;
    public static BufferedImage enemyBomber;
    public static BufferedImage enemyFighter;
    public static BufferedImage enemyFrigate;
    public static BufferedImage enemyScout;
    public static List<BufferedImage> explosionSmall;
    public static List<BufferedImage> explosionBig; // 256 x 256
    public static List<BufferedImage> explosionHuge;
    public static BufferedImage border1;
    public static BufferedImage border2;
    public static BufferedImage iconSkillAS;
    public static BufferedImage iconSkillCrosshair;
    public static BufferedImage iconSkillExplosion;
    public static BufferedImage iconSkillSkull;
    public static BufferedImage iconSkillBounce;
    public static BufferedImage iconSkillHoming;
    public static BufferedImage iconSkillMS;
    public static BufferedImage iconSkillHP;


    public static void loadAssets() {
        try {
            BufferedImage explosionAtlas = ImageIO.read(new File("assets/SFX/Explosion Animations/explosion 3.png"));
            explosionBig = loadAtlas(explosionAtlas, 8, 8, 2, 62);
            explosionAtlas = ImageIO.read(new File("assets/SFX/Explosion Animations/explosion 4.png"));
            explosionHuge = loadAtlas(explosionAtlas, 8, 8, 1, 63);
            explosionAtlas = ImageIO.read(new File("assets/SFX/Explosion Animations/explosion 1.png"));
            explosionSmall = loadAtlas(explosionAtlas, 8, 8, 2, 62);

            playerShip1 = ImageIO.read(new File("assets/Ships/basic1.png"));
            enemyBattlecruiser = ImageIO.read(new File("assets/Ships/Nairan - Battlecruiser - Base.png"));
            enemyBomber = ImageIO.read(new File("assets/Ships/Nairan - Bomber - Base.png"));
            enemyFighter = ImageIO.read(new File("assets/Ships/Nairan - Fighter - Base.png"));
            enemyFrigate = ImageIO.read(new File("assets/Ships/Nairan - Frigate - Base.png"));
            enemyScout = ImageIO.read(new File("assets/Ships/Nairan - Scout - Base.png"));
            outline(enemyBomber, Color.white);
            outline(enemyFighter, Color.white);
            outline(enemyBattlecruiser, Color.red);
            outline(enemyFrigate, Color.white);
            outline(enemyScout, Color.white);

            gem1 = ImageIO.read(new File("assets/Gems/gem1.png"));

            border1 = ImageIO.read(new File("assets/GUI/border1.png"));
            border2 = ImageIO.read(new File("assets/GUI/border2.png"));

            iconSkillAS = ImageIO.read(new File("assets/Icons/AS.png"));
            iconSkillCrosshair  = ImageIO.read(new File("assets/Icons/crosshair.png"));
            iconSkillExplosion = ImageIO.read(new File("assets/Icons/explosion.png"));
            iconSkillSkull = ImageIO.read(new File("assets/Icons/skull.png"));
            iconSkillBounce = ImageIO.read(new File("assets/Icons/bounce.png"));
            iconSkillHoming = ImageIO.read(new File("assets/Icons/homing.png"));
            iconSkillMS = ImageIO.read(new File("assets/Icons/MS.png"));
            iconSkillHP = ImageIO.read(new File("assets/Icons/HP.png"));

            menuBG = ImageIO.read(new File("assets/GUI/menuBG.png"));

            BGlayer1 = (ImageIO.read(new File("assets/BG/layer1m.png")));
            BGlayer2 = (ImageIO.read(new File("assets/BG/layer2m.png")));
            BGlayerDust = (ImageIO.read(new File("assets/BG/space_background_pack/layers/parallax-space-stars.png")));
            BGlayerPlanets = (ImageIO.read(new File("assets/BG/space_background_pack/layers/parallax-space-far-planets.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<BufferedImage> loadAtlas(BufferedImage atlas, int rows, int cols, int offset, int count) {
        List<BufferedImage> sprites = new ArrayList<>();
        int spriteWidth = atlas.getWidth()/cols;
        int spriteHeight = atlas.getHeight()/rows;
        int startCol = offset % cols;
        int startRow = offset / cols;
        int x = startCol * spriteWidth;
        int y = startRow * spriteHeight;

        for (int i = 0; i < count; i++) {
            sprites.add(atlas.getSubimage(x, y, spriteWidth, spriteHeight));
            x += spriteWidth;
            if (x >= spriteWidth * cols) {
                x = 0;
                y += spriteHeight;
            }
        }

        return sprites;
    }

    static void printRGB(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                System.out.print(img.getRGB(x, y) + " ");
            }
            System.out.println();
        }
    }

    static void outline(BufferedImage img, Color color) {
        int borderRGB = color.getRGB();
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                //System.out.print("(" + x + "," + y + ") ");
                if (img.getRGB(x, y) != 0 && img.getRGB(x,y) != borderRGB) {
                    for (Direction dir: Direction.orthogonals()) {
                        if (img.getRGB(x + (int) dir.vector().x, y + (int) dir.vector().y) == 0) {
                            img.setRGB(x + (int) dir.vector().x, y + (int) dir.vector().y, borderRGB);
                        }
                    }
                }
            }
            //System.out.println();
        }
    }

    //https://stackoverflow.com/questions/31325742/java-awt-graphics-graphics-drawimage-is-too-slow-what-is-wrong
    public static BufferedImage toCompatibleImage(BufferedImage image) {
        /*
         * if image is already compatible and optimized for current system settings, simply return it
         */
        if (image.getColorModel().equals(GFX_CONFIG.getColorModel())) {
            return image;
        }

        // image is not optimized, so create a new image that is
        final BufferedImage new_image = GFX_CONFIG.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

}
