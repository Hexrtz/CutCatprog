package com.trex.core;

import java.awt.Rectangle;

/*****************************************************************************
 *                                                                           *
 * CLASS: Collidable                                                         *
 * DESCRIPTION: Interface defining collision boundaries for game entities.   *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Interface Class: Defines getBounds() and default interaction methods.   *
 *                                                                           *
 ****************************************************************************/
public interface Collidable {
    Rectangle getBounds();
    default boolean isColliding(Collidable other) {
        return this.getBounds().intersects(other.getBounds());
    }
}
