package ha.excited.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String NEW_APK = PATH + File.separator + "new.apk";
    private static final String OUT_APK = PATH + File.separator + "out.apk";
    private static final String PATCH_FILE = PATH + File.separator + "patch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonDiff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff();
            }
        });
        findViewById(R.id.buttonMake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void diff() {
        if(!hasPermission()) return;
        if (PatchUtil.diff(this, NEW_APK, PATCH_FILE)) {
            Toast.makeText(this, getString(R.string.diff_done) + PATCH_FILE, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.diff_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void make() {
        if(!hasPermission()) return;
        if (PatchUtil.make(this, OUT_APK, PATCH_FILE)) {
            Toast.makeText(this, getString(R.string.make_done) + OUT_APK, Toast.LENGTH_SHORT).show();
            install(OUT_APK);
        } else {
            Toast.makeText(this, getString(R.string.make_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void install(String apkPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startActivity(new Intent(Intent.ACTION_VIEW).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION).setDataAndType(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(apkPath)), "application/vnd.android.package-archive"));
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive"));
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (i == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        Toast.makeText(this, getString(R.string.permission_invalid), Toast.LENGTH_SHORT).show();
        requestPermission();
        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0x0001);
    }
}
