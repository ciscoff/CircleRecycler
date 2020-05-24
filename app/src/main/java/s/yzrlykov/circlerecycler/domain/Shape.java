package s.yzrlykov.circlerecycler.domain;

import android.graphics.Paint;
import android.graphics.RectF;

public class Shape extends RectF {

    private final Paint paint;

    public Shape(int x, int y, Paint paint, int dimen) {
        super(x - dimen / 2, y - dimen / 2, x - dimen / 2 + dimen, y - dimen / 2 + dimen);
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }
}
