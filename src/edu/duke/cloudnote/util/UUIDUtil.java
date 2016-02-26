package edu.duke.cloudnote.util;

import java.util.UUID;

/**
 * UUID
 *
 */
public class UUIDUtil {
	public static String getUId(){
		return UUID.randomUUID().toString();
	}
}
