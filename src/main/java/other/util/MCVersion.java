package other.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum MCVersion {
    v1_17("1.17", 17, 0, 0),
	v1_16("1.16", 16, 0, 0),
	v1_15("1.15", 15, 0, 0),
	v1_14("1.14", 14, 0, 0),
	v1_13("1.13", 13, 0, 0),
	v1_12("1.12", 12, 0, 0),
	v1_11("1.11", 11, 0, 0),
	v1_10("1.10", 10, 0, 0),
	v1_9("1.9", 9, 0, 0),
	v1_8("1.8", 8, 0, 0),
	v1_7("1.7", 7, 0, 0),
	vLegacy("Legacy", 6, 0, 0),
	vUnknown("Unknown",0,0,0);

	public static final Map<String, MCVersion> STRING_TO_VERSION = Arrays.stream(values()).collect(Collectors.toMap(MCVersion::toString, (o) -> o));
	public final String name;
	public final int release;
	public final int subVersion;
	public final int snapshot;

	MCVersion(String name, int release, int subVersion, int snapshot) {
		this.name = name;
		this.release = release;
		this.subVersion = subVersion;
		this.snapshot = snapshot;
	}

	public static MCVersion fromString(String name) {
		return STRING_TO_VERSION.get(name);
	}

    /***
     * Compares the provided versions and returns True or False
     * "Unknown" is the oldest version
     * @param v MCVersion
     * @return Exclusive boolean
     */
	public boolean isNewerThan(MCVersion v) {
		return this.compareTo(v) < 0;
	}

    /***
     * Compares the provided versions and returns True or False
     * "Unknown" is the oldest version
     * @param v MCVersion
     * @return Exclusive boolean
     */
	public boolean isOlderThan(MCVersion v) {
		return this.compareTo(v) > 0;
	}

    /***
     * Compares the provided versions and returns True or False
     * "Unknown" is the oldest version
     * @param a MCVersion
     * @param b MCVersion
     * @return Inclusive boolean
     */
	public boolean isBetween(MCVersion a, MCVersion b) {
		return this.compareTo(a) <= 0 && this.compareTo(b) >= 0;
	}

	public String toString() {
		return this.name;
	}
}


