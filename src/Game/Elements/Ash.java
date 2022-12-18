package Game.Elements;

import Engine.Vector2;
import Game.Element;
import Game.Powder;

public class Ash extends Powder {

    private final int[][] ASH_COLOR_LIST = {{133, 130, 117},{114, 112, 100},{152, 149, 134}};
    private final String ASH_NAME = "Ash";
    private final Vector2 ASH_INIT_VEL = new Vector2(0,0);

    public Ash(){
        this.colorList = ASH_COLOR_LIST;
        randomColor();
        this.name = ASH_NAME;
        this.velocity = ASH_INIT_VEL;
        varyVelocity();
    }

    public void move(Element[][] grid, int x, int y){
        super.move(grid,x,y);
    }
}
