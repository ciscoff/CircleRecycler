package s.yzrlykov.circlerecycler.domain.pointcreator;

import android.graphics.Paint;

import java.util.List;

import s.yzrlykov.circlerecycler.domain.Point;

/**
 * Created by danylo.volokh on 12/4/2015.
 * <p>
 * Implementation should be quadrant-specific and it should "know" in which order point should be created.
 * For example : if we starting to layout views from top to bottom in first quadrant then first point should be (R;0)
 * "R" - radius
 */
public interface CirclePointsCreator {

    void fillCirclePoints(List<Point> circlePoints);

    void fillCirclePointsFirstQuadrant(List<Point> circlePoints);

    /**
     * Я добавил этот метод, чтобы ограничиться только точками первой четверти
     */
    void fillCirclePointsFirstQuadrant(List<Point> circlePoints, Paint paint);
}