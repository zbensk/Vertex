package Vertex;

import java.awt.*;
/**
 * This is used as a triangle in the full vertex games, with all of the necessary coordinates of the triangle and its color
 */
public class VertexTriangle {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;
    private Color triColor;
    
    public VertexTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.triColor = color;
    }

    public VertexTriangle() {
        this(0, 0, 0, 0, 0, 0, Color.WHITE);
    }

    public void drawVertexTriangle(Graphics g) {
        int[] xPoints = {this.x1, this.x2, this.x3};
        int[] yPoints = {this.y1, this.y2, this.y3};
        g.setColor(this.triColor);
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public int[] getVertices() {
        return new int[]{x1, y1, x2, y2, x3, y3};
    }

    public VertexPoint[] getAsVertexPoints() {
        return new VertexPoint[]{new VertexPoint(x1, y1), new VertexPoint(x2, y2), new VertexPoint(x3, y3)};
    }

    /**
     * Sees if the parameter vertex point has the same x and y values as one of the three triangles vertices
     */
    public boolean vertexPointInTriangle(VertexPoint point) {
        int[] triangleVertices = this.getVertices();
        for (int i = 0; i < triangleVertices.length; i += 2) {
            if (point.equals(triangleVertices[i], triangleVertices[i + 1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of shared points between two vertex triangles
     */
    public int getSharedPoints(VertexTriangle otherTriangle) {
        VertexPoint[] vp1 = this.getAsVertexPoints();
        VertexPoint[] vp2 = otherTriangle.getAsVertexPoints();
        int sharedPoints = 0;
        for (int i = 0; i < vp1.length; i++) {
            for (int j = 0; j < vp2.length; j++) {
                if (vp1[i].equals(vp2[j])) {
                    sharedPoints++;
                    
                }
            }
        }
        return sharedPoints;
    }

    public Color getColor() {
        return this.triColor;
    }
}
