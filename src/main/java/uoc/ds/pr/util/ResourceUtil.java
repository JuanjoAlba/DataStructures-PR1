package uoc.ds.pr.util;

import uoc.ds.pr.UniversityEvents;

public class ResourceUtil {
    public static byte getFlag(byte... flags) {
        byte resultFlag = 0;
        for (byte flag : flags) {
            resultFlag += flag;
        }
        return resultFlag;
    }

    public static boolean hasVideoProjector(byte resource) {
        return (resource & 1) != 0;
    }

    public static boolean hasTouchScreen(byte resource) {
        return (resource & 2) != 0;
    }

    public static boolean hasComputer(byte resource) {
        return (resource & 4) != 0;
    }

    public static boolean hasAuxiliaryMic(byte resource) {
        return (resource & 8) != 0;
    }

    public static boolean hasAllOpts(byte resource) {
        return resource == UniversityEvents.FLAG_ALL_OPTS;
    }
}
