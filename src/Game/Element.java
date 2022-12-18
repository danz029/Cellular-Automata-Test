package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Element implements Cloneable{

    public int[] color;
    public int[][] colorList;
    public String name;
    public final float gravity = 9.8f;
    public boolean canChange = false;
    public Class<?> changeElement;
    public boolean isFlammable = false;
    public int heatTolerance;
    public float currHeat = 0;
    public boolean givesHeat = false;
    public int heat;
    public int lifeTimeFrames;

    public Element(){

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getColor() {
        int rgb = (color[0] << 16) + (color[1] << 8) + color[2];
        return rgb;
    }

    public void randomColor(){
        Random random = new Random();
        this.color = colorList[random.nextInt(0,colorList.length)];
    }

    public void varyHeatTolerance(){
        Random random = new Random();
        if(heatTolerance > 10){
            heatTolerance = (random.nextInt((int)Math.ceil(heatTolerance * 0.4)) + (int)Math.ceil(heatTolerance * 0.8));
        }
        else{
            heatTolerance = random.nextInt(2) * (random.nextInt(2) == 0 ? 1 : -1) + heatTolerance;
        }
        if(random.nextInt(20) == 0 && heatTolerance < 100){
            heatTolerance *= 5;
        }
    }

    public void varyLifetime(){
        Random random = new Random();
        lifeTimeFrames = (random.nextInt((int)(lifeTimeFrames * 0.4)) + (int)(lifeTimeFrames * 0.8));
        if(random.nextInt(20) == 0){
            lifeTimeFrames *= 2;
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Point> elementTraverseAlgorithm(Point fPoint, Point iPoint){
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

    // currently does not consider horizontal acceleration
//    public ArrayList<Point> testAlgo(Point iPoint, Vector2 vel,Vector2 accel) {
//        ArrayList<Point> points = new ArrayList<>();
//
//        int deltaX = (int) vel.getX();
//
//        LinkedHashSet<Point> pSet = new LinkedHashSet<>();
//
//        for(int i = (deltaX / Math.abs(deltaX));-Math.abs(deltaX) <= i && i <= Math.abs(deltaX);
//            i += (deltaX / Math.abs(deltaX)) / 10){
//            int y = Math.round((float)(vel.getY() * i + 0.5 * accel.getY() * Math.pow(i,2)));
//            pSet.add(new Point(iPoint.x + i,iPoint.y + y));
//        }
//        points.addAll(pSet);
//        return points;
//    }

    public boolean isInBounds(Element[][] grid,int x,int y){
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    public boolean checkElement(Element[][] grid,int x,int y,Class<?>[] classes){
        boolean result = false;
        for(Class<?> c : classes){
            if(isInBounds(grid,x,y) && c.isAssignableFrom(grid[y][x].getClass())){
                result = true;
            }
        }
        return result;
    }

    public boolean checkElement(Element e,Class<?>[] classes){
        for(Class<?> c : classes){
            if(c.isAssignableFrom(e.getClass())){
                return true;
            }
        }
        return false;
    }

    public void setRGBColor(int[] color){
        this.color = color;
    }

    public int[] getRGBColor(){
        return color;
    }

    public void giveHeat(Element[][] grid,int x,int y,int heat){
        for(int dY = -1;dY < 2;dY++){
            for(int dX = -1;dX < 2;dX++){
                // for 8 surrounding elements that are inBounds and flammable
                if(!(dX == 0 && dY == 0) && isInBounds(grid,x + dX,y + dY) && grid[y + dY][x + dX].isFlammable){
                    grid[y + dY][x + dX].currHeat += heat / 60f;
                }
            }
        }
    }
}
