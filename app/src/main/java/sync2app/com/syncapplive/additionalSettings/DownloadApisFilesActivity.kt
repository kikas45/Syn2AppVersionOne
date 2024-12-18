package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import sync2app.com.syncapplive.WebViewPage
import sync2app.com.syncapplive.MyApplication
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesApi
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesViewModel
import sync2app.com.syncapplive.additionalSettings.myCompleteDownload.DnApi
import sync2app.com.syncapplive.additionalSettings.myCompleteDownload.DnViewModel
import sync2app.com.syncapplive.additionalSettings.myCompleteDownload.SavedDownloadsAdapter
import sync2app.com.syncapplive.additionalSettings.myFailedDownloadfiles.DnFailedApi
import sync2app.com.syncapplive.additionalSettings.myFailedDownloadfiles.DnFailedViewModel
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkUrlExistence
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityDownloadTheApisBinding
import sync2app.com.syncapplive.databinding.CustomFailedDownloadsLayoutBinding
import sync2app.com.syncapplive.databinding.ProgressDialogLayoutBinding
import java.io.File
import java.util.Objects

class DownloadApisFilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadTheApisBinding
    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var countdownTimer: CountDownTimer? = null


    private lateinit var customProgressDialog: Dialog

    private var currentDownloadIndex = 0
    private var downloadedFilesCount = 0


    private val mfilesViewModel by viewModels<FilesViewModel>()

    private val dnViewModel by viewModels<DnViewModel>()

    private val dnFailedViewModel by viewModels<DnFailedViewModel>()

    private var isFailedDownload = false

    var manager: DownloadManager? = null

    private var totalFiles: Int = 0


    private val adapter by lazy {
        SavedDownloadsAdapter()
    }

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private val sharedP: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS,
            Context.MODE_PRIVATE
        )
    }

    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC,
            Context.MODE_PRIVATE
        )
    }


    private val myHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private var preferences: SharedPreferences? = null

    @SuppressLint("NotifyDataSetChanged", "WakelockTimeout", "UnspecifiedRegisterReceiverFlag",
        "SourceLockedOrientationActivity"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadTheApisBinding.inflate(layoutInflater)
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


        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding.apply {
            if (preferences!!.getBoolean("darktheme", false)) {

                // set windows
                // Set status bar color
                window?.statusBarColor = Color.parseColor("#171616")
                // Set navigation bar color
                window?.navigationBarColor = Color.parseColor("#171616")

                // Ensure the text and icons are white
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false


                parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))
                actionBarRoot.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))

                // set text view
                textTitle.setTextColor(resources.getColor(R.color.white))


                textDownloadSieze.setTextColor(resources.getColor(R.color.white))
                textPercentageCompleted.setTextColor(resources.getColor(R.color.white))
                textRemainging.setTextColor(resources.getColor(R.color.white))
                textCsvStatus.setTextColor(resources.getColor(R.color.white))


                // test connection buttons and connect buttons
                textCancelBtn.setTextColor(resources.getColor(R.color.white))
                textCancelBtn.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textLaunchApplication.setTextColor(resources.getColor(R.color.white))
                textLaunchApplication.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textRetryBtn.setTextColor(resources.getColor(R.color.white))
                textRetryBtn.setBackgroundResource(R.drawable.card_design_darktheme)

                //  round_edit_text_design_outline_dark_theme


                // fir back button
                val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                closeBs.setImageDrawable(drawable)



                //  for divider i..n
                divider21.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))


            }
        }







        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(downloadCompleteReceiver, filter)

        countdownTimer?.cancel()


        manager = getApplicationContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock =
            powerManager!!.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YourApp::MyWakelockTag")
        wakeLock!!.acquire()

        binding.closeBs.setOnClickListener {
            closeDownloadpage()
        }

        binding.textCancelBtn.setOnClickListener {
            closeDownloadpage()
        }

        binding.textLaunchApplication.setOnClickListener {
            stratMyACtivity()
        }

        //add exception
        Methods.addExceptionHandler(this)

        startMyCSVApiDownload()

        binding.textRetryBtn.setOnClickListener {

            if (isFailedDownload == true) {
                try {
                    if (downloadCompleteReceiver != null) {
                        unregisterReceiver(downloadCompleteReceiver)
                    }
                } catch (e: Exception) {
                }

                showCustomProgressDialog("Please wait!")
                mfilesViewModel.deleteAllFiles()
                remove_download_key()
                copyFilesToFailedDownloads()
                myHandler.postDelayed(Runnable {
                    reTryTheDownlaods()
                    customProgressDialog.cancel()
                }, 4000)

            } else {
                showToastMessage("Download in Progress..")
            }


        }



        binding.apply {
            handler.postDelayed(Runnable {
                recyclerApiDownloads.adapter = adapter
                recyclerApiDownloads.layoutManager = LinearLayoutManager(applicationContext)
                dnViewModel.readAllData.observe(this@DownloadApisFilesActivity, Observer { filesApi ->
                    adapter.setDataApi(filesApi)

                })
            }, 300)
        }

    }

    private fun reTryTheDownlaods() {


        remove_download_key()

        myHandler.postDelayed(Runnable {
            dnViewModel.deleteAllFiles()
            val intent = Intent(applicationContext, RetryApiDownloadActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
        try {
            customProgressDialog.cancel()
        } catch (e: Exception) {
        }

    }


    private fun startMyCSVApiDownload() {
        remove_download_key()
        binding.apply {
            val connectivityManager22: ConnectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo22: NetworkInfo? = connectivityManager22.activeNetworkInfo

            if (networkInfo22 != null && networkInfo22.isConnected) {
                val imagUsemanualOrnotuseManual =
                    sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")

                if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {
                    handler.postDelayed(Runnable {

                        val getSavedEditTextInputSynUrlZip = sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()

                        if (getSavedEditTextInputSynUrlZip.contains("/Start/start1.csv")) {
                            myHandler.postDelayed(runnableManual, 500)

                        } else if (getSavedEditTextInputSynUrlZip.contains("/Api/update1.csv")) {
                            myHandler.postDelayed(runnableManual, 500)

                        } else {
                            // showToastMessage("Something went wrong, System Could not locate CSV from this Location")
                            binding.textCsvStatus.text = Constants.Error_CSv_Message

                        }

                    }, 1000)

                } else {

                    handler.postDelayed(Runnable {
                        myHandler.postDelayed(runnableGetApiStart, 500)

                    }, 1000)

                }


            } else {
                Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT)
                    .show()
                binding.textCsvStatus.text = "Poor Internet, Retry download";
                isFailedDownload = true

                handler.postDelayed(Runnable {
                    recreate()
                }, 3000)

            }

        }
    }


    private fun closeDownloadpage() {

        myHandler.postDelayed(Runnable {
            dnViewModel.deleteAllFiles()
            mfilesViewModel.deleteAllFiles()
            dnFailedViewModel.deleteAllFiles()

            val intent = Intent(applicationContext, ReSyncActivity::class.java)
            startActivity(intent)
            finishAffinity()

        }, 500)
        remove_download_key()

        try {
            customProgressDialog.cancel()
        } catch (e: Exception) {
        }
    }


    private val runnableGetApiStart: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            dnViewModel.deleteAllFiles()
            mfilesViewModel.readAllData.observe(this@DownloadApisFilesActivity, Observer { files ->
                if (files.isNotEmpty()) {

                    handler.postDelayed(Runnable {
                        binding.textRemainging.text = "0 / ${files.size}   Files Downloaded"
                        totalFiles = files.size.toInt()
                        binding.textCsvStatus.visibility = View.GONE
                        binding.textPercentageCompleted.visibility = View.VISIBLE
                        downloadSequentially(files)

                    }, 500)

                } else {
                    // showToastMessage("No files found")
                }
            })
        }

    }


    private val runnableManual: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            dnViewModel.deleteAllFiles()
            mfilesViewModel.readAllData.observe(this@DownloadApisFilesActivity, Observer { files ->
                if (files.isNotEmpty()) {

                    handler.postDelayed(Runnable {
                        binding.textRemainging.text = "0 / ${files.size}   Files Downloaded"
                        totalFiles = files.size.toInt()
                        binding.textCsvStatus.visibility = View.GONE
                        binding.textPercentageCompleted.visibility = View.VISIBLE
                        downloadSequentiallyManually(files)

                    }, 500)


                } else {
                    // showToastMessage("No files found")
                }
            })
        }

    }


    @SuppressLint("SetTextI18n")
    private fun downloadSequentially(files: List<FilesApi>) {

        if (currentDownloadIndex < files.size) {
            val file = files[currentDownloadIndex]
            handler.postDelayed(Runnable {
                getZipDownloads(file.SN, file.FolderName, file.FileName)
            }, 1000)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun downloadSequentiallyManually(files: List<FilesApi>) {

        if (currentDownloadIndex < files.size) {
            val file = files[currentDownloadIndex]
            handler.postDelayed(Runnable {
                getZipDownloadsManually(file.SN, file.FolderName, file.FileName)

            }, 500)

        } else {
            //  showToastMessage("All files Downloaded")
        }
    }


    private val downloadCompleteReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                handler.postDelayed(Runnable {

                    val imagUsemanualOrnotuseManual =
                        sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")

                    if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {
                        pre_Laucnh_Files_For_Manual()

                    } else {
                        pre_Laucnh_Files()
                    }

                }, 500)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pre_Laucnh_Files() {
        currentDownloadIndex++
        downloadSequentially(mfilesViewModel.readAllData.value ?: emptyList())

        downloadedFilesCount++
        copyFilesToNewFolder(mfilesViewModel.readAllData.value ?: emptyList())


        val get_fileNumber = sharedP.getString(Constants.fileNumber, "").toString()
        val get_folderName = sharedP.getString(Constants.folderName, "").toString()
        val get_fileName = sharedP.getString(Constants.fileName, "").toString()

        binding.textRemainging.text = "$currentDownloadIndex / $totalFiles Files Downloaded"

        // Save the particular download after successful
        val dnApi = DnApi(
            SN = get_fileNumber,
            FolderName = get_folderName,
            FileName = get_fileName,
            Status = "true"
        )
        dnViewModel.addFiles(dnApi)

        // remove a successful the failed downloads
        val dnApiFailed = DnFailedApi(
            SN = get_fileNumber,
            FolderName = get_folderName,
            FileName = get_fileName,
            Status = "true"
        )
        dnFailedViewModel.deleteFiles(dnApiFailed)

    }

    @SuppressLint("SetTextI18n")
    private fun pre_Laucnh_Files_For_Manual() {
        currentDownloadIndex++
        downloadSequentiallyManually(mfilesViewModel.readAllData.value ?: emptyList())

        downloadedFilesCount++
        copyFilesToNewFolder(mfilesViewModel.readAllData.value ?: emptyList())


        val get_fileNumber = sharedP.getString(Constants.fileNumber, "").toString()
        val get_folderName = sharedP.getString(Constants.folderName, "").toString()
        val get_fileName = sharedP.getString(Constants.fileName, "").toString()

        binding.textRemainging.text = "$downloadedFilesCount / $totalFiles Files Downloaded"

        // Save the particular download after successful
        val dnApi = DnApi(
            SN = get_fileNumber,
            FolderName = get_folderName,
            FileName = get_fileName,
            Status = "true"
        )
        dnViewModel.addFiles(dnApi)

        // remove a successful the failed downloads
        val dnApiFailed = DnFailedApi(
            SN = get_fileNumber,
            FolderName = get_folderName,
            FileName = get_fileName,
            Status = "true"
        )
        dnFailedViewModel.deleteFiles(dnApiFailed)


    }


    @SuppressLint("SetTextI18n")
    private fun getZipDownloads(sn: String, folderName: String, fileName: String) {


        val getFolderClo = sharedP.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "").toString()
        val get_ModifiedUrl = sharedP.getString(Constants.get_ModifiedUrl, "").toString()


        val Syn2AppLive = Constants.Syn2AppLive
        val saveMyFileToStorage = "/$Syn2AppLive/$getFolderClo/$getFolderSubpath/$folderName"


        // delete existing files first
        val directoryPath =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/" + saveMyFileToStorage
        val myFile = File(directoryPath, fileName)
        delete(myFile)

        val getFileUrl = "$get_ModifiedUrl/$getFolderClo/$getFolderSubpath/$folderName/$fileName"

        lifecycleScope.launch {
            try {
                val result = checkUrlExistence(getFileUrl)
                if (result) {
                    handler.postDelayed(Runnable {
                        binding.textRemainging.visibility = View.VISIBLE
                        binding.textPercentageCompleted.visibility = View.VISIBLE

                        ///   val fileNum = sn.toInt().toDouble()
                        val fileNum = currentDownloadIndex.toDouble()
                        val totalPercentage = ((fileNum / totalFiles.toDouble()) * 100).toInt()

                        binding.textPercentageCompleted.text = "$totalPercentage% Complete"

                        val editior = sharedP.edit()
                        editior.putString(Constants.fileNumber, sn)
                        editior.putString(Constants.folderName, folderName)
                        editior.putString(Constants.fileName, fileName)
                        editior.apply()


                        val dir = File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            saveMyFileToStorage
                        )
                        if (!dir.exists()) {
                            dir.mkdirs()
                        }


                        val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

                        // save files to this folder
                        val folder = File(
                            Environment.getExternalStorageDirectory()
                                .toString() + "/Download/$saveMyFileToStorage"
                        )

                        if (!folder.exists()) {
                            folder.mkdirs()
                        }

                        val request = DownloadManager.Request(Uri.parse(getFileUrl))
                        request.setTitle(fileName)
                        request.allowScanningByMediaScanner()
                        request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS, "/$saveMyFileToStorage/$fileName"
                        )
                        val downloadReferenceMain = managerDownload.enqueue(request)

                        val editor = sharedP.edit()
                        editor.putLong(Constants.downloadKey, downloadReferenceMain)
                        editor.apply()

                    }, 300)
                } else {
                    binding.textRemainging.visibility = View.VISIBLE
                    binding.textPercentageCompleted.visibility = View.VISIBLE

                    val editior = sharedP.edit()
                    editior.putString(Constants.fileNumber, sn)
                    editior.putString(Constants.folderName, folderName)
                    editior.putString(Constants.fileName, fileName)
                    editior.apply()

                    checkWhatAreaToDownloadFrom(
                        currentDownloadIndex.toString(),
                        folderName,
                        fileName
                    )


                }
            } catch (e: Exception) {
            }

        }


    }

    private fun checkWhatAreaToDownloadFrom(sn: String, folderName: String, fileName: String) {


        val imagUsemanualOrnotuseManual =
            sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")

        if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {
            currentDownloadIndex++
            downloadSequentiallyManually(mfilesViewModel.readAllData.value ?: emptyList())

            downloadedFilesCount++
            copyFilesToNewFolder(mfilesViewModel.readAllData.value ?: emptyList())


            //   binding.textFileCounts.text = "$sn / "

            val dnApi = DnApi(
                SN = sn,
                FolderName = folderName,
                FileName = fileName,
                Status = "false"
            )
            dnViewModel.addFiles(dnApi)

            isFailedDownload = true


        } else {
            currentDownloadIndex++
            downloadSequentially(mfilesViewModel.readAllData.value ?: emptyList())


            downloadedFilesCount++
            copyFilesToNewFolder(mfilesViewModel.readAllData.value ?: emptyList())


            //   binding.textFileCounts.text = "$sn / "

            val dnApi = DnApi(
                SN = sn,
                FolderName = folderName,
                FileName = fileName,
                Status = "false"
            )
            dnViewModel.addFiles(dnApi)


            isFailedDownload = true

        }


    }


    private fun getZipDownloadsManually(sn: String, folderName: String, fileName: String) {
        val Syn2AppLive = Constants.Syn2AppLive
        val saveMyFileToStorage = "/$Syn2AppLive/CLO/MANUAL/DEMO/$folderName"

        val getSavedEditTextInputSynUrlZip =
            sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()

        var replacedUrl = getSavedEditTextInputSynUrlZip // Initialize it with original value


        if (getSavedEditTextInputSynUrlZip.contains("/Start/start1.csv")) {
            replacedUrl = getSavedEditTextInputSynUrlZip.replace(
                "/Start/start1.csv",
                "/$folderName/$fileName"
            )

        } else if (getSavedEditTextInputSynUrlZip.contains("/Api/update1.csv")) {
            replacedUrl = getSavedEditTextInputSynUrlZip.replace(
                "/Api/update1.csv", "/$folderName/$fileName"
            )
        } else {

            Log.d("getZipDownloadsManually", "Unable to replace this url")
        }


        // delete existing files first
        val directoryPath =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/" + saveMyFileToStorage
        val myFile = File(directoryPath, fileName)
        delete(myFile)


        lifecycleScope.launch {
            try {
                val result = checkUrlExistence(replacedUrl)
                if (result) {
                    handler.postDelayed(Runnable {
                        binding.textRemainging.visibility = View.VISIBLE
                        binding.textPercentageCompleted.visibility = View.VISIBLE

                        ///   val fileNum = sn.toInt().toDouble()
                        val fileNum = currentDownloadIndex.toDouble()
                        val totalPercentage = ((fileNum / totalFiles.toDouble()) * 100).toInt()

                        binding.textPercentageCompleted.text = "$totalPercentage% Complete"

                        val editior = sharedP.edit()
                        editior.putString(Constants.fileNumber, sn)
                        editior.putString(Constants.folderName, folderName)
                        editior.putString(Constants.fileName, fileName)
                        editior.apply()


                        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveMyFileToStorage
                        )
                        if (!dir.exists()) {
                            dir.mkdirs()
                        }


                        val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

                        // save files to this folder
                        val folder = File(
                            Environment.getExternalStorageDirectory()
                                .toString() + "/Download/$saveMyFileToStorage"
                        )

                        if (!folder.exists()) {
                            folder.mkdirs()
                        }

                        val request = DownloadManager.Request(Uri.parse(replacedUrl))
                        request.setTitle(fileName)
                        request.allowScanningByMediaScanner()
                        request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS, "/$saveMyFileToStorage/$fileName"
                        )
                        val downloadReferenceMain = managerDownload.enqueue(request)

                        val editor = sharedP.edit()
                        editor.putLong(Constants.downloadKey, downloadReferenceMain)
                        editor.apply()

                    }, 300)
                } else {
                    binding.textRemainging.visibility = View.VISIBLE
                    binding.textPercentageCompleted.visibility = View.VISIBLE

                    val editior = sharedP.edit()
                    editior.putString(Constants.fileNumber, sn)
                    editior.putString(Constants.folderName, folderName)
                    editior.putString(Constants.fileName, fileName)
                    editior.apply()

                    checkWhatAreaToDownloadFrom(
                        currentDownloadIndex.toString(),
                        folderName,
                        fileName
                    )


                }
            } catch (e: Exception) {
            }

        }


    }


    private val runnableGetDownloadProgress: Runnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            getDownloadStatus()
            myHandler.postDelayed(this, 500)
        }
    }


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
                val dl_progress =
                    (bytes_downloaded.toDouble() / bytes_total.toDouble() * 100f).toInt()

                binding.progressBarPref.setProgress(dl_progress)
                val get_fileName = sharedP.getString(Constants.fileName, "").toString()


                if (c == null) {
                    statusMessage(c, dl_progress, get_fileName)
                } else {
                    c.moveToFirst()
                    statusMessage(c, dl_progress, get_fileName)
                }


            }
        } catch (ignored: java.lang.Exception) {
        }
    }


    @SuppressLint("Range", "SetTextI18n")
    private fun statusMessage(c: Cursor, dl_progress: Int, get_fileName: String) {
        var msg: String
        when (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            DownloadManager.STATUS_PENDING -> {
                //  msg = "Pending.."
                // binding.textDownloadSieze.text = msg
                //  binding.textDownloadSieze.setTextColor(resources.getColor(R.color.black))
            }

            DownloadManager.STATUS_RUNNING -> {
                binding.textDownloadSieze.text = "$get_fileName : $dl_progress%"
                binding.textDownloadSieze.setTextColor(resources.getColor(R.color.black))
            }

            DownloadManager.STATUS_PAUSED -> {
                msg = "Download Paused, check internet"
                binding.textDownloadSieze.text = msg
                binding.textDownloadSieze.setTextColor(resources.getColor(R.color.black))

                val get_fileNumber = sharedP.getString(Constants.fileNumber, "").toString()
                val get_folderName = sharedP.getString(Constants.folderName, "").toString()
                val get_fileName = sharedP.getString(Constants.fileName, "").toString()

                checkWhatAreaToDownloadFrom(
                    currentDownloadIndex.toString(),
                    get_folderName,
                    get_fileName
                )

            }

            DownloadManager.STATUS_FAILED -> {
                try {
                    msg = "Download Failed!, Retry.."
                    binding.textDownloadSieze.text = msg
                    binding.textDownloadSieze.setTextColor(resources.getColor(R.color.red))

                    val get_fileNumber = sharedP.getString(Constants.fileNumber, "").toString()
                    val get_folderName = sharedP.getString(Constants.folderName, "").toString()
                    val get_fileName = sharedP.getString(Constants.fileName, "").toString()

                    checkWhatAreaToDownloadFrom(
                        currentDownloadIndex.toString(),
                        get_folderName,
                        get_fileName
                    )


                } catch (e: Exception) {

                }

            }

            DownloadManager.STATUS_SUCCESSFUL -> {
                binding.textDownloadSieze.text = "$get_fileName : 100%"

            }

        }

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


    private fun showToastMessage(s: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, "$s", Toast.LENGTH_SHORT).show()
        }
    }


    private fun remove_download_key() {

        try {

            val download_ref: Long = sharedP.getLong(Constants.downloadKey, -15)

            val query = DownloadManager.Query()
            query.setFilterById(download_ref)
            val c =
                (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(
                    query
                )
            if (c.moveToFirst()) {
                manager!!.remove(download_ref)
                val editor: SharedPreferences.Editor = sharedP.edit()
                editor.remove(Constants.downloadKey)
                editor.apply()
            }
        } catch (ignored: java.lang.Exception) {
        }
    }


    override fun onBackPressed() {
        closeDownloadpage()

    }


    @SuppressLint("WakelockTimeout")
    override fun onResume() {
        super.onResume()
        try {

            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnableGetDownloadProgress)
            }


            getDownloadStatus()

            if (myHandler != null) {
                myHandler.postDelayed(runnableGetDownloadProgress, 500)
            }

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (ignored: java.lang.Exception) {
        }

    }

    override fun onPause() {
        super.onPause()
        try {
            if (myHandler != null) {
                myHandler.removeCallbacks(runnableGetDownloadProgress)
            }
        } catch (ignored: java.lang.Exception) {
        }
    }

    override fun onStop() {
        super.onStop()
        try {

            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            if (wakeLock != null && wakeLock!!.isHeld) {
                wakeLock!!.release()
            }

            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnableGetDownloadProgress)
            }

        } catch (ignored: java.lang.Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {


            if (wakeLock != null && wakeLock!!.isHeld) {
                wakeLock!!.release()
            }

            if (downloadCompleteReceiver != null) {
                unregisterReceiver(downloadCompleteReceiver)
            }

            remove_download_key()

            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnableGetDownloadProgress)
            }


        } catch (ignored: java.lang.Exception) {
        }
    }

    private fun stratMyACtivity() {
        try {
            handler.postDelayed(Runnable {

                try {
                    customProgressDialog.cancel()
                } catch (e: Exception) {
                }

                val getFolderClo = sharedP.getString("getFolderClo", "")
                val getFolderSubpath = sharedP.getString("getFolderSubpath", "")
                val Extracted = sharedP.getString("Extracted", "")

                val getUnzipFileFrom = "/$getFolderClo/$getFolderSubpath/$Extracted"
                val intent = Intent(applicationContext, WebViewPage::class.java)
                intent.putExtra("getUnzipFileFrom", getUnzipFileFrom)

                startActivity(intent)
                finishAffinity()


            }, 6000)


        } catch (_: Exception) {
        }
    }

    private fun showCustomProgressDialog(message: String) {
        try {
            customProgressDialog = Dialog(this)
            val bindingDN = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(this))
            customProgressDialog.setContentView(bindingDN.root)
            customProgressDialog.setCancelable(true)
            customProgressDialog.setCanceledOnTouchOutside(false)
            customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            bindingDN.textLoading.text = message

            bindingDN.imgCloseDialog.visibility = View.GONE





            val consMainAlert_sub_layout = bindingDN.consMainAlertSubLayout
            val textLoading = bindingDN.textLoading
            val imgCloseDialog = bindingDN.imgCloseDialog
            val imagSucessful = bindingDN.imagSucessful
            val progressBar2 = bindingDN.progressBar2


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

    private fun showCustomErrorDownload(message: String) {

        val bindingCm: CustomFailedDownloadsLayoutBinding =
            CustomFailedDownloadsLayoutBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@DownloadApisFilesActivity)
        builder.setView(bindingCm.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(true)

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }


        bindingCm.apply {

            val get_RetryCount = sharedP.getString(Constants.RetryCount, "")

            if (!get_RetryCount.isNullOrEmpty()) {
                textDisplAyRetryCount.text = "$get_RetryCount/3"

            } else {
                textDisplAyRetryCount.text = "0/3"
            }

            if (get_RetryCount != "3") {

                textCountDown.visibility = View.VISIBLE
                val minutes = 1L
                val milliseconds = minutes * 10 * 1000 // Convert minutes to

                countdownTimer = object : CountDownTimer(milliseconds, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onFinish() {
                        try {
                            countdownTimer?.cancel()

                            copyFilesToFailedDownloads()
                            mfilesViewModel.deleteAllFiles()
                            showCustomProgressDialog("Please wait!")

                            val editor = sharedP.edit()
                            if (get_RetryCount.equals("0") || get_RetryCount.isNullOrEmpty()) {
                                editor.putString(Constants.RetryCount, "1")
                                editor.apply()
                            } else if (get_RetryCount.equals("1")) {
                                editor.putString(Constants.RetryCount, "2")
                                editor.apply()
                            } else if (get_RetryCount.equals("2")) {
                                editor.putString(Constants.RetryCount, "3")
                                editor.apply()
                            }

                            handler.postDelayed(Runnable {
                                reTryTheDownlaods()

                            }, 4000)


                        } catch (ignored: java.lang.Exception) {
                        }
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        try {

                            val totalSecondsRemaining = millisUntilFinished / 1000
                            var minutesUntilFinished = totalSecondsRemaining / 60
                            var remainingSeconds = totalSecondsRemaining % 60

                            // Adjusting minutes if seconds are in the range of 0-59
                            if (remainingSeconds == 0L && minutesUntilFinished > 0) {
                                minutesUntilFinished--
                                remainingSeconds = 59
                            }
                            val displayText =
                                String.format("CD: %d:%02d", minutesUntilFinished, remainingSeconds)
                            textCountDown.text = displayText

                        } catch (ignored: java.lang.Exception) {
                        }
                    }
                }
                countdownTimer?.start()


            }



            textLoading2.text = message.toString()

            textCancel.setOnClickListener {

                try {
                    copyFilesToFailedDownloads()
                    mfilesViewModel.deleteAllFiles()
                    showCustomProgressDialog("Please wait!")
                    remove_download_key()
                    handler.postDelayed(Runnable {
                        reTryTheDownlaods()
                    }, 4000)
                } catch (e: Exception) {
                }

                alertDialog.dismiss()
            }


            textYesButton.setOnClickListener {
                try {
                    if (downloadCompleteReceiver != null) {
                        unregisterReceiver(downloadCompleteReceiver)
                    }
                } catch (e: Exception) {
                }
                remove_download_key()
                showCustomProgressDialog("Please wait!")
                stratMyACtivity()
                alertDialog.dismiss()
            }

        }

        alertDialog.show()
    }


    private fun copyFilesToNewFolder(files: List<FilesApi>) {
        if (downloadedFilesCount == totalFiles) {

            binding.progressBarPref.progress = 100
            binding.textDownloadSieze.text = "Completed"


            if (isFailedDownload == true) {


                try {
                    if (downloadCompleteReceiver != null) {
                        unregisterReceiver(downloadCompleteReceiver)
                    }
                } catch (e: Exception) {
                }



                try {
                    dnFailedViewModel.readAllData.observe(
                        this@DownloadApisFilesActivity,
                        Observer { files ->
                            showCustomProgressDialog("Please wait!")
                            remove_download_key()
                            val message = "Unable to download \n ${files.size} Files"
                            myHandler.postDelayed(Runnable {
                                showCustomErrorDownload(message)
                                customProgressDialog.cancel()
                            }, 2000)

                        })
                } catch (e: Exception) {
                    finish()
                    recreate()
                }


            } else {
                val totalPercentage =  100
                binding.textPercentageCompleted.text = "$totalPercentage% Complete"

                showCustomProgressDialog("Please wait!")
                mfilesViewModel.deleteAllFiles()
                myHandler.postDelayed(Runnable {

                    stratMyACtivity()
                }, 2000)
            }

        }

    }

    private fun copyFilesToFailedDownloads() {
        dnFailedViewModel.getAllFiles().observe(this@DownloadApisFilesActivity) { filesList ->
            if (filesList.isNotEmpty()) {
                val dnFailedList = filesList.map { file ->
                    FilesApi(
                        id = file.id,
                        SN = file.SN,
                        FolderName = file.FolderName,
                        FileName = file.FileName,
                        Status = file.Status
                    )
                }
                mfilesViewModel.addMultipleFiles(dnFailedList)

            } else {

                myHandler.postDelayed(Runnable {
                    stratMyACtivity()
                }, 2000)
            }

        }
    }

}





