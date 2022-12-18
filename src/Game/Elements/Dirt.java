package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Powder;
import Game.Solid;

public class Dirt extends Solid {

    private final int[][] DIRT_COLOR_LIST = {{124,94,66},{131,94,59}};
    private final String DIRT_NAME = "Dirt";

    private final boolean DIRT_CAN_CHANGE = true;
    private final Class DIRT_CHANGE_ELEMENT = Mud.class;

    public Dirt(){
        this.colorList = DIRT_COLOR_LIST;
        randomColor();
        this.name = DIRT_NAME;

        this.canChange = DIRT_CAN_CHANGE;
        this.changeElement = DIRT_CHANGE_ELEMENT;
    }
}


