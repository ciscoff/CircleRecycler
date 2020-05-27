package s.yzrlykov.circlerecycler.domain;

import android.graphics.Paint;

public class PointS1 extends android.graphics.Point{

    private final Paint paint;

    public PointS1(int x, int y, Paint paint){
        super(x, y);
        this.paint = paint;
    }

    public Paint getPaint(){
        return paint;
    }
}
