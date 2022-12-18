package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;

public class Mud extends Liquid {

    private final int[][] MUD_COLOR_LIST = {{99,75,53},{105,75,47}};
    private final String MUD_NAME = "Mud";
    private final int MUD_DISPERSION_RATE = 1;
    private final Vector2 MUD_INIT_VEL = new Vector2(0,0);

    private final boolean MUD_IS_FLAMMABLE = true;
    private final int MUD_HEAT_TOLERANCE = 10;
    private final Class MUD_CHANGE_ELEMENT = Dirt.class;
    private final boolean MUD_CAN_CHANGE = true;

    private final int MUD_DENSITY = 20;

    public Mud(){
        this.colorList = MUD_COLOR_LIST;
        randomColor();
        this.name = MUD_NAME;
        this.dispersionRate = MUD_DISPERSION_RATE;
        this.velocity = MUD_INIT_VEL;
        varyVelocity();

        this.isFlammable = MUD_IS_FLAMMABLE;
        this.heatTolerance = MUD_HEAT_TOLERANCE;
        this.changeElement = MUD_CHANGE_ELEMENT;
        this.canChange = MUD_CAN_CHANGE;
        varyHeatTolerance();

        this.density = MUD_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}
