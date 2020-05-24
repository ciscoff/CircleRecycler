package s.yzrlykov.circlerecycler.domain.pointmirror;

import java.util.List;

import s.yzrlykov.circlerecycler.domain.Point;

/**
 * Created by danylo.volokh on 12/4/2015.
 * <p>
 * This is generic interface for mirroring points related functionality.
 * <p>
 * For layouting in each quadrant you should implement quadrant-specific classes.
 * For example:
 * {@link FirstQuadrantCircleMirrorHelper}
 * TODO: create other three
 */
public interface CircleMirrorHelper {

    /**
     * This method implementation should mirror second octant using input of already created points.
     * They might be any of 7 octant that left. Specific implementation should get correct input octant and mirror 2nd octant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Octant(
            List<Point> circlePoints
    );

    /**
     * This method implementation should mirror second quadrant using input of already created points.
     * They might be any of 3 quadrant that left. Specific implementation should get correct input quadrant and mirror 2nd quadrant from it
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Quadrant(
            List<Point> circlePoints
    );

    /**
     * This method implementation should mirror second semicircle using input of already created points.
     * It should be other semicircle. Specific implementation should get correct input semicircle and mirror 2nd semicircle from it.
     * Here the order of input and order of output does matter.
     */
    void mirror_2nd_Semicircle(
            List<Point> circlePoints
    );
}
