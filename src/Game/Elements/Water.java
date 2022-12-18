package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;

public class Water extends Liquid {

    private final int[][] WATER_COLOR_LIST = {{38,102,145}};
    private final String WATER_NAME = "Water";
    private final int WATER_DISPERSION_RATE = 5;
    private final Vector2 WATER_INIT_VEL = new Vector2(0,0);

    private final boolean WATER_IS_FLAMMABLE = true;
    private final int WATER_HEAT_TOLERANCE = 5;
    private final Class WATER_CHANGE_ELEMENT = Steam.class;
    private final boolean WATER_CAN_CHANGE = true;

    private final int WATER_DENSITY = 10;

    public Water(){
        this.colorList = WATER_COLOR_LIST;
        randomColor();
        this.name = WATER_NAME;
        this.dispersionRate = WATER_DISPERSION_RATE;
        this.velocity = WATER_INIT_VEL;
        varyVelocity();

        this.isFlammable = WATER_IS_FLAMMABLE;
        this.heatTolerance = WATER_HEAT_TOLERANCE;
        this.changeElement = WATER_CHANGE_ELEMENT;
        this.canChange = WATER_CAN_CHANGE;
        varyHeatTolerance();

        this.density = WATER_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}
