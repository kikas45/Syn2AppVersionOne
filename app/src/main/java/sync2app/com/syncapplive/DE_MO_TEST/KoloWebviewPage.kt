package sync2app.com.syncapplive.DE_MO_TEST

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityKoloWebviewPageBinding
import java.io.File

class KoloWebviewPage : AppCompatActivity() {
    private lateinit var binding: ActivityKoloWebviewPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoloWebviewPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDownload.setOnClickListener {
           /// val url = "https://drive.google.com/uc?export=download&id=YOUR_FILE_ID"

            val url = "https://drive.google.com/uc?export=download&id=1LEFSA6RFiwLVZw2GlV8ASsTGogwIh_FA"

            initDownload(url)
        }
    }

    private fun initDownload(url: String) {
        val finalFolderPath = "Drive"
        val fileName = "App.zip"

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val folder = File(Environment.getExternalStorageDirectory().toString() + "/Download/${Constants.Syn2AppLive}/$finalFolderPath")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading ZIP file from Google Drive")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/${Constants.Syn2AppLive}/$finalFolderPath/$fileName")
        downloadManager.enqueue(request)


    }
}
