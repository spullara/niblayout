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
   $Date: 2004/12/18 02:28:46 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Line.java,v 1.1 2004/12/18 02:28:46 sam Exp $
   $Id: Line.java,v 1.1 2004/12/18 02:28:46 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import java.awt.*;

/**
 * User: sam
 * Date: Dec 17, 2004
 * Time: 6:03:23 PM
 */
class Line {
    private Color color;

    Line(Color color, int x1, int y1, int x2, int y2) {
        this.color = color;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    private int x1, y1, x2, y2;

    void paint(Graphics2D g) {
        g.setPaint(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
