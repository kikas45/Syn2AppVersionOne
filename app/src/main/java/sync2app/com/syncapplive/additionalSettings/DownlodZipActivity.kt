package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sync2app.com.syncapplive.MyApplication
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.WebViewPage
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityDownlodPaggerBinding
import sync2app.com.syncapplive.databinding.LaucnOnlineDonloadPaggerBinding
import sync2app.com.syncapplive.databinding.ProgressDialogLayoutBinding
import java.io.File
import java.io.FileInputStream
import java.util.Objects
import java.util.zip.ZipInputStream


class DownlodZipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownlodPaggerBinding

    private var isValid = false


    var manager: DownloadManager? = null

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val myHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC,
            Context.MODE_PRIVATE
        )
    }


    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null


    private val sharedP: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS,
            Context.MODE_PRIVATE
        )
    }

    private var preferences: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n", "WakelockTimeout", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownlodPaggerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getState = sharedBiometric.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString()
        if (getState == Constants.ENABLE_LANDSCAPE_MODE){
            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            if (getState.isNullOrEmpty()){
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }else{
                requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }


        val get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding) ){
            loadBackGroundImage()
        }


        //add exception
        Methods.addExceptionHandler(this)

        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager!!.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YourApp::MyWakelockTag")
        wakeLock!!.acquire()

        manager = getApplicationContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager


        binding.apply {
            closeBs.setOnClickListener {
                second_cancel_download()
                startActivity(Intent(applicationContext, ReSyncActivity::class.java))
                finish()

            }




            binding.textLaunchApplication.setOnClickListener {

                showPopLauchOnline()
            }



            imagePauseDownload.setOnClickListener {
                pauseDownload()
                showToastMessage("Paused")
            }


            imageResumeDownload.setOnClickListener {
                resumeDownload()
                showToastMessage("Please wait")

            }


            textCancelBtn.setOnClickListener {
                second_cancel_download()
            }




            val getFolderClo = sharedP.getString("getFolderClo", "")
            val getFolderSubpath = sharedP.getString("getFolderSubpath", "")
            val Zip = sharedP.getString("Zip", "")
            val fileName = sharedP.getString("fileName", "")
            val Extracted = sharedP.getString("Extracted", "")


            val threeFolderPath = intent.getStringExtra("threeFolderPath")
            val baseUrl = intent.getStringExtra("baseUrl")

            if (threeFolderPath !=null &&baseUrl !=null){
                textTitleFileName.text = fileName.toString()
                //  textPathFolderName.text = "$getFolderClo/$getFolderSubpath/$Zip"
                textPathFolderName.text = "$getFolderClo/$getFolderSubpath"
            }

            textRetryBtn.setOnClickListener {
                showToastMessage("Please wait..")
                if (baseUrl != null && fileName != null){
                    download(baseUrl, getFolderClo.toString(), getFolderSubpath.toString(), Zip.toString(), fileName.toString(), Extracted.toString(), threeFolderPath.toString())
                }else{
                    //   onBackPressed()
                    startActivity(Intent(applicationContext, ReSyncActivity::class.java))
                    finish()
                }
            }


        }



    }

    private val runnable: Runnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            getDownloadStatus()
            myHandler.postDelayed(this, 500)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    fun getDownloadStatus() {
        try {

            val download_ref = sharedP.getLong(Constants.downloadKey, -15)
            val query = DownloadManager.Query()
            query.setFilterById(download_ref)
            val c =
                (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(
                    query
                )
            if (c!!.moveToFirst()) {
                @SuppressLint("Range") val bytes_downloaded =
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        .toLong()
                @SuppressLint("Range") val bytes_total =
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)).toLong()
                val dl_progress = (bytes_downloaded.toDouble() / bytes_total.toDouble() * 100f).toInt()

                binding.progressBarPref.setProgress(dl_progress)

                binding.downloadBytes.setText(bytesIntoHumanReadable(
                    bytes_downloaded.toString().toLong()
                ) + "/" + bytesIntoHumanReadable(bytes_total.toString().toLong())
                )


                if (c == null) {
                    binding.textView10.setText(statusMessage(c))
                } else {
                    c.moveToFirst()
                    binding.textView10.setText(statusMessage(c))
                }
            }
        } catch (ignored: java.lang.Exception) {
        }
    }

    @SuppressLint("InflateParams", "SuspiciousIndentation")
    private fun showPopLauchOnline() {
        val bindingCm: LaucnOnlineDonloadPaggerBinding =
            LaucnOnlineDonloadPaggerBinding.inflate(
                layoutInflater
            )
        val builder = AlertDialog.Builder(this@DownlodZipActivity)
        builder.setView(bindingCm.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)

        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }



        val consMainAlert_sub_layout = bindingCm.consMainAlertSubLayout
        val textLoading = bindingCm.textLoading
        val textLoading2 = bindingCm.textLoading2
        val textCancel = bindingCm.textCancel
        val textYesButton = bindingCm.textYesButton
        val divider27 = bindingCm.divider27
        val divider28 = bindingCm.divider28



        val preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textLoading2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            textCancel.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textCancel.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

            textYesButton.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textYesButton.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

            divider27.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.dark_light_gray_pop))
            divider28.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.dark_light_gray_pop))


        }



        bindingCm.apply {

            textCancel.setOnClickListener {
                alertDialog.dismiss()
            }


            textYesButton.setOnClickListener {
                val editor = sharedP.edit()
                editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")
                editor.apply()
                second_cancel_download2222()
                stratLauncOnline()
                alertDialog.dismiss()
            }


        }

        alertDialog.show()
    }






    @SuppressLint("Range", "SetTextI18n")
    private fun statusMessage(c: Cursor): String? {
        var msg: String
        when (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            DownloadManager.STATUS_PENDING -> {
                msg = "Pending.."
                binding.imagePauseDownload.visibility = View.VISIBLE
                binding.imageResumeDownload.visibility = View.INVISIBLE
                isValid = true
            }

            DownloadManager.STATUS_RUNNING -> {
                msg = "Downloading.."
                binding.imagePauseDownload.visibility = View.VISIBLE
                binding.imageResumeDownload.visibility = View.INVISIBLE
                //  binding.textContinuPassword222222.isEnabled = false

                isValid = true

            }

            DownloadManager.STATUS_PAUSED -> {
                // msg = "Resume"
                msg = "Paused"
                binding.imagePauseDownload.visibility = View.INVISIBLE
                binding.imageResumeDownload.visibility = View.VISIBLE
                isValid = true
            }

            DownloadManager.STATUS_SUCCESSFUL -> {
                msg = "File fully downloaded"
                binding.imagePauseDownload.visibility = View.INVISIBLE
                binding.imageResumeDownload.visibility = View.VISIBLE
                binding.imagePauseDownload.isEnabled = false
                binding.imageResumeDownload.isEnabled = false

                val get_value_if_Api_is_required = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "")

                if (isValid == true) {
                    isValid = false
                    showCustomProgressDialog("Please wait! \n Download in Progress")
                    handler.postDelayed(Runnable {
                        if (get_value_if_Api_is_required.equals(Constants.imagSwtichEnableSyncFromAPI)){
                            funUnZipFile()
                        }else{
                            stratMyACtivity()
                            showToastMessage("Api Completed")
                        }


                    }, 250)
                } else {
                    //  showToastMessage("Something went wrong")
                }


            }

            DownloadManager.STATUS_FAILED -> {
                msg = "Failed!, Retry.."
                isValid = false
            }

            else -> {
                msg = "failed! , try again.. "

            }
        }
        return msg
    }


    private fun showCustomProgressDialog(message: String) {
        try {
            val customProgressDialog = Dialog(this)
            val binding = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(this))
            customProgressDialog.setContentView(binding.root)
            customProgressDialog.setCancelable(true)
            customProgressDialog.setCanceledOnTouchOutside(false)
            customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding.textLoading.text = "$message"



            val consMainAlert_sub_layout = binding.consMainAlertSubLayout
            val textLoading = binding.textLoading
            val imgCloseDialog = binding.imgCloseDialog
            val imagSucessful = binding.imagSucessful
            val progressBar2 = binding.progressBar2



            preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            val preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )

            if (preferences.getBoolean("darktheme", false)) {
                consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

                textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

                val drawable_imgCloseDialog = ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
                drawable_imgCloseDialog?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                imgCloseDialog.setImageDrawable(drawable_imgCloseDialog)

                val drawable_imagSucessfulg = ContextCompat.getDrawable(applicationContext, R.drawable.ic_download_24)
                drawable_imagSucessfulg?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                imagSucessful.setImageDrawable(drawable_imagSucessfulg)

                val colorWhite = ContextCompat.getColor(applicationContext, R.color.white)
                progressBar2.indeterminateDrawable.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN)


            }





            customProgressDialog.show()
        } catch (_: Exception) {
        }
    }



    private fun bytesIntoHumanReadable(bytes: Long): String? {
        val kilobyte: Long = 1024
        val megabyte = kilobyte * 1024
        val gigabyte = megabyte * 1024
        val terabyte = gigabyte * 1024
        return if (bytes >= 0 && bytes < kilobyte) {
            "$bytes B"
        } else if (bytes >= kilobyte && bytes < megabyte) {
            (bytes / kilobyte).toString() + " KB"
        } else if (bytes >= megabyte && bytes < gigabyte) {
            (bytes / megabyte).toString() + " MB"
        } else if (bytes >= gigabyte && bytes < terabyte) {
            (bytes / gigabyte).toString() + " GB"
        } else if (bytes >= terabyte) {
            (bytes / terabyte).toString() + " TB"
        } else {
            bytes.toString() + ""
        }
    }




    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }

            getDownloadStatus()


            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        } catch (ignored: java.lang.Exception) {
        }

        if (myHandler != null) {
            myHandler.postDelayed(runnable, 500)
        }


    }


    override fun onPause() {
        super.onPause()
        try {
            if (myHandler != null) {
                myHandler.removeCallbacks(runnable)
            }
        } catch (ignored: java.lang.Exception) {
        }
    }


    override fun onStop() {
        super.onStop()
        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }

            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


            if (wakeLock != null && wakeLock!!.isHeld) {
                wakeLock!!.release()
            }

        } catch (ignored: java.lang.Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }



            val get_value_if_Api_is_required = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "")
            if (get_value_if_Api_is_required.equals(Constants.imagSwtichEnableSyncFromAPI)){
                second_cancel_download2222()
            }

            if (wakeLock != null && wakeLock!!.isHeld) {
                wakeLock!!.release()
            }


        } catch (ignored: java.lang.Exception) {
        }
    }

    private fun second_cancel_download2222() {

        try {

            val download_ref: Long = sharedP.getLong(Constants.downloadKey, -15)

            val query = DownloadManager.Query()
            query.setFilterById(download_ref)
            val c =
                (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(query)
            if (c.moveToFirst()) {
                manager!!.remove(download_ref)
                val editor: SharedPreferences.Editor = sharedP.edit()
                editor.remove(Constants.downloadKey)
                editor.apply()
            }
        } catch (ignored: java.lang.Exception) {
        }
    }




    private fun funUnZipFile() {
        try {
            // Retrieve your parameters here...

            lifecycleScope.launch(Dispatchers.IO) {
                val getFolderClo = sharedP.getString(Constants.getFolderClo, "")
                val getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "")
                val Zip = sharedP.getString("Zip", "")
                val fileNamy = sharedP.getString("fileNamy", "")
                val Extracted = sharedP.getString(Constants.Extracted, "")


                val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
                val finalFolderPathDesired = "/$getFolderClo/$getFolderSubpath/$Extracted"
                //  val finalFolderPathDesired = "/$getFolderClo/$Extracted"



                val directoryPathString = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPath
                val destinationFolder = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPathDesired)

                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs()
                }

                val myFile = File(directoryPathString, File.separator + fileNamy)
                //  val myFile = File(directoryPathString, fileNamy)
                if (myFile.exists()) {
                    extractZip(myFile.toString(), destinationFolder.toString())
                } else {
                    withContext(Dispatchers.Main) {
                        showToastMessage("Zip file could not be found")
                    }
                }
            }
        } catch (_: Exception) {
            // Handle exceptions if necessary
        }
    }



    suspend fun extractZip(zipFilePath: String, destinationPath: String) {
        try {
            withContext(Dispatchers.Main) {
                showToastMessage(Constants.Extracting)
            }

            val buffer = ByteArray(1024)

            val zipInputStream = ZipInputStream(withContext(Dispatchers.IO) {
                FileInputStream(zipFilePath)
            })
            var entry = zipInputStream.nextEntry

            while (entry != null) {
                val entryFile = File(destinationPath, entry.name)
                val entryDir = entryFile.parent?.let { File(it) }

                if (!entryDir?.exists()!!) {
                    entryDir.mkdirs()
                }

                val outputStream = entryFile.outputStream()

                var len = withContext(Dispatchers.IO) {
                    zipInputStream.read(buffer)
                }
                while (len > 0) {
                    withContext(Dispatchers.IO) {
                        outputStream.write(buffer, 0, len)
                    }
                    len = withContext(Dispatchers.IO) {
                        zipInputStream.read(buffer)
                    }
                }

                withContext(Dispatchers.IO) {
                    outputStream.close()
                }
                withContext(Dispatchers.IO) {
                    zipInputStream.closeEntry()
                }
                entry = zipInputStream.nextEntry


                // Notify MediaScanner about the extracted file
                MediaScannerConnection.scanFile(
                    applicationContext,
                    arrayOf(entryFile.absolutePath),
                    null
                ) { path, uri ->
                    Log.i("ExternalStorage", "Scanned $path:")
                    Log.i("ExternalStorage", "-> uri=$uri")
                }

            }

            withContext(Dispatchers.IO) {
                zipInputStream.close()
            }

            withContext(Dispatchers.Main) {
                stratMyACtivity()
                showToastMessage(Constants.media_ready)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                showToastMessage(Constants.Error_during_zip_extraction)
                stratMyACtivity()
            }
        }
    }




    private fun stratMyACtivity() {
        try {

            handler.postDelayed(Runnable {

                val getFolderClo = sharedP.getString("getFolderClo", "")
                val getFolderSubpath = sharedP.getString("getFolderSubpath", "")
                val Zip = sharedP.getString("Zip", "")
                val fileName = sharedP.getString("fileName", "")
                val Extracted = sharedP.getString("Extracted", "")

                val getUnzipFileFrom =  "/$getFolderClo/$getFolderSubpath/$Extracted"
                val intent = Intent(applicationContext, WebViewPage::class.java)
                intent.putExtra("getUnzipFileFrom", getUnzipFileFrom)

                startActivity(intent)
                finish()
            }, 6000)


        }catch (_:Exception){}
    }

    @SuppressLint("SuspiciousIndentation")
    private fun stratLauncOnline() {
        try {


            val getFolderClo = sharedP.getString("getFolderClo", "")
            val getFolderSubpath = sharedP.getString("getFolderSubpath", "")

            val editor = sharedP.edit()


            editor.putString(Constants.getFolderClo, getFolderClo)
            editor.putString(Constants.getFolderSubpath, getFolderSubpath)
            editor.apply()

            val imagSwtichEnableManualOrNot = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")
            if (imagSwtichEnableManualOrNot.equals(Constants.imagSwtichEnableManualOrNot)) {
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online_Manual_Index)
                editText88.apply()
            }else{
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online)
                editText88.apply()
            }



            val intent = Intent(applicationContext, WebViewPage::class.java)
            startActivity(intent)
            finish()

        }catch (_:Exception){}
    }


    private fun showToastMessage(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }


    private fun pauseDownload(): Boolean {
        var updatedRow = 0
        val contentValues = ContentValues()
        contentValues.put("control", 1)
        try {
            val fileName = sharedP.getString("fileName", "")
            updatedRow = Objects.requireNonNull<Context>(applicationContext).contentResolver.update(
                Uri.parse("content://downloads/my_downloads"),
                contentValues,
                "title=?",
                arrayOf<String>(fileName.toString())
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0 < updatedRow
    }


    private fun resumeDownload(): Boolean {
        var updatedRow = 0
        val contentValues = ContentValues()
        contentValues.put("control", 0)
        try {
            val fileName = sharedP.getString("fileName", "")
            updatedRow = Objects.requireNonNull<Context>(applicationContext).contentResolver.update(
                Uri.parse("content://downloads/my_downloads"),
                contentValues,
                "title=?",
                arrayOf<String>(fileName.toString())
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0 < updatedRow
    }


    private fun second_cancel_download() {
        try {

            val download_ref: Long = sharedP.getLong(Constants.downloadKey, -15)

            val getFolderClo = sharedP.getString("getFolderClo", "")
            val getFolderSubpath = sharedP.getString("getFolderSubpath", "")
            val Zip = sharedP.getString("Zip", "")
            val fileName = sharedP.getString("fileName", "")


            val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip/$fileName"

            val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPath

            val myFile = File(directoryPath, fileName.toString())
            delete(myFile)


            if (download_ref !=- 15L){
                val query = DownloadManager.Query()
                query.setFilterById(download_ref)
                val c =
                    (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(query)
                if (c.moveToFirst()) {
                    manager!!.remove(download_ref)
                    val editor: SharedPreferences.Editor = sharedP.edit()
                    editor.remove(Constants.downloadKey)
                    editor.apply()
                    onBackPressed()
                }

            }

        } catch (ignored: java.lang.Exception) {
        }
    }




    @RequiresApi(Build.VERSION_CODES.Q)
    private fun download(
        url: String,
        getFolderClo: String,
        getFolderSubpath: String,
        Zip: String,
        fileNamy: String,
        Extracted: String,
        threeFolderPath: String,
    ) {


        val DeleteFolderPath = "/$getFolderClo/$getFolderSubpath/"
        val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive$DeleteFolderPath"
        val file = File(directoryPath)
        delete(file)



        handler.postDelayed(Runnable {

            val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
            val Syn2AppLive = "Syn2AppLive"

            val editior = sharedP.edit()
            editior.putString(Constants.getFolderClo, getFolderClo)
            editior.putString(Constants.getFolderSubpath, getFolderSubpath)
            editior.putString("Zip", Zip)
            editior.putString("fileNamy", fileNamy)
            editior.putString("Extracted", Extracted)
            editior.putString(Constants.baseUrl, url)

            editior.remove(Constants.PASS_URL)
            editior.apply()


            val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            val folder = File(Environment.getExternalStorageDirectory().toString() + "/Download/$Syn2AppLive/$finalFolderPath")

            if (!folder.exists()) {
                folder.mkdirs()
            }

            val request = DownloadManager.Request(Uri.parse(url))
            //  request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle(fileNamy)
            request.allowScanningByMediaScanner()
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "/$Syn2AppLive/$finalFolderPath/$fileNamy"
            )
            val downloadReferenceMain = managerDownload.enqueue(request)

            val editor = sharedP.edit()
            editor.putLong(Constants.downloadKey, downloadReferenceMain)
            editor.apply()


        }, 1000)


    }


    fun delete(file: File): Boolean {
        if (file.isFile) {
            return file.delete()
        } else if (file.isDirectory) {
            for (subFile in Objects.requireNonNull(file.listFiles())) {
                if (!delete(subFile)) return false
            }
            return file.delete()
        }
        return false
    }




    override fun onBackPressed() {
        second_cancel_download()
        startActivity(Intent(applicationContext, ReSyncActivity::class.java))
        finish()

        super.onBackPressed()
    }

    private fun loadBackGroundImage() {

        val fileTypes = "app_background.png"
        val getFolderClo = sharedP.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "").toString()

        val pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
        val folder =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + pathFolder
        val file = File(folder, fileTypes)

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage)

        }

    }




}