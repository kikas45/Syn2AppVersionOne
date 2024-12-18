package sync2app.com.syncapplive.additionalSettings


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
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
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sync2app.com.syncapplive.WebViewPage
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.SettingsActivityKT
import sync2app.com.syncapplive.additionalSettings.ApiUrls.ApiUrlViewModel
import sync2app.com.syncapplive.additionalSettings.ApiUrls.DomainUrl
import sync2app.com.syncapplive.additionalSettings.ApiUrls.SavedApiAdapter
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesApi
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesViewModel
import sync2app.com.syncapplive.additionalSettings.myCompleteDownload.DnViewModel
import sync2app.com.syncapplive.additionalSettings.myFailedDownloadfiles.DnFailedApi
import sync2app.com.syncapplive.additionalSettings.myFailedDownloadfiles.DnFailedViewModel
import sync2app.com.syncapplive.additionalSettings.utils.CSVDownloader
import sync2app.com.syncapplive.additionalSettings.savedDownloadHistory.SavedHistoryListAdapter
import sync2app.com.syncapplive.additionalSettings.savedDownloadHistory.User
import sync2app.com.syncapplive.additionalSettings.savedDownloadHistory.UserViewModel
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkUrlExistence
import sync2app.com.syncapplive.additionalSettings.urlchecks.isUrlValid
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivitySyncPowellBinding
import sync2app.com.syncapplive.databinding.ContinueWithConfigDownloadBinding
import sync2app.com.syncapplive.databinding.CustomApiHardCodedLayoutBinding
import sync2app.com.syncapplive.databinding.CustomApiUrlLayoutBinding
import sync2app.com.syncapplive.databinding.CustomContinueDownloadLayoutBinding
import sync2app.com.syncapplive.databinding.CustomDefinedTimeIntervalsBinding
import sync2app.com.syncapplive.databinding.CustomSavedHistoryLayoutBinding
import sync2app.com.syncapplive.databinding.CustomSelectLauncOrOfflinePopLayoutBinding
import sync2app.com.syncapplive.databinding.FinishWithConfigDownloadBinding
import sync2app.com.syncapplive.databinding.ProgressDialogLayoutBinding
import sync2app.com.syncapplive.databinding.SampleProgressConfigLayoutBinding
import java.io.File
import java.io.FileInputStream
import java.util.Objects
import java.util.zip.ZipInputStream


class ReSyncActivity : AppCompatActivity(), SavedHistoryListAdapter.OnItemClickListener,
    SavedApiAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySyncPowellBinding

    private val mUserViewModel by viewModels<UserViewModel>()

    private val mApiViewModel by viewModels<ApiUrlViewModel>()

    private lateinit var mFilesViewModel: FilesViewModel
    private lateinit var dnViewModel: DnViewModel
    private lateinit var dnFailedViewModel: DnFailedViewModel


    private val adapter by lazy {
        SavedHistoryListAdapter(this)
    }

    var isDownloadComplete = false
    private val adapterApi by lazy {
        SavedApiAdapter(this)
    }

    private  val TAG_RSYC = "ReSyncActivity"

    private val handlerMoveToWebviewPage: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private lateinit var customProgressDialog: Dialog
    private lateinit var customSavedDownloadDialog: Dialog
    private lateinit var custom_ApI_Dialog: Dialog


    private var fil_CLO = ""
    private var fil_DEMO = ""
    private var fil_baseUrl = ""
    private var fil_appIndex = ""

    private var getUrlBasedOnSpinnerText = ""
    private var API_Server = "API-Cloud App Server"
    private var CP_server = "CP-Cloud App Server"

    private var Minutes = " Minutes"


    private var getTimeDefined_Prime = ""
    private var timeMinuetes22 = 2L
    private var timeMinuetes55 = 5L
    private var timeMinuetes10 = 10L
    private var timeMinuetes15 = 15L
    private var timeMinuetes30 = 30L
    private var timeMinuetes60 = 60L
    private var timeMinuetes120 = 120L
    private var timeMinuetes180 = 180L
    private var timeMinuetes240 = 240L


    var hour = 0
    var min = 0


    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE
        )
    }


    private val myDownloadClass: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE
        )
    }


    private val simpleSavedPassword: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE
        )
    }

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }


    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null


    var isCompltedAndReady = false


    var manager: DownloadManager? = null

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private val sharedTVAPPModePreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_TV_APP_MODE, Context.MODE_PRIVATE
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n", "WakelockTimeout", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySyncPowellBinding.inflate(layoutInflater)
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

        handlerMoveToWebviewPage.postDelayed(Runnable {
            val intent = Intent(applicationContext, WebViewPage::class.java)
            startActivity(intent)
            finish()

        }, Constants.MOVE_BK_WEBVIEW_TIME)


        // write code for dark theme
        binding.apply {
            if (preferences.getBoolean("darktheme", false)) {

                // Set status bar color
                window?.statusBarColor = Color.parseColor("#171616")
                // Set navigation bar color
                window?.navigationBarColor = Color.parseColor("#171616")

                // Ensure the text and icons are white
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    false
                WindowInsetsControllerCompat(
                    window,
                    window.decorView
                ).isAppearanceLightNavigationBars = false


                parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))

                // set text view

                textTitle.setTextColor(resources.getColor(R.color.white))

                // text view in inner container
                texturlsSavedDownload.setTextColor(resources.getColor(R.color.white))
                texturlsViews.setTextColor(resources.getColor(R.color.white))

                textPartnerUrlLunch.setTextColor(resources.getColor(R.color.white))
                textUseManual.setTextColor(resources.getColor(R.color.white))
                textSynfromApiZip.setTextColor(resources.getColor(R.color.white))


                textView12.setTextColor(resources.getColor(R.color.white))
                textIntervalsSelect.setTextColor(resources.getColor(R.color.white))
                textDisplaytime.setTextColor(resources.getColor(R.color.white))



                textConfigfileOnline.setTextColor(resources.getColor(R.color.white))
                textLunchOnline.setTextColor(resources.getColor(R.color.white))
                textToogleMode.setTextColor(resources.getColor(R.color.white))
                textUseindexChangeOrTimestamp.setTextColor(resources.getColor(R.color.white))
                textSyncOnFileChangeIntervals.setTextColor(resources.getColor(R.color.white))

                // test connection buttons and connect buttons
                textTestConnectionAPPer.setTextColor(resources.getColor(R.color.white))
                textTestConnectionAPPer.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textDownloadZipSyncOrApiSyncNow.setTextColor(resources.getColor(R.color.white))
                textDownloadZipSyncOrApiSyncNow.setBackgroundResource(R.drawable.card_design_darktheme)

                //set edit text layout
                editTextSubPathFolder.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextSubPathFolder.setTextColor(resources.getColor(R.color.white))
                editTextSubPathFolder.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editTextCLOpath.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextCLOpath.setTextColor(resources.getColor(R.color.white))
                editTextCLOpath.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editTextInputIndexManual.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextInputIndexManual.setTextColor(resources.getColor(R.color.white))
                editTextInputIndexManual.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editTextInputSynUrlZip.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextInputSynUrlZip.setTextColor(resources.getColor(R.color.white))
                editTextInputSynUrlZip.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                textIntervalsSelect.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                textView12.setBackgroundResource(R.drawable.round_edit_text_design_theme_extra)
                textDisplaytime.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                constraintLayoutSavedDwonlaod.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                constraintLayout4.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)


                // set images
                // Assuming 'imageView' is your ImageView
                val drawable_imagSpannerSavedDownload =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_drop_down_24)
                drawable_imagSpannerSavedDownload?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                imagSpannerSavedDownload.setImageDrawable(drawable_imagSpannerSavedDownload)

                val drawable_imagSpanner =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_drop_down_24)
                drawable_imagSpanner?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                imagSpanner.setImageDrawable(drawable_imagSpanner)


                val drawable =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable?.setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
                closeBs.setImageDrawable(drawable)


                // set for switch layouts


                // Define colors for different states
                val thumbColorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        Color.LTGRAY,  // Color when checked
                        Color.LTGRAY   // Color when unchecked
                    )
                )

                val trackColorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_enabled),
                        intArrayOf(-android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        Color.DKGRAY,  // Color when enabled
                        Color.LTGRAY   // Color when disabled
                    )
                )

                // Apply the color state lists
                imagSwtichPartnerUrl.thumbTintList = thumbColorStateList
                imagSwtichPartnerUrl.trackTintList = trackColorStateList

                imagSwtichEnableManualOrNot.thumbTintList = thumbColorStateList
                imagSwtichEnableManualOrNot.trackTintList = trackColorStateList

                imagSwtichEnableSyncFromAPI.thumbTintList = thumbColorStateList
                imagSwtichEnableSyncFromAPI.trackTintList = trackColorStateList


                imagSwtichUseIndexCahngeOrTimeStamp.thumbTintList = thumbColorStateList
                imagSwtichUseIndexCahngeOrTimeStamp.trackTintList = trackColorStateList

                imagSwtichEnableSyncOnFilecahnge.thumbTintList = thumbColorStateList
                imagSwtichEnableSyncOnFilecahnge.trackTintList = trackColorStateList

                imagSwtichEnableConfigFileOnline.thumbTintList = thumbColorStateList
                imagSwtichEnableConfigFileOnline.trackTintList = trackColorStateList


                imagSwtichEnableLaucngOnline.thumbTintList = thumbColorStateList
                imagSwtichEnableLaucngOnline.trackTintList = trackColorStateList

                imagSwtichEnablEnableToggleOrNot.thumbTintList = thumbColorStateList
                imagSwtichEnablEnableToggleOrNot.trackTintList = trackColorStateList


                //  for divider i..n
                divider21.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider10.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider27.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider31.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider30.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider2756766.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider13.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider26.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider15565651.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider11.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )
                divider7.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_gray
                    )
                )


            }
        }




        mFilesViewModel = FilesViewModel(application)
        dnViewModel = DnViewModel(application)
        dnFailedViewModel = DnFailedViewModel(application)

        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager!!.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YourApp::MyWakelockTag")
        wakeLock!!.acquire()


        manager = applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?

        //add exception
        Methods.addExceptionHandler(this)


        val get_imgToggleImageBackground =
            sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(
                Constants.imageUseBranding
            )
        ) {
            loadBackGroundImage()
        }




        binding.apply {

            val getSavedCLOImPutFiled =
                myDownloadClass.getString(Constants.getSavedCLOImPutFiled, "").toString()
            val getSaveSubFolderInPutFiled =
                myDownloadClass.getString(Constants.getSaveSubFolderInPutFiled, "").toString()
            val getSavedEditTextInputSynUrlZip =
                myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()

            val getSaved_manaul_index_edit_url_Input = myDownloadClass.getString(Constants.getSaved_manaul_index_edit_url_Input, "").toString()

            if (!getSavedCLOImPutFiled.isNullOrEmpty()) {
                editTextCLOpath.setText(getSavedCLOImPutFiled)
            }

            if (!getSaveSubFolderInPutFiled.isNullOrEmpty()) {
                editTextSubPathFolder.setText(getSaveSubFolderInPutFiled)
            }


            if (!getSavedEditTextInputSynUrlZip.isNullOrEmpty()) {
                editTextInputSynUrlZip.setText(getSavedEditTextInputSynUrlZip)
            }

            if (!getSaved_manaul_index_edit_url_Input.isNullOrEmpty()) {
                editTextInputIndexManual.setText(getSaved_manaul_index_edit_url_Input)
            }

            initViewTooggle()


            // clear the download retry count
            val editor = myDownloadClass.edit()
            editor.remove(Constants.RetryCount)
            editor.apply()

            textTestConnectionAPPer.setOnClickListener {

                val editorTVMODE = sharedTVAPPModePreferences.edit()
                editorTVMODE. putString(Constants.installTVModeForFirstTime, Constants.installTVModeForFirstTime)
                editorTVMODE.apply()

                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                hideKeyBoard(binding.editTextInputSynUrlZip)
                try {

                    testConnectionSetup()

                } catch (_: Exception) {
                }


            }



            textDownloadZipSyncOrApiSyncNow.setOnClickListener {

                val editorTVMODE = sharedTVAPPModePreferences.edit()
                editorTVMODE. putString(Constants.installTVModeForFirstTime, Constants.installTVModeForFirstTime)
                editorTVMODE.apply()


                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                try {

                    lifecycleScope.launch(Dispatchers.IO) {
                        second_cancel_download()
                    }

                    hideKeyBoard(binding.editTextInputSynUrlZip)

                    handler.postDelayed(Runnable {

                        testAndDownLoadZipConnection()

                    }, 300)


                } catch (_: Exception) {
                }


            }

            textLauncheSaveDownload.setOnClickListener {
                val editorTVMODE = sharedTVAPPModePreferences.edit()
                editorTVMODE. putString(Constants.installTVModeForFirstTime, Constants.installTVModeForFirstTime)
                editorTVMODE.apply()

                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                showPopForLaunch_Oblin_offline()
            }



            closeBs.setOnClickListener {

                try {
                    val editorTVMODE = sharedTVAPPModePreferences.edit()
                    editorTVMODE. putString(Constants.installTVModeForFirstTime, Constants.installTVModeForFirstTime)
                    editorTVMODE.apply()


                    if (handlerMoveToWebviewPage != null){
                        handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                    }

                    val getStateNaviagtion = sharedBiometric.getString(Constants.CALL_RE_SYNC_MANGER, "")

                    val get_navigationS2222 = sharedBiometric.getString(Constants.SAVE_NAVIGATION, "")


                    val editor = sharedBiometric.edit()
                    if (getStateNaviagtion.equals(Constants.CALL_RE_SYNC_MANGER)) {
                        editor.remove(Constants.CALL_RE_SYNC_MANGER)
                        editor.apply()
                        val intent = Intent(applicationContext, WebViewPage::class.java)
                        startActivity(intent)
                        finish()


                    } else {

                        if (get_navigationS2222.equals(Constants.SettingsPage)) {
                            val intent = Intent(applicationContext, SettingsActivityKT::class.java)
                            startActivity(intent)
                            finish()
                        } else if (get_navigationS2222.equals(Constants.AdditionNalPage)) {
                            val intent =
                                Intent(applicationContext, AdditionalSettingsActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else if (get_navigationS2222.equals(Constants.WebViewPage)) {
                            val intent = Intent(applicationContext, WebViewPage::class.java)
                            startActivity(intent)
                            finish()
                        }


                    }
                }catch (e:Exception){}

            }


        }


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun check_for_valid_confileUrl() {

        val sharedLicenseKeys = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, MODE_PRIVATE)

        val get_tMaster = sharedLicenseKeys.getString(Constants.get_editTextMaster, "").toString()
        val get_UserID = sharedLicenseKeys.getString(Constants.get_UserID, "").toString()
        val get_LicenseKey = sharedLicenseKeys.getString(Constants.get_LicenseKey, "").toString()

        val ServerUrl = "$get_tMaster/$get_UserID/$get_LicenseKey/Zip/Config.zip"


        if (isNetworkAvailable()) {
            lifecycleScope.launch {
                try {
                    val result = checkUrlExistence(ServerUrl)
                    if (result) {
                        showConfirmationDialog(get_UserID, get_LicenseKey, ServerUrl)
                    } else {

                        showPopsForMyConnectionTest("", "", Constants.Invalid_Config_Url)
                        customProgressDialog.dismiss()

                    }
                } finally {
//                    handler.postDelayed(Runnable {
//
//                    }, 900)
                }
            }
        } else {
            showToastMessage("No Internet Connection")
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showConfirmationDialog(
        get_UserID: String,
        get_LicenseKey: String,
        ServerUrl: String,
    ) {
        customProgressDialog.dismiss()

        val bindingCM: ContinueWithConfigDownloadBinding =
            ContinueWithConfigDownloadBinding.inflate(
                LayoutInflater.from(this)
            )
        val alertDialogBuilder = AlertDialog.Builder(this@ReSyncActivity)

        alertDialogBuilder.setView(bindingCM.root)
        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }


        val consMainAlert_sub_layout = bindingCM.consMainAlertSubLayout
        val textLoading = bindingCM.textLoading
        val textLoading2 = bindingCM.textLoading2
        val textCancel = bindingCM.textCancel
        val textUpdate = bindingCM.textUpdate


        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textCancel.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textLoading2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textUpdate.setTextColor(resources.getColor(R.color.dark_light_gray_pop))


            textCancel.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)
            textCancel.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout)

        }





        bindingCM.textCancel.setOnClickListener {

            if (handlerMoveToWebviewPage != null){
                handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
            }

            runOnUiThread {
                binding.imagSwtichEnableConfigFileOnline.isChecked = false
                isCompltedAndReady = false
                testAndDownLoadZipConnection()
            }

            alertDialog.dismiss()


        }

        bindingCM.textUpdate.setOnClickListener {
            showCustomDialog(get_UserID, get_LicenseKey, ServerUrl)
            alertDialog.dismiss()
        }


        alertDialog.show()


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showContinueDialog() {

        val bindingCM: FinishWithConfigDownloadBinding = FinishWithConfigDownloadBinding.inflate(
            LayoutInflater.from(this)
        )
        val alertDialogBuilder = AlertDialog.Builder(this@ReSyncActivity)

        alertDialogBuilder.setView(bindingCM.root)
        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }

        val consMainAlert_sub_layout = bindingCM.consMainAlertSubLayout
        val textLoading = bindingCM.textLoading
        val textLoading2 = bindingCM.textLoading2
        val textCancel = bindingCM.textCancel
        val text_download_sync = bindingCM.textDownloadSync


        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textLoading2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textCancel.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            text_download_sync.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            //  textLogoutButton.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);
            text_download_sync.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)
            textCancel.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout)

        }


        bindingCM.textCancel.setOnClickListener {
            alertDialog.dismiss()
        }



        bindingCM.textDownloadSync.setOnClickListener {
            if (handlerMoveToWebviewPage != null){
                handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
            }

            runOnUiThread {
                isCompltedAndReady = true
                testAndDownLoadZipConnection()
            }
            alertDialog.dismiss()

        }


        alertDialog.show()


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showCustomDialog(get_UserID: String, get_LicenseKey: String, ServerUrl: String) {
        val bindingCM: SampleProgressConfigLayoutBinding =
            SampleProgressConfigLayoutBinding.inflate(
                LayoutInflater.from(this)
            )
        val alertDialogBuilder = AlertDialog.Builder(this@ReSyncActivity)

        alertDialogBuilder.setView(bindingCM.root)
        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }


        val consMainAlert_sub_layout = bindingCM.consMainAlertSubLayout
        val textLoading = bindingCM.teextDisplaydownload
        val textCancel = bindingCM.textCancel


        val preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )

        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textCancel.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            //  textLogoutButton.setBackgroundResource(R.drawable.card_design_darktheme_outline_pop_layout);
            textCancel.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

        }




        bindingCM.textCancel.setOnClickListener {

            if (handlerMoveToWebviewPage != null){
                handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
            }

            alertDialog.dismiss()
            binding.imagSwtichEnableConfigFileOnline.isChecked = false
            binding.textConfigfileOnline.text = "Use online config"

            lifecycleScope.launch (Dispatchers.IO){
                cancel_config_download(get_UserID, get_LicenseKey, ServerUrl)
            }

            isDownloadComplete = true


        }


        lifecycleScope.launch (Dispatchers.IO){
            downloadConfiGFile(get_UserID, get_LicenseKey, ServerUrl, Constants.Config)
        }

        alertDialog.show()

        isDownloadComplete = false // Flag to track download completion

        val handler = Handler()
        Thread {
            try {
                while (!isDownloadComplete) {

                    runOnUiThread {
                        getDownloadStatus(
                            bindingCM.progressBarPref,
                            bindingCM.teextDisplaydownload,
                            get_UserID,
                            get_LicenseKey,
                            ServerUrl,
                            Constants.Config
                        )
                    }



                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                handler.post {
                    alertDialog.dismiss()
                }
            }
        }.start()


    }


    private fun cancel_config_download(
        get_UserID: String,
        get_LicenseKey: String,
        fileName: String,
    ) {
        try {

            val download_ref: Long = myDownloadClass.getLong(Constants.downloadKey, -15)

            val folderName = Constants.Config
            val Syn2AppLive = Constants.Syn2AppLive

            val DeleteFolderPath = "/$Syn2AppLive/$get_UserID/$get_LicenseKey/$folderName/$fileName"

            val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/$DeleteFolderPath"
            val file = File(directoryPath)
            delete(file)



            if (download_ref != -15L) {
                val query = DownloadManager.Query()
                query.setFilterById(download_ref)
                val c =
                    (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(
                        query
                    )
                if (c.moveToFirst()) {
                    manager!!.remove(download_ref)
                    val editor: SharedPreferences.Editor = myDownloadClass.edit()
                    editor.remove(Constants.downloadKey)
                    editor.apply()
                }

            }

        } catch (ignored: java.lang.Exception) {
        }
    }


    private fun downloadConfiGFile(get_UserID: String, get_LicenseKey: String, url: String, fileName: String, ) {

        val Syn2AppLive = Constants.Syn2AppLive

        val DeleteFolderPath = "/$Syn2AppLive/$get_UserID/$get_LicenseKey/$fileName"

        val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/$DeleteFolderPath"
        val file = File(directoryPath)
        delete(file)


        val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        //  request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(fileName)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, DeleteFolderPath
        )
        val downloadReferenceMain = managerDownload.enqueue(request)

        val editor = myDownloadClass.edit()
        editor.putLong(Constants.downloadKey, downloadReferenceMain)
        editor.apply()

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n", "Range")
    fun getDownloadStatus(
        progressBarPref: ProgressBar,
        textprogressPercentage: TextView,
        get_UserID: String,
        get_LicenseKey: String,
        url: String,
        fileName: String,
    ) {
        try {

            val download_ref = myDownloadClass.getLong(Constants.downloadKey, -15)
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
                progressBarPref.setProgress(dl_progress)


                // Calculate the percentage of completion
                val progressPercentage =
                    (bytes_downloaded.toFloat() / bytes_total.toFloat() * 100).toInt()
                textprogressPercentage.text =
                    "" + progressPercentage.toString() + "%  Zip Downloaded"


                if (c.moveToFirst()) {
                    val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {

                        runOnUiThread {
                            isDownloadComplete = true
                            funUnZipFile(get_UserID, get_LicenseKey, url, fileName)
                        }
                    }

                }

            }
        } catch (ignored: java.lang.Exception) {
        }
    }

    private fun funUnZipFile(
        get_UserID: String,
        get_LicenseKey: String,
        url: String,
        fileName: String,
    ) {
        try {

            showCustomProgressDialog("Please wait for files to unpack")

            lifecycleScope.launch(Dispatchers.IO) {

                val Syn2AppLive = Constants.Syn2AppLive

                val finalFolderPath = "/$get_UserID/$get_LicenseKey"

                val get_Clo = simpleSavedPassword.getString(Constants.get_UserID, "").toString()
                val get_DEmo = simpleSavedPassword.getString(Constants.get_LicenseKey, "").toString()

                val newConfigFolder = "/$get_Clo/$get_DEmo/App/"

                val directoryPathString = Environment.getExternalStorageDirectory().absolutePath + "/Download/$Syn2AppLive" + finalFolderPath
                val destinationFolder = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/$Syn2AppLive/" + newConfigFolder)

                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs()
                }

                val myFile = File(directoryPathString, File.separator + fileName)
                if (myFile.exists()) {
                    extractZip(myFile.toString(), destinationFolder.toString())
                } else {
                    withContext(Dispatchers.Main) {
                        showToastMessage("Zip file could not be found")
                    }
                }
            }
        } catch (_: Exception) {
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

                outputStream.close()
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry


                // Notify MediaScanner about the extracted file
                MediaScannerConnection.scanFile(applicationContext,
                    arrayOf(entryFile.absolutePath),
                    null,
                    object : MediaScannerConnection.OnScanCompletedListener {
                        override fun onScanCompleted(path: String?, uri: Uri?) {
                        }
                    })

            }

            zipInputStream.close()

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            runOnUiThread {
                handler.postDelayed(Runnable {
                    showContinueDialog()
                    customProgressDialog.cancel()
                }, 1000)
            }
        }


    }


    override fun onBackPressed() {

        try {
            val editorTVMODE = sharedTVAPPModePreferences.edit()
            editorTVMODE. putString(Constants.installTVModeForFirstTime, Constants.installTVModeForFirstTime)
            editorTVMODE.apply()

            val getStateNaviagtion = sharedBiometric.getString(Constants.CALL_RE_SYNC_MANGER, "").toString()
            val get_navigationS2222 = sharedBiometric.getString(Constants.SAVE_NAVIGATION, "").toString()

            val editor = sharedBiometric.edit()
            if (getStateNaviagtion.equals(Constants.CALL_RE_SYNC_MANGER)) {
                editor.remove(Constants.CALL_RE_SYNC_MANGER)
                editor.apply()
                val intent = Intent(applicationContext, WebViewPage::class.java)
                startActivity(intent)
                finish()
            } else {

                if (get_navigationS2222.equals(Constants.SettingsPage)) {
                    val intent = Intent(applicationContext, SettingsActivityKT::class.java)
                    startActivity(intent)
                    finish()
                } else if (get_navigationS2222.equals(Constants.AdditionNalPage)) {
                    val intent = Intent(applicationContext, AdditionalSettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (get_navigationS2222.equals(Constants.WebViewPage)) {
                    val intent = Intent(applicationContext, WebViewPage::class.java)
                    startActivity(intent)
                    finish()
                }


            }
        }catch (e:Exception){}


    }

    override fun onResume() {
        super.onResume()

        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        } catch (ignored: java.lang.Exception) {
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        try {

            if (handlerMoveToWebviewPage != null){
                handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
            }

            if (wakeLock != null && wakeLock!!.isHeld) {
                wakeLock!!.release()
            }

        } catch (_: Exception) {
        }
    }


    private fun showCustomProgressDialog(message: String) {
        try {
            customProgressDialog = Dialog(this)
            val binding = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(this))
            customProgressDialog.setContentView(binding.root)
            customProgressDialog.setCancelable(true)
            customProgressDialog.setCanceledOnTouchOutside(false)
            customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding.textLoading.text = message


            val consMainAlert_sub_layout = binding.consMainAlertSubLayout
            val textLoading = binding.textLoading
            val imgCloseDialog = binding.imgCloseDialog
            val imagSucessful = binding.imagSucessful
            val progressBar2 = binding.progressBar2



            if (preferences.getBoolean("darktheme", false)) {
                consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

                textLoading.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

                val drawable_imgCloseDialog =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
                drawable_imgCloseDialog?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                imgCloseDialog.setImageDrawable(drawable_imgCloseDialog)

                val drawable_imagSucessfulg =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_download_24)
                drawable_imagSucessfulg?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                imagSucessful.setImageDrawable(drawable_imagSucessfulg)

                val colorWhite = ContextCompat.getColor(applicationContext, R.color.white)
                progressBar2.indeterminateDrawable.setColorFilter(
                    colorWhite,
                    PorterDuff.Mode.SRC_IN
                )


            }



            binding.imgCloseDialog.setOnClickListener {
                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                customProgressDialog.cancel()
            }

            customProgressDialog.show()
        } catch (_: Exception) {
        }
    }


    private fun testConnectionSetup() {
        binding.apply {
            val getFolderClo = editTextCLOpath.text.toString().trim()
            val getFolderSubpath = editTextSubPathFolder.text.toString().trim()

            val editor = myDownloadClass.edit()
            if (isNetworkAvailable()) {
                if (!imagSwtichEnableManualOrNot.isChecked) {

                    if (imagSwtichPartnerUrl.isChecked) {

                        if (getUrlBasedOnSpinnerText.isNotEmpty()) {


                            when (getUrlBasedOnSpinnerText) {
                                CP_server -> {
                                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty()) {
                                        httpNetworkTester(getFolderClo, getFolderSubpath)
                                        editor.putString(
                                            Constants.getSavedCLOImPutFiled, getFolderClo
                                        )
                                        editor.putString(
                                            Constants.getSaveSubFolderInPutFiled, getFolderSubpath
                                        )
                                        editor.apply()

                                    } else {
                                        editTextCLOpath.error = "Input a valid path e.g CLO"
                                        editTextSubPathFolder.error =
                                            "Input a valid path e.g DE_MO_2021000"
                                        showToastMessage("Fields can not be empty")
                                    }
                                }

                                API_Server -> {
                                    showToastMessage("No Logic For API Server Yet")
                                }

                            }

                        } else {
                            showToastMessage("Select Partner Url")
                        }

                    } else {

                        // let consider Custom Domain was selected

                        val getFolderClo222 = binding.editTextCLOpath.text.toString().trim()
                        val getFolderSubpath22 =
                            binding.editTextSubPathFolder.text.toString().trim()


                        var Saved_Domains_Urls =
                            myDownloadClass.getString(Constants.Saved_Domains_Urls, "").toString()


                        if (Saved_Domains_Urls.isNotEmpty()) {

                            if (getFolderClo222.isNotEmpty() && getFolderSubpath22.isNotEmpty()) {
                                testConnectionSetup_API_Test(getFolderClo222, getFolderSubpath22)
                                editor.putString(Constants.getSavedCLOImPutFiled, getFolderClo222)
                                editor.putString(
                                    Constants.getSaveSubFolderInPutFiled, getFolderSubpath22
                                )
                                editor.apply()

                            } else {
                                editTextCLOpath.error = "Input a valid path e.g CLO"
                                editTextSubPathFolder.error = "Input a valid path e.g DE_MO_2021000"
                                showToastMessage("Fields can not be empty")
                            }


                        } else {
                            showToastMessage("Select Custom Domain")
                        }

                    }

                } else {

                    /// when the button is checked

                    // for maunal download
                    val editInputUrl = editTextInputSynUrlZip.text.toString().trim()
                    // to luanch a live url manual
                    val editInputAppIndex = editTextInputIndexManual.text.toString().trim()

                    if (isUrlValid(editInputUrl) && isUrlValid(editInputAppIndex)) {
                        httpNetSingleUrlTest(editInputUrl, editInputAppIndex)

                        editor.putString(Constants.getSavedEditTextInputSynUrlZip, editInputUrl)
                        editor.putString(
                            Constants.getSaved_manaul_index_edit_url_Input, editInputAppIndex
                        )
                        editor.apply()
                    } else {

                        if (!isUrlValid(editInputUrl)) {
                            binding.editTextInputSynUrlZip.error = "Invalid url format"
                        }

                        if (!isUrlValid(editInputAppIndex)) {
                            binding.editTextInputIndexManual.error = "Invalid url format"
                        }

                        showToastMessage("Invalid url format")
                    }


                }

            } else {
                showToastMessage("No Internet Connection")
            }


        }
    }


    private fun httpNetworkTester(getFolderClo: String, getFolderSubpath: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Testing connection")
            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {
                val baseUrl =
                    "${CP_AP_MASTER_DOMAIN}/$getFolderClo/$getFolderSubpath/Zip/App.zip"
                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Successful"
                            )

                            // save also to room data base
                            val user = User(
                                CLO = getFolderClo,
                                DEMO = getFolderSubpath,
                                EditUrl = "",
                                EditUrlIndex = ""
                            )
                            mUserViewModel.addUser(user)
                            customProgressDialog.dismiss()


                        } else {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Failed!"
                            )
                            customProgressDialog.dismiss()
                        }
                    } finally {
                        //   customProgressDialog.dismiss()

                    }
                }

            } else {


                val baseUrl = "${CP_AP_MASTER_DOMAIN}/$getFolderClo/$getFolderSubpath/App/index.html"

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Successful"
                            )

                            val user = User(
                                CLO = getFolderClo,
                                DEMO = getFolderSubpath,
                                EditUrl = "",
                                EditUrlIndex = ""
                            )
                            mUserViewModel.addUser(user)
                            customProgressDialog.dismiss()

                        } else {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Failed!"
                            )
                            customProgressDialog.dismiss()
                        }
                    } finally {
                        //  customProgressDialog.dismiss()
                    }
                }


            }


        }, 300)
    }


    private fun httpNetSingleUrlTest(editInputUrl: String, editInputAppIndex: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Testing connection")

            val lastString = editInputUrl.substringAfterLast("/")
            val fileNameWithoutExtension = lastString.substringBeforeLast(".")

            lifecycleScope.launch {
                try {
                    val result = checkUrlExistence(editInputUrl)
                    if (result) {
                        showPopsForMyConnectionTest(
                            "CLO", fileNameWithoutExtension, "Successful"
                        )

                        val user = User(
                            CLO = "",
                            DEMO = "",
                            EditUrl = editInputUrl,
                            EditUrlIndex = editInputAppIndex
                        )
                        mUserViewModel.addUser(user)
                        customProgressDialog.dismiss()

                    } else {

                        showPopsForMyConnectionTest("CLO", fileNameWithoutExtension, "Failed!")
                        customProgressDialog.dismiss()
                    }
                } finally {
                    //  customProgressDialog.dismiss()
                }
            }

        }, 300)
    }


    private fun initViewTooggle() {
        binding.apply {

            //for history
            constraintLayoutSavedDwonlaod.setOnClickListener {
                showSaveduserHistory()
                hideKeyBoard(binding.editTextInputSynUrlZip)
            }


            // for server
            //  getUrlBasedOnSpinnerText = CP_server   // it was hard coded before, but now saved
            constraintLayout4.setOnClickListener {
                hideKeyBoard(binding.editTextInputSynUrlZip)

                if (imagSwtichPartnerUrl.isChecked) {
                    serVerOptionDialog()

                } else {

                    show_API_Urls()
                }

            }


            //// inilaizing the Set Syn Timmer
            // for time intervals
            // getTimeDefined = timeMinuetesDefined

            initSyncTimmer()



            textIntervalsSelect.setOnClickListener {
                definedTimeIntervals()
            }


            textView12.setOnClickListener {
                //  setUpCustomeTimmer()
                showSyncDialog()
            }


            val imgLunchOnline = sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "")
            imagSwtichEnableLaucngOnline.isChecked = imgLunchOnline.equals(Constants.imgAllowLunchFromOnline)

            val editor333 = myDownloadClass.edit()

            if (imgLunchOnline.equals(Constants.imgAllowLunchFromOnline)) {
                textLunchOnline.text = "Launch online"
                editor333.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOnline)
                editor333.apply()


            } else {

                textLunchOnline.text = "Launch offline"
                editor333.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOffline)
                editor333.apply()
            }

            imagSwtichEnableLaucngOnline.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                try{
                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                val editor = sharedBiometric.edit()
                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (compoundButton.isChecked) {

                    editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")
                    editor.apply()

                    textLunchOnline.text = "Launch online"

                    editor333.putString(
                        Constants.Tapped_OnlineORoffline, Constants.tapped_launchOnline
                    )
                    editor333.apply()

                } else {

                    editor.remove("imgAllowLunchFromOnline")
                    editor.apply()

                    textLunchOnline.text = "Launch offline"

                    editor333.putString(
                        Constants.Tapped_OnlineORoffline, Constants.tapped_launchOffline
                    )
                    editor333.apply()

                }

            }catch (e:Exception){
            Log.d(TAG_RSYC, "initViewTooggle: ${e.message.toString()}")
        }


            }


            /// use first Sync  or Do not use First Sync

            // enable satrt file for first synct

            val startFileFirstSync = sharedBiometric.getString(Constants.imgStartFileFirstSync, "").toString()
            imgStartFileFirstSync.isChecked = startFileFirstSync.equals(Constants.imgStartFileFirstSync)


            if (startFileFirstSync.equals(Constants.imgStartFileFirstSync)) {
                textUseStartFile.text = "Use start file on"
            } else {
                textUseStartFile.text = "Use start file off"
            }


            imgStartFileFirstSync.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE

                try {
                    if (handlerMoveToWebviewPage != null){
                        handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                    }

                    val editor = sharedBiometric.edit()
                    if (compoundButton.isChecked) {
                        editor.putString(
                            Constants.imgStartFileFirstSync, Constants.imgStartFileFirstSync
                        )
                        editor.apply()
                        textUseStartFile.text = "Use start file on"

                    } else {

                        editor.remove(Constants.imgStartFileFirstSync)
                        editor.apply()
                        textUseStartFile.text = "Use start file off"

                    }
                }catch (e:Exception){
                    Log.d(TAG_RSYC, "initViewTooggle: ${e.message.toString()}")
                }
            }


            // set up toggle for index file change
            // set up toggle for index file change
            // set up toggle for index file change

            val get_imagSwtichUseIndexCahngeOrTimeStamp = sharedBiometric.getString(Constants.imagSwtichUseIndexCahngeOrTimeStamp, "").toString()
            imagSwtichUseIndexCahngeOrTimeStamp.isChecked =
                get_imagSwtichUseIndexCahngeOrTimeStamp.equals(Constants.imagSwtichUseIndexCahngeOrTimeStamp)


            if (get_imagSwtichUseIndexCahngeOrTimeStamp.equals(Constants.imagSwtichUseIndexCahngeOrTimeStamp)) {
                textUseindexChangeOrTimestamp.text = "Use Index Change"
            } else {
                textUseindexChangeOrTimestamp.text = "Use Time Stamp"
            }



            imagSwtichUseIndexCahngeOrTimeStamp.setOnCheckedChangeListener { compoundButton, isValued ->
             try {

                 val editor = sharedBiometric.edit()
                 if (compoundButton.isChecked) {
                     editor.putString(
                         Constants.imagSwtichUseIndexCahngeOrTimeStamp,
                         Constants.imagSwtichUseIndexCahngeOrTimeStamp
                     )
                     editor.apply()
                     textUseindexChangeOrTimestamp.setText("Use Index Change")

                 } else {

                     if (handlerMoveToWebviewPage != null){
                         handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                     }

                     editor.remove(Constants.imagSwtichUseIndexCahngeOrTimeStamp)
                     editor.apply()
                     textUseindexChangeOrTimestamp.text = "Use Time Stamp"

                     if (handlerMoveToWebviewPage != null){
                         handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                     }
                     hideKeyBoard(binding.editTextInputSynUrlZip)
                     val editor = sharedBiometric.edit()
                     if (compoundButton.isChecked) {
                         editor.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)
                         editor.apply()
                         textPartnerUrlLunch.setText("Select Partner Url")


                         val Saved_Parthner_Name111 = myDownloadClass.getString(Constants.Saved_Parthner_Name, "")
                         if (Saved_Parthner_Name111!!.isNotEmpty()) {
                             texturlsViews.setText(Saved_Parthner_Name111)
                             getUrlBasedOnSpinnerText = Saved_Parthner_Name111

                         } else {
                             texturlsViews.setText("Select Partner Url")
                         }


                     } else {

                         editor.remove(Constants.imagSwtichPartnerUrl)
                         editor.apply()
                         textPartnerUrlLunch.setText("Select Custom Domain")


                         val get_Saved_Domains_Name111 = myDownloadClass.getString(Constants.Saved_Domains_Name, "")
                         if (get_Saved_Domains_Name111!!.isNotEmpty()) {
                             texturlsViews.setText(get_Saved_Domains_Name111)

                         } else {
                             texturlsViews.setText("Select Custom Domain")
                         }

                     }

                 }
             }catch (_:Exception){}
            }

            // set the toggle by default

            handler.postDelayed(Runnable {

                val editor = sharedBiometric.edit()
                val get_check_if_index_chnage_is_enabled =
                    sharedBiometric.getString(Constants.check_if_index_chnage_is_enabled, "").toString()

                if (get_check_if_index_chnage_is_enabled.isNullOrEmpty()) {
                    imagSwtichUseIndexCahngeOrTimeStamp.isChecked = true
                    editor.putString(Constants.imagSwtichUseIndexCahngeOrTimeStamp, Constants.imagSwtichUseIndexCahngeOrTimeStamp)
                    editor.putString(Constants.check_if_index_chnage_is_enabled, Constants.check_if_index_chnage_is_enabled)
                    editor.apply()
                    textUseindexChangeOrTimestamp.setText("Use Index Change")

                }


            }, 300)


            // enable config
            // enable config
            // enable config

            val imgLCongigFile = sharedBiometric.getString(Constants.imagSwtichEnableConfigFileOnline, "").toString()
            imagSwtichEnableConfigFileOnline.isChecked = imgLCongigFile.equals(Constants.imagSwtichEnableConfigFileOnline)


            if (imgLCongigFile.equals(Constants.imagSwtichEnableConfigFileOnline)) {
                textConfigfileOnline.setText("Config File Offline")
            } else {
                textConfigfileOnline.setText("Config File Online")
            }


            imagSwtichEnableConfigFileOnline.setOnCheckedChangeListener { compoundButton, isValued ->
               try{
                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }


                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(Constants.imagSwtichEnableConfigFileOnline, "imagSwtichEnableConfigFileOnline")
                    editor.apply()
                    textConfigfileOnline.setText("Config File Offline")
                } else {

                    editor.remove("imagSwtichEnableConfigFileOnline")
                    editor.apply()

                    textConfigfileOnline.setText("Config File Online")
                }
               }catch (_:Exception){}
            }


            // enable Sync on File Change

            val imgEnableFileOnSyncChange = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "").toString()
            imagSwtichEnableSyncOnFilecahnge.isChecked = imgEnableFileOnSyncChange.equals(Constants.imagSwtichEnableSyncOnFilecahnge)


            if (imgEnableFileOnSyncChange.equals(Constants.imagSwtichEnableSyncOnFilecahnge)) {
                textSyncOnFileChangeIntervals.setText("Download on Intervals")
            } else {
                textSyncOnFileChangeIntervals.setText("Download on change")

            }





            imagSwtichEnableSyncOnFilecahnge.setOnCheckedChangeListener { compoundButton, isValued ->

                try {
                    if (handlerMoveToWebviewPage != null){
                        handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                    }

                    val editor = sharedBiometric.edit()
                    val editorDN = myDownloadClass.edit()
                    editorDN.remove(Constants.SynC_Status)
                    editorDN.remove(Constants.textDownladByes)
                    editorDN.remove(Constants.progressBarPref)
                    editorDN.remove(Constants.progressBarPref)
                    editorDN.remove(Constants.filesChange)
                    editorDN.remove(Constants.numberOfFiles)

                    editorDN.apply()



                    if (compoundButton.isChecked) {
                        editor.putString(Constants.imagSwtichEnableSyncOnFilecahnge, Constants.imagSwtichEnableSyncOnFilecahnge)

                        // editor.putString(Constants.showDownloadSyncStatus, "showDownloadSyncStatus")

                        editor.apply()
                        textSyncOnFileChangeIntervals.setText("Download on Intervals")


                        // if the user switch to use sync on change, then we need to manage with Api or Url Zip

                    } else {
                        // if the user switch to use sync on change, then we need to manage with Api or Url Zip

                        val editor22 = sharedBiometric.edit()
                        editor22.remove(Constants.imagSwtichEnableSyncOnFilecahnge)
                        editor22.apply()

                        textSyncOnFileChangeIntervals.setText("Download on change")




                    }
                }catch (_:Exception){}
            }


            // enable Toggle Mode

            val imgEnableToggleMode = sharedBiometric.getString(Constants.imagSwtichEnablEnableToggleOrNot, "").toString()
            imagSwtichEnablEnableToggleOrNot.isChecked = imgEnableToggleMode.equals(Constants.imagSwtichEnablEnableToggleOrNot)

            if (imgEnableToggleMode.equals(Constants.imagSwtichEnablEnableToggleOrNot)) {
                textToogleMode.setText("Disable Test Toggle Mode")
            } else {
                textToogleMode.setText("Enable Test Toggle Mode")
            }

            imagSwtichEnablEnableToggleOrNot.setOnCheckedChangeListener { compoundButton, isValued ->

                try {
                    if (handlerMoveToWebviewPage != null){
                        handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                    }
                    val editor = sharedBiometric.edit()
                    if (compoundButton.isChecked) {
                        editor.putString(
                            Constants.imagSwtichEnablEnableToggleOrNot,
                            Constants.imagSwtichEnablEnableToggleOrNot
                        )
                        editor.apply()
                        textToogleMode.setText("Disable Test Toggle Mode")
                    } else {

                        editor.remove(Constants.imagSwtichEnablEnableToggleOrNot)
                        editor.apply()

                        textToogleMode.setText("Enable Test Toggle Mode")
                    }
                }catch (e:Exception){}
            }






            funManulOrNotInteView()

            imageEnablePartherOrmasterDomain()


            val get_imagSwtichEnableSyncFromAPI = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "").toString()
            imagSwtichEnableSyncFromAPI.isChecked = get_imagSwtichEnableSyncFromAPI.equals(Constants.imagSwtichEnableSyncFromAPI)

            if (!preferences!!.getBoolean("darktheme", false)) {
                if (get_imagSwtichEnableSyncFromAPI.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                    textSynfromApiZip.text = "Use ZIP Sync"
                    editTextInputSynUrlZip.setHint("Input url  ZIP Sync")
                    textDownloadZipSyncOrApiSyncNow.text = "Connect ZIP Sync"


                    textDownloadZipSyncOrApiSyncNow.setBackgroundResource(R.drawable.card_design_buy_gift_card)
                    textDownloadZipSyncOrApiSyncNow.setTextColor(
                        ContextCompat.getColor(
                            applicationContext, R.color.white
                        )
                    )


                } else {

                    textSynfromApiZip.text = "Use API Sync"
                    textDownloadZipSyncOrApiSyncNow.text = "Connect API Sync"
                    editTextInputSynUrlZip.setHint("Input url  API Sync")

                    textDownloadZipSyncOrApiSyncNow.setBackgroundResource(R.drawable.light_background_blue_color)
                    textDownloadZipSyncOrApiSyncNow.setTextColor(
                        ContextCompat.getColor(
                            applicationContext, R.color.deep_blue
                        )
                    )


                }

            }



            imagSwtichEnableSyncFromAPI.setOnCheckedChangeListener { compoundButton, isValued ->

               try {
                   if (handlerMoveToWebviewPage != null){
                       handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                   }

                   val editorDN = myDownloadClass.edit()
                   editorDN.remove(Constants.SynC_Status)
                   editorDN.remove(Constants.textDownladByes)
                   editorDN.remove(Constants.progressBarPref)
                   editorDN.remove(Constants.progressBarPref)
                   editorDN.remove(Constants.filesChange)
                   editorDN.remove(Constants.numberOfFiles)

                   editorDN.apply()


                   val editor = sharedBiometric.edit()
                   hideKeyBoard(binding.editTextInputSynUrlZip)

                   if (compoundButton.isChecked) {
                       editor.putString(
                           Constants.imagSwtichEnableSyncFromAPI,
                           Constants.imagSwtichEnableSyncFromAPI
                       )
                       editor.apply()
                       textSynfromApiZip.setText("Use ZIP Sync")
                       editTextInputSynUrlZip.setHint("Input url  ZIP Sync")
                       textDownloadZipSyncOrApiSyncNow.setText("Connect ZIP Sync")


                       if (!preferences.getBoolean("darktheme", false)) {
                           textDownloadZipSyncOrApiSyncNow.setBackgroundResource(R.drawable.card_design_buy_gift_card)
                           textDownloadZipSyncOrApiSyncNow.setTextColor(
                               ContextCompat.getColor(
                                   applicationContext, R.color.white
                               )
                           )

                       }




                   } else {


                       editor.remove(Constants.imagSwtichEnableSyncFromAPI)
                       editor.apply()

                       textSynfromApiZip.setText("Use API Sync")
                       textDownloadZipSyncOrApiSyncNow.setText("Connect API Sync")
                       editTextInputSynUrlZip.setHint("Input url  API Sync")

                       if (!preferences.getBoolean("darktheme", false)) {

                           textDownloadZipSyncOrApiSyncNow.setBackgroundResource(R.drawable.light_background_blue_color)
                           textDownloadZipSyncOrApiSyncNow.setTextColor(
                               ContextCompat.getColor(
                                   applicationContext, R.color.deep_blue
                               )
                           )
                       }


                   }
               }catch (_:Exception){}

            }


        }


    }

    private fun imageEnablePartherOrmasterDomain() {

        binding.apply {
            //// logic for Select Partner Url

            val imagPartnerurl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()
            imagSwtichPartnerUrl.isChecked = imagPartnerurl.equals(Constants.imagSwtichPartnerUrl)

            val get_Saved_Domains_Name = myDownloadClass.getString(Constants.Saved_Domains_Name, "").toString()
            val Saved_Parthner_Name = myDownloadClass.getString(Constants.Saved_Parthner_Name, "").toString()

            if (imagPartnerurl.equals(Constants.imagSwtichPartnerUrl)) {
                textPartnerUrlLunch.setText("Select Partner Url")


                if (Saved_Parthner_Name.isNotEmpty()) {
                    texturlsViews.setText(Saved_Parthner_Name)
                    getUrlBasedOnSpinnerText = Saved_Parthner_Name

                } else {
                    texturlsViews.setText("Select Partner Url")
                }


            } else {
                textPartnerUrlLunch.setText("Select Custom Domain")



                if (get_Saved_Domains_Name!!.isNotEmpty()) {
                    texturlsViews.setText(get_Saved_Domains_Name)

                } else {
                    texturlsViews.setText("Select Custom Domain")
                }


            }



            imagSwtichPartnerUrl.setOnCheckedChangeListener { compoundButton, isValued ->

                try {
                    if (handlerMoveToWebviewPage != null){
                        handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                    }
                    hideKeyBoard(binding.editTextInputSynUrlZip)
                    val editor = sharedBiometric.edit()
                    if (compoundButton.isChecked) {
                        editor.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)
                        editor.apply()
                        textPartnerUrlLunch.setText("Select Partner Url")


                        val Saved_Parthner_Name111 = myDownloadClass.getString(Constants.Saved_Parthner_Name, "")
                        if (Saved_Parthner_Name111!!.isNotEmpty()) {
                            texturlsViews.setText(Saved_Parthner_Name111)
                            getUrlBasedOnSpinnerText = Saved_Parthner_Name111

                        } else {
                            texturlsViews.setText("Select Partner Url")
                        }


                    } else {

                        editor.remove(Constants.imagSwtichPartnerUrl)
                        editor.apply()
                        textPartnerUrlLunch.setText("Select Custom Domain")


                        val get_Saved_Domains_Name111 = myDownloadClass.getString(Constants.Saved_Domains_Name, "")
                        if (get_Saved_Domains_Name111!!.isNotEmpty()) {
                            texturlsViews.setText(get_Saved_Domains_Name111)

                        } else {
                            texturlsViews.setText("Select Custom Domain")
                        }

                    }

            }catch (e:Exception){}
            }


        }
    }


    private fun initSyncTimmer() {

        val get_savedIntervals = myDownloadClass.getLong(Constants.getTimeDefined, 0)

        if (get_savedIntervals != 0L) {

            binding.textIntervalsSelect.text = get_savedIntervals.toString() + Minutes
            binding.textDisplaytime.text = get_savedIntervals.toString() + Minutes

        } else {

            binding.textIntervalsSelect.text = "Sync interval timer"
            binding.textDisplaytime.text = "Selected time : 00:55"

        }

    }


    private fun showSaveduserHistory() {

        customSavedDownloadDialog = Dialog(this)
        val bindingCm = CustomSavedHistoryLayoutBinding.inflate(LayoutInflater.from(this))
        customSavedDownloadDialog.setContentView(bindingCm.root)
        customSavedDownloadDialog.setCancelable(true)
        customSavedDownloadDialog.setCanceledOnTouchOutside(true)
        customSavedDownloadDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customSavedDownloadDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation

        val consMainAlert_sub_layout = bindingCm.consMainAlertSubLayout
        val textTitle = bindingCm.textTitle
        val textErrorText = bindingCm.textErrorText
        val textClearAllData = bindingCm.textClearAllData
        val imgCloseDialog = bindingCm.imageCrossClose
        val close_bs = bindingCm.closeBs
        val divider21 = bindingCm.divider21


        val preferences =
            android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textTitle.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textErrorText.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textClearAllData.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            val drawable_close_bs =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
            drawable_close_bs?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            close_bs.setImageDrawable(drawable_close_bs)

            val drawable_imageCrossClose =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
            drawable_imageCrossClose?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            imgCloseDialog.setImageDrawable(drawable_imageCrossClose)

            divider21.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                )
            )


        }


        if (handlerMoveToWebviewPage != null){
            handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
        }


        bindingCm.apply {


            closeBs.setOnClickListener {
                customSavedDownloadDialog.dismiss()
            }


            imageCrossClose.setOnClickListener {
                customSavedDownloadDialog.dismiss()
            }

            textClearAllData.setOnClickListener {
                startActivity(Intent(applicationContext, FileExplorerActivity::class.java))
                customSavedDownloadDialog.dismiss()
            }



            handler.postDelayed(Runnable {
                recyclerSavedDownload.adapter = adapter
                recyclerSavedDownload.layoutManager = LinearLayoutManager(applicationContext)

                mUserViewModel.readAllData.observe(this@ReSyncActivity, Observer { user ->
                    adapter.setData(user)
                    if (user.isNotEmpty()) {
                        textErrorText.visibility = View.GONE
                        textClearAllData.visibility = View.VISIBLE
                    } else {
                        textClearAllData.visibility = View.GONE
                        textErrorText.visibility = View.VISIBLE
                    }
                })


            }, 100)
        }




        customSavedDownloadDialog.show()

    }


    @SuppressLint("InflateParams", "SuspiciousIndentation", "SetTextI18n")
    private fun definedTimeIntervals() {
        val bindingCm: CustomDefinedTimeIntervalsBinding =
            CustomDefinedTimeIntervalsBinding.inflate(
                layoutInflater
            )
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(bindingCm.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }




        bindingCm.apply {


            val preferences =
                android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

            if (preferences.getBoolean("darktheme", false)) {
                consMainAlertSubLayout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

                textTitle.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textTwoMinutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                text100minutes2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                text55minutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                text1500minutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                text3000minutes2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                text6000minutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textOneTwentyMinutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                textOneEightThyMinutes2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                tex24000ThyMinutes.setTextColor(resources.getColor(R.color.dark_light_gray_pop))


                val drawable_close_bs =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable_close_bs?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dark_light_gray_pop
                    ), PorterDuff.Mode.SRC_IN
                )
                closeBs.setImageDrawable(drawable_close_bs)

                val drawable_imageCrossClose =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
                drawable_imageCrossClose?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dark_light_gray_pop
                    ), PorterDuff.Mode.SRC_IN
                )
                imageCrossClose.setImageDrawable(drawable_imageCrossClose)

                divider21.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.dark_light_gray_pop
                    )
                )


            }


        }



        if (handlerMoveToWebviewPage != null){
            handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
        }



        bindingCm.apply {


            imageCrossClose.setOnClickListener {
                alertDialog.dismiss()
            }

            closeBs.setOnClickListener {
                alertDialog.dismiss()
            }



            textTwoMinutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes22.toString() + " $Minutes"
                binding.textDisplaytime.text = "2 Minutes"
                getTimeDefined_Prime = timeMinuetes22.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_2min)
                editor.apply()

            }


            text55minutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes55.toString() + " $Minutes"
                binding.textDisplaytime.text = "5 Minutes"
                getTimeDefined_Prime = timeMinuetes55.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_5min)
                editor.apply()


            }

            text100minutes2.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes10.toString() + " $Minutes"
                binding.textDisplaytime.text = "10 Minutes"
                getTimeDefined_Prime = timeMinuetes10.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_10min)
                editor.apply()


            }


            text1500minutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes15.toString() + " $Minutes"
                binding.textDisplaytime.text = "15 Minutes"
                getTimeDefined_Prime = timeMinuetes15.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_15min)
                editor.apply()



            }

            text3000minutes2.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes30.toString() + " $Minutes"
                binding.textDisplaytime.text = "30 Minutes"
                getTimeDefined_Prime = timeMinuetes30.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_30min)
                editor.apply()



            }

            text6000minutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes60.toString() + " $Minutes"
                binding.textDisplaytime.text = "60 Minutes"
                getTimeDefined_Prime = timeMinuetes60.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_60min)
                editor.apply()


            }


            textOneTwentyMinutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes120.toString() + " $Minutes"
                binding.textDisplaytime.text = "2 Hours"
                getTimeDefined_Prime = timeMinuetes120.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_120min)
                editor.apply()


            }



            textOneEightThyMinutes2.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes180.toString() + " $Minutes"
                binding.textDisplaytime.text = "3 hours"
                getTimeDefined_Prime = timeMinuetes180.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_180min)
                editor.apply()


            }


            tex24000ThyMinutes.setOnClickListener {
                binding.textIntervalsSelect.text = timeMinuetes240.toString() + " $Minutes"
                binding.textDisplaytime.text = "4 hours"
                getTimeDefined_Prime = timeMinuetes240.toString()
                alertDialog.dismiss()

                val editor = myDownloadClass.edit()
                editor.putLong(Constants.getTimeDefined, Constants.t_240min)
                editor.apply()


            }


        }


        alertDialog.show()
    }


    @SuppressLint("InflateParams", "SuspiciousIndentation")
    private fun serVerOptionDialog() {
        val bindingCm: CustomApiHardCodedLayoutBinding = CustomApiHardCodedLayoutBinding.inflate(
            layoutInflater
        )
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(bindingCm.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }


        val consMainAlert_sub_layout = bindingCm.consMainAlertSubLayout
        val textTitle = bindingCm.textTitle
        val textApiServer = bindingCm.textApiServer
        val textCloudServer = bindingCm.textCloudServer
        val imgCloseDialog = bindingCm.imageCrossClose
        val close_bs = bindingCm.closeBs
        val divider21 = bindingCm.divider21


        val preferences =
            android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textTitle.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textApiServer.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textCloudServer.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            val drawable_close_bs =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
            drawable_close_bs?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            close_bs.setImageDrawable(drawable_close_bs)

            val drawable_imageCrossClose =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
            drawable_imageCrossClose?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            imgCloseDialog.setImageDrawable(drawable_imageCrossClose)


            divider21.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                )
            )

        }






        bindingCm.apply {

            textApiServer.setOnClickListener {

                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                binding.texturlsViews.text = CP_server
                getUrlBasedOnSpinnerText = CP_server

                val editor = myDownloadClass.edit()
                editor.putString(Constants.Saved_Parthner_Name, CP_server)
                editor.apply()

                alertDialog.dismiss()
            }


            textCloudServer.setOnClickListener {
                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }


                binding.texturlsViews.text = API_Server
                getUrlBasedOnSpinnerText = API_Server

                val editor = myDownloadClass.edit()
                editor.putString(Constants.Saved_Parthner_Name, API_Server)
                editor.apply()

                alertDialog.dismiss()
            }


            imageCrossClose.setOnClickListener {

                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }
                alertDialog.dismiss()
            }

            closeBs.setOnClickListener {

                if (handlerMoveToWebviewPage != null){
                    handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
                }

                alertDialog.dismiss()
            }


        }


        alertDialog.show()
    }


    @SuppressLint("MissingInflatedId", "DiscouragedApi")
    private fun showSyncDialog() {

        //create dialog
        val syncDialog = AlertDialog.Builder(this).create()
        val inflater = this.layoutInflater
        val viewOptions: View = inflater.inflate(R.layout.time_picker_dialog, null)


        //widgets
        val timePicker = viewOptions.findViewById<TimePicker>(R.id.timePicker)
        val cancelBtn = viewOptions.findViewById<Button>(R.id.cancelBtn)
        val setBtn = viewOptions.findViewById<Button>(R.id.setBtn)
        val consMainAlert_sub_layout =
            viewOptions.findViewById<ConstraintLayout>(R.id.consMainAlert_sub_layout)
        val textView7 = viewOptions.findViewById<TextView>(R.id.textView7)


        //dialog props
        syncDialog.setView(viewOptions)
        syncDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        syncDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val preferences =
            android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (preferences.getBoolean("darktheme", false)) {

            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)
            textView7.setTextColor(resources.getColor(R.color.dark_light_gray_pop))


            cancelBtn.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            cancelBtn.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

            setBtn.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            setBtn.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

        }

        if (handlerMoveToWebviewPage != null){
            handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
        }


        //initialize time picker
        timePicker.hour = 0
        timePicker.minute = 0
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { view: TimePicker?, hourOfDay: Int, minute: Int ->

            //get time
            hour = hourOfDay
            min = minute

        }

        //cancel
        cancelBtn.setOnClickListener { v: View? ->
            //dismiss
            syncDialog.dismiss()
        }

        //grant access
        setBtn.setOnClickListener { v: View? ->

            //calculate minutes
            val totalMins: Int = hour * 60 + min


            if (totalMins < 1) {
                showToastMessage("Time cannot be less than 1 minute")
                syncDialog.dismiss()
                return@setOnClickListener // Abort further execution
            }


            //set on edt
            binding.textDisplaytime.text = getFormattedTime(hour, min)
            binding.textIntervalsSelect.text = getFormattedTime(hour, min)


            //reset temp data
            hour = 0
            min = 0


            val editor = myDownloadClass.edit()
            editor.putLong(Constants.getTimeDefined, totalMins.toLong())
            editor.apply()


            //close dialog
            syncDialog.dismiss()
        }

        //show dialog
        syncDialog.show()
    }


    private fun getFormattedTime(hour: Int, minute: Int): String {
        val totalMinutes = hour * 60 + minute

        return when {
            totalMinutes == 1 -> "1 minute"
            totalMinutes == 2 -> "2 minutes"
            totalMinutes % 60 == 0 -> "${totalMinutes / 60} hour(s)"
            else -> "$totalMinutes minutes"
        }
    }

    private fun funManulOrNotInteView() {
        binding.apply {
            // logic for use manual or not
            val imagUsemanualOrnotuseManual = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")
            imagSwtichEnableManualOrNot.isChecked = imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)


            if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {
                textUseManual.setText("Use manual")

                editTextInputSynUrlZip.visibility = View.VISIBLE
                editTextInputIndexManual.visibility = View.VISIBLE
                // for clos
                editTextCLOpath.visibility = View.GONE
                editTextSubPathFolder.visibility = View.GONE


            } else {
                textUseManual.setText("Do not use manual")

                editTextInputSynUrlZip.visibility = View.GONE
                editTextInputIndexManual.visibility = View.GONE
                // for clos
                editTextCLOpath.visibility = View.VISIBLE
                editTextSubPathFolder.visibility = View.VISIBLE


            }

            imagSwtichEnableManualOrNot.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnableManualOrNot, "imagSwtichEnableManualOrNot"
                    )
                    editor.apply()
                    textUseManual.setText("Use manual")

                    editTextInputSynUrlZip.visibility = View.VISIBLE
                    editTextInputIndexManual?.visibility = View.VISIBLE
                    // for clos
                    editTextCLOpath.visibility = View.GONE
                    editTextSubPathFolder.visibility = View.GONE


                } else {

                    editor.remove("imagSwtichEnableManualOrNot")
                    editor.apply()
                    textUseManual.setText("Do not use manual")

                    editTextInputSynUrlZip.visibility = View.GONE
                    editTextInputIndexManual?.visibility = View.GONE
                    // for clos
                    editTextCLOpath.visibility = View.VISIBLE
                    editTextSubPathFolder.visibility = View.VISIBLE

                }
            }

        }
    }


    private fun hideKeyBoard(editText: EditText) {
        try {
            editText.clearFocus()
            val imm =
                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        } catch (ignored: java.lang.Exception) {
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun testAndDownLoadZipConnection() {

        binding.apply {
            val getFolderClo = editTextCLOpath.text.toString().trim()
            val getFolderSubpath = editTextSubPathFolder.text.toString().trim()
            val editor = myDownloadClass.edit()


            if (isNetworkAvailable()) {
                if (!imagSwtichEnableManualOrNot.isChecked) {


                    if (imagSwtichPartnerUrl.isChecked) {

                        if (getUrlBasedOnSpinnerText.isNotEmpty()) {

                            when (getUrlBasedOnSpinnerText) {
                                CP_server -> {
                                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty()) {
                                        httpNetworkDownloadsMultiplePaths(getFolderClo, getFolderSubpath)
                                        editor.putString(Constants.getSavedCLOImPutFiled, getFolderClo)
                                        editor.putString(Constants.getSaveSubFolderInPutFiled, getFolderSubpath)
                                        editor.putString(Constants.get_ModifiedUrl, Constants.CUSTOM_CP_SERVER_DOMAIN)
                                        editor.apply()

                                    } else {
                                        editTextCLOpath.error = "Input a valid path e.g CLO"
                                        editTextSubPathFolder.error =
                                            "Input a valid path e.g DE_MO_2021000"
                                        showToastMessage("Fields can not be empty")
                                    }
                                }

                                API_Server -> {

                                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty()) {
                                        httpNetworkDownloadsMultiplePaths(getFolderClo, getFolderSubpath)
                                        editor.putString(Constants.getSavedCLOImPutFiled, getFolderClo)
                                        editor.putString(Constants.getSaveSubFolderInPutFiled, getFolderSubpath)
                                        editor.putString(Constants.get_ModifiedUrl, Constants.CUSTOM_API_SERVER_DOMAIN)
                                        editor.apply()

                                    } else {
                                        editTextCLOpath.error = "Input a valid path e.g CLO"
                                        editTextSubPathFolder.error =
                                            "Input a valid path e.g DE_MO_2021000"
                                        showToastMessage("Fields can not be empty")
                                    }

                                }

                            }

                        } else {
                            showToastMessage("Select Partner Url")
                        }

                    } else {

                        val getFolderClo222 = binding.editTextCLOpath.text.toString().trim()
                        val getFolderSubpath22 = binding.editTextSubPathFolder.text.toString().trim()
                        var Saved_Domains_Urls = myDownloadClass.getString(Constants.Saved_Domains_Urls, "").toString()

                        if (Saved_Domains_Urls.isNotEmpty()) {


                            if (getFolderClo222.isNotEmpty() && getFolderSubpath22.isNotEmpty()) {
                                testAndDownLoad_My_API(getFolderClo222, getFolderSubpath22)
                                editor.putString(Constants.getSavedCLOImPutFiled, getFolderClo222)
                                editor.putString(Constants.getSaveSubFolderInPutFiled, getFolderSubpath22)
                                editor.apply()

                            } else {
                                editTextCLOpath.error = "Input a valid path e.g CLO"
                                editTextSubPathFolder.error = "Input a valid path e.g DE_MO_2021000"
                                showToastMessage("Fields can not be empty")
                            }


                        } else {
                            showToastMessage("Select Custom Domain")
                        }

                    }
                } else {

                    // When allowed to use manual

                    // for maunal download
                    val editInputUrl = editTextInputSynUrlZip.text.toString().trim()
                    // to luanch a live url manual
                    val editInputAppIndex = editTextInputIndexManual.text.toString().trim()

                    if (isUrlValid(editInputUrl) && isUrlValid(editInputAppIndex)) {

                        httpNetSingleDwonload(editInputUrl, editInputAppIndex)

                        editor.putString(Constants.getSavedEditTextInputSynUrlZip, editInputUrl)
                        editor.putString(
                            Constants.getSaved_manaul_index_edit_url_Input, editInputAppIndex
                        )
                        editor.apply()

                    } else {

                        if (!isUrlValid(editInputUrl)) {
                            binding.editTextInputSynUrlZip.error = "Invalid url format"
                        }

                        if (!isUrlValid(editInputAppIndex)) {
                            binding.editTextInputIndexManual.error = "Invalid url format"
                        }

                        showToastMessage("Invalid url format")

                    }

                }
            } else {
                showToastMessage("No Internet Connection")
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun httpNetworkDownloadsMultiplePaths(
        getFolderClo: String,
        getFolderSubpath: String,
    ) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Please wait")



            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

               /// val baseDomain = Constants.CUSTOM_CP_SERVER_DOMAIN

                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()
                val baseDomain = CP_AP_MASTER_DOMAIN
                val baseUrl = "$baseDomain/$getFolderClo/$getFolderSubpath/Zip/App.zip"

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            startMyDownlaodsMutiplesPath(
                                baseUrl,
                                getFolderClo,
                                getFolderSubpath,
                                Constants.Zip,
                                Constants.fileNmae_App_Zip,
                            )

                            // save also to room data base
                            val user = User(
                                CLO = getFolderClo,
                                DEMO = getFolderSubpath,
                                EditUrl = "",
                                EditUrlIndex = ""
                            )
                            mUserViewModel.addUser(user)


                        } else {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Failed!"
                            )

                            customProgressDialog.dismiss()
                        }
                    } finally {

                    }
                }


            } else {


                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                //val baseDomain = Constants.CUSTOM_CP_SERVER_DOMAIN
                val baseDomain = CP_AP_MASTER_DOMAIN
                val myCSvEndPath = Constants.myCSvEndPath
                // val baseUrl = "$baseDomain/$getFolderClo/$getFolderSubpath/App/index.html"
                val baseUrl = "$baseDomain/$getFolderClo/$getFolderSubpath/$myCSvEndPath"



                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            startMyDownlaodsMutiplesPath(
                                baseUrl,
                                getFolderClo,
                                getFolderSubpath,
                                Constants.App,
                                "index.html",
                            )

                            val user = User(
                                CLO = getFolderClo,
                                DEMO = getFolderSubpath,
                                EditUrl = "",
                                EditUrlIndex = ""
                            )
                            mUserViewModel.addUser(user)


                        } else {
                            showPopsForMyConnectionTest(
                                getFolderClo, getFolderSubpath, "Failed!"
                            )
                            customProgressDialog.dismiss()
                        }
                    } finally {

                    }
                }

            }


        }, 300)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun httpNetSingleDwonload(editInputUrl: String, editInputAppIndex: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Please wait")
            val lastString = editInputUrl.substringAfterLast("/")
            val fileNameWithoutExtension = lastString.substringBeforeLast(".")


            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(editInputUrl)
                        if (result) {
                            startDownloadSingles(
                                editInputUrl,
                                Constants.Zip,
                                Constants.fileNmae_App_Zip
                            )

                            val user = User(
                                CLO = "",
                                DEMO = "",
                                EditUrl = editInputUrl,
                                EditUrlIndex = editInputAppIndex
                            )
                            mUserViewModel.addUser(user)

                        } else {
                            showPopsForMyConnectionTest(
                                "CLO", fileNameWithoutExtension, "Failed!"
                            )
                            customProgressDialog.dismiss()
                        }
                    } finally {

                    }
                }

            } else {

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(editInputUrl)
                        if (result) {
                            startDownloadSingles(
                                editInputUrl, Constants.Zip, Constants.fileNmae_App_Zip
                            )
                            val user = User(
                                CLO = "",
                                DEMO = "",
                                EditUrl = editInputUrl,
                                EditUrlIndex = editInputAppIndex
                            )
                            mUserViewModel.addUser(user)
                        } else {

                            showPopsForMyConnectionTest(
                                "CLO", fileNameWithoutExtension, "Failed!"
                            )

                            customProgressDialog.dismiss()
                        }
                    } finally {

                    }
                }
            }


        }, 300)
    }


    private fun testConnectionSetup_API_Test(getFolderClo: String, getFolderSubpath: String) {

        showCustomProgressDialog("Testing connection")

        binding.apply {

            var Saved_Domains_Urls =
                myDownloadClass.getString(Constants.Saved_Domains_Urls, "").toString()


            if (isNetworkAvailable()) {

                val get_ModifiedUrl = Saved_Domains_Urls
                val editor = myDownloadClass.edit()
                editor.putString(Constants.get_ModifiedUrl, get_ModifiedUrl)
                editor.apply()



                if (binding.imagSwtichEnableSyncFromAPI.isChecked) {
                    val get_Full_url = Saved_Domains_Urls + "/$getFolderClo/$getFolderSubpath/Zip/App.zip"

                    showToastMessageLong(get_Full_url)

                    lifecycleScope.launch {
                        try {
                            val result = checkUrlExistence(get_Full_url)
                            if (result) {
                                showPopsForMyConnectionTest(
                                    getFolderClo, getFolderSubpath, "Successful"
                                )

                                val user = User(
                                    CLO = getFolderClo,
                                    DEMO = getFolderSubpath,
                                    EditUrl = "",
                                    EditUrlIndex = ""
                                )
                                mUserViewModel.addUser(user)
                                customProgressDialog.dismiss()

                            } else {
                                showPopsForMyConnectionTest(
                                    getFolderClo, getFolderSubpath, "Failed!"
                                )

                                customProgressDialog.dismiss()
                            }
                        } finally {
                            // customProgressDialog.dismiss()
                        }
                    }


//                    handler.postDelayed(Runnable {
//                        customProgressDialog.cancel()
//                    }, 900)


                } else {
                    // val get_Full_url = Saved_Domains_Urls + "/$getFolderClo/$getFolderSubpath/App/index.html"

                    val myCSvEndPath = Constants.myCSvEndPath
                    val get_Full_url = "$Saved_Domains_Urls/$getFolderClo/$getFolderSubpath/$myCSvEndPath"

                    showToastMessageLong(get_Full_url)

                    lifecycleScope.launch {
                        try {
                            val result = checkUrlExistence(get_Full_url)
                            if (result) {
                                showPopsForMyConnectionTest(
                                    getFolderClo, getFolderSubpath, "Successful"
                                )

                                val user = User(
                                    CLO = getFolderClo,
                                    DEMO = getFolderSubpath,
                                    EditUrl = "",
                                    EditUrlIndex = ""
                                )
                                mUserViewModel.addUser(user)
                                customProgressDialog.dismiss()

                            } else {
                                showPopsForMyConnectionTest(
                                    getFolderClo, getFolderSubpath, "Failed!"
                                )
                                customProgressDialog.dismiss()
                            }
                        } finally {
                            //   customProgressDialog.dismiss()
                        }
                    }


//                    handler.postDelayed(Runnable {
//                        customProgressDialog.cancel()
//                    }, 900)


                }


            } else {
                showToastMessage("No Internet Connection")
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun testAndDownLoad_My_API(getFolderClo: String, getFolderSubpath: String) {


        showCustomProgressDialog("Testing connection")

        binding.apply {

            var Saved_Domains_Urls =
                myDownloadClass.getString(Constants.Saved_Domains_Urls, "").toString()


            if (isNetworkAvailable()) {

                val get_ModifiedUrl = Saved_Domains_Urls
                val editor = myDownloadClass.edit()
                editor.putString(Constants.get_ModifiedUrl, get_ModifiedUrl)
                editor.apply()



                if (binding.imagSwtichEnableSyncFromAPI.isChecked) {
                    val get_Full_url =
                        "$Saved_Domains_Urls/$getFolderClo/$getFolderSubpath/Zip/App.zip"

                    showToastMessageLong(get_Full_url)

                    lifecycleScope.launch {
                        try {
                            val result = checkUrlExistence(get_Full_url)
                            if (result) {
                                startMyDownlaodsMutiplesPath(
                                    get_Full_url,
                                    getFolderClo,
                                    getFolderSubpath,
                                    "Zip",
                                    "App.zip",
                                )

                                // save also to room data base
                                val user = User(
                                    CLO = getFolderClo,
                                    DEMO = getFolderSubpath,
                                    EditUrl = "",
                                    EditUrlIndex = ""
                                )
                                mUserViewModel.addUser(user)

                            } else {
                                showPopsForMyConnectionTest(
                                    getFolderClo,
                                    getFolderSubpath,
                                    "Failed!"
                                )

                                customProgressDialog.dismiss()
                            }
                        } finally {
//                            handler.postDelayed(Runnable {
//                                customProgressDialog.dismiss()
//                            }, 900)
                        }
                    }

//                    handler.postDelayed(Runnable {
//                        customProgressDialog.cancel()
//                    }, 900)


                } else {
                    //   val get_Full_url = Saved_Domains_Urls + "/$getFolderClo/$getFolderSubpath/App/index.html"

                    val myCSvEndPath = Constants.myCSvEndPath
                    val get_Full_url = Saved_Domains_Urls + "/$getFolderClo/$getFolderSubpath/$myCSvEndPath"


                    showToastMessageLong(get_Full_url)

                    lifecycleScope.launch {
                        try {
                            val result = checkUrlExistence(get_Full_url)
                            if (result) {
                                startMyDownlaodsMutiplesPath(
                                    get_Full_url,
                                    getFolderClo,
                                    getFolderSubpath,
                                    Constants.App,
                                    "index.html",
                                )

                                // save also to room data base
                                val user = User(
                                    CLO = getFolderClo,
                                    DEMO = getFolderSubpath,
                                    EditUrl = "",
                                    EditUrlIndex = ""
                                )
                                mUserViewModel.addUser(user)

                            } else {
                                showPopsForMyConnectionTest(
                                    getFolderClo,
                                    getFolderSubpath,
                                    "Failed!"
                                )
                                customProgressDialog.dismiss()
                            }
                        } finally {
                            handler.postDelayed(Runnable {
                                //  customProgressDialog.dismiss()
                            }, 900)
                        }
                    }


                }


            } else {
                showToastMessage("No Internet Connection")
            }
        }

    }


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun showPopsForMyConnectionTest(
        getFolderClo: String,
        getFolderSubpath: String,
        message: String,
    ) {

        val bindingCM: CustomContinueDownloadLayoutBinding =
            CustomContinueDownloadLayoutBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(bindingCM.root)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(true)

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation

        val consMainAlert_sub_layout = bindingCM.consMainAlertSubLayout
        val textSucessful = bindingCM.textSucessful
        val textYourUrlTest = bindingCM.textYourUrlTest
        val textContinuPassword2 = bindingCM.textContinuPassword2
        val imgCloseDialog = bindingCM.imgCloseDialog



        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textSucessful.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textYourUrlTest.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textContinuPassword2.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            textContinuPassword2.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)

            val drawable_imgCloseDialog =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
            drawable_imgCloseDialog?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
            imgCloseDialog.setImageDrawable(drawable_imgCloseDialog)


        }





        bindingCM.apply {

            if (message.equals(Constants.Invalid_Config_Url)) {
                textYourUrlTest.text = Constants.Invalid_Config_Url
            } else {
                val userPath = "Username-$getFolderClo\nlicense-$getFolderSubpath"
                textYourUrlTest.text = userPath
            }


            textSucessful.text = message

            textContinuPassword2.setOnClickListener {

                alertDialog.dismiss()
            }

            imgCloseDialog.setOnClickListener {
                alertDialog.dismiss()
            }

        }


        alertDialog.show()


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startMyDownlaodsMutiplesPath(
        baseUrl: String,
        getFolderClo: String,
        getFolderSubpath: String,
        Zip: String,
        fileName: String,
    ) {

        if (binding.imagSwtichEnableConfigFileOnline.isChecked && isCompltedAndReady == false) {
            check_for_valid_confileUrl()

        } else {
            binding.apply {

                //    customProgressDialog.dismiss()
                val threeFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"


                val Extracted = "App"



                if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

                    download(
                        baseUrl,
                        getFolderClo,
                        getFolderSubpath,
                        Zip,
                        fileName,
                        Extracted,
                        threeFolderPath
                    )
                } else {

                    callApiClassActivity(
                        baseUrl,
                        getFolderClo,
                        getFolderSubpath,
                        Zip,
                        fileName,
                        Extracted,
                        threeFolderPath
                    )

                }


                /// similar but used on under second cancel downoad in danwload pager
                val editor = myDownloadClass.edit()
                editor.putString(Constants.getFolderClo, getFolderClo)
                editor.putString(Constants.getFolderSubpath, getFolderSubpath)
                editor.putString("Zip", Zip)
                editor.putString("fileName", fileName)
                editor.putString(Constants.Extracted, Extracted)
                editor.putString("baseUrl", baseUrl)


                val editText88 = sharedBiometric.edit()

                if (binding.imagSwtichEnableLaucngOnline.isChecked) {

                    editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online)
                    editText88.apply()

                } else {

                    editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Offline)
                    editText88.apply()

                }

                editor.apply()
            }
        }


    }

    private fun callApiClassActivity(
        url: String,
        getFolderClo: String,
        getFolderSubpath: String,
        Zip: String,
        fileNamy: String,
        Extracted: String,
        threeFolderPath: String,

        ) {

        val editior = myDownloadClass.edit()
        editior.putString(Constants.getFolderClo, getFolderClo)
        editior.putString(Constants.getFolderSubpath, getFolderSubpath)
        editior.putString("Zip", Zip)
        editior.putString("fileNamy", fileNamy)
        editior.putString(Constants.Extracted, Extracted)

        // used to control Sync Start from  set up page
        editior.remove(Constants.Manage_My_Sync_Start)


        val get_savedIntervals = myDownloadClass.getLong(Constants.getTimeDefined, 0)

        if (get_savedIntervals != 0L) {
            editior.putLong(Constants.getTimeDefined, get_savedIntervals)

        } else {
            editior.putLong(Constants.getTimeDefined, Constants.t_5min)

        }

        editior.apply()


        downloadTheApiCsvData()


    }

    private fun downloadTheApiCsvData() {
        binding.apply {

            val imagUsemanualOrnotuseManual =
                sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")

            //  if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {


            if (binding.imagSwtichEnableManualOrNot.isChecked) {
                handler.postDelayed(Runnable {

                    val getSavedEditTextInputSynUrlZip = myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()

                    if (getSavedEditTextInputSynUrlZip.contains(Constants.myCSvEndPath)) {
                        getDownloadMyCSVManual()
                    } else if (getSavedEditTextInputSynUrlZip.contains(Constants.myCSVUpdate1)) {
                        getDownloadMyCSVManual()
                    } else {
                        // showToastMessage("Something went wrong, System Could not locate CSV from this Location")
                        //   binding.textCsvStatus.text = Constants.Error_CSv_Message

                        showToastMessage(Constants.Error_CSv_Message)
                        customProgressDialog.dismiss()

                    }

                }, 500)

            } else {

                handler.postDelayed(Runnable {
                    getDownloadMyCSV()
                    // showToastMessage("getDownloadMyCSV")
                }, 500)
            }

        }
    }


    @SuppressLint("SetTextI18n")
    private fun getDownloadMyCSV() {

        lifecycleScope.launch(Dispatchers.IO) {
            val imagUsemanualOrnotuseManual = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "").toString()
            val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val getFolderSubpath =
                myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val get_ModifiedUrl =
                myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
            val get_getSavedEditTextInputSynUrlZip =
                myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()
            //  val baseDomain = Constants.customDomainUrl

            Log.d("ResyncActivity", get_ModifiedUrl)

            //   showToastMessage("API Content Connection Successful!")
            //  binding.textCsvStatus.text = "API Content Connection Successful!"

            val lastEnd = "Start/start1.csv";
            val csvDownloader = CSVDownloader()
            val csvData =
                csvDownloader.downloadCSV(get_ModifiedUrl, getFolderClo, getFolderSubpath, lastEnd)
            saveURLPairs(csvData)
            //  handler.postDelayed(runnable, 500)

            handler.postDelayed(Runnable {
                val intent = Intent(applicationContext, DownloadApisFilesActivity::class.java)
                startActivity(intent)
                finish()

                customProgressDialog.dismiss()

            }, 5000)


        }


    }


    @SuppressLint("SetTextI18n")
    private fun getDownloadMyCSVManual() {

        lifecycleScope.launch(Dispatchers.IO) {

            val get_getSavedEditTextInputSynUrlZip =
                myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()


            //   showToastMessage("API Content Connection Successful!")
            //  binding.textCsvStatus.text =  "API Content Connection Successful!"

            val csvDownloader = CSVDownloader()
            val csvData = csvDownloader.downloadCSV(get_getSavedEditTextInputSynUrlZip, "", "", "")
            saveURLPairs(csvData)

            //  myHandler.postDelayed(runnableManual, 500)

            handler.postDelayed(Runnable {
                val intent = Intent(applicationContext, DownloadApisFilesActivity::class.java)
                startActivity(intent)
                finish()
                finish()
                finishAndRemoveTask()

                customProgressDialog.dismiss()

            }, 5000)


        }


    }


    private fun saveURLPairs(csvData: String) {

        mFilesViewModel.deleteAllFiles()
        dnFailedViewModel.deleteAllFiles()
        dnViewModel.deleteAllFiles()

        val pairs = parseCSV(csvData)

        // Add files to Room Database
        lifecycleScope.launch(Dispatchers.IO) {
            for ((index, line) in pairs.withIndex()) {
                val parts = line.split(",").map { it.trim() }
                if (parts.size < 2) continue // Skip lines with insufficient data

                val sn = parts[0].toIntOrNull() ?: continue // Skip lines with invalid SN
                val folderAndFile = parts[1].split("/")

                val folderName = if (folderAndFile.size > 1) {
                    folderAndFile.dropLast(1).joinToString("/")
                } else {
                    "MyApiFolder" // Assuming default folder name
                }

                val fileName =
                    folderAndFile.lastOrNull() ?: continue // Skip lines with missing file name
                val status = "true" // Set your status here
                // val id =   System.currentTimeMillis()
                val files = FilesApi(
                    SN = sn.toString(),
                    FolderName = folderName,
                    FileName = fileName,
                    Status = status
                )
                mFilesViewModel.addFiles(files)

                val dnFailedApi = DnFailedApi(
                    SN = sn.toString(),
                    FolderName = folderName,
                    FileName = fileName,
                    Status = status
                )
                dnFailedViewModel.addFiles(dnFailedApi)

            }
        }

    }


    // for no need of comma CSV
    private fun parseCSV(csvData: String): List<String> {
        val pairs = mutableListOf<String>()
        val lines = csvData.split("\n")
        for (line in lines) {
            if (line.isNotBlank()) {
                pairs.add(line.trim())
            }
        }
        return pairs
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startDownloadSingles(baseUrl: String, Zip: String, fileNamy: String) {

        val Extracted = Constants.App
        val threeFolderPath = "/MANUAL/DEMO/$Zip"



        if (binding.imagSwtichEnableConfigFileOnline.isChecked && !isCompltedAndReady) {
            check_for_valid_confileUrl()

        } else {
            binding.apply {

                // customProgressDialog.dismiss()

                if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

                    binding.apply {


                        download(
                            baseUrl,
                            "MANUAL",
                            "DEMO",
                            Zip,
                            fileNamy,
                            Extracted,
                            threeFolderPath
                        )

                        val editor = myDownloadClass.edit()
                        editor.putString(Constants.getFolderClo, "MANUAL")
                        editor.putString(Constants.getFolderSubpath, "DEMO")
                        editor.putString(Constants.Zip, Zip)
                        editor.putString(Constants.fileName, fileNamy)
                        editor.putString(Constants.Extracted, Extracted)
                        editor.putString(Constants.baseUrl, baseUrl)
                        editor.apply()

                    }


                } else {

                    callApiClassActivity(
                        baseUrl,
                        "CLO",
                        "MANUAL/DEMO",
                        Zip,
                        fileNamy,
                        Extracted,
                        threeFolderPath
                    )

                }


                /// similar but used on under second cancel downoad in danwload pager
                val editor = myDownloadClass.edit()
                editor.putString(Constants.getFolderClo, "CLO")
                editor.putString(Constants.getFolderSubpath, "MANUAL/DEMO")
                editor.putString(Constants.Zip, Zip)
                editor.putString(Constants.fileName, fileNamy)
                editor.putString(Constants.Extracted, Extracted)
                editor.putString(Constants.baseUrl, baseUrl)




                val editText88 = sharedBiometric.edit()

                if (binding.imagSwtichEnableLaucngOnline.isChecked) {

                    editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online_Manual_Index)
                    editText88.apply()

                } else {

                    editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Offline_Manual_Index)
                    editText88.apply()

                }




                editor.apply()
            }
        }




    }


    private fun showToastMessage(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }

    private fun showToastMessageLong(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
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


        //  val DeleteFolderPath = "/$getFolderClo/$getFolderSubpath/"

        val DeleteFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip/$fileNamy"

        val directoryPath =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive$DeleteFolderPath"
        val file = File(directoryPath)
        delete(file)



        handler.postDelayed(Runnable {

            val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
            val Syn2AppLive = "Syn2AppLive"

            val editior = myDownloadClass.edit()
            editior.putString(Constants.getFolderClo, getFolderClo)
            editior.putString(Constants.getFolderSubpath, getFolderSubpath)
            editior.putString(Constants.Zip, Zip)
            editior.putString("fileNamy", fileNamy)
            editior.putString(Constants.Extracted, Extracted)

            // used to control Sync Start from  set up page
            editior.remove(Constants.Manage_My_Sync_Start)


            val get_savedIntervals = myDownloadClass.getLong(Constants.getTimeDefined, 0)

            if (get_savedIntervals != 0L) {
                editior.putLong(Constants.getTimeDefined, get_savedIntervals)

            } else {
                editior.putLong(Constants.getTimeDefined, Constants.t_5min)

            }

            editior.apply()


            val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            val folder = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/Download/$Syn2AppLive/$finalFolderPath"
            )

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

            val editor = myDownloadClass.edit()
            editor.putLong(Constants.downloadKey, downloadReferenceMain)
            editor.apply()


            val intent = Intent(applicationContext, DownlodZipActivity::class.java)
            intent.putExtra(Constants.baseUrl, url)
            intent.putExtra(Constants.getFolderClo, getFolderClo)
            intent.putExtra(Constants.getFolderSubpath, getFolderSubpath)
            intent.putExtra(Constants.Zip, Zip)
            intent.putExtra(Constants.fileName, fileNamy)
            intent.putExtra(Constants.Extracted, Extracted)

            intent.putExtra(Constants.threeFolderPath, threeFolderPath)
            intent.putExtra(Constants.baseUrl, url)
            startActivity(intent)
            finish()


            val editor222 = sharedBiometric.edit()
            //  editor222.putString(Constants.showDownloadSyncStatus, "showDownloadSyncStatus")
            editor222.apply()

            customProgressDialog.dismiss()

        }, 200)


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


    override fun onItemClicked(photo: User) {


        val clo = photo.CLO.toString()
        val demo = photo.DEMO
        val editurl = photo.EditUrl
        val editurlAppIndex = photo.EditUrlIndex

        if (!clo.isNullOrEmpty()) {
            binding.editTextCLOpath.setText(clo)
            fil_CLO = clo

            binding.textLauncheSaveDownload.visibility = View.VISIBLE

        }

        if (!demo.isNullOrEmpty()) {
            binding.editTextSubPathFolder.setText(demo)
            fil_DEMO = demo

            binding.textLauncheSaveDownload.visibility = View.VISIBLE

        }




        if (!editurl.isNullOrEmpty()) {
            binding.editTextInputSynUrlZip.setText(editurl)
            fil_baseUrl = editurl

            binding.textLauncheSaveDownload.visibility = View.VISIBLE
        }




        if (!editurlAppIndex.isNullOrEmpty()) {
            binding.editTextInputIndexManual.setText(editurlAppIndex)
            fil_appIndex = editurlAppIndex

            binding.textLauncheSaveDownload.visibility = View.VISIBLE
        }



        customSavedDownloadDialog.dismiss()
    }


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun showPopForLaunch_Oblin_offline() {
        val bindingCM: CustomSelectLauncOrOfflinePopLayoutBinding =
            CustomSelectLauncOrOfflinePopLayoutBinding.inflate(
                layoutInflater
            )
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingCM.getRoot())
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }

        val textLaunchMyOnline: TextView = bindingCM.textLaunchMyOnline
        val textLaunchMyOffline: TextView = bindingCM.textLaunchMyOffline
        val imgCloseDialog: ImageView = bindingCM.imageCrossClose
        val close_bs: ImageView = bindingCM.closeBs

        val userPath = "Username-$fil_CLO\nlicense-$fil_DEMO"

        bindingCM.textDescription.text = userPath


        textLaunchMyOnline.setOnClickListener {



            binding.apply {

                textLunchOnline.setText("Launch online")


                val editor = myDownloadClass.edit()
                val imagSwtichEnableManualOrNot = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "").toString()

                if (imagSwtichEnableManualOrNot.equals(Constants.imagSwtichEnableManualOrNot)) {

                    // Use manual

                    if (fil_baseUrl.isNotEmpty() && fil_appIndex.isNotEmpty()) {

                        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                        // this url does not affect the Outcome of Master Domain
                        val url = "${CP_AP_MASTER_DOMAIN}/$fil_CLO/$fil_DEMO/App/index.html"

                        imagSwtichEnableLaucngOnline.isChecked = true


                        //   editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")

                        editor.putString(Constants.getSavedEditTextInputSynUrlZip, fil_baseUrl)
                        editor.putString(Constants.getSaved_manaul_index_edit_url_Input, fil_appIndex)
                        editor.putString(Constants.syncUrl, url)
                        editor.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOnline)
                        editor.apply()


                        val editText88 = sharedBiometric.edit()
                        editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online_Manual_Index)
                        editText88.apply()



                        val intent = Intent(applicationContext, WebViewPage::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        showToastMessage("Select Saved Download Path")
                    }


                } else {

                    // Do Not use Manual


                    if (fil_CLO.isNotEmpty() && fil_DEMO.isNotEmpty()) {

                        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                        // this url does not affect the Outcome of Master Domain
                        val url = "${CP_AP_MASTER_DOMAIN}/$fil_CLO/$fil_DEMO/App/index.html"

                        imagSwtichEnableLaucngOnline.isChecked = true


                        //  editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")

                        editor.putString(Constants.getFolderClo, fil_CLO)
                        editor.putString(Constants.getFolderSubpath, fil_DEMO)
                        editor.putString(Constants.syncUrl, url)
                        editor.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOnline)
                        editor.apply()


                        val editText88 = sharedBiometric.edit()
                        editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Online)
                        editText88.apply()


                        val intent = Intent(applicationContext, WebViewPage::class.java)
                        startActivity(intent)
                        finish()


                    } else {
                        showToastMessage("Select Saved Download Path")
                    }


                }



                alertDialog.dismiss()
            }

        }



        textLaunchMyOffline.setOnClickListener {

            binding.apply {

                textLunchOnline.setText("Launch offline")

                val editor = myDownloadClass.edit()

                val imagSwtichEnableManualOrNot = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "").toString()

                if (imagSwtichEnableManualOrNot.equals(Constants.imagSwtichEnableManualOrNot)) {


                    if (fil_baseUrl.isNotEmpty() && fil_appIndex.isNotEmpty()) {

                        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                        // this url does not affect the Outcome of Master Domain
                        val url = "${CP_AP_MASTER_DOMAIN}/$fil_CLO/$fil_DEMO/App/index.html"

                        imagSwtichEnableLaucngOnline.isChecked = false

                        // editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")

                        editor.putString(Constants.getSavedEditTextInputSynUrlZip, fil_baseUrl)
                        editor.putString(Constants.getSaved_manaul_index_edit_url_Input, fil_appIndex)
                        editor.putString(Constants.syncUrl, url)
                        editor.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOffline)
                        editor.apply()



                        val editText88 = sharedBiometric.edit()
                        editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Offline)
                        editText88.apply()



                        val intent = Intent(applicationContext, WebViewPage::class.java)
                        startActivity(intent)
                        finish()


                    } else {
                        showToastMessage("Select Saved Download Path")
                    }


                } else {

                    // Do not use manual
                    if (fil_CLO.isNotEmpty() && fil_DEMO.isNotEmpty()) {

                        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                        // this url does not affect the Outcome of Master Domain
                        val url = "${CP_AP_MASTER_DOMAIN}/$fil_CLO/$fil_DEMO/App/index.html"

                        imagSwtichEnableLaucngOnline.isChecked = false

                        //  editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")

                        editor.putString(Constants.getFolderClo, fil_CLO)
                        editor.putString(Constants.getFolderSubpath, fil_DEMO)
                        editor.putString(Constants.syncUrl, url)
                        editor.putString(Constants.Tapped_OnlineORoffline, Constants.tapped_launchOffline)
                        editor.apply()

                        val editText88 = sharedBiometric.edit()
                        editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Offline_Manual_Index)
                        editText88.apply()



                        val intent = Intent(applicationContext, WebViewPage::class.java)
                        startActivity(intent)
                        finish()


                    } else {
                        showToastMessage("Select Saved Download Path")
                    }


                }



                alertDialog.dismiss()
            }
        }


        imgCloseDialog.setOnClickListener { alertDialog.dismiss() }

        close_bs.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }


    @SuppressLint("SetTextI18n")
    private fun show_API_Urls() {
        custom_ApI_Dialog = Dialog(this)
        val bindingCm = CustomApiUrlLayoutBinding.inflate(LayoutInflater.from(this))
        custom_ApI_Dialog.setContentView(bindingCm.root)
        custom_ApI_Dialog.setCancelable(true)
        custom_ApI_Dialog.setCanceledOnTouchOutside(true)
        custom_ApI_Dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        custom_ApI_Dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation

        val consMainAlert_sub_layout = bindingCm.constraintLayout
        val textTitle = bindingCm.textTitle
        val textErrorText = bindingCm.textErrorText
        val textTryAgin = bindingCm.textTryAgin
        val imgCloseDialog = bindingCm.imageCrossClose
        val close_bs = bindingCm.closeBs
        val progressBar2 = bindingCm.progressBar2
        val divider21 = bindingCm.divider21


        val preferences =
            android.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (preferences.getBoolean("darktheme", false)) {
            consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)

            textTitle.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textErrorText.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
            textTryAgin.setTextColor(resources.getColor(R.color.dark_light_gray_pop))

            val drawable_close_bs =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
            drawable_close_bs?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            close_bs.setImageDrawable(drawable_close_bs)

            val drawable_imageCrossClose =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_close_24)
            drawable_imageCrossClose?.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                ), PorterDuff.Mode.SRC_IN
            )
            imgCloseDialog.setImageDrawable(drawable_imageCrossClose)

            val colorWhite = ContextCompat.getColor(applicationContext, R.color.dark_light_gray_pop)
            progressBar2.indeterminateDrawable.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN)

            divider21.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.dark_light_gray_pop
                )
            )


        }

        if (handlerMoveToWebviewPage != null){
            handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
        }


        if (isNetworkAvailable()) {
            bindingCm.progressBar2.visibility = View.VISIBLE
            bindingCm.textTryAgin.visibility = View.GONE
            bindingCm.textErrorText.visibility = View.GONE

            mApiViewModel.fetchApiUrls(Constants.BASE_URL_OF_MASTER_DOMAIN)

        } else {
            bindingCm.apply {
                progressBar2.visibility = View.GONE
                textErrorText.visibility = View.VISIBLE
                textTryAgin.visibility = View.VISIBLE
                textErrorText.text = "No Internet Connection"
            }

        }

        bindingCm.apply {


            recyclerApi.adapter = adapterApi
            recyclerApi.layoutManager = LinearLayoutManager(applicationContext)

            mApiViewModel.apiUrls.observe(this@ReSyncActivity, Observer { apiUrls ->
                apiUrls?.let {
                    adapterApi.setData(it.DomainUrls)

                    if (it.DomainUrls.isNotEmpty()) {
                        textErrorText.visibility = View.GONE
                        progressBar2.visibility = View.GONE
                        textTryAgin.visibility = View.GONE

                    } else {
                        textErrorText.visibility = View.VISIBLE
                        textTryAgin.visibility = View.VISIBLE
                        textErrorText.text = "Opps! No Data Found"
                    }

                }
            })


            imageCrossClose.setOnClickListener {
                custom_ApI_Dialog.dismiss()
            }

            closeBs.setOnClickListener {
                custom_ApI_Dialog.dismiss()
            }


            textTryAgin.setOnClickListener {
                if (isNetworkAvailable()) {
                    bindingCm.progressBar2.visibility = View.VISIBLE
                    bindingCm.textTryAgin.visibility = View.GONE
                    bindingCm.textErrorText.visibility = View.GONE

                    mApiViewModel.fetchApiUrls(Constants.BASE_URL_OF_MASTER_DOMAIN)

                } else {
                    bindingCm.apply {
                        progressBar2.visibility = View.GONE
                        textErrorText.visibility = View.VISIBLE
                        textTryAgin.visibility = View.VISIBLE
                        textErrorText.text = "No Internet Connection"
                        showToastMessage("No Internet Connection")
                    }

                }
            }

        }


        custom_ApI_Dialog.show()

    }

    override fun onItemClicked(domainUrl: DomainUrl) {

        val name = domainUrl.name + ""
        val urls = domainUrl.url + ""
        if (name.isNotEmpty()) {
            binding.texturlsViews.text = name
        }

        // Note - later you can use the url as well , the  name is displayed on textview
        if (name.isNotEmpty() && urls.isNotEmpty()) {
            val editor = myDownloadClass.edit()
            editor.putString(Constants.Saved_Domains_Name, name)
            editor.putString(Constants.Saved_Domains_Urls, urls)
            editor.apply()

            if (handlerMoveToWebviewPage != null){
                handlerMoveToWebviewPage.removeCallbacksAndMessages(null)
            }

        }


        custom_ApI_Dialog.dismiss()
    }

    private fun second_cancel_download() {
        try {

            val download_ref: Long = myDownloadClass.getLong(Constants.downloadKey, -15)

            val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val Zip = myDownloadClass.getString(Constants.Zip, "").toString()
            val fileName = myDownloadClass.getString(Constants.fileName, "").toString()


            val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip/$fileName"

            val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + finalFolderPath

            val myFile = File(directoryPath, fileName)

            delete(myFile)


            if (download_ref != -15L) {
                val query = DownloadManager.Query()
                query.setFilterById(download_ref)
                val c =
                    (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(
                        query
                    )
                if (c.moveToFirst()) {
                    manager!!.remove(download_ref)
                    val editor: SharedPreferences.Editor = myDownloadClass.edit()
                    editor.remove(Constants.downloadKey)
                    editor.apply()
                }

            }

        } catch (ignored: java.lang.Exception) {
        }
    }

    private fun loadBackGroundImage() {

        val fileTypes = "app_background.png"
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()

        val pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
        val folder =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + pathFolder
        val file = File(folder, fileTypes)

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage)

        }

    }


}

