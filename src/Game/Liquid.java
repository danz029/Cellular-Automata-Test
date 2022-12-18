package Game;

import Game.Elements.Empty;

import java.util.Random;

public abstract class Liquid extends Movable {

    // can move if gas
    // TODO: Fix liquid lag when placing (only fire liquid and oil)

    public int dispersionRate;
    private final Class<?>[] classArr = new Class[]{Empty.class,Gas.class,Liquid.class};

    public int density;

    public void move(Element[][] grid,int x,int y){

        // store element to be moved as well as its position
        Element iElement = grid[y][x];
        int iX = x;
        int iY = y;

        super.move(grid,x,y);

        x = getAfterVelocityMove().x;
        y = getAfterVelocityMove().y;

        int finalX = x;
        int finalY = y;

        if(!((Movable) grid[y][x]).isMoved()){
            int currX = x;
            int currY = y;

            // move down
            if(down(grid,currX,currY) && checkElement(grid,currX,currY + 1,classArr )
                    && !checkElement(grid,currX,currY + 1,new Class[]{grid[currY][currX].getClass()})
                    && isMoreDense(grid,currX,currY,currX,currY + 1)){
                replace(grid,currX,currY,currX,currY + 1);
                currY++;
            }
            else{

                Random randomNum = new Random();
                if(randomNum.nextInt(2) == 0){
                    //move left down
                    if(isInBounds(grid,currX,currY) && (canMoveLeftDown(grid,currX,currY,classArr)
                            && checkElement(grid,currX - 1,currY + 1,classArr))
                            && !checkElement(grid,currX - 1,currY + 1,new Class[]{grid[currY][currX].getClass()})
                            && isMoreDense(grid,currX,currY,currX - 1,currY + 1)){
                        replace(grid,currX,currY,currX - 1,currY + 1);
                        currX--;
                        currY++;
                    }
                    else{
                        for(int i = 0;i < dispersionRate;i++){
                            //move left
                            if(isInBounds(grid,currX,currY) && left(grid,currX,currY)
                                    && checkElement(grid,currX - 1,currY,classArr)
                                    && !checkElement(grid,currX - 1,currY,new Class[]{grid[currY][currX].getClass()})
                                    // trying to prevent lag
//                                    && grid[currY][currX].getClass() != grid[currY][currX - 1].getClass()
                            ){
                                replace(grid,currX,currY,currX - 1,currY);
                                currX--;
                            }
                        }
                    }
                }
                else{
                    //move right down
                    if(isInBounds(grid,currX,currY) && canMoveRightDown(grid,currX,currY,classArr) &&
                            checkElement(grid,currX + 1,currY + 1,classArr)
                            && !checkElement(grid,currX + 1,currY + 1,new Class[]{grid[currY][currX].getClass()})
                            && isMoreDense(grid,currX,currY,currX + 1,currY + 1)){
                        replace(grid,currX,currY,currX + 1,currY + 1);
                        currX++;
                        currY++;
                    }
                    else{
                        for(int i = 0;i < dispersionRate;i++){
                            //move right
                            if(isInBounds(grid,currX,currY) && right(grid,currX,currY)
                                    && checkElement(grid,currX + 1,currY,classArr)
                                    && !checkElement(grid,currX + 1,currY,new Class[]{grid[currY][currX].getClass()})
                                    // trying to prevent lag
//                                    && grid[currY][currX].getClass() != grid[currY][currX + 1].getClass()
                            ){
                                replace(grid,currX,currY,currX + 1,currY);
                                currX++;
                            }
                        }
                    }
                }
            }
            finalX = currX;
            finalY = currY;
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
            this.velocity.setX(this.velocity.getX()/2);
        }
        if(framesNotMovingY > 3){
            this.velocity.setY(this.velocity.getY()/3);
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

    private boolean isMoreDense(Element[][] grid,int iX,int iY,int fX,int fY){
        Element iElement = grid[iY][iX];
        Element fElement = grid[fY][fX];

        // if destination is not liquid
        if(!checkElement(fElement,new Class[]{Liquid.class})){
            return true;
        }
        else{
            return ((Liquid)iElement).density > ((Liquid)fElement).density;
        }
    }

    public Class<?>[] getClassArr() {
        return classArr;
    }

}
