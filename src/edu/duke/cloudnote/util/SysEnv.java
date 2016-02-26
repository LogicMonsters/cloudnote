package edu.duke.cloudnote.util;

import java.io.Serializable;

public class SysEnv implements Serializable {
	private static final long serialVersionUID = -8590844343788255616L;

	public static String NOTEBOOK_NORMAL="normal";

	public final static String NOTEBOOK_RECYCLE="recycle";

	public final static String NOTEBOOK_FAVORITES="favorites";

	public final static String NOTEBOOK_PUSH="push";
	
	public static String[] typeCode = {SysEnv.NOTEBOOK_NORMAL,SysEnv.NOTEBOOK_FAVORITES,SysEnv.NOTEBOOK_RECYCLE,SysEnv.NOTEBOOK_PUSH};
}
