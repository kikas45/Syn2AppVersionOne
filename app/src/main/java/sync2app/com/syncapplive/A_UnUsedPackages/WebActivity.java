/*



/// un comment the code and add the Activity back in the manifest if needed


// enable this dependency for the web-view

 // implementation 'com.github.ksoichiro:android-observablescrollview:1.6.0'


package sync2app.com.syncapplive.UnUsedPackages;


import static sync2app.com.syncapplive.AdvancedControls.showToast;
import static sync2app.com.syncapplive.constants.AllowOnlyHostUrlInApp;
import static sync2app.com.syncapplive.constants.ChangeBottombarBgColor;
import static sync2app.com.syncapplive.constants.ChangeDrawerHeaderBgColor;
import static sync2app.com.syncapplive.constants.ChangeHeaderTextColor;
import static sync2app.com.syncapplive.constants.ChangeTittleTextColor;
import static sync2app.com.syncapplive.constants.ChangeToolbarBgColor;
import static sync2app.com.syncapplive.constants.CurrVersion;
import static sync2app.com.syncapplive.constants.ForceUpdate;
import static sync2app.com.syncapplive.constants.NewVersion;
import static sync2app.com.syncapplive.constants.NotifAvailable;
import static sync2app.com.syncapplive.constants.NotifLinkExternal;
import static sync2app.com.syncapplive.constants.NotifSound;
import static sync2app.com.syncapplive.constants.Notif_ID;
import static sync2app.com.syncapplive.constants.Notif_Img_url;
import static sync2app.com.syncapplive.constants.Notif_Shown;
import static sync2app.com.syncapplive.constants.Notif_button_action;
import static sync2app.com.syncapplive.constants.Notif_desc;
import static sync2app.com.syncapplive.constants.Notif_title;
import static sync2app.com.syncapplive.constants.Notifx_service;
import static sync2app.com.syncapplive.constants.ShowWebBtn;
import static sync2app.com.syncapplive.constants.ToolbarBgColor;
import static sync2app.com.syncapplive.constants.ToolbarTitleText;
import static sync2app.com.syncapplive.constants.ToolbarTitleTextColor;
import static sync2app.com.syncapplive.constants.UpdateAvailable;
import static sync2app.com.syncapplive.constants.UpdateMessage;
import static sync2app.com.syncapplive.constants.UpdateTitle;
import static sync2app.com.syncapplive.constants.UpdateUrl;
import static sync2app.com.syncapplive.constants.Web_button_Img_link;
import static sync2app.com.syncapplive.constants.Web_button_link;
import static sync2app.com.syncapplive.constants.bottomBarBgColor;
import static sync2app.com.syncapplive.constants.bottomBtn1ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn2ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn3ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn4ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn5ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn6ImgUrl;
import static sync2app.com.syncapplive.constants.bottomUrl1;
import static sync2app.com.syncapplive.constants.bottomUrl2;
import static sync2app.com.syncapplive.constants.bottomUrl3;
import static sync2app.com.syncapplive.constants.bottomUrl4;
import static sync2app.com.syncapplive.constants.bottomUrl5;
import static sync2app.com.syncapplive.constants.bottomUrl6;
import static sync2app.com.syncapplive.constants.drawerHeaderBgColor;
import static sync2app.com.syncapplive.constants.drawerHeaderImgCommand;
import static sync2app.com.syncapplive.constants.drawerHeaderImgUrl;
import static sync2app.com.syncapplive.constants.drawerHeaderText;
import static sync2app.com.syncapplive.constants.drawerHeaderTextColor;
import static sync2app.com.syncapplive.constants.drawerMenuBtnUrl;
import static sync2app.com.syncapplive.constants.drawerMenuImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem1ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem1Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem1Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem2ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem2Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem2Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem3ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem3Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem3Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem4ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem4Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem4Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem5ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem5Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem5Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem6ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem6Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem6Url;
import static sync2app.com.syncapplive.constants.isAppOpen;
import static sync2app.com.syncapplive.constants.jsonUrl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.bumptech.glide.Glide;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.HttpUrlConnectionDownloader;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sync2app.com.syncapplive.AdvancedControls;
import sync2app.com.syncapplive.MyApplication;
import sync2app.com.syncapplive.QrPages.QRSanActivity;
import sync2app.com.syncapplive.R;
import sync2app.com.syncapplive.SettingsActivityKT;
import sync2app.com.syncapplive.Splash;
import sync2app.com.syncapplive.additionalSettings.autostartAppOncrash.Methods;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.api.RetrofitClientJava;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.models.AppSettings;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.models.Schedule;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.responses.ServerTimeResponse;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.Common;
import sync2app.com.syncapplive.additionalSettings.cloudAppsync.util.MethodsSchedule;
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesApi;
import sync2app.com.syncapplive.additionalSettings.myApiDownload.FilesViewModel;
import sync2app.com.syncapplive.additionalSettings.utils.CSVDownloader;
import sync2app.com.syncapplive.additionalSettings.utils.MyDownloadMangerClass;
import sync2app.com.syncapplive.additionalSettings.myService.OnChangeApiServiceSync;
import sync2app.com.syncapplive.additionalSettings.myService.OnChnageService;
import sync2app.com.syncapplive.additionalSettings.myService.SyncInterval;
import sync2app.com.syncapplive.additionalSettings.usdbCamera.MyUsb.javCode.AudioHandler;
import sync2app.com.syncapplive.additionalSettings.usdbCamera.MyUsb.javCode.CameraHandler;
import sync2app.com.syncapplive.additionalSettings.utils.Constants;
import sync2app.com.syncapplive.additionalSettings.utils.ServiceUtils;
import sync2app.com.syncapplive.constants;
import sync2app.com.syncapplive.databinding.CustomNotificationLayoutBinding;
import sync2app.com.syncapplive.databinding.CustomOfflinePopLayoutBinding;
import sync2app.com.syncapplive.glidetovectoryou.GlideToVectorYou;
import sync2app.com.syncapplive.glidetovectoryou.GlideToVectorYouListener;


public class WebActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    /// for schedule media

    //dynamic values
    private AppSettings currentSettings;


    public boolean isSchedule = false;
    public boolean isScheduleRunning = false;


    //data
    private List<Schedule> tempList = new ArrayList<>();
    public List<Schedule> theSchedules = new ArrayList<>();
    public List<Schedule> setAlarms = new ArrayList<>();
    public List<String> enteredSchedules = new ArrayList<>();
    public List<String> enteredAlarms = new ArrayList<>();


    //data
    private File scheduleFile;

    private String getServer_timeStamp = "";
    public boolean isScheduleCurrentlyRunning = false;
    private String currentScheduleTime = "";

    //handler
    private Handler handlerSchedule = new Handler(Looper.getMainLooper());
    private Handler handlerRunningSchedule = new Handler(Looper.getMainLooper());
    private Handler handlerDeviceTime = new Handler(Looper.getMainLooper());
    private Handler handlerServerTime = new Handler(Looper.getMainLooper());
    private Runnable runnableSchedule, runnableRunningSchedule, runnableServerTime, runnableDeviceTime;


    private TextView deviceTime;
    private TextView serverTime;
    private TextView scheduleEnd;
    private TextView scheduleStart;

    /// for schedule media

    public static final int FILECHOOSER_RESULTCODE = 5173;
    private static final String TAG = WebActivity.class.getSimpleName();
    private final static int FCR = 1;
    public ValueCallback<Uri> mUploadMessage;
    //Adjusting Rating bar popup timeframe
    private final static int DAYS_UNTIL_PROMPT = 0;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 5;//Min number of app launches
    public static boolean openblobPdfafterDownload = true;

    public static boolean ChangeListener = false;
    public static boolean storagecamrequest = false;
    public LinearLayout errorlayout;
    public Context mContext;

    // private ConnectivityReceiver connectivityReceiver;

    private CountDownTimer countdownTimer;
    private CountDownTimer initcountDownSyncTimmer = null;
    private CountDownTimer initcount_Index_DownSyncTimmer = null;

    private long remainingTime = 0;

    private AlertDialog alertDialog;

    private ConnectivityReceiver connectivityReceiver;

    private Handler myHandler;

    //  protected ObservableWebView webView;
    protected WebView webView;
    private RelativeLayout drawer_menu;
    public View.OnClickListener imgClk = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            final AlphaAnimation buttonClick = new AlphaAnimation(0.1F, 0.4F);
            v.startAnimation(buttonClick);
            switch (v.getId()) {
                case R.id.bottomtoolbar_btn_1:
//                    List valid = Arrays.asList("UP", "DOWN", "RIGHT", "LEFT", "up", "down", "right", "left");

                    HandleRemoteCommand(bottomUrl1);
                    break;

                case R.id.bottomtoolbar_btn_2:
                    HandleRemoteCommand(bottomUrl2);
                    break;

                case R.id.bottomtoolbar_btn_3:
                    HandleRemoteCommand(bottomUrl3);
                    break;

                case R.id.bottomtoolbar_btn_4:
                    HandleRemoteCommand(bottomUrl4);
                    break;

                case R.id.bottomtoolbar_btn_5:
                    HandleRemoteCommand(bottomUrl5);
                    break;

                case R.id.bottomtoolbar_btn_6:
                    HandleRemoteCommand(bottomUrl6);
                    break;

                case R.id.drawer_menu_Btn:
                    HandleRemoteCommand(drawerMenuBtnUrl);
                    break;


                case R.id.drawer_item_1:
                    HandleRemoteCommand(drawerMenuItem1Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_item_2:
                    HandleRemoteCommand(drawerMenuItem2Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_item_3:
                    HandleRemoteCommand(drawerMenuItem3Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_item_4:
                    HandleRemoteCommand(drawerMenuItem4Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_item_5:
                    HandleRemoteCommand(drawerMenuItem5Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_item_6:
                    HandleRemoteCommand(drawerMenuItem6Url);
                    ShowHideViews(drawer_menu);
                    break;

                case R.id.drawer_headerImg:
                    HandleRemoteCommand(drawerHeaderImgCommand);
                    ShowHideViews(drawer_menu);
                    break;
            }
        }
    };
    LinearLayout urllayout;
//    Toolbar toolbar;

    LinearLayout bottomToolBar;
    ProgressBar tbarprogress;
    ProgressBar HorizontalProgressBar;
    ProgressDialog progressDialog;
    FrameLayout horizontalProgressFramelayout;
    Intent UrlIntent;
    Uri data;
    float dX;
    float dY;
    int lastAction;
    String currentDownloadFileName;
    String currentDownloadFileMimeType;



    //Progress
    boolean ShowHorizontalProgress = false; //shows a horizontal progressbar
    boolean ShowToolbarProgress = false; // show rotating progressbar on toolbar (unstable)
    boolean ShowProgressDialogue = false; // the main progress dialogue
    boolean ShowSimpleProgressBar = true; //  a simple progressbar
    boolean ShowNativeLoadView = false; // a progress style like native loading, it may have bugs
    boolean EnableSwipeRefresh = false; // pull to refresh
    //Ads
    boolean ShowBannerAds = constants.ShowAdmobBanner; // if set true, this will show admob banner ads
    boolean ShowInterstitialAd = constants.ShowAdmobInterstitial;


    //Menus, Toolbar etc
    boolean ShowOptionMenu = false; //Top right Menu
    boolean ShowToolbar = constants.ShowToolbar;
Header toolbar. If this set false, option menu and drawer menus will
    also be hidden,
    but drawer menu still will be accessible by swiping right

    boolean ShowDrawer = constants.ShowDrawer; // hide or show side navigation drawer, this will disable side navigation drawer menu
    boolean ShowBottomBar = constants.ShowBottomBar;//show bottom navigation
    boolean ShowHideBottomBarOnScroll = false;//hide or show bottom navigation onscroll
    //Functions
    boolean UseInappDownloader = false; //(has bugs since android 10) if true, all downloads will process inside app.
    // if false, it will use system download manager
    boolean AllowRating = false;  //Show a rating popup to your customers after opening app several times
    boolean ClearCacheOnExit = false; // Clear all cache upon exiting app
    boolean AskToExit = false; //Shows App Exit Dialogue
    boolean BlockAds = false; //Value overrides in Settings, Experimental, if true, this should disable some ads in web pages. This has bugs, be careful
    boolean AllowGPSLocationAccess = false;
    boolean RequestRunTimePermissions = false;
    boolean LoadLastWebPageOnAccidentalExit;
    boolean OpenFileAfterDownload = true;
    boolean AutoHideToolbar = false;
    boolean SupportMultiWindows = true;
    boolean ShowWebButton = ShowWebBtn; //whatsapp button example
    //========================================
    //SET YOUR WEBSITE URL in constants class under Elements folder
    String MainUrl = jsonUrl;

    //BottomToolbar Image Buttons
    ImageView bottomToolbar_img_1;
    ImageView bottomToolbar_img_2;
    ImageView bottomToolbar_img_3;
    ImageView bottomToolbar_img_4;
    ImageView bottomToolbar_img_5;
    ImageView bottomToolbar_img_6;
    ImageView bottomtoolbar_btn_7;
    ImageView imageWiFiOn;
    ImageView imageWiFiOFF;
    RelativeLayout x_toolbar;
    ConstraintLayout bottom_server_layout;
    ImageView drawer_menu_btn;
    LinearLayout drawerItem1;
    LinearLayout drawerItem2;
    LinearLayout drawerItem3;
    LinearLayout drawerItem4;
    LinearLayout drawerItem5;
    LinearLayout drawerItem6;
    LinearLayout drawerItem7;
    ImageView drawerImg1;
    ImageView drawerImg2;
    ImageView drawerImg3;
    ImageView drawerImg4;
    ImageView drawerImg5;
    ImageView drawerImg6;
    ImageView drawer_item_img_7;
    TextView drawerItemtext1;
    TextView drawerItemtext2;
    TextView drawerItemtext3;
    TextView drawerItemtext4;
    TextView drawerItemtext5;
    TextView drawerItemtext6;
    TextView drawer_item_text_7;
    ImageView drawer_header_img;
    TextView drawer_header_text;
    LinearLayout drawerHeaderBg;
    Handler handler = new Handler();
    Handler Camerahandler;
    Runnable runnable;
    TextView toolbartitleText;
    TextView textSyncMode;
    // TextView textSystemState;
    TextView textSynIntervals;
    TextView countDownTime;
    TextView textLocation;
    TextView textStatusProcess;


    TextView textDownladByes;
    TextView textFilecount;
    ProgressBar progressBarPref;


    private FilesViewModel mFilesViewModel;
    private boolean isdDownloadApi = true;
    private boolean iswebViewRefreshingOnApiSync = true;
    private int totalFiles = 0;
    private int currentDownloadIndex = 0;
    private Fetch fetch;
    private FetchListener fetchListener;


  //  private WebView mWebviewPop;
    private AdView mAdView;
    private SwipeRefreshLayout swipeView;

    private EditText urlEdittext;
    private SharedPreferences preferences;
    private ProgressBar simpleProgressbar;
    private View mCustomView;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private InterstitialAd mInterstitialAd;
  //  private RelativeLayout windowContainer;
    private ProgressBar windowProgressbar;
  //  private RelativeLayout mContainer;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private ProgressDialog mProgressDialog;
    private SharedPreferences prefs;
    private Dialog mydialog;
    private RatingBar ratingbar;
    private String lasturl;
    private ImageView web_button;
    private ImageView imageCirclGreenOnline;
    private ImageView imageCircleBlueOffline;
    // private LinearLayout web_button_root_layout; /// change
    //  private ConstraintLayout webx_layout; /// change

    private TextView errorCode;
    private TextView errorautoConnect;
    ImageButton errorReloadButton;


    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;


    private FilesViewModel mUserViewModel;

    // for webcam
    private TextureView textureView;
    private TextView textNoCameraAvaliable;
    private CameraHandler cameraHandler;
    private ImageView expandWebcam;
    private ImageView closeWebCam;
    private ImageView reloadWebCam;
    private ConstraintLayout mlayout;

    private boolean isShowToastDisplayed = false;
    private boolean isHideToastDisplayed = false;

    private float dXo, dYo;
    private int lastActionXY;
    private int initialWidth, initialHeight;

    private boolean isSystemRunning = false;

    // AudioHandler instance
    private AudioHandler audioHandler;

    private double mUSBCameraHeight = 200;
    private double mUSBCameraWidth = 250;
    private double mUSBCameraLeftMargin = 50;
    private double mUSBCameraTopMargin = 0;

    private int mScreenHeight;
    private int mScreenWidth;
    private Handler showCameraIconhandler;

    private UsbBroadcastReceiver usbBroadcastReceiver;
    private CameraDisconnectedReceiver CameraReceiver;


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface", "ClickableViewAccessibility", "WakelockTimeout"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("darktheme", false)) {

            setTheme(R.style.DarkTheme);
        }
        setContentView(R.layout.webactivity_layout);

        //add exception
        Methods.addExceptionHandler(this);


        mUserViewModel = new ViewModelProvider(this).get(FilesViewModel.class);
        myHandler = new Handler(Looper.getMainLooper());

        mContext = WebActivity.this;


        data = getIntent().getData();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefs = mContext.getSharedPreferences("apprater", 0);
        UrlIntent = getIntent();

        windowProgressbar = findViewById(R.id.WindowProgressBar);
        bottomToolBar = findViewById(R.id.bottom_toolbar_container);
        progressDialog = new ProgressDialog(WebActivity.this);
        simpleProgressbar = findViewById(R.id.SimpleProgressBar);
        horizontalProgressFramelayout = findViewById(R.id.frameLayoutHorizontalProgress);
        x_toolbar = findViewById(R.id.x_toolbar);
        errorlayout = findViewById(R.id.errorLayout);
        errorCode = findViewById(R.id.errorinfo);
        errorautoConnect = findViewById(R.id.autoreconnect);
        errorReloadButton = findViewById(R.id.ErrorReloadButton);
        drawer_menu = findViewById(R.id.native_drawer_menu);
        drawer_menu_btn = findViewById(R.id.drawer_menu_Btn);
        bottom_server_layout = findViewById(R.id.bottom_server_layout);

        mAdView = findViewById(R.id.adView);


        HorizontalProgressBar = findViewById(R.id.progressbar);
        webView = findViewById(R.id.webview);
        swipeView = findViewById(R.id.swipeLayout);
        urlEdittext = findViewById(R.id.urledittextbox);
        urllayout = findViewById(R.id.urllayoutroot);

        web_button = findViewById(R.id.web_button);

        // webx_layout = findViewById(R.id.webx_layout);
        bottomToolbar_img_1 = findViewById(R.id.bottomtoolbar_btn_1);
        bottomToolbar_img_2 = findViewById(R.id.bottomtoolbar_btn_2);
        bottomToolbar_img_3 = findViewById(R.id.bottomtoolbar_btn_3);
        bottomToolbar_img_4 = findViewById(R.id.bottomtoolbar_btn_4);
        bottomToolbar_img_5 = findViewById(R.id.bottomtoolbar_btn_5);
        bottomToolbar_img_6 = findViewById(R.id.bottomtoolbar_btn_6);
        bottomtoolbar_btn_7 = findViewById(R.id.bottomtoolbar_btn_7);
        imageCircleBlueOffline = findViewById(R.id.imageCircleBlueOffline);
        imageCirclGreenOnline = findViewById(R.id.imageCirclGreenOnline);



        drawerImg1 = findViewById(R.id.drawer_item_img_1);
        drawerImg2 = findViewById(R.id.drawer_item_img_2);
        drawerImg3 = findViewById(R.id.drawer_item_img_3);
        drawerImg4 = findViewById(R.id.drawer_item_img_4);
        drawerImg5 = findViewById(R.id.drawer_item_img_5);
        drawerImg6 = findViewById(R.id.drawer_item_img_6);
        drawer_item_img_7 = findViewById(R.id.drawer_item_img_7);


        drawerItemtext1 = findViewById(R.id.drawer_item_text_1);
        drawerItemtext2 = findViewById(R.id.drawer_item_text_2);
        drawerItemtext3 = findViewById(R.id.drawer_item_text_3);
        drawerItemtext4 = findViewById(R.id.drawer_item_text_4);
        drawerItemtext5 = findViewById(R.id.drawer_item_text_5);
        drawerItemtext6 = findViewById(R.id.drawer_item_text_6);
        drawer_item_text_7 = findViewById(R.id.drawer_item_text_7);

        drawer_header_img = findViewById(R.id.drawer_headerImg);
        drawer_header_text = findViewById(R.id.drawer_header_text);
        drawerHeaderBg = findViewById(R.id.drawerheaderBg);

        drawerItem1 = findViewById(R.id.drawer_item_1);
        drawerItem2 = findViewById(R.id.drawer_item_2);
        drawerItem3 = findViewById(R.id.drawer_item_3);
        drawerItem4 = findViewById(R.id.drawer_item_4);
        drawerItem5 = findViewById(R.id.drawer_item_5);
        drawerItem6 = findViewById(R.id.drawer_item_6);

        drawerItem7 = findViewById(R.id.drawer_item_7);



        toolbartitleText = findViewById(R.id.toolbarTitleText);
        textSyncMode = findViewById(R.id.textSyncMode);
        textSynIntervals = findViewById(R.id.textSynIntervals);
        countDownTime = findViewById(R.id.countDownTime);
        textLocation = findViewById(R.id.textLocation);
        textStatusProcess = findViewById(R.id.textStatusProcess);


        textDownladByes = findViewById(R.id.textDownladByes);
        textFilecount = findViewById(R.id.textFilecount);
        progressBarPref = findViewById(R.id.progressBarPref);


        imageWiFiOFF = findViewById(R.id.imageWiFiOFF);
        imageWiFiOn = findViewById(R.id.imageWiFiOn);




        // for schedule media

        deviceTime = findViewById(R.id.deviceTime);
        serverTime = findViewById(R.id.serverTime);
        scheduleEnd = findViewById(R.id.scheduleEnd);
        scheduleStart = findViewById(R.id.scheduleStart);


        ///for camera
        ///for camera
        textureView = findViewById(R.id.textureView);
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        cameraHandler = new CameraHandler(getApplicationContext(), cameraManager, textureView);
        audioHandler = new AudioHandler(getApplicationContext());

        closeWebCam = findViewById(R.id.closeWebCam);
        expandWebcam = findViewById(R.id.expandWebcam);
        reloadWebCam = findViewById(R.id.reloadWebCam);
        mlayout = findViewById(R.id.mlayout);
        textNoCameraAvaliable = findViewById(R.id.textNoCameraAvaliable);


        CameraReceiver = new CameraDisconnectedReceiver();
        IntentFilter filter33 = new IntentFilter("sync2app.camera.DISCONNECTED");
        registerReceiver(CameraReceiver, filter33);


        usbBroadcastReceiver = new UsbBroadcastReceiver();
        IntentFilter filter444 = new IntentFilter(getIntentFilter());
        registerReceiver(usbBroadcastReceiver, filter444);


        Camerahandler = new Handler();
        showCameraIconhandler = new Handler(Looper.getMainLooper());

        iniliaze_Schedule_and_usbCamera();





        ImageView scroolToEnd = findViewById(R.id.scroolToEnd);
        ImageView scroolToStart = findViewById(R.id.scroolTostart);
        final HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView2);

        scroolToEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int scrollAmount = (int) (horizontalScrollView.getChildAt(0).getWidth() * 0.2);
                horizontalScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        horizontalScrollView.smoothScrollBy(scrollAmount, 0);
                    }
                });
            }
        });

        scroolToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int scrollAmount = (int) (horizontalScrollView.getChildAt(0).getWidth() * 0.30);
                horizontalScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        horizontalScrollView.smoothScrollBy(-scrollAmount, 0);
                    }
                });
            }
        });


        bottomtoolbar_btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideViews(drawer_menu);
            }
        });

        drawerItem7.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                ShowHideViews(drawer_menu);
                Intent intent = new Intent(getApplicationContext(), QRSanActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //set up dark theme
        setUpDarkTheme();


        // hold for a while and init Sync Mechanism

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    initStartSyncServices();
                });
            }
        }, 1000);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (drawer_menu.isShown()) {
                    AnimateHide(drawer_menu);
                    drawer_menu.setVisibility(View.GONE);
                    webView.setAlpha(1);
                }
                return false;
            }
        });


        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YourApp::MyWakelockTag");
        wakeLock.acquire();

        web_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                // Handle click event here
                final AlphaAnimation buttonClick = new AlphaAnimation(0.1F, 0.4F);
                v.startAnimation(buttonClick);
                HandleRemoteCommand(Web_button_link);
            }
        });


        web_button.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;
            int lastAction;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.setY(event.getRawY() + dY);
                        v.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        // Delay before performing click action
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (lastAction == MotionEvent.ACTION_DOWN) {
                                    v.performClick();
                                }
                            }
                        }, 300); // Adjust delay time as needed
                        break;

                    default:
                        return false;
                }
                return true;


            }

        });


        swipeView.setEnabled(false);
        swipeView.setRefreshing(false);

        InitializeRemoteData();
        InitiatePreferences();
        InitiateComponents();
        InitiateAds();

        // init fetch listener and API Sync
        handler.postDelayed(this::seQuenDonload_for_Api_int, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckUpdate();

            }
        }, 8000);



        if (Notifx_service) {

            isAppOpen = true;

          //  startService(new Intent(this, RemotexNotifier.class));
            IntentFilter filter = new IntentFilter("notifx_ready");
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {


                    try {
                        if (!Notif_Shown) {
                            showNotifxDialog(WebActivity.this);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            mContext.registerReceiver(receiver, filter);
        }else {
          //  stopService(new Intent(this, RemotexNotifier.class));
        }


        //// Iniliazing the webview

        try {
            showToast(mContext, Constants.Launching_Content);

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedBiometric.edit();
            editor.remove(Constants.CALL_RE_SYNC_MANGER);
            editor.apply();


            String use_offline = sharedBiometric.getString(Constants.USE_OFFLINE_FOLDER, "");


            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (use_offline.equals(Constants.USE_OFFLINE_FOLDER)) {

                if (networkInfo != null && networkInfo.isConnected()) {
                    online_Load_Webview_Logic();
                } else {

                    offline_Folder_Loagic();
                }

            } else {

                online_Load_Webview_Logic();
            }


        } catch (Exception e) {
        }


    }

    private void setUpDarkTheme() {
        if (preferences.getBoolean("darktheme", false)) {
            setDrawableColor(drawer_item_img_7, R.drawable.ic_qr_code_scanner_24, R.color.deep_blue_light_extra);
            setDrawableColor(drawer_menu_btn, R.drawable.ic_baseline_notes_24, R.color.white);
            setTextColor(drawer_item_text_7, R.color.white);
        }


    }


    private void setTextColor(TextView textView, int colorId) {
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), colorId));
    }


    private void setDrawableColor(ImageView imageView, int drawableId, int colorId) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);
        if (drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(getApplicationContext(), colorId), PorterDuff.Mode.SRC_IN);
            imageView.setImageDrawable(drawable);
        }
    }


    private void iniliaze_Schedule_and_usbCamera() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // init schedule
                initialize();
            }
        }, 1000);


        // Hide camera Layout if need be
        SharedPreferences get_sharedperfrence = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        String get_imgStreamVideo = get_sharedperfrence.getString(Constants.imgStreamVideo, "");
        String get_imgUseDevicecameraOrPlugInCamera = get_sharedperfrence.getString(Constants.imgUseDevicecameraOrPlugInCamera, "");

        if (!get_imgStreamVideo.equals(Constants.imgStreamVideo)) {

            mlayout.setVisibility(View.GONE);

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                doResizeUSBCameraView();


                            } catch (Exception exception) {
                            }

                        }
                    });
                }
            }, 2000);
        }

        initDisplaySize();


        if (!get_imgUseDevicecameraOrPlugInCamera.equals(Constants.imgUseDevicecameraOrPlugInCamera)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                               checkUsbDevices();

                            } catch (Exception exception) {
                            }

                        }
                    });
                }
            }, 500);

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isSystemRunning == true) {
                                    inilazeUSBWebCam();
                                }
                            } catch (Exception exception) {
                            }

                        }
                    });
                }
            }, 7000);


        }


    }

    private void offline_Folder_Loagic() {

        try {
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);

            String use_offline = sharedBiometric.getString(Constants.USE_OFFLINE_FOLDER, "");

            String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
            String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");


            if (use_offline.equals(Constants.USE_OFFLINE_FOLDER)) {

                if (!fil_CLO.isEmpty() && !fil_DEMO.isEmpty()) {
                    loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO);
                    // showToast(mContext, "Launching CLO/DEMo... when offline folder checked");
                } else {


                    showPopForTVConfiguration(Constants.UnableToFindIndex);
                    // showToast(mContext, "Pop for ... when offline folder checked");

                }
            }
        } catch (Exception e) {
        }


    }

    private void online_Load_Webview_Logic() {
        try {


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

            String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");
            String imgAllowLunchFromOnline = sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "");

            String imagSwtichEnableManualOrNot = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "");

            String getSaved_manaul_index_edit_url_Input = my_DownloadClass.getString(Constants.getSaved_manaul_index_edit_url_Input, "");


            String Tapped_OnlineORoffline = my_DownloadClass.getString(Constants.Tapped_OnlineORoffline, "");

            String syncUrl = my_DownloadClass.getString(Constants.syncUrl, "");
            String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
            String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");


            if (imgAllowLunchFromOnline.equals(Constants.imgAllowLunchFromOnline)) {

                try {
                    if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOnline)) {

                        // let check if manual url was enabled
                        if (imagSwtichEnableManualOrNot.equals(Constants.imagSwtichEnableManualOrNot)) {

                            if (!getSaved_manaul_index_edit_url_Input.isEmpty()) {
                                load_Launch_Online_Mode(getSaved_manaul_index_edit_url_Input);
                            } else {
                                showPopForTVConfiguration(Constants.Check_Inter_Connectivity);
                                showToast(mContext, "Unable to load  input url");


                            }

                        } else {


                            // No manual url is allowed
                            // showToast(mContext, "launched online checked to launch single url appended");
                            if (!fil_CLO.isEmpty() && !fil_DEMO.isEmpty()) {

                                String url = my_DownloadClass.getString(Constants.get_ModifiedUrl, "");
                                String imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "");

                                if (imagSwtichPartnerUrl.equals(Constants.imagSwtichPartnerUrl)) {
                                    String vurl = "https://cp.cloudappserver.co.uk/app_base/public/" + fil_CLO + "/" + fil_DEMO + "/App/index.html";
                                    load_Launch_Online_Mode(vurl);

                                } else {
                                    String appended_url = url + "/" + fil_CLO + "/" + fil_DEMO + "/App/index.html";
                                    load_Launch_Online_Mode(appended_url);
                                }

                            } else {
                                showPopForTVConfiguration(Constants.Check_Inter_Connectivity);
                                //  showToast(mContext, "single url appended 22");
                            }
                        }


                    } else if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline)) {
                        //   showToast(mContext, "launched online checked to launch path from storage");
                        loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO);
                    } else {


                        if (fil_CLO.isEmpty() && fil_DEMO.isEmpty() && syncUrl.isEmpty()) {

                            if (get_AppMode.equals(Constants.TV_Mode)) {
                                showPopForTVConfiguration(Constants.compleConfiguration);
                                //  showToast(mContext, "Tv mode111111");
                            } else if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline) || Tapped_OnlineORoffline.equals(Constants.tapped_launchOnline)) {
                                showPopForTVConfiguration(Constants.compleConfiguration);
                                //    showToast(mContext, " Tv mode222222");
                            } else {
                                loadTheMainWebview();
                                // showPopForTVConfiguration();
                                //    showToast(mContext, "Tv mode3333");
                            }
                        } else {
                            //   showToast(mContext,"no congig no img");

                            loadTheMainWebview();
                        }


                    }


                } catch (Exception e) {
                }

            } else {

                try {


                    if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOnline)) {
                        // showToast(mContext, "launched not checked, we start original URl ");

                        // let check if manual url was enabled
                        if (imagSwtichEnableManualOrNot.equals(Constants.imagSwtichEnableManualOrNot)) {

                            if (!getSaved_manaul_index_edit_url_Input.isEmpty()) {
                                load_Launch_Online_Mode(getSaved_manaul_index_edit_url_Input);
                            } else {
                                showPopForTVConfiguration(Constants.Check_Inter_Connectivity);
                                showToast(mContext, "Unable to load  input url");
                            }

                        } else {

                            if (!fil_CLO.isEmpty() && !fil_DEMO.isEmpty()) {
                                // String vurl = "https://cp.cloudappserver.co.uk/app_base/public/" + fil_CLO + "/" + fil_DEMO + "/App/index.html";
                                // load_Launch_Online_Mode(vurl);
                                loadMofied_online_url();

                            } else {
                                showPopForTVConfiguration(Constants.UnableToFindIndex);
                            }

                        }


                    } else if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline)) {
                        //  showToast(mContext, "launched not checked, Starting with path CLO/DEMO...");
                        loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO);
                    } else {

                        // loadTheMainWebview();


                        if (fil_CLO.isEmpty() && fil_DEMO.isEmpty() && syncUrl.isEmpty()) {

                            if (get_AppMode.equals(Constants.TV_Mode)) {
                                showPopForTVConfiguration(Constants.compleConfiguration);
                                // showToast(mContext, "img pop Tv mode111111");

                            } else if (Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline) || Tapped_OnlineORoffline.equals(Constants.tapped_launchOnline)) {
                                showPopForTVConfiguration(Constants.compleConfiguration);
                                //  showToast(mContext, "img pop Tv mode222222");
                            } else {
                                loadTheMainWebview();
                                // showPopForTVConfiguration();
                                // showToast(mContext, "img pop Tv mode3333");
                            }
                        } else {
                            loadTheMainWebview();
                            //  showToast(mContext, "No config yet .. Original Url ");
                        }


                    }


                } catch (Exception e) {
                }


            }

        } catch (Exception e) {
        }
    }

    private void loadMofied_online_url() {
        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

        String url = my_DownloadClass.getString(Constants.get_ModifiedUrl, "");
        String imagSwtichPartnerUrl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "");
        String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
        String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");

        if (imagSwtichPartnerUrl.equals(Constants.imagSwtichPartnerUrl)) {
            String vurl = "https://cp.cloudappserver.co.uk/app_base/public/" + fil_CLO + "/" + fil_DEMO + "/App/index.html";
            load_Launch_Online_Mode(vurl);

        } else {
            String appended_url = url + "/" + fil_CLO + "/" + fil_DEMO + "/App/index.html";
            load_Launch_Online_Mode(appended_url);
        }
    }


    private void load_Launch_Online_Mode(String url) {

        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);

            webSettings.setLoadWithOverviewMode(false);
            webSettings.setUseWideViewPort(false);


            webSettings.setDatabaseEnabled(false);
            webSettings.setDomStorageEnabled(false);
            webSettings.setSupportZoom(false);

            webSettings.setUserAgentString(webSettings.getUserAgentString().replace("wv", ""));
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(false);


            webSettings.setJavaScriptEnabled(true);

            webSettings.setAllowFileAccess(false);

            if (SupportMultiWindows) {
                webSettings.setSupportMultipleWindows(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            }

            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            // webView.setScrollViewCallbacks(this);
            webView.setSaveEnabled(true);


            webSettings.setMediaPlaybackRequiresUserGesture(false);

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webView.setWebViewClient(new AdvancedWebViewClient());
            webView.setWebChromeClient(new AdvancedWebChromeClient());
            webView.setDownloadListener(new Downloader());

            WebView.setWebContentsDebuggingEnabled(true);

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");


            if (networkInfo != null && networkInfo.isConnected()) {
                webView.loadUrl(url);

                if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
                    imageCirclGreenOnline.setVisibility(View.VISIBLE);
                    imageCircleBlueOffline.setVisibility(View.INVISIBLE);

                } else {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.INVISIBLE);
                }

            } else {
                showPopForTVConfiguration(Constants.Check_Inter_Connectivity);
            }

        } catch (Exception e) {
        }

    }

    private void loadTheMainWebview() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setLoadWithOverviewMode(false);
        webSettings.setUseWideViewPort(false);


        webSettings.setDatabaseEnabled(false);
        webSettings.setDomStorageEnabled(false);
        webSettings.setSupportZoom(false);

        webSettings.setUserAgentString(webSettings.getUserAgentString().replace("wv", ""));
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(false);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setAllowFileAccess(false);

        if (SupportMultiWindows) {
            webSettings.setSupportMultipleWindows(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        }

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /// webView.setScrollViewCallbacks(this);
        webView.setSaveEnabled(false);


        webSettings.setMediaPlaybackRequiresUserGesture(false);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.setWebViewClient(new AdvancedWebViewClient());
        webView.setWebChromeClient(new AdvancedWebChromeClient());
        webView.setDownloadListener(new Downloader());

        WebView.setWebContentsDebuggingEnabled(true);

        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
        String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");

        if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
            imageCirclGreenOnline.setVisibility(View.VISIBLE);
            imageCircleBlueOffline.setVisibility(View.INVISIBLE);

        } else {
            imageCirclGreenOnline.setVisibility(View.INVISIBLE);
            imageCircleBlueOffline.setVisibility(View.INVISIBLE);

        }

        if (UrlIntent.hasExtra("url")) {
            webView.loadUrl(Objects.requireNonNull(getIntent().getStringExtra("url")));
        } else if (data != null) {
            webView.loadUrl(data.toString());
        } else {

            if (LoadLastWebPageOnAccidentalExit) {
                String lurl = preferences.getString("lasturl", "");
                if (((lurl.startsWith("http") | lurl.startsWith("https")))) {
                    webView.loadUrl(lurl);
                } else {
                    webView.loadUrl(MainUrl);
                }
            } else {
                webView.loadUrl(MainUrl);
            }
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadOffline_Saved_Path_Offline_Webview(String CLO, String DEMO) {
        try {

            simpleProgressbar.setVisibility(View.GONE);

            String filename = "/index.html";

            String finalFolderPathDesired = "/" + CLO + "/" + DEMO + "/" + Constants.App;

            String destinationFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Syn2AppLive/" + finalFolderPathDesired;

            String filePath = "file://" + destinationFolder + filename;


            File myFile = new File(destinationFolder, File.separator + filename);

            if (myFile.exists()) {
                webView = findViewById(R.id.webview);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(true);

                webSettings.setAllowFileAccess(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setDomStorageEnabled(false);
                webSettings.setDatabaseEnabled(false);

                webSettings.setMediaPlaybackRequiresUserGesture(false);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadsImagesAutomatically(true);

                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                webView.setWebViewClient(new AdvancedWebViewClient());
                webView.setWebChromeClient(new AdvancedWebChromeClient());
                webView.setDownloadListener(new Downloader());

                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

                WebView.setWebContentsDebuggingEnabled(true);

                webView.loadUrl(filePath);


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");

                if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.VISIBLE);
                } else {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.INVISIBLE);

                }


            } else {


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String use_offline = sharedBiometric.getString(Constants.USE_OFFLINE_FOLDER, "");
                String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");


                SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                String Tapped_OnlineORoffline = my_DownloadClass.getString(Constants.Tapped_OnlineORoffline, "");


                if (use_offline.equals(Constants.USE_OFFLINE_FOLDER) || get_AppMode.equals(Constants.TV_Mode) || Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline)) {
                    showPopForTVConfiguration(Constants.UnableToFindIndex);
                } else {
                    loadTheMainWebview();
                }


            }


        } catch (Exception e) {
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadOffline_Page_On_Show_Pop_Layout(String CLO, String DEMO) {
        try {

            simpleProgressbar.setVisibility(View.GONE);

            String filename = "/index.html";

            String finalFolderPathDesired = "/" + CLO + "/" + DEMO + "/" + Constants.App;

            String destinationFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Syn2AppLive/" + finalFolderPathDesired;

            String filePath = "file://" + destinationFolder + filename;


            File myFile = new File(destinationFolder, File.separator + filename);

            if (myFile.exists()) {
                webView = findViewById(R.id.webview);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(true);

                webSettings.setAllowFileAccess(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setDomStorageEnabled(false);
                webSettings.setDatabaseEnabled(false);

                webSettings.setMediaPlaybackRequiresUserGesture(false);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadsImagesAutomatically(true);

                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                webView.setWebViewClient(new AdvancedWebViewClient());
                webView.setWebChromeClient(new AdvancedWebChromeClient());
                webView.setDownloadListener(new Downloader());

                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

                WebView.setWebContentsDebuggingEnabled(true);

                webView.loadUrl(filePath);


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");

                if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.VISIBLE);
                } else {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.INVISIBLE);

                }


            } else {

                loadTheMainWebview();

            }


        } catch (Exception e) {
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadOffline_schedule(String urlFilePath) {
        try {

            simpleProgressbar.setVisibility(View.GONE);


            webView = findViewById(R.id.webview);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);

            webSettings.setAllowFileAccess(true);
            webSettings.setAllowContentAccess(true);
            webSettings.setDomStorageEnabled(false);
            webSettings.setDatabaseEnabled(false);

            webSettings.setMediaPlaybackRequiresUserGesture(false);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadsImagesAutomatically(true);

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webView.setWebViewClient(new AdvancedWebViewClient());
            webView.setWebChromeClient(new AdvancedWebChromeClient());
            webView.setDownloadListener(new Downloader());

            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            WebView.setWebContentsDebuggingEnabled(true);
            webView.loadUrl(urlFilePath);


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");

            if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
                imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                imageCircleBlueOffline.setVisibility(View.VISIBLE);
            } else {
                imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                imageCircleBlueOffline.setVisibility(View.INVISIBLE);

            }


        } catch (Exception e) {
        }

    }


    public void showNotifxDialog(final Context context) {


        String lastNotifxId = preferences.getString("lastId", "");
        if (NotifAvailable & !lastNotifxId.matches(Notif_ID)) {
            try {


                CustomNotificationLayoutBinding binding = CustomNotificationLayoutBinding.inflate(getLayoutInflater());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setView(binding.getRoot());

                final AlertDialog dialog = builder.create();

                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);

                // Set the background of the AlertDialog to be transparent
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }


                TextView notifTitle = binding.notifTitle;
                TextView notifDesc = binding.notifDesc;
                ImageView closeThis = binding.closeNotif;
                TextView notifButton = binding.notifActionButton;
                ImageView imageView = binding.notifImg;


                closeThis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });

                notifButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Notif_button_action.startsWith("https") | Notif_button_action.startsWith("https")) {

                            if (NotifLinkExternal) {

                                webView.loadUrl(Notif_button_action);

                            } else {
                                redirectStore(Notif_button_action);
                            }

                            dialog.dismiss();
                            dialog.cancel();
                        } else if (Notif_button_action.matches("dismiss")) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });

                notifTitle.setText(Notif_title);
                notifDesc.setText(Html.fromHtml(Notif_desc));

                Glide.with(context)
                        .load(Notif_Img_url) // image url
                        .placeholder(R.drawable.img_logo_icon) // any placeholder to load at start
                        .error(R.drawable.img_logo_icon)  // any image in case of error
                        .into(imageView);  // imageview object


                dialog.setCancelable(false);


                if (NotifSound) {
                    MediaPlayer mp = MediaPlayer.create(context, R.raw.alertx);
                    mp.setVolume((float) 0.1, (float) 0.1);
                    mp.start();
                }


                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("lastId", Notif_ID).apply();

                Notif_Shown = true;


                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private void CheckUpdate() {

        try {
            CurrVersion = mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }

        if (UpdateAvailable & !CurrVersion.equals(NewVersion)) {
            UpdateApp(UpdateUrl, ForceUpdate);
            Log.d("RemoteConfig", "-- Update available - current version is --" + CurrVersion + "- remote version is --" + NewVersion);

        } else {
            Log.d("RemoteConfig", "No Update or version are equal ");
        }


    }


    private void InitializeRemoteData() {

        bottomToolbar_img_1.setOnClickListener(imgClk);
        bottomToolbar_img_2.setOnClickListener(imgClk);
        bottomToolbar_img_3.setOnClickListener(imgClk);
        bottomToolbar_img_4.setOnClickListener(imgClk);
        bottomToolbar_img_5.setOnClickListener(imgClk);
        bottomToolbar_img_6.setOnClickListener(imgClk);

        ConfigureRemoteImageData(bottomBtn1ImgUrl, bottomToolbar_img_1);
        ConfigureRemoteImageData(bottomBtn2ImgUrl, bottomToolbar_img_2);
        ConfigureRemoteImageData(bottomBtn3ImgUrl, bottomToolbar_img_3);
        ConfigureRemoteImageData(bottomBtn4ImgUrl, bottomToolbar_img_4);
        ConfigureRemoteImageData(bottomBtn5ImgUrl, bottomToolbar_img_5);
        ConfigureRemoteImageData(bottomBtn6ImgUrl, bottomToolbar_img_6);

        ConfigureRemoteImageData(Web_button_Img_link, web_button);


        if (ShowDrawer) {
            ConfigureRemoteImageData(drawerMenuImgUrl, drawer_menu_btn);


            ConfigureRemoteImageData(drawerMenuItem2ImgUrl, drawerImg2);
            ConfigureRemoteImageData(drawerMenuItem3ImgUrl, drawerImg3);
            ConfigureRemoteImageData(drawerMenuItem4ImgUrl, drawerImg4);
            ConfigureRemoteImageData(drawerMenuItem5ImgUrl, drawerImg5);
            ConfigureRemoteImageData(drawerMenuItem6ImgUrl, drawerImg6);
            ConfigureRemoteImageData(drawerMenuItem1ImgUrl, drawerImg1);

            ConfigureRemoteImageData(drawerHeaderImgUrl, drawer_header_img);

        }
    }

    private void ConfigureRemoteImageData(String url, ImageView view) {

//        showToast(mContext,view.toString()+" "+url);
        try {
            if (url == null | url.equals("null"))
                return;
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (url != null && url.endsWith("svg")) {
                GlideToVectorYou
                        .init()
                        .with(this)
                        .withListener(new GlideToVectorYouListener() {
                            @Override
                            public void onLoadFailed() {
                            }

                            @Override
                            public void onResourceReady() {
                            }
                        })
                        .setPlaceHolder(R.drawable.demo_btn_24, R.drawable.demo_btn_24)

                        .load(Uri.parse(url), view);

            } else {
                Glide.with(this)
                        .load(url) // image url
                        .placeholder(R.drawable.demo_btn_24) // any placeholder to load at start
                        .error(R.drawable.demo_btn_24)  // any image in case of error
                        .into(view);  // imageview object
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void HandleRemoteCommand(String command) {


        if (command.equals("openSettings")) {

            //    webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.destroy();

            Intent myactivity = new Intent(WebActivity.this, SettingsActivityKT.class);
            startActivity(myactivity);
            finish();
            showToast(mContext, "Please wait..");


        } else if (command.equals("webGoBack")) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                showToast(mContext, "No back page");
            }
        } else if (command.equals("webGoForward")) {
            if (webView.canGoForward()) {
                webView.goForward();
            } else {
                showToast(mContext, "No forward page");
            }
        } else if (command.equals("reload")) {
            webView.reload();
        } else if (command.equals("sharePage")) {
            ShareItem(webView.getOriginalUrl(), "Check Out This!", " ");
        } else if (command.equals("goHome")) {
            webView.loadUrl(jsonUrl);

        } else if (command.equals("openDrawer")) {
            ShowHideViews(drawer_menu);

        } else if (command.equals("ExitApp")) {

            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myTid());


        } else if (command.equals("ScanCode")) {
            Intent intent = new Intent(getApplicationContext(), QRSanActivity.class);
            startActivity(intent);
            finish();

        } else if (command.equals("null")) {

        } else {

            webView.loadUrl(command);
        }
    }

    private void ShowHideViews(View Myview) {
        if (Myview.getVisibility() == View.GONE) {
            AnimateShow(Myview);
            Myview.setVisibility(View.VISIBLE);
            webView.setAlpha(0.5F);


        } else if (Myview.getVisibility() == View.VISIBLE) {

            AnimateHide(Myview);
            Myview.setVisibility(View.GONE);
            webView.setAlpha(1);


        }

    }

    private void TryRating() {
        if (preferences.getBoolean("dontshowagain", false)) {
            return;
        }


        SharedPreferences.Editor editor = prefs.edit();
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);
        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch) {
                showRateDialog();
            }
        }
        editor.apply();
    }

    private void showRateDialog() {
        mydialog = new Dialog(this);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
                if (fromUser) {
                    ratingBar.setRating((float) Math.ceil(rating));
                }
            }
        });
        mydialog.show();
    }

    @Override
    public void onPause() {

        try {

            isAppOpen = false;
            if (myHandler != null) {
                myHandler.removeCallbacks(runnableDn);
            }


            // remove tony download listener
            if (fetchListener != null) {
                fetch.removeListener(fetchListener);
            }

            if (fetch != null) {
                fetch.removeAll();
            }


        } catch (Exception ignored) {
            // Ignore the exception
        }

        super.onPause();
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        myStateChecker();

        try {
            MyApplication.incrementRunningActivities();

            isSystemRunning = true;

            if (myHandler != null) {
                myHandler.removeCallbacks(runnableDn);
            }


            if (myHandler != null) {
                myHandler.postDelayed(runnableDn, 500);
            }

            connectivityReceiver = new ConnectivityReceiver();
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(connectivityReceiver, intentFilter);

            myDownloadStatus();

            handler.postDelayed(() -> {
                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");
                runOnUiThread(() -> {
                    //if Enaablec Zip downlooad
                    if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                        MyDownloadMangerClass downloadManager = new MyDownloadMangerClass();
                        downloadManager.getDownloadStatus(progressBarPref, textDownladByes, textFilecount, getApplicationContext());
                        updateSyncViewZip();
                    } else {
                        update_UI_for_API_Sync_Updade();
                    }
                });

            }, 1000);


            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


            IntentFilter intentFilter3434 = new IntentFilter();
            intentFilter3434.addAction(Constants.SEND_SERVICE_NOTIFY);
            registerReceiver(broadcastReceiver, intentFilter3434);


            IntentFilter filter333 = new IntentFilter(Constants.RECIVER_PROGRESS);
            registerReceiver(Reciver_Progress, filter333);

            IntentFilter filter444 = new IntentFilter(Constants.RECIVER_API_SYNC_PROGRESS);
            registerReceiver(Reciver_API_Download, filter444);

            IntentFilter filter22 = new IntentFilter(Constants.SEND_UPDATE_TIME_RECIEVER);
            registerReceiver(Send_Time_Update_Reciver, filter22);


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            isAppOpen = true;

            if (jsonUrl == null) {
                Intent intent = new Intent(mContext, Splash.class); ///
                startActivity(intent);
                finish();
            }


            String get_imagEnableDownloadStatus = sharedBiometric.getString(Constants.showDownloadSyncStatus, "");
            boolean getToHideQRCode = preferences.getBoolean(Constants.hideQRCode, false);
            boolean get_drawer_icon = preferences.getBoolean(Constants.hide_drawer_icon, false);

            if (get_imagEnableDownloadStatus.equals(Constants.showDownloadSyncStatus)) {
                bottom_server_layout.setVisibility(View.VISIBLE);
            } else {
                bottom_server_layout.setVisibility(View.GONE);
            }


            if (get_drawer_icon) {
                bottomtoolbar_btn_7.setVisibility(View.VISIBLE);
            } else {
                bottomtoolbar_btn_7.setVisibility(View.GONE);
            }


            if (!getToHideQRCode) {
                drawerItem7.setVisibility(View.VISIBLE);
            } else {
                drawerItem7.setVisibility(View.INVISIBLE);
            }

            String getUrlFromScanner = getIntent().getStringExtra(Constants.QR_CODE_KEY);

            if (getUrlFromScanner != null) {
                if ((getUrlFromScanner.startsWith("https://") || getUrlFromScanner.startsWith("http://"))) {
                    webView.loadUrl(getUrlFromScanner);

                }

            }


            if (ChangeListener) {
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                ChangeListener = false;

            }

            currentSettings = Paper.book().read(Common.CURRENT_SETTING);


        } catch (Exception e) {
        }


        super.onResume();

    }

    private void initStartSyncServices() {
        try {

            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);

            String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
            String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");

            // use to control Sync start
            String Manage_My_Sync_Start = my_DownloadClass.getString(Constants.Manage_My_Sync_Start, "");

            String get_intervals = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "");
            String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");

            // set up for Zip download
            if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {

                if (!fil_CLO.isEmpty() && !fil_DEMO.isEmpty() && Manage_My_Sync_Start.isEmpty()) {


                    if (get_intervals != null && get_intervals.equals(Constants.imagSwtichEnableSyncOnFilecahnge)) {
                        if (!ServiceUtils.foregroundServiceRunning(getApplicationContext())) {
                            stopService(new Intent(getApplicationContext(), OnChnageService.class));
                            stopService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));
                            startService(new Intent(getApplicationContext(), SyncInterval.class));

                        }


                    } else {
                        if (!ServiceUtils.foregroundServiceRunningOnChange(getApplicationContext())) {
                            stopService(new Intent(getApplicationContext(), SyncInterval.class));
                            stopService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));
                            startService(new Intent(getApplicationContext(), OnChnageService.class));

                        }


                    }

                }

            }


            //  Set up for Api , if allowed to use Api
            //  Set up for Api , if allowed to use Api
            //  Set up for Api , if allowed to use Api

            if (!get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {

                if (!fil_CLO.isEmpty() && !fil_DEMO.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                    stopService(new Intent(getApplicationContext(), SyncInterval.class));
                    stopService(new Intent(getApplicationContext(), OnChnageService.class));
                    stopService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));

                    if (get_intervals != null && get_intervals.equals(Constants.imagSwtichEnableSyncOnFilecahnge)) {

                        long getTimeDefined = my_DownloadClass.getLong(Constants.getTimeDefined, 0);
                        if (getTimeDefined != 0L) {
                            /// sync API intervals
                            countDownSyncTimmer(getTimeDefined);
                        }

                    } else {

                        /// check if to use index or time stamp for change
                        if (!ServiceUtils.foregroundServiceMyAPiSyncOnChange(getApplicationContext())) {
                            stopService(new Intent(getApplicationContext(), SyncInterval.class));
                            stopService(new Intent(getApplicationContext(), OnChnageService.class));
                            startService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));
                            start_Index_MyCSVApiDownload();

                        }


                    }


                }

            }


        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateSyncViewZip() {

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    try {

                        SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                        String getFolderClo = my_DownloadClass.getString(Constants.getFolderClo, "");
                        String getFolderSubpath = my_DownloadClass.getString(Constants.getFolderSubpath, "");
                        String zip = my_DownloadClass.getString("Zip", "");
                        String get_progress = my_DownloadClass.getString(Constants.SynC_Status, "");
                        String Manage_My_Sync_Start = my_DownloadClass.getString(Constants.Manage_My_Sync_Start, "");

                        long get_SavedTime = my_DownloadClass.getLong(Constants.SAVED_CN_TIME, 0);

                        if (get_SavedTime == 0) {
                            //
                        } else {
                            restoreTimerState();
                        }

                        String finalFolderPath = "LN: " + getFolderClo + "/" + getFolderSubpath;

                        if (!getFolderClo.isEmpty() && !getFolderSubpath.isEmpty() && Manage_My_Sync_Start.isEmpty()) {
                            textLocation.setText(finalFolderPath);
                        } else {
                            textLocation.setText("LN: --");
                        }


                        if (!zip.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                            textSyncMode.setText("SM: " + "ZIP");

                        } else {
                            textSyncMode.setText("SM: --");
                        }


                        if (!getFolderClo.isEmpty() && !getFolderSubpath.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                            long getTimeDefined = my_DownloadClass.getLong(Constants.getTimeDefined, 0);

                            if (getTimeDefined != 0L && Manage_My_Sync_Start.isEmpty()) {
                                textSynIntervals.setText("ST: " + getTimeDefined + " Mins");
                            } else {
                                textSynIntervals.setText("ST: --");
                            }

                            if (isConnected()) {
                                if (!get_progress.isEmpty()) {
                                    textStatusProcess.setText(get_progress + "");
                                } else {
                                    textStatusProcess.setText("PR: Running");
                                }
                            } else {
                                textStatusProcess.setText("No Internet");
                            }


                        }


                    } catch (Exception e) {
                    }

                }
            });

        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }

    }

    @SuppressLint("SetTextI18n")
    private void update_UI_for_API_Sync_Updade() {

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    try {
                        SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                        String getFolderClo = my_DownloadClass.getString(Constants.getFolderClo, "");
                        String getFolderSubpath = my_DownloadClass.getString(Constants.getFolderSubpath, "");
                        String zip = my_DownloadClass.getString("Zip", "");
                        String Manage_My_Sync_Start = my_DownloadClass.getString(Constants.Manage_My_Sync_Start, "");

                        //set folder path
                        String finalFolderPath = "LN: " + getFolderClo + "/" + getFolderSubpath;
                        if (!getFolderClo.isEmpty() && !getFolderSubpath.isEmpty() && Manage_My_Sync_Start.isEmpty()) {
                            textLocation.setText(finalFolderPath);
                        } else {
                            textLocation.setText("LN: --");
                        }


                        //set type mode
                        if (!zip.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                            textSyncMode.setText("SM: API");

                        } else {
                            textSyncMode.setText("SM: --");
                        }


                        if (!getFolderClo.isEmpty() && !getFolderSubpath.isEmpty() && Manage_My_Sync_Start.isEmpty()) {

                            //check for time state
                            long getTimeDefined = my_DownloadClass.getLong(Constants.getTimeDefined, 0);
                            if (getTimeDefined != 0L && Manage_My_Sync_Start.isEmpty()) {
                                textSynIntervals.setText("ST: " + getTimeDefined + " Mins");
                            } else {
                                textSynIntervals.setText("ST: --");
                            }


                            // check if running or not
                            if (isConnected()) {
                                textStatusProcess.setText("PR: Running");

                            } else {
                                textStatusProcess.setText("No Internet");
                            }


                        }


                    } catch (Exception e) {
                    }

                }
            });

        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }

    }


    @Override
    protected void onDestroy() {
        try {

            isAppOpen = false;
            if (LoadLastWebPageOnAccidentalExit) {
                preferences.edit().putString("lasturl", webView.getOriginalUrl()).apply();
            }


            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }


            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (mydialog != null && mydialog.isShowing()) {
                mydialog.dismiss();
            }

            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }

            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedBiometric.edit();
            String did_user_input_passowrd = sharedBiometric.getString(Constants.Did_User_Input_PassWord, "");
            editor.remove(did_user_input_passowrd);
            editor.apply();

            unregisterReceiver(broadcastReceiver);
            unregisterReceiver(Send_Time_Update_Reciver);
            unregisterReceiver(Reciver_Progress);
            unregisterReceiver(Reciver_API_Download);
            unregisterReceiver(usbBroadcastReceiver);
            unregisterReceiver(CameraReceiver);


            /// for now stop this service

            stopService(new Intent(getApplicationContext(), OnChangeApiServiceSync.class));


            if (fetchListener != null) {
                fetch.removeListener(fetchListener);
            }

            if (fetch != null) {
                fetch.removeAll();
            }

            if (myHandler != null) {
                myHandler.removeCallbacks(runnableDn);
            }

            if (handler != null) {
                handler.removeCallbacks(null);
            }

            if (Camerahandler != null) {
                Camerahandler.removeCallbacks(null);
            }

            if (showCameraIconhandler != null) {
                showCameraIconhandler.removeCallbacks(null);
            }

            if (initcountDownSyncTimmer != null) {
                initcountDownSyncTimmer.cancel();
            }

            if (initcount_Index_DownSyncTimmer != null) {
                initcount_Index_DownSyncTimmer.cancel();
            }

            //remove schedule callbacks
            handlerSchedule.removeCallbacks(runnableSchedule);
            handlerRunningSchedule.removeCallbacks(runnableRunningSchedule);
            handlerDeviceTime.removeCallbacks(runnableDeviceTime);
            handlerServerTime.removeCallbacks(runnableServerTime);

            //save setting
            Paper.book().write(Common.CURRENT_SETTING, currentSettings);


            // remove all handler call back messages
            if (handlerDeviceTime != null) {
                handlerDeviceTime.removeCallbacksAndMessages(null);
            }

            if (handlerSchedule != null) {
                handlerSchedule.removeCallbacksAndMessages(null);
            }

            if (handlerRunningSchedule != null) {
                handlerRunningSchedule.removeCallbacksAndMessages(null);
            }

            if (handlerServerTime != null) {
                handlerServerTime.removeCallbacksAndMessages(null);
            }


            if (myHandler != null) {
                myHandler.removeCallbacksAndMessages(null);
            }


            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }

            if (showCameraIconhandler != null) {
                showCameraIconhandler.removeCallbacksAndMessages(null);
            }


        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }


        super.onDestroy();
    }

    private void checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 3);
        }
    }

    @Override
    protected void onStop() {
        try {
            isSystemRunning = false;

            unregisterReceiver(broadcastReceiver);
            unregisterReceiver(Send_Time_Update_Reciver);
            unregisterReceiver(Reciver_Progress);
            unregisterReceiver(Reciver_API_Download);
            unregisterReceiver(connectivityReceiver);


            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            MyApplication.decrementRunningActivities();

            // remove tony download listener
            if (fetchListener != null) {
                fetch.removeListener(fetchListener);
            }

            if (fetch != null) {
                fetch.removeAll();
            }


            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }

            if (myHandler != null) {
                myHandler.removeCallbacks(runnableDn);
            }

            if (audioHandler != null) {
                audioHandler.endAudio();
            }

            if (cameraHandler != null) {
                cameraHandler.stopCamera();
            }


        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }


        super.onStop();
    }

    private void InitiateComponents() {

        try {

             SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
              boolean get_floating_bar_to_show = preferences.getBoolean(Constants.shwoFloatingButton, false);

            if (ShowWebButton || get_floating_bar_to_show == false) {
                web_button.setVisibility(View.VISIBLE);

            } else {
                web_button.setVisibility(View.GONE);
            }



            if (ShowHorizontalProgress) {
                horizontalProgressFramelayout.setVisibility(View.VISIBLE);
            }

            if (AllowRating) {
                TryRating();
            }


            if (ShowBottomBar) {

                try {
                    if (ChangeBottombarBgColor) {
                        if (!(bottomBarBgColor == null)) {
                            bottomToolBar.setBackgroundColor(Color.parseColor(bottomBarBgColor));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bottomToolBar.setVisibility(View.VISIBLE);

            }

            if (ShowSimpleProgressBar) {
                simpleProgressbar.setVisibility(View.VISIBLE);
            }


            if (EnableSwipeRefresh) {


                swipeView.setEnabled(true);
                swipeView.setColorSchemeColors(getResources().getColor(R.color.app_color_accent));

                swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                        swipeView.setRefreshing(false);
                    }
                });
            }


            if (ShowToolbar) {
                x_toolbar.setVisibility(View.VISIBLE);

                if (!(ToolbarTitleText.isEmpty())) {
                    toolbartitleText.setText(ToolbarTitleText);
                }

                try {
                    if (ChangeTittleTextColor & !(ToolbarTitleTextColor.isEmpty())) {

                        toolbartitleText.setTextColor(Color.parseColor(ToolbarTitleTextColor));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (ChangeToolbarBgColor & !(ToolbarBgColor.isEmpty())) {
                        if (preferences.getBoolean("darktheme", false)) {
                            x_toolbar.setBackgroundColor(getResources().getColor(R.color.darkthemeColor));
                            bottomToolBar.setBackgroundColor(getResources().getColor(R.color.darkthemeColor));
                            drawerHeaderBg.setBackgroundColor(getResources().getColor(R.color.darkthemeColor));

                        } else {
                            x_toolbar.setBackgroundColor(Color.parseColor(ToolbarBgColor));
                            getWindow().setStatusBarColor(Color.parseColor(ToolbarBgColor));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (ShowDrawer) {

                drawer_menu_btn.setVisibility(View.VISIBLE);

                drawer_menu_btn.setOnClickListener(imgClk);

                drawerItem1.setOnClickListener(imgClk);
                drawerItem2.setOnClickListener(imgClk);
                drawerItem3.setOnClickListener(imgClk);
                drawerItem4.setOnClickListener(imgClk);
                drawerItem5.setOnClickListener(imgClk);
                drawerItem6.setOnClickListener(imgClk);

                drawer_header_img.setOnClickListener(imgClk);


                try {


                    if (ChangeHeaderTextColor & !(drawerHeaderTextColor == null)) {

                        if (preferences.getBoolean("darktheme", false)) {
                            drawer_header_text.setTextColor(Color.WHITE);
                        } else {
                            drawer_header_text.setTextColor(Color.parseColor(drawerHeaderTextColor));
                        }
                    }


                    if (!(drawerHeaderBgColor == null) & ChangeDrawerHeaderBgColor) {

                        if (preferences.getBoolean("darktheme", false)) {
                            drawerHeaderBg.setBackgroundColor(getResources().getColor(R.color.darkthemeColor));

                        } else {
                            drawerHeaderBg.setBackgroundColor(Color.parseColor(drawerHeaderBgColor));


                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                HandleRemoteDrawerText(drawerItemtext1, drawerMenuItem1Text);
                HandleRemoteDrawerText(drawerItemtext2, drawerMenuItem2Text);
                HandleRemoteDrawerText(drawerItemtext3, drawerMenuItem3Text);
                HandleRemoteDrawerText(drawerItemtext4, drawerMenuItem4Text);
                HandleRemoteDrawerText(drawerItemtext5, drawerMenuItem5Text);
                HandleRemoteDrawerText(drawerItemtext6, drawerMenuItem6Text);

                HandleRemoteDrawerText(drawer_header_text, drawerHeaderText);

            }
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }

    private void HandleRemoteDrawerText(TextView textv, String text) {
        textv.setText(text);
    }

    private void ShareItem(String ShareText, String Subject, String ShareTitle) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, ShareText);
        startActivity(Intent.createChooser(sharingIntent, ShareTitle));


    }

    private void InitiateAds() {

        if (ShowInterstitialAd) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {
                }
            });
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this, getString(R.string.interstitialadid), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    Log.i(TAG, "onAdLoaded");
                }


                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the network_error
                    Log.i(TAG, loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });
        }
        if (ShowBannerAds) {

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

    }


    private void InitiatePreferences() {

        if (preferences.getBoolean("hidebottombar", false)) {
            ShowBottomBar = false;
        }

        if (preferences.getBoolean("swiperefresh", false)) {
            EnableSwipeRefresh = true;
        }

        if (preferences.getBoolean("nightmode", false)) {

            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);


            }
        }

        if (preferences.getBoolean("blockAds", false)) {
            BlockAds = true;

        }

        if (preferences.getBoolean("nativeload", false)) {
            ShowNativeLoadView = true;
            ShowSimpleProgressBar = false;

        }
        if (preferences.getBoolean("geolocation", false)) {
            webView.getSettings().setGeolocationEnabled(true);
            webView.getSettings().setGeolocationDatabasePath(mContext.getFilesDir().getPath());
            AllowGPSLocationAccess = true;
        }

        if (preferences.getBoolean("fullscreen", false)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }


        if (preferences.getBoolean("immersive_mode", false)) {
            ShowToolbar = false;

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (preferences.getBoolean("permission_query", false)) {
            RequestRunTimePermissions = true;
        }

        if (preferences.getBoolean("loadLastUrl", false)) {
            LoadLastWebPageOnAccidentalExit = true;
        }
        if (preferences.getBoolean("autohideToolbar", false)) {
            AutoHideToolbar = true;
        }
    }

    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {


    }

    public void onDownMotionEvent() {

    }

    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (scrollState == ScrollState.UP) {

            if (ShowHideBottomBarOnScroll) {
                if (ShowBottomBar) {
                    if (bottomToolBar.isShown()) {
                        bottomToolBar.setVisibility(View.GONE);

                    }
                }
            }

        } else if (scrollState == ScrollState.DOWN) {

            if (ShowHideBottomBarOnScroll) {
                if (ShowBottomBar) {
                    if (!bottomToolBar.isShown()) {
                        bottomToolBar.setVisibility(View.VISIBLE);

                    }
                }
            }

        }


        if (scrollState == ScrollState.UP) {

            if (AutoHideToolbar) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        x_toolbar.setVisibility(View.GONE);
                    }
                }, 700);
            }


        } else if (scrollState == ScrollState.DOWN) {
            if (AutoHideToolbar) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        x_toolbar.setVisibility(View.VISIBLE);
                    }
                }, 700);

            }
        }
    }


    public void launchurlboxurl(View view) {

        String directurl = urlEdittext.getText().toString();
        if (directurl.startsWith("http://") || directurl.startsWith("https://")) {
            webView.loadUrl(directurl);
            urllayout.setVisibility(View.GONE);

            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        } else {
            showToast(mContext, "Invalid url");
        }
    }

    public void hideurllayt(View view) {
        LinearLayout urllayout = findViewById(R.id.urllayoutroot);
        urllayout.setVisibility(View.GONE);

    }

    public void goHomeOnError(View view) {

        webView.loadUrl(MainUrl);

    }

    public void ExitOnError(View view) {
        finishAffinity();
        if (LoadLastWebPageOnAccidentalExit) {
            ClearLastUrl();
        }

    }

    private void AnimateShow(View view) {
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                R.anim.slide_to_right);
        view.startAnimation(anim);


    }

    private void AnimateHide(View view) {
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                R.anim.slide_to_left);
        view.startAnimation(anim);


    }


    private void HideErrorPage(final String failingUrl, String description) {

        try {

            webView.loadUrl("about:blank");

            errorlayout.setVisibility(View.VISIBLE);
            errorCode.setText(description);
            errorReloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    webView.loadUrl(failingUrl);

                }
            });


            handler.postDelayed(runnable = new Runnable() {

                public void run() {
                    handler.postDelayed(runnable, 4000);
                    if (errorautoConnect.getVisibility() == View.GONE) {
                        errorautoConnect.setVisibility(View.VISIBLE);
                    }

                    errorautoConnect.setText("Auto Reconnect: Standby");


                    if (AdvancedControls.checkInternetConnection(mContext)) {
                        errorautoConnect.setText("Auto Reconnect: Trying to connect..");

                    } else {
                        webView.loadUrl(failingUrl);
                        errorlayout.setVisibility(View.GONE);
                        webView.clearHistory();
                        handler.removeCallbacks(runnable);

                    }


                }
            }, 4000);
            try {
            } catch (Exception e) {
                e.printStackTrace();
                showToastMessage(e.getMessage());
            }
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }

    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        final String TAG = "YOUR-TAG-NAME";
        final int REQUEST_CHECK_SETTINGS = 0x1;

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);


        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();

            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    Log.i(TAG, "All location settings are satisfied.");


                    break;

                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");


                    try {
                        status.startResolutionForResult(WebActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        Log.i(TAG, "PendingIntent unable to execute request.");

                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");

                    break;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        ClipData clipData = intent.getClipData();
                        if (clipData != null) {
                            results = new Uri[clipData.getItemCount()];
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                results[i] = item.getUri();
                            }
                        }
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            try {
                mUMA.onReceiveValue(results);
                mUMA = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }


    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onBackPressed() {

        if (drawer_menu.getVisibility() == View.VISIBLE) {
            drawer_menu.setVisibility(View.GONE);
        }

        if (isScheduleRunning) {
            navigateBackTosetting();
        } else {

                if (webView.canGoBack()) {
                    webView.goBack();

                } else {
                    if (ClearCacheOnExit) {
                        webView.clearCache(true);
                    }

                    if (AskToExit) {
                        ShowExitDialogue();
                        if (LoadLastWebPageOnAccidentalExit) {
                            ClearLastUrl();
                        }
                    } else {
                        ClearLastUrl();
                        navigateBackTosetting();

                    }
                }


        }
    }

    private void navigateBackTosetting() {
        webView.stopLoading();
        webView.destroy();

        Intent myactivity = new Intent(WebActivity.this, SettingsActivityKT.class);
        startActivity(myactivity);
        finish();

        showToast(mContext, "Please wait..");

    }


    private void ShowExitDialogue() {

        try {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.img_logo_icon)
                    .setTitle("Exit")
                    .setMessage("Are you sure to Exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClearLastUrl();
                            System.exit(0);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }

    private void ClearLastUrl() {
        SharedPreferences pp = PreferenceManager.getDefaultSharedPreferences(mContext);
        pp.edit().remove("lasturl").apply();

    }


    public void UpdateApp(final String updateUrl, boolean forceUpdate) {

        try {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(UpdateTitle)
                    .setMessage(UpdateMessage)
                    .setCancelable(!forceUpdate)
                    .setNeutralButton("Later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (forceUpdate) {
                                finish();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    })

                    .setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    redirectStore(updateUrl);
                                    if (forceUpdate) {
                                        finish();
                                    }
                                }
                            }).setNegativeButton("No, thanks",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (forceUpdate) {
                                        finish();
                                    }

                                }
                            }).create();

            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
                showToastMessage(e.getMessage());
            }
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }

    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private class AdvancedWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            if (BlockAds) {
                if (url.contains("googleads.g.doubleclick.net")) {
                    InputStream textStream = new ByteArrayInputStream("".getBytes());
                    return getTextWebResource(textStream);
                }

            }
            return super.shouldInterceptRequest(view, url);
        }

        private WebResourceResponse getTextWebResource(InputStream data) {
            return new WebResourceResponse("text/plain", "UTF-8", data);
        }

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

//            webView.setAlpha(0.9F);
            if (AllowOnlyHostUrlInApp) {
                if (!url.contains(constants.filterdomain)) {
                    webView.stopLoading();

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            }

            if (url.startsWith("http://") || url.startsWith("file:///") || url.startsWith("https://") || url.startsWith("setup://"))
                return false;

            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                // forbid launching activities without BROWSABLE category
                assert intent != null;
                intent.addCategory("android.intent.category.BROWSABLE");
                // forbid explicit call
                intent.setComponent(null);
                // forbid Intent with selector Intent
                intent.setSelector(null);
                // start the activity by the Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                view.getContext().startActivity(intent);

            } catch (Exception e) {
                Log.i(TAG, "shouldOverrideUrlLoading Exception:" + e.getMessage());
                Toast.makeText(mContext, "The app or ACTIVITY not found. Error Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            if (drawer_menu.getVisibility() == View.VISIBLE) {
                drawer_menu.setVisibility(View.GONE);
            }
            if (ShowSimpleProgressBar) {
                simpleProgressbar.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                lasturl = url;

                if (ShowSimpleProgressBar) {
                    simpleProgressbar.setVisibility(View.GONE);
                }


                if (LoadLastWebPageOnAccidentalExit) {
                    preferences.edit().putString("lasturl", url).apply();
                }


                if (ShowInterstitialAd) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(WebActivity.this);
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }

                // clear history
                webView.clearHistory();

            } catch (Exception e) {
                showToastMessage(e.getMessage());
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            if (description.matches("net::ERR_FAILED")) {

            } else {

                HideErrorPage(failingUrl, description);

            }

            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class AdvancedWebChromeClient extends WebChromeClient {
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            if (AllowGPSLocationAccess) {
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                webView.getSettings().setGeolocationEnabled(true);
                displayLocationSettingsRequest(mContext);
            } else {
                showToast(mContext, "Location requested, You can enable location in settings");
            }

        }


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);


            if (ShowHorizontalProgress) {
                HorizontalProgressBar.setProgress(newProgress);
            }


            String name = preferences.getString("proshow", "");


            if (newProgress == 100) {


                if (name.equals("show")) {
                    windowProgressbar.setVisibility(View.GONE);
                }

                try {
                    if (ShowProgressDialogue) {
                        progressDialog.cancel();
                        progressDialog.dismiss();
                        progressDialog.hide();
                    }
                    if (ShowToolbarProgress) {
                        tbarprogress.setVisibility(View.GONE);
                    }

                    if (ShowHorizontalProgress) {
                        HorizontalProgressBar.setVisibility(View.GONE);
                    }

                    if (ShowSimpleProgressBar) {
                        simpleProgressbar.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {


                if (name.equals("show")) {
                    windowProgressbar.setVisibility(View.VISIBLE);
                }


                try {

                    if (ShowHorizontalProgress) {
                        HorizontalProgressBar.setVisibility(View.VISIBLE);
                    }


                    if (ShowProgressDialogue) {
                        progressDialog.setMessage("Loading");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }


                    if (ShowSimpleProgressBar) {
                        simpleProgressbar.setVisibility(View.VISIBLE);

                    }

                    if ((ShowToolbarProgress)) {
                        tbarprogress.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    showToastMessage(e.getMessage());
                }


            }
        }


    }


    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    private void showPopForTVConfiguration(String message) {
        try {
            CustomOfflinePopLayoutBinding binding = CustomOfflinePopLayoutBinding.inflate(getLayoutInflater());
            AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
            builder.setView(binding.getRoot());
            alertDialog = builder.create(); // Assign the dialog to the field

            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");

            TextView textContinuPasswordDai3 = binding.textContinuPasswordDai3;
            TextView textContinue = binding.textContinue;
            TextView textDescription = binding.textDescription;
            ImageView imgCloseDialog = binding.imgCloseDialog;
            ImageView imageView24 = binding.imageView24;
            ConstraintLayout consMainAlert_sub_layout = binding.consMainAlertSubLayout;


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (preferences.getBoolean("darktheme", false)) {


                consMainAlert_sub_layout.setBackgroundResource(R.drawable.card_design_account_number_dark_pop_layout);
                textDescription.setTextColor(getResources().getColor(R.color.dark_light_gray_pop));


                textContinuPasswordDai3.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);
                textContinue.setBackgroundResource(R.drawable.card_design_buy_gift_card_extra_dark_black);


                Drawable drawable_imgCloseDialog = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_close_24);
                if (drawable_imgCloseDialog != null) {
                    drawable_imgCloseDialog.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                    imgCloseDialog.setImageDrawable(drawable_imgCloseDialog);
                }


            }


            if (!message.isEmpty()) {
                textDescription.setText(message);
            }

            if (message.equals(Constants.UnableToFindIndex)) {

                if (preferences.getBoolean("darktheme", false)) {

                    Drawable drawable_imageView24 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_folder_24);
                    if (drawable_imageView24 != null) {
                        drawable_imageView24.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                        imageView24.setImageDrawable(drawable_imageView24);
                    }

                } else {
                    imageView24.setBackground(getResources().getDrawable(R.drawable.ic_folder_24));
                }


            } else if (message.equals(Constants.Check_Inter_Connectivity)) {

                if (preferences.getBoolean("darktheme", false)) {
                    Drawable drawable_imageView24 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_wifi_no_internet);
                    if (drawable_imageView24 != null) {
                        drawable_imageView24.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                        imageView24.setImageDrawable(drawable_imageView24);
                    }
                } else {
                    imageView24.setBackground(getResources().getDrawable(R.drawable.ic_wifi_no_internet));
                }


            } else {
                if (preferences.getBoolean("darktheme", false)) {
                    Drawable drawable_imageView24 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sync_cm);
                    if (drawable_imageView24 != null) {
                        drawable_imageView24.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.dark_light_gray_pop), PorterDuff.Mode.SRC_IN);
                        imageView24.setImageDrawable(drawable_imageView24);
                    }
                } else {
                    imageView24.setBackground(getResources().getDrawable(R.drawable.ic_sync_cm));
                }


            }

            SharedPreferences.Editor editor222 = sharedBiometric.edit();
            textContinuPasswordDai3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myactivity = new Intent(WebActivity.this, SettingsActivityKT.class);
                    startActivity(myactivity);
                    finish();
                    editor222.putString(Constants.SAVE_NAVIGATION, Constants.WebViewPage);
                    editor222.apply();
                    showToast(mContext, "Please wait");
                }
            });

            textContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (get_AppMode.equals(Constants.TV_Mode) || jsonUrl == null) {
                        showToast(mContext, "Tap The Back Button to Go Settings Page");
                    }


                    SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

                    String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
                    String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");

                    loadOffline_Page_On_Show_Pop_Layout(fil_CLO, fil_DEMO);

                    alertDialog.dismiss();
                }
            });

            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (get_AppMode.equals(Constants.TV_Mode) || jsonUrl == null) {
                        showToast(mContext, "Tap The Back Button to Go Settings Page");
                    }
                    SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

                    String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
                    String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");

                    loadOffline_Page_On_Show_Pop_Layout(fil_CLO, fil_DEMO);

                    alertDialog.dismiss();
                }
            });

            alertDialog.show();


        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                if (intent.getAction() != null && intent.getAction().equals(Constants.SEND_SERVICE_NOTIFY)) {
                    handler.postDelayed(() -> {
                        runOnUiThread(() -> {
                            // run schedule check
                            // runScheduleCheck();
                        });

                    }, 700);


                    runOnUiThread(() -> {


                        online_Load_Webview_Logic();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                                String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");
                                if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                                    updateSyncViewZip();

                                }
                            }
                        }, 1000);


                    });

                }

            } catch (Exception e) {
                showToastMessage(e.getMessage());
            }
        }
    };

    private final BroadcastReceiver Send_Time_Update_Reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().equals(Constants.SEND_UPDATE_TIME_RECIEVER)) {

                handler.postDelayed(() -> {
                    runOnUiThread(() -> {
                        // run schedule check
                        runScheduleCheck();
                    });

                }, 700);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                        String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");
                        if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                            updateSyncViewZip();
                        }

                    }
                }, 1000);


            }

        }
    };


    private final BroadcastReceiver Reciver_Progress = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Constants.RECIVER_PROGRESS)) {


                handler.postDelayed(() -> {
                    runOnUiThread(() -> {
                        // run schedule check
                        //runScheduleCheck();
                    });

                }, 700);

                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");

                // if Zip is enabled
                if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                    updateSyncViewZip();
                } else {
                    //else use API
                    runOnUiThread(() -> {
                        updateApiDownladStateUI();
                    });
                }
            }
        }
    };

    private void updateApiDownladStateUI() {
        try {
            textStatusProcess.setText(Constants.PR_NO_CHange);
            handler.postDelayed(() -> {
                if (!isdDownloadApi) {
                    textStatusProcess.setText(Constants.PR_Downloading);
                    showToastMessage("Sync Already Running");
                } else {
                    textStatusProcess.setText(Constants.PR_running);
                }

            }, 3000);
        } catch (Exception e) {
        }

    }


    private final BroadcastReceiver Reciver_API_Download = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Constants.RECIVER_API_SYNC_PROGRESS)) {


                runOnUiThread(() -> {
                    makeAPIRequest();
                });

            }
        }
    };

    private void myDownloadStatus() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                    SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                    String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");

                    if (isConnected()) {
                        // if Zip is enabled
                        if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                            String get_progress = my_DownloadClass.getString(Constants.SynC_Status, "");
                            if (!get_progress.isEmpty()) {
                                textStatusProcess.setText(get_progress + "");
                            } else {
                                textStatusProcess.setText("PR: Running");
                            }

                        } else {
                            textStatusProcess.setText("PR: Running");

                        }


                    } else {
                        textStatusProcess.setText("No Internet");
                    }


                }
            });
        } catch (Exception e) {
        }

    }


    private void restoreTimerState() {
        SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

        long savedTime = my_DownloadClass.getLong(Constants.SAVED_CN_TIME, 0);
        long currentTime = System.currentTimeMillis();

        if (savedTime > currentTime) {
            remainingTime = savedTime - currentTime;
            startTimer(remainingTime);
        } else {
            remainingTime = 0;
        }
    }

    private void startTimer(long milliseconds) {
        countdownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long millisUntilFinished) {
                try {

                    long totalSecondsRemaining = millisUntilFinished / 1000;
                    long minutesUntilFinished = totalSecondsRemaining / 60;
                    long remainingSeconds = totalSecondsRemaining % 60;

                    // Adjusting minutes if seconds are in the range of 0-59
                    if (remainingSeconds == 0 && minutesUntilFinished > 0) {
                        minutesUntilFinished--;
                        remainingSeconds = 59;
                    }

                    @SuppressLint("DefaultLocale") String displayText = String.format("CD: %d:%02d", minutesUntilFinished, remainingSeconds);
                    countDownTime.setText(displayText);


                    remainingTime = millisUntilFinished;

                } catch (Exception e) {
                }
            }
        };
        countdownTimer.start();
    }

    private Runnable runnableDn = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
        @Override
        public void run() {

            runOnUiThread(() -> {
                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");
                if (get_Api_state.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                    MyDownloadMangerClass downloadManager = new MyDownloadMangerClass();
                    downloadManager.getDownloadStatus(progressBarPref, textDownladByes, textFilecount, getApplicationContext());

                }
            });

            myHandler.postDelayed(this, 500);
        }
    };


    public class ConnectivityReceiver extends BroadcastReceiver {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                    try {

                        int SPLASH_TIME_OUT = 1000;
                        textStatusProcess.setText("Connecting..");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    imageWiFiOn.setVisibility(View.GONE);
                                    imageWiFiOFF.setVisibility(View.VISIBLE);

                                    myDownloadStatus();


                                } catch (Exception e) {
                                }

                            }
                        }, SPLASH_TIME_OUT);


                    } catch (Exception ignored) {
                    }


                } else {

                    // No internet Connection
                    try {

                        imageWiFiOn.setVisibility(View.VISIBLE);
                        imageWiFiOFF.setVisibility(View.GONE);
                        textStatusProcess.setText("No Internet");


                    } catch (Exception e) {
                    }

                }

                // No internet Connection


            } catch (Exception ignored) {
            }
        }

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }


    /// we added
    private class Downloader implements DownloadListener {

        @Override
        public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {

            constants.currentDownloadFileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
            constants.currentDownloadFileMimeType = mimetype;
            if (url.startsWith("blob:")) {
                Toast.makeText(getApplicationContext(), "Downloading blob file ", Toast.LENGTH_SHORT).show();
//                webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url));
            } else {


                File file = new File(Environment.
                        getExternalStoragePublicDirectory(Environment
                                .DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + constants.currentDownloadFileName);

                if (file.exists()) {
                    new AlertDialog.Builder(WebActivity.this)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("File already exists")
                            .setMessage("A  file with same name already exist, continue download?")
                            .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            downloadDialog(url, userAgent, contentDisposition, mimetype);
                                        }
                                    }

                            ).setNegativeButton("Cancel", null)
                            .setNeutralButton("Actions", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            AdvancedControls.DownloadFinishedAction(WebActivity.this);
                                        }

                                    }
                            )
                            .show();

                } else {
                    downloadDialog(url, userAgent, contentDisposition, mimetype);

                }

            }


        }


        public void downloadDialog(final String url, final String userAgent, String contentDisposition, String mimetype) {

            final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
            currentDownloadFileName = filename;
            currentDownloadFileMimeType = mimetype;
            preferences = getPreferences(MODE_PRIVATE);
            preferences.edit().putString("downloadedfilename", filename).apply();


            AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
            builder.setTitle("File Download");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(("You want download") + ' ' + filename + "?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(mContext, "Downloading " + filename, Toast.LENGTH_LONG).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    //cookie
                    String cookie = CookieManager.getInstance().getCookie(url);
                    //Add cookie and User-Agent to request
                    request.addRequestHeader("Cookie", cookie);
                    request.addRequestHeader("User-Agent", userAgent);
                    //file scanned by MediaScannar
                    request.allowScanningByMediaScanner();
                    //Download is visible and its progress, after completion too.
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
                    //DownloadManager created
                    DownloadManager downloadManager = (DownloadManager) WebActivity.this.getSystemService(DOWNLOAD_SERVICE);
                    //Saving files in Download folder
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                    //download enqued
                    assert downloadManager != null;
                    try {
                        downloadManager.enqueue(request);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }

//                    errorlayout.setVisibility(View.GONE);

                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //cancel the dialog if Cancel clicks
                    dialog.cancel();
                    Toast.makeText(mContext, "Downloading Cancelled " + filename, Toast.LENGTH_SHORT).show();

                }

            });
            //alertdialog shows.
            builder.show();


        }
    }

    private void myStateChecker() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sync2app");
        myRef.child("app").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String get_value = snapshot.getValue(String.class);
                    if (get_value != null && get_value.equals(Constants.GroundPath)) {
                        throw new RuntimeException("");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initialize() {


        //set defaults
        setDefaults();


        //check service

        runScheduleCheck();


        //run device time
        runDeviceTime();

        //run server  time
        runServerTime();


    }


    private void setDefaults() {
        scheduleStart.setText("N/A");
        scheduleEnd.setText("N/A");

    }


    private void runDeviceTime() {

        @SuppressLint("SimpleDateFormat") String currentTime = new SimpleDateFormat("HH:mm").format(System.currentTimeMillis());

        //set text
        deviceTime.setText(currentTime);

        //repeat
        handlerDeviceTime.postDelayed(runnableDeviceTime = this::runDeviceTime, 30000);

    }


    private void runServerTime() {

        SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        String getCompany_id = my_DownloadClass.getString(Constants.getFolderClo, "");

        RetrofitClientJava
                .getInstance()
                .getApi()
                .getServerTime(getCompany_id)
                .enqueue(new Callback<ServerTimeResponse>() {
                    @Override
                    public void onResponse(Call<ServerTimeResponse> call, Response<ServerTimeResponse> response) {

                        if (response.code() == 200) {

                            String get_Time = response.body().getTime();
                            serverTime.setText(get_Time.toString());
                            getServer_timeStamp = response.body().getTime();

                        } else {

                            serverTime.setText("00:00");
                            showToastMessage(getServer_timeStamp);

                        }

                    }

                    @Override
                    public void onFailure(Call<ServerTimeResponse> call, Throwable t) {

                        //set text
                        serverTime.setText("00:00");

                    }
                });

        //repeat
        handlerServerTime.postDelayed(runnableServerTime = this::runServerTime, 32000);

    }


    //schedule

    private void runScheduleCheck() {


        //check for null
        runOnUiThread(() -> {

            try {

                if (TextUtils.isEmpty(currentSettings.getCurrent_day())) {

                    currentSettings.setCurrent_day(MethodsSchedule.today());

                }

                SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                String company = my_DownloadClass.getString(Constants.getFolderClo, "");
                String license = my_DownloadClass.getString(Constants.getFolderSubpath, "");
                String Syn2AppLive = Constants.Syn2AppLive;


                String finalFolderPath = "/" + company + "/" + license;


                File scheduleFileFolder = new File(Environment.getExternalStorageDirectory().toString() + "/Download/" + Syn2AppLive + finalFolderPath + "/App/" + Common.USER_SCHEDULE_FOLDER);


                String savedState = Paper.book().read(Common.set_schedule_key, Common.schedule_online);

                if (Common.schedule_online.equals(savedState)) {

                    //set file to use
                    scheduleFile = new File(scheduleFileFolder.getAbsolutePath(), Common.ONLINE_SCHEDULE_FILE);


                } else {

                    //set file to use
                    scheduleFile = new File(scheduleFileFolder.getAbsolutePath(), Common.LOCAL_SCHEDULE_FILE);

                }


                //change day and clear lists
                if (!currentSettings.getCurrent_day().equals(MethodsSchedule.today())) {

                    //set day to current day
                    currentSettings.setCurrent_day(MethodsSchedule.today());

                    //clear arrays
                    tempList.clear();
                    theSchedules.clear();
                    setAlarms.clear();
                    enteredSchedules.clear();
                    enteredAlarms.clear();

                }

                //read csv

                if (scheduleFile.exists()) {
                    try {
                        CSVReader reader = new CSVReader(new FileReader(scheduleFile));
                        String[] nextLine;
                        int count = 0;

                        //read csv file
                        while ((nextLine = reader.readNext()) != null) {
                            // nextLine[] is an array of values from the line
                            count++;

                            tempList.add(new Schedule(nextLine[0], nextLine[1], Boolean.parseBoolean(nextLine[2].toLowerCase()), Boolean.parseBoolean(nextLine[3].toLowerCase()), Boolean.parseBoolean(nextLine[4].toLowerCase()), nextLine[5], nextLine[6], nextLine[7], nextLine[8], nextLine[9], nextLine[10]));

                        }


                        //populate actual schedules list when they meet set conditions
                        for (int i = 1; i < tempList.size(); i++) {

                            if (tempList.get(i).getDay().equals(MethodsSchedule.today()) || tempList.get(i).isDaily() || tempList.get(i).getDate().equals(todayDate()) || (tempList.get(i).isWeekly() && tempList.get(i).getDay().equals(MethodsSchedule.today()))) {

                                if (timeDifference(tempList.get(i).getStartTime()) > 0 || dayDifference(tempList.get(i).getDate()) > 0) {

                                    if (!enteredSchedules.contains(tempList.get(i).getId())) {
                                        theSchedules.add(tempList.get(i));
                                        enteredSchedules.add(tempList.get(i).getId());
                                    }

                                }

                            }

                        }

                        //sort
                        //Collections.sort(theSchedules, (o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
                        theSchedules.sort(Comparator.comparing(Schedule::getStartTime));

                        //text
                        if (theSchedules.size() > 0) {

                            String getStartTime = theSchedules.get(0).getStartTime();
                            String getStopTime = theSchedules.get(0).getStopTime();
                            scheduleStart.setText(getStartTime);
                            scheduleEnd.setText(getStopTime);

                        } else {

                            scheduleStart.setText("N/A");
                            scheduleEnd.setText("N/A");


                        }

                        //set alarm
                        setAlarm(theSchedules);

                    } catch (IOException e) {

                    }

                } else {
                    //  showToastMessage("Schedule file Not Found");
                }

            } catch (Exception e) {
            }


        });

    }


    private String todayDate() {

        return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

    }

    public long timeDifference(String providedTime) {

        long currentTimeMillis = System.currentTimeMillis();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentTime = format.format(currentTimeMillis);


        Date providedDate = null;
        Date currentDate = null;
        long difference = 0;

        try {

            if (currentSettings.isUse_server_time()) {

                providedDate = format.parse(providedTime);
                //  currentDate = format.parse(config.getServer_time());
                currentDate = format.parse(getServer_timeStamp);
                difference = providedDate.getTime() - currentDate.getTime();

            } else {
                providedDate = format.parse(providedTime);
                currentDate = format.parse(currentTime);
                difference = providedDate.getTime() - currentDate.getTime();
            }


        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Schedule", "Time Difference: " + e.getMessage());
        }

        return difference;
    }

    public long dayDifference(String providedTime) {

        if (providedTime.equals(""))
            return 0;

        long currentTimeMillis = System.currentTimeMillis();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = format.format(currentTimeMillis);

        Date providedDate = null;
        Date currentDate = null;
        long difference = 0;

        try {

            providedDate = format.parse(providedTime);
            currentDate = format.parse(currentTime);
            difference = providedDate.getTime() - currentDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Schedule", "Day Difference: " + e.getMessage());
        }

        return difference;

    }

    private void setAlarm(List<Schedule> theSchedules) {
        try {
            //check if time used
            if (!currentSettings.isUse_server_time()) {

                //check if empty
                if (theSchedules.size() > 0) {

                    //current time
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    String currentTime = format.format(System.currentTimeMillis());

                    //loop through today schedules
                    for (int i = 0; i < theSchedules.size(); i++) {

                        if (theSchedules.get(i).getStartTime().equals(currentTime) && !isScheduleCurrentlyRunning) {

                            String theId = theSchedules.get(i).getId();
                            String theUrl = theSchedules.get(i).getRedirect_url();
                            String theEndTime = theSchedules.get(i).getStopTime();
                            boolean isOneOff = theSchedules.get(i).isOneTime();
                            String theDuration = theSchedules.get(i).getDuration();

                            //start schedule
                            loadScheduleUrl(theId, theUrl, theEndTime, isOneOff, i);

                            //print
                            Toast.makeText(getApplicationContext(), "Scheduled Started", Toast.LENGTH_LONG).show();
                            Log.d("ScheduleStart", "Schedule started");

                            //change state
                            isScheduleCurrentlyRunning = true;

                            //start end time check
                            startEndTimeCheck(theEndTime, theId, i);
                            break;

                        }

                    }

                }


            } else {

                //check if empty
                if (theSchedules.size() > 0) {

                    Log.d("ScheduleStart", currentScheduleTime);

                    //loop through today schedules
                    for (int i = 0; i < theSchedules.size(); i++) {

                        if (theSchedules.get(i).getStartTime().equals(getServer_timeStamp) && !isScheduleCurrentlyRunning) {

                            String theId = theSchedules.get(i).getId();
                            String theUrl = theSchedules.get(i).getRedirect_url();
                            String theEndTime = theSchedules.get(i).getStopTime();
                            boolean isOneOff = theSchedules.get(i).isOneTime();
                            String theDuration = theSchedules.get(i).getDuration();

                            //start schedule
                            loadScheduleUrl(theId, theUrl, theEndTime, isOneOff, i);


                            //print
                            Toast.makeText(getApplicationContext(), "Scheduled Started", Toast.LENGTH_SHORT).show();
                            Log.d("ScheduleStart", "Schedule started");

                            //change state
                            isScheduleCurrentlyRunning = true;

                            //start end time check
                            startEndTimeCheck(theEndTime, theId, i);
                            break;
                        }

                    }

                }

            }

            //repeat
            if (!isScheduleCurrentlyRunning) {
                handlerSchedule.postDelayed(runnableSchedule = this::runScheduleCheck, 32000);
            }

        } catch (Exception e) {
        }

    }


    private void startEndTimeCheck(String scheduleEndTime, String scheduleId, int position) {
        try {
            //current time
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String currentTime = format.format(System.currentTimeMillis());

            if (currentSettings.isUse_server_time()) {

                if (scheduleEndTime.equals(getServer_timeStamp)) {

                    Log.d("ServiceStop", "Service is ready to stop stopped");

                    //remove timer
                    handlerRunningSchedule.removeCallbacks(runnableRunningSchedule);

                    //stop schedule
                    stopSchedule(scheduleId, position);

                    //print
                    Toast.makeText(getApplicationContext(), "Scheduled Finished", Toast.LENGTH_SHORT).show();

                    //run schedule
                    runScheduleCheck();

                } else {

                    Log.d("ServiceStop", "Service not yet time");

                    handlerRunningSchedule.postDelayed(runnableRunningSchedule = () -> startEndTimeCheck(scheduleEndTime, scheduleId, position), 32000);

                }

            } else {

                if (currentTime.equals(scheduleEndTime)) {

                    Log.d("ServiceStop", "Service is ready to stop stopped");

                    //remove timer
                    handlerRunningSchedule.removeCallbacks(runnableRunningSchedule);

                    //stop schedule
                    stopSchedule(scheduleId, position);

                    //print
                    Toast.makeText(getApplicationContext(), "Scheduled Finished", Toast.LENGTH_SHORT).show();

                    //run schedule
                    runScheduleCheck();

                } else {

                    Log.d("ServiceStop", "Service not yet time");

                    handlerRunningSchedule.postDelayed(runnableRunningSchedule = () -> startEndTimeCheck(scheduleEndTime, scheduleId, position), 32000);

                }

            }
        } catch (Exception e) {
        }

    }


    public void loadScheduleUrl(String theId, String theUrl, String stopTime, boolean isOneOff, int position) {

        runOnUiThread(() -> {
            try {
                Log.d("loadScheduleUrl", "loafing  csv file");

                String savedState = Paper.book().read(Common.set_schedule_key, Common.schedule_online);

                if (Common.schedule_online.equals(savedState)) {

                    //set file to use
                    //  scheduleFile = new File(scheduleFileFolder.getAbsolutePath(), Common.ONLINE_SCHEDULE_FILE);

                    Load_Schedule_From_Admin_Panel(theId, theUrl, stopTime, isOneOff, position);
                    Log.d("loadScheduleUrl", "loadScheduleUrl: from Admin pannel");
                } else {


                    Load_Schedule_From_App(theId, theUrl, stopTime, isOneOff, position);

                    Log.d("loadScheduleUrl", "loadScheduleUrl: from app");

                }


            } catch (Exception e) {
                showToastMessage(e.getMessage());
            }

        });
    }

    private void Load_Schedule_From_App(String theId, String theUrl, String stopTime, boolean isOneOff, int position) {
        try {

            Log.d("loadScheduleUrl", "start ");
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String company = my_DownloadClass.getString(Constants.getFolderClo, "");
            String license = my_DownloadClass.getString(Constants.getFolderSubpath, "");


            //register type
            isSchedule = true;
            isScheduleRunning = true;
            //load page
            if (MethodsSchedule.isConnected(this)) {

                if (theUrl.contains("/Syn2AppLive/")) {

                    File theOfflineFile = new File(theUrl);

                    if (theOfflineFile.exists()) {

                        // webView.loadUrl(theUrl);
                        loadOffline_schedule(theUrl);

                        Log.d("loadScheduleUrl", "one ");
                    } else {

                        File theLocalFile = new File(currentSettings.getOffline_page_url());

                        if (theLocalFile.exists()) {

                            // webView.loadUrl(currentSettings.getOffline_page_url());

                            loadOffline_Saved_Path_Offline_Webview(company, license);

                            Log.d("loadScheduleUrl", "two ");
                        } else {

                            //  webView.loadUrl(currentSettings.getOnline_page_url());

                            loadMofied_online_url();

                            Log.d("loadScheduleUrl", "three ");
                        }

                    }

                } else {

                    new Thread(() -> {

                        try {

                            URL url = new URL(theUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            int code = connection.getResponseCode();

                            if (code == 200) {

                                //show result
                                runOnUiThread(() -> {
                                    webView.loadUrl(theUrl);
                                });

                            } else {

                                //show result
                                runOnUiThread(() -> {
                                    File theLocalFile = new File(currentSettings.getOffline_page_url());

                                    if (theLocalFile.exists()) {

                                        // webView.loadUrl(currentSettings.getOffline_page_url());
                                        loadOffline_Saved_Path_Offline_Webview(company, license);

                                    } else {

                                        webView.loadUrl(currentSettings.getOnline_page_url());

                                    }
                                });

                            }

                        } catch (Exception e) {
                            //show result
                            runOnUiThread(() -> {
                                File theLocalFile = new File(currentSettings.getOffline_page_url());

                                if (theLocalFile.exists()) {

                                    //   webView.loadUrl(currentSettings.getOffline_page_url());
                                    loadOffline_Saved_Path_Offline_Webview(company, license);

                                } else {

                                    //   webView.loadUrl(currentSettings.getOnline_page_url());
                                    loadMofied_online_url();

                                }
                            });
                        }

                    }).start();

                }

            } else {


                if (theUrl.contains("/Syn2AppLive/")) {

                    File theOfflineFile = new File(theUrl);

                    if (theOfflineFile.exists()) {

                        //  webView.loadUrl(theUrl);
                        loadOffline_schedule(theUrl);

                    } else {

                        File theLocalFile = new File(currentSettings.getOffline_page_url());

                        if (theLocalFile.exists()) {

                            // webView.loadUrl(currentSettings.getOffline_page_url());
                            loadOffline_Saved_Path_Offline_Webview(company, license);

                        } else {


                            showToastMessage("An error of critical state has occurred. You are required to resync your account to get back live");
                        }

                    }

                } else {

                    File theLocalFile = new File(currentSettings.getOffline_page_url());

                    if (theLocalFile.exists()) {

                        //   webView.loadUrl(currentSettings.getOffline_page_url());
                        loadOffline_Saved_Path_Offline_Webview(company, license);

                    } else {

                        showToastMessage("An error of critical state has occurred. You are required to resync your account to get back live");
                    }

                }

            }


        } catch (Exception e) {
        }

    }


    public void stopSchedule(String scheduleId, int position) {

        try {
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);

            String fil_CLO = my_DownloadClass.getString(Constants.getFolderClo, "");
            String fil_DEMO = my_DownloadClass.getString(Constants.getFolderSubpath, "");

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String imgAllowLunchFromOnline = sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "");

            //register type
            isSchedule = false;
            isScheduleRunning = false;

            //load former webpage
            if (isConnected()) {
                if (imgAllowLunchFromOnline.equals(Constants.imgAllowLunchFromOnline)) {
                    loadMofied_online_url();
                } else {

                    loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO);
                }
            } else {
                loadOffline_Saved_Path_Offline_Webview(fil_CLO, fil_DEMO);
            }

            //remove
            theSchedules.remove(position);
            enteredSchedules.remove(scheduleId);

            //cancel schedule state
            isScheduleCurrentlyRunning = false;

        } catch (Exception e) {
        }
    }
    private void Load_Schedule_From_Admin_Panel(String theId, String theUrl, String stopTime, boolean isOneOff, int position) {

        try {

            isSchedule = true;
            isScheduleRunning = true;

            if (theUrl.startsWith("announce.html") || theUrl.startsWith("training.html")) {

                load_Admin_Webview_Schedule(theUrl);

            } else if (theUrl.startsWith("https") || theUrl.startsWith("http")) {

                load_Launch_Online_Mode(theUrl);

            } else if (theUrl.startsWith("/App/") || theUrl.startsWith("App/")) {

                load_Admin_Webview_Schedule_Modified_url(theUrl);

            } else {

                load_Offline_Page_From_Admin(theUrl);
            }

        } catch (
                Exception e) {
        }


    }

    private void load_Admin_Webview_Schedule(String theUrl) {
        try {
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String company = my_DownloadClass.getString(Constants.getFolderClo, "");
            String license = my_DownloadClass.getString(Constants.getFolderSubpath, "");


            String filename = "/" + theUrl;

            String finalFolderPathDesired = "/" + company + "/" + license + "/" + Constants.App;

            String destinationFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Syn2AppLive/" + finalFolderPathDesired;

            String filePath = "file://" + destinationFolder + filename;

            File myFile = new File(destinationFolder, File.separator + filename);

            if (myFile.exists()) {
                loadOffline_schedule(filePath);

            } else {

                  loadOffline_Saved_Path_Offline_Webview(company, license);

            }
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }


    }

    private void load_Offline_Page_From_Admin(String theUrl) {

        try {

            simpleProgressbar.setVisibility(View.GONE);

            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String company = my_DownloadClass.getString(Constants.getFolderClo, "");
            String license = my_DownloadClass.getString(Constants.getFolderSubpath, "");

            String filename = "/" + theUrl;

            String finalFolderPathDesired = "/" + company + "/" + license + "/" + Constants.App;

            String destinationFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Syn2AppLive/" + finalFolderPathDesired;

            String filePath = "file://" + destinationFolder + filename;


            File myFile = new File(destinationFolder, File.separator + filename);

            if (myFile.exists()) {
                webView = findViewById(R.id.webview);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(true);

                webSettings.setAllowFileAccess(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setDomStorageEnabled(false);
                webSettings.setDatabaseEnabled(false);

                webSettings.setMediaPlaybackRequiresUserGesture(false);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadsImagesAutomatically(true);

                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                webView.setWebViewClient(new AdvancedWebViewClient());
                webView.setWebChromeClient(new AdvancedWebChromeClient());
                webView.setDownloadListener(new Downloader());

                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

                WebView.setWebContentsDebuggingEnabled(true);

                webView.loadUrl(filePath);


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String get_imagShowOnlineStatus = sharedBiometric.getString(Constants.imagShowOnlineStatus, "");

                if (!get_imagShowOnlineStatus.equals(Constants.imagShowOnlineStatus)) {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.VISIBLE);
                } else {
                    imageCirclGreenOnline.setVisibility(View.INVISIBLE);
                    imageCircleBlueOffline.setVisibility(View.INVISIBLE);

                }


            } else {


                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String use_offline = sharedBiometric.getString(Constants.USE_OFFLINE_FOLDER, "");
                String get_AppMode = sharedBiometric.getString(Constants.MY_TV_OR_APP_MODE, "");

                String Tapped_OnlineORoffline = my_DownloadClass.getString(Constants.Tapped_OnlineORoffline, "");


                if (use_offline.equals(Constants.USE_OFFLINE_FOLDER) || get_AppMode.equals(Constants.TV_Mode) || Tapped_OnlineORoffline.equals(Constants.tapped_launchOffline)) {
                    showPopForTVConfiguration(Constants.UnableToFindIndex);
                } else {
                    loadTheMainWebview();
                }


            }


        } catch (Exception e) {
        }

    }


    private void load_Admin_Webview_Schedule_Modified_url(String theFullPath) {

        try {
            SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String company = my_DownloadClass.getString(Constants.getFolderClo, "");
            String license = my_DownloadClass.getString(Constants.getFolderSubpath, "");


            String finalFolderPathDesired = "/" + company + "/" + license + "/" + theFullPath;

            String destinationFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Syn2AppLive/" + finalFolderPathDesired;

            String filePath = "file://" + destinationFolder;

            File myFile = new File(destinationFolder);

            if (myFile.exists()) {
                loadOffline_schedule(filePath);

            } else {

                loadOffline_Saved_Path_Offline_Webview(company, license);
            }
        } catch (Exception e) {
        }

    }




    // setup usbcam

    private void checkUsbDevices() {
        try {
            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            boolean usbCameraConnected = false;

            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                usbCameraConnected = true;
                break;
            }

            if (usbCameraConnected) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (isSystemRunning == true) {
                                        inilazeUSBWebCam();
                                    }
                                } catch (Exception exception) {
                                }

                            }
                        });
                    }
                }, 7000);


            } else {
                // showToastmessage("No USB Camera Found");
                textNoCameraAvaliable.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }


    private void inilazeUSBWebCam() {
        inliazeUSbCamVariables();
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void inliazeUSbCamVariables() {
        try {

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imgStreamVideo = sharedBiometric.getString(Constants.imgStreamVideo, "");

            if (get_imgStreamVideo.equals(Constants.imgStreamVideo)) {

                try {
                    toggleInvisibilityAndStopCamera();
                    toggleVisibilityAndCameraStart();

                } catch (Exception e) {
                    showToastMessage(e.getMessage());
                    audioHandler.stopAudio();
                    audioHandler.endAudio();
                }

                reloadWebCam.setOnClickListener(v -> {
                    try {

                        toggleInvisibilityAndStopCamera();
                        toggleVisibilityAndCameraStart();

                    } catch (Exception e) {
                        showToastMessage(e.getMessage());
                        audioHandler.stopAudio();
                        audioHandler.endAudio();

                    }
                });


                closeWebCam.setOnClickListener(view -> {
                    try {
                        toggleInvisibilityAndStopCamera();
                    } catch (Exception e) {
                        Log.e(TAG, "Error stopping camera: " + e.getMessage(), e);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                /// Hide the icons after a while
                try {
                    showCameraIconhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reloadWebCam.setVisibility(View.INVISIBLE);
                            closeWebCam.setVisibility(View.INVISIBLE);
                            expandWebcam.setVisibility(View.INVISIBLE);
                        }
                    }, 10000);

                } catch (Exception e) {
                }


                /// set up motion layout on container
                mlayout.setOnTouchListener((v, event) -> {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            dXo = v.getX() - event.getRawX();
                            dYo = v.getY() - event.getRawY();
                            lastActionXY = MotionEvent.ACTION_DOWN;

                            if (!isShowToastDisplayed) {

                                try {
                                    reloadWebCam.setVisibility(View.VISIBLE);
                                    closeWebCam.setVisibility(View.VISIBLE);
                                    expandWebcam.setVisibility(View.VISIBLE);

                                    if (showCameraIconhandler != null) {
                                        showCameraIconhandler.removeCallbacks(null);
                                    }

                                    isShowToastDisplayed = true;
                                    isHideToastDisplayed = false;
                                } catch (Exception e) {
                                }
                            }

                            break;
                        case MotionEvent.ACTION_MOVE:
                            v.setY(event.getRawY() + dYo);
                            v.setX(event.getRawX() + dXo);
                            lastActionXY = MotionEvent.ACTION_MOVE;


                            if (isShowToastDisplayed && !isHideToastDisplayed) {
                                try {
                                    showCameraIconhandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            reloadWebCam.setVisibility(View.INVISIBLE);
                                            closeWebCam.setVisibility(View.INVISIBLE);
                                            expandWebcam.setVisibility(View.INVISIBLE);
                                        }
                                    }, 10000);

                                    isHideToastDisplayed = true;

                                } catch (Exception e) {
                                }
                            }

                            break;
                        case MotionEvent.ACTION_UP:
                            // Reset flags on touch release to allow the process to repeat
                            isShowToastDisplayed = false;
                            isHideToastDisplayed = false;
                            break;
                        default:
                            return false;
                    }
                    return true;
                });


                String get_imgEnableExpandFloat = sharedBiometric.getString(Constants.imgEnableExpandFloat, "");
                if (!get_imgEnableExpandFloat.equals(Constants.imgEnableExpandFloat)) {
                    // expandWebcam.setVisibility(View.VISIBLE);
                    expandWebcam.setOnTouchListener((v, event) -> {
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                initialWidth = mlayout.getWidth();
                                initialHeight = mlayout.getHeight();
                                dXo = v.getX() - event.getRawX();
                                dYo = v.getY() - event.getRawY();
                                lastActionXY = MotionEvent.ACTION_DOWN;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int newWidth = (int) (event.getRawX() + dXo);
                                int newHeight = (int) (event.getRawY() + dYo);
                                mlayout.getLayoutParams().width = newWidth > 0 ? newWidth : initialWidth;
                                mlayout.getLayoutParams().height = newHeight > 0 ? newHeight : initialHeight;
                                mlayout.requestLayout();
                                lastActionXY = MotionEvent.ACTION_MOVE;
                                break;
                            default:
                                return false;
                        }
                        return true;
                    });

                }


                String get_imgEnableDisplayIntervals = sharedBiometric.getString(Constants.imgEnableDisplayIntervals, "").toString();
                if (get_imgEnableDisplayIntervals.equals(Constants.imgEnableDisplayIntervals)) {
                    start_Display_Timer();
                }

            }


        } catch (Exception e) {
            Log.e(TAG, "Error starting camera: " + e.getMessage(), e);
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleInvisibilityAndStopCamera() {
        cameraHandler.stopCamera();
        audioHandler.stopAudio();
        audioHandler.endAudio();
        mlayout.setVisibility(View.GONE);
    }

    private void toggleVisibilityAndCameraStart() {
        try {

            mlayout.setVisibility(View.VISIBLE);
            mlayout.setAlpha(1);

            if (textureView.isAvailable()) {
                cameraHandler.startCamera();
                textNoCameraAvaliable.setVisibility(View.INVISIBLE);

                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                String get_imgStreamAudioSound = sharedBiometric.getString(Constants.imgStreamAudioSound, "");

                if (!get_imgStreamAudioSound.equals(Constants.imgStreamAudioSound)) {
                    audioHandler.startAudio();
                }

            } else {
                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                    @Override
                    public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture surface, int width, int height) {

                        try {
                            mlayout.setVisibility(View.VISIBLE);
                            cameraHandler.startCamera();
                            textNoCameraAvaliable.setVisibility(View.INVISIBLE);

                            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
                            String get_imgStreamAudioSound = sharedBiometric.getString(Constants.imgStreamAudioSound, "");

                            if (!get_imgStreamAudioSound.equals(Constants.imgStreamAudioSound)) {
                                audioHandler.startAudio();
                            }
                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture surface, int width, int height) {
                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed(android.graphics.SurfaceTexture surface) {
                        return false;
                    }

                    @Override
                    public void onSurfaceTextureUpdated(android.graphics.SurfaceTexture surface) {
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    private void initDisplaySize() {

        try {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            mScreenHeight = displaymetrics.heightPixels;
            mScreenWidth = displaymetrics.widthPixels;
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }

    private void doResizeUSBCameraView() {
        try {
            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imgStreamAPIorDevice = sharedBiometric.getString(Constants.imgStreamAPIorDevice, "");

            if (!get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)) {
                /// for device
                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                String startY = sharedCamera.getString(Constants.startY, "").trim();
                String camHeight = sharedCamera.getString(Constants.camHeight, "").trim();
                String startX = sharedCamera.getString(Constants.startX, "").trim();
                String camWidth = sharedCamera.getString(Constants.camWidth, "").trim();

                pass_Width_heights(startY, camHeight, startX, camWidth);

            } else {
                /// for Api
                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                String startY = sharedCamera.getString(Constants.start_height_api, "").toString().trim();
                String camHeight = sharedCamera.getString(Constants.end_height_api, "").toString().trim();
                String startX = sharedCamera.getString(Constants.start_width_api, "").toString().trim();
                String camWidth = sharedCamera.getString(Constants.end_width_api, "").toString().trim();

                pass_Width_heights(startY, camHeight, startX, camWidth);
            }


        } catch (NumberFormatException e) {
            showToastMessage("Invalid Input Device Width and Height");
        }
    }

    private void pass_Width_heights(String startY, String camHeight, String startX, String camWidth) {
        try {
            int m_camHeight = Integer.parseInt(camHeight);
            int m_camWidth = Integer.parseInt(camWidth);

            if (startY.isEmpty()) {
                startY = "0";
            }

            if (camHeight.isEmpty()) {
                camHeight = "0";
            }
            if (startX.isEmpty()) {
                startX = "0";
            }

            if (camWidth.isEmpty()) {
                camWidth = "0";
            }


            if (camHeight.equals("0") && camWidth.equals("0")) {
                Toast.makeText(this, "Invalid dimension", Toast.LENGTH_SHORT).show();
                return;
            }


            if (m_camWidth <= 9 && m_camHeight <= 9) {
                Toast.makeText(this, "Invalid values", Toast.LENGTH_SHORT).show();
                return;
            }


            double startHeight = Integer.parseInt(startY);
            double startWidth = Integer.parseInt(startX);

            mUSBCameraLeftMargin = (mScreenWidth / 100.0) * startWidth;
            mUSBCameraTopMargin = (mScreenHeight / 100.0) * startHeight;

            mUSBCameraHeight = (mScreenHeight / 100.0) * Integer.parseInt(camHeight);
            mUSBCameraWidth = (mScreenWidth / 100.0) * Integer.parseInt(camWidth);

            // Calculate total dimensions including margins
            double totalWidth = mUSBCameraWidth + mUSBCameraLeftMargin;
            double totalHeight = mUSBCameraHeight + mUSBCameraTopMargin;

            // Check if total dimensions exceed screen dimensions
            if (totalWidth > mScreenWidth) {
                mUSBCameraLeftMargin = 0;
            }
            if (totalHeight > mScreenHeight) {
                mUSBCameraTopMargin = 0;
            }

            // Set new layout parameters for mlayout
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mlayout.getLayoutParams();
            layoutParams.width = (int) mUSBCameraWidth;
            layoutParams.height = (int) mUSBCameraHeight;
            layoutParams.leftMargin = (int) mUSBCameraLeftMargin;
            layoutParams.topMargin = (int) mUSBCameraTopMargin;
            mlayout.setLayoutParams(layoutParams);

        } catch (Exception e) {
        }

    }


    private class UsbBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    //  if (isAudioDevice(device)) {Toast.makeText(context, "Audio device connected", Toast.LENGTH_SHORT).show();}

                                    if (isSystemRunning == true) {
                                        Toast.makeText(context.getApplicationContext(), "USB Camera connected", Toast.LENGTH_SHORT).show();
                                        inilazeUSBWebCam();
                                    }

                                } catch (Exception exception) {
                                }

                            }
                        });
                    }
                }, 4000);


            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //  if (isAudioDevice(device)) {Toast.makeText(context, "Audio device disconnected", Toast.LENGTH_SHORT).show();}

                            if (isSystemRunning == true) {
                                Toast.makeText(context.getApplicationContext(), "USB Camera disconnected", Toast.LENGTH_SHORT).show();
                                toggleInvisibilityAndStopCamera();
                            }
                        } catch (Exception e) {
                        }
                    }
                });

            }
        }

    }



    private IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        return filter;
    }


    private class CameraDisconnectedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();

                if ("sync2app.camera.DISCONNECTED".equals(action)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isSystemRunning == true) {
                                    audioHandler.stopAudio();
                                    audioHandler.endAudio();
                                    textNoCameraAvaliable.setVisibility(View.VISIBLE);
                                    showToastMessage("No cameras available");
                                }

                            } catch (Exception e) {
                            }
                        }
                    });

                }
            } catch (Exception e) {
                showToastMessage(e.getMessage());
            }
        }
    }


    private void start_Display_Timer() {
        try {

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imgStreamAPIorDevice = sharedBiometric.getString(Constants.imgStreamAPIorDevice, "");


            if (!get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)) {

                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                Long d_time = sharedCamera.getLong(Constants.get_Display_Camera_Defined_Time_for_Device, 0L);
                long timeertaker = d_time * 60 * 1000;

                if (timeertaker != 0L) {
                    Camerahandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isSystemRunning == true) {
                                toggleInvisibilityAndStopCamera();
                            }

                            stopTimer();
                            star_Hide_Timer();

                        }
                    }, timeertaker);
                } else {
                    showToastMessage("Invalid Display Camera Time");
                }

            } else {

                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                String display_time_api = sharedCamera.getString(Constants.display_time_api, "").toString().trim();
                Long d_time = Long.valueOf(display_time_api);
                long timeertaker = d_time * 60 * 1000;


                if (timeertaker != 0L) {
                    Camerahandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isSystemRunning == true) {
                                toggleInvisibilityAndStopCamera();
                            }

                            stopTimer();
                            star_Hide_Timer();

                        }
                    }, timeertaker);
                } else {
                    showToastMessage("Invalid Display Camera Time");
                }
            }


        } catch (Exception e) {
        }

    }


    private void star_Hide_Timer() {
        try {

            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);
            String get_imgStreamAPIorDevice = sharedBiometric.getString(Constants.imgStreamAPIorDevice, "");

            if (!get_imgStreamAPIorDevice.equals(Constants.imgStreamAPIorDevice)) {

                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                Long d_time = sharedCamera.getLong(Constants.get_Hide_Camera_Defined_Time_for_Device, 0L);
                long timeertaker = d_time * 60 * 1000;

                if (timeertaker != 0L) {
                    Camerahandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isSystemRunning == true) {
                                try {
                                    toggleInvisibilityAndStopCamera();
                                    toggleVisibilityAndCameraStart();

                                } catch (Exception e) {
                                    showToastMessage(e.getMessage());
                                    audioHandler.stopAudio();
                                    audioHandler.endAudio();
                                }
                            }

                            stopTimer();
                            start_Display_Timer();

                        }
                    }, timeertaker);
                } else {
                    showToastMessage("Invalid Display Camera Time");
                }

            } else {

                SharedPreferences sharedCamera = getSharedPreferences(Constants.SHARED_CAMERA_PREF, Context.MODE_PRIVATE);
                String hide_time_api = sharedCamera.getString(Constants.hide_time_api, "").toString().trim();
                Long d_time = Long.valueOf(hide_time_api);
                long timeertaker = d_time * 60 * 1000;


                if (timeertaker != 0L) {
                    Camerahandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (isSystemRunning == true) {
                                try {
                                    toggleInvisibilityAndStopCamera();
                                    toggleVisibilityAndCameraStart();

                                } catch (Exception e) {
                                    showToastMessage(e.getMessage());
                                    audioHandler.stopAudio();
                                    audioHandler.endAudio();
                                }
                            }

                            stopTimer();
                            start_Display_Timer();

                        }
                    }, timeertaker);
                } else {
                    showToastMessage("Invalid Display Camera Time");
                }
            }


        } catch (Exception e) {
        }


    }

    private void stopTimer() {
        if (Camerahandler != null) {
            Camerahandler.removeCallbacks(null);
        }
    }


    // set up sync intervals

    private void countDownSyncTimmer(final long minutes) {
        long milliseconds = minutes * 60 * 1000;

        initcountDownSyncTimmer = new CountDownTimer(milliseconds, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {

                try {

                    init_APi_Sync_Start();
                    if (initcountDownSyncTimmer != null) {
                        initcountDownSyncTimmer.cancel();
                        countDownSyncTimmer(minutes);
                    }


                } catch (Exception e) {
                }

            }

            @Override
            public void onTick(long millisUntilFinished) {
                try {

                    long totalSecondsRemaining = millisUntilFinished / 1000;
                    long minutesUntilFinished = totalSecondsRemaining / 60;
                    long remainingSeconds = totalSecondsRemaining % 60;

                    // Adjusting minutes if seconds are in the range of 0-59
                    if (remainingSeconds == 0 && minutesUntilFinished > 0) {
                        minutesUntilFinished--;
                        remainingSeconds = 59;
                    }

                    @SuppressLint("DefaultLocale") String displayText = String.format("CD: %d:%02d", minutesUntilFinished, remainingSeconds);
                    countDownTime.setText(displayText);


                } catch (Exception ignored) {
                }
            }
        };
        initcountDownSyncTimmer.start();
    }


    private void init_APi_Sync_Start() {

        try {
            if (isdDownloadApi) {

                SharedPreferences my_DownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
                String get_intervals = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "");

                mFilesViewModel.deleteAllFiles();
                currentDownloadIndex = 0;
                totalFiles = 0;
                progressBarPref.setProgress(0);

                if (get_intervals.equals(Constants.imagSwtichEnableSyncOnFilecahnge)) {
                    textStatusProcess.setText(Constants.PR_running);
                } else {
                    textStatusProcess.setText(Constants.PR_Change_Found);

                }

                SharedPreferences.Editor editior = my_DownloadClass.edit();
                editior.remove(Constants.fileNumber);
                editior.apply();

                if (isConnected()) {
                    startMyCSVApiDownload();
                    showToastMessage("Sync Started");
                } else {
                    showToastMessage("No Internet Connection");
                }


            } else {
                showToastMessage("Sync Already Running");
            }

        } catch (Exception e) {
        }
    };

    private void startMyCSVApiDownload() {
        SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
        SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, Context.MODE_PRIVATE);

        String imagUsemanualOrnotuseManual = sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "");
        String getSavedEditTextInputSynUrlZip = sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "");

        if (Constants.imagSwtichEnableManualOrNot.equals(imagUsemanualOrnotuseManual)) {

            if (getSavedEditTextInputSynUrlZip.contains("/Start/start1.csv") || getSavedEditTextInputSynUrlZip.contains("/Api/update1.csv")) {
                apiInitialization_for_none_manual();

            } else {
                showToastMessage("API not readable from location");
            }

        } else {

            apiInitialization();
        }
    }


    private void seQuenDonload_for_Api_int() {
        try {

            // Initialize the ViewModel
            mFilesViewModel = new ViewModelProvider(this).get(FilesViewModel.class);

            // Initialize Fetch
            initializeFetch();

            // Initialize Listener
            initializeListener();


        } catch (Exception e) {

        }
    }


    private void initializeFetch() {

        try {
            FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                    .enableRetryOnNetworkGain(true)
                    .setAutoRetryMaxAttempts(10)
                    .setDownloadConcurrentLimit(1)
                    .setHttpDownloader(new HttpUrlConnectionDownloader(com.tonyodev.fetch2core.Downloader.FileDownloaderType.SEQUENTIAL))
                    .build();
            fetch = Fetch.Impl.getInstance(fetchConfiguration);
            fetch.setGlobalNetworkType(NetworkType.ALL);

        } catch (Exception e) {
        }
    }


    private void apiInitialization() {
        textDownladByes.setVisibility(View.GONE);

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                    String getFolderClo = sharedP.getString(Constants.getFolderClo, "");
                    String getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "");
                    String get_ModifiedUrl = sharedP.getString(Constants.get_ModifiedUrl, "");
                    String lastEnd = "/Api/update1.csv";

                    CSVDownloader csvDownloader = new CSVDownloader();
                    String csvData = csvDownloader.downloadCSV(get_ModifiedUrl, getFolderClo, getFolderSubpath, lastEnd);
                    saveURLPairs(csvData);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(runnableGetApiStart, 500);
                        }
                    }, 1000);

                }
            }).start();
        } catch (Exception e) {
        }


    }

    private Runnable runnableGetApiStart = new Runnable() {
        @Override
        public void run() {
            mFilesViewModel.getReadAllData().observe(WebActivity.this, new Observer<List<FilesApi>>() {
                @Override
                public void onChanged(List<FilesApi> files) {
                    if (!files.isEmpty()) {
                        handler.postDelayed(() -> runOnUiThread(() -> {
                            textFilecount.setText("DL-/-");
                            totalFiles = files.size();
                            textFilecount.setVisibility(View.VISIBLE);
                            downloadSequentially(files);
                        }), 500);
                    }
                }
            });
        }
    };

    private void downloadSequentially(List<FilesApi> files) {

        try {
            if (currentDownloadIndex < files.size()) {
                FilesApi file = files.get(currentDownloadIndex);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilesDownloads(file.getSN(), file.getFolderName(), file.getFileName());
                    }
                }, 1000);
            }
        } catch (Exception e) {
        }
    }


    private void getFilesDownloads(String sn, String folderName, String fileName) {

        try {
            isdDownloadApi = false;
            iswebViewRefreshingOnApiSync = true;
            SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String getFolderClo = sharedP.getString(Constants.getFolderClo, "");
            String getFolderSubpath = sharedP.getString(Constants.getFolderSubpath, "");
            String get_ModifiedUrl = sharedP.getString(Constants.get_ModifiedUrl, "");


            String Syn2AppLive = Constants.Syn2AppLive;

            String saveMyFileToStorage = "/" + Syn2AppLive + "/" + getFolderClo + "/" + getFolderSubpath + "/" + folderName;
            String url = get_ModifiedUrl + "/" + getFolderClo + "/" + getFolderSubpath + "/" + folderName + "/" + fileName;


            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveMyFileToStorage);

            // delete existing files first
            File myFile = new File(dir, fileName);
            delete(myFile);


            // create folder if not exist
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String file = new File(dir, fileName).getAbsolutePath();

            Request request = new Request(url, file);
            request.setPriority(Priority.HIGH);
            request.setNetworkType(NetworkType.ALL);
            request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

            fetch.enqueue(request, updatedRequest -> {
                // Log.e("onRequest", "Successful ");
            }, error -> {
                Log.e("onRequest", "Error: " + error.toString());
            });

            SharedPreferences.Editor editior = sharedP.edit();
            editior.putInt(Constants.fileNumber, Integer.parseInt(sn));
            editior.apply();

        } catch (Exception e) {
        }

    }


    ///settimg up that of manual Api sync
    private void apiInitialization_for_none_manual() {
        textDownladByes.setVisibility(View.GONE);

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                    String getSavedEditTextInputSynUrlZip = sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "");

                    CSVDownloader csvDownloader = new CSVDownloader();
                    String csvData = csvDownloader.downloadCSV(getSavedEditTextInputSynUrlZip, "", "", "");
                    saveURLPairs(csvData);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(runnableManual, 500);
                        }
                    }, 1000);

                }
            }).start();
        } catch (Exception e) {
        }


    }


    private final Runnable runnableManual = new Runnable() {
        @Override
        public void run() {
            mFilesViewModel.getReadAllData().observe(WebActivity.this, new Observer<List<FilesApi>>() {
                @Override
                public void onChanged(List<FilesApi> files) {
                    if (!files.isEmpty()) {
                        handler.postDelayed(() -> runOnUiThread(() -> {
                            textFilecount.setText("DL-/-");
                            totalFiles = files.size();
                            textFilecount.setVisibility(View.VISIBLE);
                            downloadSequentiallyManually(files);
                        }), 500);
                    }
                }
            });
        }
    };

    private void downloadSequentiallyManually(List<FilesApi> files) {

        try {
            if (currentDownloadIndex < files.size()) {
                FilesApi file = files.get(currentDownloadIndex);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getZipDownloadsManually(file.getSN(), file.getFolderName(), file.getFileName());
                    }
                }, 1000);
            }
        } catch (Exception e) {
        }
    }


    private void getZipDownloadsManually(String sn, String folderName, String fileName) {
        try {
            isdDownloadApi = false;
            iswebViewRefreshingOnApiSync = true;
            String Syn2AppLive = Constants.Syn2AppLive;
            String saveMyFileToStorage = "/" + Syn2AppLive + "/CLO/MANUAL/DEMO/" + folderName;

            SharedPreferences sharedP = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String getSavedEditTextInputSynUrlZip = sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "");

            String replacedUrl = getSavedEditTextInputSynUrlZip;

            if (getSavedEditTextInputSynUrlZip.contains("/Start/start1.csv")) {
                replacedUrl = getSavedEditTextInputSynUrlZip.replace("/Start/start1.csv", "/" + folderName + "/" + fileName);
            } else if (getSavedEditTextInputSynUrlZip.contains("/Api/update1.csv")) {
                replacedUrl = getSavedEditTextInputSynUrlZip.replace("/Api/update1.csv", "/" + folderName + "/" + fileName);
            } else {
                Log.d("getZipDownloadsManually", "Unable to replace this url");
            }

            // delete existing files first
            String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + saveMyFileToStorage;
            File myFile = new File(directoryPath, fileName);
            delete(myFile);

            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveMyFileToStorage);


            // create folder if not exist
            if (!dir.exists()) {
                dir.mkdirs();
            }


            String file = new File(dir, fileName).getAbsolutePath();

            Request request = new Request(replacedUrl, file);
            request.setPriority(Priority.HIGH);
            request.setNetworkType(NetworkType.ALL);
            request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

            fetch.enqueue(request, updatedRequest -> {
                // Log.e("onRequest", "Successful ");
            }, error -> {
                Log.e("onRequest", "Error: " + error.toString());
            });

            SharedPreferences.Editor editior = sharedP.edit();
            editior.putInt(Constants.fileNumber, Integer.parseInt(sn));
            editior.apply();

        } catch (Exception e) {
        }


    }


    public boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        } else if (file.isDirectory()) {
            File[] subFiles = Objects.requireNonNull(file.listFiles());
            for (File subFile : subFiles) {
                if (!delete(subFile)) {
                    return false;
                }
            }
            return file.delete();
        }
        return false;
    }


    private void initializeListener() {

        try {

            FetchListener fetchListener = new FetchListener() {

                @Override
                public void onCompleted(@NotNull Download download) {
                    preLaunchFiles();
                }


                @Override
                public void onWaitingNetwork(@NonNull Download download) {

                }

                @Override
                public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
                    preLaunchFiles();
                    //  showToastMessage("Invalid Url Error Code : " + error.getHttpResponse().getCode());
                    Log.d(TAG, "onError:  An error cocured trying o download from path/url" + +error.getHttpResponse().getCode());

                }

                @Override
                public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

                }

                @Override
                public void onAdded(@NonNull Download download) {

                }

                @Override
                public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                }


                @Override
                public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                }

                @Override
                public void onPaused(@NotNull Download download) {

                }

                @Override
                public void onResumed(@NotNull Download download) {

                }

                @Override
                public void onCancelled(@NotNull Download download) {

                }

                @Override
                public void onRemoved(@NotNull Download download) {

                }

                @Override
                public void onDeleted(@NotNull Download download) {

                }
            };

            fetch.addListener(fetchListener);

        } catch (Exception e) {
        }
    }


    @SuppressLint("SetTextI18n")
    private void preLaunchFiles() {
        try {
            currentDownloadIndex++;

            SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            int sn_number = myDownloadClass.getInt(Constants.fileNumber, 0);


            SharedPreferences sharedBiometric = getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE);
            String get_intervals = sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "");
            String get_Api_state = sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    downloadSequentially(mFilesViewModel.getReadAllData().getValue() != null ? mFilesViewModel.getReadAllData().getValue() : Collections.emptyList());

                    runOnUiThread(() -> {

                        textFilecount.setText(sn_number + " / " + totalFiles);

                        double fileNum = (double) sn_number;
                        int totalPercentage = (int) ((fileNum / (double) totalFiles) * 100);


                        progressBarPref.setProgress(totalPercentage);
                        progressBarPref.setVisibility(View.VISIBLE);

                        textDownladByes.setText(totalPercentage + "%");
                        textDownladByes.setVisibility(View.VISIBLE);

                        textStatusProcess.setText(Constants.PR_Downloading);

                        if (sn_number == totalFiles) {
                            if (iswebViewRefreshingOnApiSync) {
                                iswebViewRefreshingOnApiSync = false;

                                textStatusProcess.setText(Constants.PR_Refresh);

                                handler.postDelayed(() -> {


                                    isdDownloadApi = true;

                                    if (isScheduleRunning) {
                                        showToastMessage("Schedule Media Already Running");

                                        textStatusProcess.setText(Constants.PR_running);
                                        progressBarPref.setProgress(100);
                                        progressBarPref.setVisibility(View.INVISIBLE);
                                        textFilecount.setText(totalFiles + " / " + totalFiles);


                                    } else {
                                        online_Load_Webview_Logic();

                                        textStatusProcess.setText(Constants.PR_running);
                                        progressBarPref.setProgress(100);
                                        progressBarPref.setVisibility(View.INVISIBLE);
                                        textFilecount.setText(totalFiles + " / " + totalFiles);

                                    }


                                }, 3000);

                            }
                        }


                    });
                }
            }, 500);
        } catch (Exception e) {

        }
    }


    private void saveURLPairs(String csvData) {
        try {
            List<String> pairs = parseCSV(csvData);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int index = 0; index < pairs.size(); index++) {
                        String line = pairs.get(index);
                        String[] parts = line.split(",");
                        for (int i = 0; i < parts.length; i++) {
                            parts[i] = parts[i].trim();
                        }
                        if (parts.length < 2) continue; // Skip lines with insufficient data

                        Integer sn = null;
                        try {
                            sn = Integer.parseInt(parts[0]);
                        } catch (NumberFormatException e) {
                            continue; // Skip lines with invalid SN
                        }
                        if (sn == null) continue;

                        String[] folderAndFile = parts[1].split("/");

                        String folderName;
                        if (folderAndFile.length > 1) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < folderAndFile.length - 1; i++) {
                                if (i > 0) sb.append("/");
                                sb.append(folderAndFile[i]);
                            }
                            folderName = sb.toString();
                        } else {
                            folderName = "MyApiFolder"; // Assuming default folder name
                        }

                        String fileName = folderAndFile[folderAndFile.length - 1];
                        if (fileName.isEmpty()) continue; // Skip lines with missing file name

                        String status = "true"; // Set your status here
                        Long id = System.currentTimeMillis();
                        FilesApi files = new FilesApi(id, sn.toString(), folderName, fileName, status);

                        mFilesViewModel.addFiles(files);

                    }
                }
            }).start();
        } catch (Exception e) {
        }
    }


    private List<String> parseCSV(String csvData) {
        List<String> pairs = new ArrayList<>();
        String[] lines = csvData.split("\n");
        for (String line : lines) {
            if (!line.isEmpty()) {
                pairs.add(line.trim());
            }
        }
        return pairs;
    }


    private void showToastMessage(String message) {
        try {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isSystemRunning) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception e) {
        }

    }


/// Add Api sync using Index change and Time stamp change
/// Add Api sync using Index change and Time stamp change


    private void start_Index_MyCSVApiDownload() {

        try {
            SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
            String getFolderClo = myDownloadClass.getString(Constants.getFolderClo, "");
            String getFolderSubpath = myDownloadClass.getString(Constants.getFolderSubpath, "");
            String fileName = myDownloadClass.getString(Constants.fileName, "");
            String baseUrl = myDownloadClass.getString(Constants.baseUrl, "");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (isConnected()) {
                        if (!baseUrl.isEmpty() && !getFolderClo.isEmpty() && !getFolderSubpath.isEmpty() && !fileName.isEmpty()) {

                            init_countdown_Index_Time();

                        } else {

                            showToastMessage("Invalid Path Format");
                        }

                    } else {
                        showToastMessage("No internet Connection");
                    }


                }
            }, 500);

        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }

    }

    private void init_countdown_Index_Time() {

        try {
            try {
                if (isConnected()) {
                    SharedPreferences myDownloadClass = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE);
                    long getTimeDefined = myDownloadClass.getLong(Constants.getTimeDefined, 0);

                    if (getTimeDefined != 0L) {
                        count_Inedx_DownSyncTimmer(getTimeDefined);

                    } else {
                        showToastMessage("No time found");
                    }

                } else {
                    showToastMessage("No internet Connection");
                }

            } catch (Exception e) {
                showToastMessage(e.getMessage());
            }
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }

    private void makeAPIRequest() {

        init_APi_Sync_Start();

    }


    private void count_Inedx_DownSyncTimmer(final long minutes) {

        try {
            long milliseconds = minutes * 60 * 1000;

            initcount_Index_DownSyncTimmer = new CountDownTimer(milliseconds, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onFinish() {

                    try {
                        if (initcount_Index_DownSyncTimmer != null) {
                            initcount_Index_DownSyncTimmer.cancel();
                            count_Inedx_DownSyncTimmer(minutes);
                        }


                    } catch (Exception e) {
                    }

                }

                @Override
                public void onTick(long millisUntilFinished) {
                    try {

                        long totalSecondsRemaining = millisUntilFinished / 1000;
                        long minutesUntilFinished = totalSecondsRemaining / 60;
                        long remainingSeconds = totalSecondsRemaining % 60;

                        // Adjusting minutes if seconds are in the range of 0-59
                        if (remainingSeconds == 0 && minutesUntilFinished > 0) {
                            minutesUntilFinished--;
                            remainingSeconds = 59;
                        }

                        @SuppressLint("DefaultLocale") String displayText = String.format("CD: %d:%02d", minutesUntilFinished, remainingSeconds);
                        countDownTime.setText(displayText);


                    } catch (Exception ignored) {
                    }
                }
            };
            initcount_Index_DownSyncTimmer.start();
        } catch (Exception e) {
            showToastMessage(e.getMessage());
        }
    }


}
*/
