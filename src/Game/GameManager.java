package Game;

import Engine.*;
import Engine.gfx.Image;
import Game.Elements.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameManager extends AbstractGame {

    private Image image;
    World world;

    private int oldMouseX = 0;
    private int oldMouseY = 0;

    private Element[] elementList = new Element[]{new Empty(),new Water(),new Sand(),new Rock(),new Smoke(),
            new Steam(),new Wood(),new Fire(),new FireSolid(),new Ash(),new Glass(),new FireLiquid(),new Oil(),
            new Lava(),new Metal(),new MetalLiquid(),new Acid(),new PoisonGas(),new Dirt(),new Mud()};
    private int elementIndex = 0;
    private int radius = 3;

    private boolean selectElement = true;

    private boolean willUpdateWorld = true;

    DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public GameManager(){
        world = new World(240,320);
        world.init();
        image = new Image(world);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(willUpdateWorld){
            world.update();
        }
        mouseFunction(gc);
        keyFunction(gc);
        displayFps(gc);
        displayUpdateStatus(gc);
        image = new Image(world);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(image,0,0);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }

    public void displayFps(GameContainer gc){
        gc.getWindow().addToTextList(new Text(world.getWorldWidth()/100,world.getWorldHeight()/100 + 3,
                "FPS: " + Integer.toString(gc.getFps())));
    }

    public void displayUpdateStatus(GameContainer gc){
        gc.getWindow().addToTextList(new Text(world.getWorldWidth()/100 + 20,world.getWorldHeight()/100 + 3,
                this.willUpdateWorld ? "" : "Paused"));
    }

    public void clear(){
        for(int y = 0;y < world.getWorldHeight();y++) {
            for (int x = 0; x < world.getWorldWidth(); x++) {
                world.setElement(x,y,new Empty());
            }
        }
    }

    public void keyFunction(GameContainer gc){
        boolean spaceDown = gc.getInput().isKeyDown(KeyEvent.VK_SPACE);
        boolean eDown = gc.getInput().isKeyDown(KeyEvent.VK_E);

        // toggle willUpdateWorld with space
        if(spaceDown){
            willUpdateWorld = !willUpdateWorld;
        }

        // update world once with e
        if(!willUpdateWorld && eDown){
            world.update();
        }
    }

    public void mouseFunction(GameContainer gc) {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        int mouseScroll = gc.getInput().getScroll();
        boolean mouseLeft = gc.getInput().isButton(MouseEvent.BUTTON1);
        boolean mouseRightDown = gc.getInput().isButtonDown(MouseEvent.BUTTON3);
        boolean mouse4Down = gc.getInput().isButtonDown(4);
        boolean mouse5Down = gc.getInput().isButtonDown(5);

        // check bounds
        if(mouseX < 0 || mouseX >= world.getWorldWidth() || mouseY < 0 || mouseY >= world.getWorldHeight()){
            mouseX = oldMouseX;
            mouseY = oldMouseY;
        }

        // toggle selectedElement
        if(mouseRightDown){
            selectElement = !selectElement;
        }

        if(selectElement){
            // select element
            elementIndex += mouseScroll;
            if(elementIndex < 0){
                elementIndex = elementList.length - 1;
            }
            else if(elementIndex >= elementList.length){
                elementIndex = 0;
            }
        }
        else{
            // select radius
            radius -= mouseScroll;
            if(radius < 0){
                radius = 0;
            }
        }

        // show element to place
        Element selectedElement = elementList[elementIndex];
        gc.getWindow().addToTextList(new Text(world.getWorldWidth()/100,world.getWorldHeight()/100 + 13,
                "Element: " + selectedElement.getName()));

        // show radius
        gc.getWindow().addToTextList(new Text(world.getWorldWidth()/100,world.getWorldHeight()/100 + 18,
                "Radius: " + radius));

        // show selected mouse wheel menu
        String mouseSelection;
        if(selectElement){
            mouseSelection = "Element";
        }
        else{
            mouseSelection = "Radius";
        }
        gc.getWindow().addToTextList(new Text(world.getWorldWidth()/100,world.getWorldHeight()/100 + 23,
                "Selected: " + mouseSelection));

        // text to show next to mouse
        ArrayList<Text> mouseText = new ArrayList<>();
        int mouseTextX = 10;
        int mouseTextY = 5;

        // show element hovered next to mouse
        Element hoverElement = world.getGrid()[mouseY][mouseX];
        mouseText.add(new Text(mouseX + mouseTextX,mouseY + mouseTextY,hoverElement.getName()));
        mouseTextY += 5;

        if(Movable.class.isAssignableFrom(hoverElement.getClass())){
            // show velocity
            Vector2 vel = ((Movable) hoverElement).velocity;
            mouseText.add(new Text(mouseX + mouseTextX,mouseY + mouseTextY,
                    "Vel: (" + decimalFormat.format(vel.getX()) + ", " + decimalFormat.format(vel.getY()) + ")"));
            mouseTextY += 5;

            // show isMoving
            mouseText.add(new Text(mouseX + mouseTextX,mouseY + mouseTextY,
                    "isMoving: " + String.valueOf(((((Movable) hoverElement).isMoving())))));
            mouseTextY += 5;
        }
        // show heat
        if(hoverElement.isFlammable){
            mouseText.add(new Text(mouseX + mouseTextX,mouseY + mouseTextY,
                    "currHeat: " + String.valueOf((int)hoverElement.currHeat)));
            mouseTextY += 5;

            mouseText.add(new Text(mouseX + mouseTextX,mouseY + mouseTextY,
                    "heatTolerance: " + String.valueOf((int)hoverElement.heatTolerance)));
            mouseTextY += 5;
        }

        // show mouse text
        for(Text text : mouseText){
            gc.getWindow().addToTextList(text);
        }

        // place selectedElement in radius when left click
        if(mouseLeft){
            Point fPoint = new Point(mouseX,mouseY);
            Point iPoint = new Point(oldMouseX,oldMouseY);

            ArrayList<Point> points = traverseMatrixAlgorithm(fPoint,iPoint);
            for(Point p : points){
                placeInRadius(selectedElement,p.x,p.y,radius);
            }
        }

        // clear world
        if(mouse4Down){
            clear();
        }

        // initialize world
        if(mouse5Down){
            world.init();
        }

        oldMouseX = mouseX;
        oldMouseY = mouseY;
    }

    private void placeInRadius(Element selectedElement,int mouseX,int mouseY,int radius){
        for(int y = 0;y < world.getWorldHeight();y++){
            for(int x = 0;x < world.getWorldWidth();x++){
                if(Math.pow(mouseX - x,2) + Math.pow(mouseY - y,2) <= Math.pow(radius,2) // within radius
                        && (world.getGrid()[y][x].getClass() == Empty.class // destination is empty
                        || selectedElement.getClass() == Empty.class // selected is empty
                        || placeHelper(x,y,selectedElement,Liquid.class,Solid.class) // place solid in liquid
                        || placeHelper(x,y,selectedElement,Gas.class,Gas.class) // place gas in gas
                        || placeHelper(x,y,selectedElement,Gas.class,Solid.class) // place solid in gas
                        || placeHelper(x,y,selectedElement,Gas.class,Liquid.class)) // place liquid in gas
                        && !placeHelper(x,y,selectedElement,Steam.class,Fire.class) // can't place fire on steam
                ){

                    // clones selectedElement and places clones on grid
                    Object newElement = null;
                    try {
                        newElement = selectedElement.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    ((Element) newElement).randomColor();
                    if(Movable.class.isAssignableFrom(newElement.getClass())){
                        ((Movable) newElement).resetVelocity();
                        ((Movable) newElement).varyVelocity();
                    }
                    if(((Element) newElement).isFlammable){
                        ((Element) newElement).varyHeatTolerance();
                    }
                    if(((Element) newElement).checkElement((Element)newElement,
                            new Class[]{FireLiquid.class,FireSolid.class})){
                        ((Element) newElement).varyLifetime();
                    }
                    world.setElement(x,y,(Element)newElement);
                }
            }
        }
    }

    public ArrayList<Point> traverseMatrixAlgorithm(Point fPoint, Point iPoint){
        ArrayList<Point> points = new ArrayList<>();

        // return point if final and initial points are the same
        if(fPoint.equals(iPoint)){
            points.add(fPoint);
            return points;
        }

        int xDiff = iPoint.x - fPoint.x;
        int yDiff = iPoint.y - fPoint.y;
        boolean xDiffIsLarger = Math.abs(xDiff) > Math.abs(yDiff);

        int xModifier = -1;
        int yModifier = -1;
        if(xDiff < 0){
            xModifier = 1;
        }
        if(yDiff < 0){
            yModifier = 1;
        }

        int longerSideLength = Math.max(Math.abs(xDiff),Math.abs(yDiff));
        int shorterSideLength = Math.min(Math.abs(xDiff),Math.abs(yDiff));

        float slope = 0;
        if(shorterSideLength != 0 && longerSideLength != 0){
            slope = (float)(shorterSideLength + 1) / (longerSideLength + 1);
        }

        int shorterSideIncrease;
        for(int i = 1;i <= longerSideLength;i++){
            shorterSideIncrease = Math.round(i * slope);
            int yIncrease,xIncrease;
            if(xDiffIsLarger){
                xIncrease = i;
                yIncrease = shorterSideIncrease;
            }
            else{
                yIncrease = i;
                xIncrease = shorterSideIncrease;
            }

            int currY = iPoint.y + (yIncrease * yModifier);
            int currX = iPoint.x + (xIncrease * xModifier);
            points.add(new Point(currX,currY));
        }
        return points;
    }

    private boolean placeHelper(int x,int y,Element selectedElement,Class former,Class latter){
        return selectedElement.checkElement(world.getGrid(),x,y,new Class[]{former}) &&
                selectedElement.checkElement(selectedElement,new Class[]{latter});
    }
}
