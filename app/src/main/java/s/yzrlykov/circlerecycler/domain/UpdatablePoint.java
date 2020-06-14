package s.yzrlykov.circlerecycler.domain;

public class UpdatablePoint extends PointS2 {

    public UpdatablePoint(int x, int y) {
        super(x, y);
    }

    public void update(int x, int y) {
        setX(x);
        setY(y);
    }
}