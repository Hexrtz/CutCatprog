package com.trex.core;

import java.awt.Graphics2D;

/*****************************************************************************
 *                                                                           *
 * CLASS: Renderable                                                         *
 * DESCRIPTION: Interface for visually drawable game components.             *
 *                                                                           *
 * OOP CONCEPTS APPLIED:                                                     *
 * - Interface Class: Enforces the implementation of the draw() method.      *
 *                                                                           *
 ****************************************************************************/
public interface Renderable {
    void draw(Graphics2D g2d);
}
