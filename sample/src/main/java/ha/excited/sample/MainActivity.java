package ha.excited.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

    private void diff() {
        if (PatchUtil.diff(this, NEW_APK, PATCH_FILE)) {
            Toast.makeText(this, getString(R.string.diff_done) + PATCH_FILE, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.diff_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void make() {
        if (PatchUtil.make(this, OUT_APK, PATCH_FILE)) {
            Toast.makeText(this, getString(R.string.make_done) + OUT_APK, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(new File(OUT_APK)),
                    "application/vnd.android.package-archive"));
        } else {
            Toast.makeText(this, getString(R.string.make_failed), Toast.LENGTH_SHORT).show();
        }
    }
}
