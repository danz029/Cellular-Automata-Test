package Engine;

public class Vector2 {

    private float x;
    private float y;

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v){
        this.x = v.x;
        this.y = v.y;
    }

    public void increase(float x, float y){
        this.x += x;
        this.y += y;
    }

    public double getMagnitude(){
        return Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
