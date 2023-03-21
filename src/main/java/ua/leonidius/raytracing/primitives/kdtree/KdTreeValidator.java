package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.RayFragment;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.List;

public class KdTreeValidator extends IKdTreeVisitor<List<IPrimitive>> {

    @Override
    protected List<IPrimitive> visit(KdTree.InteriorNode node, Ray ray, RayFragment fragment) {
        var leftPrim = visit(node.leftChild(), ray, fragment);
        var rightPrim = visit(node.rightChild(), ray, fragment);

        var leftNum = leftPrim.size();
        var rightNum = rightPrim.size();

        var dup = leftPrim.stream().filter(rightPrim::contains).toList();
        var dupNum =  dup.size();

        boolean valid = leftNum + rightNum - dupNum == node.info().primitivesNumber;

        var sb = new StringBuilder();
        sb.append("-".repeat(Math.max(0, node.info().depth)));
        sb.append(node.info().type).append(" ");
        sb.append("Valid: ").append(valid).append(" ,");
        sb.append("depth: ").append(node.info().depth).append(" ,");
        sb.append("left: ").append(leftNum).append(" ,");
        sb.append("right: ").append(rightNum).append(" ,");
        sb.append("dup: ").append(dupNum).append(" ,");
        sb.append("total: ").append(node.info().primitivesNumber).append(" ,");
        sb.append("missing: ").append(node.info().primitivesNumber - leftNum - rightNum + dupNum).append(" ,");

        System.out.println(sb);

        var result = new java.util.ArrayList<>(List.copyOf(leftPrim));
        rightPrim.forEach(p -> {
            if (!result.contains(p)) {
                result.add(p);
            }
        });
        return result;
    }

    @Override
    protected List<IPrimitive> visit(KdTree.LeafNode node, Ray ray, RayFragment fragment) {
        return node.leafPrimitives();
    }

    public void validate(KdTree kdTree) {
        visit(kdTree.root, null, null);
    }

}
