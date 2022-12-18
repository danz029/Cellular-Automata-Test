package Engine.gfx;

import Game.Element;
import Game.World;

import java.awt.image.BufferedImage;

public class Image {

    private int w,h;
    private int[] p;

    public Image(World world){

        w = world.getWorldWidth();
        h = world.getWorldHeight();

        p = new int[w * h];

        Element[][] grid = world.getGrid();

        for(int y = 0;y < h;y++){
            for(int x = 0;x < w;x++){
                p[x + y * w] = grid[y][x].getColor();
            }
        }
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int[] getP() {
        return p;
    }
}
