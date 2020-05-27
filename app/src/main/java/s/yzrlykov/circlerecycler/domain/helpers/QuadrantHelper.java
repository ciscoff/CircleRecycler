package s.yzrlykov.circlerecycler.domain.helpers;

import android.view.View;

import s.yzrlykov.circlerecycler.domain.PointS1;
import s.yzrlykov.circlerecycler.domain.PointS2;
import s.yzrlykov.circlerecycler.domain.ViewData;

/**
 * Created by danylo.volokh on 12/3/2015.
 *
 * This is generic interface for quadrant related functionality.
 *
 * To layout in each quadrant you should implement quadrant-specific classes :
 * {@link FirstQuadrantHelper}
 */
public interface QuadrantHelper {
    PointS2 findNextViewCenter(ViewData previousViewData, int nextViewHalfViewWidth, int nextViewHalfViewHeight);

    int getViewCenterPointIndex(PointS2 pointS2);

    PointS2 getViewCenterPoint(int newCenterPointIndex);

    int getNewCenterPointIndex(int newCalculatedIndex);

    PointS2 findPreviousViewCenter(ViewData nextViewData, int previousViewHalfViewHeight);

    boolean isLastLayoutedView(int recyclerHeight, View view);

    int checkBoundsReached(int recyclerViewHeight, int dy, View firstView, View lastView, boolean isFirstItemReached, boolean isLastItemReached);

    int getOffset(int recyclerViewHeight, View lastView);

}