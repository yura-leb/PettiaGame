//====================================================================
//                   JUML CASE tool (JUML = Java + UML).
// Copyright (c) 2001.                             Copyright (c) 2001.
// Romanov V.U.                                           ������� �.�.
// CM� Department                                      ��������� ����.
// Moscow State University,                     ��� ��.�.�.����������.
// Moscow, Russia.                                     ������, ������.
// All rights reserved.                            ��� ����� ��������.
//                     romsrcc@rom.srcc.msu.su
//                     master.cmc.msu.ru/romanov
//====================================================================
package tools.db.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 * The class <code>Generator</code> is used
 * for output formated strings to some target.
 *
 * @version 1.1, 04/03/2002
 * @author  Romanov V.U.
 */
public class FileGenerator implements Generator {

private int level;
public int tabSize;
public boolean isTabUsed;
private boolean firstOut;

public String fileName;
private FileWriter fw;

final static
public String nlString = "\r\n";

public FileGenerator(String pathName, String fileName) {
    init(pathName, fileName);
} // FileGenerator

public FileGenerator(String fullFileName) {
    int i = fullFileName.lastIndexOf('/');
    int j = fullFileName.lastIndexOf('\\');
    int k = Math.max(i,j);

    String pathName = fullFileName.substring(0,k);
    String fileName = fullFileName.substring(k+1);
    init(pathName, fileName);
} // FileGenerator

private void init(String pathName, String fileName) {
    this.fileName = fileName;
    tabSize   = 4;
    isTabUsed = true;
    firstOut  = true;

    try {
        File d = new File(pathName);
        d.mkdirs();

        fw = new FileWriter(pathName + '/' + fileName);
    }
    catch (IOException e)
        { System.out.println(e); }
} // init

private void makeShift() {
    try { for (int i = 0; i < level; i++)
              fw.write('\t');
          fw.flush();
    }
    catch (IOException e)
        { System.out.println(e); }
} // makeShift

public void nl() {
    try { fw.write(nlString);
          firstOut = true;
          fw.flush();
    }
    catch (IOException e)
        { System.out.println(e); }
} // nl

public void out(String s) {
    try {
        if (firstOut)
            makeShift();

        fw.write("" + s);
        fw.flush();
        firstOut = false;
    }
    catch (IOException e)
        { System.out.println(e); }
} // out

public void outln(String s)
    { out(s); nl(); }

public void inc()
    { level++; }

public void dec()
    { if (level > 0) level--; }

public void setTabSize(int n)
    { tabSize = n; }

public void setTabUsing(boolean b)
    { isTabUsed = b; }

public void close() {
    try { fw.close(); }
    catch (IOException e)
        { System.out.println(e); }
} // close

} // class FileGenerator
