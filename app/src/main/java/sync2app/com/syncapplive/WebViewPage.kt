package sync2app.com.syncapplive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.SurfaceTexture
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraManager
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.preference.PreferenceManager
import android.text.Html
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.TextureView.SurfaceTextureListener
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.bumptech.glide.Glide
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.opencsv.CSVReader
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.Fetch.Impl.getInstance
import com.tonyodev.fetch2.FetchConfiguration
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2.HttpUrlConnectionDownloader
import com.tonyodev.fetch2.NetworkType
import com.tonyodev.fetch2.Priority
import com.tonyodev.fetch2.Request
import com.tonyodev.fetch2core.DownloadBlock
import com.tonyodev.fetch2core.Downloader.FileDownloaderType
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import sync2app.com.syncapplive.QrPages.QRSanActivity
import sync2app.com.syncapplive.additionalSettings.OnFileChange.Retro_On_Change
import sync2app.com.syncapplive.additionalSettings.ReSyncActivity
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.api.RetrofitClient
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.models.AppSettings
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.models.Schedule
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.Common
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.MethodsSchedule
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesApi
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesViewModel
import sync2app.com.syncapplive.additionalSettings.scanutil.CustomShortcutsDemo
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkUrlExistence
import sync2app.com.syncapplive.additionalSettings.usdbCamera.MyUsb.CameraHandlerKT
import sync2app.com.syncapplive.additionalSettings.usdbCamera.MyUsb.kotlionCode.AudioHandlerKT
import sync2app.com.syncapplive.additionalSettings.utils.CSVDownloader
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.additionalSettings.utils.IndexFileChecker
import sync2app.com.syncapplive.additionalSettings.utils.Utility
import sync2app.com.syncapplive.constants.isAppOpen
import sync2app.com.syncapplive.constants.jsonUrl
import sync2app.com.syncapplive.databinding.ActivityWebViewPageBinding
import sync2app.com.syncapplive.databinding.CustomNotificationLayoutBinding
import sync2app.com.syncapplive.databinding.CustomOfflinePopLayoutBinding
import sync2app.com.syncapplive.glidetovectoryou.GlideToVectorYou
import sync2app.com.syncapplive.glidetovectoryou.GlideToVectorYouListener
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects
import java.util.zip.ZipInputStream


class WebViewPage : AppCompatActivity(){
    private lateinit var binding: ActivityWebViewPageBinding

    //dynamic values
    private var currentSettings: AppSettings? = null


    private var isSchedule = false
    private var isScheduleRunning = false


    // check if API or ZIP
    private var isAPISyncRunning = false


    //data
    private val tempList: MutableList<Schedule> = mutableListOf()
    private val theSchedules: MutableList<Schedule> = mutableListOf()
    private val setAlarms: MutableList<Schedule> = mutableListOf()
    private val enteredSchedules: MutableList<String> = mutableListOf()
    private val enteredAlarms: MutableList<String> = mutableListOf()

    //data
    private var scheduleFile: File? = null


    private var getServer_timeStamp = ""
    var isScheduleCurrentlyRunning = false
    private val currentScheduleTime = ""

    //handler
    private var handlerSchedule = Handler(Looper.getMainLooper())
    private var handlerRunningSchedule = Handler(Looper.getMainLooper())
    private var handlerDeviceTime = Handler(Looper.getMainLooper())
    private var handlerServerTime = Handler(Looper.getMainLooper())
    private var runnableSchedule: Runnable? = null
    private var runnableRunningSchedule: Runnable? = null
    private var runnableServerTime: Runnable? = null
    private var runnableDeviceTime: Runnable? = null


    private var deviceTime: TextView? = null
    private var serverTime: TextView? = null
    private var scheduleEnd: TextView? = null
    private var scheduleStart: TextView? = null


    /// for schedule media
     // val FILECHOOSER_RESULTCODE = 5173
    private val TAG = "WebViewPage"
   // private val FCR = 1
    //  var mUploadMessage: ValueCallback<Uri>? = null

    //Adjusting Rating bar popup timeframe
   // private val DAYS_UNTIL_PROMPT = 0 //Min number of days

    private val LAUNCHES_UNTIL_PROMPT = 5 //Min number of app launches

   // var openblobPdfafterDownload = true

    var ChangeListener = false
   // var storagecamrequest = false

    var errorlayout: LinearLayout? = null
    // var mContext: Context? = null


    private var countdownTimer_Api_Sync: CountDownTimer? = null
    private var countdownTimer_Short_Cut: CountDownTimer? = null
    private var countdownTimer_App_Refresh: CountDownTimer? = null

    private var connectivityReceiver: ConnectivityReceiver? = null

    private var alertDialog: AlertDialog? = null

    private var myHandler: Handler? = null

    private var webView: WebView? = null

    private var drawer_menu: RelativeLayout? = null


    @RequiresApi(Build.VERSION_CODES.Q)
    private var imgClk = View.OnClickListener { v ->
        val buttonClick = AlphaAnimation(0.1f, 0.4f)
        v.startAnimation(buttonClick)
        when (v.id) {
            R.id.bottomtoolbar_btn_1 -> HandleRemoteCommand(constants.bottomUrl1)
            R.id.bottomtoolbar_btn_2 -> HandleRemoteCommand(constants.bottomUrl2)
            R.id.bottomtoolbar_btn_3 -> HandleRemoteCommand(constants.bottomUrl3)
            R.id.bottomtoolbar_btn_4 -> HandleRemoteCommand(constants.bottomUrl4)
            R.id.bottomtoolbar_btn_5 -> HandleRemoteCommand(constants.bottomUrl5)
            R.id.bottomtoolbar_btn_6 -> HandleRemoteCommand(constants.bottomUrl6)
            R.id.drawer_menu_Btn -> HandleRemoteCommand(constants.drawerMenuBtnUrl)
            R.id.drawer_item_1 -> {
                HandleRemoteCommand(constants.drawerMenuItem1Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_item_2 -> {
                HandleRemoteCommand(constants.drawerMenuItem2Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_item_3 -> {
                HandleRemoteCommand(constants.drawerMenuItem3Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_item_4 -> {
                HandleRemoteCommand(constants.drawerMenuItem4Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_item_5 -> {
                HandleRemoteCommand(constants.drawerMenuItem5Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_item_6 -> {
                HandleRemoteCommand(constants.drawerMenuItem6Url)
                ShowHideViews(drawer_menu!!)
            }

            R.id.drawer_headerImg -> {
                HandleRemoteCommand(constants.drawerHeaderImgCommand)
                ShowHideViews(drawer_menu!!)
            }
        }
    }
    var urllayout: LinearLayout? = null

    //    Toolbar toolbar;
    //    Toolbar toolbar;
    var bottomToolBar: LinearLayout? = null
    var tbarprogress: ProgressBar? = null
    var HorizontalProgressBar: ProgressBar? = null
    var progressDialog: ProgressDialog? = null
    var horizontalProgressFramelayout: FrameLayout? = null
    var UrlIntent: Intent? = null
    var data: Uri? = null
    // var dX = 0f
    // var dY = 0f
    // var lastAction = 0
    //var currentDownloadFileName: String? = null
    //  var currentDownloadFileMimeType: String? = null

    //Progress
    var ShowHorizontalProgress = false

    var ShowToolbarProgress = false

    var ShowProgressDialogue = false

    var ShowSimpleProgressBar = true

    var ShowNativeLoadView = false

    var EnableSwipeRefresh = false

    //Ads
   // var ShowBannerAds = constants.ShowAdmobBanner


    var ShowInterstitialAd = constants.ShowAdmobInterstitial

   // var ShowOptionMenu = false
    var ShowToolbar = constants.ShowToolbar

    var ShowDrawer = constants.ShowDrawer

    var ShowBottomBar = constants.ShowBottomBar

   // var ShowHideBottomBarOnScroll = false
   // var UseInappDownloader = false
    var AllowRating = false
    var ClearCacheOnExit = false
    var AskToExit = false
    var BlockAds = false
    var AllowGPSLocationAccess = false
    var RequestRunTimePermissions = false
    var LoadLastWebPageOnAccidentalExit = false
   // var OpenFileAfterDownload = true
    var AutoHideToolbar = false
   // var SupportMultiWindows = true

    var ShowWebButton = constants.ShowWebBtn

    //========================================
    //SET YOUR WEBSITE URL in constants class under Elements folder
    var MainUrl = constants.jsonUrl

    //BottomToolbar Image Buttons
    private var bottomToolbar_img_1: ImageView? = null
    private var bottomToolbar_img_2: ImageView? = null
    private var bottomToolbar_img_3: ImageView? = null
    private var bottomToolbar_img_4: ImageView? = null
    private var bottomToolbar_img_5: ImageView? = null
    private var bottomToolbar_img_6: ImageView? = null
    private var bottomtoolbar_btn_7: ImageView? = null
    private var imageWiFiOn: ImageView? = null
    private var imageWiFiOFF: ImageView? = null
    private var x_toolbar: RelativeLayout? = null
    private var bottom_server_layout: ConstraintLayout? = null
    private var drawer_menu_btn: ImageView? = null
    private var drawerItem1: LinearLayout? = null
    private var drawerItem2: LinearLayout? = null
    private var drawerItem3: LinearLayout? = null
    private var drawerItem4: LinearLayout? = null
    private var drawerItem5: LinearLayout? = null
    private var drawerItem6: LinearLayout? = null
    private var drawerItem7: LinearLayout? = null
    private var drawerImg1: ImageView? = null
    private var drawerImg2: ImageView? = null
    private var drawerImg3: ImageView? = null
    private var drawerImg4: ImageView? = null
    private var drawerImg5: ImageView? = null
    private var drawerImg6: ImageView? = null
    private var drawer_item_img_7: ImageView? = null
    private var drawerItemtext1: TextView? = null
    private var drawerItemtext2: TextView? = null
    private var drawerItemtext3: TextView? = null
    private var drawerItemtext4: TextView? = null
    private var drawerItemtext5: TextView? = null
    private var drawerItemtext6: TextView? = null
    private var drawer_item_text_7: TextView? = null
    private var drawer_header_img: ImageView? = null
    private var drawer_header_text: TextView? = null

    private var drawerHeaderBg: LinearLayout? = null


    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private var runnable: Runnable? = null
    private var toolbartitleText: TextView? = null

    private var textSyncMode: TextView? = null

    // TextView textSystemState;
    private var textSynIntervals: TextView? = null
    private var countDownTime: TextView? = null
    var textLocation: TextView? = null


    var textStatusProcess: TextView? = null
    private var textDownladByes: TextView? = null
    private var textFilecount: TextView? = null


    private var progressBarPref: ProgressBar? = null
    private val mFilesViewModel by viewModels<FilesViewModel>()

    private var isdDownloadApi = true
    private var iswebViewRefreshingOnApiSync = true
    private var totalFiles = 0
    private var currentDownloadIndex = 0
    private var fetch: Fetch? = null


    private val fetchListener: FetchListener? = null

    //  private WebView mWebviewPop;
    //  private var mAdView: AdView? = null

    private var swipeView: SwipeRefreshLayout? = null
    private var urlEdittext: EditText? = null
    private var simpleProgressbar: ProgressBar? = null
    //  private val mCustomView: View? = null
    // private val mOriginalSystemUiVisibility = 0
    // private val mOriginalOrientation = 0
    // private val mCustomViewCallback: CustomViewCallback? = null

    private var mInterstitialAd: InterstitialAd? = null

    //  private RelativeLayout windowContainer;
    private var windowProgressbar: ProgressBar? = null

    //  private RelativeLayout mContainer;
    // private val mCM: String? = null
    // private var mUM: ValueCallback<Uri>? = null
    // private var mUMA: ValueCallback<Array<Uri>>? = null
    private val mProgressDialog: ProgressDialog? = null
    private var prefs: SharedPreferences? = null
    private var mydialog: Dialog? = null
    private val ratingbar: RatingBar? = null
    private var lasturl: String? = null
    private var web_button: ImageView? = null
    private var imageCirclGreenOnline: ImageView? = null
    private var imageCircleBlueOffline: ImageView? = null

    //  private ConstraintLayout webx_layout; /// change
    // private LinearLayout web_button_root_layout; /// change
    //  private ConstraintLayout webx_layout; /// change
    private var errorCode: TextView? = null
    private var errorautoConnect: TextView? = null


    private var errorReloadButton: ImageButton? = null
   // private var powerManager: PowerManager? = null
   // private var wakeLock: PowerManager.WakeLock? = null

    private var mUserViewModel: FilesViewModel? = null

    // for webcam, Camera and Audio
    // private var textureView: TextureView? = null
    private var textNoCameraAvaliable: TextView? = null
    private var cameraHandler: CameraHandlerKT? = null
    private var audioHandler: AudioHandlerKT? = null

    private var expandWebcam: ImageView? = null
    private var closeWebCam: ImageView? = null
    private var reloadWebCam: ImageView? = null

    private var mlayout: ConstraintLayout? = null
    private var isShowToastDisplayed = false

    private var isHideToastDisplayed = false
    private var dXo = 0f
    private var dYo = 0f
    private var lastActionXY = 0
    private var initialWidth = 0

    private var initialHeight: Int = 0

    private var isSystemRunning = false


    private var mUSBCameraHeight = 200.0
    private var mUSBCameraWidth = 250.0
    private var mUSBCameraLeftMargin = 50.0

    private var mUSBCameraTopMargin = 0.0
    private var mScreenHeight = 0
    private var mScreenWidth = 0


    private val StartCameraHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val showCameraIconhandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private val RefreshCameraHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }


    private var usbBroadcastReceiver: UsbBroadcastReceiver? = null
    private var CameraReceiver: CameraDisconnectedReceiver? = null


    private val myDownloadClass: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE
        )
    }


    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE
        )
    }

    private val sharedCamera: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE
        )
    }

    private val preferences: SharedPreferences by lazy {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    private val sharedTVAPPModePreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_TV_APP_MODE, Context.MODE_PRIVATE
        )
    }
    private var receiver: BroadcastReceiver? = null
    private lateinit var filter: IntentFilter

    // netowrk error layout
    private var errorlayoutExitButton: ImageButton? = null
    private var errorlayouHomeButton: ImageButton? = null
    private var ErrorReloadButton: ImageButton? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint(
        "SetJavaScriptEnabled",
        "AddJavascriptInterface",
        "JavascriptInterface",
        "ClickableViewAccessibility",
        "WakelockTimeout", "CutPasteId"
        , "SourceLockedOrientationActivity")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_web_view_page)
        binding = ActivityWebViewPageBinding.inflate(layoutInflater)
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



        //add exception

        //add exception
        Methods.addExceptionHandler(this)
        myStateChecker()

        mUserViewModel = ViewModelProvider(this).get(FilesViewModel::class.java)
        myHandler = Handler(Looper.getMainLooper())

        //  mContext = this@WebViewPage



        data = intent.data
        prefs = applicationContext.getSharedPreferences("apprater", 0)
        UrlIntent = intent

        windowProgressbar = findViewById(R.id.WindowProgressBar)
        bottomToolBar = findViewById(R.id.bottom_toolbar_container)
        progressDialog = ProgressDialog(this@WebViewPage)
        simpleProgressbar = findViewById(R.id.SimpleProgressBar)
        horizontalProgressFramelayout = findViewById(R.id.frameLayoutHorizontalProgress)
        x_toolbar = findViewById(R.id.x_toolbar)
        errorlayout = findViewById(R.id.errorLayout)
        errorCode = findViewById(R.id.errorinfo)
        errorautoConnect = findViewById(R.id.autoreconnect)
        errorReloadButton = findViewById(R.id.ErrorReloadButton)
        drawer_menu = findViewById(R.id.native_drawer_menu)
        drawer_menu_btn = findViewById(R.id.drawer_menu_Btn)
        bottom_server_layout = findViewById(R.id.bottom_server_layout)

        //  mAdView = findViewById(R.id.adView)


        HorizontalProgressBar = findViewById(R.id.progressbar)
        webView = findViewById(R.id.webview)


        swipeView = findViewById(R.id.swipeLayout)
        urlEdittext = findViewById(R.id.urledittextbox)
        urllayout = findViewById(R.id.urllayoutroot)

        web_button = findViewById(R.id.web_button)

        // webx_layout = findViewById(R.id.webx_layout);

        // webx_layout = findViewById(R.id.webx_layout);
        bottomToolbar_img_1 = findViewById(R.id.bottomtoolbar_btn_1)
        bottomToolbar_img_2 = findViewById(R.id.bottomtoolbar_btn_2)
        bottomToolbar_img_3 = findViewById(R.id.bottomtoolbar_btn_3)
        bottomToolbar_img_4 = findViewById(R.id.bottomtoolbar_btn_4)
        bottomToolbar_img_5 = findViewById(R.id.bottomtoolbar_btn_5)
        bottomToolbar_img_6 = findViewById(R.id.bottomtoolbar_btn_6)
        bottomtoolbar_btn_7 = findViewById(R.id.bottomtoolbar_btn_7)
        imageCircleBlueOffline = findViewById(R.id.imageCircleBlueOffline)
        imageCirclGreenOnline = findViewById(R.id.imageCirclGreenOnline)



        drawerImg1 = findViewById(R.id.drawer_item_img_1)
        drawerImg2 = findViewById(R.id.drawer_item_img_2)
        drawerImg3 = findViewById(R.id.drawer_item_img_3)
        drawerImg4 = findViewById(R.id.drawer_item_img_4)
        drawerImg5 = findViewById(R.id.drawer_item_img_5)
        drawerImg6 = findViewById(R.id.drawer_item_img_6)
        drawer_item_img_7 = findViewById(R.id.drawer_item_img_7)


        drawerItemtext1 = findViewById(R.id.drawer_item_text_1)
        drawerItemtext2 = findViewById(R.id.drawer_item_text_2)
        drawerItemtext3 = findViewById(R.id.drawer_item_text_3)
        drawerItemtext4 = findViewById(R.id.drawer_item_text_4)
        drawerItemtext5 = findViewById(R.id.drawer_item_text_5)
        drawerItemtext6 = findViewById(R.id.drawer_item_text_6)
        drawer_item_text_7 = findViewById(R.id.drawer_item_text_7)

        drawer_header_img = findViewById(R.id.drawer_headerImg)
        drawer_header_text = findViewById(R.id.drawer_header_text)
        drawerHeaderBg = findViewById(R.id.drawerheaderBg)

        drawerItem1 = findViewById(R.id.drawer_item_1)
        drawerItem2 = findViewById(R.id.drawer_item_2)
        drawerItem3 = findViewById(R.id.drawer_item_3)
        drawerItem4 = findViewById(R.id.drawer_item_4)
        drawerItem5 = findViewById(R.id.drawer_item_5)
        drawerItem6 = findViewById(R.id.drawer_item_6)

        drawerItem7 = findViewById(R.id.drawer_item_7)


        errorlayoutExitButton = findViewById(R.id.errorlayoutExitButton)
        errorlayouHomeButton = findViewById(R.id.errorlayouHomeButton)
        ErrorReloadButton = findViewById(R.id.errorlayouHomeButton)



        toolbartitleText = findViewById(R.id.toolbarTitleText)
        textSyncMode = findViewById(R.id.textSyncMode)
        textSynIntervals = findViewById(R.id.textSynIntervals)
        countDownTime = findViewById(R.id.countDownTime)
        textLocation = findViewById(R.id.textLocation)
        textStatusProcess = findViewById(R.id.textStatusProcess)


        textDownladByes = findViewById(R.id.textDownladByes)
        textFilecount = findViewById(R.id.textFilecount)
        progressBarPref = findViewById(R.id.progressBarPref)


        imageWiFiOFF = findViewById(R.id.imageWiFiOFF)
        imageWiFiOn = findViewById(R.id.imageWiFiOn)


        // for schedule media


        // for schedule media
        deviceTime = findViewById(R.id.deviceTime)
        serverTime = findViewById(R.id.serverTime)
        scheduleEnd = findViewById(R.id.scheduleEnd)
        scheduleStart = findViewById(R.id.scheduleStart)


        ///for camera
        ///for camera
        ///  textureView = findViewById<TextureView>(R.id.textureView)
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        cameraHandler = CameraHandlerKT(applicationContext, cameraManager, binding.textureView)
        audioHandler = AudioHandlerKT(applicationContext)



        closeWebCam = findViewById(R.id.closeWebCam)
        expandWebcam = findViewById(R.id.expandWebcam)
        reloadWebCam = findViewById(R.id.reloadWebCam)
        mlayout = findViewById(R.id.mlayout)
        textNoCameraAvaliable = findViewById(R.id.textNoCameraAvaliable)


        CameraReceiver = CameraDisconnectedReceiver()
        val filter33 = IntentFilter(Constants.SYNC_CAMERA_DISCONNECTED)
        registerReceiver(CameraReceiver, filter33)


        usbBroadcastReceiver = UsbBroadcastReceiver()
        val filter444 = getIntentFilter()
        registerReceiver(usbBroadcastReceiver, filter444)




        ///end of init  camera


        val scroolToEnd = findViewById<ImageView>(R.id.scroolToEnd)
        val scroolToStart = findViewById<ImageView>(R.id.scroolTostart)
        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.horizontalScrollView2)

        scroolToEnd.setOnClickListener {
            val scrollAmount = (horizontalScrollView.getChildAt(0).width * 0.2).toInt()
            horizontalScrollView.post(object : Runnable {
                override fun run() {
                    horizontalScrollView.smoothScrollBy(scrollAmount, 0)
                }
            })
        }

        scroolToStart.setOnClickListener {
            val scrollAmount = (horizontalScrollView.getChildAt(0).width * 0.30).toInt()
            horizontalScrollView.post(object : Runnable {
                override fun run() {
                    horizontalScrollView.smoothScrollBy(-scrollAmount, 0)
                }
            })
        }


        bottomtoolbar_btn_7!!.setOnClickListener {
            ShowHideViews(drawer_menu!!)
        }

        drawerItem7!!.setOnClickListener {
            val intent = Intent(applicationContext, QRSanActivity::class.java)
            startActivity(intent)
            finish()
        }


        errorlayoutExitButton?.setOnClickListener {
            ExitOnError()
        }


        errorlayouHomeButton?.setOnClickListener {
            goHomeOnError()
        }

        errorlayouHomeButton?.setOnClickListener {
            webView.let {
                it?.reload()
            }
        }



        swipeView?.setEnabled(false)
        swipeView?.setRefreshing(false)



        handler.postDelayed(Runnable { CheckUpdate() }, 8000)


        InitializeWebSettings()
        InitializeRemoteData()
        InitiatePreferences()
        InitiateComponents()
        IntClikListnerOnWebView()
        InitWebvIewloadStates()




        // init fetch listener and API Sync
        Init_Fetch_Download_Lsitner()

        registerNotificationBroadCast()


        // This aree Required for Tv mode
        val get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "").toString()
        if (get_AppMode == Constants.TV_Mode) {

            // Init Api Sync Types
            initStartSyncServices()

            //init USB Camera
            iniliaze_Schedule_and_usbCamera()

            // init schedule
            initialize()

        }



        // init Api Sync or Zip Sync
        InitBoleanApiSync_OR_Zip()


        // init shortcut
        CheckShortCutImage()


        // init refresh Time
        val getTimeForRefresh = myDownloadClass.getLong(Constants.get_Refresh_Timer, 0)
        getTimeForRefresh.let { it1 ->
            if (it1 != 0L) {
                start_App_Refresh_Time(it1)
            }
        }


    }

    private fun InitBoleanApiSync_OR_Zip() {
        sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "").toString().also {
            isAPISyncRunning = it == Constants.imagSwtichEnableSyncFromAPI
            // if set true , Zip is running ....

            // Also remove sync Status
            val editorSyn = myDownloadClass.edit()
            editorSyn.remove(Constants.SynC_Status)
            editorSyn.apply()

        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun InitializeWebSettings() {
        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = false
        webSettings.databaseEnabled = false
        webSettings.mediaPlaybackRequiresUserGesture = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.loadsImagesAutomatically = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        WebView.setWebContentsDebuggingEnabled(true)

    }


    private fun registerNotificationBroadCast() {
        if (constants.Notifx_service) {
            isAppOpen = true

            startService(Intent(this, RemotexNotifierKT::class.java))

            filter = IntentFilter("notifx_ready")

            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    try {
                        if (!constants.Notif_Shown) {
                            showNotifxDialog(this@WebViewPage)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            applicationContext.registerReceiver(receiver, filter)
        } else {
            stopService(Intent(this, RemotexNotifierKT::class.java))
        }
    }

    private fun InitWebvIewloadStates() {

        // get input paths to device storage
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
        val fil_CLO = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val fil_DEMO = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()


        // get state for launch
        val get_launching_state = sharedBiometric.getString(Constants.get_Launching_State_Of_WebView, "").toString()

        Log.d("InitWebvIewloadStates", "InitWebvIewloadStates: $get_launching_state")

        if (get_launching_state.equals(Constants.launch_WebView_Offline)) {
            val filename = "/index.html"
            lifecycleScope.launch {
                loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO, filename)
            }



        } else if (get_launching_state.equals(Constants.launch_WebView_Offline_Manual_Index)) {

            val filename = "/index.html"
            lifecycleScope.launch {
                loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO, filename)
            }

          //  showToastMessage("Launch Offline")


        } else if (get_launching_state.equals(Constants.launch_WebView_Online)) {

            if (Utility.isNetworkAvailable(applicationContext)) {

                load_live_Parther_url_Format()

            } else {

                showPopForTVConfiguration(Constants.Check_Inter_Connectivity)

            }


        } else if (get_launching_state.equals(Constants.launch_WebView_Online_Manual_Index)) {


            if (Utility.isNetworkAvailable(applicationContext)) {

                val getSaved_manaul_index_edit_url_Input = myDownloadClass.getString(Constants.getSaved_manaul_index_edit_url_Input, "").toString()
                loadOnlineLiveUrl(getSaved_manaul_index_edit_url_Input)
                load_live_indicator()
                //  showToastMessage("Launch Online")

            } else {

                showPopForTVConfiguration(Constants.Check_Inter_Connectivity)
            }


        } else if (get_launching_state.equals(Constants.launch_Default_WebView_url)) {

            loadOnlineUrl()

            //  showToastMessage("Launch Online")

        } else {

            loadOnlineUrl()
            // showToastMessage("Launch Online")
        }


    }

    private fun load_live_Parther_url_Format() {
        try {

            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
            val fil_CLO = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val fil_DEMO = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

            val imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()

            // if allowed to use partner url
            if (imagSwtichPartnerUrl == Constants.imagSwtichPartnerUrl) {

                val vurl = "${CP_AP_MASTER_DOMAIN}/$fil_CLO/$fil_DEMO/App/index.html"
                loadOnlineLiveUrl(vurl)
                load_live_indicator()
                //  showToastMessage("Launch Online")

                // if NOT allowed to use partner url
            } else {
                val get_custom_path_url = myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
                val appended_url = "$get_custom_path_url/$fil_CLO/$fil_DEMO/App/index.html"
                loadOnlineLiveUrl(appended_url)
                load_live_indicator()
                //   showToastMessage("Launch Online")
            }

        }catch (e:Exception){
            Log.d(TAG, "load_live_Parther_url_Format: "+e.message.toString())
        }
    }


    private inner class NotifBroadcastReceiver : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (!constants.Notif_Shown) {
                    showNotifxDialog(this@WebViewPage)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun goHomeOnError() {
        webView!!.loadUrl(MainUrl)

        // init chrome client if not avaliabel
        setupWebViewClients()


    }

    private fun ExitOnError() {
        // finishAffinity()

        finishAndRemoveTask()
        Process.killProcess(Process.myTid())

        if (LoadLastWebPageOnAccidentalExit) {
            ClearLastUrl()
        }
    }


    private fun showToastMessage(message: String) {
        try {
            runOnUiThread {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun getFilePath(CLO: String, DEMO: String, filename: String): String? {

        val finalFolderPathDesired = "/" + CLO + "/" + DEMO + "/" + Constants.App
        val destinationFolder = Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + finalFolderPathDesired
        val filePath = "file://$destinationFolder$filename"
        val myFile = File(destinationFolder, File.separator + filename)

        return if (myFile.exists()) {
            filePath
        } else {
            null
        }
    }



    @SuppressLint("SetJavaScriptEnabled")
    private fun loadOffline_Saved_Path_Offline_Webview(
        CLO: String,
        DEMO: String,
        fileName: String
    ) {
        lifecycleScope.launch {
            try {

                val filePath = withContext(Dispatchers.IO) {
                    try {
                        getFilePath(CLO, DEMO, fileName)
                    } catch (e: Exception) {
                        Log.d(TAG, "loadOffline_Saved_Path_Offline_Webview: ${e.message}")
                        null
                    }
                }

                // Now back on the main thread to update the UI
                if (filePath != null) {
                    webView?.apply {

                        clearHistory()
                        loadUrl(filePath.toString())
                        setupWebViewClients()
                        load_offline_indicator()
                    }

                } else {
                    showPopForTVConfiguration(Constants.compleConfiguration)
                }



            } catch (e: Exception) {
                Log.d(TAG, "loadOffline_Saved_Path_Offline_Webview: ${e.message}")
                showPopForTVConfiguration(Constants.compleConfiguration)
            }
        }
    }




    @SuppressLint("SetJavaScriptEnabled")
    private fun loadOffline_Saved_Path_Offline_Webview_For_Pop_Layout(
        CLO: String,
        DEMO: String,
        filename: String
    ) {
        lifecycleScope.launch {
            try {
                // Offload file I/O operation to a background thread
                val filePath = withContext(Dispatchers.IO) {
                    try {
                        getFilePath(CLO, DEMO, filename)
                    } catch (e: Exception) {
                        Log.d(TAG, "loadOffline_Saved_Path_Offline_Webview: ${e.message}")
                        null
                    }
                }

                // Now back on the main thread to update the UI
                if (filePath != null) {
                    webView?.apply {

                        clearHistory()
                        loadUrl(filePath.toString())
                        setupWebViewClients()
                        load_offline_indicator()

                    }

                } else {
                    loadOnlineUrl()
                    load_live_indicator()
                }


            } catch (e: Exception) {

                Log.d(TAG, "loadOffline_Saved_Path_Offline_Webview_For_Pop_Layout: ${e.message}")
                loadOnlineUrl()
                load_live_indicator()


            }
        }
    }





    @SuppressLint("SetJavaScriptEnabled")
    private fun loadOnlineUrl() {

        load_live_indicator()

        // Configure WebViewClient and WebChromeClient if not already configured
        setupWebViewClients()

        if (UrlIntent!!.hasExtra("url")) {
            webView!!.loadUrl(intent.getStringExtra("url")!!)

        } else if (data != null) {
            webView!!.loadUrl(data.toString())
        } else {
            if (LoadLastWebPageOnAccidentalExit) {
                val lurl = preferences.getString("lasturl", "")
                if (lurl!!.startsWith("http") || lurl.startsWith("https")) {
                    webView!!.loadUrl(lurl)
                } else {
                    webView!!.loadUrl(MainUrl)
                }
            } else {
                webView!!.loadUrl(MainUrl)
            }
        }


    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadOnlineLiveUrl(url: String) {
        try {

            // Load the provided URL
            webView?.loadUrl(url)


            // init chrome client
            setupWebViewClients()


        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "loadOnlineLiveUrl: " + e.message.toString())
        }
    }


    private fun load_live_indicator() {
        val get_imagShowOnlineStatus =
            sharedBiometric.getString(Constants.imagShowOnlineStatus, "").toString()
        if (get_imagShowOnlineStatus != Constants.imagShowOnlineStatus) {
            imageCirclGreenOnline!!.visibility = View.VISIBLE
            imageCircleBlueOffline!!.visibility = View.INVISIBLE
        } else {
            imageCirclGreenOnline!!.visibility = View.INVISIBLE
            imageCircleBlueOffline!!.visibility = View.INVISIBLE
        }

    }


    private fun load_offline_indicator() {
        val get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "")
        if (get_imagShowOnlineStatus != Constants.imagShowOnlineStatus) {
            imageCirclGreenOnline!!.visibility = View.INVISIBLE
            imageCircleBlueOffline!!.visibility = View.VISIBLE
        } else {
            imageCirclGreenOnline!!.visibility = View.INVISIBLE
            imageCircleBlueOffline!!.visibility = View.INVISIBLE
        }

    }


    private fun setupWebViewClients() {
        /*      try {
                  //clear history
                  webView!!.clearHistory()

              }catch (e:Exception){
                  Log.d(TAG, "setupWebViewClients: " +e.message.toString())
              }*/


        //web view client
        webView?.setWebViewClient(object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                url: String
            ): WebResourceResponse? {
                if (BlockAds) {
                    if (url.contains("googleads.g.doubleclick.net")) {
                        val textStream: InputStream = ByteArrayInputStream("".toByteArray())
                        return getTextWebResource(textStream)
                    }
                }
                return super.shouldInterceptRequest(view, url)
            }

            private fun getTextWebResource(data: InputStream): WebResourceResponse? {
                return WebResourceResponse("text/plain", "UTF-8", data)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try {
                    if (constants.AllowOnlyHostUrlInApp) {
                        if (!url.contains(constants.filterdomain)) {
                            webView!!.stopLoading()
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            return true
                        }
                    }

                } catch (e: java.lang.Exception) {
                    Log.i(TAG, "shouldOverrideUrlLoading Exception:" + e.message)

                }

                if (url.startsWith("http://") || url.startsWith("file:///") || url.startsWith("https://") || url.startsWith(
                        "setup://"
                    )
                )
                    return false
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)!!
                    intent.addCategory("android.intent.category.BROWSABLE")
                    // forbid explicit call
                    intent.component = null
                    // forbid Intent with selector Intent
                    intent.selector = null
                    // start the activity by the Intent
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    view.context.startActivity(intent)
                } catch (e: java.lang.Exception) {
                    Log.i(TAG, "shouldOverrideUrlLoading Exception:" + e.message)
                    showToastMessage("The app or ACTIVITY not found. Error Message:" + e.message)

                }
                return true
            }


            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (drawer_menu!!.visibility == View.VISIBLE) {
                    drawer_menu!!.visibility = View.GONE
                }
                if (ShowSimpleProgressBar) {
                    simpleProgressbar!!.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String) {
                try {
                    lasturl = url
                    if (ShowSimpleProgressBar) {
                        simpleProgressbar!!.visibility = View.GONE
                    }
                    if (LoadLastWebPageOnAccidentalExit) {
                        preferences.edit().putString("lasturl", url).apply()
                    }
                    if (ShowInterstitialAd) {
                        if (mInterstitialAd != null) {
                            mInterstitialAd!!.show(this@WebViewPage)
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
                        }
                    }

                    // clear history
                    //  webView!!.clearHistory()

                } catch (e: java.lang.Exception) {
                    showToastMessage(e.message!!)
                }
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String,
                failingUrl: String?
            ) {
                if (description == "net::ERR_FAILED") {
                } else {
                    HideErrorPage(failingUrl!!, description)
                }
                super.onReceivedError(view, errorCode, description, failingUrl)
            }


        })


        //chrome web client
        webView!!.setWebChromeClient(object : WebChromeClient() {

            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback
            ) {
                callback.invoke(origin, true, false)
                if (AllowGPSLocationAccess) {
                    checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    webView!!.settings.setGeolocationEnabled(true)
                    displayLocationSettingsRequest(applicationContext)
                } else {
                    showToastMessage("Location requested, You can enable location in settings")
                }
            }


            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (ShowHorizontalProgress) {
                    HorizontalProgressBar!!.progress = newProgress
                }
                val name = preferences!!.getString("proshow", "")
                if (newProgress == 100) {
                    if (name == "show") {
                        windowProgressbar!!.visibility = View.GONE
                    }
                    try {
                        if (ShowProgressDialogue) {
                            progressDialog!!.cancel()
                            progressDialog!!.dismiss()
                            progressDialog!!.hide()
                        }
                        if (ShowToolbarProgress) {
                            tbarprogress!!.visibility = View.GONE
                        }
                        if (ShowHorizontalProgress) {
                            HorizontalProgressBar!!.visibility = View.GONE
                        }
                        if (ShowSimpleProgressBar) {
                            simpleProgressbar!!.visibility = View.GONE
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } else {
                    if (name == "show") {
                        windowProgressbar!!.visibility = View.VISIBLE
                    }
                    try {
                        if (ShowHorizontalProgress) {
                            HorizontalProgressBar!!.visibility = View.VISIBLE
                        }
                        if (ShowProgressDialogue) {
                            progressDialog!!.setMessage("Loading")
                            progressDialog!!.setCancelable(false)
                            progressDialog!!.show()
                        }
                        if (ShowSimpleProgressBar) {
                            simpleProgressbar!!.visibility = View.VISIBLE
                        }
                        if (ShowToolbarProgress) {
                            tbarprogress!!.visibility = View.VISIBLE
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.d(TAG, "HideErrorPage: "+e.message.toString())
                    }
                }
            }


        })


    }


    private fun displayLocationSettingsRequest(context: Context) {

        try {
            val googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build()
            googleApiClient.connect()
            val TAG = "YOUR-TAG-NAME"
            val REQUEST_CHECK_SETTINGS = 0x1
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10000
            locationRequest.fastestInterval = (10000 / 2).toLong()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            val result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback { result1: LocationSettingsResult ->
                val status = result1.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> Log.i(
                        TAG,
                        "All location settings are satisfied."
                    )

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(
                            TAG,
                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                        )
                        try {
                            status.startResolutionForResult(
                                this@WebViewPage,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: SendIntentException) {
                            Log.i(TAG, "PendingIntent unable to execute request.")
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        TAG,
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        } catch (eP: Exception) {
            Log.d(TAG, "HideErrorPage: "+eP.message.toString())
        }
    }

    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 3)
        }
    }

    private fun HideErrorPage(failingUrl: String, description: String) {
        try {
          ///  webView?.loadUrl("about:blank")
            errorlayout?.visibility = View.VISIBLE
            errorCode?.text = description
            errorReloadButton!!.setOnClickListener { webView!!.loadUrl(failingUrl) }
            handler.postDelayed(Runnable {
                handler.postDelayed(runnable!!, 4000)
                if (errorautoConnect?.visibility == View.GONE) {
                    errorautoConnect?.visibility = View.VISIBLE
                }
                errorautoConnect?.text = "Auto Reconnect: Standby"
                if (AdvancedControls.checkInternetConnection(applicationContext)) {
                    errorautoConnect!!.text = "Auto Reconnect: Trying to connect.."
                } else {
                    webView!!.loadUrl(failingUrl)
                    errorlayout!!.visibility = View.GONE
                    webView!!.clearHistory()
                    handler.removeCallbacks(runnable!!)
                }
            }.also { runnable = it }, 4000)
            try {
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d(TAG, "HideErrorPage: "+e.message.toString())
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "HideErrorPage: "+e.message.toString())
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ClickableViewAccessibility")
    private fun IntClikListnerOnWebView() {
        webView?.setOnTouchListener(OnTouchListener { view, motionEvent ->
            if (drawer_menu!!.isShown()) {
                AnimateHide(drawer_menu!!)
                drawer_menu!!.setVisibility(View.GONE)
                webView!!.setAlpha(1f)
            }
            false
        })


        web_button?.setOnClickListener(View.OnClickListener { v -> // Handle click event here
            try {
                       val buttonClick = AlphaAnimation(0.1f, 0.4f)
                       v.startAnimation(buttonClick)
                       HandleRemoteCommand(constants.Web_button_link)
                   } catch (e: Exception) {
                       showToastMessage(e.message.toString())
                   }
        })


             web_button?.setOnTouchListener(object : OnTouchListener {
                 var dX = 0f
                 var dY = 0f
                 var lastAction = 0

                 @SuppressLint("ClickableViewAccessibility")
                 override fun onTouch(v: View, event: MotionEvent): Boolean {
                     when (event.actionMasked) {
                         MotionEvent.ACTION_DOWN -> {
                             dX = v.x - event.rawX
                             dY = v.y - event.rawY
                             lastAction = MotionEvent.ACTION_DOWN
                         }

                         MotionEvent.ACTION_MOVE -> {
                             v.y = event.rawY + dY
                             v.x = event.rawX + dX
                             lastAction = MotionEvent.ACTION_MOVE
                         }

                         MotionEvent.ACTION_UP ->                         // Delay before performing click action
                             Handler().postDelayed({
                                 if (lastAction == MotionEvent.ACTION_DOWN) {
                                     v.performClick()
                                 }
                             }, 300) // Adjust delay time as needed
                         else -> return false
                     }
                     return true
                 }
             })




    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun InitiateComponents() {
        try {
            val preferences = PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )

            val get_INSTALL_TV_JSON_USER_CLICKED = sharedTVAPPModePreferences.getString(Constants.INSTALL_TV_JSON_USER_CLICKED, "").toString()
            val showFloating_Button_APP = sharedTVAPPModePreferences.getBoolean(Constants.hide_Floating_Button_APP, false)


            if (get_INSTALL_TV_JSON_USER_CLICKED == Constants.INSTALL_TV_JSON_USER_CLICKED) {
                if (!showFloating_Button_APP){
                    web_button!!.visibility = View.VISIBLE
                }else{
                    web_button!!.visibility = View.GONE
                }
            }


            /// continue with previous JSon
            /// continue with previous JSon
            if (get_INSTALL_TV_JSON_USER_CLICKED != Constants.INSTALL_TV_JSON_USER_CLICKED) {

                val get_floating_bar_to_show =
                    preferences.getBoolean(Constants.shwoFloatingButton, false)
                if (ShowWebButton || get_floating_bar_to_show == false) {
                    web_button!!.visibility = View.VISIBLE
                } else {
                    web_button!!.visibility = View.GONE
                }

            }
            /// end of part continue with previous JSon
            /// end part of continue with previous JSon



            if (ShowHorizontalProgress) {
                horizontalProgressFramelayout!!.visibility = View.VISIBLE
            }




            if (AllowRating) {
                TryRating()
            }



            if (ShowBottomBar) {
                try {
                    if (constants.ChangeBottombarBgColor) {
                        if (constants.bottomBarBgColor != null) {
                            bottomToolBar!!.setBackgroundColor(Color.parseColor(constants.bottomBarBgColor))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                bottomToolBar!!.visibility = View.VISIBLE


            }




            if (ShowSimpleProgressBar) {
                simpleProgressbar!!.visibility = View.VISIBLE
            }
            if (EnableSwipeRefresh) {
                swipeView!!.isEnabled = true
                swipeView!!.setColorSchemeColors(resources.getColor(R.color.app_color_accent))
                swipeView!!.setOnRefreshListener {
                    webView!!.reload()
                    swipeView!!.isRefreshing = false
                }
            }


            if (ShowToolbar) {
                x_toolbar!!.visibility = View.VISIBLE
                if (!constants.ToolbarTitleText.isEmpty()) {
                    toolbartitleText!!.text = constants.ToolbarTitleText
                }
                try {
                    if (constants.ChangeTittleTextColor and !constants.ToolbarTitleTextColor.isEmpty()) {
                        toolbartitleText!!.setTextColor(Color.parseColor(constants.ToolbarTitleTextColor))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    if (constants.ChangeToolbarBgColor and !constants.ToolbarBgColor.isEmpty()) {
                        if (preferences.getBoolean("darktheme", false)) {
                            x_toolbar!!.setBackgroundColor(resources.getColor(R.color.darkthemeColor))
                            bottomToolBar!!.setBackgroundColor(resources.getColor(R.color.darkthemeColor))
                            drawerHeaderBg!!.setBackgroundColor(resources.getColor(R.color.darkthemeColor))
                        } else {
                            x_toolbar!!.setBackgroundColor(Color.parseColor(constants.ToolbarBgColor))
                            window.statusBarColor = Color.parseColor(constants.ToolbarBgColor)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            if (ShowDrawer) {
                drawer_menu_btn!!.visibility = View.VISIBLE
                drawer_menu_btn!!.setOnClickListener(imgClk)
                drawerItem1!!.setOnClickListener(imgClk)
                drawerItem2!!.setOnClickListener(imgClk)
                drawerItem3!!.setOnClickListener(imgClk)
                drawerItem4!!.setOnClickListener(imgClk)
                drawerItem5!!.setOnClickListener(imgClk)
                drawerItem6!!.setOnClickListener(imgClk)
                drawer_header_img!!.setOnClickListener(imgClk)
                try {
                    if (constants.ChangeHeaderTextColor and (constants.drawerHeaderTextColor != null)) {
                        if (preferences.getBoolean("darktheme", false)) {
                            drawer_header_text!!.setTextColor(Color.WHITE)
                        } else {
                            drawer_header_text!!.setTextColor(Color.parseColor(constants.drawerHeaderTextColor))
                        }
                    }
                    if ((constants.drawerHeaderBgColor != null) and constants.ChangeDrawerHeaderBgColor) {
                        if (preferences.getBoolean("darktheme", false)) {
                            drawerHeaderBg!!.setBackgroundColor(resources.getColor(R.color.darkthemeColor))
                        } else {
                            drawerHeaderBg!!.setBackgroundColor(Color.parseColor(constants.drawerHeaderBgColor))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                HandleRemoteDrawerText(drawerItemtext1!!, constants.drawerMenuItem1Text)
                HandleRemoteDrawerText(drawerItemtext2!!, constants.drawerMenuItem2Text)
                HandleRemoteDrawerText(drawerItemtext3!!, constants.drawerMenuItem3Text)
                HandleRemoteDrawerText(drawerItemtext4!!, constants.drawerMenuItem4Text)
                HandleRemoteDrawerText(drawerItemtext5!!, constants.drawerMenuItem5Text)
                HandleRemoteDrawerText(drawerItemtext6!!, constants.drawerMenuItem6Text)
                HandleRemoteDrawerText(drawer_header_text!!, constants.drawerHeaderText)
            }
        } catch (e: Exception) {
            Log.d(TAG, "InitiateComponents: "+e.message.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun InitializeRemoteData() {
        try {
            bottomToolbar_img_1!!.setOnClickListener(imgClk)
            bottomToolbar_img_2!!.setOnClickListener(imgClk)
            bottomToolbar_img_3!!.setOnClickListener(imgClk)
            bottomToolbar_img_4!!.setOnClickListener(imgClk)
            bottomToolbar_img_5!!.setOnClickListener(imgClk)
            bottomToolbar_img_6!!.setOnClickListener(imgClk)
            ConfigureRemoteImageData(constants.bottomBtn1ImgUrl, bottomToolbar_img_1!!)
            ConfigureRemoteImageData(constants.bottomBtn2ImgUrl, bottomToolbar_img_2!!)
            ConfigureRemoteImageData(constants.bottomBtn3ImgUrl, bottomToolbar_img_3!!)
            ConfigureRemoteImageData(constants.bottomBtn4ImgUrl, bottomToolbar_img_4!!)
            ConfigureRemoteImageData(constants.bottomBtn5ImgUrl, bottomToolbar_img_5!!)
            ConfigureRemoteImageData(constants.bottomBtn6ImgUrl, bottomToolbar_img_6!!)
            ConfigureRemoteImageData(constants.Web_button_Img_link, web_button!!)
            if (ShowDrawer) {
                ConfigureRemoteImageData(constants.drawerMenuImgUrl, drawer_menu_btn!!)
                ConfigureRemoteImageData(constants.drawerMenuItem2ImgUrl, drawerImg2!!)
                ConfigureRemoteImageData(constants.drawerMenuItem3ImgUrl, drawerImg3!!)
                ConfigureRemoteImageData(constants.drawerMenuItem4ImgUrl, drawerImg4!!)
                ConfigureRemoteImageData(constants.drawerMenuItem5ImgUrl, drawerImg5!!)
                ConfigureRemoteImageData(constants.drawerMenuItem6ImgUrl, drawerImg6!!)
                ConfigureRemoteImageData(constants.drawerMenuItem1ImgUrl, drawerImg1!!)
                ConfigureRemoteImageData(constants.drawerHeaderImgUrl, drawer_header_img!!)
            }
        } catch (e: Exception) {
            Log.d(TAG, "InitializeRemoteData: "+e.message.toString())
        }
    }

    private fun InitiatePreferences() {


        try {

            val get_INSTALL_TV_JSON_USER_CLICKED = sharedTVAPPModePreferences.getString(Constants.INSTALL_TV_JSON_USER_CLICKED, "").toString()
            val show_BottomBar_APP = sharedTVAPPModePreferences.getBoolean(Constants.hide_BottomBar_APP, false)
            val fullScreen_APP = sharedTVAPPModePreferences.getBoolean(Constants.hide_BottomBar_APP, false)
            val immersive_Mode_APP = sharedTVAPPModePreferences.getBoolean(Constants.immersive_Mode_APP, false)

            if (get_INSTALL_TV_JSON_USER_CLICKED == Constants.INSTALL_TV_JSON_USER_CLICKED) {

               // show_BottomBar_APP.also { this.ShowBottomBar = it }

                ShowBottomBar = !show_BottomBar_APP

                if (fullScreen_APP) {
                    // Enable full screen
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )

                } else {
                    // Disable full screen
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                }


                /// enable immersive mode if true
                if (immersive_Mode_APP) {
                    ShowToolbar = false
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }else {
                    // Disable full screen
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                }



            }


            /// continue with previous JSon
            /// continue with previous JSon
            if (get_INSTALL_TV_JSON_USER_CLICKED != Constants.INSTALL_TV_JSON_USER_CLICKED) {

                // check if hide bottombar
                if (preferences.getBoolean("hidebottombar", false)) {
                    ShowBottomBar = false
                }

                // enable Full Screen if true
                if (preferences.getBoolean("fullscreen", false)) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }



                /// enable immersive mode if true
                if (preferences.getBoolean("immersive_mode", false)) {
                    ShowToolbar = false
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }


            }


            /// End of continue with previous JSon
            /// End of continue with previous JSon


            if (preferences.getBoolean("swiperefresh", false)) {
                EnableSwipeRefresh = true
            }


            if (preferences.getBoolean("nightmode", false)) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        webView!!.settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }
            }
            if (preferences.getBoolean("blockAds", false)) {
                BlockAds = true
            }
            if (preferences.getBoolean("nativeload", false)) {
                ShowNativeLoadView = true
                ShowSimpleProgressBar = false
            }


            if (preferences.getBoolean("geolocation", false)) {
                webView!!.settings.setGeolocationEnabled(true)
                webView!!.settings.setGeolocationDatabasePath(applicationContext!!.filesDir.path)
                AllowGPSLocationAccess = true
            }




            if (preferences.getBoolean("permission_query", false)) {
                RequestRunTimePermissions = true
            }


            if (preferences.getBoolean("loadLastUrl", false)) {
                LoadLastWebPageOnAccidentalExit = true
            }



            if (preferences.getBoolean("autohideToolbar", false)) {
                AutoHideToolbar = true
            }



        } catch (e: Exception) {
            Log.d(TAG, "ConfigureRemoteImageData: "+e.message.toString())
        }
    }


    private fun ConfigureRemoteImageData(url: String?, view: ImageView) {

//        showToast(mContext,view.toString()+" "+url);
        try {
            if ((url == null) or (url == "null")) return
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            if (url != null && url.endsWith("svg")) {
                GlideToVectorYou
                    .init()
                    .with(this)
                    .withListener(object : GlideToVectorYouListener {
                        override fun onLoadFailed() {}
                        override fun onResourceReady() {}
                    })
                    .setPlaceHolder(R.drawable.demo_btn_24, R.drawable.demo_btn_24)
                    .load(Uri.parse(url), view)
            } else {
                Glide.with(this)
                    .load(url) // image url
                    .placeholder(R.drawable.demo_btn_24) // any placeholder to load at start
                    .error(R.drawable.demo_btn_24) // any image in case of error
                    .into(view) // imageview object
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "ConfigureRemoteImageData: "+e.message.toString())
        }
    }

    private fun HandleRemoteDrawerText(textv: TextView, text: String) {
        textv.text = text
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun HandleRemoteCommand(command: String) {

        try {
            if (command == "openSettings") {

                try {
                    if (fetchListener != null) {
                        fetch!!.removeListener(fetchListener)
                    }

                    if (fetch != null) {
                        fetch!!.removeAll()
                    }



                   // webView!!.stopLoading()
                  //  webView!!.destroy()

                    handler.postDelayed(Runnable {
                        val myactivity = Intent(this@WebViewPage, SettingsActivityKT::class.java)
                        startActivity(myactivity)
                        finish()

                    },500)

                    showToastMessage("Please wait..")
                } catch (e: Exception) {
                    showToastMessage(e.message.toString())
                }
            } else if (command == "webGoBack") {
                if (webView!!.canGoBack()) {
                    webView!!.goBack()
                } else {
                    AdvancedControls.showToast(applicationContext, "No back page")
                }
            } else if (command == "webGoForward") {
                if (webView!!.canGoForward()) {
                    webView!!.goForward()
                } else {
                    AdvancedControls.showToast(applicationContext, "No forward page")
                }
            } else if (command == "reload") {
                webView!!.reload()
            } else if (command == "sharePage") {
                ShareItem(webView!!.originalUrl!!, "Check Out This!", " ")
            } else if (command == "goHome") {
                webView!!.loadUrl(constants.jsonUrl)
            } else if (command == "openDrawer") {
                ShowHideViews(drawer_menu!!)
            } else if (command == "ExitApp") {
                finishAndRemoveTask()
                Process.killProcess(Process.myTid())
            } else if (command == "ScanCode") {

                handler.postDelayed(Runnable {
                    val intent = Intent(applicationContext, QRSanActivity::class.java)
                    startActivity(intent)
                    finish()
                },500)


            } else if (command == "null") {
            } else {
                webView!!.loadUrl(command)
            }
        } catch (e: Exception) {
            Log.d(TAG, "HandleRemoteCommand: "+e.message.toString())
        }

    }


    private fun ShowHideViews(Myview: View) {
        if (Myview.visibility == View.GONE) {
            AnimateShow(Myview)
            Myview.visibility = View.VISIBLE
            webView!!.alpha = 0.5f
        } else if (Myview.visibility == View.VISIBLE) {
            AnimateHide(Myview)
            Myview.visibility = View.GONE
            webView!!.alpha = 1f
        }
    }


    private fun AnimateShow(view: View) {
        val anim = AnimationUtils.loadAnimation(
            baseContext,
            R.anim.slide_to_right
        )
        view.startAnimation(anim)
    }

    private fun AnimateHide(view: View) {
        val anim = AnimationUtils.loadAnimation(
            baseContext,
            R.anim.slide_to_left
        )
        view.startAnimation(anim)
    }

    private fun ShareItem(ShareText: String, Subject: String, ShareTitle: String) {
        try {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Subject)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, ShareText)
            startActivity(Intent.createChooser(sharingIntent, ShareTitle))
        } catch (e: Exception) {
            Log.d(TAG, "ShareItem: "+e.message.toString())
        }
    }

    private fun TryRating() {
        if (preferences.getBoolean("dontshowagain", false)) {
            return
        }
        val editor = prefs!!.edit()
        val launch_count = prefs!!.getLong("launch_count", 0) + 1
        editor.putLong("launch_count", launch_count)
        // Get date of first launch
        var date_firstLaunch = prefs!!.getLong("date_firstlaunch", 0)
        if (date_firstLaunch == 0L) {
            date_firstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", date_firstLaunch)
        }
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch) {
                showRateDialog()
            }
        }
        editor.apply()
    }


    private fun showRateDialog() {

        try {
            mydialog = Dialog(this)
            ratingbar!!.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    if (fromUser) {
                        ratingBar.rating = Math.ceil(rating.toDouble()).toFloat()
                    }
                }
            mydialog!!.show()
        } catch (e: Exception) {
            Log.d(TAG, "showRateDialog: "+e.message.toString())
        }

    }

    private fun showNotifxDialog(context: Context?) {
        try {

            val lastNotifxId = preferences?.getString("lastId", "").toString()
            if (constants.NotifAvailable and (lastNotifxId != constants.Notif_ID!!)) {
                try {
                    val binding: CustomNotificationLayoutBinding =
                        CustomNotificationLayoutBinding.inflate(layoutInflater)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(binding.getRoot())
                    val dialog = builder.create()
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.setCancelable(false)

                    // Set the background of the AlertDialog to be transparent
                    if (dialog.window != null) {
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }
                    val notifTitle: TextView = binding.notifTitle
                    val notifDesc: TextView = binding.notifDesc
                    val closeThis: ImageView = binding.closeNotif
                    val notifButton: TextView = binding.notifActionButton
                    val imageView: ImageView = binding.notifImg
                    closeThis.setOnClickListener {
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    notifButton.setOnClickListener {
                        if (constants.Notif_button_action.startsWith("https") or constants.Notif_button_action.startsWith(
                                "https"
                            )
                        ) {
                            if (constants.NotifLinkExternal) {
                                webView!!.loadUrl(constants.Notif_button_action)
                            } else {
                                redirectStore(constants.Notif_button_action)
                            }
                            dialog.dismiss()
                            dialog.cancel()
                        } else if (constants.Notif_button_action == "dismiss") {
                            dialog.dismiss()
                            dialog.cancel()
                        }
                        dialog.dismiss()
                        dialog.cancel()
                    }
                    notifTitle.text = constants.Notif_title
                    notifDesc.text = Html.fromHtml(constants.Notif_desc)
                    Glide.with(context!!)
                        .load(constants.Notif_Img_url) // image url
                        .placeholder(R.drawable.img_logo_icon) // any placeholder to load at start
                        .error(R.drawable.img_logo_icon) // any image in case of error
                        .into(imageView) // imageview object
                    dialog.setCancelable(false)
                    if (constants.NotifSound) {
                        val mp = MediaPlayer.create(context, R.raw.alertx)
                        mp.setVolume(0.1.toFloat(), 0.1.toFloat())
                        mp.start()
                    }
                    val editor = preferences.edit()
                    editor.putString("lastId", constants.Notif_ID).apply()
                    constants.Notif_Shown = true
                    try {
                        dialog.show()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        } catch (e: Exception) {
            Log.d(TAG, "navigateBackTosetting: "+e.message.toString())
        }
    }

    private fun redirectStore(updateUrl: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Log.d(TAG, "navigateBackTosetting: "+e.message.toString())
        }
    }


    private fun UpdateApp(updateUrl: String?, forceUpdate: Boolean) {
        try {
            val dialog = AlertDialog.Builder(this)
                .setTitle(constants.UpdateTitle)
                .setMessage(constants.UpdateMessage)
                .setCancelable(!forceUpdate)
                .setNeutralButton("Later") { dialog, which ->
                    if (forceUpdate) {
                        finish()
                    } else {
                        dialog.dismiss()
                    }
                }
                .setPositiveButton(
                    "Update"
                ) { dialog, which ->
                    redirectStore(updateUrl!!)
                    if (forceUpdate) {
                        finish()
                    }
                }.setNegativeButton(
                    "No, thanks"
                ) { dialog, which ->
                    if (forceUpdate) {
                        finish()
                    }
                }.create()
            try {
                dialog.show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d(TAG, "navigateBackTosetting: "+e.message.toString())
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "navigateBackTosetting: "+e.message.toString())
        }
    }


    private fun CheckUpdate() {
        try {
            constants.CurrVersion = applicationContext!!.packageManager
                .getPackageInfo(applicationContext!!.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (constants.UpdateAvailable and (constants.CurrVersion != constants.NewVersion)) {
            UpdateApp(constants.UpdateUrl, constants.ForceUpdate)

        } else {
            Log.d("RemoteConfig", "No Update or version are equal ")
        }
    }


    private fun navigateBackTosetting() {

        try {
            webView!!.stopLoading()
            webView!!.destroy()

            if (fetchListener != null) {
                fetch!!.removeListener(fetchListener)
            }

            if (fetch != null) {
                fetch!!.removeAll()
            }


          //  webView!!.stopLoading()
          //  webView!!.destroy()

            handler.postDelayed(Runnable {
                val myactivity = Intent(this@WebViewPage, SettingsActivityKT::class.java)
                startActivity(myactivity)
                finish()

            },500)


            showToastMessage("Please wait..")
        } catch (e: Exception) {
            Log.d(TAG, "navigateBackTosetting: "+e.message.toString())
        }
    }

    private fun ShowExitDialogue() {
        try {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.img_logo_icon)
                .setTitle("Exit")
                .setMessage("Are you sure to Exit?")
                .setPositiveButton("Yes") { dialog, which ->
                    ClearLastUrl()
                    System.exit(0)
                }
                .setNegativeButton("No", null)
                .show()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "ShowExitDialogue: "+e.message.toString())
        }
    }


    private fun ClearLastUrl() {
        val pp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        pp.edit().remove("lasturl").apply()
    }


    private inner class ConnectivityReceiver : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val connectivityManager =
                    context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    try {
                        val SPLASH_TIME_OUT = 1000
                        textStatusProcess?.setText("Connecting..")

                        handler.postDelayed(Runnable {
                            try {
                                imageWiFiOn?.visibility = View.GONE
                                imageWiFiOFF?.visibility = View.VISIBLE
                                myDownloadStatus()
                            } catch (e: java.lang.Exception) {
                            }
                        }, SPLASH_TIME_OUT.toLong())

                    } catch (ignored: java.lang.Exception) {
                    }
                } else {

                    // No internet Connection
                    try {
                        imageWiFiOn!!.visibility = View.VISIBLE
                        imageWiFiOFF!!.visibility = View.GONE
                        textStatusProcess!!.text = "No Internet"
                    } catch (e: java.lang.Exception) {
                    }
                }

                // No internet Connection
            } catch (ignored: java.lang.Exception) {
            }
        }
    }


    private fun myDownloadStatus() {
        try {
            runOnUiThread {
                val get_Api_state =
                    sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "").toString()
                if (Utility.isNetworkAvailable(applicationContext)) {
                    // if Zip is enabled
                    if (get_Api_state == Constants.imagSwtichEnableSyncFromAPI) {
                        val get_progress =
                            myDownloadClass.getString(Constants.SynC_Status, "").toString()
                        if (get_progress.isNotEmpty()) {
                            textStatusProcess?.text = get_progress + ""
                        } else {
                            textStatusProcess?.text = "PR: Running"
                        }
                    } else {
                        textStatusProcess?.text = "PR: Running"
                    }
                } else {
                    textStatusProcess?.text = "No Internet"
                }
            }
        } catch (e: java.lang.Exception) {
        }
    }


    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    private fun showPopForTVConfiguration(message: String) {
        try {

                try {
                    val binding: CustomOfflinePopLayoutBinding = CustomOfflinePopLayoutBinding.inflate(
                        layoutInflater
                    )
                    val builder = AlertDialog.Builder(this@WebViewPage)
                    builder.setView(binding.getRoot())
                    alertDialog = builder.create() // Assign the dialog to the field
                    alertDialog!!.setCanceledOnTouchOutside(false)
                    alertDialog!!.setCancelable(false)
                    if (alertDialog!!.window != null) {
                        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog!!.window!!.attributes.windowAnimations = R.style.PauseDialogAnimationCloseOnly
                    }
                    val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
                    val textContinuPasswordDai3: TextView = binding.textContinuPasswordDai3
                    val textContinue: TextView = binding.textContinue
                    val textDescription: TextView = binding.textDescription
                    val imgCloseDialog: ImageView = binding.imgCloseDialog
                    val imageView24: ImageView = binding.imageView24
                    val consMainAlert_sub_layout: ConstraintLayout = binding.consMainAlertSubLayout
                    val preferences = PreferenceManager.getDefaultSharedPreferences(
                        applicationContext
                    )
                    if (preferences.getBoolean("darktheme", false)) {
                        consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout)
                        textDescription.setTextColor(resources.getColor(R.color.dark_light_gray_pop))
                        textContinuPasswordDai3.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)
                        textContinue.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black)
                        val drawable_imgCloseDialog = ContextCompat.getDrawable(
                            applicationContext, R.drawable.ic_close_24
                        )
                        if (drawable_imgCloseDialog != null) {
                            drawable_imgCloseDialog.setColorFilter(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.dark_light_gray_pop
                                ), PorterDuff.Mode.SRC_IN
                            )
                            imgCloseDialog.setImageDrawable(drawable_imgCloseDialog)
                        }
                    }


                    if (!message.isEmpty()) {
                        textDescription.text = message
                    }

                    if (message == Constants.UnableToFindIndex) {
                        if (preferences.getBoolean("darktheme", false)) {
                            val drawable_imageView24 =
                                ContextCompat.getDrawable(applicationContext, R.drawable.ic_folder_24)
                            if (drawable_imageView24 != null) {
                                drawable_imageView24.setColorFilter(
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.dark_light_gray_pop
                                    ), PorterDuff.Mode.SRC_IN
                                )
                                imageView24.setImageDrawable(drawable_imageView24)
                            }
                        } else {
                            imageView24.background = resources.getDrawable(R.drawable.ic_folder_24)
                        }
                    } else if (message == Constants.Check_Inter_Connectivity) {
                        if (preferences.getBoolean("darktheme", false)) {
                            val drawable_imageView24 = ContextCompat.getDrawable(
                                applicationContext, R.drawable.ic_wifi_no_internet
                            )
                            if (drawable_imageView24 != null) {
                                drawable_imageView24.setColorFilter(
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.dark_light_gray_pop
                                    ), PorterDuff.Mode.SRC_IN
                                )
                                imageView24.setImageDrawable(drawable_imageView24)
                            }
                        } else {
                            imageView24.background = resources.getDrawable(R.drawable.ic_wifi_no_internet)
                        }
                    } else {
                        if (preferences.getBoolean("darktheme", false)) {
                            val drawable_imageView24 =
                                ContextCompat.getDrawable(applicationContext, R.drawable.ic_sync_cm)
                            if (drawable_imageView24 != null) {
                                drawable_imageView24.setColorFilter(
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.dark_light_gray_pop
                                    ), PorterDuff.Mode.SRC_IN
                                )
                                imageView24.setImageDrawable(drawable_imageView24)
                            }
                        } else {
                            imageView24.background = resources.getDrawable(R.drawable.ic_sync_cm)
                        }
                    }


                    val editor222 = sharedBiometric.edit()
                    textContinuPasswordDai3.setOnClickListener {
                        try {
                            if (fetchListener != null) {
                                fetch!!.removeListener(fetchListener)
                            }

                            if (fetch != null) {
                                fetch!!.removeAll()
                            }


                            val myactivity = Intent(this@WebViewPage, ReSyncActivity::class.java)
                            startActivity(myactivity)
                            finish()
                            editor222.putString(Constants.SAVE_NAVIGATION, Constants.WebViewPage)
                            editor222.apply()
                            showToastMessage("Please wait")
                        } catch (e: Exception) {
                            Log.d(TAG, "showPopForTVConfiguration: "+e.message.toString())
                        }
                    }

                    textContinue.setOnClickListener {

                        //  if (get_AppMode == Constants.TV_Mode || jsonUrl == null) {
                        //     showToastMessage("Tap The Back Button to Go Settings Page")}



                        // get input paths to device storage
                        val fil_CLO = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                        val fil_DEMO = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                        val filename = "/index.html"
                        lifecycleScope.launch {
                            loadOffline_Saved_Path_Offline_Webview_For_Pop_Layout(
                                fil_CLO,
                                fil_DEMO,
                                filename
                            )
                        }


                        alertDialog!!.dismiss()
                    }
                    imgCloseDialog.setOnClickListener {
                        //if (get_AppMode == Constants.TV_Mode || jsonUrl == null) {
                        //    showToastMessage("Tap The Back Button to Go Settings Page") }

                        // get input paths to device storage
                        val fil_CLO = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                        val fil_DEMO = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                        val filename = "/index.html"
                        lifecycleScope.launch {
                            loadOffline_Saved_Path_Offline_Webview_For_Pop_Layout(
                                fil_CLO,
                                fil_DEMO,
                                filename
                            )
                        }


                        alertDialog!!.dismiss()
                    }
                    alertDialog!!.show()

                } catch (e: java.lang.Exception) {
                    Log.d(TAG, "showPopForTVConfiguration: "+e.message.toString())
                }


          //  handler.postDelayed(Runnable {
          //  },1000)
        }catch (e:Exception){
            Log.d(TAG, "showPopForTVConfiguration: Eroor ${e.message}")
        }
    }


    //////// The API SYNC
    //////// The API SYNC
    //////// The API SYNC
    //////// The API SYNC


    @SuppressLint("SuspiciousIndentation")
    private fun initStartSyncServices() {

        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val fil_CLO = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val fil_DEMO = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
        val get_intervals =
            sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "").toString()

        val get_Api_state =
            sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "").toString()
        // use to control Sync start
        val Manage_My_Sync_Start = myDownloadClass.getString(Constants.Manage_My_Sync_Start, "").toString()

        //  Set up for Api , if allowed to use Api

        if (fil_CLO.isNotEmpty() && fil_DEMO.isNotEmpty() && Manage_My_Sync_Start.isEmpty()) {

            // FOR API SYNC
            // FOR API SYNC
            try {
                //  check if allowed to use APi
                if (get_Api_state != Constants.imagSwtichEnableSyncFromAPI) {
                    //check if Allowed to use Download on change
                    if (get_intervals != Constants.imagSwtichEnableSyncOnFilecahnge) {
                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)
                        getTimeDefined.let { it1 ->
                            if (it1 != 0L) {

                                showToastMessage("API Sync On Change")
                                // Read and Save data time from server
                                ReadSyncTimeFromServer()

                                //start sync time
                                startTimerApiSync(getTimeDefined)
                            }
                        }

                    } else {
                        //  we are allowed to use Sync on Interval
                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)
                        getTimeDefined.let { it1 ->
                            if (it1 != 0L) {

                                showToastMessage("API Sync On Interval")
                                startTimerApiSync(getTimeDefined)
                            }
                        }

                    }


                }
            } catch (e: Exception) {
                Log.d(TAG, "initStartSyncServices: "+e.message.toString())
            }


            /// FOR ZIP SYNC
            /// FOR ZIP SYNC
            //  check if allowed to use APi

            try {
                if (get_Api_state == Constants.imagSwtichEnableSyncFromAPI) {
                    //check if Allowed to use Download on change
                    if (get_intervals != Constants.imagSwtichEnableSyncOnFilecahnge) {

                        /// implemnt Zip Logic on change

                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)
                        getTimeDefined.let { it1 ->
                            if (it1 != 0L) {

                                showToastMessage("Zip Sync On Change")
                                // Read and Save data time from server
                                ReadSyncTimeFromServer()

                                //start sync time
                                startTimerApiSync(getTimeDefined)
                            }
                        }


                    } else {
                        //  we are allowed to use Sync on Interval

                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)
                        getTimeDefined.let { it1 ->
                            if (it1 != 0L) {

                                showToastMessage("Zip Sync On Interval")
                                startTimerApiSync(getTimeDefined)
                            }
                        }
                    }


                }

            } catch (e: Exception) {
                Log.d(TAG, "initStartSyncServices: "+e.message.toString())
            }


        }


    }


    private fun startTimerApiSync(minutes: Long) {
        val milliseconds = minutes * 60 * 1000 // Convert minutes to
        countdownTimer_Api_Sync = object : CountDownTimer(milliseconds, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                startTimerApiSync(minutes)

                try {
                    val sharedBiometric =
                        getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
                    val get_intervals =
                        sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "")
                            .toString()

                    if (get_intervals == Constants.imagSwtichEnableSyncOnFilecahnge) {
                        if (isAPISyncRunning) {
                            // Sync on Interval for Zip
                            init_Zip_Sync_Start()
                        } else {
                            // Sync On interval API
                            init_APi_Sync_Start()

                        }


                    } else {
                        //  sync on Change
                        get_Index_Time_Stamp_Loaction()
                    }


                } catch (e: java.lang.Exception) {
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    val totalSecondsRemaining = millisUntilFinished / 1000
                    var minutesUntilFinished = totalSecondsRemaining / 60
                    var remainingSeconds = totalSecondsRemaining % 60

                    if (remainingSeconds == 0L && minutesUntilFinished > 0) {
                        minutesUntilFinished--
                        remainingSeconds = 59
                    }
                    val displayText =
                        String.format("CD: %d:%02d", minutesUntilFinished, remainingSeconds)
                    countDownTime!!.text = displayText

                } catch (ignored: java.lang.Exception) {
                }
            }
        }
        countdownTimer_Api_Sync?.start()
    }


    private fun get_Index_Time_Stamp_Loaction() {
        if (Utility.isNetworkAvailable(applicationContext)) {

            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

            val get_tMaster: String = myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
            val get_UserID: String = myDownloadClass.getString(Constants.getSavedCLOImPutFiled, "").toString()
            val get_LicenseKey: String = myDownloadClass.getString(Constants.getSaveSubFolderInPutFiled, "").toString()
            val imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()
            val get_imagSwtichUseIndexCahngeOrTimeStamp = sharedBiometric.getString(Constants.imagSwtichUseIndexCahngeOrTimeStamp, "").toString()
            val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()


            if (imagSwtichPartnerUrl == Constants.imagSwtichPartnerUrl) {

                if (get_imagSwtichUseIndexCahngeOrTimeStamp.equals(Constants.imagSwtichUseIndexCahngeOrTimeStamp)) {
                    val urlPath = "${CP_AP_MASTER_DOMAIN}/$get_UserID/$get_LicenseKey/App/index.html"
                    Implement_Logic_With_Index_OnChange(urlPath)

                } else {
                    val un_dynaic_path = CP_AP_MASTER_DOMAIN
                    val dynamicPart = "$get_UserID/$get_LicenseKey/PTime/"
                    Implement_Logic_With_PT_Server_Time(un_dynaic_path, dynamicPart)
                    Log.d("OnChnageService", "Img  $un_dynaic_path$dynamicPart")
                }


            } else {

                if (get_imagSwtichUseIndexCahngeOrTimeStamp.equals(Constants.imagSwtichUseIndexCahngeOrTimeStamp)) {
                    val urlPath = "$get_tMaster/$get_UserID/$get_LicenseKey/App/index.html"
                    Implement_Logic_With_Index_OnChange(urlPath)

                } else {
                    val dynamicPart = "$get_UserID/$get_LicenseKey/PTime/"
                    Implement_Logic_With_PT_Server_Time(get_tMaster, dynamicPart)
                    Log.d("OnChnageService", "$get_tMaster$dynamicPart")
                }


            }


        } else {
            showToastMessage("No internet Connection")


        }
    }


    private fun ReadSyncTimeFromServer() {

        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
        val fileName = myDownloadClass.getString(Constants.fileName, "").toString()
        val baseUrl = myDownloadClass.getString(Constants.baseUrl, "").toString()
        val get_imagSwtichUseIndexCahngeOrTimeStamp =
            sharedBiometric.getString(Constants.imagSwtichUseIndexCahngeOrTimeStamp, "").toString()


        if (Utility.isNetworkAvailable(applicationContext)) {
            handler.postDelayed(Runnable {

                if (baseUrl.isNotEmpty() && getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty() && fileName.isNotEmpty()) {

                    if (get_imagSwtichUseIndexCahngeOrTimeStamp.equals(Constants.imagSwtichUseIndexCahngeOrTimeStamp)) {

                        fetchIndexChangeTime()

                    } else {
                        getPT_Time_From_JSON()
                    }


                } else {
                    showToastMessage("Invalid Path Format")
                }

            }, 500)
        } else {
            showToastMessage("No internet Connection")
        }

    }


    private fun getPT_Time_From_JSON() {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val currentTime = myDownloadClass.getString(Constants.CurrentServerTime, "").toString()
        val severTime = myDownloadClass.getString(Constants.SeverTimeSaved, "").toString()

        if (currentTime.isEmpty() || severTime.isEmpty()) {

            val get_tMaster: String = myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
            val get_UserID: String = myDownloadClass.getString(Constants.getSavedCLOImPutFiled, "").toString()
            val get_LicenseKey: String = myDownloadClass.getString(Constants.getSaveSubFolderInPutFiled, "").toString()
            val imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()
            val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

            if (imagSwtichPartnerUrl == Constants.imagSwtichPartnerUrl) {
                val un_dynaic_path = CP_AP_MASTER_DOMAIN
                val dynamicPart = "$get_UserID/$get_LicenseKey/PTime/"
                Check_Updated_Time_From_JSON(un_dynaic_path, dynamicPart)

                Log.d("OnChnageService", " $un_dynaic_path$dynamicPart")

            } else {

                val dynamicPart = "$get_UserID/$get_LicenseKey/PTime/"
                Check_Updated_Time_From_JSON(get_tMaster, dynamicPart)

                Log.d("OnChnageService", "$get_tMaster$dynamicPart")

            }

        }
    }

    private fun Check_Updated_Time_From_JSON(baseUrl: String, dynamicPart: String) {


        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val api = Retro_On_Change.create(baseUrl)
                val response = api.getAppConfig(dynamicPart)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val getvalue = response.body()?.last_updated.toString()

                        val editor = myDownloadClass.edit()
                        editor.putString(Constants.SeverTimeSaved, getvalue)
                        editor.apply()
                        Log.d(TAG, "JSON PT TIME UPDated")

                    } else {
                        showToastMessage("bad request")
                    }


                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Check_Updated_Time_From_JSON: "+e.message.toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Check_Updated_Time_From_JSON: "+e.message.toString())
                }
            }
        }


    }


    private fun fetchIndexChangeTime() {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val currentTime = myDownloadClass.getString(Constants.CurrentServerTime_for_IndexChange, "").toString()
        val severTime = myDownloadClass.getString(Constants.SeverTimeSaved_For_IndexChange, "").toString()

        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()

        if (currentTime.isEmpty() || severTime.isEmpty()) {

            val get_tMaster: String = myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
            val get_UserID: String = myDownloadClass.getString(Constants.getSavedCLOImPutFiled, "").toString()
            val get_LicenseKey: String = myDownloadClass.getString(Constants.getSaveSubFolderInPutFiled, "").toString()

            val imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()

            if (imagSwtichPartnerUrl == Constants.imagSwtichPartnerUrl) {

                val urlPath = "${CP_AP_MASTER_DOMAIN}/$get_UserID/$get_LicenseKey/App/index.html"
                checkIndexFileChange(urlPath)

            } else {

                val urlPath = "$get_tMaster$get_UserID/$get_LicenseKey/App/index.html"
                checkIndexFileChange(urlPath)
            }


        }


    }


    private fun checkIndexFileChange(url: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val checker = IndexFileChecker(url)
            val result = checker.checkIndexFileChange()

            withContext(Dispatchers.Main) {
                val editor = myDownloadClass.edit()
                editor.putString(Constants.SeverTimeSaved, result)
                editor.apply()
            }
        }
    }


    private fun Implement_Logic_With_Index_OnChange(urlPath: String) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {

                val checker = IndexFileChecker(urlPath)
                val getvalue = checker.checkIndexFileChange()


                withContext(Dispatchers.Main) {
                    val myDownloadClass =
                        getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                    val editor = myDownloadClass.edit()

                    editor.putString(Constants.CurrentServerTime, getvalue)
                    editor.apply()

                    val severTime =
                        myDownloadClass.getString(Constants.SeverTimeSaved, "").toString()

                    handler.postDelayed(Runnable {

                        if (getvalue == severTime) {

                            if (isdDownloadApi) {
                                textStatusProcess?.text = Constants.PR_NO_CHange

                                handler.postDelayed(Runnable {
                                    textStatusProcess?.text = Constants.PR_running
                                }, 1500)

                            } else {

                                val get_progress =
                                    myDownloadClass.getString(Constants.SynC_Status, "").toString()
                                if (get_progress.isNotEmpty()) {
                                    textStatusProcess!!.text = get_progress + ""
                                } else {
                                    textStatusProcess!!.text = "PR: Running"
                                }
                                showToastMessage("Sync Already in Progress")

                            }

                        } else {
                            checkIndexFileChange(urlPath)

                            if (isAPISyncRunning) {
                                // Sync on Interval Zip
                                init_Zip_Sync_Start()
                            } else {
                                // Sync On interval API
                                init_APi_Sync_Start()

                            }


                        }

                    }, 200)

                }

            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Implement_Logic_With_Index_OnChange: "+e.message.toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToastMessage("No internet Connection")


                }
            }
        }
    }


    private fun Implement_Logic_With_PT_Server_Time(baseUrl: String, dynamicPart: String) {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val editor = myDownloadClass.edit()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val api = Retro_On_Change.create(baseUrl)
                val response = api.getAppConfig(dynamicPart)


                if (response.isSuccessful) {

                    withContext(Dispatchers.Main) {
                        val getvalue = response.body()?.last_updated.toString()
                        editor.putString(Constants.CurrentServerTime, getvalue)
                        editor.apply()

                        val severTime =
                            myDownloadClass.getString(Constants.SeverTimeSaved, "").toString()

                        handler.postDelayed(Runnable {

                            if (getvalue == severTime) {

                                if (isdDownloadApi) {
                                    textStatusProcess?.text = Constants.PR_NO_CHange

                                    handler.postDelayed(Runnable {
                                        textStatusProcess?.text = Constants.PR_running
                                    }, 1500)

                                } else {

                                    val get_progress =
                                        myDownloadClass.getString(Constants.SynC_Status, "")
                                            .toString()
                                    if (get_progress.isNotEmpty()) {
                                        textStatusProcess?.text = get_progress + ""
                                    } else {
                                        textStatusProcess?.text = "PR: Running"
                                    }
                                    showToastMessage("Sync Already in Progress")

                                }


                            } else {

                                Check_Updated_Time_From_JSON(baseUrl, dynamicPart)

                                if (isAPISyncRunning) {
                                    // Sync on Interval Zip
                                    init_Zip_Sync_Start()
                                } else {
                                    // Sync On interval API
                                    init_APi_Sync_Start()

                                }


                            }

                        }, 200)

                    }
                } else {
                    showToastMessage("bad request")
                }


            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Implement_Logic_With_PT_Server_Time: "+e.message.toString())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToastMessage("No internet Connection")
                }
            }
        }
    }


    // init the download


    private fun Init_Fetch_Download_Lsitner() {


        try {
            // Initialize Fetch
            initializeFetch()
            // Initialize Listener
            initializeListener()
        } catch (e: java.lang.Exception) {
        }

        // remove any pending fetch download that is enqueued in other to have clean slate
        try {
            fetch?.let { it.removeAll() }
        } catch (e: Exception) {
            Log.d(TAG, "Init_Fetch_Download_Lsitner: "+e.message.toString())
        }

    }


    private fun initializeFetch() {
        try {

            val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(this)
                .enableRetryOnNetworkGain(true)
                .setAutoRetryMaxAttempts(10)
                .setDownloadConcurrentLimit(1)
                .setHttpDownloader(HttpUrlConnectionDownloader(FileDownloaderType.SEQUENTIAL))
                .build()
            fetch = getInstance(fetchConfiguration)
            fetch!!.setGlobalNetworkType(NetworkType.ALL)
        } catch (e: java.lang.Exception) {
        }
    }

    private fun init_APi_Sync_Start() {
        try {
            if (isdDownloadApi) {
                mFilesViewModel.deleteAllFiles()
                currentDownloadIndex = 0
                totalFiles = 0
                progressBarPref!!.progress = 0

                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

                val get_intervals = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "").toString()
                if (get_intervals == Constants.imagSwtichEnableSyncOnFilecahnge) {
                    textStatusProcess?.text = Constants.PR_running
                } else {
                    textStatusProcess?.text = Constants.PR_Change_Found
                }

                val editior = myDownloadClass.edit()
                editior.remove(Constants.fileNumber)
                editior.apply()

                if (Utility.isNetworkAvailable(applicationContext)) {
                    startMyCSVApiDownload()
                    //  showToastMessage("Sync Started")
                } else {
                    showToastMessage("No Internet Connection")
                }
            } else {
                showToastMessage("Sync Already Running")
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun startMyCSVApiDownload() {

        val imagUsemanualOrnotuseManual =
            sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "").toString()
        val getSavedEditTextInputSynUrlZip =
            myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()
        if (Constants.imagSwtichEnableManualOrNot == imagUsemanualOrnotuseManual) {
            if (getSavedEditTextInputSynUrlZip.contains(Constants.myCSvEndPath)
                || getSavedEditTextInputSynUrlZip.contains(
                    Constants.myCSVUpdate1
                )
            ) {
                apiInitialization_for_none_manual()

            } else {
                showToastMessage("API not readable from location")
            }
        } else {
            apiInitialization()
        }
    }


    private fun apiInitialization() {
        try {

            lifecycleScope.launch(Dispatchers.IO) {

                withContext(Dispatchers.Main) {
                    textDownladByes!!.visibility = View.GONE
                }
                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val getFolderClo = myDownloadClass.getString(sync2app.com.syncapplive.additionalSettings.utils.Constants.getFolderClo, "").toString()
                val getFolderSubpath = myDownloadClass.getString(sync2app.com.syncapplive.additionalSettings.utils.Constants.getFolderSubpath, "").toString()
                val get_ModifiedUrl = myDownloadClass.getString(sync2app.com.syncapplive.additionalSettings.utils.Constants.get_ModifiedUrl, "").toString()

                val lastEnd = Constants.myCSVUpdate1
                val csvDownloader = CSVDownloader()
                val csvData = csvDownloader.downloadCSV(get_ModifiedUrl, getFolderClo, getFolderSubpath, lastEnd)
                saveURLPairs(csvData)

                withContext(Dispatchers.Main) {
                    handler.postDelayed({ handler.postDelayed(runnableGetApiStart, 500) }, 1000)
                }
            }


        } catch (e: java.lang.Exception) {
        }
    }


    private fun saveURLPairs(csvData: String) {

        val pairs = parseCSV(csvData)
        lifecycleScope.launch(Dispatchers.IO) {
            for ((index, line) in pairs.withIndex()) {
                val parts = line.split(",").map { it.trim() }
                if (parts.size < 2) continue

                val sn = parts[0].toIntOrNull() ?: continue
                val folderAndFile = parts[1].split("/")

                val folderName = if (folderAndFile.size > 1) {
                    folderAndFile.dropLast(1).joinToString("/")
                } else {
                    "MyApiFolder"
                }

                val fileName = folderAndFile.lastOrNull() ?: continue
                val status = "true"

                val files = FilesApi(
                    SN = sn.toString(),
                    FolderName = folderName,
                    FileName = fileName,
                    Status = status
                )
                mFilesViewModel.addFiles(files)

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


    private val runnableGetApiStart: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            mFilesViewModel.readAllData.observe(this@WebViewPage, Observer { files ->
                handler.postDelayed({
                    runOnUiThread {
                        textFilecount!!.text = "DL-/-"
                        totalFiles = files.size
                        textFilecount!!.visibility = View.VISIBLE
                        downloadSequentially(files)
                    }
                }, 500)
            })
        }

    }


    private fun downloadSequentially(files: List<FilesApi>) {
        try {
            if (currentDownloadIndex < files.size) {
                val (_, SN, FolderName, FileName) = files[currentDownloadIndex]
                handler.postDelayed({ getFilesDownloads(SN, FolderName, FileName) }, 1000)
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun getFilesDownloads(sn: String, folderName: String, fileName: String) {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    isdDownloadApi = false
                    iswebViewRefreshingOnApiSync = true
                }

                // Create directory and delete existing file if necessary
                val saveMyFileToStorage = constructFilePath(folderName)
                val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveMyFileToStorage)
                val myFile = File(dir, fileName)
                delete(myFile)

                // Delay and enqueue the download request
                delay(200)

                // Re-create directory if it doesn't exist
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val url = constructDownloadUrl(folderName, fileName)
                val file = File(dir, fileName).absolutePath
                val request = Request(url, file).apply {
                    priority = Priority.HIGH
                    networkType = NetworkType.ALL
                    addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")
                }

                fetch!!.enqueue(request, { updatedRequest: Request? -> }) { error: Error ->
                    Log.e("onRequest", "Error: $error")
                }

                // Save file number
                val editor = myDownloadClass.edit()
                editor.putInt(Constants.fileNumber, sn.toInt())
                editor.apply()
            }
        } catch (e: Exception) {
            Log.e("getFilesDownloads", "Error occurred: ${e.message}")
        }
    }




    private fun constructFilePath(folderName: String): String {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").orEmpty()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").orEmpty()
        val Syn2AppLive = Constants.Syn2AppLive
        return "/$Syn2AppLive/$getFolderClo/$getFolderSubpath/$folderName"
    }

    private fun constructDownloadUrl(folderName: String, fileName: String): String {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").orEmpty()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").orEmpty()
        val get_ModifiedUrl = myDownloadClass.getString(Constants.get_ModifiedUrl, "").orEmpty()
        return "$get_ModifiedUrl/$getFolderClo/$getFolderSubpath/$folderName/$fileName"
    }


    /// Init Zip Download
    /// Init Zip Download
    /// Init Zip Download

    private fun init_Zip_Sync_Start() {
        try {
            if (isdDownloadApi) {
                progressBarPref!!.progress = 0

                val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

                val get_intervals = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "").toString()
                if (get_intervals == Constants.imagSwtichEnableSyncOnFilecahnge) {
                    textStatusProcess?.text = Constants.PR_running
                } else {
                    textStatusProcess?.text = Constants.PR_Change_Found
                }

                if (Utility.isNetworkAvailable(applicationContext)) {
                    manage_Zip_Download()

                } else {
                    showToastMessage("No Internet Connection")
                }
            } else {
                showToastMessage("Sync Already Running")
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun manage_Zip_Download() {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
        val imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "").toString()
        val imagSwtich_get_manual = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "").toString()

        val CP_AP_MASTER_DOMAIN = myDownloadClass.getString(Constants.CP_OR_AP_MASTER_DOMAIN, "").toString()


        // when enable Sync Zip is  toggle On from Syn manager Page
        if (isAPISyncRunning) {

            // Manual is allowed
            if (imagSwtich_get_manual.equals(Constants.imagSwtichEnableManualOrNot)) {
                val get_edit_Saved_url_manual_zip =
                    myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "")
                        .toString()

                lifecycleScope.launch {
                    val result = checkUrlExistence(get_edit_Saved_url_manual_zip)
                    if (result) {
                        get_Zip_FilesDownloads(get_edit_Saved_url_manual_zip)

                    } else {
                        withContext(Dispatchers.Main) {
                            showToastMessage("Invalid url")
                        }
                    }
                }

            } else {

                /// if not allowed to use manual
                if (imagSwtichPartnerUrl == Constants.imagSwtichPartnerUrl) {

                    val baseUrl = "${CP_AP_MASTER_DOMAIN}/$getFolderClo/$getFolderSubpath/Zip/App.zip"

                    lifecycleScope.launch {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            get_Zip_FilesDownloads(baseUrl)

                        } else {
                            withContext(Dispatchers.Main) {
                                showToastMessage("Invalid url")

                            }
                        }
                    }


                } else {

                    // No partner url
                    val get_tMaster: String =
                        myDownloadClass.getString(Constants.get_ModifiedUrl, "").toString()
                    val baseUrl = "$get_tMaster$getFolderClo/$getFolderSubpath/Zip/App.zip"

                    lifecycleScope.launch {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            get_Zip_FilesDownloads(baseUrl)
                        } else {
                            withContext(Dispatchers.Main) {
                                showToastMessage("Invalid url")

                            }
                        }
                    }

                }
            }
        }

    }


    private fun get_Zip_FilesDownloads(url: String) {
        try {

            binding.textStatusProcess.text = Constants.PR_checking

            lifecycleScope.launch(Dispatchers.IO) {
                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                val DeleteFolderPath = "/$getFolderClo/$getFolderSubpath/${Constants.Zip}/${Constants.fileNmae_App_Zip}"
                val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}$DeleteFolderPath"
                val file = File(directoryPath)
                delete(file)
            }


            handler.postDelayed({

                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        isdDownloadApi = false
                        iswebViewRefreshingOnApiSync = true
                        binding.textFilecount.text = "1/1"
                        binding.textStatusProcess.text = Constants.PR_Downloading
                        binding.progressBarPref.visibility = View.VISIBLE

                        val myDownloadClass =
                            getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val editorSyn = myDownloadClass.edit()
                        editorSyn.putString(Constants.SynC_Status, Constants.PR_Downloading)
                        editorSyn.apply()

                    }

                    val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                    val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                    val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()

                    val finalFolderPath = "/$getFolderClo/$getFolderSubpath/${Constants.Zip}"
                    val dir = File(Environment.getExternalStorageDirectory().toString() + "/Download/${Constants.Syn2AppLive}/$finalFolderPath")

                    // create folder if not exist
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }

                    val file = File(dir, "App.zip").absolutePath
                    val request = Request(url, file)
                    request.priority = Priority.HIGH
                    request.networkType = NetworkType.ALL
                    request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")

                    fetch?.enqueue(request, { updatedRequest: Request? ->
                        // handle success
                    }) { error: Error ->
                        Log.e("WebZippper", "Error: $error")

                        runOnUiThread {
                            // tell the system allow download again because of an error
                            isdDownloadApi = true

                        }
                    }

                }
            }, 1500)


        } catch (e: Exception) {
            Log.e("WebZippper", "Exception occurred: ${e.message}")
            runOnUiThread {
                // tell the system allow download again because of an error
                isdDownloadApi = true

            }
        }
    }


    private fun funUnZipFile() {
        try {

            lifecycleScope.launch(Dispatchers.IO) {

                withContext(Dispatchers.Main) {
                    binding.textStatusProcess.text = Constants.PR_checking
                    binding.progressBarPref.visibility = View.VISIBLE
                    binding.textFilecount.text = "1/1"
                    binding.textDownladByes.text = "100%"
                }

                val myDownloadClass =
                    getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                val getFolderSubpath =
                    myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                val Zip = myDownloadClass.getString(Constants.Zip, "").toString()
                val Extracted = myDownloadClass.getString(Constants.Extracted, "").toString()


                // val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
                //  val finalFolderPathDesired = "/$getFolderClo/$getFolderSubpath/$Extracted"


                val finalFolderPath = "/$getFolderClo/$getFolderSubpath/${Constants.Zip}"
                val finalFolderPathDesired = "/$getFolderClo/$getFolderSubpath/${Constants.App}"


                val directoryPathString = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPath
                val destinationFolder =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPathDesired)

                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs()
                }

                val myFile = File(directoryPathString, File.separator + Constants.fileNmae_App_Zip)
                if (myFile.exists()) {
                    extractZip(myFile.toString(), destinationFolder.toString())
                } else {
                    withContext(Dispatchers.Main) {
                        binding.textStatusProcess.text = Constants.PR_Zip_error
                        binding.progressBarPref.visibility = View.INVISIBLE
                        binding.textFilecount.text = "1/1"
                        binding.textDownladByes.visibility = View.INVISIBLE

                        // tell the system allow download again because of an error
                        isdDownloadApi = true

                        showToastMessage("my Zip file Not found")

                    }
                }
            }
        } catch (e: Exception) {
            // Handle exceptions if necessary
            Log.d(TAG, "funUnZipFile: ${e.message.toString()}")
            runOnUiThread {
                binding.textStatusProcess.text = Constants.PR_Zip_error
                binding.progressBarPref.visibility = View.INVISIBLE
                binding.textFilecount.text = "1/1"
                binding.textDownladByes.visibility = View.INVISIBLE

                // tell the system allow download again because of an error
                isdDownloadApi = true
            }
        }
    }


    suspend fun extractZip(zipFilePath: String, destinationPath: String) {
        try {
            withContext(Dispatchers.Main) {
                binding.textStatusProcess.text = Constants.PR_Extracting
                binding.progressBarPref.visibility = View.VISIBLE
                binding.textFilecount.text = "1/1"
                binding.textDownladByes.text = "100%"

                val myDownloadClass =
                    getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val editorSyn = myDownloadClass.edit()
                editorSyn.putString(Constants.SynC_Status, Constants.PR_Extracting)
                editorSyn.apply()

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
                    /// Log.i("ExternalStorage", "Scanned $path:")
                    //  Log.i("ExternalStorage", "-> uri=$uri")
                }

            }

            withContext(Dispatchers.IO) {
                zipInputStream.close()
            }

            withContext(Dispatchers.Main) {
                // media is ready
                Refresh_WebView_After_Extraction()
                showToastMessage(Constants.media_ready)

            }

        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Log.d(TAG, "funUnZipFile: ${e.message.toString()}")
                showToastMessage(Constants.Error_during_zip_extraction)
                Refresh_WebView_After_Extraction()

            }
        }
    }

    private fun Refresh_WebView_After_Extraction() {
        try {

            binding.progressBarPref.visibility = View.VISIBLE
            binding.textStatusProcess.text = Constants.PR_Refresh
            binding.textFilecount.text = "1/1"
            binding.textDownladByes.text = "100%"

            handler.postDelayed({
                isdDownloadApi = true

                if (isScheduleRunning) {
                    showToastMessage("Schedule Media Already Running")
                    binding.textStatusProcess.text = Constants.PR_running
                    binding.progressBarPref.progress = 100
                    binding.progressBarPref.visibility = View.INVISIBLE
                    binding.textFilecount.text = "1/1"
                } else {
                    offline_Load_Webview_Logic()
                    binding.textStatusProcess.text = Constants.PR_running
                    binding.progressBarPref.progress = 100
                    binding.progressBarPref.visibility = View.INVISIBLE
                    binding.textFilecount.text = "1/1"

                    try {
                        fetch?.let { it.removeAll() }
                    } catch (e: Exception) {
                        Log.d(TAG, "Refresh_WebView_After_Extraction: " + e.message.toString())
                    }

                }
            }, 3000)

        } catch (_: Exception) {
        }
    }


//  End of Init Zip Download
//  End of Init Zip Download
//  End of Init Zip Download


    private fun delete(file: File): Boolean {
        if (file.isFile) {
            return file.delete()
        } else if (file.isDirectory) {
            val subFiles = Objects.requireNonNull(file.listFiles())
            for (subFile in subFiles) {
                if (!delete(subFile)) {
                    return false
                }
            }
            return file.delete()
        }
        return false
    }


    ///settimg up that of manual Api sync
    private fun apiInitialization_for_none_manual() {
        try {

            lifecycleScope.launch(Dispatchers.IO) {

                withContext(Dispatchers.Main) {
                    textDownladByes!!.visibility = View.GONE
                }
                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                val getSavedEditTextInputSynUrlZip = myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()
                val csvDownloader = CSVDownloader()
                val csvData = csvDownloader.downloadCSV(getSavedEditTextInputSynUrlZip, "", "", "")
                saveURLPairs(csvData)
                withContext(Dispatchers.Main) {
                    handler.postDelayed({ handler.postDelayed(runnableManual, 500) }, 1000)
                }
            }

        } catch (e: java.lang.Exception) {
        }
    }


    private val runnableManual: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun run() {
            mFilesViewModel.readAllData.observe(this@WebViewPage, Observer { files ->
                handler.postDelayed({
                    runOnUiThread {
                        textFilecount!!.text = "DL-/-"
                        totalFiles = files.size
                        textFilecount!!.visibility = View.VISIBLE
                        downloadSequentiallyManually(files)
                    }
                }, 500)
            })
        }

    }


    private fun downloadSequentiallyManually(files: List<FilesApi>) {
        try {
            if (currentDownloadIndex < files.size) {
                val (_, SN, FolderName, FileName) = files[currentDownloadIndex]
                handler.postDelayed({ getZipDownloadsManually(SN, FolderName, FileName) }, 1000)
            }
        } catch (e: java.lang.Exception) {
        }
    }



    private fun getZipDownloadsManually(sn: String, folderName: String, fileName: String) {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    isdDownloadApi = false
                    iswebViewRefreshingOnApiSync = true
                }

                val saveMyFileToStorage = "/${Constants.Syn2AppLive}/CLO/MANUAL/DEMO/$folderName"
                val getSavedEditTextInputSynUrlZip = myDownloadClass.getString(Constants.getSavedEditTextInputSynUrlZip, "").toString()
                val replacedUrl = replaceUrl(getSavedEditTextInputSynUrlZip, folderName, fileName)

                replacedUrl?.let { url ->
                    val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + saveMyFileToStorage
                    val myFile = File(directoryPath, fileName)
                    delete(myFile)

                    delay(200)

                    val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveMyFileToStorage)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }

                    val file = File(dir, fileName).absolutePath
                    val request = Request(url, file).apply {
                        priority = Priority.HIGH
                        networkType = NetworkType.ALL
                        addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")
                    }

                    fetch?.enqueue(request, { updatedRequest: Request? ->
                        // Handle success
                    }) { error: Error ->
                        Log.e("onRequest", "Error: $error")
                    }

                    myDownloadClass.edit().apply {
                        putInt(Constants.fileNumber, sn.toInt())
                        apply()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("getZipDownloadsManually", "Exception occurred: ${e.message}", e)
        }
    }


    private fun replaceUrl(url: String, folderName: String, fileName: String): String? {
        return when {url.contains(Constants.myCSvEndPath) -> url.replace(Constants.myCSvEndPath, "/$folderName/$fileName")
            url.contains(Constants.myCSVUpdate1, ) -> url.replace(Constants.myCSVUpdate1, "/$folderName/$fileName")
            else -> null
        }
    }


    private fun initializeListener() {
        try {
            val fetchListener: FetchListener = object : FetchListener {
                override fun onCompleted(download: Download) {

                    if (isAPISyncRunning) {
                        funUnZipFile()
                    } else {
                        preLaunchFiles()
                    }

                }


                @SuppressLint("SetTextI18n")
                override fun onError(download: Download, error: Error, throwable: Throwable?) {

                    if (!isAPISyncRunning) {
                        preLaunchFiles()
                    }

                    Log.d(
                        TAG,
                        "onError:  An error cocured trying o download from path/url" + error.httpResponse?.code
                    )
                }

                override fun onDownloadBlockUpdated(
                    download: Download,
                    downloadBlock: DownloadBlock,
                    i: Int
                ) {
                }

                override fun onAdded(download: Download) {}
                override fun onQueued(download: Download, waitingOnNetwork: Boolean) {}
                override fun onProgress(
                    download: Download,
                    etaInMilliSeconds: Long,
                    downloadedBytesPerSecond: Long
                ) {
                    try {
                        if (isAPISyncRunning) {

                            /// allowed to use only for Zip
                            val progress = download.progress
                            binding.progressBarPref.progress = progress
                            binding.textDownladByes.visibility = View.VISIBLE
                            binding.textDownladByes.text = "$progress%"
                        }

                    } catch (e: Exception) {
                        Log.d(TAG, e.message.toString())
                    }
                }

                override fun onPaused(download: Download) {}

                override fun onResumed(download: Download) {}

                override fun onStarted(
                    download: Download,
                    downloadBlocks: List<DownloadBlock>,
                    totalBlocks: Int
                ) {
                }

                override fun onWaitingNetwork(download: Download) {}

                override fun onCancelled(download: Download) {}
                override fun onRemoved(download: Download) {}
                override fun onDeleted(download: Download) {}
            }
            fetch!!.addListener(fetchListener)
        } catch (e: java.lang.Exception) {
        }
    }


    @SuppressLint("SetTextI18n")
    private fun preLaunchFiles() {
        try {
            currentDownloadIndex++
            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val sn_number = myDownloadClass.getInt(Constants.fileNumber, 0)

            handler.postDelayed({
                downloadSequentially(mFilesViewModel.readAllData.value ?: emptyList())

                runOnUiThread {
                    textFilecount!!.text = "$sn_number / $totalFiles"
                    val fileNum = sn_number.toDouble()
                    val totalPercentage = (fileNum / totalFiles.toDouble() * 100).toInt()
                    progressBarPref!!.progress = totalPercentage
                    progressBarPref!!.visibility = View.VISIBLE
                    textDownladByes!!.text = "$totalPercentage%"
                    textDownladByes!!.visibility = View.VISIBLE
                    textStatusProcess!!.text = Constants.PR_Downloading
                    if (sn_number == totalFiles) {
                        if (iswebViewRefreshingOnApiSync) {
                            iswebViewRefreshingOnApiSync = false
                            textStatusProcess!!.text = Constants.PR_Refresh
                            handler.postDelayed({
                                isdDownloadApi = true
                                if (isScheduleRunning) {
                                    showToastMessage("Schedule Media Already Running")
                                    textStatusProcess!!.text = Constants.PR_running
                                    progressBarPref!!.progress = 100
                                    progressBarPref!!.visibility = View.INVISIBLE
                                    textFilecount!!.text = "$totalFiles / $totalFiles"
                                } else {
                                    offline_Load_Webview_Logic()
                                    textStatusProcess!!.text = Constants.PR_running
                                    progressBarPref!!.progress = 100
                                    progressBarPref!!.visibility = View.INVISIBLE
                                    textFilecount!!.text = "$totalFiles / $totalFiles"


                                    try {
                                        fetch?.let { it.removeAll() }
                                    } catch (e: Exception) {
                                        Log.d(TAG, "preLaunchFiles: " + e.message.toString())
                                    }


                                }
                            }, 3000)
                        }
                    }
                }
            }, 500)
        } catch (e: java.lang.Exception) {
        }
    }

    private fun offline_Load_Webview_Logic() {

        val myDownloadClassDD = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val fil_CLO = myDownloadClassDD.getString(Constants.getFolderClo, "").toString()
        val fil_DEMO = myDownloadClassDD.getString(Constants.getFolderSubpath, "").toString()

        val filename = "/index.html"
        lifecycleScope.launch {
            loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO, filename)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun update_UI_for_API_Sync_Updade() {
        try {
            runOnUiThread {
                try {
                    val myDownloadClass =
                        getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                    val getFolderClo =
                        myDownloadClass.getString(Constants.getFolderClo, "").toString()
                    val getFolderSubpath =
                        myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                    val zip = myDownloadClass.getString("Zip", "").toString()
                    val Manage_My_Sync_Start =
                        myDownloadClass.getString(Constants.Manage_My_Sync_Start, "").toString()

                    //set folder path
                    val finalFolderPath = "LN: $getFolderClo/$getFolderSubpath"
                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty() && Manage_My_Sync_Start.isEmpty()) {
                        textLocation!!.text = finalFolderPath
                    } else {
                        textLocation!!.text = "LN: --"
                    }


                    //set type mode
                    if (zip.isNotEmpty() && Manage_My_Sync_Start!!.isEmpty()) {
                        textSyncMode!!.text = "SM: API"
                    } else {
                        textSyncMode!!.text = "SM: --"
                    }
                    if (getFolderClo.isNotEmpty() && !getFolderSubpath.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                        //check for time state
                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)
                        if (getTimeDefined != 0L && Manage_My_Sync_Start.isEmpty()) {
                            textSynIntervals!!.text = "ST: $getTimeDefined Mins"
                        } else {
                            textSynIntervals!!.text = "ST: --"
                        }


                        // check if running or not
                        if (Utility.isNetworkAvailable(applicationContext)) {
                            textStatusProcess!!.text = "PR: Running"
                        } else {
                            textStatusProcess!!.text = "No Internet"
                        }
                    }
                } catch (e: java.lang.Exception) {
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "update_UI_for_API_Sync_Updade: " + e.message!!)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun updateSyncViewZip() {
        try {
            runOnUiThread {
                try {
                    val myDownloadClass =
                        getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                    val getFolderClo =
                        myDownloadClass.getString(Constants.getFolderClo, "").toString()
                    val getFolderSubpath =
                        myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                    val zip = myDownloadClass.getString("Zip", "").toString()
                    val get_progress =
                        myDownloadClass.getString(Constants.SynC_Status, "").toString()
                    val Manage_My_Sync_Start =
                        myDownloadClass.getString(Constants.Manage_My_Sync_Start, "").toString()


                    val finalFolderPath = "LN: $getFolderClo/$getFolderSubpath"
                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty() && Manage_My_Sync_Start.isEmpty()) {
                        textLocation!!.text = finalFolderPath
                    } else {
                        textLocation!!.text = "LN: --"
                    }
                    if (zip.isNotEmpty() && Manage_My_Sync_Start.isEmpty()) {
                        textSyncMode!!.text = "SM: " + "ZIP"
                    } else {
                        textSyncMode!!.text = "SM: --"
                    }
                    if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty() && Manage_My_Sync_Start.isEmpty()) {
                        val getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0)

                        if (getTimeDefined != 0L && Manage_My_Sync_Start.isEmpty()) {
                            textSynIntervals!!.text = "ST: $getTimeDefined Mins"
                        } else {
                            textSynIntervals!!.text = "ST: --"
                        }
                        if (Utility.isNetworkAvailable(applicationContext)) {
                            if (get_progress.isNotEmpty()) {
                                textStatusProcess!!.text = get_progress + ""
                            } else {
                                textStatusProcess!!.text = "PR: Running"
                            }
                        } else {
                            textStatusProcess!!.text = "No Internet"
                        }
                    }
                } catch (e: java.lang.Exception) {
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "updateSyncViewZip: " + e.message!!)
        }
    }






    /// The Schedule Media
    /// The Schedule Media
    /// The Schedule Media
    /// The Schedule Media

    private fun initialize() {

            handler.postDelayed(Runnable {

                //set defaults
                setDefaults()

                //check service
                runScheduleCheck()


                //run device time
                runDeviceTime()

                //run server  time
                runServerTime()


            }, 1000)

    }


    private fun setDefaults() {
        scheduleStart!!.text = "N/A"
        scheduleEnd!!.text = "N/A"
    }


    private fun runDeviceTime() {
        @SuppressLint("SimpleDateFormat")
        val currentTime = SimpleDateFormat("HH:mm").format(
            System.currentTimeMillis()
        )

        //set text
        deviceTime!!.text = currentTime

        //repeat
        handlerDeviceTime.postDelayed(Runnable { runDeviceTime() }
            .also { runnableDeviceTime = it }, 30000)
    }


    @SuppressLint("SetTextI18n")
    private fun runServerTime() {
        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
        val getCompanyId = myDownloadClass.getString(Constants.getFolderClo, "").toString()

        // Use coroutines to handle the network request
        lifecycleScope.launch {
            try {
                // Perform the network request on the IO thread
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getInstance().getApi().getServerTime(getCompanyId)
                }

                // Switch back to the Main thread to update the UI
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val getTime = response.body()?.time ?: "00:00"
                        serverTime?.text = getTime
                        getServer_timeStamp = getTime
                    } else {
                        serverTime?.text = "00:00"
                    }
                }
            } catch (e: Exception) {
                // Handle any errors that occur during the network request
                withContext(Dispatchers.Main) {
                    serverTime?.text = "00:00"
                }
            }
        }

        // Repeat the task using the Handler
        handlerServerTime.postDelayed(Runnable { runServerTime() }
            .also { runnableServerTime = it }, 32000)
    }


    //schedule
    private fun myStateChecker() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("sync2app")
        myRef.child("app").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val get_value = snapshot.getValue(String::class.java)
                    if (get_value != null && get_value == Constants.GroundPath) {
                        Process.killProcess(Process.myTid())
                        System.exit(0)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    //schedule

    private fun runScheduleCheck() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                if (TextUtils.isEmpty(currentSettings!!.current_day)) {
                    currentSettings!!.current_day = MethodsSchedule.today()
                }

                val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE)
                val company = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                val license = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                val syn2AppLive = Constants.Syn2AppLive

                val finalFolderPath = "/$company/$license"

                val scheduleFileFolder =
                    File("${Environment.getExternalStorageDirectory()}/Download/$syn2AppLive$finalFolderPath/App/${Common.USER_SCHEDULE_FOLDER}")

                val savedState = Paper.book().read(Common.set_schedule_key, Common.schedule_online)

                scheduleFile = if (Common.schedule_online == savedState) {
                    File(scheduleFileFolder.absolutePath, Common.ONLINE_SCHEDULE_FILE)
                } else {
                    File(scheduleFileFolder.absolutePath, Common.LOCAL_SCHEDULE_FILE)
                }

                if (currentSettings!!.current_day != MethodsSchedule.today()) {
                    currentSettings!!.current_day = MethodsSchedule.today()
                    tempList.clear()
                    theSchedules.clear()
                    setAlarms.clear()
                    enteredSchedules.clear()
                    enteredAlarms.clear()
                }

                if (scheduleFile?.exists() == true) {
                    try {
                        val reader = CSVReader(FileReader(scheduleFile))
                        var nextLine: Array<String>?

                        while (reader.readNext().also { nextLine = it } != null) {
                            nextLine?.let {
                                tempList.add(
                                    Schedule(
                                        it[0], it[1], it[2].toBoolean(), it[3].toBoolean(),
                                        it[4].toBoolean(), it[5], it[6], it[7], it[8], it[9], it[10]
                                    )
                                )
                            }
                        }

                        for (i in 1 until tempList.size) {
                            val schedule = tempList[i]
                            if (schedule.day == MethodsSchedule.today() || schedule.isDaily || schedule.date == todayDate() || (schedule.isWeekly && schedule.day == MethodsSchedule.today())) {
                                if (timeDifference(schedule.startTime) > 0 || dayDifference(schedule.date) > 0) {
                                    if (!enteredSchedules.contains(schedule.id)) {
                                        theSchedules.add(schedule)
                                        enteredSchedules.add(schedule.id)
                                    }
                                }
                            }
                        }

                        theSchedules.sortBy { it.startTime }

                        withContext(Dispatchers.Main) {
                            if (theSchedules.isNotEmpty()) {
                                val getStartTime = theSchedules[0].startTime
                                val getStopTime = theSchedules[0].stopTime
                                scheduleStart?.text = getStartTime
                                scheduleEnd?.text = getStopTime
                            } else {
                                scheduleStart?.text = "N/A"
                                scheduleEnd?.text = "N/A"
                            }
                        }

                        setAlarm(theSchedules)

                    } catch (e: IOException) {
                        // Handle IOException
                    }
                } else {
                     showToastMessage("Schedule file Not Found")
                }

            } catch (e: Exception) {
                // Handle general exceptions
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun todayDate(): String {
        return SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
    }


    private suspend fun timeDifference(providedTime: String?): Long {
        return try {
            lifecycleScope.async(Dispatchers.IO) {
                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                val currentTimeMillis = System.currentTimeMillis()
                val currentTime = format.format(currentTimeMillis)

                val providedDate: Date?
                val currentDate: Date?

                if (currentSettings!!.isUse_server_time) {
                    providedDate = providedTime?.let { format.parse(it) }
                    currentDate = format.parse(getServer_timeStamp)
                } else {
                    providedDate = providedTime?.let { format.parse(it) }
                    currentDate = format.parse(currentTime)
                }

                val difference: Long = if (providedDate != null && currentDate != null) {
                    providedDate.time - currentDate.time
                } else {
                    0L
                }

                difference
            }.await()
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.d("Schedule", "Time Difference: " + e.message)
            0L
        }
    }


    private suspend fun dayDifference(providedTime: String): Long {
        return if (providedTime.isEmpty()) {
            0L
        } else {
            try {
                lifecycleScope.async(Dispatchers.IO) {
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val currentTimeMillis = System.currentTimeMillis()
                    val currentTime = format.format(currentTimeMillis)
                    val providedDate = format.parse(providedTime)
                    val currentDate = format.parse(currentTime)

                    if (providedDate != null && currentDate != null) {
                        providedDate.time - currentDate.time
                    } else {
                        0L
                    }
                }.await()
            } catch (e: ParseException) {
                e.printStackTrace()
                Log.d("Schedule", "Day Difference: " + e.message)
                0L
            }
        }
    }


    private fun setAlarm(theSchedules: List<Schedule>) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Check if time used
                if (!currentSettings!!.isUse_server_time) {

                    // Check if empty
                    if (theSchedules.isNotEmpty()) {

                        // Current time
                        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val currentTime = format.format(System.currentTimeMillis())

                        // Loop through today's schedules
                        for (i in theSchedules.indices) {
                            if (theSchedules[i].startTime == currentTime && !isScheduleCurrentlyRunning) {
                                val theId = theSchedules[i].id
                                val theUrl = theSchedules[i].redirect_url
                                val theEndTime = theSchedules[i].stopTime
                                val isOneOff = theSchedules[i].isOneTime

                                withContext(Dispatchers.Main) {

                                    loadScheduleUrl(theId, theUrl, theEndTime, isOneOff, i)

                                    showToastMessage("Scheduled Started")

                                }

                                // Change state
                                isScheduleCurrentlyRunning = true

                                // Start end time check
                                startEndTimeCheck(theEndTime, theId, i)
                                break
                            }
                        }
                    }
                } else {

                    // Check if empty
                    if (theSchedules.isNotEmpty()) {
                        Log.d("ScheduleStart", currentScheduleTime)

                        // Loop through today's schedules
                        for (i in theSchedules.indices) {
                            if (theSchedules[i].startTime == getServer_timeStamp && !isScheduleCurrentlyRunning) {
                                val theId = theSchedules[i].id
                                val theUrl = theSchedules[i].redirect_url
                                val theEndTime = theSchedules[i].stopTime
                                val isOneOff = theSchedules[i].isOneTime

                                // Start schedule
                                loadScheduleUrl(theId, theUrl, theEndTime, isOneOff, i)

                                // Update UI
                                withContext(Dispatchers.Main) {
                                    showToastMessage("Scheduled Started")
                                }

                                // Change state
                                isScheduleCurrentlyRunning = true

                                // Start end time check
                                startEndTimeCheck(theEndTime, theId, i)
                                break
                            }
                        }
                    }
                }

                // Repeat
                if (!isScheduleCurrentlyRunning) {
                    withContext(Dispatchers.Main) {
                        handlerSchedule.postDelayed(Runnable { runScheduleCheck() }
                            .also { runnableSchedule = it }, 32000)
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    private fun startEndTimeCheck(scheduleEndTime: String, scheduleId: String, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Current time
                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                val currentTime = format.format(System.currentTimeMillis())

                if (currentSettings!!.isUse_server_time) {
                    if (scheduleEndTime == getServer_timeStamp) {
                        // Stop the schedule and update UI
                        withContext(Dispatchers.Main) {

                            // Remove timer
                            handlerRunningSchedule.removeCallbacks(runnableRunningSchedule!!)

                            // Stop schedule
                            stopSchedule(scheduleId, position)

                            // Print message
                            showToastMessage("Scheduled Finished")

                            // Run schedule check
                            runScheduleCheck()
                        }
                    } else {
                        // Schedule not yet finished, post delayed check
                        withContext(Dispatchers.Main) {
                            handlerRunningSchedule.postDelayed(Runnable {
                                startEndTimeCheck(scheduleEndTime, scheduleId, position)
                            }.also { runnableRunningSchedule = it }, 32000)
                        }
                    }
                } else {
                    if (currentTime == scheduleEndTime) {
                        // Stop the schedule and update UI
                        withContext(Dispatchers.Main) {

                            // Remove timer
                            handlerRunningSchedule.removeCallbacks(runnableRunningSchedule!!)

                            // Stop schedule
                            stopSchedule(scheduleId, position)

                            // Print message
                            showToastMessage("Scheduled Finished")

                            // Run schedule check
                            runScheduleCheck()
                        }
                    } else {
                        // Schedule not yet finished, post delayed check
                        withContext(Dispatchers.Main) {

                            handlerRunningSchedule.postDelayed(Runnable {
                                startEndTimeCheck(scheduleEndTime, scheduleId, position)
                            }.also { runnableRunningSchedule = it }, 32000)
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    private fun loadScheduleUrl(
        theId: String,
        theUrl: String,
        stopTime: String,
        isOneOff: Boolean,
        position: Int
    ) {

        try {

            val savedState = Paper.book().read(Common.set_schedule_key, Common.schedule_online)
            if (Common.schedule_online == savedState) {

                //set file to use online Schedule
                Load_Schedule_From_Admin_Panel(theId, theUrl, stopTime, isOneOff, position)
            } else {
                //set file to use local Schedule
                Load_Schedule_From_App(theId, theUrl, stopTime, isOneOff, position)

            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "loadScheduleUrl: "+e.message.toString())
        }
    }


    private fun Load_Schedule_From_App(
        theId: String,
        theUrl: String,
        stopTime: String,
        isOneOff: Boolean,
        position: Int
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {

                // Register type
                isSchedule = true
                isScheduleRunning = true

                // Load page
                if (Utility.isNetworkAvailable(applicationContext)) {
                    if (theUrl.contains("/Syn2AppLive/")) {
                        val theOfflineFile = File(theUrl)
                        if (theOfflineFile.exists()) {

                            withContext(Dispatchers.Main) {
                                webView?.let { it.clearHistory() }
                                loadOnlineLiveUrl(theUrl)
                                load_offline_indicator()
                            }

                        } else {
                            withContext(Dispatchers.Main) {
                                InitWebvIewloadStates()
                            }
                        }


                    } else {
                        // check if the url is valid
                        lifecycleScope.launch {
                            val result = checkUrlExistence(theUrl)
                            if (result) {

                                withContext(Dispatchers.Main) {
                                    loadOnlineLiveUrl(theUrl)
                                    load_live_indicator()
                                }

                            } else {
                                withContext(Dispatchers.Main) {
                                    InitWebvIewloadStates()
                                    showToastMessage("404 Invalid Schedule Url")
                                }

                            }
                        }
                    }

                    // else if there is no internet
                    // else if there is no internet
                    // else if there is no internet
                } else {

                    if (theUrl.contains("/Syn2AppLive/")) {
                        val theOfflineFile = File(theUrl)
                        if (theOfflineFile.exists()) {

                            withContext(Dispatchers.Main) {
                                webView?.let { it.clearHistory() }
                                loadOnlineLiveUrl(theUrl)
                                load_offline_indicator()
                            }

                        } else {
                            withContext(Dispatchers.Main) {
                                InitWebvIewloadStates()
                                showToastMessage("Schedule Index File not found")
                            }
                        }


                    } else {

                        withContext(Dispatchers.Main) {
                            showToastMessage("No Internet connection")
                        }
                    }

                }

            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    private fun stopSchedule(scheduleId: String?, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {

                withContext(Dispatchers.Main) {
                    // Register type
                    isSchedule = false
                    isScheduleRunning = false


                    //load Schedule media
                    InitWebvIewloadStates()


                    // Remove schedule
                    theSchedules.removeAt(position)
                    enteredSchedules.remove(scheduleId)

                    // Cancel schedule state
                    isScheduleCurrentlyRunning = false


                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    private fun Load_Schedule_From_Admin_Panel(
        theId: String,
        theUrl: String,
        stopTime: String,
        isOneOff: Boolean,
        position: Int
    ) {
        try {
            isSchedule = true
            isScheduleRunning = true
            if (theUrl.startsWith("announce.html") || theUrl.startsWith("training.html")) {
                load_Admin_Webview_Schedule(theUrl)
            } else if (theUrl.startsWith("https") || theUrl.startsWith("http")) {

                lifecycleScope.launch {
                    val result = checkUrlExistence(theUrl)
                    if (result) {

                        withContext(Dispatchers.Main) {
                            loadOnlineLiveUrl(theUrl)
                            load_live_indicator()
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            InitWebvIewloadStates()
                            showToastMessage("404 Invalid Schedule Url")
                        }

                    }
                }


            } else if (theUrl.startsWith("/App/") || theUrl.startsWith("App/")) {
                load_Admin_Webview_Schedule_Modified_url(theUrl)
            } else {
                load_Offline_Page_From_Admin(theUrl)
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun load_Admin_Webview_Schedule(theUrl: String) {
        try {
            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val company = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val license = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val filename = "/$theUrl"
            val finalFolderPathDesired = "/" + company + "/" + license + "/" + Constants.App
            val destinationFolder =
                Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + finalFolderPathDesired
            val filePath = "file://$destinationFolder$filename"
            val myFile = File(destinationFolder, File.separator + filename)

            if (myFile.exists()) {
                webView?.let { it.clearHistory() }
                loadOnlineLiveUrl(filePath)
                load_offline_indicator()

            } else {

                InitWebvIewloadStates()
                showToastMessage("Schedule Index File not found..")
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "load_Admin_Webview_Schedule: " + e.message.toString())
        }
    }


    private fun load_Admin_Webview_Schedule_Modified_url(theFullPath: String) {
        try {
            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val company = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val license = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val finalFolderPathDesired = "/$company/$license/$theFullPath"
            val destinationFolder =
                Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + finalFolderPathDesired
            val filePath = "file://$destinationFolder"
            val myFile = File(destinationFolder)
            if (myFile.exists()) {
                webView?.let { it.clearHistory() }
                loadOnlineLiveUrl(filePath)
                load_offline_indicator()

            } else {

                InitWebvIewloadStates()
                showToastMessage("Schedule Index File not found..")
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun load_Offline_Page_From_Admin(theUrl: String) {
        try {
            val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
            val company = myDownloadClass.getString(Constants.getFolderClo, "").toString()
            val license = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
            val filename = "/$theUrl"
            val finalFolderPathDesired = "/" + company + "/" + license + "/" + Constants.App
            val destinationFolder =
                Environment.getExternalStorageDirectory().absolutePath + "/Download/${Constants.Syn2AppLive}/" + finalFolderPathDesired
            val filePath = "file://$destinationFolder$filename"
            val myFile = File(destinationFolder, File.separator + filename)


            if (myFile.exists()) {
                webView?.let { it.clearHistory() }
                loadOnlineLiveUrl(filePath)
                load_offline_indicator()

            } else {

                showToastMessage("Schedule Index File not found..")
                InitWebvIewloadStates()

            }
        } catch (e: java.lang.Exception) {
        }
    }


    /// Set Up Camera
    /// Set Up Camera
    // setup usbcam

    private fun iniliaze_Schedule_and_usbCamera() {

        // init Widows Screen siezwe
        initDisplaySize()

        // Hide camera Layout if need be
        val get_imgStreamVideo = sharedBiometric.getString(Constants.imgStreamVideo, "").toString()
        val get_imgUseDevicecameraOrPlugInCamera = sharedBiometric.getString(Constants.imgUseDevicecameraOrPlugInCamera, "").toString()
        if (get_imgStreamVideo != Constants.imgStreamVideo) {
            mlayout?.visibility = View.GONE
        } else {
            handler.postDelayed({
                runOnUiThread {
                    try {
                        doResizeUSBCameraView()
                    } catch (exception: java.lang.Exception) {
                    }
                }
            }, 2000)
        }




        if (get_imgUseDevicecameraOrPlugInCamera != Constants.imgUseDevicecameraOrPlugInCamera) {
            handler.postDelayed({
                runOnUiThread {
                    try {
                        checkUsbDevices();
                    } catch (exception: java.lang.Exception) {
                    }
                }
            }, 500)
        } else {
            handler.postDelayed({
                runOnUiThread {
                    try {
                        if (isSystemRunning) {
                            inilazeUSBWebCam();
                        }
                    } catch (exception: java.lang.Exception) {
                    }
                }
            }, 7000)
        }
    }


    private fun checkUsbDevices() {
        try {
            val usbManager = getSystemService(USB_SERVICE) as UsbManager
            val deviceList = usbManager.deviceList
            val deviceIterator: Iterator<UsbDevice> = deviceList.values.iterator()
            var usbCameraConnected = false
            while (deviceIterator.hasNext()) {
                val device = deviceIterator.next()
                usbCameraConnected = true
                break
            }
            if (usbCameraConnected) {
                handler.postDelayed({
                    runOnUiThread {
                        try {
                            if (isSystemRunning) {
                                inilazeUSBWebCam()
                            }
                        } catch (exception: java.lang.Exception) {
                        }
                    }
                }, 7000)
            } else {
                showToastMessage("USB Live Stream not found");
                textNoCameraAvaliable?.visibility = View.VISIBLE
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun inilazeUSBWebCam() {
        inliazeUSbCamVariables()
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun inliazeUSbCamVariables() {
        try {
            val get_imgStreamVideo = sharedBiometric.getString(Constants.imgStreamVideo, "").toString()
            if (get_imgStreamVideo == Constants.imgStreamVideo) {
                try {
                    toggleInvisibilityAndStopCamera()
                    toggleVisibilityAndCameraStart()
                } catch (e: java.lang.Exception) {
                    Log.d(TAG, "inliazeUSbCamVariables: " + e.message.toString())
                    audioHandler!!.stopAudio()
                    audioHandler!!.endAudio()
                }
                reloadWebCam!!.setOnClickListener { v: View? ->
                    try {
                        toggleInvisibilityAndStopCamera()
                        toggleVisibilityAndCameraStart()
                    } catch (e: java.lang.Exception) {
                        Log.d(TAG, "inliazeUSbCamVariables: " + e.message.toString())
                        audioHandler!!.stopAudio()
                        audioHandler!!.endAudio()
                    }
                }
                closeWebCam!!.setOnClickListener { view: View? ->
                    try {
                        toggleInvisibilityAndStopCamera()
                    } catch (e: java.lang.Exception) {
                        Log.d(TAG, "inliazeUSbCamVariables: " + e.message.toString())
                    }
                }


                /// Hide the icons after a while
                try {
                    showCameraIconhandler.postDelayed({
                        reloadWebCam!!.visibility = View.INVISIBLE
                        closeWebCam!!.visibility = View.INVISIBLE
                        expandWebcam!!.visibility = View.INVISIBLE
                    }, 10000)
                } catch (e: java.lang.Exception) {
                }


                /// set up motion layout on container
                mlayout!!.setOnTouchListener { v: View, event: MotionEvent ->
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            dXo = v.x - event.rawX
                            dYo = v.y - event.rawY
                            lastActionXY = MotionEvent.ACTION_DOWN
                            if (!isShowToastDisplayed) {
                                try {
                                    reloadWebCam?.visibility = View.VISIBLE
                                    closeWebCam?.visibility = View.VISIBLE
                                    expandWebcam?.visibility = View.VISIBLE

                                    showCameraIconhandler?.let { it.removeCallbacksAndMessages(null) }

                                    isShowToastDisplayed = true
                                    isHideToastDisplayed = false
                                } catch (e: java.lang.Exception) {
                                }
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            v.y = event.rawY + dYo
                            v.x = event.rawX + dXo
                            lastActionXY = MotionEvent.ACTION_MOVE
                            if (isShowToastDisplayed && !isHideToastDisplayed) {
                                try {
                                    showCameraIconhandler?.postDelayed({
                                        reloadWebCam?.visibility = View.INVISIBLE
                                        closeWebCam?.visibility = View.INVISIBLE
                                        expandWebcam?.visibility = View.INVISIBLE
                                    }, 10000)
                                    isHideToastDisplayed = true
                                } catch (e: java.lang.Exception) {
                                }
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            // Reset flags on touch release to allow the process to repeat
                            isShowToastDisplayed = false
                            isHideToastDisplayed = false
                        }

                        else -> return@setOnTouchListener false
                    }
                    true
                }
                val get_imgEnableExpandFloat =
                    sharedBiometric.getString(Constants.imgEnableExpandFloat, "")
                if (get_imgEnableExpandFloat != Constants.imgEnableExpandFloat) {
                    // expandWebcam.setVisibility(View.VISIBLE);
                    expandWebcam!!.setOnTouchListener { v: View, event: MotionEvent ->
                        when (event.actionMasked) {
                            MotionEvent.ACTION_DOWN -> {
                                initialWidth = mlayout!!.width
                                initialHeight = mlayout!!.height
                                dXo = v.x - event.rawX
                                dYo = v.y - event.rawY
                                lastActionXY = MotionEvent.ACTION_DOWN
                            }

                            MotionEvent.ACTION_MOVE -> {
                                val newWidth = (event.rawX + dXo).toInt()
                                val newHeight = (event.rawY + dYo).toInt()
                                mlayout?.layoutParams?.width =
                                    if (newWidth > 0) newWidth else initialWidth
                                mlayout?.layoutParams?.height =
                                    if (newHeight > 0) newHeight else initialHeight
                                mlayout?.requestLayout()
                                lastActionXY = MotionEvent.ACTION_MOVE
                            }

                            else -> return@setOnTouchListener false
                        }
                        true
                    }
                }
                val get_imgEnableDisplayIntervals =
                    sharedBiometric.getString(Constants.imgEnableDisplayIntervals, "").toString()
                if (get_imgEnableDisplayIntervals == Constants.imgEnableDisplayIntervals) {
                    start_Display_Timer()
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Error starting camera: " + e.message, e)
        }
    }

    private fun toggleInvisibilityAndStopCamera() {
        try {
        cameraHandler!!.stopCamera()
        audioHandler!!.stopAudio()
        audioHandler!!.endAudio()
        mlayout!!.visibility = View.GONE

    } catch (e: java.lang.Exception) {
            Log.e(TAG, "Error starting camera: " + e.message, e)
    }

    }

    private fun toggleVisibilityAndCameraStart() {
        try {
            mlayout?.visibility = View.VISIBLE
            mlayout?.alpha = 1f
            if (binding.textureView.isAvailable()) {
                cameraHandler?.startCamera()
                textNoCameraAvaliable?.visibility = View.INVISIBLE
                val get_imgStreamAudioSound =
                    sharedBiometric.getString(Constants.imgStreamAudioSound, "").toString()
                if (get_imgStreamAudioSound != Constants.imgStreamAudioSound) {
                    audioHandler!!.startAudio()
                }
            } else {
                binding.textureView.setSurfaceTextureListener(object : SurfaceTextureListener {
                    override fun onSurfaceTextureAvailable(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        try {
                            mlayout?.visibility = View.VISIBLE
                            cameraHandler?.startCamera()
                            textNoCameraAvaliable?.visibility = View.INVISIBLE
                            val get_imgStreamAudioSound =
                                sharedBiometric.getString(Constants.imgStreamAudioSound, "")
                                    .toString()
                            if (get_imgStreamAudioSound != Constants.imgStreamAudioSound) {
                                audioHandler!!.startAudio()
                            }
                        } catch (e: java.lang.Exception) {
                        }
                    }

                    override fun onSurfaceTextureSizeChanged(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                    }

                    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                        return false
                    }

                    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                })
            }
        } catch (e: java.lang.Exception) {
        }
    }

    private fun initDisplaySize() {
        try {
            val displaymetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displaymetrics)
            mScreenHeight = displaymetrics.heightPixels
            mScreenWidth = displaymetrics.widthPixels
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "initDisplaySize: "+e.message.toString())
        }
    }

    private fun doResizeUSBCameraView() {
        try {

            val get_imgStreamAPIorDevice =
                sharedBiometric.getString(Constants.imgStreamAPIorDevice, "").toString()
            if (get_imgStreamAPIorDevice != Constants.imgStreamAPIorDevice) {
                /// for device
                val sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, MODE_PRIVATE)
                val startY = sharedCamera.getString(Constants.startY, "").toString()
                    .trim { it <= ' ' }
                val camHeight = sharedCamera.getString(Constants.camHeight, "").toString()
                    .trim { it <= ' ' }
                val startX = sharedCamera.getString(Constants.startX, "").toString()
                    .trim { it <= ' ' }
                val camWidth = sharedCamera.getString(Constants.camWidth, "").toString()
                    .trim { it <= ' ' }
                pass_Width_heights(startY, camHeight, startX, camWidth)
            } else {
                /// for Api
                val sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, MODE_PRIVATE)
                val startY = sharedCamera.getString(Constants.start_height_api, "").toString()
                    .trim { it <= ' ' }
                val camHeight = sharedCamera.getString(Constants.end_height_api, "").toString()
                    .trim { it <= ' ' }
                val startX = sharedCamera.getString(Constants.start_width_api, "").toString()
                    .trim { it <= ' ' }
                val camWidth = sharedCamera.getString(Constants.end_width_api, "").toString()
                    .trim { it <= ' ' }
                pass_Width_heights(startY, camHeight, startX, camWidth)
            }
        } catch (e: NumberFormatException) {
            showToastMessage("Invalid Input Device Width and Height")
        }
    }

    private fun pass_Width_heights(
        startY: String,
        camHeight: String,
        startX: String,
        camWidth: String
    ) {
        var startY = startY
        var camHeight = camHeight
        var startX = startX
        var camWidth = camWidth
        try {
            val m_camHeight = camHeight.toInt()
            val m_camWidth = camWidth.toInt()
            if (startY.isEmpty()) {
                startY = "0"
            }
            if (camHeight.isEmpty()) {
                camHeight = "0"
            }
            if (startX.isEmpty()) {
                startX = "0"
            }
            if (camWidth.isEmpty()) {
                camWidth = "0"
            }
            if (camHeight == "0" && camWidth == "0") {
                showToastMessage("Invalid dimension")
                return
            }
            if (m_camWidth <= 9 && m_camHeight <= 9) {
                showToastMessage("Invalid values")
                return
            }
            val startHeight = startY.toInt().toDouble()
            val startWidth = startX.toInt().toDouble()
            mUSBCameraLeftMargin = mScreenWidth / 100.0 * startWidth
            mUSBCameraTopMargin = mScreenHeight / 100.0 * startHeight
            mUSBCameraHeight = mScreenHeight / 100.0 * camHeight.toInt()
            mUSBCameraWidth = mScreenWidth / 100.0 * camWidth.toInt()

            // Calculate total dimensions including margins
            val totalWidth = mUSBCameraWidth + mUSBCameraLeftMargin
            val totalHeight = mUSBCameraHeight + mUSBCameraTopMargin

            // Check if total dimensions exceed screen dimensions
            if (totalWidth > mScreenWidth) {
                mUSBCameraLeftMargin = 0.0
            }
            if (totalHeight > mScreenHeight) {
                mUSBCameraTopMargin = 0.0
            }

            // Set new layout parameters for mlayout
            val layoutParams = mlayout?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.width = mUSBCameraWidth.toInt()
            layoutParams.height = mUSBCameraHeight.toInt()
            layoutParams.leftMargin = mUSBCameraLeftMargin.toInt()
            layoutParams.topMargin = mUSBCameraTopMargin.toInt()
            mlayout!!.layoutParams = layoutParams
        } catch (e: java.lang.Exception) {
        }
    }


    private inner class UsbBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            // val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED == action) {
                handler.postDelayed(Runnable {
                    runOnUiThread(Runnable {
                        try {
                            //  if (isAudioDevice(device)) {Toast.makeText(context, "Audio device connected", Toast.LENGTH_SHORT).show();}
                            if (isSystemRunning) {
                                showToastMessage("USB Camera connected")
                                inilazeUSBWebCam()
                            }
                        } catch (exception: java.lang.Exception) {
                        }
                    })
                }, 4000)
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED == action) {
                runOnUiThread(Runnable {
                    try {
                        //  if (isAudioDevice(device)) {Toast.makeText(context, "Audio device disconnected", Toast.LENGTH_SHORT).show();}
                        if (isSystemRunning) {
                            showToastMessage("USB Camera disconnected")
                            toggleInvisibilityAndStopCamera()
                        }
                    } catch (e: java.lang.Exception) {
                    }
                })
            }
        }
    }


    private fun getIntentFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        return filter
    }


    private inner class CameraDisconnectedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val action = intent.action
                if (Constants.SYNC_CAMERA_DISCONNECTED == action) {
                    runOnUiThread(Runnable {
                        try {
                            if (isSystemRunning) {
                                audioHandler?.stopAudio()
                                audioHandler?.endAudio()
                                textNoCameraAvaliable?.setVisibility(View.VISIBLE)
                                showToastMessage("No cameras available")
                            }
                        } catch (e: java.lang.Exception) {
                        }
                    })
                }else{
                    showToastMessage("Camera Connected")
                }
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "onReceive: " + e.message.toString())
            }
        }
    }


    private fun start_Display_Timer() {
        try {

            val get_imgStreamAPIorDevice = sharedBiometric.getString(Constants.imgStreamAPIorDevice, "").toString()
            if (get_imgStreamAPIorDevice != Constants.imgStreamAPIorDevice) {
                val d_time = sharedCamera.getLong(Constants.get_Display_Camera_Defined_Time_for_Device, 0L)
                val timeertaker = d_time * 60 * 1000
                if (timeertaker != 0L) {
                    StartCameraHandler.postDelayed({
                        if (isSystemRunning) {
                            toggleInvisibilityAndStopCamera()
                        }
                        stopTimer()
                        star_Hide_Timer()
                    }, timeertaker)
                } else {
                    showToastMessage("Invalid Display Camera Time")
                }
            } else {
                val sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, MODE_PRIVATE)
                val display_time_api =
                    sharedCamera.getString(Constants.display_time_api, "").toString()
                        .trim { it <= ' ' }
                val d_time = java.lang.Long.valueOf(display_time_api)
                val timeertaker = d_time * 60 * 1000
                if (timeertaker != 0L) {
                    StartCameraHandler?.postDelayed({
                        if (isSystemRunning) {
                            toggleInvisibilityAndStopCamera()
                        }
                        stopTimer()
                        star_Hide_Timer()
                    }, timeertaker)
                } else {
                    showToastMessage("Invalid Display Camera Time")
                }
            }
        } catch (e: java.lang.Exception) {
        }
    }


    private fun star_Hide_Timer() {
        try {
            val get_imgStreamAPIorDevice =
                sharedBiometric.getString(Constants.imgStreamAPIorDevice, "").toString()
            if (get_imgStreamAPIorDevice != Constants.imgStreamAPIorDevice) {
                val d_time =
                    sharedCamera.getLong(Constants.get_Hide_Camera_Defined_Time_for_Device, 0L)
                val timeertaker = d_time * 60 * 1000
                if (timeertaker != 0L) {
                    StartCameraHandler.postDelayed({
                        if (isSystemRunning) {
                            try {
                                toggleInvisibilityAndStopCamera()
                                toggleVisibilityAndCameraStart()
                            } catch (e: java.lang.Exception) {
                                Log.d(TAG, "star_Hide_Timer: "+e.message.toString())
                                audioHandler?.stopAudio()
                                audioHandler?.endAudio()
                            }
                        }
                        stopTimer()
                        start_Display_Timer()
                    }, timeertaker)
                } else {
                    showToastMessage("Invalid Display Camera Time")
                }
            } else {
                val hide_time_api = sharedCamera.getString(Constants.hide_time_api, "").toString()
                    .trim { it <= ' ' }
                val d_time = java.lang.Long.valueOf(hide_time_api)
                val timeertaker = d_time * 60 * 1000
                if (timeertaker != 0L) {
                    StartCameraHandler.postDelayed({
                        if (isSystemRunning) {
                            try {
                                toggleInvisibilityAndStopCamera()
                                toggleVisibilityAndCameraStart()
                            } catch (e: java.lang.Exception) {
                                showToastMessage(e.message!!)
                                audioHandler?.stopAudio()
                                audioHandler?.endAudio()
                            }
                        }
                        stopTimer()
                        start_Display_Timer()
                    }, timeertaker)
                } else {
                    showToastMessage("Invalid Display Camera Time")
                }
            }
        } catch (e: java.lang.Exception) {
        }
    }

    private fun stopTimer() {
        StartCameraHandler.let {
            it.removeCallbacksAndMessages(null)
        }
    }


    private fun CheckShortCutImage(){
        try {

            val get_ShortCutStatus = sharedBiometric.getString(Constants.Do_NO_SHOW_SHORT_CUT_AGAIN, "").toString()

            if (get_ShortCutStatus != Constants.Do_NO_SHOW_SHORT_CUT_AGAIN) {

                handler.postDelayed(Runnable {

                    lifecycleScope.launch(Dispatchers.IO) {

                        val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                        val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                        val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                        val pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
                        val folder = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + Constants.Syn2AppLive + "/" + pathFolder
                        val fileTypes = "app_logo.png"
                        val file = File(folder, fileTypes)

                        if (file.exists()) {
                            withContext(Dispatchers.Main) {
                                initShortCut()
                            }
                        }
                        else {
                            withContext(Dispatchers.Main){
                                Log.d(TAG, "CheckShortCutImage: No Short Image Found")
                            }
                        }
                    }


                }, 7000)

            }


        }catch (e:Exception){
            Log.d(TAG, "CheckShortCutImage: "+e.message.toString())
        }

    }

    private fun initShortCut(){
        try {
            binding.adView.visibility = View.VISIBLE

            startCountDownShortCut(5)

            binding.textAgreeYes.setOnClickListener {

                lifecycleScope.launch(Dispatchers.IO) {

                    val myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, MODE_PRIVATE)
                    val getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "").toString()
                    val getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "").toString()
                    val pathFolder = "/" + getFolderClo + "/" + getFolderSubpath + "/" + Constants.App + "/" + "Config"
                    val folder = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + Constants.Syn2AppLive + "/" + pathFolder
                    val fileTypes = "app_logo.png"
                    val file = File(folder, fileTypes)

                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    if (Build.VERSION.SDK_INT >= 25) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            CustomShortcutsDemo.setUp(applicationContext, getFolderSubpath, bitmap)
                        }
                    }

                    if (Build.VERSION.SDK_INT >= 28) {
                        shortcutPin(applicationContext, Constants.shortcut_messages_id, 1)
                    }
                }
                binding.adView.visibility = View.GONE

                countdownTimer_Short_Cut.let {
                    it?.cancel()
                }


                // save so it doesn't show again
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.Do_NO_SHOW_SHORT_CUT_AGAIN, Constants.Do_NO_SHOW_SHORT_CUT_AGAIN)
                editText88.putString(Constants.imageUseBranding, Constants.imageUseBranding)
                editText88.putString(Constants.imgToggleImageBackground, Constants.imgToggleImageBackground)
                editText88.putString(Constants.imgToggleImageSplashOrVideoSplash, Constants.imgToggleImageSplashOrVideoSplash)
                editText88.apply()

            }



            binding.textAgreeNO.setOnClickListener {
                binding.adView.visibility = View.GONE
                countdownTimer_Short_Cut.let {
                    it?.cancel()
                }

                // save so it doesn't show again
                val editText88 = sharedBiometric.edit()
                editText88.putString(Constants.Do_NO_SHOW_SHORT_CUT_AGAIN, Constants.Do_NO_SHOW_SHORT_CUT_AGAIN)
                editText88.apply()

            }




        }catch (e:Exception){
            Log.d(TAG, "initShortCut: "+e.message.toString())
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun shortcutPin(context: Context, shortcut_id: String, requestCode: Int) {
        try {
            val shortcutManager = applicationContext.getSystemService(ShortcutManager::class.java)

            if (shortcutManager!!.isRequestPinShortcutSupported) {

                val pinShortcutInfo = ShortcutInfo.Builder(context, shortcut_id).build()

                val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)

                val successCallback = PendingIntent.getBroadcast(
                    context, /* request code */ requestCode,
                    pinnedShortcutCallbackIntent, /* flags */ PendingIntent.FLAG_MUTABLE
                )

                shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.intentSender)


            } else {
                Log.d(TAG, "shortcutPin: not supported")
            }
        }catch (e:Exception){
            Log.d(TAG, "shortcutPin: "+e.message.toString())
        }
    }


    private fun startCountDownShortCut(minutes: Long) {
        val milliseconds = minutes * 60 * 1000 // Convert minutes to
        countdownTimer_Short_Cut = object : CountDownTimer(milliseconds, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {

                try {
                    binding.adView.visibility = View.GONE
                } catch (e: java.lang.Exception) {
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    val totalSecondsRemaining = millisUntilFinished / 1000
                    var minutesUntilFinished = totalSecondsRemaining / 60
                    var remainingSeconds = totalSecondsRemaining % 60

                    if (remainingSeconds == 0L && minutesUntilFinished > 0) {
                        minutesUntilFinished--
                        remainingSeconds = 59
                    }
                    val displayText =
                        String.format("%d:%02d", minutesUntilFinished, remainingSeconds)
                    binding.textShortTimeDisplay.text = displayText

                } catch (ignored: java.lang.Exception) {
                }
            }
        }
        countdownTimer_Short_Cut?.start()
    }





    override fun onBackPressed() {
        try {
            if (drawer_menu?.visibility == View.VISIBLE) {
                drawer_menu?.visibility = View.GONE
            }
            if (isScheduleRunning) {
                navigateBackTosetting()
            } else {
                if (webView!!.canGoBack()) {
                    webView?.goBack()
                } else {
                    if (ClearCacheOnExit) {
                        webView!!.clearCache(true)
                    }
                    if (AskToExit) {
                        ShowExitDialogue()
                        if (LoadLastWebPageOnAccidentalExit) {
                            ClearLastUrl()
                        }
                    } else {
                        ClearLastUrl()
                        navigateBackTosetting()
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onBackPressed: ${e.message.toString()}")
        }
    }


    override fun onPause() {
        super.onPause()
        try {

            isAppOpen = false

        } catch (e: java.lang.Exception) {
            Log.d(TAG, "onPause: " +e.message.toString())
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        try {

            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            isSystemRunning = true
            isAppOpen = true

            if (jsonUrl == null) {
                val intent = Intent(applicationContext, SplashKT::class.java) ///
                startActivity(intent)
                finish()
            }


            val get_imagEnableDownloadStatus = sharedBiometric.getString(Constants.showDownloadSyncStatus, "").toString()
            val getToHideQRCode = preferences.getBoolean(Constants.hideQRCode, false)
            val get_drawer_icon = preferences.getBoolean(Constants.hide_drawer_icon, false)




            if (get_imagEnableDownloadStatus == Constants.showDownloadSyncStatus) {
                bottom_server_layout?.visibility = View.VISIBLE
            } else {
                bottom_server_layout?.visibility = View.GONE
            }


            val get_INSTALL_TV_JSON_USER_CLICKED = sharedTVAPPModePreferences.getString(Constants.INSTALL_TV_JSON_USER_CLICKED, "").toString()
            val hideBottom_MenuIcon_APP = sharedTVAPPModePreferences.getBoolean(Constants.hideBottom_MenuIcon_APP, false)


            if (get_INSTALL_TV_JSON_USER_CLICKED == Constants.INSTALL_TV_JSON_USER_CLICKED) {
                if (hideBottom_MenuIcon_APP){
                    bottomtoolbar_btn_7?.visibility = View.VISIBLE
                }else{
                    bottomtoolbar_btn_7?.visibility = View.GONE
                }
            }



            if (get_INSTALL_TV_JSON_USER_CLICKED != Constants.INSTALL_TV_JSON_USER_CLICKED) {
                if (get_drawer_icon) {
                    bottomtoolbar_btn_7?.visibility = View.VISIBLE
                } else {
                    bottomtoolbar_btn_7?.visibility = View.GONE
                }

            }


            if (!getToHideQRCode) {
                drawerItem7?.visibility = View.VISIBLE
            } else {
                drawerItem7?.visibility = View.INVISIBLE
            }
            val getUrlFromScanner = intent.getStringExtra(Constants.QR_CODE_KEY)
            if (getUrlFromScanner != null) {
                if (getUrlFromScanner.startsWith("https://") || getUrlFromScanner.startsWith("http://")) {
                    webView?.loadUrl(getUrlFromScanner)
                }
            }


            currentSettings = Paper.book().read(Common.CURRENT_SETTING)


            // initialize connection broadCast listener
            connectivityReceiver = ConnectivityReceiver()
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(connectivityReceiver, intentFilter)


            val get_Api_state =
                sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "").toString()
            if (get_Api_state == Constants.imagSwtichEnableSyncFromAPI) {
                updateSyncViewZip()
            } else {
                update_UI_for_API_Sync_Updade()
            }


        } catch (e: java.lang.Exception) {
            Log.d(TAG, "onResume: "+e.message.toString())
        }

    }

    override fun onStop() {
        super.onStop()

        try {

            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            isSystemRunning = false

            // remove connection broadcast listener
            connectivityReceiver?.let {
                unregisterReceiver(it)
            }

        } catch (e: java.lang.Exception) {
            showToastMessage(e.message!!)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {

            isAppOpen = false

            if (LoadLastWebPageOnAccidentalExit) {
                preferences.edit().putString("lasturl", webView!!.originalUrl).apply()
            }


            mProgressDialog?.takeIf { itmDialog ->
                itmDialog.isShowing
            }?.dismiss()


            progressDialog?.let { itShow ->

                if (itShow.isShowing) {
                    itShow.dismiss()
                }
            }

            mydialog?.let { itDilaog ->
                if (itDilaog.isShowing) {
                    itDilaog.dismiss()
                }
            }


            alertDialog?.let { italt ->
                if (italt.isShowing) {
                    italt.dismiss()
                }
            }




            if (countdownTimer_Api_Sync != null) {
                countdownTimer_Api_Sync?.cancel()
            }


            if (countdownTimer_Short_Cut != null) {
                countdownTimer_Short_Cut?.cancel()
            }


            if (countdownTimer_App_Refresh != null) {
                countdownTimer_App_Refresh?.cancel()
            }


            receiver?.let { itpp ->
                applicationContext.unregisterReceiver(itpp)
            }


            // remove fecth listner
            fetchListener?.let { it4573 ->
                fetch?.removeListener(it4573)
            }

            fetch?.let { it489 ->
                it489.removeAll()
            }

            // remove all handler call back messages
            handlerDeviceTime?.let { it11 ->
                it11.removeCallbacksAndMessages(null)
            }

            handlerSchedule?.let { it222 ->
                it222.removeCallbacksAndMessages(null)
            }

            handlerRunningSchedule?.let { it333 ->
                it333.removeCallbacksAndMessages(null)
            }

            handlerServerTime?.let { it444 ->
                it444.removeCallbacksAndMessages(null)
            }


            // Stop Camera
            cameraHandler?.let { itCamera ->
                itCamera.stopCamera()
            }

            // Stop audio
            audioHandler?.let { itAudio ->
                itAudio.endAudio()
            }

            // remove all camera handler
            StartCameraHandler?.let { ito ->
                ito.removeCallbacksAndMessages(null)
            }

            showCameraIconhandler?.let { itq ->
                itq.removeCallbacksAndMessages(null)
            }

            // remove sync ui status
            val editorSyn = myDownloadClass.edit()
            editorSyn.remove(Constants.SynC_Status)
            editorSyn.apply()


            // unregister camera Broadcast Receiver
            usbBroadcastReceiver?.let { itUSBRF->
                unregisterReceiver(itUSBRF)
            }

            // unregister camera Broadcast Receiver
            CameraReceiver?.let {itUSBRFCAM->
                unregisterReceiver(itUSBRFCAM)
            }

          //  webView?.let { itWeb->
           //     itWeb.clearHistory()
           //     itWeb.clearCache(true)
          //  }


        } catch (e: java.lang.Exception) {
            Log.d(TAG, "onDestroy: " +e.message.toString())
        }

    }


    override fun onLowMemory() {
        super.onLowMemory()

        restartApp()

    }

    private fun restartApp() {
        webView?.let { it->
            it.clearHistory()
        }

        finishAffinity()
        val intent = Intent(applicationContext, SplashKT::class.java)
        startActivity(intent)
      //  Process.killProcess(Process.myTid())

    }


    private fun start_App_Refresh_Time(hours: Long) {
        val milliseconds = hours * 60 * 60 * 1000 // Convert hours to milliseconds
        countdownTimer_App_Refresh = object : CountDownTimer(milliseconds, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                try {
                    restartApp()
                } catch (e: Exception) {
                    // Handle exception if needed
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                try {
                    val totalSecondsRemaining = millisUntilFinished / 1000
                    val hoursUntilFinished = totalSecondsRemaining / 3600
                    val minutesUntilFinished = (totalSecondsRemaining % 3600) / 60
                    val remainingSeconds = totalSecondsRemaining % 60

                    val displayText = String.format("%d:%02d:%02d", hoursUntilFinished, minutesUntilFinished, remainingSeconds)
                    binding.textRefreshTime.text = displayText

                } catch (ignored: Exception) {
                    // Handle exception if needed
                }
            }
        }
        countdownTimer_App_Refresh?.start()
    }


}