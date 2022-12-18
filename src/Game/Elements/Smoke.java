package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Gas;

import java.util.Random;

public class Smoke extends Gas {

    private final int[][] SMOKE_COLOR_LIST = {{150,150,150},{130,130,130}};
    private final String SMOKE_NAME = "Smoke";
    private final Vector2 SMOKE_INIT_VEL = new Vector2(0,0);
    private final int SMOKE_MOVE_PROB_DENOMINATOR = 3;
    private final int SMOKE_DISPERSION_RATE = 7;
    private final int SMOKE_DISAPPEAR_PROB_DENOMINATOR = 50;

    private final int SMOKE_DENSITY = 3;

    public Smoke(){
        this.colorList = SMOKE_COLOR_LIST;
        randomColor();
        this.name = SMOKE_NAME;
        this.gasMoveProbDenominator = SMOKE_MOVE_PROB_DENOMINATOR;
        this.dispersionRate = SMOKE_DISPERSION_RATE;
        this.velocity = SMOKE_INIT_VEL;
        varyVelocity();

        this.density = SMOKE_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        // chance to disappear
        Random random = new Random();
        if(random.nextInt(SMOKE_DISAPPEAR_PROB_DENOMINATOR) == 0){
            grid[y][x] = new Empty();
        }
        else{
            super.move(grid,x,y);
        }
    }
}
