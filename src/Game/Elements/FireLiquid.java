package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;

public class FireLiquid extends Liquid {

    private final int[][] FIRE_COLOR_LIST = {{240,94,27},{250, 192, 0},{126, 58, 13}};
    private final String FIRE_NAME = "Fire (Liquid)";
    private final int FIRE_LIQUID_DISPERSION_RATE = 5;
    private final Vector2 FIRE_LIQUID_INIT_VEL = new Vector2(0,0);

    private final boolean FIRE_GIVES_HEAT = true;
    private final int FIRE_LIQUID_HEAT = 5;
    private final int FIRE_LIQUID_LIFETIME_FRAMES = 160; // backup value 20

    private final int FIRE_LIQUID_DENSITY = 2;

    public FireLiquid(){
        this.colorList = FIRE_COLOR_LIST;
        randomColor();
        this.name = FIRE_NAME;
        this.dispersionRate = FIRE_LIQUID_DISPERSION_RATE;
        this.velocity = FIRE_LIQUID_INIT_VEL;
        varyVelocity();

        this.givesHeat = FIRE_GIVES_HEAT;
        this.heat = FIRE_LIQUID_HEAT;
        this.lifeTimeFrames = FIRE_LIQUID_LIFETIME_FRAMES;
        varyLifetime();

        this.density = FIRE_LIQUID_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}