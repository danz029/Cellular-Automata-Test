package Game.Elements;

import Game.Solid;

public class Glass extends Solid {

    private final int[][] Glass_COLOR_LIST = {{233,233,233},{243,243,243},{238,238,238}};
    private final String Glass_NAME = "Glass";
    private final boolean GLASS_GIVES_HEAT = true;
    private final boolean GLASS_IS_FLAMMABLE = true;
    private final int GLASS_HEAT_TOLERANCE = 80;

    public Glass(){
        this.colorList = Glass_COLOR_LIST;
        randomColor();
        this.name = Glass_NAME;

        // allow glass to spread heat
        this.isFlammable = GLASS_IS_FLAMMABLE;
        this.givesHeat = GLASS_GIVES_HEAT;
        this.heatTolerance = GLASS_HEAT_TOLERANCE;
        varyHeatTolerance();
    }
}
