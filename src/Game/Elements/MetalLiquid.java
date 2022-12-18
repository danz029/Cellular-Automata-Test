package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;
import java.util.Random;

public class MetalLiquid extends Liquid { // TODO: cool down

    private final int[][] MOLTEN_METAL_COLOR_LIST = {{192,192,192}, {180,180,180}};
    private final String MOLTEN_METAL_NAME = "MetalLiquid";
    private final int MOLTEN_METAL_DISPERSION_RATE = 4;
    private final Vector2 MOLTEN_METAL_INIT_VEL = new Vector2(0,0);

    private final Class MOLTEN_METAL_CHANGE_ELEMENT = Metal.class;
    private final boolean MOLTEN_METAL_CAN_CHANGE = true;

    private final boolean MOLTEN_METAL_GIVES_HEAT = true;
    private final int MOLTEN_METAL_HEAT = 8;

    private final int MOLTEN_METAL_DENSITY = 15;

    private final int MOLTEN_METAL_SMOKE_PROB_DENOMINATOR = 500;



    public MetalLiquid(){
        this.colorList = MOLTEN_METAL_COLOR_LIST;
        randomColor();
        this.name = MOLTEN_METAL_NAME;
        this.dispersionRate = MOLTEN_METAL_DISPERSION_RATE;
        this.velocity = MOLTEN_METAL_INIT_VEL;
        varyVelocity();

        this.changeElement = MOLTEN_METAL_CHANGE_ELEMENT;
        this.canChange = MOLTEN_METAL_CAN_CHANGE;

        this.givesHeat = MOLTEN_METAL_GIVES_HEAT;
        this.heat = MOLTEN_METAL_HEAT;

        this.density = MOLTEN_METAL_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);

        // smoke
        Random random = new Random();
        for (int dX = -1; dX < 2; dX++) {
            if (isInBounds(grid, x + dX, y - 1) &&
                    checkElement(grid, x + dX, y - 1, new Class[]{Empty.class})
                    && random.nextInt(MOLTEN_METAL_SMOKE_PROB_DENOMINATOR) == 0) {
                grid[y - 1][x + dX] = new Fire();
            }
        }
    }
}
