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
   $Date: 2004/12/19 07:56:25 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Resizer.java,v 1.9 2004/12/19 07:56:25 sam Exp $
   $Id: Resizer.java,v 1.9 2004/12/19 07:56:25 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import com.sampullara.nib.NIBConstraint;
import com.sampullara.nib.NIBLayoutManager;

import java.awt.*;

/**
 * User: sam
 * Date: Dec 17, 2004
 * Time: 6:04:15 PM
 */
class Resizer {
    private Component c;
    private Dot[][] dots = new Dot[3][3];
    private NIBLayoutManager lm;

    Resizer(NIBLayoutManager lm, Component c) {
        this.setC(c);
        this.lm = lm;
        moveDots();
    }

    private void moveDots() {
        Rectangle r = c.getBounds();
        dots[0][0] = new Dot(r.x, r.y);
        dots[0][1] = new Dot(r.x, r.y + r.height / 2);
        dots[0][2] = new Dot(r.x, r.y + r.height);
        dots[1][0] = new Dot(r.x + r.width / 2, r.y);
        dots[1][2] = new Dot(r.x + r.width / 2, r.y + r.height);
        dots[2][0] = new Dot(r.x + r.width, r.y);
        dots[2][1] = new Dot(r.x + r.width, r.y + r.height / 2);
        dots[2][2] = new Dot(r.x + r.width, r.y + r.height);
    }

    private boolean selected;
    private int sx;
    private int sy;
    private Point lastOrigin;

    boolean findDot(int x, int y) {
        setSelected(false);
        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                Dot dot = dots[i][j];
                if (dot != null && dot.hit(x, y)) {
                    setSelected(true);
                    sx = i;
                    sy = j;
                    lastOrigin = new Point(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    void paint(Graphics2D g) {
        moveDots();
        if (isSelected()) {
            dots[sx][sy].paint(g);
        } else {
            for (int i = 0; i < dots.length; i++) {
                for (int j = 0; j < dots[i].length; j++) {
                    Dot dot = dots[i][j];
                    if (dot != null) dot.paint(g);
                }
            }
        }
    }

    void resize(int x, int y) {
        int dx = x - lastOrigin.x;
        int dy = y - lastOrigin.y;
        lastOrigin = new Point(x, y);
        NIBConstraint nc = lm.getConstraint(c);
        Rectangle frame = nc.frame;
        switch (sx) {
            case 0:
                frame.x += dx;
                frame.width -= dx;
                nc.dw = nc.dw + dx;
                break;
            case 1:
                break;
            case 2:
                frame.width += dx;
                nc.de = nc.de - dx;
                break;
        }
        switch (sy) {
            case 0:
                frame.y += dy;
                nc.dn = nc.dn + dy;
                frame.height -= dy;
                break;
            case 1:
                break;
            case 2:
                frame.height += dy;
                nc.ds = nc.ds - dy;
                break;
        }
    }

    public Component getC() {
        return c;
    }

    public void setC(Component c) {
        this.c = c;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void snap(boolean hg, boolean vg, int guidex, int guidey) {
        NIBConstraint nc = lm.getConstraint(c);
        Rectangle frame = nc.frame;
        Rectangle bounds = c.getBounds();
        if (hg) {
            switch (sy) {
                case 0:
                    {
                        int dy = guidey - bounds.y;
                        if (dy <= Editor.FUDGE) {
                            frame.y += dy;
                            nc.dn = nc.dn + dy;
                            frame.height -= dy;
                        }
                        break;
                    }
                case 2:
                    {
                        int dy = guidey - bounds.y - bounds.height;
                        if (dy <= Editor.FUDGE) {
                            frame.height += dy;
                            nc.ds = nc.ds - dy;
                        }
                        break;
                    }
            }
        } else {
            switch (sy) {
                case 0:
                    {
                        nc.setLocationY(bounds.y);
                        nc.setHeight(bounds.height);
                        break;
                    }
                case 2:
                    {
                        nc.setHeight(bounds.height);
                        break;
                    }
            }
        }
        if (vg) {
            switch (sx) {
                case 0:
                    {
                        int dx = guidex - bounds.x;
                        if (dx <= Editor.FUDGE) {
                            frame.x += dx;
                            frame.width -= dx;
                            nc.dw = nc.dw + dx;
                        }
                        break;
                    }
                case 2:
                    {
                        int dx = guidex - bounds.x - bounds.width;
                        if (dx <= Editor.FUDGE) {
                            frame.width += dx;
                            nc.de = nc.de - dx;
                        }
                        break;
                    }
            }
        } else {
            switch (sx) {
                case 0:
                    {
                        nc.setLocationX(bounds.x);
                        nc.setWidth(bounds.width);
                        break;
                    }
                case 2:
                    {
                        nc.setWidth(bounds.width);
                        break;
                    }
            }
        }
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }
}
