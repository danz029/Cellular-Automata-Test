package Game.Elements;

import Game.Solid;

public class Rock extends Solid {

    private final int[][] ROCK_COLOR_LIST = {{101,101,101},{86,86,86},{81,81,81}};
    private final String ROCK_NAME = "Rock";

    private final boolean ROCK_IS_FLAMMABLE = true;
    private final int ROCK_HEAT_TOLERANCE = 350;

    private final boolean ROCK_CAN_CHANGE = true;
    private final Class ROCK_CHANGE_ELEMENT = Lava.class;

    public Rock(){
        this.colorList = ROCK_COLOR_LIST;
        randomColor();
        this.name = ROCK_NAME;

        this.isFlammable = ROCK_IS_FLAMMABLE;
        this.heatTolerance = ROCK_HEAT_TOLERANCE;
        varyHeatTolerance();
        this.canChange = ROCK_CAN_CHANGE;
        this.changeElement = ROCK_CHANGE_ELEMENT;
    }
}
