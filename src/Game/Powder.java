package Game;

import Game.Elements.Empty;

import java.util.Random;

public abstract class Powder extends Movable{

    private final Class<?>[] classArr = new Class[]{Empty.class,Liquid.class,Gas.class};

    public void move(Element[][] grid, int x, int y){

        // store element to be moved as well as its position
        Element iElement = grid[y][x];
        int iX = x;
        int iY = y;

        super.move(grid,x,y);

        x = getAfterVelocityMove().x;
        y = getAfterVelocityMove().y;

        boolean down = down(grid,x,y + 1);;
        boolean left = left(grid,x - 1,y);;
        boolean right = right(grid,x + 1,y);;

        int finalX = x;
        int finalY = y;

        // move if space is empty or occupied by liquid
        if(!((Movable) grid[y][x]).isMoved()){
            // move down
            if(down && checkElement(grid,x,y + 1,classArr)){
                replace(grid,x,y,x,y + 1);
                finalY++;
            }
            else{
                Random randomNum = new Random();
                if(randomNum.nextInt(2) == 0){
                    //move left down
                    if(isInBounds(grid,x - 1,y + 1) && canMoveLeftDown(grid,x,y,classArr)
                            && checkElement(grid,x - 1,y + 1,classArr)){
                        replace(grid,x,y,x - 1,y + 1);
                        finalX--;
                        finalY++;
                    }
                }
                else{
                    //move right down
                    if(isInBounds(grid,x + 1,y + 1) && canMoveRightDown(grid,x,y,classArr)
                            && checkElement(grid,x + 1,y + 1,classArr)){
                        replace(grid,x,y,x + 1,y + 1);
                        finalX++;
                        finalY++;
                    }
                }
            }
        }

        // not moving
        if(grid[iY][iX] == iElement){
            ((Movable)grid[iY][iX]).setMoving(false);
            framesNotMoving++;
        }
        //moving
        else{
            ((Movable)grid[finalY][finalX]).setMoving(true);
            framesNotMoving = 0;
        }

        // check frames not moving in x and y directions
        if(iX == finalX){
            framesNotMovingX++;
        }
        else{
            framesNotMovingX = 0;
        }
        if(iY == finalY){
            framesNotMovingY++;
        }
        else{
            framesNotMovingY = 0;
        }

        // set vel in x or y direction to zero if not moving for 3 frames in direction
        // values tweaked
        if(framesNotMovingX > Math.abs(this.velocity.getX())){
            this.velocity.setX(this.velocity.getX()/3);
        }
        if(framesNotMovingY > 2){
            this.velocity.setY(this.velocity.getY()/2);
        }

        // set vel to zero if not moving for 2 frames (OLD)
//        if(framesNotMoving > 1){
//            if(finalX == iX){
//                this.velocity.setX(0);
//            }
//            if(finalY == iY){
//                this.velocity.setY(0);
//            }
//        }
    }

    public Class<?>[] getClassArr() {
        return classArr;
    }
}
