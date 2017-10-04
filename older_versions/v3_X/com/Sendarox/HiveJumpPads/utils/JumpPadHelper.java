package com.Sendarox.HiveJumpPads.utils;

import java.util.List;

import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;

public class JumpPadHelper {

	public static boolean isJumpPadExisting(int jumppadid){
		VersionConfiguration ver = new VersionConfiguration();
		List<Integer> blockedids = ver.getMissingJumppadIDs();
		if(!blockedids.contains(jumppadid) && jumppadid <= (int) ver.get("HiveJumpPads.jumpid")){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isJumpPadExistingMySQL(int jumppadid){
		return false;
	}
	
}
