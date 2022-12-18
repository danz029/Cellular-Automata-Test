package Game.Elements;

import Engine.Vector2;
import Game.Solid;

public class FireSolid extends Solid {

    private final int[][] FIRE_COLOR_LIST = {{240,94,27},{250, 192, 0},{126, 58, 13}};
    private final String FIRE_NAME = "Fire (Solid)";

    private final boolean FIRE_GIVES_HEAT = true;
    private final int FIRE_SOLID_HEAT = 30;
    private final int FIRE_SOLID_LIFETIME_FRAMES = 120;

    public FireSolid(){
        this.colorList = FIRE_COLOR_LIST;
        randomColor();
        this.name = FIRE_NAME;

        this.givesHeat = FIRE_GIVES_HEAT;
        this.heat = FIRE_SOLID_HEAT;
        this.lifeTimeFrames = FIRE_SOLID_LIFETIME_FRAMES;
        varyLifetime();
    }
}
