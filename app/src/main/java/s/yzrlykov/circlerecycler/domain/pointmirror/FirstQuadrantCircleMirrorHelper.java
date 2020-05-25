package s.yzrlykov.circlerecycler.domain.pointmirror;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;
import java.util.Map;

import s.yzrlykov.circlerecycler.domain.PointS1;
import s.yzrlykov.circlerecycler.domain.PointS2;

/**
 * This class is a helper for FirstQuadrantCirclePointsCreator
 * <p>
 * It can create a full circle pointS1s using pointS1s from 1st octant. It is mirroring existing pointS1s to the pointS1s in other circle sectors.
 */
public class FirstQuadrantCircleMirrorHelper implements CircleMirrorHelper {

    private static final boolean SHOW_LOGS = false;
    private static final String TAG = FirstQuadrantCircleMirrorHelper.class.getSimpleName();

    private final int mX0;
    private final int mY0;

    private final Paint _2_octant_paint = new Paint();
    private final Paint _2_quadrant_paint = new Paint();
    private final Paint _2_semicircle_paint = new Paint();

    {
        _2_octant_paint.setColor(Color.RED);
        _2_octant_paint.setStrokeWidth(2);

        _2_quadrant_paint.setColor(Color.BLACK);
        _2_quadrant_paint.setStrokeWidth(2);

        _2_semicircle_paint.setColor(Color.BLUE);
        _2_semicircle_paint.setStrokeWidth(2);
    }


    public FirstQuadrantCircleMirrorHelper(int x0, int y0) {
        mX0 = x0;
        mY0 = y0;
    }

    /**
     * Я добавил для управления цветом.
     */
    public FirstQuadrantCircleMirrorHelper(int x0, int y0, int color) {
        mX0 = x0;
        mY0 = y0;

        _2_octant_paint.setColor(color);
        _2_quadrant_paint.setColor(color);
        _2_semicircle_paint.setColor(color);
    }

    enum Action {
        MIRROR_2ND_OCTANT,
        MIRROR_2ND_QUADRANT,
        MIRROR_2ND_SEMICIRCLE
    }

    /**
     * This method takes the pointS1s from 1st octant and mirror them to the 2nd octant
     * <p>
     * ^                  /
     * +y | 2nd octant    /
     * |             /
     * |_____      /
     * |     --_ /
     * |       / *_  <-- this s the point from where we start
     * |     /     |                  \
     * |   /       | 1st Octant        | We are going through pointS1s in this direction
     * | /         |                   V
     * ---------------|--------------->      V
     * |            |
     * |            |
     * |_           |
     * \_         |
     * --_______|
     * |
     * |
     */
    @Override
    public void mirror_2nd_Octant(
            List<PointS1> circlePointS1s
    ) {

        int countOfPointsIn_1st_octant = circlePointS1s.size();
        if (SHOW_LOGS)
            Log.v(TAG, "mirror_2nd_Octant, countOfPointsIn_1st_octant " + countOfPointsIn_1st_octant);

        for (int pointIndex = countOfPointsIn_1st_octant - 1;
             pointIndex >= 0;
             pointIndex--) {

            createMirroredPoint(Action.MIRROR_2ND_OCTANT, pointIndex, circlePointS1s);
        }
    }

    @Override
    public void mirror_2nd_Octant(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    ) {

        int countOfPointsIn_1st_octant = circlePointIndex.size();
        if(SHOW_LOGS) Log.v(TAG, "mirror_2nd_Octant, countOfPointsIn_1st_octant " + countOfPointsIn_1st_octant);

        for(int pointIndex = countOfPointsIn_1st_octant - 1;
            pointIndex >= 0;
            pointIndex-- ){

            createMirroredPoint(Action.MIRROR_2ND_OCTANT, pointIndex, circleIndexPoint, circlePointIndex);
        }
    }

    @Override
    public void mirror_2nd_Quadrant(
            List<PointS1> circlePointS1s
    ) {

        int countOfPointsIn_1st_quadrant = circlePointS1s.size();
        if (SHOW_LOGS)
            Log.v(TAG, "mirror_2nd_Quadrant, countOfPointsIn_1st_quadrant " + countOfPointsIn_1st_quadrant);

        for (int pointIndex = countOfPointsIn_1st_quadrant
                - 1 // last point
                - 1 // previous to the last because last point is already in the list (x0, radius + y0). It is a point on Y axis
             ;
             pointIndex >= 0;
             pointIndex--) {

            createMirroredPoint(Action.MIRROR_2ND_QUADRANT, pointIndex, circlePointS1s);
        }
    }

    @Override
    public void mirror_2nd_Quadrant(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    ) {

        int countOfPointsIn_1st_quadrant = circlePointIndex.size();
        if(SHOW_LOGS) Log.v(TAG, "mirror_2nd_Quadrant, countOfPointsIn_1st_quadrant " + countOfPointsIn_1st_quadrant);

        for(int pointIndex = countOfPointsIn_1st_quadrant
                - 1 // last point
                - 1 // previous to the last because last point is already in the list (x0, radius + y0). It is a point on Y axis
            ;
            pointIndex >= 0;
            pointIndex-- ){

            createMirroredPoint(Action.MIRROR_2ND_QUADRANT, pointIndex, circleIndexPoint, circlePointIndex);
        }
    }

    @Override
    public void mirror_2nd_Semicircle(
            List<PointS1> circlePointS1s
    ) {

        int countOfPointsIn_1st_semicircle = circlePointS1s.size();
        if (SHOW_LOGS)
            Log.v(TAG, "mirror_2nd_Semicircle, countOfPointsIn_1st_semicircle " + countOfPointsIn_1st_semicircle);

        for (int pointIndex = countOfPointsIn_1st_semicircle - 2; // don't count (-radius, 0) because it already in the list
             pointIndex > 0; // don't count (radius, 0) because it already in the list
             pointIndex--) {

            createMirroredPoint(Action.MIRROR_2ND_SEMICIRCLE, pointIndex, circlePointS1s);

        }
    }

    @Override
    public void mirror_2nd_Semicircle(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    ) {

        int countOfPointsIn_1st_semicircle = circlePointIndex.size();
        if(SHOW_LOGS) Log.v(TAG, "mirror_2nd_Semicircle, countOfPointsIn_1st_semicircle " + countOfPointsIn_1st_semicircle);

        for(int pointIndex = countOfPointsIn_1st_semicircle - 2; // don't count (-radius, 0) because it already in the list
            pointIndex > 0; // don't count (radius, 0) because it already in the list
            pointIndex-- ){

            createMirroredPoint(Action.MIRROR_2ND_SEMICIRCLE, pointIndex, circleIndexPoint, circlePointIndex);

        }

    }

    private void createMirroredPoint(
            Action action,
            int pointIndex,
            List<PointS1> circlePointS1s
    ) {

        PointS1 pointS1AtIndex = circlePointS1s.get(pointIndex);

        PointS1 mirroredPointS1;
        switch (action) {
            case MIRROR_2ND_OCTANT:
                mirroredPointS1 = mirror_2nd_OctantPoint(pointS1AtIndex, _2_octant_paint);
                break;
            case MIRROR_2ND_QUADRANT:
                mirroredPointS1 = mirror_2nd_QuadrantPoint(pointS1AtIndex, _2_quadrant_paint);
                break;
            case MIRROR_2ND_SEMICIRCLE:
                mirroredPointS1 = mirror_2nd_SemicirclePoint(pointS1AtIndex, _2_semicircle_paint);
                break;
            default:
                throw new RuntimeException("Not handled action " + action);
        }

        if (mirroredPointS1 != null) {
            circlePointS1s.add(mirroredPointS1);
        } else {
            if (SHOW_LOGS)
                Log.i(TAG, "createMirroredPoint, found a point that should not be mirrored, pointS1AtIndex " + pointS1AtIndex + ", action " + action);
            if (SHOW_LOGS)
                Log.i(TAG, "createMirroredPoint, this point is already created. Skip it");

        }
    }

    private void createMirroredPoint(
            Action action,
            int pointIndex,
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    ) {

        PointS2 pointS1AtIndex = circleIndexPoint.get(pointIndex);

        PointS2 mirroredPointS2;
        switch (action) {
            case MIRROR_2ND_OCTANT:
                mirroredPointS2 = mirror_2nd_OctantPoint(pointS1AtIndex);
                break;
            case MIRROR_2ND_QUADRANT:
                mirroredPointS2 = mirror_2nd_QuadrantPoint(pointS1AtIndex);
                break;
            case MIRROR_2ND_SEMICIRCLE:
                mirroredPointS2 = mirror_2nd_SemicirclePoint(pointS1AtIndex);
                break;
            default:
                throw new RuntimeException("Not handled action " + action);
        }

        if (mirroredPointS2 != null) {
            int index = circleIndexPoint.size();

            circleIndexPoint.put(index, mirroredPointS2);
            circlePointIndex.put(mirroredPointS2, index);
        } else {
            if (SHOW_LOGS)
                Log.i(TAG, "createMirroredPoint, found a point that should not be mirrored, pointS1AtIndex " + pointS1AtIndex + ", action " + action);
            if (SHOW_LOGS)
                Log.i(TAG, "createMirroredPoint, this point is already created. Skip it");

        }
    }


    /**
     * This method takes a single point from 1st octant and mirror it to the 2nd octant
     * <p>
     * ^
     * +y |   2nd octant
     * |                           /
     * |                         /
     * |                       /
     * |                     /
     * |                   /
     * |     * (x1*, y1*)  /
     * |         * (x2*, y2*)
     * |             /
     * |           /    * (x2, y2)
     * |         /
     * |       /          * (x1, y1)
     * |     /
     * |   /
     * | /                       1st octant
     * |------------------------------------->
     * <p>
     * How to get a mirrored point (x1*, y1*) when we have it's mirror (x1, y1)?
     * x1 = y1
     * y1 = x1
     * ***********
     * How to get a mirrored point (x2*, y2*) when we have it's mirror (x2, y2)?
     * x2* = y2
     * y2* = x2
     * <p>
     * Here is the explanation of the implementation.
     * This is how 1st and 2nd octant is drawn in "Mid point circle" algorithm
     * <p>
     * DrawPixel( x + x0,  y + y0); // Octant 1
     * DrawPixel( y + x0,  x + y0); // Octant 2
     * <p>
     * To mirror second point using "firstOctantPointS1" we have to know original x and y;
     * <p>
     * Get original x, y from "firstOctantPointS1":
     * firstOctant_X = x + x0; -> x = firstOctant_X - x0;
     * firstOctant_Y = y + y0; -> y = firstOctant_Y - y0;
     * <p>
     * Get "secondOctantPoint" from original x, y
     * secondOctant_X = y + x0; -> firstOctant_Y - y0 + x0;
     * secondOctant_Y = x + y0; -> firstOctant_X - x0 + y0;
     */
    private PointS1 mirror_2nd_OctantPoint(PointS1 firstOctantPointS1, Paint paint) {
        int correctedX = firstOctantPointS1.x - mX0;
        int correctedY = firstOctantPointS1.y - mY0;
        return correctedX != correctedY ? new PointS1(correctedY + mX0, correctedX + mY0, paint)
                : null; // null means that the mirror of this point is going to be the same. (24; 24) -> mirrored (24:24)
    }

    private PointS2 mirror_2nd_OctantPoint(PointS2 firstOctantPointS1) {
        int correctedX = firstOctantPointS1.getX() - mX0;
        int correctedY = firstOctantPointS1.getY() - mY0;
        return correctedX != correctedY ? new PointS2(correctedY + mX0, correctedX + mY0)
                : null; // null means that the mirror of this point is going to be the same. (24; 24) -> mirrored (24:24)
    }

    /**
     * This method takes a single point from 1st octant and mirror it to the 2nd octant
     * <p>
     * ^ +y                                ^
     * |                  2nd Quadrant  +y |     1st Quadrant
     * |                                   |
     * |                                   |
     * |                    (x3*; y3*) *<--|---* (x3; y3)
     * |             (x2*; y2*) *<---------|----------*(x2; y2)
     * |                                   |
     * |                                   |
     * |                                   |
     * |                                   |
     * |                                   |
     * |                                   |
     * |                                   |
     * |        (x1*; y1*) *<--------------|---------------* (x1; y1)
     * |                                   |
     * |                                   |                                      +x
     * --|-----------------------------------|------------------------------------->
     * <p>
     * How to get a mirrored point (x1*, y1*) when we have it's mirror (x1, y1)?
     * x1* = x0 - (x1 - x0) = 2*x0 - x1
     * y1* = y1
     * ***********
     * How to get a mirrored point (x2*, y2*) when we have it's mirror (x2, y2)?
     * x2* = x0 - (x2 - x0) = 2*x0 - x2
     * y2* = y2
     * ***********
     * How to get a mirrored point (x3*, y3*) when we have it's mirror (x3, y3)?
     * x3* = x0 - (x3 - x0) = 2*x0 - x3
     * y3* = y3
     */
    private PointS1 mirror_2nd_QuadrantPoint(PointS1 secondQuadrantPointS1, Paint paint) {
        return new PointS1(-secondQuadrantPointS1.x + 2 * mX0, secondQuadrantPointS1.y, paint);
    }

    private PointS2 mirror_2nd_QuadrantPoint(PointS2 secondQuadrantPointS2) {
        return new PointS2(-secondQuadrantPointS2.getX() + 2 * mX0, secondQuadrantPointS2.getY());
    }

    /**
     * This method takes a single point from 1st octant and mirror it to the 2nd octant
     * <p>
     * ^
     * 2nd Quadrant  +y |     1st Quadrant
     * |
     * |
     * (x3; y3)   *   |
     * (x4; y4) *               |   |
     * |               |   |
     * |               |   |
     * |               |   |
     * |               |   |
     * |               |   |                              * (x2; y2)
     * |               |   |                              |
     * |               |   |                              |
     * |               |   |                              |  * (x1; y1)
     * |               |   |                              |  |
     * |               |   |                              |  |     +x
     * -------------------------------------|------------------------------------->
     * |               |   |                              |  |
     * |               |   |                              |  V
     * |               |   |                              |  * (x1*; y1*)
     * |               |   |                              |
     * |               |   |                              V
     * |               |   |                              * (x2*; y2*)
     * |               |   |
     * |               |   |
     * |               |   |
     * V               |   |
     * (x4; y4) *               V   |
     * (x3*; y3*) *   |
     * |
     * |
     * How to get a mirrored point (x1*, y1*) when we have it's mirror (x1, y1)?
     * x1* = x1
     * y1* = y0 - (y1 - y0) = 2 * y0 - y1
     * ***********
     * How to get a mirrored point (x2*, y2*) when we have it's mirror (x2, y2)?
     * x2* = x2
     * y2* = y0 - (y2 - y0) = 2 * y0 - y2
     * ***********
     * How to get a mirrored point (x3*, y3*) when we have it's mirror (x3, y3)?
     * x3* = x3
     * y3* = y0 - (y3 - y0) = 2 * y0 - y3
     * ***********
     * How to get a mirrored point (x4*, y4*) when we have it's mirror (x4, y4)?
     * x4* = x4
     * y4* = y0 - (y3 - y0) = 2 * y0 - y3
     */
    private PointS1 mirror_2nd_SemicirclePoint(PointS1 firstSemicirclePointS1, Paint paint) {
        /** TODO: use the same logic as {@link #mirror_2nd_OctantPoint}*/
        return new PointS1(firstSemicirclePointS1.x, -firstSemicirclePointS1.y + 2 * mY0, paint);
    }

    private PointS2 mirror_2nd_SemicirclePoint(PointS2 firstSemicirclePoint) {
        /** TODO: use the same logic as {@link #mirror_2nd_OctantPoint}*/
        return new PointS2(firstSemicirclePoint.getX(), -firstSemicirclePoint.getY() + 2 * mY0);
    }

}
