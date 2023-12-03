package net.bunnystream.android.ui.streaming

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View.OnClickListener
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import net.bunnystream.android.R
import net.bunnystream.android.databinding.ActivityStreamBinding

class StreamActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "StreamActivity"
        private const val PERMISSIONS_REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityStreamBinding

    private var camGranted: Boolean = false
    private var micGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.streamView.closeStreamClickListener = OnClickListener {
            finish()
        }

        binding.openSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.fromParts("package", packageName, null)
            startActivity(intent)
        }

        camGranted = hasPermission(Manifest.permission.CAMERA)
        micGranted = hasPermission(Manifest.permission.RECORD_AUDIO)

        if(camGranted && micGranted){
            binding.streamView.startPreview()
        } else {
            val permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            requestPermissions(
                permissions.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.streamView.isStreaming()) {
                    showStreamingActiveDialog()
                } else {
                    finish()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        val newCamGranted = hasPermission(Manifest.permission.CAMERA)
        val newMicGranted = hasPermission(Manifest.permission.RECORD_AUDIO)

        Log.d(TAG, "newCamGranted=$newCamGranted, camGranted=$camGranted newMicGranted=$newMicGranted micGranted=$micGranted")

        if(camGranted && newCamGranted && micGranted && newMicGranted) {
            return
        }

        if(newCamGranted && newMicGranted){
            binding.streamView.isVisible = true
            binding.streamView.startPreview()
            binding.noPermissionsInfo.isVisible = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult grantResults: $grantResults")

        if(requestCode == PERMISSIONS_REQUEST_CODE) {
            if(grantResults.filter { it == PackageManager.PERMISSION_GRANTED }.size == 2) {
                binding.noPermissionsInfo.isVisible = false
                binding.streamView.isVisible = true
            } else {
                binding.streamView.isVisible = false
                binding.noPermissionsInfo.isVisible = true
            }
        }
    }

    private fun hasPermission(permissionId: String): Boolean {
        val permission = ContextCompat.checkSelfPermission(this, permissionId)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun showStreamingActiveDialog(){
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_streaming_active)
            .setMessage(R.string.dialog_message_end_streaming)
            .setNegativeButton(R.string.dialog_no, null)
            .setPositiveButton(R.string.dialog_end_streaming) {_, _ ->
                binding.streamView.stopStreaming()
                finish()
            }
            .show()
    }
}