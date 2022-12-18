package Game;

import Game.Elements.Empty;
import Game.Elements.Fire;

import java.util.Random;

public abstract class Gas extends Movable{

    private final Class<?>[] classArr = new Class[]{Empty.class,Liquid.class, Gas.class};

    public int gasMoveProbDenominator;
    public int dispersionRate;
    public boolean changedPosition = false;

    public int density;

    public void move(Element[][] grid,int x,int y){

        // store element to be moved as well as its position
        Element iElement = grid[y][x];
        int iX = x;
        int iY = y;

        int finalX = x;
        int finalY = y;

        Random random = new Random();
        if(random.nextInt(gasMoveProbDenominator) == 0){
//
            if(!((Movable) grid[y][x]).isMoved()){
                int currX = x;
                int currY = y;

                // move up
                if(up(grid,currX,currY) && checkElement(grid,currX,currY - 1,classArr)
                        && isLessDense(grid,currX,currY,currX,currY - 1)){
                    replace(grid,currX,currY,currX,currY - 1);
                    currY--;
                }
                else{
                    Random randomNum = new Random();
                    if(randomNum.nextInt(2) == 0){
                        for(int i = 0;i < randomNum.nextInt(dispersionRate);i++){
                            //move left up
                            if(isInBounds(grid,currX,currY) && canMoveLeftUp(grid,currX,currY,classArr) &&
                                    checkElement(grid,currX - 1,currY - 1,classArr)
                                    && isLessDense(grid,currX,currY,currX - 1,currY - 1)){
                                replace(grid,currX,currY,currX - 1,currY - 1);
                                currX--;
                                currY--;
                            }
                            //move left
                            else if(isInBounds(grid,currX,currY) && left(grid,currX,currY) &&
                                    checkElement(grid,currX - 1,currY,classArr)){
                                replace(grid,currX,currY,currX - 1,currY);
                                currX--;
                            }
                        }
                    }
                    else{
                        for(int i = 0;i < randomNum.nextInt(dispersionRate);i++){
                            //move right up
                            if(isInBounds(grid,currX,currY) && canMoveRightUp(grid,currX,currY,classArr) &&
                                    checkElement(grid,currX + 1,currY - 1,classArr)
                                    && isLessDense(grid,currX,currY,currX + 1,currY - 1)){
                                replace(grid,currX,currY,currX + 1,currY - 1);
                                currX++;
                                currY--;
                            }
                            //move right
                            else if(isInBounds(grid,currX,currY) && right(grid,currX,currY) &&
                                    checkElement(grid,currX + 1,currY,classArr)){
                                replace(grid,currX,currY,currX + 1,currY);
                                currX++;
                            }
                        }
                    }
                }
                finalX = currX;
                finalY = currY;
            }
        }

        // not moving
        if(finalY == iY){
            ((Movable)grid[finalY][finalX]).setMoving(false);
            framesNotMoving++;
            changedPosition = false;
        }
        //moving
        else{
            ((Movable)grid[finalY][finalX]).setMoving(true);
            framesNotMoving = 0;
            changedPosition = true;
        }
    }

    private boolean isLessDense(Element[][] grid,int iX,int iY,int fX,int fY){
        Element iElement = grid[iY][iX];
        Element fElement = grid[fY][fX];

        // if destination is solid, liquid
        if(checkElement(fElement,new Class[]{Solid.class,Liquid.class})){
            return false;
        }
        // else if destination is empty
        else if(checkElement(fElement,new Class[]{Empty.class})){
            return true;
        }
        else{
            return ((Gas)iElement).density < ((Gas)fElement).density;
        }
    }
}
