package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.paperdb.Paper
import kotlinx.coroutines.launch
import sync2app.com.syncapplive.WebViewPage
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.additionalSettings.ApiUrls.ApiUrlViewModel
import sync2app.com.syncapplive.additionalSettings.ApiUrls.DomainUrl
import sync2app.com.syncapplive.additionalSettings.ApiUrls.SavedApiAdapter
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.Common
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.MethodsSchedule
import sync2app.com.syncapplive.additionalSettings.savedDownloadHistory.User
import sync2app.com.syncapplive.additionalSettings.savedDownloadHistory.UserViewModel
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkStoragePermission
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkUrlExistence
import sync2app.com.syncapplive.additionalSettings.urlchecks.getPermissionStatus
import sync2app.com.syncapplive.additionalSettings.urlchecks.requestStoragePermission
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.additionalSettings.utils.Utility
import sync2app.com.syncapplive.databinding.ActivityTvOrAppModePageBinding
import sync2app.com.syncapplive.databinding.CustomApiHardCodedLayoutBinding
import sync2app.com.syncapplive.databinding.CustomApiUrlLayoutBinding
import sync2app.com.syncapplive.databinding.CustomGrantAccessPageBinding
import sync2app.com.syncapplive.databinding.CustomPopDisplayOverAppsBinding
import sync2app.com.syncapplive.databinding.CustomeAllowAppWriteSystemBinding
import sync2app.com.syncapplive.databinding.ProgressValidateUserDialogLayoutBinding
import java.io.File


class TvActivityOrAppMode : AppCompatActivity(), SavedApiAdapter.OnItemClickListener {
    private lateinit var binding: ActivityTvOrAppModePageBinding

    private val prefs by lazy { getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private val adapterApi by lazy {
        SavedApiAdapter(this)
    }


    private var isDialogPermissionShown = false


    private val multiplePermissionId = 14
    private val multiplePermissionNameList = if (Build.VERSION.SDK_INT >= 33) {
        arrayListOf(
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.POST_NOTIFICATIONS,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
            android.Manifest.permission.RECORD_AUDIO
        )
    } else {
        arrayListOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
            android.Manifest.permission.RECORD_AUDIO
        )
    }


    private var btnisClicked = false

    private var navigateTVMode = false;
    private var navigateAppMolde = false;

    private val TAG = "TvActivityOrAppMode"

    private var getUrlBasedOnSpinnerText = ""
    private var API_Server = "API-Cloud App Server"
    private var CP_server = "CP-Cloud App Server"

    private val mApiViewModel by viewModels<ApiUrlViewModel>()

    private val mUserViewModel by viewModels<UserViewModel>()

    private lateinit var custom_ApI_Dialog: Dialog
    private lateinit var customProgressDialog: Dialog

    private val simpleSavedPassword: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SIMPLE_SAVED_PASSWORD,
            Context.MODE_PRIVATE
        )
    }


    private val myDownloadClass: SharedPreferences by lazy {
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


    private val sharedTVAPPModePreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_TV_APP_MODE, Context.MODE_PRIVATE
        )
    }

    private var preferences: SharedPreferences? = null


    @SuppressLint("CommitPrefEdits", "SetTextI18n", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvOrAppModePageBinding.inflate(layoutInflater)
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


        setUpdarkUITheme()

        //add exception
        Methods.addExceptionHandler(this)


        // initialize schedule settings
        MethodsSchedule.setPersistentDefaults()


        val get_imgToggleImageBackground =
            sharedBiometric.getString(Constants.imgToggleImageBackground, "").toString()
        val get_imageUseBranding =
            sharedBiometric.getString(Constants.imageUseBranding, "").toString()
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(
                Constants.imageUseBranding
            )
        ) {
            loadBackGroundImage()
        }

        if (get_imageUseBranding == Constants.imageUseBranding) {
            loadImage()
        }


        val CheckForPassword =
            simpleSavedPassword.getString(Constants.onCreatePasswordSaved, "").toString()

        if (CheckForPassword.isNullOrEmpty()) {
            val editor = simpleSavedPassword.edit()
            editor.putString(Constants.onCreatePasswordSaved, "onCreatePasswordSaved")
            editor.putString(Constants.mySimpleSavedPassword, "1234_1234_1234_E-***#^8678488_587377_73784#GGGkkk***2345_KING")
            editor.apply()

        }


        val sharedBiometric: SharedPreferences =
            applicationContext.getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val get_useOfflineOrOnline =
            sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "").toString()
        val get_TV_or_App_Mode =
            sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "").toString()


        val editor = sharedBiometric.edit()
        if (get_useOfflineOrOnline.equals(Constants.imgAllowLunchFromOnline) || get_TV_or_App_Mode.equals(Constants.TV_Mode)) {
            editor.putString(Constants.FIRST_TIME_APP_START, Constants.FIRST_TIME_APP_START)
            editor.apply()
            startActivity(Intent(applicationContext, WebViewPage::class.java))
            finish()
        }


        val editoreedde = simpleSavedPassword.edit()
        getUrlBasedOnSpinnerText = ""
        editoreedde.remove(Constants.Saved_Domains_Name)
        editoreedde.remove(Constants.Saved_Domains_Urls)
        editoreedde.apply()


        handler.postDelayed(Runnable {
            val editoreee = simpleSavedPassword.edit()
            binding.texturlsSavedDownload.text = "Select Partner Url"

            binding.texturlsSavedDownload.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white_wash_dim_bit
                )
            )

            binding.textPartnerUrlLunch.text = "Select Partner Url"

            editoreee.remove(Constants.Saved_Domains_Name)
            editoreee.remove(Constants.Saved_Domains_Urls)

            editoreee.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)
            editoreee.apply()

            binding.imgUserMasterDomainORCustom.isChecked = true

        }, 300)



        binding.imgUserMasterDomainORCustom.setOnCheckedChangeListener { compoundButton, isValued ->

            Utility.hideKeyBoard(applicationContext, binding.editTextUserID)
            if (compoundButton.isChecked) {
                val editoreee = simpleSavedPassword.edit()
                binding.texturlsSavedDownload.text = "Select Partner Url"

                binding.texturlsSavedDownload.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white_wash_dim_bit
                    )
                )

                binding.textPartnerUrlLunch.text = "Select Partner Url"

                editoreee.remove(Constants.Saved_Domains_Name)
                editoreee.remove(Constants.Saved_Domains_Urls)

                editoreee.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)

                editoreee.apply()


            } else {

                binding.textPartnerUrlLunch.text = "Select Custom Domain"
                binding.texturlsSavedDownload.text = "Select Custom Domain"

                binding.texturlsSavedDownload.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white_wash_dim_bit
                    )
                )
                getUrlBasedOnSpinnerText = ""

                val editoreee = simpleSavedPassword.edit()
                editoreee.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)
                editoreee.apply()
            }
        }


        binding.constraintLayoutSlectDomain.setOnClickListener {
            if (binding.imgUserMasterDomainORCustom.isChecked) {
                serVerOptionDialog()
            } else {
                show_API_Urls()
            }
        }





        binding.apply {


            textAppMode.setOnClickListener {
                btnisClicked = true

                Utility.hideKeyBoard(applicationContext, binding.editTextUserID)

                // Save Launch State
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_Default_WebView_url)
                editText88.putString(Constants.imgStartAppRestartOnTvMode, Constants.imgStartAppRestartOnTvMode)
                editText88.remove(Constants.imgEnableAutoBoot)
                editText88.apply()


                navigateAppMolde = true
                navigateTVMode = false


                ///  Shared pref for app Settings
                // used for App settings Shared Prefernce
                val editorPref = preferences?.edit()
                editorPref!!.putBoolean(Constants.hidebottombar, false)
                editorPref.putBoolean(Constants.fullscreen, false)
                editorPref.putBoolean(Constants.immersive_mode, false)
                editorPref.putBoolean(Constants.shwoFloatingButton, false)
                editorPref.apply()


                // remove Json Data for Tv Setting
                val editorTV = sharedTVAPPModePreferences.edit()
                editorTV.remove(Constants.INSTALL_TV_JSON_USER_CLICKED)
                editorTV.remove(Constants.installTVModeForFirstTime)
                editorTV.apply()


                handleFormVerification()

            }






            textTvMode.setOnClickListener {
                btnisClicked = true



                Utility.hideKeyBoard(applicationContext, binding.editTextUserID)

                // Save Launch State
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.get_Launching_State_Of_WebView, Constants.launch_WebView_Offline)
                editText88.putString(Constants.imgEnableAutoBoot, Constants.imgEnableAutoBoot)
                editText88.putString(Constants.imgStartAppRestartOnTvMode, Constants.imgStartAppRestartOnTvMode)
                editText88.apply()


                navigateAppMolde = false
                navigateTVMode = true


                // Shared pref for app Settings
                // used for App settings Shared Prefernce
                val editorPref = preferences?.edit()
                editorPref!!.putBoolean(Constants.hidebottombar, true)
                editorPref.putBoolean(Constants.fullscreen, true)
                editorPref.putBoolean(Constants.immersive_mode, true)
                editorPref.putBoolean(Constants.shwoFloatingButton, true)
                editorPref.apply()



                // remove Json Data for Tv Setting
                val editorTV = sharedTVAPPModePreferences.edit()
                editorTV.remove(Constants.INSTALL_TV_JSON_USER_CLICKED)
                editorTV.remove(Constants.installTVModeForFirstTime)
                editorTV.apply()


                handleFormVerification()

            }


            textDefaultMode.setOnClickListener {
                btnisClicked = true

                Utility.hideKeyBoard(applicationContext, binding.editTextUserID)

                navigateAppMolde = false
                navigateTVMode = true


                // remove Json Data for Tv Setting
                val editorTV = sharedTVAPPModePreferences.edit()
                editorTV.putString(Constants.INSTALL_TV_JSON_USER_CLICKED, Constants.INSTALL_TV_JSON_USER_CLICKED)
                editorTV.remove(Constants.installTVModeForFirstTime)
                editorTV.apply()


                // Save Launch State
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.imgStartAppRestartOnTvMode, Constants.imgStartAppRestartOnTvMode)
                editText88.apply()

                handleFormVerification()

            }


        }


    }




    // now handle verification from cloud

    private fun handleFormVerification() {
        val get_UserID = binding.editTextUserID.text.toString().trim()
        val get_LicenseKey = binding.editTextLicenseKey.text.toString().trim()

        Utility.hideKeyBoard(applicationContext, binding.editTextUserID)

        if (Utility.isNetworkAvailable(this)) {


            val get_Saved_Domains_Urls = simpleSavedPassword.getString(Constants.Saved_Domains_Urls, "").toString().trim()

            if (binding.imgUserMasterDomainORCustom.isChecked) {
                // add the toggle check for Sync Page
                val editor = sharedBiometric.edit()
                editor.putString(Constants.imagSwtichPartnerUrl, Constants.imagSwtichPartnerUrl)
                editor.apply()


                if (getUrlBasedOnSpinnerText.isNotEmpty()) {
                    when (getUrlBasedOnSpinnerText) {
                        CP_server -> {

                            /// val customDomainUrl = "https://cp.cloudappserver.co.uk/app_base/public/"
                            val get_editTextMaster = Constants.CUSTOM_CP_SERVER_DOMAIN
                            checkMyConnection(get_UserID, get_LicenseKey, get_editTextMaster)
                        }

                        API_Server -> {

                            val get_editTextMaster = Constants.CUSTOM_API_SERVER_DOMAIN
                            checkMyConnection(get_UserID, get_LicenseKey, get_editTextMaster)

                        }

                    }


                } else {
                    showToastMessage("Select Partner Url")
                }

            } else if (get_Saved_Domains_Urls.isNotEmpty()) {

                // remove the toggle check for Sync Page
                val editor = sharedBiometric.edit()
                editor.remove(Constants.imagSwtichPartnerUrl)
                editor.apply()
                // test my connection
                checkMyConnection(get_UserID, get_LicenseKey, get_Saved_Domains_Urls)


            } else {
                showToastMessage("Select Custom Domain")
            }

        } else {
            showToastMessage("No Internet Connection")
        }
    }


    @SuppressLint("InflateParams", "SuspiciousIndentation")
    private fun serVerOptionDialog() {
        val bindingCm: CustomApiHardCodedLayoutBinding =
            CustomApiHardCodedLayoutBinding.inflate(layoutInflater)
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
                binding.texturlsSavedDownload.text = CP_server
                getUrlBasedOnSpinnerText = CP_server

                if (preferences!!.getBoolean("darktheme", false)) {
                    binding.texturlsSavedDownload.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )
                } else {

                    binding.texturlsSavedDownload.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.deep_blue
                        )
                    )
                }

                val editor = myDownloadClass.edit()
                editor.putString(Constants.Saved_Parthner_Name, CP_server)
                editor.putString(Constants.CP_OR_AP_MASTER_DOMAIN, Constants.CUSTOM_CP_SERVER_DOMAIN)
                editor.apply()

                alertDialog.dismiss()
            }


            textCloudServer.setOnClickListener {
                binding.texturlsSavedDownload.text = API_Server
                getUrlBasedOnSpinnerText = API_Server

                if (preferences!!.getBoolean("darktheme", false)) {

                    binding.texturlsSavedDownload.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )

                } else {

                    binding.texturlsSavedDownload.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.deep_blue
                        )
                    )
                }


                val editor = myDownloadClass.edit()
                editor.putString(Constants.Saved_Parthner_Name, API_Server)
                editor.putString(Constants.CP_OR_AP_MASTER_DOMAIN, Constants.CUSTOM_API_SERVER_DOMAIN)
                editor.apply()

                alertDialog.dismiss()
            }


            imageCrossClose.setOnClickListener {
                alertDialog.dismiss()
            }
            closeBs.setOnClickListener {
                alertDialog.dismiss()
            }


        }


        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun show_API_Urls() {
        custom_ApI_Dialog = Dialog(this)
        val bindingCm = CustomApiUrlLayoutBinding.inflate(LayoutInflater.from(this))
        custom_ApI_Dialog.setContentView(bindingCm.root)
        custom_ApI_Dialog.setCancelable(true)
        custom_ApI_Dialog.setCanceledOnTouchOutside(true)

        // Set the background of the AlertDialog to be transparent
        if (custom_ApI_Dialog.window != null) {
            custom_ApI_Dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            custom_ApI_Dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }




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




        if (Utility.isNetworkAvailable(this@TvActivityOrAppMode)) {
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

            mApiViewModel.apiUrls.observe(this@TvActivityOrAppMode, Observer { apiUrls ->
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
                if (Utility.isNetworkAvailable(this@TvActivityOrAppMode)) {
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
            binding.texturlsSavedDownload.text = name

            if (preferences!!.getBoolean("darktheme", false)) {
                binding.texturlsSavedDownload.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    )
                )
            } else {
                binding.texturlsSavedDownload.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.deep_blue
                    )
                )
            }


        }

        // Note - later you can use the url as well , the  name is displayed on textview
        if (name.isNotEmpty() && urls.isNotEmpty()) {
            val editor = simpleSavedPassword.edit()
            editor.putString(Constants.Saved_Domains_Name, name)
            editor.putString(Constants.Saved_Domains_Urls, urls)
            editor.apply()


            val editorDowbload = myDownloadClass.edit()
            editorDowbload.putString(Constants.Saved_Domains_Name, name)
            editorDowbload.putString(Constants.Saved_Domains_Urls, urls)
            editorDowbload.apply()


        }


        custom_ApI_Dialog.dismiss()
    }


    private fun checkMyConnection(
        get_UserID: String,
        get_LicenseKey: String,
        get_editTextMaster: String,
    ) {
        if (get_UserID.isNotEmpty() && get_LicenseKey.isNotEmpty() && get_editTextMaster.isNotEmpty()) {

            val baseUrl = "$get_editTextMaster/$get_UserID/$get_LicenseKey/App/Config/appConfig.json"

            if (get_editTextMaster.startsWith("https://") || get_editTextMaster.startsWith("http://")) {
                showCustomProgressDialog()

                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {

                            prefs.edit { putBoolean("button_clicked", true) }
                            startPermissionProcess()

                            val editorValue = simpleSavedPassword.edit()
                            editorValue.putString(Constants.get_masterDomain, baseUrl)
                            editorValue.putString(Constants.get_UserID, get_UserID)
                            editorValue.putString(Constants.get_LicenseKey, get_LicenseKey)
                            editorValue.putString(Constants.get_editTextMaster, get_editTextMaster)
                            editorValue.apply()


                            /// used for the getting first path which will be prefilled on Sync-page
                            val editorValueBio = myDownloadClass.edit()
                            editorValueBio.putString(Constants.getSavedCLOImPutFiled, get_UserID)
                            editorValueBio.putString(Constants.getSaveSubFolderInPutFiled, get_LicenseKey)


                            editorValueBio.putString(Constants.getFolderClo, get_UserID)
                            editorValueBio.putString(Constants.getFolderSubpath, get_LicenseKey)

                            editorValueBio.remove(Constants.SynC_Status)

                            editorValueBio.apply()

                            val user = User(
                                CLO = get_UserID,
                                DEMO = get_LicenseKey,
                                EditUrl = "",
                                EditUrlIndex = ""
                            )
                            mUserViewModel.addUser(user)


                            // use Paper Book to Save Use online CSv or Local CSv
                            Paper.book().write(Common.set_schedule_key, Common.schedule_online)

                            val editText88 = sharedBiometric.edit()
                            editText88.putString(Constants.imagSwtichEnableSyncFromAPI, Constants.imagSwtichEnableSyncFromAPI)
                            editText88.apply()


                            // check if Api or Custom server
                            if (binding.imgUserMasterDomainORCustom.isChecked) {
                                // save the modified url
                                val editorDownload = myDownloadClass.edit()
                                editorDownload.putString(Constants.get_ModifiedUrl, get_editTextMaster)
                                editorDownload.apply()
                            } else {
                                // save the custom url
                                val editorDownload = myDownloadClass.edit()
                                editorDownload.putString(Constants.get_ModifiedUrl, CP_AP_MASTER_DOMAIN)
                                editorDownload.apply()
                            }


                        } else {

                            showToastMessage("Invalid User")

                            if (customProgressDialog!=null){
                                customProgressDialog.dismiss()
                            }

                        }
                    } finally {
                        if (customProgressDialog!=null){
                            customProgressDialog.dismiss()
                        }

                    }
                }


            } else {

                showToastMessage("Invalid Master Url Format")

            }


        } else {
            if (get_UserID.isEmpty()) {
                binding.editTextUserID.error = "Input User Id"
            }

            if (get_LicenseKey.isEmpty()) {
                binding.editTextLicenseKey.error = "Input LicenseKey"
            }
        }
    }

    private fun showCustomProgressDialog() {
        try {
            customProgressDialog = Dialog(this)
            val binding = ProgressValidateUserDialogLayoutBinding.inflate(LayoutInflater.from(this))
            customProgressDialog.setContentView(binding.root)
            customProgressDialog.setCancelable(true)
            customProgressDialog.setCanceledOnTouchOutside(false)
            customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            customProgressDialog.show()
        } catch (_: Exception) {
        }
    }

    private fun isReadToMove_All_Permission() {
        binding.apply {

            if (customProgressDialog!=null){
                customProgressDialog.dismiss()
            }


            val editor = sharedBiometric.edit()
            if (navigateAppMolde) {


                startActivity(Intent(applicationContext, RequiredBioActivity::class.java))
                editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.App_Mode)
                editor.putString(Constants.FIRST_TIME_APP_START, Constants.FIRST_TIME_APP_START)
                editor.apply()
                finish()

                showToastMessage("Please wait")

            }


            if (navigateTVMode) {

                startActivity(Intent(applicationContext, RequiredBioActivity::class.java))
                editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.TV_Mode)
                editor.putString(Constants.CALL_RE_SYNC_MANGER, Constants.CALL_RE_SYNC_MANGER)
                editor.putString(Constants.FIRST_TIME_APP_START, Constants.FIRST_TIME_APP_START)
                editor.apply()
                finish()

                showToastMessage("Please wait")

            }

        }
    }



    override fun onResume() {
        super.onResume()
        if (btnisClicked){
            if (prefs.getBoolean("button_clicked", false)) {
                startPermissionProcess()
            }
        }


        val first_time_app_start = sharedBiometric.getString(Constants.FIRST_TIME_APP_START, "").toString()
        if (first_time_app_start.equals(Constants.FIRST_TIME_APP_START)) {

            startActivity(Intent(applicationContext, RequiredBioActivity::class.java))
            finish()
        }

        val get_savedDominName = simpleSavedPassword.getString(Constants.Saved_Domains_Name, "").toString()
        if (!get_savedDominName.isNullOrEmpty()) {
            binding.texturlsSavedDownload.text = get_savedDominName
        }




    }



    private fun startPermissionProcess() {
        when {
    /*        !isIgnoringBatteryOptimizations(this, packageName) -> {
                requestIgnoreBatteryOptimizations()
            }*/


            !Settings.System.canWrite(applicationContext) -> {
                showPopAllowAppToWriteSystem()
            }
            !Settings.canDrawOverlays(this) -> {
                showPop_For_Allow_Display_Over_Apps()
            }
            !checkStoragePermission(this) -> {
                showPop_For_Grant_Permsiion()
            }
            else -> {

                if (!isDialogPermissionShown){

                    handler.postDelayed(Runnable {

                        checkMultiplePermissions()

                    }, 500)
                }
            }
        }
    }





    /*
        private fun startPermissionProcess() {
            when {
                !isIgnoringBatteryOptimizations(this, packageName) -> {
                    requestIgnoreBatteryOptimizations()
                }
                !Settings.System.canWrite(applicationContext) -> {
                    showPopAllowAppToWriteSystem()
                }
                !Settings.canDrawOverlays(this) -> {
                    showPop_For_Allow_Display_Over_Apps()
                }
                !checkStoragePermission(this) -> {
                    showPop_For_Grant_Permsiion()
                }
                else -> {

                    if (!isDialogPermissionShown){

                        handler.postDelayed(Runnable {

                            checkMultiplePermissions()

                        }, 500)
                    }
                }
            }
        }
    */

    @SuppressLint("BatteryLife")
    private fun requestIgnoreBatteryOptimizations() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun isIgnoringBatteryOptimizations(context: Context, packageName: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isIgnoringBatteryOptimizations(packageName)
        } else {
            false
        }
    }

    private fun requestWriteSettingsPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }




    private fun checkOverlayBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun showPopAllowAppToWriteSystem() {
        val bindingCM: CustomeAllowAppWriteSystemBinding = CustomeAllowAppWriteSystemBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingCM.root)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)

        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }



        val permissionButton: TextView = bindingCM.textContinuPassword2
        val imgCloseDialog: ImageView = bindingCM.imgCloseDialog

        Utility.startPulseAnimationForText(bindingCM.imagSucessful)


        permissionButton.setOnClickListener {
            requestWriteSettingsPermission()
            alertDialog.dismiss()
        }

        imgCloseDialog.setOnClickListener {
            alertDialog.dismiss()
        }



        alertDialog.show()
    }





    @SuppressLint("MissingInflatedId")
    private fun showPop_For_Grant_Permsiion() {
        val bindingCM: CustomGrantAccessPageBinding = CustomGrantAccessPageBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingCM.root)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation

        val permissionButton: TextView = bindingCM.textContinuPassword2
        val imgCloseDialog: ImageView = bindingCM.imgCloseDialog

        Utility.startPulseAnimationForText(bindingCM.imagSucessful)


        permissionButton.setOnClickListener {
            requestStoragePermission(this@TvActivityOrAppMode)
            alertDialog.dismiss()
        }

        imgCloseDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }



    @SuppressLint("MissingInflatedId")
    private fun showPop_For_Allow_Display_Over_Apps() {
        val bindingCM: CustomPopDisplayOverAppsBinding = CustomPopDisplayOverAppsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingCM.root)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)
        // Set the background of the AlertDialog to be transparent
        if (alertDialog.window != null) {
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        }


        val permissionButton: TextView = bindingCM.textContinuPassword2
        val imgCloseDialog: ImageView = bindingCM.imgCloseDialog

        Utility.startPulseAnimationForText(bindingCM.imagSucessful)

        permissionButton.setOnClickListener {
            checkOverlayBackground()
            alertDialog.dismiss()
        }

        imgCloseDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle("Permission Required")
        builder.setMessage("Please grant the required permissions in the app settings to proceed.")

        isDialogPermissionShown = true

        builder.setPositiveButton("Go to Settings") { dialog: DialogInterface?, which: Int ->
            openAppSettings()
            dialog?.dismiss()
            isDialogPermissionShown = false
        }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
            showToastMessage("Permission Denied!")
            isDialogPermissionShown = false
        }
        builder.show()
    }




    private fun checkMultiplePermissions() {
        val listPermissionNeeded = arrayListOf<String>()
        for (permission in multiplePermissionNameList) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionNeeded.add(permission)
            }
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionNeeded.toTypedArray(),
                multiplePermissionId
            )
        } else {
            onAllPermissionsGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == multiplePermissionId) {
            if (grantResults.isNotEmpty()) {
                var isGranted = true
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        isGranted = false
                        break
                    }
                }
                if (isGranted) {
                    onAllPermissionsGranted()
                } else {
                    if (!isDialogPermissionShown) {
                        isDialogPermissionShown = true
                        showPermissionDeniedDialog()
                    }
                }
            }
        }
    }



    private fun onAllPermissionsGranted() {
        // Toast.makeText(this, "All permissions finally granted!", Toast.LENGTH_SHORT).show()
        btnisClicked = false

        showCustomProgressDialog()

        handler.postDelayed(Runnable {
            isReadToMove_All_Permission()
        },1000)
    }


    private fun showToastMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivity(intent)
    }




    private fun loadImage() {

        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath =
            myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
        val pathFolder =
            "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
        val folder =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/" + Constants.Syn2AppLive + "/" + pathFolder
        val fileTypes = "app_logo.png"
        val file = File(folder, fileTypes)
        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.imageView2)
        }


    }


    private fun loadBackGroundImage() {

        val fileTypes = "app_background.png"
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()

        val pathFolder =
            "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
        val folder =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + pathFolder
        val file = File(folder, fileTypes)

        if (file.exists()) {
            Glide.with(this).load(file).centerCrop().into(binding.backgroundImage)

        }
    }


    private fun setUpdarkUITheme() {
        // write code for dark theme
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        binding.apply {
            if (preferences!!.getBoolean("darktheme", false)) {


                parentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))
                constraintLayoutSlectDomain.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)

                // set text view

                textView2.setTextColor(resources.getColor(R.color.white))

                // text view in inner container
                textView3.setTextColor(resources.getColor(R.color.white))
                textPartnerUrlLunch.setTextColor(resources.getColor(R.color.white))
                textView16.setTextColor(resources.getColor(R.color.white))
                textAppMode.setTextColor(resources.getColor(R.color.white))
                textView15.setTextColor(resources.getColor(R.color.white))
                texturlsSavedDownload.setTextColor(resources.getColor(R.color.white))


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
                imgUserMasterDomainORCustom.thumbTintList = thumbColorStateList
                imgUserMasterDomainORCustom.trackTintList = trackColorStateList


                val drawable_imagSpannerSavedDownload =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_drop_down_24)
                drawable_imagSpannerSavedDownload?.setColorFilter(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                imagSpannerSavedDownload.setImageDrawable(drawable_imagSpannerSavedDownload)


                // test connection buttons and connect buttons
                textTvMode.setTextColor(resources.getColor(R.color.white))
                textTvMode.setBackgroundResource(R.drawable.card_design_darktheme_outline)

                textAppMode.setTextColor(resources.getColor(R.color.white))
                textAppMode.setBackgroundResource(R.drawable.card_design_darktheme)


                // for edit text values
                editTextUserID.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextUserID.setTextColor(resources.getColor(R.color.white))
                editTextUserID.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editTextLicenseKey.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editTextLicenseKey.setTextColor(resources.getColor(R.color.white))
                editTextLicenseKey.setHintTextColor(resources.getColor(R.color.light_gray_wash))


            }
        }

    }

}
