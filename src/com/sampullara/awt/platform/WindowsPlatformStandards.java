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
   $Date: 2004/12/17 06:24:54 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/awt/platform/WindowsPlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $
   $Id: WindowsPlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $

   Revisions:
   $Log: WindowsPlatformStandards.java,v $
   Revision 1.2  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.1  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.3  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.awt.platform;

import java.awt.*;

/**
 * User: sam
 * Date: Dec 13, 2004
 * Time: 4:31:45 PM
 */
public class WindowsPlatformStandards extends PlatformStandards {

    private int xdlu;
    private int ydlu;

    public WindowsPlatformStandards(Component container) {
        Font font = container.getFont();
        FontMetrics metrics = container.getFontMetrics(font);
        xdlu = (int) (0.25 * getAverageCharacterWidth(metrics));
        ydlu = (int) (0.125 * getCharacterHeight(metrics));
    }

    private static double getCharacterHeight(FontMetrics metrics) {
        return metrics.getAscent();
    }

    private static double getAverageCharacterWidth(FontMetrics metrics) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        double width = metrics.stringWidth(characters);
        return width / 52;
    }

    public int getVerticalEdgeSpacing() {
        return 7 * ydlu;
    }

    public int getHorizontalEdgeSpacing() {
        return 7 * xdlu;
    }

    public int getVerticalComponentSpacing() {
        return 7 * ydlu;
    }

    public int getHorizontalComponentSpacing() {
        return 7 * xdlu;
    }
}
