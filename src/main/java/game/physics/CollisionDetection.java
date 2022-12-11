package game.physics;

import processing.core.PVector;

import java.util.ArrayList;

public class CollisionDetection {

    public CollisionDetection(){}

    private float[] distPointToLine(PVector point, PVector a, PVector b){
        PVector ab = new PVector(a.x - b.x, a.y - b.y);
        PVector ap = new PVector(point.x - a.x, point.y - a.y);

        float proj = PVector.dot(ab,ap);
        float abLenSqr = ab.x * ab.x + ab.y * ab.y;
        float d = proj / abLenSqr;

        PVector closest = new PVector();

        if (d <= 0.0)
        {
            closest = a;
        }
        else if (d >= 1.0)
        {
            closest = b;
        }
        else
        {
            closest = new PVector(a.x + ab.x * d, a.y + ab.y * d);
        }

        float dx = point.x - closest.x;
        float dy = point.y - closest.y;

        float distSqr = dx * dx + dy * dy;

        float[] data = new float[3];
        data[0] = closest.x;
        data[1] = closest.y;
        data[2] = distSqr;
        return data;
    }

    private PVector getMinMax(ArrayList<PVector> points, PVector normal)
    {
        float minProj = PVector.dot(points.get(0), normal);
        float maxProj = PVector.dot(points.get(0), normal);

        for (int i = 1; i < points.size(); i++)
        {
            float currentProj = PVector.dot(points.get(i), normal);
            if (currentProj < minProj)
            {
                minProj = currentProj;
            }

            if (currentProj > maxProj)
            {
                maxProj = currentProj;
            }
        }

        return new PVector(minProj,maxProj);
    }

    private ArrayList<PVector> getNormals(PolygonCollider a)
    {
        ArrayList<PVector> normals = new ArrayList<>();
        ArrayList<PVector> points = a.getTransformedCornerPoints();

        for (int i = 0; i < points.size(); i++)
        {
            int j = (i + 1) % points.size();
            PVector point1 = points.get(i);
            PVector point2 = points.get(j);
            PVector edge = new PVector(point2.x - point1.x, point2.y - point1.y);
            normals.add(new PVector(edge.y, -edge.x));
        }
        return normals;
    }

    public CollisionData PolygonCollisionSatManifold(PolygonCollider a, PolygonCollider b)
    {
        CollisionData m = new CollisionData();

        if(a.getMass() + b.getMass() <= 0) return m;

        ArrayList<PVector> normalsPoly1 = getNormals(a);
        ArrayList<PVector> normalsPoly2 = getNormals(b);

        boolean separated = false;

        PVector normal = new PVector();
        float minDepth = Float.MAX_VALUE;

        for (int i = 0; i < normalsPoly1.size(); i++)
        {
            PVector projectionPoly1 = getMinMax(a.getTransformedCornerPoints(), normalsPoly1.get(i));
            PVector projectionPoly2 = getMinMax(b.getTransformedCornerPoints(), normalsPoly1.get(i));

            separated = projectionPoly1.x >= projectionPoly2.y || projectionPoly2.x >= projectionPoly1.y;
            if (separated) break;

            float depth = Math.min(projectionPoly2.y - projectionPoly1.x, projectionPoly1.y - projectionPoly2.x);

            if (depth < minDepth)
            {
                minDepth = depth;
                normal = normalsPoly1.get(i);
            }
        }

        if (!separated)
        {
            for (int i = 0; i < normalsPoly2.size(); i++)
            {
                PVector projectionPoly1 = getMinMax(a.getTransformedCornerPoints(), normalsPoly2.get(i));
                PVector projectionPoly2 = getMinMax(b.getTransformedCornerPoints(), normalsPoly2.get(i));


                separated = projectionPoly1.x >= projectionPoly2.y || projectionPoly2.x >= projectionPoly1.y;
                if (separated) break;

                float depth = Math.min(projectionPoly2.y - projectionPoly1.x, projectionPoly1.y - projectionPoly2.x);

                if (depth < minDepth)
                {
                    minDepth = depth;
                    normal = normalsPoly2.get(i);
                }
            }
        }


        if (!separated)
        {
            PVector ab = PVector.sub(b.position,a.position);//new PVector(b.position.x -a.position.x, b.position.y - a.position.y);

            //if (PVector.dot(ab, normal) < 0) normal.mult(-1);

            m.penetration = minDepth / normal.mag();
            m.normal = normal.normalize();
            m.collided = true;
        }
        return m;
    }
}
