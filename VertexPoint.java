package Vertex;

import java.awt.*;

/**
 * A VertexPoint is a circle in the vertex game that has a number on it for the number of vertices and a circle surrounding it where lines can be drawn out of it to create other triangles
 */
public class VertexPoint {
    private int x;
    private int y;
    private static final int RADIUS = 25; // radius of the vertex circle
    private int numTriangles; // number of triangles that connect to the vertex point
    
    public VertexPoint(int x, int y, int numTriangles) {
        this.x = x;
        this.y = y;
        this.numTriangles = numTriangles;
    }

    /**
     * This constructor is solely for use in the VertexTriangle class
     */ 
    public VertexPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.numTriangles = -1;
    }

    public void drawVertexPoint(Graphics g) {
        g.setColor(Color.BLACK);
        // x and y values represent the top left corner for these methods
        g.drawOval(x - RADIUS/2, y-RADIUS/2, RADIUS, RADIUS);
        // Draw a gray layer to prevent line overlaps
        g.setColor(new Color(217, 216, 212));
        g.fillOval(x - RADIUS/2, y-RADIUS/2, RADIUS, RADIUS);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, RADIUS/2));
        g.drawString(Integer.toString(numTriangles), x - RADIUS/8, y + RADIUS/5);
    }

    /**
     * Change numTriangles by change amount
     */
    public void changeNumTriangles(int change) {
        this.numTriangles += change;
    }

    /**
     * @param num Number of triangles to set numTriangles variable to
     */
    public void setNumTriangles(int num) {
        this.numTriangles = num;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNumTriangles() {
        return this.numTriangles;
    }

    public void changeX(int change) {
        this.x += change;
    }   

    /**
     * @returns true if the given x and y coordinates are within the radius of the VertexPoint
     * uses distance formula
     */
    public boolean inVertex(int x, int y) {
        if (Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) <= RADIUS) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y) {
        if (this.x == x && this.y == y) {
            return true;
        } 
        return false;
    }

    public boolean equals(VertexPoint otherPoint) {
        if (this.getX() == otherPoint.getX() && this.getY() == otherPoint.getY()) {
            return true;
        }
        return false;
    }
}
