package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;
import java.util.Random;

public class Lava extends Liquid {

    private final int[][] LAVA_COLOR_LIST = {{240,94,27},{235, 95,25}};
    private final String LAVA_NAME = "Lava";
    private final int LAVA_DISPERSION_RATE = 3;
    private final Vector2 LAVA_INIT_VEL = new Vector2(0,0);

    private final Class LAVA_CHANGE_ELEMENT = Rock.class;
    private final boolean LAVA_CAN_CHANGE = true;

    private final boolean LAVA_GIVES_HEAT = true;
    private final int LAVA_HEAT = 40;

    private final int LAVA_DENSITY = 20;

    private final int LAVA_SMOKE_PROB_DENOMINATOR = 200;



    public Lava(){
        this.colorList = LAVA_COLOR_LIST;
        randomColor();
        this.name = LAVA_NAME;
        this.dispersionRate = LAVA_DISPERSION_RATE;
        this.velocity = LAVA_INIT_VEL;
        varyVelocity();

        this.changeElement = LAVA_CHANGE_ELEMENT;
        this.canChange = LAVA_CAN_CHANGE;

        this.givesHeat = LAVA_GIVES_HEAT;
        this.heat = LAVA_HEAT;

        this.density = LAVA_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);

        // smoke
        Random random = new Random();
        for (int dX = -1; dX < 2; dX++) {
            if (isInBounds(grid, x + dX, y - 1) &&
                    checkElement(grid, x + dX, y - 1, new Class[]{Empty.class})
                    && random.nextInt(LAVA_SMOKE_PROB_DENOMINATOR) == 0) {
                grid[y - 1][x + dX] = new Fire();
            }
        }
    }
}
