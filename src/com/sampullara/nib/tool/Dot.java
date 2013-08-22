/*
   Copyright 2004 Sam Pullara

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   $Author: sam $
   $Date: 2004/12/19 00:53:52 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Dot.java,v 1.3 2004/12/19 00:53:52 sam Exp $
   $Id: Dot.java,v 1.3 2004/12/19 00:53:52 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * User: sam
 * Date: Dec 17, 2004
 * Time: 6:03:46 PM
 */
class Dot {
    private Paint paint;
    private Ellipse2D shape;
    private static final int DOT_DIAMETER = 7;

    Dot(int x, int y) {
        int dr = DOT_DIAMETER / 2;
        shape = new Ellipse2D.Double(x - dr, y - dr, DOT_DIAMETER, DOT_DIAMETER);
        Color start = new Color(0, 0, 255, 200);
        Color end = new Color(0, 170, 246, 200);
        paint = new GradientPaint(x - dr, y - dr, start, x + dr, y + dr, end, true);
    }

    boolean hit(int mx, int my) {
        return shape.contains(mx, my);
    }

    void paint(Graphics2D g) {
        g.setPaint(paint);
        g.fill(shape);
    }
}
