package Game;

import Game.Elements.*;

import java.util.Random;

public class World {

    private int worldHeight;
    private int worldWidth;
    private Element[][] grid;

    public World(int worldHeight,int worldWidth){
        grid = new Element[worldHeight][worldWidth];
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
    }

    public void init(){
        // set all empty
        for(int y = 0;y < worldHeight;y++){
            for(int x = 0;x < worldWidth;x++){
                setElement(x, y, new Empty());
            }
        }

        // set bottom to rock
        for(int y = worldHeight-1;y > 2 * worldHeight / 3;y--){
            for(int x = 0;x < worldWidth;x++){
                setElement(x, y, new Rock());
            }
        }

        // set cube to water
        for(int y = 30;y < 60; y++){
            for(int x = 30;x < 60;x++){
                setElement(x, y, new Water());
            }
        }

        // set cube to sand
        for(int y = 30;y < 60; y++){
            for(int x = 120;x < 150;x++){
                setElement(x, y, new Sand());
            }
        }
    }

    public void update(){

        // reset all moved to false
        for(int y = worldHeight-1;y >= 0;y--) {
            for (int x = 0; x < worldWidth; x++) {
                if(Movable.class.isAssignableFrom(grid[y][x].getClass())){
                    ((Movable) grid[y][x]).setMoved(false);
                    ((Movable) grid[y][x]).setVelocityMoved(false);
                }
            }
        }

        // moves and updates each element
        for(int y = worldHeight-1;y >= 0;y--){
            Random randomNum = new Random();
            if(randomNum.nextInt(2) == 0){
                // update left to right
                for(int x = 0;x < worldWidth;x++){
                    if(Movable.class.isAssignableFrom(grid[y][x].getClass())){
                        ((Movable) grid[y][x]).move(this.getGrid(),x,y);
                    }
                    if(grid[y][x].checkElement(grid[y][x],new Class[]{Steam.class})){
                        elementChange(x,y);
                    }
                    updateHeat(x,y);
                    reaction(x,y);

                }
            }
            else{
                // update right to left
                for(int x = worldWidth - 1;x >= 0;x--){
                    if(Movable.class.isAssignableFrom(grid[y][x].getClass())){
                        ((Movable) grid[y][x]).move(this.getGrid(),x,y);
                    }
                    if(grid[y][x].checkElement(grid[y][x],new Class[]{Steam.class})){
                        elementChange(x,y);
                    }
                    updateHeat(x,y);
                    reaction(x,y);
                }
            }
        }
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public Element[][] getGrid() {
        return grid;
    }

    public void setElement(int x,int y,Element e) {
        if(x >= 0 && x < worldWidth && y >= 0 || y < worldHeight){
            grid[y][x] = e;
        }
    }

    public void clear(){
        for(int y = 0;y < worldHeight;y++){
            for(int x = 0;x < worldWidth;x++){
                setElement(x,y,new Empty());
            }
        }
    }

    public void elementChange(int x,int y){
        Element e = grid[y][x];
        if(e.canChange){
            try {
                grid[y][x] = (Element) e.changeElement.newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateHeat(int x,int y){
        Element e = grid[y][x];
        // is flammable and exceeds tolerance
        if(e.isFlammable){
            if(e.currHeat >= e.heatTolerance){
                this.elementChange(x,y);
            }
            else{
                e.currHeat -= e.heatTolerance / 300f;
                if(e.currHeat < 0){
                    e.currHeat = 0;
                }
            }
        }

        // for heat givers
        if(e.givesHeat){
            // fire liquid and fire solid
            if(e.checkElement(grid,x,y,new Class[]{FireLiquid.class,FireSolid.class}))
            {
                e.randomColor();
                // spread heat to surrounding elements
                e.giveHeat(grid,x,y,e.heat);

                // surrounding smoke and fire
                // check surrounding 8 elements are empty
                for(int dY = -1;dY < 2;dY++){
                    for(int dX = -1;dX < 2 && !(dX == 0 && dY == 0);dX++){
                        if(e.checkElement(grid,x + dX,y + dY,new Class[]{Empty.class})){
                            // chance to set as smoke or fire
                            Random random = new Random();
                            if(random.nextInt(5) == 0){
                                // fire on top of fireSolid/liquid
                                if(dY == -1){
                                    grid[y + dY][x + dX] = new Fire();
                                }
                                // chance of smoke or fire
                                else if(random.nextInt(3) == 0){
                                    grid[y + dY][x + dX] = new Smoke();
                                }
                                else{
                                    grid[y + dY][x + dX] = new Fire();
                                }
                            }
                        }
                    }
                }

                // turns to ash if dies
                if(e.lifeTimeFrames == 0){
                    // fire solid
                    if(e.checkElement(e,new Class[]{Solid.class})){
                        if((new Random()).nextInt(10) == 0){
                            grid[y][x] = new Ash();
                        }
                        else{
                            grid[y][x] = new Fire();
                        }
                    }
                    // fire liquid
                    else{
                        if((new Random()).nextInt(5) == 0){
                            grid[y][x] = new Fire();
                        }
                        else{
                            grid[y][x] = new Empty();
                        }
                    }

                }

                e.lifeTimeFrames--;
            }

            // glass
            if(e.checkElement(e,new Class[]{Glass.class})){
                e.giveHeat(grid,x,y,(int)(e.currHeat/8));
                e.currHeat -= e.currHeat/120;
                if(e.currHeat > 100){
                    e.currHeat = 100;
                }
            }

            // lava & metal liquid
            if(e.checkElement(e,new Class[]{Lava.class,MetalLiquid.class})){
                e.giveHeat(grid,x,y,e.heat);
            }
        }
    }

    public void reaction(int x,int y) {
        Element e = grid[y][x];

        // break if all surrounding elements are the same
        boolean same = true;
        for(int r=-1;r<2;r++) {
            for (int c = -1; c < 2; c++) {
                if (e.isInBounds(grid, x + c, y + r) &&
                        !e.checkElement(grid[y + r][x + c], new Class[]{e.getClass()})) {
                    same = false;
                }
            }
        }
        if(same){
            return;
        }

        // lava or metal liquid + water
        if(e.checkElement(e,new Class[]{Lava.class,MetalLiquid.class})){
            boolean changed = false;
            for(int r=-1;r<2;r++){
                for(int c=-1;c<2;c++){
                    if(e.isInBounds(grid,x + c,y+r) &&
                            e.checkElement(grid[y + r][x + c],new Class[]{Water.class})){
                        changed = true;
                        elementChange(x + c,y + r);
                    }
                }
            }
            if(changed){
                for(int r=-1;r<2;r++) {
                    for (int c = -1; c < 2; c++) {
                        if(e.isInBounds(grid,x + c,y + r) &&
                                e.checkElement(grid[y + r][x + c],new Class[]{Lava.class,MetalLiquid.class})){
                            elementChange(x + c, y + r);
                        }
                    }
                }
            }
        }
        // acid
        else if(e.checkElement(e,new Class[]{Acid.class})){
            boolean changed = false;
            for(int r=1;r>-2;r--){
                boolean innerBreak = false;
                Random random = new Random();

                // left to right
                if(random.nextInt(2) == 0){
                    for(int c=-1;c<2;c++){
                        if(e.isInBounds(grid,x + c,y+r)
                                && !e.checkElement(grid[y + r][x + c],new Class[]{Acid.class,Empty.class,Glass.class,Gas.class})
                                && ((Acid)e).willReact()){
                            changed = true;
                            grid[y + r][x + c] = new Smoke();
                            innerBreak = true;
                            break;
                        }
                    }
                }
                // right to left
                else{
                    for(int c=1;c>-2;c--){
                        if(e.isInBounds(grid,x + c,y+r)
                                && !e.checkElement(grid[y + r][x + c],new Class[]{Acid.class,Empty.class,Glass.class,Gas.class})
                                && ((Acid)e).willReact()){
                            changed = true;
                            grid[y + r][x + c] = new Smoke();
                            innerBreak = true;
                            break;
                        }
                    }
                }
                if(innerBreak){
                    break;
                }
            }
            if(changed){
                elementChange(x,y);
            }
        }
        // poison gas + steam
        if(e.checkElement(e,new Class[]{PoisonGas.class})){
            boolean changed = false;
            for(int r=-1;r<2;r++){
                boolean innerBreak = false;
                for(int c=-1;c<2;c++){
                    if(e.isInBounds(grid,x + c,y+r)
                            && e.checkElement(grid[y + r][x + c],new Class[]{Steam.class})){
                        changed = true;
                        grid[y + r][x + c] = new Empty();
                        innerBreak = true;
                        break;
                    }
                }
                if(innerBreak){
                    break;
                }
            }
            if(changed){
                if(((PoisonGas) e).willReact()){
                    elementChange(x,y);
                }
                else{
                    grid[y][x] = new Empty();
                }
            }
        }
        // dirt + water
        if(e.checkElement(e,new Class[]{Dirt.class})) {
            boolean changed = false;
            for (int r = -2; r < 2; r++) {
                for (int c = -1; c < 2; c++) {
                    if (e.isInBounds(grid, x + c, y + r)
                            && (Math.abs(c) != Math.abs(r) || (r == 0 && c == 0) || (r == -2 && c == 0))
                            && e.checkElement(grid[y + r][x + c], new Class[]{Water.class})) {
                        changed = true;
                        elementChange(x, y);
                        grid[y + r][x + c] = new Empty();
                    }
                }
            }
        }
    }
}
