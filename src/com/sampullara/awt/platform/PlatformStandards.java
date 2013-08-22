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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/awt/platform/PlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $
   $Id: PlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $

   Revisions:
   $Log: PlatformStandards.java,v $
   Revision 1.2  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.1  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.3  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.awt.platform;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * Created by IntelliJ IDEA.
 * User: sam
 * Date: Dec 10, 2004
 * Time: 2:13:38 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PlatformStandards {
    private static Map platformStandardsCache = new WeakHashMap();

    public abstract int getVerticalEdgeSpacing();

    public abstract int getHorizontalEdgeSpacing();

    public abstract int getVerticalComponentSpacing();

    public abstract int getHorizontalComponentSpacing();

    public static PlatformStandards getPlatformStandards(Component c) {
        PlatformStandards ps = (PlatformStandards) platformStandardsCache.get(c);
        if (ps != null) return ps;
        String lafClassName = UIManager.getSystemLookAndFeelClassName();
        if (lafClassName.equals("apple.laf.AquaLookAndFeel")) {
            ps = new MacPlatformStandards();
        } else if (lafClassName.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
            ps = new WindowsPlatformStandards(c);
        } else {
            ps = new FlushPlatformStandards();
        }
        platformStandardsCache.put(c, ps);
        return ps;
    }
}
