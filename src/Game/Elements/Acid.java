package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Liquid;
import java.util.Random;

public class Acid extends Liquid {

    private final int[][] ACID_COLOR_LIST = {{15,200,15},{20,210,20}};
    private final String ACID_NAME = "Acid";
    private final int ACID_DISPERSION_RATE = 5;
    private final Vector2 ACID_INIT_VEL = new Vector2(0,0);

    private final Class ACID_CHANGE_ELEMENT = PoisonGas.class;
    private final boolean ACID_CAN_CHANGE = true;

    private final int ACID_DENSITY = 12;

    private final int REACT_RATE_DENOMINATOR = 50;

    public Acid(){
        this.colorList = ACID_COLOR_LIST;
        randomColor();
        this.name = ACID_NAME;
        this.dispersionRate = ACID_DISPERSION_RATE;
        this.velocity = ACID_INIT_VEL;
        varyVelocity();

        this.changeElement = ACID_CHANGE_ELEMENT;
        this.canChange = ACID_CAN_CHANGE;

        this.density = ACID_DENSITY;
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }

    public boolean willReact(){
        Random random = new Random();
        return random.nextInt(REACT_RATE_DENOMINATOR) == 0;
    }
}
