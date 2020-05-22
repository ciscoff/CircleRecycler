package s.yzrlykov.circlerecycler.domain;

import android.graphics.Paint;

public class Point extends android.graphics.Point{

    private final Paint paint;

    public Point(int x, int y, Paint paint){
        super(x, y);
        this.paint = paint;
    }

    public Paint getPaint(){
        return paint;
    }
}
