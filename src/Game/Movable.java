package Game;

import Engine.Vector2;
import Game.Elements.Empty;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Movable extends Element {

    private boolean moved = false;
    private boolean velocityMoved = false;
    public Vector2 velocity;
    private boolean isMoving = true;
    private Point afterVelocityMove;

    public int framesNotMoving = 0;
    public int framesNotMovingX = 0;
    public int framesNotMovingY = 0;
    public Integer framesElementChange; // check for not null

    //TODO: fix friction & velocity. Initial velocity for sand and water
    public int friction;

    public void move(Element[][] grid,int x,int y){

        afterVelocityMove = new Point(x,y);

        Point iPoint = new Point(x,y);

        if(((Movable)grid[y][x]).isMoving() && !velocityMoved){

            // final and initial positions
            Point fPoint = new Point(Math.round(iPoint.x + this.velocity.getX()),Math.round(iPoint.y + this.velocity.getY()));
            ArrayList<Point> nextPoints = elementTraverseAlgorithm(fPoint,iPoint);

//            // parabolic trajectory (doesn't work)
//            ArrayList<Point> nextPoints = testAlgo(iPoint,velocity,new Vector2(0,gravity));

            // move element along trajectory
            for(Point p : nextPoints){
                // check if next point is diagonal
                int[] arr = new int[]{-1,1};
                boolean isDiagonal = false;
                for(int dY : arr){
                    for(int dX : arr){
                        if(iPoint.x + dX == p.x && iPoint.x + dX == p.y){
                            isDiagonal = true;
                        }
                    }
                }

                // set classes that elements can velocityMove through
                Class<?>[] classArr = new Class[]{Empty.class,Gas.class};

                if(checkElement(grid,p.x,p.y,classArr)){

                    // check diagonal and break if both are false
                    if(isDiagonal && !checkElement(grid,iPoint.x,p.y,classArr)
                            && !checkElement(grid,p.x,iPoint.y,classArr)){
                        break;
                    }

                    replace(grid,iPoint.x,iPoint.y, p.x,p.y);
                    iPoint = p;
                }
                else{
                    break;
                }
            }
            afterVelocityMove = new Point(iPoint);

            //set velocity to displacement
//            this.velocity.setX(iPoint.x - x);
//            this.velocity.setY(iPoint.y - y);
            this.velocity.increase(0,gravity / 60);

            this.velocityMoved = true;

//            // set velocity to zero if element touched the ground
//            if(isInBounds(grid, iPoint.x,iPoint.y + 1) && grid[iPoint.y + 1][iPoint.x].getClass() != Empty.class &&
//                    this.velocity.getMagnitude() > 1) {
//                this.velocity.setX(0);
//                this.velocity.setY(0);
//            }

        }

    }

    public void replace(Element[][] grid,int ix,int iy,int fx,int fy){
        Element temp = grid[fy][fx];
        grid[fy][fx] = grid[iy][ix];
        grid[iy][ix] = temp;

        ((Movable) grid[fy][fx]).setMoved(true);
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean hasVelocityMoved() {
        return velocityMoved;
    }

    public void setVelocityMoved(boolean velocityMoved) {
        this.velocityMoved = velocityMoved;
    }

    public boolean down(Element[][] grid, int x, int y){
        return y + 1 < grid.length;
    }

    public boolean up(Element[][] grid,int x,int y){
        return y - 1 >= 0;
    }

    public boolean left(Element[][] grid,int x,int y){
         return x - 1 >= 0;
    }
    public boolean right(Element[][] grid,int x,int y){
         return x + 1 < grid[0].length;
    }

    public void resetVelocity() {
        this.velocity = new Vector2(velocity.getX(),velocity.getY());
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void varyVelocity(){
        int scatter = 1;
        this.velocity.setY((float)(this.velocity.getY() + Math.random() * scatter));
    }

    public Point getAfterVelocityMove() {
        return afterVelocityMove;
    }

    public void setAfterVelocityMove(Point afterVelocityMove) {
        this.afterVelocityMove = afterVelocityMove;
    }

    public boolean canMoveLeftDown(Element[][] grid, int x, int y,Class<?>[] allowedClasses){
        return left(grid,x,y) && down(grid,x,y) && (checkElement(grid[y][x - 1],allowedClasses) ||
                checkElement(grid[y + 1][x],allowedClasses));
    }

    public boolean canMoveRightDown(Element[][] grid, int x, int y,Class<?>[] allowedClasses){
        return right(grid,x,y) && down(grid,x,y) && (checkElement(grid[y][x + 1],allowedClasses) ||
                checkElement(grid[y + 1][x],allowedClasses));
    }

    public boolean canMoveLeftUp(Element[][] grid, int x, int y,Class<?>[] allowedClasses){
        return left(grid,x,y) && up(grid,x,y) && (checkElement(grid[y][x - 1],allowedClasses) ||
                checkElement(grid[y - 1][x],allowedClasses));
    }

    public boolean canMoveRightUp(Element[][] grid, int x, int y,Class<?>[] allowedClasses){
        return right(grid,x,y) && up(grid,x,y) && (checkElement(grid[y][x + 1],allowedClasses) ||
                checkElement(grid[y - 1][x],allowedClasses));
    }
}
