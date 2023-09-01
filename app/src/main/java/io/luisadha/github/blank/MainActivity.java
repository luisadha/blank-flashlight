package io.luisadha.github.blank;

import android.app.Activity;
import android.view.WindowManager.LayoutParams;
import android.os.*;
import android.util.Log;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast; // Anda perlu mengimpor Toast
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isTorchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mengecek apakah perangkat memiliki flash/senter
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // Perangkat tidak memiliki flash/senter, tambahkan penanganan sesuai kebutuhan Anda
            Toast.makeText(this, "Perangkat Anda tidak mendukung senter.", Toast.LENGTH_SHORT).show();
        } else {
            cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            try {
                cameraId = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // Perangkat tidak mendukung senter, tampilkan pesan peringatan
            Toast.makeText(this, "Perangkat Anda tidak mendukung senter.", Toast.LENGTH_SHORT).show();
        } else if (isTorchOn) {
            turnOffTorch();
        } else {
            turnOnTorch();
        }
    }
    return super.onTouchEvent(event);
}

    private void turnOnTorch() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            isTorchOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffTorch() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isTorchOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    
    public class BrightnessControlActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        
        // Mengatur kecerahan layar ke nilai yang diinginkan (contoh: 0.5 untuk setengah kecerahan)
        float brightnessValue = 1.0f; // Anda dapat mengganti nilai ini sesuai dengan kebutuhan Anda
        
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightnessValue;
        getWindow().setAttributes(layoutParams);
        }
    }
    
  
}