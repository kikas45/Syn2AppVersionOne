package sync2app.com.syncapplive.additionalSettings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.additionalSettings.USBCamera.CamModel.CamRetrofitInstance
import sync2app.com.syncapplive.additionalSettings.usdbCamera.InputFilterMinMax
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityUsbCamConfigBinding
import sync2app.com.syncapplive.databinding.CustomDefinedTimeIntervalsBinding
import java.io.File

class UsbCamConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsbCamConfigBinding

    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC,
            Context.MODE_PRIVATE
        )
    }

    private val simpleSavedPassword: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SIMPLE_SAVED_PASSWORD,
            Context.MODE_PRIVATE
        )
    }


    private val sharedCameraPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_CAMERA_PREF,
            Context.MODE_PRIVATE
        )
    }


    private val myDownloadClass: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS,
            Context.MODE_PRIVATE
        )
    }


    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    // define time
    private var Minutes = " Minutes"
    private var timeMinuetes22 = 2L
    private var timeMinuetes55 = 5L
    private var timeMinuetes10 = 10L
    private var timeMinuetes15 = 15L
    private var timeMinuetes30 = 30L
    private var timeMinuetes60 = 60L
    private var timeMinuetes120 = 120L
    private var timeMinuetes180 = 180L
    private var timeMinuetes240 = 240L

//    private var getSavedable_Hide_Time = 0L
//    private var getSavedable_Display_Time = 0L

    private val MIN_H_W = 0
    private val MAX_H_W = 100

    private var preferences: SharedPreferences? = null



    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsbCamConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        val getState = sharedBiometric.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString()
        if (getState == Constants.ENABLE_LANDSCAPE_MODE){
            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            *//* if (getState.isNullOrEmpty()){
                 requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
             }else{
                 requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
             }*//*
        }*/


        val getState = sharedBiometric.getString(Constants.ENABLE_LANDSCAPE_MODE, "").toString()
        if (getState == Constants.ENABLE_LANDSCAPE_MODE){

            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        binding.apply {
            if (preferences!!.getBoolean("darktheme", false)) {

                // Set status bar color
                window?.statusBarColor = Color.parseColor("#171616")
                window?.navigationBarColor = Color.parseColor("#171616")
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false


                myparentContainer.setBackgroundColor(resources.getColor(R.color.dark_layout_for_ui))

                // set text view

                textView42.setTextColor(resources.getColor(R.color.white))


                textUseUsbCamera.setTextColor(resources.getColor(R.color.white))
                textEnableStreamVideo.setTextColor(resources.getColor(R.color.white))
                textFloatAndexpand.setTextColor(resources.getColor(R.color.white))
                textPositionAngles.setTextColor(resources.getColor(R.color.white))
                textStreamAudioSound.setTextColor(resources.getColor(R.color.white))
                textUseAPIorDeviceSource.setTextColor(resources.getColor(R.color.white))
                textWindowDisplayInterval.setTextColor(resources.getColor(R.color.white))
                textPositionAngles.setTextColor(resources.getColor(R.color.white))
                textDisplayTime.setTextColor(resources.getColor(R.color.white))
                textHideTime.setTextColor(resources.getColor(R.color.white))
                textTime30minuteDisplay.setTextColor(resources.getColor(R.color.white))
                textTime30minuteHideTime.setTextColor(resources.getColor(R.color.white))
                textErrorText.setTextColor(resources.getColor(R.color.white))
                textStartHeight.setTextColor(resources.getColor(R.color.white))
                textEndHeight.setTextColor(resources.getColor(R.color.white))
                textStartWidthPos.setTextColor(resources.getColor(R.color.white))
                textEndWidth.setTextColor(resources.getColor(R.color.white))


                // fir back button
                val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_arrow)
                drawable?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.white), PorterDuff.Mode.SRC_IN)
                closeBs.setImageDrawable(drawable)


                // for all forward icon
                val drawable_imageView6 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_branding)
                drawable_imageView6?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView51.setImageDrawable(drawable_imageView6)
                imageView49.setImageDrawable(drawable_imageView6)
                imageView50.setImageDrawable(drawable_imageView6)
                imageView41.setImageDrawable(drawable_imageView6)
                imageView52.setImageDrawable(drawable_imageView6)


                // for nav icons
                val drawable_imageView4 = ContextCompat.getDrawable(applicationContext, R.drawable.ic_arrow_move_front)
                drawable_imageView4?.setColorFilter(ContextCompat.getColor(applicationContext, R.color.deep_blue_light_extra), PorterDuff.Mode.SRC_IN)
                imageView4.setImageDrawable(drawable_imageView4)
                imageView56.setImageDrawable(drawable_imageView4)



                textSavesettings.setBackgroundResource(R.drawable.round_edit_text_design_theme_extra)








                editInputStartHeightPosition.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editInputStartHeightPosition.setTextColor(resources.getColor(R.color.white))
                editInputStartHeightPosition.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editInputEndHeightPosition.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editInputEndHeightPosition.setTextColor(resources.getColor(R.color.white))
                editInputEndHeightPosition.setHintTextColor(resources.getColor(R.color.light_gray_wash))


                editInputStartWidthPosition.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editInputStartWidthPosition.setTextColor(resources.getColor(R.color.white))
                editInputStartWidthPosition.setHintTextColor(resources.getColor(R.color.light_gray_wash))

                editInpuEndWidthPosition.setBackgroundResource(R.drawable.round_edit_text_design_outline_dark_theme)
                editInpuEndWidthPosition.setTextColor(resources.getColor(R.color.white))
                editInpuEndWidthPosition.setHintTextColor(resources.getColor(R.color.light_gray_wash))









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
                imgStreamAudioSound.thumbTintList = thumbColorStateList
                imgStreamAudioSound.trackTintList = trackColorStateList

                imgStreamAPIorDevice.thumbTintList = thumbColorStateList
                imgStreamAPIorDevice.trackTintList = trackColorStateList

                imgEnableDisplayIntervals.thumbTintList = thumbColorStateList
                imgEnableDisplayIntervals.trackTintList = trackColorStateList

                imgUseDevicecameraOrPlugInCamera.thumbTintList = thumbColorStateList
                imgUseDevicecameraOrPlugInCamera.trackTintList = trackColorStateList

                imgStreamVideo.thumbTintList = thumbColorStateList
                imgStreamVideo.trackTintList = trackColorStateList

                imgEnableExpandFloat.thumbTintList = thumbColorStateList
                imgEnableExpandFloat.trackTintList = trackColorStateList



                //  for divider i..n
                divider21.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider44.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider58.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider51.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider53.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider54.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider63.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider65.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
                divider64.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_gray))

            }
        }







        val get_imgToggleImageBackground = sharedBiometric.getString(Constants.imgToggleImageBackground, "")
        val get_imageUseBranding = sharedBiometric.getString(Constants.imageUseBranding, "")
        if (get_imgToggleImageBackground.equals(Constants.imgToggleImageBackground) && get_imageUseBranding.equals(Constants.imageUseBranding) ){
            loadBackGroundImage()
        }



        binding.apply {


            // set display time
            textTime30minuteDisplay.setOnClickListener {
                if (!imgStreamAPIorDevice.isChecked) {
                    defined_Display_TimeIntervals()
                } else {
                    showToastMessage("First Enable Device Mode")
                }
            }

            textDisplayTime.setOnClickListener {
                if (!imgStreamAPIorDevice.isChecked) {
                    defined_Display_TimeIntervals()
                } else {
                    showToastMessage("First Enable Device Mode")
                }
            }

            // set hide time
            textTime30minuteHideTime.setOnClickListener {
                if (!imgStreamAPIorDevice.isChecked) {
                    defined_Hide_TimeIntervals()

                } else {
                    showToastMessage("First Enable Device Mode")
                }
            }

            textHideTime.setOnClickListener {
                if (!imgStreamAPIorDevice.isChecked) {
                    defined_Hide_TimeIntervals()

                } else {
                    showToastMessage("First Enable Device Mode")
                }
            }




            closeBs.setOnClickListener {
                onBackPressed()

            }

            textSavesettings.setOnClickListener {

                if (imgStreamAPIorDevice.isChecked) {
                    val get_tMaster =
                        simpleSavedPassword.getString(Constants.get_editTextMaster, "").toString()
                    val get_UserID =
                        simpleSavedPassword.getString(Constants.get_UserID, "").toString()
                    val get_LicenseKey =
                        simpleSavedPassword.getString(Constants.get_LicenseKey, "").toString()

                    val dynamicPart = "$get_tMaster/$get_UserID/$get_LicenseKey/App/USBCAM/DisplaySettings.json"

                    if (isNetworkAvailable()) {
                        fetchApiCameraData(get_tMaster, dynamicPart)
                    } else {
                        showToastMessage("No Internet Connection")
                        binding.progressBar.visibility = View.GONE
                        binding.viewCover.visibility = View.GONE
                        binding.textErrorText.visibility = View.GONE
                    }

                } else {

                    saveUSBCamSettings()
                    binding.progressBar.visibility = View.VISIBLE
                    binding.viewCover.visibility = View.VISIBLE
                    binding.textErrorText.visibility = View.VISIBLE

                    handler.postDelayed(Runnable {
                        val intent = Intent(applicationContext, AdditionalSettingsActivity::class.java)
                        startActivity(intent)
                        finish()

                        binding.progressBar.visibility = View.GONE
                        binding.viewCover.visibility = View.GONE
                        binding.textErrorText.visibility = View.GONE

                    }, 1000)


                }
            }


            //
            initliazeUsbCamsettings()
            setLayoutParameters()
        }


    }

    private fun setLayoutParameters() {
        try {
            binding.apply {
                val inputFilter = arrayOf<InputFilter>(InputFilterMinMax(MIN_H_W, MAX_H_W))
                editInputStartHeightPosition.setFilters(inputFilter)
                editInputEndHeightPosition.setFilters(inputFilter)
                editInputStartWidthPosition.setFilters(inputFilter)
                editInpuEndWidthPosition.setFilters(inputFilter)
            }
        } catch (_: Exception) {
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchApiCameraData(baseUrl: String, dynamicPart: String) {

        binding.progressBar.visibility = View.VISIBLE
        binding.viewCover.visibility = View.VISIBLE
        binding.textErrorText.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val api = CamRetrofitInstance.create(baseUrl)
                val response = api.getAppConfig(dynamicPart)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        val start_height_api = response.body()?.start_height.toString()
                        val end_height_api = response.body()?.end_height.toString()
                        val start_width_api = response.body()?.start_width.toString()
                        val end_width_api = response.body()?.end_width.toString()
                        val display_time_api = response.body()?.display_time.toString()
                        val hide_time_api = response.body()?.hide_time.toString()

                        showToastMessage("API Settings Saved")

                        val editor = sharedCameraPreferences.edit()
                        editor.putString(Constants.start_height_api, start_height_api)
                        editor.putString(Constants.end_height_api, end_height_api)
                        editor.putString(Constants.start_width_api, start_width_api)
                        editor.putString(Constants.end_width_api, end_width_api)
                        editor.putString(Constants.display_time_api, display_time_api)
                        editor.putString(Constants.hide_time_api, hide_time_api)
                        editor.apply()

                        saveUSBCamSettings()

                        handler.postDelayed(Runnable {
                            val intent = Intent(applicationContext, AdditionalSettingsActivity::class.java)
                            startActivity(intent)
                            finish()

                            binding.progressBar.visibility = View.GONE
                            binding.viewCover.visibility = View.GONE
                            binding.textErrorText.visibility = View.GONE

                        }, 500)


                    } else {
                        showToastMessage("bad request")
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    showToastMessage("HTTP Exception: ${e.message()}")
                    binding.progressBar.visibility = View.GONE
                    binding.viewCover.visibility = View.GONE
                    binding.textErrorText.visibility = View.GONE

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToastMessage("Error: ${e.message}")

                    binding.progressBar.visibility = View.GONE
                    binding.viewCover.visibility = View.GONE
                    binding.textErrorText.visibility = View.GONE
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    private fun initliazeUsbCamsettings() {


        iniliaze_Device_And_API_values()


        binding.apply {

            // enable Usb camera
            val get_imgUseDevicecameraOrPlugInCamera =
                sharedBiometric.getString(Constants.imgUseDevicecameraOrPlugInCamera, "")
            imgUseDevicecameraOrPlugInCamera.isChecked =
                get_imgUseDevicecameraOrPlugInCamera.equals(Constants.imgUseDevicecameraOrPlugInCamera)

            if (get_imgUseDevicecameraOrPlugInCamera.equals(Constants.imgUseDevicecameraOrPlugInCamera)) {
                binding.textUseUsbCamera.text = "Device Live Stream"

            } else {
                binding.textUseUsbCamera.text = "USB Live Stream"
            }


            imgUseDevicecameraOrPlugInCamera.setOnCheckedChangeListener { compoundButton, isValued ->

                if (compoundButton.isChecked) {
                    binding.textUseUsbCamera.text = "Device Live Stream"
                } else {
                    binding.textUseUsbCamera.text = "USB Live Stream"
                }
            }


            // enable video
            val get_imgStreamVideo = sharedBiometric.getString(Constants.imgStreamVideo, "")
            imgStreamVideo.isChecked = get_imgStreamVideo.equals(Constants.imgStreamVideo)
            if (get_imgStreamVideo.equals(Constants.imgStreamVideo)) {

                binding.textEnableStreamVideo.text = "Stream Video"

            } else {
                binding.textEnableStreamVideo.text = "Do not Stream Video"
            }


            imgStreamVideo.setOnCheckedChangeListener { compoundButton, isValued ->
                if (compoundButton.isChecked) {
                    binding.textEnableStreamVideo.text = "Stream Video"
                } else {
                    binding.textEnableStreamVideo.text = "Do not Stream Video"
                }
            }


            // enable floatimg or api
            val get_imgEnableExpandFloat =
                sharedBiometric.getString(Constants.imgEnableExpandFloat, "")
            imgEnableExpandFloat.isChecked =
                get_imgEnableExpandFloat.equals(Constants.imgEnableExpandFloat)
            if (get_imgEnableExpandFloat.equals(Constants.imgEnableExpandFloat)) {

                binding.textFloatAndexpand.text = "Disable Float and Expand"

            } else {

                binding.textFloatAndexpand.text = "Allow Float and Expand"
            }

            imgEnableExpandFloat.setOnCheckedChangeListener { compoundButton, isValued ->

                if (compoundButton.isChecked) {
                    binding.textFloatAndexpand.text = "Disable Float and Expand"
                } else {
                    binding.textFloatAndexpand.text = "Allow Float and Expand"
                }

            }


            // enable device or Api
            val get_imgStreamAPIorDevice =
                sharedBiometric.getString(Constants.imgStreamAPIorDevice, "")
            imgStreamAPIorDevice.isChecked =
                get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)
            if (get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)) {
                binding.textUseAPIorDeviceSource.text = "Use API"

            } else {
                binding.textUseAPIorDeviceSource.text = "Use Device"
            }


            imgStreamAPIorDevice.setOnCheckedChangeListener { compoundButton, isValued ->

                if (compoundButton.isChecked) {
                    binding.textUseAPIorDeviceSource.text = "Use API"
                    saveSettingForApi()
                } else {
                    binding.textUseAPIorDeviceSource.text = "Use Device"
                    saveSettingForDevice()
                }


            }


            /// enable or diable Audio
            val get_imgStreamAudioSound =
                sharedBiometric.getString(Constants.imgStreamAudioSound, "")
            imgStreamAudioSound.isChecked =
                get_imgStreamAudioSound.equals(Constants.imgStreamAudioSound)
            if (get_imgStreamAudioSound.equals(Constants.imgStreamAudioSound)) {
                binding.textStreamAudioSound.text = "Disable Audio"

            } else {
                binding.textStreamAudioSound.text = "Enable Audio"
            }

            imgStreamAudioSound.setOnCheckedChangeListener { compoundButton, isValued ->

                if (compoundButton.isChecked) {
                    binding.textStreamAudioSound.text = "Disable Audio"
                } else {
                    binding.textStreamAudioSound.text = "Enable Audio"
                }

            }


            /// enable or Display Interval
            val get_imgEnableDisplayIntervals =
                sharedBiometric.getString(Constants.imgEnableDisplayIntervals, "")
            imgEnableDisplayIntervals.isChecked =
                get_imgEnableDisplayIntervals.equals(Constants.imgEnableDisplayIntervals)

            if (get_imgEnableDisplayIntervals.equals(Constants.imgEnableDisplayIntervals)) {
                binding.textWindowDisplayInterval.text = "Enable Window Display Interval"

            } else {
                binding.textWindowDisplayInterval.text = "Disable Window Display Interval"
            }

            imgEnableDisplayIntervals.setOnCheckedChangeListener { compoundButton, isValued ->

                if (compoundButton.isChecked) {
                    binding.textWindowDisplayInterval.text = "Enable Window Display Interval"
                } else {
                    binding.textWindowDisplayInterval.text = "Disable Window Display Interval"
                }

            }

        }
    }


    @SuppressLint("SetTextI18n")
    private fun iniliaze_Device_And_API_values() {

        binding.apply {

            val get_imgStreamAPIorDevice =
                sharedBiometric.getString(Constants.imgStreamAPIorDevice, "")

            if (get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)) {

                saveSettingForApi()

            } else {
                saveSettingForDevice()

            }


        }

        try {
        } catch (_: Exception) {

        }
    }


    private fun saveSettingForApi() {
        binding.apply {
            val startY: String =
                sharedCameraPreferences.getString(Constants.start_height_api, "").toString()
                    .trim()
            val camHeight: String =
                sharedCameraPreferences.getString(Constants.end_height_api, "").toString()
                    .trim()
            val startX: String =
                sharedCameraPreferences.getString(Constants.start_width_api, "").toString()
                    .trim()
            val camWidth: String =
                sharedCameraPreferences.getString(Constants.end_width_api, "").toString()
                    .trim()
            val display_time_api: String =
                sharedCameraPreferences.getString(Constants.display_time_api, "").toString()
                    .trim()
            val hide_time_api: String =
                sharedCameraPreferences.getString(Constants.hide_time_api, "").toString().trim()

            // for device


            if (!startY.isNullOrEmpty()) {
                editInputStartHeightPosition.setText(startY)
            }

            if (!camHeight.isNullOrEmpty()) {
                editInputEndHeightPosition.setText(camHeight)
            }

            if (!startX.isNullOrEmpty()) {
                editInputStartWidthPosition.setText(startX)
            }

            if (!camWidth.isNullOrEmpty()) {
                editInpuEndWidthPosition.setText(camWidth)
            }

            if (!display_time_api.isNullOrEmpty()) {
                textTime30minuteDisplay.text = display_time_api
            }

            if (!hide_time_api.isNullOrEmpty()) {
                textTime30minuteHideTime.text = display_time_api
            }


        }
    }


    private fun saveSettingForDevice() {
        binding.apply {
            val startY: String =
                sharedCameraPreferences.getString(Constants.startY, "").toString().trim()
            val camHeight: String =
                sharedCameraPreferences.getString(Constants.camHeight, "").toString().trim()
            val startX: String =
                sharedCameraPreferences.getString(Constants.startX, "").toString().trim()
            val camWidth: String =
                sharedCameraPreferences.getString(Constants.camWidth, "").toString().trim()


            if (startY.isNotEmpty()) {
                editInputStartHeightPosition.setText(startY)
            }

            if (camHeight.isNotEmpty()) {
                editInputEndHeightPosition.setText(camHeight)
            }

            if (startX.isNotEmpty()) {
                editInputStartWidthPosition.setText(startX)
            }

            if (!camWidth.isNullOrEmpty()) {
                editInpuEndWidthPosition.setText(camWidth)
            }


            val get_Hide_Camera: Long = sharedCameraPreferences.getLong(
                Constants.get_Hide_Camera_Defined_Time_for_Device,
                0
            )
            val get_Display_Camera: Long = sharedCameraPreferences.getLong(
                Constants.get_Display_Camera_Defined_Time_for_Device,
                0
            )

            if (get_Display_Camera != 0L) {
                textTime30minuteDisplay.text = "$get_Display_Camera $Minutes"
            }

            if (get_Hide_Camera != 0L) {
                textTime30minuteHideTime.text = "$get_Hide_Camera $Minutes"
            }


            // make the default time be 30 min

            val editor = sharedCameraPreferences.edit();

            if (get_Display_Camera == 0L) {
                editor.putLong(
                    Constants.get_Display_Camera_Defined_Time_for_Device,
                    Constants.t_30min
                )
            }
            if (get_Hide_Camera == 0L) {
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_30min)
            }
            editor.apply();

        }

    }


    private fun saveUSBCamSettings() {
        binding.apply {
            val editor = sharedBiometric.edit()


            // enable Usb camera
            if (imgUseDevicecameraOrPlugInCamera.isChecked) {
                editor.putString(
                    Constants.imgUseDevicecameraOrPlugInCamera,
                    "imgUseDevicecameraOrPlugInCamera"
                )
                editor.apply()
                binding.textUseUsbCamera.text = "Device Camera"

            } else {
                editor.remove(Constants.imgUseDevicecameraOrPlugInCamera)
                editor.apply()
                binding.textUseUsbCamera.text = "USB Camera"
            }


            // enable video camera
            if (imgStreamVideo.isChecked) {

                editor.putString(Constants.imgStreamVideo, "imgStreamVideo")
                editor.apply()
                binding.textEnableStreamVideo.text = "Stream Video"

            } else {
                editor.remove(Constants.imgStreamVideo)
                editor.apply()
                binding.textEnableStreamVideo.text = "Do not Stream Video"
            }


            // enable float
            if (imgEnableExpandFloat.isChecked) {

                editor.putString(Constants.imgEnableExpandFloat, "imgEnableExpandFloat")
                editor.apply()
                binding.textFloatAndexpand.text = "Disable Float and Expand"

            } else {
                editor.remove(Constants.imgEnableExpandFloat)
                editor.apply()
                binding.textFloatAndexpand.text = "Allow Float and Expand"
            }


            // enable api
            if (imgStreamAPIorDevice.isChecked) {

                editor.putString(Constants.imgStreamAPIorDevice, "imgStreamAPIorDevice")
                editor.apply()
                binding.textUseAPIorDeviceSource.text = "Use API"


            } else {
                editor.remove(Constants.imgStreamAPIorDevice)
                editor.apply()
                binding.textUseAPIorDeviceSource.text = "Use Device"

            }


            // enable audio

            if (imgStreamAudioSound.isChecked) {
                editor.putString(Constants.imgStreamAudioSound, "imgStreamAudioSound")
                editor.apply()
                binding.textStreamAudioSound.text = "Disable Audio"

            } else {
                editor.remove(Constants.imgStreamAudioSound)
                editor.apply()
                binding.textStreamAudioSound.text = "Enable Audio"
            }


            // enable display intervals
            if (imgEnableDisplayIntervals.isChecked) {
                editor.putString(Constants.imgEnableDisplayIntervals, "imgEnableDisplayIntervals")
                editor.apply()
                binding.textWindowDisplayInterval.text = "Enable Window Display Interval"

            } else {
                editor.remove(Constants.imgEnableDisplayIntervals)
                editor.apply()
                binding.textWindowDisplayInterval.text = "Disable Window Display Interval"
            }


            // save edit text values
            saveCameraCordinates_For_Device()


        }

    }

    @SuppressLint("CommitPrefEdits")
    private fun saveCameraCordinates_For_Device() {

        binding.apply {

            if (!imgStreamAPIorDevice.isChecked) {
                val startY: String = editInputStartHeightPosition.getText().toString().trim()
                val camHeight: String = editInputEndHeightPosition.getText().toString().trim()
                val startX: String = editInputStartWidthPosition.getText().toString().trim()
                val camWidth: String = editInpuEndWidthPosition.getText().toString().trim()

                val editor = sharedCameraPreferences.edit();
                editor.putString(Constants.startY, startY)
                editor.putString(Constants.camHeight, camHeight)
                editor.putString(Constants.startX, startX)
                editor.putString(Constants.camWidth, camWidth)
                editor.apply();
            }

        }
    }


    private fun showToastMessage(message: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, AdditionalSettingsActivity::class.java)
        startActivity(intent)
        finish()
    }


    @SuppressLint("InflateParams", "SuspiciousIndentation", "SetTextI18n")
    private fun defined_Display_TimeIntervals() {
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
        }

        bindingCm.apply {
            val editor = sharedCameraPreferences.edit();


            imageCrossClose.setOnClickListener {
                alertDialog.dismiss()
            }

            closeBs.setOnClickListener {
                alertDialog.dismiss()
            }

            textTitle.text = "Display Time"


            textTwoMinutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes22 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_2min)
                editor.apply();
                alertDialog.dismiss()

            }



            text55minutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes55 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_5min)
                editor.apply();
                alertDialog.dismiss()
            }

            text100minutes2.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes10 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_10min)
                editor.apply();
                alertDialog.dismiss()

            }


            text1500minutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes15 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_15min)
                editor.apply();

                alertDialog.dismiss()

            }

            text3000minutes2.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes30 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_30min)
                editor.apply();
                alertDialog.dismiss()

            }

            text6000minutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes60 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_60min)
                editor.apply();
                alertDialog.dismiss()

            }


            textOneTwentyMinutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes120 $Minutes"
                editor.putLong(Constants.get_Display_Camera_Defined_Time_for_Device, Constants.t_120min)
                editor.apply();
                alertDialog.dismiss()
            }



            textOneEightThyMinutes2.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes180 $Minutes"
                editor.putLong(
                    Constants.get_Display_Camera_Defined_Time_for_Device,
                    Constants.t_180min
                )
                editor.apply();
                alertDialog.dismiss()

            }


            tex24000ThyMinutes.setOnClickListener {
                binding.textTime30minuteDisplay.text = "$timeMinuetes240 $Minutes"
                editor.putLong(
                    Constants.get_Display_Camera_Defined_Time_for_Device,
                    Constants.t_240min
                )
                editor.apply();
                alertDialog.dismiss()

            }



            alertDialog.show()
        }


    }


    @SuppressLint("InflateParams", "SuspiciousIndentation", "SetTextI18n")
    private fun defined_Hide_TimeIntervals() {
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
        }

        bindingCm.apply {

            val editor = sharedCameraPreferences.edit();


            imageCrossClose.setOnClickListener {
                alertDialog.dismiss()
            }

            closeBs.setOnClickListener {
                alertDialog.dismiss()
            }

            textTitle.text = "Display Time"


            textTwoMinutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes22.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_2min)
                editor.apply();

                alertDialog.dismiss()

            }



            text55minutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes55.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_5min)
                editor.apply();

                alertDialog.dismiss()
            }

            text100minutes2.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes10.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_10min)
                editor.apply();
                alertDialog.dismiss()

            }


            text1500minutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes15.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_15min)
                editor.apply();
                alertDialog.dismiss()

            }

            text3000minutes2.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes30.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_30min)
                editor.apply();
                alertDialog.dismiss()

            }

            text6000minutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes60.toString() + " $Minutes"
                editor.putLong(Constants.get_Hide_Camera_Defined_Time_for_Device, Constants.t_60min)
                editor.apply();
                alertDialog.dismiss()

            }


            textOneTwentyMinutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes120.toString() + " $Minutes"
                editor.putLong(
                    Constants.get_Hide_Camera_Defined_Time_for_Device,
                    Constants.t_120min
                )
                editor.apply();
                alertDialog.dismiss()
            }



            textOneEightThyMinutes2.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes180.toString() + " $Minutes"
                editor.putLong(
                    Constants.get_Hide_Camera_Defined_Time_for_Device,
                    Constants.t_180min
                )
                editor.apply();
                alertDialog.dismiss()

            }


            tex24000ThyMinutes.setOnClickListener {
                binding.textTime30minuteHideTime.text = timeMinuetes240.toString() + " $Minutes"
                editor.putLong(
                    Constants.get_Hide_Camera_Defined_Time_for_Device,
                    Constants.t_240min
                )
                editor.apply();
                alertDialog.dismiss()

            }


            alertDialog.show()
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