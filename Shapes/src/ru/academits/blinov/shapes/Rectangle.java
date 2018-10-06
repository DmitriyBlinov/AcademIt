package ru.academits.blinov.shapes;

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle (double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getPerimeter() {
        return (height + width) * 2;
    }

    public double getArea() {
        return height * width;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
}
