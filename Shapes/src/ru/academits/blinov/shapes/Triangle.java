package ru.academits.blinov.shapes;

public class Triangle implements Shape {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double x3;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    private static double calculateLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public double getArea() {
        double lengthAB = calculateLength(x1, y1, x2, y2);
        double lengthBC = calculateLength(x2, y2, x3, y3);
        double lengthAC = calculateLength(x1, y1, x3, y3);
        double p = (lengthAB + lengthBC + lengthAC) / 2;
        return Math.sqrt(p * (p - lengthAB) * (p - lengthBC) * (p - lengthAC));
    }

    public double getPerimeter() {
        return calculateLength(x1, y1, x2, y2) + calculateLength(x2, y2, x3, y3) + calculateLength(x1, y1, x3, y3);
    }

    @Override
    public String toString() {
        return "ТРЕУГОЛЬНИК (" + "h = " + getHeight() + ", " + "b = " + getWidth() + ", " + "S = " + getArea() + ", " + "P = " + getPerimeter() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        Triangle triangle = (Triangle) obj;
        return (x1 == triangle.x1) && (x2 == triangle.x2) && (x3 == triangle.x3) && (y1 == triangle.y1) && (y2 == triangle.y2) && (y3 == triangle.y3);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(y3);
        return hash;
    }
}