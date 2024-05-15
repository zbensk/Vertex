package Vertex;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;

// File handling
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GamePanel extends JPanel {

    // TODO maybe write code for when the game is finished (nothing left in fullCoords)
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;

    // Line variables
    private ArrayList<VertexPoint[]> completedLines;
    private boolean drawingLine = false;
    private VertexPoint startPoint;
    private int currentX;
    private int currentY;

    // contains all of the vertices of a triangle and colors of those trianlges
    private ArrayList<VertexTriangle> fullCoords;
    
    private ArrayList<VertexTriangle> filledTriangles;

    private ArrayList<VertexPoint> vertexPoints;
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // Initialize coordinates list by setting its values from a VertexTriangle (example below)
        fullCoords = new ArrayList<VertexTriangle>();
        // fullCoords.add(new VertexTriangle(10, 20, 10, 50, 200, 20, Color.RED));
        // fullCoords.add(new VertexTriangle(200, 20, 190, 100, 280, 20, Color.BLUE));
        // fullCoords.add(new VertexTriangle(200, 20, 10, 50, 190, 100, Color.GREEN));


        // temp while fixing errors: manually create vertexPoints
        vertexPoints = new ArrayList<VertexPoint>();
        // vertexPoints.add(new VertexPoint(360, 33, 4));
        // vertexPoints.add(new VertexPoint(411, 39, 5));
        // vertexPoints.add(new VertexPoint(366, 72, 5));
        // vertexPoints.add(new VertexPoint(351, 96, 5));
        // vertexPoints.add(new VertexPoint(318, 72, 4));
        // vertexPoints.add(new VertexPoint(411, 39, 5));
        // vertexPoints.add(new VertexPoint(438, 24, 2));
        // vertexPoints.add(new VertexPoint(465, 75, 4));
        // vertexPoints.add(new VertexPoint(393, 78, 5));
        // vertexPoints.add(new VertexPoint(372, 117, 6));
        // vertexPoints.add(new VertexPoint(273, 120, 6));
        // vertexPoints.add(new VertexPoint(468, 117, 4));
        // vertexPoints.add(new VertexPoint(453, 201, 3));
        // vertexPoints.add(new VertexPoint(378, 162, 7));
        // vertexPoints.add(new VertexPoint(321, 168, 4));
        // vertexPoints.add(new VertexPoint(408, 222, 5));
        // vertexPoints.add(new VertexPoint(453, 222, 4));
        // vertexPoints.add(new VertexPoint(330, 204, 4));
        // vertexPoints.add(new VertexPoint(402, 258, 4));
        // vertexPoints.add(new VertexPoint(342, 243, 2));

        // for (VertexPoint point : vertexPoints) {
        //     point.changeX(-100);
        // }

        completedLines = new ArrayList<VertexPoint[]>();


        filledTriangles = new ArrayList<VertexTriangle>();

        
        // Mouse trackers
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // See if user clicked within a vertex point, and if so start drawing a line from the center of the vertex
                for (VertexPoint point : vertexPoints) {
                    if (point.getNumTriangles() > 0 && point.inVertex(e.getX(), e.getY())) {
                        startPoint = point;
                        drawingLine = true;
                    }
                }
            }

            /**
             * Finish drawing the line, if final position is within a vertex point, then that line will stay, if not delete the line
             * Only draw the line if it is correct (DIFFERENT FROM NYT VERTEX GAME)
             * Can't draw a line that overlaps an already drawn line
             * Change the number shown on the VertexPoints
             * Reset line variables
             */
            @Override 
            public void mouseReleased(MouseEvent e) {
                if (drawingLine) {
                    for (VertexPoint point : vertexPoints) {
                        // Also must check that startPoint and and point are not the same
                        if (!point.equals(startPoint) && point.getNumTriangles() > 0 && point.inVertex(e.getX(), e.getY()) && connectionExists(startPoint, point) && !lineAlreadyDrawn(startPoint, point)) {
                            completedLines.add(new VertexPoint[]{startPoint, point});
                            startPoint.changeNumTriangles(-1);
                            point.changeNumTriangles(-1);
                            triangleFilled();
                            break;
                        }
                        
                    }
                    startPoint = null;
                    currentX = 0;
                    currentY = 0;
                    drawingLine = false;
                    repaint();
                }
                
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            /**
             * Set the current x and y components to the mouse's position
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawingLine) {
                    currentX = e.getX();
                    currentY = e.getY();
                    repaint();
                }
                
            }
        });
        
        startGame();
    }

    public void startGame() {
        populateFullCoords();
        createVertexPoints(); // couldn't figure out how to make it work so doing it manually
    }

    /**
     * Read the file as input and then fill up fullCoords by handling the data
     */
    public void populateFullCoords() {
        File input = new File("input.txt");
        try(Scanner scan = new Scanner(input)) {
            while (scan.hasNextLine()) {
                String[] coordsColor = scan.nextLine().split("\\(");
                String[] coords = coordsColor[0].split(", ");
                // ensure that correct number of coords has been entered
                if (coords.length != 6) {
                    continue;
                }
                // Convert values to ints
                int[] intCoords = new int[coords.length];
                for (int i = 0; i < intCoords.length; i++) {
                    intCoords[i] = Integer.parseInt(coords[i]);
                    // temp shift for x
                    if (i % 2 == 0) {
                        intCoords[i] -= 100;
                    } 
                }
                // Convert values to ints and cleanse data
                String[] colors = coordsColor[1].split(", ");
                String lastColorVal = colors[colors.length - 1];
                // removes trailing parentheses
                colors[colors.length - 1] = lastColorVal.substring(0, lastColorVal.length() - 1);

                int[] intColors = new int[colors.length];
                for (int i = 0; i < intColors.length; i++) {
                    intColors[i] = Integer.parseInt(colors[i]);
                }
                VertexTriangle vt = new VertexTriangle(intCoords[0], intCoords[1], intCoords[2], intCoords[3], intCoords[4], intCoords[5], new Color(intColors[0], intColors[1], intColors[2]));
                fullCoords.add(vt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Overrides JPanel paint operation to allow drawing to this panel(canvas)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * This is where I can draw to the JPanel
     */
    public void draw(Graphics g) {
        // Draw all completed lines (first layer)
        for (VertexPoint[] line : completedLines) {
            g.drawLine(line[0].getX(), line[0].getY(), line[1].getX(), line[1].getY());
            
        }
        if (drawingLine) {
            drawLine(g);
        }

        // Draw all completed vertex triangles
        for (VertexTriangle vt : filledTriangles) {
            vt.drawVertexTriangle(g);
        }

        if (vertexPoints.size() > 0) {
            for (VertexPoint point : vertexPoints) {
                point.drawVertexPoint(g);
            }
        }

    }

    /**
     * Draws a line from one vertex point and continues as user drags
     */
    public void drawLine(Graphics g) {
        g.drawLine(startPoint.getX(), startPoint.getY(), currentX, currentY);
    }

    /**
     * Iterates through all of the vertex triangles, and returns true if both points exist in one of them
     */
    public boolean connectionExists(VertexPoint p1, VertexPoint p2) {
        for (VertexTriangle vt : fullCoords) {
            VertexPoint[] trianglePoints = vt.getAsVertexPoints();
            // returns true if p1 and p2 are both within trianglePoints (count = 2)
            int count = 0;
            for (VertexPoint point : trianglePoints) {
                if (point.equals(p1) || point.equals(p2)) {
                    count++;
                }
            }

            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through the list of completed lines and returns true is the two points have already connected to create a line
     */
    public boolean lineAlreadyDrawn(VertexPoint p1, VertexPoint p2) {
        for (VertexPoint[] line : completedLines) {
            if (line[0].equals(p1) && line[1].equals(p2)) {
                return true;
            } else if (line[0].equals(p2) && line[1].equals(p1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a triangle has been filled by iterating through both Full_coords and completedLines
     * If triangle is filled, add it to filled triangle list, remove VertexPoints within newly filled triangle if numTriangles is 0, and potentially remove triangle from Full_Coords
    */ 
    public void triangleFilled() {
        for (int i = fullCoords.size() - 1; i >= 0; i--) {
            VertexTriangle vt = fullCoords.get(i);
            int count = 0;
            for (VertexPoint[] line : completedLines) {
                if (vt.vertexPointInTriangle(line[0]) && vt.vertexPointInTriangle(line[1])) {
                    count++;
                }
            }
            // if count is three, triangle is filled and all the operations mentioned above can be done
            if (count == 3) {
                filledTriangles.add(vt);
                // remove any VertexPoints with 0 triangles available
                for (int j = vertexPoints.size() - 1; j >= 0; j--) {
                    VertexPoint point = vertexPoints.get(j);
                    boolean pointInOtherTriangle = false;
                    for (VertexTriangle tri : fullCoords) {
                        if (!tri.equals(vt) && tri.vertexPointInTriangle(point)) {
                            pointInOtherTriangle = true;
                        }
                    }
                    if (!pointInOtherTriangle && point.getNumTriangles() == 0) {
                        vertexPoints.remove(j);
                    }
                }
                // remove VertexTriangle from fullCoords
                fullCoords.remove(i);
            }
        }
    }

    public void createVertexPoints() {
        for (VertexTriangle vt : fullCoords) {
            VertexPoint[] points = vt.getAsVertexPoints();
            
            for (VertexPoint point : points) {
                // System.out.println(point.getNumTriangles());
                // See if the point is already in vertex points, if so increment numTriangles by 1
                int pointIndex = getPointIndex(point);
                if (pointIndex != -1) {
                    vertexPoints.get(pointIndex).changeNumTriangles(1);

                } else {
                    // If not, add to vertex points
                    point.setNumTriangles(2);
                    vertexPoints.add(point);
                }

                // TODO fix errors (temporary)
                // if (pointIndex != -1 && vertexPoints.get(pointIndex).getNumTriangles() >= 6) {
                //     vertexPoints.get(pointIndex).changeNumTriangles(-1);
                // }
                
            }
        }
    }


    /**
     * @returns the first index of point in ArrayList vertexPoints, or -1 if not found
     */
    public int getPointIndex(VertexPoint point) {
        for (int i = 0; i < vertexPoints.size(); i++) {
            if (point.equals(vertexPoints.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public void pan() {

    }

    public void zoom() {
        
    }
   
}
