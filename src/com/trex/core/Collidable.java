package com.trex.core;

import java.awt.Rectangle;

public interface Collidable {
    Rectangle getBounds();
    default boolean isColliding(Collidable other) {
        return this.getBounds().intersects(other.getBounds());
    }
}
