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

/**
 * The class <code>Generator</code> is used
 * for output of formated text to some target.
 *
 * @version 1.0, 05/17/2002
 * @author  Romanov V.U.
 */
public interface Generator {

public abstract void inc();
public abstract void dec();

public abstract void out(String s);
public abstract void outln(String s);
public abstract void nl();

public abstract void setTabSize(int s);
public abstract void setTabUsing(boolean s);

public abstract void close();
} // interface Generator
