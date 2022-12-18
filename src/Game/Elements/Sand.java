package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Powder;

import java.util.Random;

public class Sand extends Powder {

    private final int[][] SAND_COLOR_LIST = {{220,192,139},{237,201,175},{248,223,161}};
    private final String SAND_NAME = "Sand";
    private final Vector2 SAND_INIT_VEL = new Vector2(0,0);

    private final boolean SAND_IS_FLAMMABLE = true;
    private final int SAND_HEAT_TOLERANCE = 25;

    private final boolean SAND_CAN_CHANGE = true;
    private final Class SAND_CHANGE_ELEMENT = Glass.class;

    public Sand(){
        this.colorList = SAND_COLOR_LIST;
        randomColor();
        this.name = SAND_NAME;
        this.velocity = SAND_INIT_VEL;
        varyVelocity();

        this.isFlammable = SAND_IS_FLAMMABLE;
        this.heatTolerance = SAND_HEAT_TOLERANCE;
        varyHeatTolerance();
        this.canChange = SAND_CAN_CHANGE;
        this.changeElement = SAND_CHANGE_ELEMENT;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}
