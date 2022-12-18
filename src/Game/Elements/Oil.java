package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;

public class Oil extends Liquid {

    private final int[][] OIL_COLOR_LIST = {{44,36,22},{40,33,20}};
    private final String OIL_NAME = "Oil";
    private final int OIL_DISPERSION_RATE = 5;
    private final Vector2 OIL_INIT_VEL = new Vector2(0,0);

    private final boolean OIL_IS_FLAMMABLE = true;
    private final int OIL_HEAT_TOLERANCE = 3;
    private final Class OIL_CHANGE_ELEMENT = FireLiquid.class;
    private final boolean OIL_CAN_CHANGE = true;

    private final int OIL_DENSITY = 3;

    public Oil(){
        this.colorList = OIL_COLOR_LIST;
        randomColor();
        this.name = OIL_NAME;
        this.dispersionRate = OIL_DISPERSION_RATE;
        this.velocity = OIL_INIT_VEL;
        varyVelocity();

        this.isFlammable = OIL_IS_FLAMMABLE;
        this.heatTolerance = OIL_HEAT_TOLERANCE;
        this.changeElement = OIL_CHANGE_ELEMENT;
        this.canChange = OIL_CAN_CHANGE;
        varyHeatTolerance();

        this.density = OIL_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}
