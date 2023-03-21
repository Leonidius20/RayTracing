package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;

import java.util.ArrayList;
import java.util.OptionalDouble;

public class BoxOutline implements IShape3d {

    private final ArrayList<Ray> rays = new ArrayList<>(12);
    private final ArrayList<Double> rayEnds = new ArrayList<>(12);
    private BoundingBox boundingBox = null;

    public static BoxOutline fromAABB(BoundingBox aabb) {
        var box = new BoxOutline();
        box.boundingBox = aabb;

        var a = aabb.getMinPoint();
        var b = a.add(new Vector3(0, 1, 0));
        var c = b.add(new Vector3(1, 0, 0));
        var d = a.add(new Vector3(1, 0, 0));

        var f = aabb.getMaxPoint();
        var e = f.add(new Vector3(-1, 0, 0));
        var h = f.add(new Vector3(0, -1, 0));
        var g = h.add(new Vector3(-1, 0, 0));

        var ad = d.subtract(a);
        var adRay = new Ray(a, ad.normalize());
        var adRayEnd = ad.calculateLength();

        var bc = c.subtract(b);
        var bcRay = new Ray(b, bc.normalize());
        var bcRayEnd = bc.calculateLength();

        var gh = h.subtract(g);
        var ghRay = new Ray(g, gh.normalize());
        var ghRayEnd = gh.calculateLength();

        var ef = f.subtract(e);
        var efRay = new Ray(e, ef.normalize());
        var efRayEnd = ef.calculateLength();

        var ab = b.subtract(a);
        var abRay = new Ray(a, ab.normalize());
        var abRayEnd = ab.calculateLength();

        var cd = d.subtract(c);
        var cdRay = new Ray(c, cd.normalize());
        var cdRayEnd = cd.calculateLength();

        var ge = e.subtract(g);
        var geRay = new Ray(g, ge.normalize());
        var geRayEnd = ge.calculateLength();

        var hf = f.subtract(h);
        var hfRay = new Ray(h, hf.normalize());
        var hfRayEnd = hf.calculateLength();

        var ag = g.subtract(a);
        var agRay = new Ray(a, ag.normalize());
        var agRayEnd = ag.calculateLength();

        var be = e.subtract(b);
        var beRay = new Ray(b, be.normalize());
        var beRayEnd = be.calculateLength();

        var cf = f.subtract(c);
        var cfRay = new Ray(c, cf.normalize());
        var cfRayEnd = cf.calculateLength();

        var dh = h.subtract(d);
        var dhRay = new Ray(d, dh.normalize());
        var dhRayEnd = dh.calculateLength();

        box.rays.add(adRay);
        box.rays.add(bcRay);
        box.rays.add(ghRay);
        box.rays.add(efRay);
        box.rays.add(abRay);
        box.rays.add(cdRay);
        box.rays.add(geRay);
        box.rays.add(hfRay);
        box.rays.add(agRay);
        box.rays.add(beRay);
        box.rays.add(cfRay);
        box.rays.add(dhRay);

        box.rayEnds.add(adRayEnd);
        box.rayEnds.add(bcRayEnd);
        box.rayEnds.add(ghRayEnd);
        box.rayEnds.add(efRayEnd);
        box.rayEnds.add(abRayEnd);
        box.rayEnds.add(cdRayEnd);
        box.rayEnds.add(geRayEnd);
        box.rayEnds.add(hfRayEnd);
        box.rayEnds.add(agRayEnd);
        box.rayEnds.add(beRayEnd);
        box.rayEnds.add(cfRayEnd);
        box.rayEnds.add(dhRayEnd);

        return box;
    }


    @Override
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        var bbIntersectionO = boundingBox.findVisibleIntersectionWithRay(ray);
        if (bbIntersectionO.isEmpty()) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(bbIntersectionO.get().tMin());
        }
    }

    @Override
    public Normal getRealNormalAt(Point point) {
        return new Normal(0, -1, 0);
    }

    @Override
    public Normal getInterpolatedNormalAt(Point point) {
        return new Normal(0, -1, 0);
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return boundingBox;
    }
}
