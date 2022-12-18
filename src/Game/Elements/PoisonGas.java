package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Gas;

import java.util.Random;

public class PoisonGas extends Gas {

    private final int[][] POISON_GAS_COLOR_LIST = {{177, 219, 48},{251, 252, 55},{200, 228, 76}};
    private final String POISON_GAS_NAME = "Poison (Gas)";
    private final Vector2 POISON_GAS_INIT_VEL = new Vector2(0,0);
    private final int POISON_GAS_MOVE_PROB_DENOMINATOR = 4;
    private final int POISON_GAS_DISPERSION_RATE = 11;
    private final Class POISON_GAS_CHANGE_ELEMENT = Acid.class;

    private final int POISON_GAS_DENSITY = 5;

    private final int POISON_GAS_DISAPPEAR_PROB_DENOMINATOR = 300;
    private final int POISON_GAS_REACT_PROB_DENOMINATOR = 3;

    public PoisonGas(){
        this.colorList = POISON_GAS_COLOR_LIST;
        randomColor();
        this.name = POISON_GAS_NAME;
        this.gasMoveProbDenominator = POISON_GAS_MOVE_PROB_DENOMINATOR;
        this.dispersionRate = POISON_GAS_DISPERSION_RATE;
        this.velocity = POISON_GAS_INIT_VEL;
        varyVelocity();

        this.changeElement = POISON_GAS_CHANGE_ELEMENT;

        this.density = POISON_GAS_DENSITY;

        this.canChange = true;
    }

    public void move(Element[][] grid, int x, int y){
        // chance to disappear
        Random random = new Random();
        if(random.nextInt(POISON_GAS_DISAPPEAR_PROB_DENOMINATOR) == 0){
            grid[y][x] = new Empty();
        }
        else{
            super.move(grid,x,y);
        }
    }

    public boolean willReact(){
        Random random = new Random();
        if(random.nextInt(POISON_GAS_REACT_PROB_DENOMINATOR) == 0){
            return true;
        }
        return false;
    }
}
