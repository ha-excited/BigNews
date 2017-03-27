package ha.excited.sample;

import android.content.Context;

import java.io.File;

import ha.excited.BigNews;

final public class PatchUtil {
    public static boolean make(Context context, String newApkPath, String patchPath) {
        return BigNews.make(context.getPackageResourcePath(), newApkPath, patchPath) && new File(newApkPath).exists();
    }

    public static boolean diff(Context context, String newApkPath, String patchPath) {
        return BigNews.diff(context.getPackageResourcePath(), newApkPath, patchPath) && new File(patchPath).exists();
    }
}
