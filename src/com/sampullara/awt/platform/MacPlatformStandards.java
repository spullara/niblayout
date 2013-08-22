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
   $Date: 2004/12/17 01:06:41 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/awt/platform/MacPlatformStandards.java,v 1.1 2004/12/17 01:06:41 sam Exp $
   $Id: MacPlatformStandards.java,v 1.1 2004/12/17 01:06:41 sam Exp $

   Revisions:
   $Log: MacPlatformStandards.java,v $
   Revision 1.1  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.3  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.awt.platform;


/**
 * These platform guidelines for the Mac were derived from use the Apple IB tool.
 * <p/>
 * User: sam
 * Date: Dec 10, 2004
 * Time: 2:24:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class MacPlatformStandards extends PlatformStandards {
    public int getVerticalEdgeSpacing() {
        return 12;
    }

    public int getHorizontalEdgeSpacing() {
        return 14;
    }

    public int getVerticalComponentSpacing() {
        return 10;
    }

    public int getHorizontalComponentSpacing() {
        return 10;
    }
}
