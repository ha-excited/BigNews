package ha.excited;

final public class BigNews {
    static {
        System.loadLibrary("bigNews");
    }

    public static native boolean make(String oldFilePath, String newFilePath, String patchPath);

    public static native boolean diff(String oldFilePath, String newFilePath, String patchPath);
}
