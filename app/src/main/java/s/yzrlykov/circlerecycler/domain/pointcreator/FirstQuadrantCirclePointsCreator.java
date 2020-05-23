package s.yzrlykov.circlerecycler.domain.pointcreator;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;

import s.yzrlykov.circlerecycler.domain.Point;
import s.yzrlykov.circlerecycler.domain.pointmirror.CircleMirrorHelper;
import s.yzrlykov.circlerecycler.domain.pointmirror.FirstQuadrantCircleMirrorHelper;

/**
 * Created by danylo.volokh on 11/22/2015.
 * This class is a "Template method". It "knows" in which order point should be created.
 * <p>
 * This class also uses CircleMirrorHelper to mirror created point.
 * Exactly as required by "Mid point circle algorithm" but a bit modified.
 * <p>
 * "Midpoint circle algorithm" creates all 8 octant in parallel.
 * We are using "Midpoint circle algorithm" to create 1st octant and the mirror other points consecutively:
 * 1 octant, 2 octant, 2 quadrant, 2 semicircle
 */
public class FirstQuadrantCirclePointsCreator implements CirclePointsCreator {

    private static final String TAG = FirstQuadrantCirclePointsCreator.class.getSimpleName();

    private final int mRadius;
    private final int mX0;
    private final int mY0;

    private final CircleMirrorHelper mCircleMirrorHelper;

    private Paint mPaintFor1stOctant = new Paint();

    {
        mPaintFor1stOctant.setColor(Color.MAGENTA);
        mPaintFor1stOctant.setStrokeWidth(2);
    }

    public FirstQuadrantCirclePointsCreator(int radius, int x0, int y0) {
        mRadius = radius;
        mX0 = x0;
        mY0 = y0;
        mCircleMirrorHelper = new FirstQuadrantCircleMirrorHelper(x0, y0);
    }

    /**
     * Я добавил чтобы назначить цвет на все сегменты
     */
    public FirstQuadrantCirclePointsCreator(int radius, int x0, int y0, int color) {
        mRadius = radius;
        mX0 = x0;
        mY0 = y0;
        mCircleMirrorHelper = new FirstQuadrantCircleMirrorHelper(x0, y0, color);
    }

    /**
     * This method is based on "Midpoint circle algorithm."
     * <p>
     * We use three steps:
     * <p>
     * 1. Create 1 octant of a circle.
     * 2. Mirror the created points for the 2nd octant
     * At this stage we have points for 1 quadrant of a circle
     * <p>
     * 3. Mirror 2nd quadrant points using points from 1 quadrant
     * At this stage we have points for 1 semicircle
     * <p>
     * 4. Mirror 2nd semicircle points using points from 1 semicircle
     */

    @Override
    public void fillCirclePoints(List<Point> circlePoints) {

        Log.v(TAG, ">> fillCirclePoints");

        createFirstOctant(circlePoints, mPaintFor1stOctant);

        /** at this stage "circleIndexPoint" and "circlePointIndex" contains only the points from first octant*/
        mCircleMirrorHelper.mirror_2nd_Octant(
                circlePoints);

        /** at this stage "circleIndexPoint" and "circlePointIndex" contains only the points from first quadrant*/
        mCircleMirrorHelper.mirror_2nd_Quadrant(
                circlePoints
        );

        /** at this stage "circleIndexPoint" and "circlePointIndex" contains only the points from first semicircle*/
        mCircleMirrorHelper.mirror_2nd_Semicircle(
                circlePoints
        );

        Log.v(TAG, "<< fillCirclePoints");
    }

    @Override
    public void fillCirclePointsFirstQuadrant(List<Point> circlePoints) {
        Log.v(TAG, ">> fillCirclePointsFirstQuadrant");

        createFirstOctant(circlePoints, mPaintFor1stOctant);

        /** at this stage "circleIndexPoint" and "circlePointIndex" contains only the points from first octant*/
        mCircleMirrorHelper.mirror_2nd_Octant(circlePoints);
    }

    /**
     * Это я добавил, чтобы рисовать со своей Paint
     */
    @Override
    public void fillCirclePointsFirstQuadrant(List<Point> circlePoints, Paint paint) {
        createFirstOctant(circlePoints, paint);

        /** at this stage "circleIndexPoint" and "circlePointIndex" contains only the points from first octant*/
        mCircleMirrorHelper.mirror_2nd_Octant(circlePoints);
    }

    /**
     * This method is based on "Midpoint circle algorithm."
     * It creates a points that are situated in first octant.
     * <p>
     * First point has an index of "0", next is "1" and so on.
     * First point is (radius;0)
     * ^         here is the last point of first octant. x == y
     * +y |        |      /
     * |        |    /
     * ______|        V  /
     * _--      |        *
     * _/          |       / \_  1st Octant    <-_
     * _|            |     /     |_                   \  Points are created in this direction
     * |              |   /         |                    |
     * |              | /           |                    |
     * ---------------|---------- * --->      V
     * |              |           ^
     * |_             |           |
     * |_           |           |
     * \         |            here is the first point of first octant (radius;0)
     * --______|
     * |
     * <p>
     * <p>
     * In our reverse coordinates system it's a bit different
     * <p>
     * -y |
     * |
     * ______|
     * _--      |
     * _/          |
     * |            |
     * |            |
     * |            |
     * ---------------|-------------------------------------->      V
     * |            | \         |            *
     * |            |   \       | 1st Octant *
     * |_           |     \    _|            *
     * \_         |       \_/              *
     * --_______|         \              *
     * |           \            *
     * |                        *
     * V                        *
     * +y *                        *
     * *     Device's display   *
     * *                        *
     * *                        *
     * *                        *
     * *************************
     */
    private void createFirstOctant(
            List<Point> circlePoints,
            Paint paint
    ) {

        int x = mRadius;
        int y = 0;
        int decisionOver2 = 1 - x;   // Decision criterion divided by 2 evaluated at x=r, y=0
        while (y <= x) {

            createPoint(x + mX0, y + mY0, circlePoints, paint);

            y++;
            if (decisionOver2 <= 0) {
                decisionOver2 += 2 * y + 1;   // Change in decision criterion for y -> y+1
            } else {
                x--;
                decisionOver2 += 2 * (y - x) + 1;   // Change for y -> y+1, x -> x-1
            }
        }
    }

    private void createPoint(
            int x,
            int y,
            List<Point> circlePoints,
            Paint paint) {

        Point point = new Point(x, y, paint);

        circlePoints.add(point);
    }
}
