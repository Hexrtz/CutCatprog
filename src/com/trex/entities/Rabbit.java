package com.trex.entities;

import java.awt.Image;

/*****************************************************************************
 *                                                                           *
 * CLASS: Rabbit                                                             *
 * DESCRIPTION: A specific fast-moving enemy obstacle.                       *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Inheritance: Extends from Obstacle.                                     *
 * - Overriding: Overrides update() to define its unique movement.           *
 *                                                                           *
 ****************************************************************************/
public class Rabbit extends Obstacle {

    public Rabbit(double x, double y, int width, int height, Image image, double speed) {
        super(x, y, width, height, image, speed);
    }

    @Override
    public void update() {
        x -= speed;
    }
}
