package Game.Elements;

import Game.Element;
import Game.Solid;

import java.util.Random;

public class Wood extends Solid {

    private final int[][] WOOD_COLOR_LIST = {{90,60,55},{82,55,47}};
    private final String WOOD_NAME = "Wood";
    private final boolean WOOD_IS_FLAMMABLE = true;
    private final int WOOD_HEAT_TOLERANCE = 20;

    private final boolean WOOD_CAN_CHANGE = true;
    private final Class WOOD_CHANGE_ELEMENT = FireSolid.class;

    public Wood(){
        this.colorList = WOOD_COLOR_LIST;
        randomColor();
        this.name = WOOD_NAME;
        this.isFlammable = WOOD_IS_FLAMMABLE;
        this.heatTolerance = WOOD_HEAT_TOLERANCE;
        varyHeatTolerance();
        this.canChange = WOOD_CAN_CHANGE;
        this.changeElement = WOOD_CHANGE_ELEMENT;
    }
}