package s.yzrlykov.circlerecycler.stages.s03;


import s.yzrlykov.circlerecycler.domain.PointS2;

public class UpdatablePoint extends PointS2 {

    public UpdatablePoint(int x, int y) {
        super(x, y);
    }

    public void update(int x, int y) {
        setX(x);
        setY(y);
    }
}