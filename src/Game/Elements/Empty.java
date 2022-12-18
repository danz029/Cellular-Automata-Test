package Game.Elements;

import Game.Element;

public class Empty extends Element {

    private final int[][] EMPTY_COLOR_LIST = {{255,255,255}};
    private final String EMPTY_NAME = "";

    public Empty(){
        this.colorList = EMPTY_COLOR_LIST;
        randomColor();
        this.name = EMPTY_NAME;
    }
}
