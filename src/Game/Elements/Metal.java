package Game.Elements;

import Game.Solid;

public class Metal extends Solid {

    private final int[][] METAL_COLOR_LIST = {{150,140,130},{150,145,140},{150,140,140}};
    private final String METAL_NAME = "Metal";

    private final boolean METAL_IS_FLAMMABLE = true;
    private final int METAL_HEAT_TOLERANCE = 100;

    private final boolean METAL_CAN_CHANGE = true;
    private final Class METAL_CHANGE_ELEMENT = MetalLiquid.class;

    public Metal(){
        this.colorList = METAL_COLOR_LIST;
        randomColor();
        this.name = METAL_NAME;

        this.isFlammable = METAL_IS_FLAMMABLE;
        this.heatTolerance = METAL_HEAT_TOLERANCE;
        varyHeatTolerance();
        this.canChange = METAL_CAN_CHANGE;
        this.changeElement = METAL_CHANGE_ELEMENT;
    }
}
