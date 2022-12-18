package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Gas;

import java.util.Random;

public class Steam extends Gas {

    private final int[][] STEAM_COLOR_LIST = {{210,210,210},{205,205,205}};
    private final String STEAM_NAME = "Steam";
    private final Vector2 STEAM_INIT_VEL = new Vector2(0,0);
    private final int STEAM_MOVE_PROB_DENOMINATOR = 3;
    private final int STEAM_DISPERSION_RATE = 11;
    private final int STEAM_CONDENSATION_FRAMES = 240;
    private final int STEAM_CONDENSATION_PROB_DENOMINATOR = 60;
    private final Class STEAM_CHANGE_ELEMENT = Water.class;

    private final int STEAM_DENSITY = 4;

    public Steam(){
        this.colorList = STEAM_COLOR_LIST;
        randomColor();
        this.name = STEAM_NAME;
        this.gasMoveProbDenominator = STEAM_MOVE_PROB_DENOMINATOR;
        this.dispersionRate = STEAM_DISPERSION_RATE;
        this.velocity = STEAM_INIT_VEL;
        varyVelocity();

        this.framesElementChange = STEAM_CONDENSATION_FRAMES;
        this.changeElement = STEAM_CHANGE_ELEMENT;

        this.density = STEAM_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);

        // sets requirements for canChange
        this.canChange = false;
        if(framesElementChange != null && changeElement != null && framesNotMoving > framesElementChange &&
                new Random().nextInt(STEAM_CONDENSATION_PROB_DENOMINATOR) == 0){
            for(int i = -1;i < 2;i++){
                if(isInBounds(grid,x + i,y + 1) && checkElement(grid,x + i,y + 1,new Class[]{Empty.class})){
                    this.canChange = true;
                }
            }
        }
    }
}
