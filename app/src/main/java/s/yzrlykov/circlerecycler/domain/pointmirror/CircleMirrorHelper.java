package s.yzrlykov.circlerecycler.domain.pointmirror;

import java.util.List;
import java.util.Map;

import s.yzrlykov.circlerecycler.domain.PointS1;
import s.yzrlykov.circlerecycler.domain.PointS2;

/**
 * Created by danylo.volokh on 12/4/2015.
 * <p>
 * This is generic interface for mirroring pointS1s related functionality.
 * <p>
 * For layouting in each quadrant you should implement quadrant-specific classes.
 * For example:
 * {@link FirstQuadrantCircleMirrorHelper}
 * TODO: create other three
 */
public interface CircleMirrorHelper {

    /**
     * This method implementation should mirror second octant using input of already created pointS1s.
     * They might be any of 7 octant that left. Specific implementation should get correct input octant and mirror 2nd octant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Octant(
            List<PointS1> circlePointS1s
    );

    /**
     * This method implementation should mirror second quadrant using input of already created pointS1s.
     * They might be any of 3 quadrant that left. Specific implementation should get correct input quadrant and mirror 2nd quadrant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Quadrant(
            List<PointS1> circlePointS1s
    );

    /**
     * This method implementation should mirror second semicircle using input of already created pointS1s.
     * It should be other semicircle. Specific implementation should get correct input semicircle and mirror 2nd semicircle from it.
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Semicircle(
            List<PointS1> circlePointS1s
    );

    /**
     * This method implementation should mirror second octant using input of already created pointS1s.
     * They might be any of 7 octant that left. Specific implementation should get correct input octant and mirror 2nd octant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Octant(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    );

    /**
     * This method implementation should mirror second quadrant using input of already created pointS1s.
     * They might be any of 3 quadrant that left. Specific implementation should get correct input quadrant and mirror 2nd quadrant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Quadrant(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    );

    /**
     * This method implementation should mirror second semicircle using input of already created pointS1s.
     * It should be other semicircle. Specific implementation should get correct input semicircle and mirror 2nd semicircle from it.
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Semicircle(
            Map<Integer, PointS2> circleIndexPoint,
            Map<PointS2, Integer> circlePointIndex
    );
}
