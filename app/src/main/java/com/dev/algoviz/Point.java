package com.dev.algoviz;

import java.util.Objects;

/**
 * Represents a point in a 2D coordinate space. Points have x and y coordinates.
 *
 * @author Andrew Meads
 */
public class Point {

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns a value indicating whether the given object is equal to this one. An object is considered equal if it is
     * also a Point, with the same x and y coordinates.
     * <p>
     * This method was auto-generated using IntelliJ's "generate equals() and hashCode()" function.
     *
     * @param o the other  object to compare
     * @return true if o is equal to this instance; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    /**
     * Allows Points to be used as keys in {@link java.util.HashMap}s and other hashtables.
     * <p>
     * This method was auto-generated using IntelliJ's "generate equals() and hashCode()" function.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
