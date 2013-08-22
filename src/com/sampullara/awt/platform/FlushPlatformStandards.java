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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/awt/platform/FlushPlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $
   $Id: FlushPlatformStandards.java,v 1.2 2004/12/17 06:24:54 sam Exp $

   Revisions:
   $Log: FlushPlatformStandards.java,v $
   Revision 1.2  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.1  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.3  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.awt.platform;


/**
 * These are a set of default spacings that put all components flush with one another and the edge
 * of their container.  Not pretty.
 * <p/>
 * User: sam
 * Date: Dec 10, 2004
 * Time: 2:25:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlushPlatformStandards extends PlatformStandards {

    public int getVerticalEdgeSpacing() {
        return 0;
    }

    public int getHorizontalEdgeSpacing() {
        return 0;
    }

    public int getVerticalComponentSpacing() {
        return 0;
    }

    public int getHorizontalComponentSpacing() {
        return 0;
    }
}
