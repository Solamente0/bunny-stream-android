package net.bunnystream.stream;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.common.ConnectChecker;
import com.pedro.encoder.input.video.CameraOpenException;
import com.pedro.library.rtmp.RtmpCamera1;

public class StreamActivity extends AppCompatActivity
        implements ConnectChecker, View.OnClickListener, SurfaceHolder.Callback {

    private RtmpCamera1 rtmpCamera1;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.stream_view);
        SurfaceView surfaceView = findViewById(R.id.surface_view);
        button = findViewById(R.id.start_stop);
        button.setOnClickListener(this);
        rtmpCamera1 = new RtmpCamera1(surfaceView, this);
        rtmpCamera1.getStreamClient().setReTries(10);
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void onConnectionStarted(@NonNull String url) {
    }

    @Override
    public void onConnectionSuccess() {
    }

    @Override
    public void onConnectionFailed(@NonNull final String reason) {
        if (rtmpCamera1.getStreamClient().reTry(5000, reason, null)) {

        } else {
            rtmpCamera1.stopStream();
        }
    }

    @Override
    public void onNewBitrate(final long bitrate) {

    }

    @Override
    public void onDisconnect() {
    }

    @Override
    public void onAuthError() {
        rtmpCamera1.stopStream();
    }

    @Override
    public void onAuthSuccess() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_stop) {
            if (!rtmpCamera1.isStreaming()) {
                if (rtmpCamera1.isRecording()
                        || rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {
                    button.setBackgroundResource(R.drawable.start_stop_recording_background);
                    rtmpCamera1.startStream("rtmp://a.rtmp.youtube.com/live2/r654-ca4h-jysb-p7d1-due5");
                } else {
                    Toast.makeText(this, "Error preparing stream, This device cant do it",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                button.setBackgroundResource(R.drawable.start_stop_default_background);
                rtmpCamera1.stopStream();
            }
        } else if (id == 1) {
            try {
                rtmpCamera1.switchCamera();
            } catch (CameraOpenException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        rtmpCamera1.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (rtmpCamera1.isStreaming()) {
            rtmpCamera1.stopStream();
            button.setBackgroundResource(R.drawable.start_stop_default_background);
        }
        rtmpCamera1.stopPreview();
    }
}
