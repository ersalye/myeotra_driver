package com.myeotra.driver.ui.activity.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.myeotra.driver.BuildConfig;
import com.myeotra.driver.MvpApplication;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.ChatHeadService;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.GPSTracker;
import com.myeotra.driver.common.LocaleHelper;
import com.myeotra.driver.common.PolyUtil;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.common.Utilities;
import com.myeotra.driver.common.chat.ChatActivity;
import com.myeotra.driver.common.fcm.MyFireBaseMessagingService;
import com.myeotra.driver.common.swipe_button.SwipeButton;
import com.myeotra.driver.data.network.model.LatLngFireBaseDB;
import com.myeotra.driver.data.network.model.SettingsResponse;
import com.myeotra.driver.data.network.model.TripResponse;
import com.myeotra.driver.data.network.model.UserResponse;
import com.myeotra.driver.ui.activity.add_card.AddCardActivity;
import com.myeotra.driver.ui.activity.card.CardActivity;
import com.myeotra.driver.ui.activity.document.DocumentActivity;
import com.myeotra.driver.ui.activity.earnings.EarningsActivity;
import com.myeotra.driver.ui.activity.help.HelpActivity;
import com.myeotra.driver.ui.activity.instant_ride.InstantRideActivity;
import com.myeotra.driver.ui.activity.notification_manager.NotificationManagerActivity;
import com.myeotra.driver.ui.activity.profile.ProfileActivity;
import com.myeotra.driver.ui.activity.setting.SettingsActivity;
import com.myeotra.driver.ui.activity.summary.SummaryActivity;
import com.myeotra.driver.ui.activity.wallet.WalletActivity;
import com.myeotra.driver.ui.activity.your_trips.YourTripActivity;
import com.myeotra.driver.ui.bottomsheetdialog.invoice_flow.InvoiceDialogFragment;
import com.myeotra.driver.ui.bottomsheetdialog.rating.RatingDialogFragment;
import com.myeotra.driver.ui.fragment.incoming_request.IncomingRequestFragment;
import com.myeotra.driver.ui.fragment.offline.OfflineFragment;
import com.myeotra.driver.ui.fragment.status_flow.StatusFlowFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.myeotra.driver.MvpApplication.DATUM;
import static com.myeotra.driver.MvpApplication.DEFAULT_ZOOM;
import static com.myeotra.driver.MvpApplication.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.myeotra.driver.MvpApplication.canGoToChatScreen;
import static com.myeotra.driver.MvpApplication.isChatScreenOpen;
import static com.myeotra.driver.MvpApplication.mLastKnownLocation;
import static com.myeotra.driver.common.Constants.checkStatus.ARRIVED;
import static com.myeotra.driver.common.Constants.checkStatus.EMPTY;
import static com.myeotra.driver.common.Constants.checkStatus.PICKEDUP;
import static com.myeotra.driver.common.Constants.checkStatus.STARTED;

public class MainActivity extends BaseActivity implements
        MainIView,
        OnMapReadyCallback,
        LocationListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        DirectionCallback {

    private static final int APP_PERMISSION_REQUEST = 102;
    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 104;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.lnrLocationHeader)
    LinearLayout lnrLocationHeader;
    @BindView(R.id.lblLocationType)
    TextView lblLocationType;
    @BindView(R.id.lblLocationName)
    TextView lblLocationName;
    @BindView(R.id.offline_container)
    FrameLayout offlineContainer;


    @BindView(R.id.rlViewProfile)
    RelativeLayout rlViewProfile;

    @BindView(R.id.gps)
    ImageView gps;
    @BindView(R.id.navigation_img)
    ImageView navigationImg;
    @BindView(R.id.sbChangeStatus)
    SwipeButton sbChangeStatus;
    @BindView(R.id.lblMenuName)
    TextView lblMenuName;
    @BindView(R.id.lblMenuEmail)
    TextView lblMenuEmail;
    ImageView imgMenu, imgStatus;
    MainPresenter presenter = new MainPresenter();
    SupportMapFragment mapFragment;
    GoogleMap googleMap;


    @BindView(R.id.llyourtrips)
    LinearLayout llyourtrips;
    @BindView(R.id.llearning)
    LinearLayout llearning;
    @BindView(R.id.llinstant_ride)
    LinearLayout llinstant_ride;
    @BindView(R.id.llsummary)
    LinearLayout llsummary;
    @BindView(R.id.llwallet)
    LinearLayout llwallet;
    @BindView(R.id.llcard)
    LinearLayout llcard;
    @BindView(R.id.llsettings)
    LinearLayout llsettings;
    @BindView(R.id.llnotificationmanager)
    LinearLayout llnotificationmanager;
    @BindView(R.id.llhelp)
    LinearLayout llhelp;
    @BindView(R.id.llshare)
    LinearLayout llshare;
    @BindView(R.id.lllogout)
    LinearLayout lllogout;

    @BindView(R.id.llprivacypilicy)
    LinearLayout llprivacypilicy;

    @BindView(R.id.picture)
    ImageView picture;


    private Runnable r;
    private Handler h;
    private int delay = 5000;
    private String STATUS = "";
    private String CURRENT_DEST_ADDRESS = "";
    private String ACCOUNTSTATUS = "";
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocation;
    private BottomSheetBehavior bottomSheetBehavior;
    private Intent gpsServiceIntent;
    //    private Intent floatingWidgetIntent;
    private DatabaseReference mProviderLocation;
    private ArrayList<LatLng> polyLinePoints;
    private Polyline mPolyline;
    private boolean canReRoute = true, canCarAnim = true;
    private LatLng start = null, end = null;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HashMap<String, Object> params = new HashMap<>();
            if (mLastKnownLocation != null) {
                params.put("latitude", mLastKnownLocation.getLatitude());
                params.put("longitude", mLastKnownLocation.getLongitude());
            }
            presenter.getTrip(params);
        }
    };
    private int canMapAnimate;
    private Marker srcMarker;
    private String TAG = "AAAA";

    private static void startFloatingViewService(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 1. 'windowLayoutInDisplayCutoutMode' do not be set to 'never'
            if (activity.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                throw new RuntimeException("'windowLayoutInDisplayCutoutMode' do not be set to 'never'");
            }
            // 2. Do not set Activity to landscape
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                throw new RuntimeException("Do not set Activity to landscape");
            }
        }

        final Class<? extends Service> service;
        service = ChatHeadService.class;

        final Intent intent = new Intent(activity, service);
        //intent.putExtra(ChatHeadService.EXTRA_CUTOUT_SAFE_AREA, FloatingViewManager.findCutoutSafeArea(activity));
        ContextCompat.startForegroundService(activity, intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        presenter.attachView(this);
        registerReceiver(myReceiver, new IntentFilter(MyFireBaseMessagingService.INTENT_FILTER));


        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(container);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Un used
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Un used
            }
        });

        String profileUrl = getIntent().getStringExtra("avartar");
        if (profileUrl != null && !profileUrl.isEmpty())
            Glide.with(activity())
                    .load(profileUrl)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.ic_user_placeholder)
                            .dontAnimate()
                            .error(R.drawable.ic_user_placeholder))
                    .into(picture);

        sbChangeStatus.setOnStateChangeListener(active -> {
            if (active) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("service_status", "offline");
                presenter.providerAvailable(map);
            }
        });

        /*if (floatingWidgetIntent == null)
            floatingWidgetIntent = new Intent(MainActivity.this, FloatWidgetService.class);*/

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                presenter.getSettings();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        showFloatingView(activity(), true);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else {
                    UserResponse user = new Gson().fromJson(SharedHelper.getKey(getApplicationContext(), "userInfo"), UserResponse.class);
                    if (user != null) {
                        SharedHelper.putKey(getApplicationContext(), Constants.SharedPref.CURRENCY, user.getCurrency());
                        Constants.Currency = SharedHelper.getKey(getApplicationContext(), Constants.SharedPref.CURRENCY);

                        lblMenuName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
                        lblMenuEmail.setText(user.getEmail());
                        SharedHelper.putKey(activity(), Constants.SharedPref.PICTURE, user.getAvatar());
                        Glide.with(activity())
                                .load(BuildConfig.BASE_IMAGE_URL + user.getAvatar())
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder)
                                        .dontAnimate()
                                        .error(R.drawable.ic_user_placeholder))
                                .into(picture);
                    } else presenter.getProfile();
                    drawerLayout.openDrawer(Gravity.START);
                }

            }
        });


    }

    @SuppressLint("NewApi")
    private void showFloatingView(Context context, boolean isShowOverlayPermission) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService(activity());
            return;
        }

        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService(activity());
            return;
        }

        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    private void startCheckStatusCall() {
        try {
            h = new Handler();
            r = () -> {
                if (mLastKnownLocation != null) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("latitude", mLastKnownLocation.getLatitude());
                    params.put("longitude", mLastKnownLocation.getLongitude());
                    presenter.getTrip(params);
                } else if (DATUM.getStatus().equals(STARTED) || DATUM.getStatus().equals(ARRIVED)
                        || DATUM.getStatus().equals(PICKEDUP)) if (canMapAnimate % 3 == 0) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng latLng : polyLinePoints) builder.include(latLng);
                    LatLngBounds bounds = builder.build();
                    try {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));
                    } catch (Exception e) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 90));

                        Log.e(TAG, "startCheckStatusCall error: " + e.getMessage());
                    }
                }
                canMapAnimate++;
                h.postDelayed(r, delay);
            };
            h.postDelayed(r, delay);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "startCheckStatusCall: error" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_PERMISSION_REQUEST && resultCode == RESULT_OK) openMap();
        else
            Toast.makeText(this, "Draw over other app permission not enable.", Toast.LENGTH_SHORT).show();

        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(activity(), false);
        }
    }

    private void openMap() {
        /*try {
            startService(floatingWidgetIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        if (getString(R.string.pick_up_location).equalsIgnoreCase(lblLocationType.getText().toString())) {
            if (DATUM.getSAddress() != null && !DATUM.getSAddress().isEmpty()) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + DATUM.getSAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        } else if (DATUM.getDAddress() != null && !DATUM.getDAddress().isEmpty()) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + DATUM.getDAddress());

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ACCOUNTSTATUS = "";
        STATUS = "";

//        gpsServiceIntent = new Intent(this, GPSTracker.class);
//        startService(gpsServiceIntent);

        presenter.getProfile();
        HashMap<String, Object> params = new HashMap<>();
        if (mLastKnownLocation != null) {


            params.put("latitude", mLastKnownLocation.getLatitude());
            params.put("longitude", mLastKnownLocation.getLongitude());


            Log.e(TAG, "onResume: latitude :" + mLastKnownLocation.getLatitude() + " longitide : " + mLastKnownLocation.getLongitude());
            SharedHelper.putKey(this, Constants.SharedPref.LATITUDE, String.valueOf(mLastKnownLocation.getLatitude()));
            SharedHelper.putKey(this, Constants.SharedPref.LONGITUDE, String.valueOf(mLastKnownLocation.getLongitude()));
        } else {
            params.put("latitude", 23.022505);
            params.put("longitude", 72.5713621);
            SharedHelper.putKey(this, Constants.SharedPref.LATITUDE, String.valueOf(23.022505));
            SharedHelper.putKey(this, Constants.SharedPref.LONGITUDE, String.valueOf(72.5713621));
        }
        presenter.getTrip(params);
        registerReceiver(myReceiver, new IntentFilter(MyFireBaseMessagingService.INTENT_FILTER));
//        startCheckStatusCall();

        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
//        h.removeCallbacks(r);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*if (floatingWidgetIntent != null)
            stopService(floatingWidgetIntent);*/
        // if (gpsServiceIntent != null) stopService(gpsServiceIntent);
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) activity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (GPSTracker.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onCameraIdle() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onCameraMove() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        } catch (Resources.NotFoundException e) {

            Log.e(TAG, "onMapReady: error" + e.getMessage());
        }

        this.googleMap = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        boolean serviceRunningStatus = isServiceRunning();

        if (serviceRunningStatus) {
            Intent serviceIntent = new Intent(this, GPSTracker.class);
            stopService(serviceIntent);
        }
        if (!serviceRunningStatus) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                activity().startService(new Intent(activity(), GPSTracker.class));
            } else {
                Intent serviceIntent = new Intent(activity(), GPSTracker.class);
                ContextCompat.startForegroundService(activity(), serviceIntent);
            }
        }
    }

    @OnClick({R.id.menu, R.id.nav_view, R.id.navigation_img, R.id.gps, R.id.llyourtrips, R.id.llearning, R.id.llinstant_ride, R.id.llsummary, R.id.llwallet, R.id.llcard, R.id.llsettings, R.id.llnotificationmanager, R.id.llhelp, R.id.llshare, R.id.lllogout, R.id.rlViewProfile, R.id.llprivacypilicy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.nav_view:
                break;

            case R.id.rlViewProfile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.gps:
                if (mLastKnownLocation != null) {
                    LatLng currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
                }
                break;
            case R.id.navigation_img:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this))
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName())), APP_PERMISSION_REQUEST);
                else openMap();
                break;

            case R.id.llyourtrips:
                startActivity(new Intent(this, YourTripActivity.class));
                break;

            case R.id.llearning:
                startActivity(new Intent(this, EarningsActivity.class));
                break;

            case R.id.llinstant_ride:
                startActivity(new Intent(this, InstantRideActivity.class));
                break;

            case R.id.llsummary:
                startActivity(new Intent(this, SummaryActivity.class));
                break;

            case R.id.llwallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;

            case R.id.llcard:
                startActivity(new Intent(this, CardActivity.class));
                break;

            case R.id.llsettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("setting", "isClick");
                startActivity(intent);
                break;

            case R.id.llnotificationmanager:
                startActivity(new Intent(this, NotificationManagerActivity.class));
                break;

            case R.id.llhelp:

                startActivity(new Intent(this, HelpActivity.class));
                break;

            case R.id.llshare:
                navigateToShareScreen();
                break;

            case R.id.llprivacypilicy:
                break;

            case R.id.lllogout:
                ShowLogoutPopUp();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) mLocationPermissionGranted = true;
        else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void updateLocationUI() {
        if (googleMap == null) return;
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.setOnCameraMoveListener(this);
                googleMap.setOnCameraIdleListener(this);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());

            Log.e(TAG, "updateLocationUI: " + e.getMessage());
        }
    }

    void getDeviceLocation() {

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocation.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();

                        googleMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location))
                                .position(new LatLng(
                                        mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()
                                )));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                        mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()),
                                DEFAULT_ZOOM));
                    } else {
                        Log.e("Map", "Current location is null. Using defaults.: %s", task.getException());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();

            Log.e(TAG, "getDeviceLocation: error" + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    updateLocationUI();
                    getDeviceLocation();
                }
            }
        }
    }

    private void changeFragment(Fragment fragment) {

        if (isFinishing()) return;

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, fragment.getTag());
            transaction.commitAllowingStateLoss();
            sbChangeStatus.setVisibility(View.GONE);
        } else {
            if (IncomingRequestFragment.countDownTimer != null) {
                IncomingRequestFragment.countDownTimer.cancel();
                if (IncomingRequestFragment.mPlayer.isPlaying())
                    IncomingRequestFragment.mPlayer.stop();
            }
            container.removeAllViews();
            sbChangeStatus.collapseButton();
            sbChangeStatus.setVisibility(View.VISIBLE);
            lnrLocationHeader.setVisibility(View.GONE);
            googleMap.clear();
        }
    }

    private void offlineFragment(String s) {
        Fragment fragment = new OfflineFragment();
        Bundle b = new Bundle();
        b.putString("status", s);
        fragment.setArguments(b);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.offline_container, fragment, fragment.getTag());
        transaction.commitAllowingStateLoss();
        ACCOUNTSTATUS = "";
    }

    @Override
    public void onSuccess(SettingsResponse response) {

    }

    @Override
    public void onSettingError(Throwable e) {

    }


    @Override
    public void onSuccess(UserResponse user) {

        Log.e(TAG, "getProfile onSuccess: " + new Gson().toJson(user));
        if (user != null) {
            String dd = LocaleHelper.getLanguage(this);
            if (user.getProfile() != null && user.getProfile().getLanguage() != null &&
                    !user.getProfile().getLanguage().equalsIgnoreCase(dd)) {
                LocaleHelper.setLocale(this, user.getProfile().getLanguage());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            lblMenuName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
            lblMenuEmail.setText(user.getEmail());
            SharedHelper.putKey(activity(), Constants.SharedPref.PICTURE, user.getAvatar());
            Glide.with(activity())
                    .load(BuildConfig.BASE_IMAGE_URL + user.getAvatar())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder)
                            .dontAnimate()
                            .error(R.drawable.ic_user_placeholder))
                    .into(picture);
            SharedHelper.putKey(this, Constants.SharedPref.STRIPE_PUBLISHABLE_KEY, user.getStripePublishableKey());
            SharedHelper.putKey(this, Constants.SharedPref.USER_ID, String.valueOf(user.getId()));
            SharedHelper.putKey(this, Constants.SharedPref.USER_NAME, user.getFirstName()
                    + " " + user.getLastName());

            SharedHelper.putKey(this, "Username", user.getFirstName()
                    + " " + user.getLastName());
            SharedHelper.putKey(this, Constants.SharedPref.USER_AVATAR, BuildConfig.BASE_IMAGE_URL + user.getAvatar());
            SharedHelper.putKey(this, Constants.SharedPref.CURRENCY, user.getCurrency());
            SharedHelper.putKey(this, Constants.SharedPref.SERVICE_TYPE, user.getService().getServiceType().getId());
            SharedHelper.putKey(this, Constants.SharedPref.USER_INFO, printJSON(user));
            Constants.Currency = SharedHelper.getKey(this, Constants.SharedPref.CURRENCY);
//            Constants.showOTP = user.getRide_otp().equals("1");
            Constants.showOTP = user.getRideOtp().equals("1");
            int card = user.getCard();
            /*if (card == 0) {
                Menu nav_Menu = navView.getMenu();
                nav_Menu.findItem(R.id.nav_card).setVisible(false);
            } else {
                Menu nav_Menu = navView.getMenu();
                nav_Menu.findItem(R.id.nav_card).setVisible(true);
            }*/
            SharedHelper.putKey(this, "card", card);

//            SharedHelper.putKey(this, Constants.ReferalKey.REFERRAL_CODE, user.getReferral_unique_id());
//            SharedHelper.putKey(this, Constants.ReferalKey.REFERRAL_COUNT, user.getReferral_count());
//            SharedHelper.putKey(this, Constants.ReferalKey.REFERRAL_TEXT, user.getReferral_text());
//            SharedHelper.putKey(this, Constants.ReferalKey.REFERRAL_TOTAL_TEXT, user.getReferral_total_text());
        }
    }


    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();
        if (e != null)
            onErrorBase(e);
        Log.e(TAG, "onError main: " + e.getMessage());
    }


    @Override
    public void onSuccessLogout(Object object) {
        Utilities.LogoutApp(activity(), "");
    }

    @Override
    public void onSuccess(TripResponse response) {

        Log.e("data", "TripResponse onSuccess: " + new Gson().toJson(response));


//        Log.e(TAG, "payable : " + response.getRequests().get(0).getPayment().getPayable());
        String accountStatus = response.getAccountStatus();
        String serviceStatus = response.getServiceStatus();

        MvpApplication.tripResponse = response;

        if (!ACCOUNTSTATUS.equalsIgnoreCase(accountStatus)) {
            ACCOUNTSTATUS = accountStatus;
            if (accountStatus.equalsIgnoreCase(Constants.User.Account.PENDING_DOCUMENT)) {
                startActivity(new Intent(MainActivity.this, DocumentActivity.class));
//                imgStatus.setImageResource(R.drawable.banner_waiting);
            } else if (accountStatus.equalsIgnoreCase(Constants.User.Account.PENDING_CARD)) {
                startActivity(new Intent(MainActivity.this, AddCardActivity.class));
//                imgStatus.setImageResource(R.drawable.banner_waiting);
            } else if (accountStatus.equalsIgnoreCase(Constants.User.Account.ONBOARDING)) {
                offlineFragment(Constants.User.Account.ONBOARDING);
//                imgStatus.setImageResource(R.drawable.banner_waiting);
            } else if (Constants.User.Account.BANNED.equalsIgnoreCase(accountStatus)) {
                offlineFragment(Constants.User.Account.BANNED);
//                imgStatus.setImageResource(R.drawable.banner_banned);
            } else if (Constants.User.Account.APPROVED.equalsIgnoreCase(accountStatus)
                    && Constants.User.Service.OFFLINE.equalsIgnoreCase(serviceStatus)) {
                offlineFragment(Constants.User.Service.OFFLINE);
//                imgStatus.setImageResource(R.drawable.banner_active);
            } else if (Constants.User.Account.APPROVED.equalsIgnoreCase(accountStatus)
                    && Constants.User.Service.ACTIVE.equalsIgnoreCase(serviceStatus)) {
                offlineContainer.removeAllViews();
//                imgStatus.setImageResource(R.drawable.banner_active);
            } else if (Constants.User.Account.BALANCE.equalsIgnoreCase(accountStatus)
                    || Constants.User.Service.BALANCE.equalsIgnoreCase(serviceStatus)) {
                offlineFragment(Constants.User.Service.BALANCE);
//                imgStatus.setImageResource(R.drawable.banner_active);
            }
        }

        if (response.getRequests() != null && response.getRequests().size() == 0) {

            Log.e("data", "getRequests empty: ");
            CURRENT_DEST_ADDRESS = "";
            googleMap.clear();
            getDeviceLocation();
            changeFlow(Constants.checkStatus.EMPTY);
        } else {

            Log.e("data", "getRequests not empty: ");
            MvpApplication.time_to_left = response.getRequests().get(0).getTimeLeftToRespond();
            DATUM = response.getRequests().get(0).getRequest();
            Log.e("data", "DATUM" + new Gson().toJson(DATUM));
            Log.e("data", "DATUM getStatus" + DATUM.getStatus());

            changeFlow(DATUM.getStatus());

        }

        if (canGoToChatScreen) {
            if (!isChatScreenOpen && DATUM != null) {
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                i.putExtra(Constants.SharedPref.REQUEST_ID, String.valueOf(DATUM.getId()));
                startActivity(i);
            }
            canGoToChatScreen = false;
        }

    }

    @Override
    public void onSuccessProviderAvailable(Object object) {
        offlineFragment("");
        sbChangeStatus.toggleState();
    }

    @Override
    public void onSuccessFCM(Object object) {
        Utilities.printV("onSuccessFCM", "onSuccessFCM");
    }

    @Override
    public void onSuccessLocationUpdate(TripResponse tripResponse) {

        Log.e(TAG, "onSuccessLocationUpdate:" + new Gson().toJson(tripResponse));

    }

    @SuppressLint("StringFormatInvalid")
    public void changeFlow(String status) {

        System.out.println("RRR status = [" + status + "]");


        if (!DATUM.getDAddress().equalsIgnoreCase(CURRENT_DEST_ADDRESS)
                && STATUS.equalsIgnoreCase(Constants.checkStatus.PICKEDUP)
                && status.equalsIgnoreCase(Constants.checkStatus.PICKEDUP))
            showDestinationAlert(String.format(getString(R.string.destination_change_to), DATUM.getDAddress()));

        switch (status) {
            case Constants.checkStatus.ACCEPTED:
            case STARTED:
            case ARRIVED:
                /*String refPath = "loc_p_" + DATUM.getProviderId();
                if (!refPath.equals("loc_p_0") && mProviderLocation == null) {
                    mProviderLocation = FirebaseDatabase.getInstance().getReference()
                            .child("loc_p_" + DATUM.getProviderId());
                    updateDriverNavigation();
                }*/
                break;
            case Constants.checkStatus.PICKEDUP:
                String refPath = "loc_p_" + DATUM.getProviderId();
                if (!refPath.equals("loc_p_0") && mProviderLocation == null) {
                    mProviderLocation = FirebaseDatabase.getInstance().getReference()
                            .child("loc_p_" + DATUM.getProviderId());
                    updateDriverNavigation();
                }
                break;
            case Constants.checkStatus.DROPPED:
            case Constants.checkStatus.COMPLETED:
                mProviderLocation = null;
                break;
            default:
                mProviderLocation = null;
                break;
        }

        if (!STATUS.equalsIgnoreCase(status)) {
            STATUS = status;
//            if (DATUM.getDAddress() != null)
            if (DATUM != null)
                CURRENT_DEST_ADDRESS = DATUM.getDAddress();
            lblLocationType.setText(getString(R.string.pick_up_location));
            if (DATUM != null && DATUM.getSAddress() != null && !DATUM.getSAddress().isEmpty())
                lblLocationName.setText(DATUM.getSAddress());
//            if (DATUM.getDAddress() != null)
            CURRENT_DEST_ADDRESS = DATUM.getDAddress();
            switch (status) {
                case EMPTY:
                    mProviderLocation = null;
                    changeFragment(null);
                    break;
                case Constants.checkStatus.SEARCHING:
                    changeFragment(new IncomingRequestFragment());
                    break;
                case Constants.checkStatus.ACCEPTED:
                    lnrLocationHeader.setVisibility(View.VISIBLE);
                    changeFragment(new StatusFlowFragment());
                    break;
                case STARTED:
                    lnrLocationHeader.setVisibility(View.VISIBLE);
                    changeFragment(new StatusFlowFragment());
                    break;
                case ARRIVED:
                    googleMap.clear();
                    lblLocationType.setText(getString(R.string.drop_off_location));
                    lblLocationName.setText(DATUM.getDAddress());
                    lnrLocationHeader.setVisibility(View.VISIBLE);
                    changeFragment(new StatusFlowFragment());
                    break;
                case Constants.checkStatus.PICKEDUP:
                    lblLocationType.setText(getString(R.string.drop_off_location));
                    lblLocationName.setText(DATUM.getDAddress());
                    lnrLocationHeader.setVisibility(View.VISIBLE);
                    changeFragment(new StatusFlowFragment());
                    break;
                case Constants.checkStatus.DROPPED:
                    lblLocationType.setText(getString(R.string.drop_off_location));
                    lblLocationName.setText(DATUM.getDAddress());
                    changeFragment(new InvoiceDialogFragment());
                    break;
                case Constants.checkStatus.COMPLETED:
                    lblLocationType.setText(getString(R.string.drop_off_location));
                    lblLocationName.setText(DATUM.getDAddress());
                    changeFragment(new RatingDialogFragment());
                    break;
                default:
                    break;
            }
        } else {

//            Log.e(TAG, "changeFlow: " + hasOpenedDialogs());

            System.out.println("Opened Dialogs ==> " + hasOpenedDialogs());
        }
    }

    private void updateDriverNavigation() {
        Log.e(TAG, "updateDriverNavigation: ");
        mProviderLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    LatLngFireBaseDB fbData = dataSnapshot.getValue(LatLngFireBaseDB.class);
                    assert fbData != null;
                    double lat = fbData.getLat();
                    double lng = fbData.getLng();

                    System.out.println("RRR Lat: " + lat + " Lng: " + lng);
                    if (lng > 0 && lat > 0)
                        if (STARTED.equalsIgnoreCase(DATUM.getStatus()) ||
                                ARRIVED.equalsIgnoreCase(DATUM.getStatus()) ||
                                PICKEDUP.equalsIgnoreCase(DATUM.getStatus())) {
                            LatLng source = null, destination = null;
                            switch (DATUM.getStatus()) {
                                case STARTED:
                                    source = new LatLng(lat, lng);
                                    destination = new LatLng(DATUM.getSLatitude(), DATUM.getSLongitude());
                                    break;
                                case ARRIVED:
                                case PICKEDUP:
                                    source = new LatLng(lat, lng);
                                    destination = new LatLng(DATUM.getDLatitude(), DATUM.getDLongitude());
                                    break;
                            }
                            if (polyLinePoints == null || polyLinePoints.size() < 2 || mPolyline == null)
                                drawRoute(source, destination);
                            else {
                                int index = checkForReRoute(source);
                                if (index < 0) reRoutingDelay(source, destination);
                                else polyLineRerouting(source, index);
                            }

                            if (start != null) {
                                SharedHelper.putCurrentLocation(MainActivity.this, start);
                                end = start;
                            }
                            start = source;
                            if (end != null && canCarAnim) {
                                /*if (start != null && (start.latitude != end.latitude ||
                                        start.longitude != end.longitude))*/
//                                    carAnim(srcMarker, end, start);
                            }
                        } else mProviderLocation = null;

                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e(TAG, "onDataChange error: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("RRR ", "Failed to read value.", error.toException());
            }
        });
    }

    private void reRoutingDelay(LatLng source, LatLng destination) {
        if (canReRoute) {
            canReRoute = !canReRoute;
            drawRoute(source, destination);
            new Handler().postDelayed(() -> canReRoute = true, 8000);
        }
    }

    private void polyLineRerouting(LatLng point, int index) {
        if (index > 0) {
            polyLinePoints.subList(0, index + 1).clear();
            polyLinePoints.add(0, point);
            mPolyline.remove();

            mPolyline = googleMap.addPolyline(DirectionConverter.createPolyline
                    (this, polyLinePoints, 2, getResources().getColor(R.color.colorAccent)));

            System.out.println("RRR mPolyline = " + polyLinePoints.size());
        } else System.out.println("RRR mPolyline = Failed");
    }

    private int checkForReRoute(LatLng point) {
        if (polyLinePoints != null && polyLinePoints.size() > 0) {
            System.out.println("RRR indexOnEdgeOrPath = " +
                    new PolyUtil().indexOnEdgeOrPath(point, polyLinePoints, false, true, 100));
            //      indexOnEdgeOrPath returns -1 if the point is outside the polyline
            //      returns the index position if the point is inside the polyline
            return new PolyUtil().indexOnEdgeOrPath(point, polyLinePoints, false, true, 100);
        } else return -1;
    }

    public boolean hasOpenedDialogs() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments)
            if (fragment != null && fragment.isVisible()) return true;
        return false;
    }

    private void showDestinationAlert(String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.ride_updated))
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), (dialog, id) -> updateDestination());
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();

            Log.e(TAG, "showDestinationAlert:error " + e.getMessage());
        }
    }

    private void carAnim(final Marker marker, final LatLng start, final LatLng end) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1900);
        final LatLngInterface latLngInterpolator = new LatLngInterface.LinearFixed();
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(valueAnimator1 -> {
            try {
                canCarAnim = false;
                float v = valueAnimator1.getAnimatedFraction();
                LatLng newPos = latLngInterpolator.interpolate(v, start, end);
                marker.setPosition(newPos);
                marker.setAnchor(0.5f, 0.5f);
                marker.setRotation(bearingBetweenLocations(start, end));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                canCarAnim = true;
            }
        });
        animator.start();
    }

    private double rotateMarker(double currentLat, double currentLng,
                                double nextLat, double nextLng) {
        double degToRad = Math.PI / 180.0;
        double phi1 = currentLat * degToRad;
        double phi2 = nextLat * degToRad;
        double lam1 = currentLng * degToRad;
        double lam2 = nextLng * degToRad;

        return Math.atan2(
                Math.sin(lam2 - lam1) * Math.cos(phi2),
                Math.cos(phi1) * Math.sin(phi2) - Math.sin(phi1)
                        * Math.cos(phi2) * Math.cos(lam2 - lam1))
                * 180 / Math.PI;
    }

    private void updateDestination() {
        STATUS = "";
        if (googleMap != null) googleMap.clear();
    }

    public void ShowLogoutPopUp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(getString(R.string.log_out_title))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", SharedHelper.getKey(activity(),
                            Constants.SharedPref.USER_ID) + "");
                    presenter.logout(map);
                }).setNegativeButton(getString(R.string.cancel), (dialog, id) -> {
            String user_id = SharedHelper.getKey(activity(), Constants.SharedPref.USER_ID);
            Utilities.printV("user_id===>", user_id);
            dialog.cancel();
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void drawRoute(LatLng source, LatLng destination) {
        GoogleDirection.withServerKey(getString(R.string.google_map_key))
                .from(source)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            googleMap.clear();
            Route route = direction.getRouteList().get(0);
            if (!route.getLegList().isEmpty()) {

                Leg startLeg = route.getLegList().get(0);
                Leg endLeg = route.getLegList().get(0);

                LatLng origin = new LatLng(startLeg.getStartLocation().getLatitude(), startLeg.getStartLocation().getLongitude());
                LatLng destination = new LatLng(endLeg.getEndLocation().getLatitude(), endLeg.getEndLocation().getLongitude());

                srcMarker = googleMap.addMarker(new MarkerOptions()
                        .position(origin)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
                googleMap.addMarker(new MarkerOptions()
                        .position(destination)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.des_icon)))
                        .setTag(endLeg);
            }

            polyLinePoints = route.getLegList().get(0).getDirectionPoint();
            mPolyline = googleMap.addPolyline(DirectionConverter.createPolyline
                    (this, polyLinePoints, 2, getResources().getColor(R.color.yellow)));
            setCameraWithCoordinationBounds(route);

        } else {

            Toast.makeText(this, "---" + direction.getStatus(), Toast.LENGTH_SHORT).show();

            Log.e("direction", "message=>" + direction.getErrorMessage());
        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(this, "++++" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public void navigateToShareScreen() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey Checkout this app," +
                getString(R.string.app_name) + "\nhttps://play.google.com/store/apps/details?id=" +
                BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public float bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {
        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        return (float) brng;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocationUI();
        getDeviceLocation();
        updateDriverNavigation();
    }

    private interface LatLngInterface {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterface {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                if (Math.abs(lngDelta) > 180) lngDelta -= Math.signum(lngDelta) * 360;
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }


}
