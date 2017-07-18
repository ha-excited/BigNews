package ha.excited;

final public class BigNews {
    static {
        System.loadLibrary("bigNews");
    }

    public synchronized static native boolean make(String oldFilePath, String newFilePath, String patchPath);

    public synchronized static native boolean diff(String oldFilePath, String newFilePath, String patchPath);
}
