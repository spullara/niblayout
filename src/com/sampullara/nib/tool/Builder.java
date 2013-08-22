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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Builder.java,v 1.1 2004/12/19 07:56:25 sam Exp $
   $Id: Builder.java,v 1.1 2004/12/19 07:56:25 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import com.sampullara.nib.NIBLayoutManager;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.prefs.Preferences;

/**
 * User: sam
 * Date: Dec 18, 2004
 * Time: 7:46:36 PM
 */
public class Builder {

    private String name;
    private JFrame editFrame;
    private JPanel editPanel;
    private Editor editor;
    private static Preferences prefs = Preferences.userNodeForPackage(Builder.class);

    public Builder() {
    }

    public static Builder load(File file) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        try {
            XMLDecoder xmld = new XMLDecoder(is);
            return (Builder) xmld.readObject();
        } finally {
            is.close();
        }
    }

    public void save(File file) throws IOException {
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        XMLEncoder xmle = new XMLEncoder(os);
        xmle.writeObject(this);
        os.close();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init() {
        init("Untitled");
    }

    public void init(String name) {
        Builder builder = new Builder();
        this.name = name;
        editFrame = new EditFrame(name);
        editPanel = new JPanel(new NIBLayoutManager());
        editFrame.setContentPane(builder.editPanel);
        editor = new Editor(builder.editFrame);
    }

}
