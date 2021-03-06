/* *****************************************************************************
 *  Name: Ossim Belias
 *  Date: 2020-07-17
 *  Description: Among all points given, this program examines 4 points at a time and checks whether
 * they all lie on the same line segment, returning all such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int INIT_SIZE = 1;
    private int count;
    private final Point[] points;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument null");
        }
        else {
            this.count = 0;
            this.lineSegments = new LineSegment[INIT_SIZE];
            Arrays.sort(points);
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) {
                    throw new IllegalArgumentException("Argument null");
                }
                if (i + 1 <= points.length - 1 && points[i].compareTo(points[i + 1]) == 0) {
                    throw new IllegalArgumentException("Same point twice");
                }
            }
        }
        this.points = points;
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < this.count; i++) {
            copy[i] = this.lineSegments[i];
        }
        this.lineSegments = copy;
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.count;
    }

    // the line segments
    public LineSegment[] segments() {
        for (int i = 0; i < this.points.length; i++) {
            for (int j = i + 1; j < this.points.length - 2; j += 3) {

                double slp1 = this.points[i].slopeTo(this.points[j]);
                double slp2 = this.points[i].slopeTo(this.points[j + 1]);
                double slp3 = this.points[i].slopeTo(this.points[j + 2]);
                if (slp1 - slp2 == 0.0 && slp1 - slp3 == 0.0) {
                    if (this.count == this.lineSegments.length) {
                        this.resize(4 * this.count);
                    }
                    lineSegments[this.count++] = new LineSegment(this.points[i],
                                                                 this.points[j + 2]);
                }
            }
        }
        return this.lineSegments;
    }

    // use this function for test only
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.count);
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}
