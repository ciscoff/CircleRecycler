package s.yzrlykov.circlerecycler.domain.pointcreator;

import android.graphics.Paint;

import java.util.List;
import java.util.Map;

import s.yzrlykov.circlerecycler.domain.PointS1;
import s.yzrlykov.circlerecycler.domain.PointS2;

/**
 * Created by danylo.volokh on 12/4/2015.
 * <p>
 * Implementation should be quadrant-specific and it should "know" in which order point should be created.
 * For example : if we starting to layout views from top to bottom in first quadrant then first point should be (R;0)
 * "R" - radius
 */
public interface CirclePointsCreator {

    /**
     * Метод отсюда:
     * https://bit.ly/3gcZhI2
     */
    void fillCirclePoints(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex);

    void fillCirclePoints(List<PointS1> circlePointS1s);

    void fillCirclePointsFirstQuadrant(List<PointS1> circlePointS1s);

    void fillCirclePointsFirstQuadrant(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex);

    /**
     * Я добавил этот метод, чтобы ограничиться только точками первой четверти
     */
    void fillCirclePointsFirstQuadrant(List<PointS1> circlePointS1s, Paint paint);
}