package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Gas;

import java.util.Random;

public class Fire extends Gas {

    private final int[][] FIRE_COLOR_LIST = {{215, 53, 2},{252, 100, 0},{250, 192, 0}};
    private final String FIRE_NAME = "Fire";
    private final Vector2 FIRE_INIT_VEL = new Vector2(0,0);
    private final int FIRE_MOVE_PROB_DENOMINATOR = 3;
    private final int FIRE_DISPERSION_RATE = 7;
    private final int FIRE_DISAPPEAR_PROB_DENOMINATOR = 3;
    private final int FIRE_CHECK_Y = 4;

    private final boolean FIRE_GIVES_HEAT = true;
    private final int FIRE_HEAT = 10;

    private final int FIRE_DENSITY = 1;

    public Fire(){
        this.colorList = FIRE_COLOR_LIST;
        randomColor();
        this.name = FIRE_NAME;
        this.gasMoveProbDenominator = FIRE_MOVE_PROB_DENOMINATOR;
        this.dispersionRate = FIRE_DISPERSION_RATE;
        this.velocity = FIRE_INIT_VEL;
        varyVelocity();

        this.heat = FIRE_HEAT;
        this.givesHeat = FIRE_GIVES_HEAT;

        this.density = FIRE_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){

        // give heat
        giveHeat(grid,x,y,heat);

        // chance to disappear
        Random random = new Random();
        boolean willNotDisappear = false;
        // check bottom 3 elements if in bounds and element is fire
        for(int dX = -1;dX < 2;dX++){
            if(isInBounds(grid,x + dX,y + 1) && checkElement(grid,x + dX,y + 1,new Class[]{Fire.class})){
                willNotDisappear = true;
            }
        }
        // check y + dY element
        for(int dY = 1;dY < FIRE_CHECK_Y;dY++){
            if(isInBounds(grid,x,y + dY) && checkElement(grid,x,y + dY,new Class[]{Fire.class})){
                willNotDisappear = true;
            }
        }

        // element disappears: turns to smoke if top 3 elements are empty, new empty otherwise
        if(!willNotDisappear && random.nextInt(FIRE_DISAPPEAR_PROB_DENOMINATOR) == 0){
            boolean surroundingsEmpty = true;
            for(int dX = -1;dX < 2;dX++){
                if(isInBounds(grid,x + dX,y - 1) &&
                        !checkElement(grid,x + dX,y - 1,new Class[]{Empty.class})){
                    surroundingsEmpty = false;
                }
            }
            if(surroundingsEmpty){
                grid[y][x] = new Smoke();
            }
            else{
                grid[y][x] = new Empty();
            }
        }
        // element moves
        else{
            super.move(grid,x,y);
        }

        if(!changedPosition){
            randomColor();
        }
    }
}
