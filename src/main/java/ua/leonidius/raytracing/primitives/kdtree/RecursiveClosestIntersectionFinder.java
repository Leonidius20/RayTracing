package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.RayFragment;

import java.util.Optional;

public class RecursiveClosestIntersectionFinder extends IKdTreeVisitor<Optional<Intersection>> {

    @Override
    public Optional<Intersection> visit(KdTree.INode node, Ray ray, RayFragment fragment) {

        return super.visit(node, ray, fragment);
    }

    @Override
    protected Optional<Intersection> visit(KdTree.InteriorNode node, Ray ray, RayFragment fragment) {
        node.info().traversed = true;

        // idk when this happens but who knows maybe it does
       /* if (fragment.tMin() > fragment.tMax()) {
            return Optional.empty();
        }*/

        // compute distance to split plane along ray
        double tSplit = (node.splitCoordinate() - ray.getOrigin().getValue(node.splitAxis()))
                / ray.getDirection().getValue(node.splitAxis()); // todo: can there be a division by zero?

        boolean isLeftChildFirst = ray.getOrigin().getValue(node.splitAxis()) < node.splitCoordinate()
                || (ray.getOrigin().getValue(node.splitAxis()) == node.splitCoordinate() && ray.getDirection().getValue(node.splitAxis()) <= 0);

        KdTree.INode firstChild = isLeftChildFirst ? node.leftChild() : node.rightChild();
        KdTree.INode secondChild = isLeftChildFirst ? node.rightChild() : node.leftChild();

        // check whether we need to check both children or just one

        // if the ray enters and exits only the left sub-box
        // or if it goes in inverse direction starting from the split plane
        if (tSplit > fragment.tMax() || tSplit <= 0) {
            return visit(firstChild, ray, fragment);
        } else if (tSplit < fragment.tMin()) { // if the ray enters and exits only the right sub box
            return visit(secondChild, ray, fragment);
        } else {
            // if the ray enters both sub-boxes
            // check the first child
            // var intersection = firstChild.checkIntersection(ray, new RayFragment(fragment.tMin(), tSplit));
            var intersection = visit(firstChild, ray, new RayFragment(fragment.tMin(), tSplit));
            if (intersection.isPresent()) {
                return intersection;
            }

            // check the second child
            // return secondChild.checkIntersection(ray, new RayFragment(tSplit, fragment.tMax()));
            return visit(secondChild, ray, new RayFragment(tSplit, fragment.tMax()));
        }
    }

    @Override
    protected Optional<Intersection> visit(KdTree.LeafNode node, Ray ray, RayFragment fragment) {
        node.info().traversed = true;

        Optional<Intersection> closestIntersection = Optional.empty();

        // again idk if this is needed
        if (fragment.tMin() > fragment.tMax()) {
            return Optional.empty();
        }

        for (var object : node.leafPrimitives()) {
            var intersection = object
                    .findVisibleIntersectionWithRay(ray);
            if (intersection.isEmpty()) continue;
            if (intersection.get().tParam() > fragment.tMax()
                    || intersection.get().tParam() < fragment.tMin()) continue;

            if (closestIntersection.isEmpty()
                    || intersection.get().tParam() < closestIntersection.get().tParam()) {
                closestIntersection = intersection;
            }
        }

        return closestIntersection;
    }
}
