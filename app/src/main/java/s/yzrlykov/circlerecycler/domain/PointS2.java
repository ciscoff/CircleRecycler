package s.yzrlykov.circlerecycler.domain;

/**
 * Created by danylo.volokh on 11/15/2015.
 */
public class PointS2 {

    private int x;
    private int y;

    public PointS2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     *  A hashCode like this gives us performance of O(N/4) when using {@link java.util.HashMap}
     *  N - count of pointS1s in the circle.
     *
     *  Explanation: we use "Mid point algorithm" for creating circle pointS1s.
     *  We create circle pointS1s for (x; y) in 1 octant, N/8 pointS1s. 2nd octant is created using (y; x), N/8 pointS1s.
     *  One quadrant is N/4 pointS1s.
     *
     *  We have 8 pair of pointS1s combined from every octant
     *  (x; y)      (y; x)
     *  (x; -y)     (-y; x)
     *  (-x; y)     (y; -x)
     *  (-x; -y)    (-y; -x)
     *
     *  Sum of four pairs are equal. Example:
     *  x = 8; y =22;
     *
     *  (30)   =   (30)
     *  (-14)  =   (-14)
     *  (14)   =   (14)
     *  (-30)  =   (-30)
     *
     */
    @Override
    public int hashCode() {
        return x + y;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof PointS2)){
            return false;
        }
        PointS2 other = (PointS2) o;
        return other.x == x && other.y == y;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}