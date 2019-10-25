package com.kmcoder.maharashtrabhulekh.AppActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.kmcoder.maharashtrabhulekh.R;
import com.kmcoder.maharashtrabhulekh.SupportActivity.Global;
import com.kmcoder.maharashtrabhulekh.SupportActivity.InterstitialClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class WebViewActivity extends AppCompatActivity {

    private WebView myWebView;
    AdView adView;
    Global g = Global.getInstance();
    String site,download,downloadurl;
    FileOutputStream out;
    File imgDir;
    Bitmap bmp;
    ProgressDialog pd;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        site=getIntent().getExtras().getString("address");

        // Instantiate an Banner AdView view
        adView = new AdView(this, getString(R.string.FB_WebView_Banner), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer =findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);

        //Set Ad Listener
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                adView.loadAd();
            }
            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });


        try {

            //Check DateTime is set or not
            if(g.getData()==null)
            {

                //Check interstitial ad is loaded or not
                if(InterstitialClass.getInstance().interstitialAd.isAdLoaded())
                {
                    InterstitialClass.getInstance().interstitialAd.show();
                    //Set  DateTime for Interstitial ad
                    g.setData(new Date());

                }

            }
            else
            {
                //Fetch DateTime stored
                Date storetime = g.getData();
                Date currenttime = new Date();
                // Check gape between current and stored time is greater than 180 second
                if((currenttime.getTime()-storetime.getTime())>180000)
                {

                    //Check Interstitial ad is loaded or not
                    if(InterstitialClass.getInstance().interstitialAd.isAdLoaded())
                    {
                        InterstitialClass.getInstance().interstitialAd.show();
                        ////Set  DateTime for Interstitial ad
                        g.setData(new Date());

                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        myWebView =findViewById(R.id.mywebview);
        final Button btnSaveImage = findViewById(R.id.fab);
        final Button btnSahreImage =  findViewById(R.id.fab1);
        Button btnRefresh = findViewById(R.id.fab2);



        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final View view) {



                    //check external storage permission
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        AlertDialog.Builder inputAlert = new AlertDialog.Builder(WebViewActivity.this);
                        inputAlert.setTitle("Enter Name");
                        inputAlert.setMessage("Save at MaharashtraBhulekh Folder");
                        final EditText input = new EditText(WebViewActivity.this);
                        input.setFilters(new InputFilter[] { filter });
                        input.requestFocus();
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        inputAlert.setView(input);
                        inputAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userInputValue = input.getText().toString();

                                try {
                                    File folder = new File(Environment.getExternalStorageDirectory() + "/MaharashtraBhulekh");
                                    if (!folder.exists()) {
                                        folder.mkdir();
                                    }

                                    imgDir = new File(folder, userInputValue+".jpg");
                                    out = new FileOutputStream(imgDir);
                                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_SHORT).show();
                                    if (out != null) {
                                        out.flush();
                                        out.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Save Image" + e);
                                }


                            }
                        });
                        inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = inputAlert.create();
                        alertDialog.show();

                        try {
                            bmp = getBitmapForVisibleRegion(myWebView);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("-----error--" + e);
                        }


                    }
                    else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                        try {
                            btnSaveImage.performClick();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

            }

        });

        btnSahreImage.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final View view) {


                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    try {
                        Bitmap b = getBitmapForVisibleRegion(myWebView);
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                                b, "ShareImage", null);
                        if (bytes != null) {
                            bytes.flush();
                            bytes.close();
                        }
                        Uri imageUri = Uri.parse(path);
                        share.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(share, "Select"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("-----error--" + e);
                    }

                }
                else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                    try {
                        btnSahreImage.performClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }


        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(final View view) {

                myWebView.loadUrl(site);

            }

        });


        // Set webview property
        myWebView.setDrawingCacheEnabled(true);
        myWebView.buildDrawingCache();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().getLoadWithOverviewMode();
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setInitialScale(30);

        // To download map or bhulekh
        myWebView.setDownloadListener(new DownloadListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                //Notify client once download is completed!
                downloadurl=url;

                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                        AlertDialog.Builder inputAlert = new AlertDialog.Builder(WebViewActivity.this);
                        inputAlert.setTitle("Enter Name");
                        inputAlert.setMessage("Save at MaharashtraBhulekh Folder");
                        final EditText input = new EditText(WebViewActivity.this);
                        input.setFilters(new InputFilter[] { filter });
                        input.requestFocus();
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        inputAlert.setView(input);

                        inputAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                download = input.getText().toString();
                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(downloadurl));

                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                try {
                                    File folder = new File(Environment.getExternalStorageDirectory() + "/MaharashtraBhulekh");
                                    if (!folder.exists()) {
                                        folder.mkdir();
                                    }

                                    request.setDestinationInExternalPublicDir("/MaharashtraBhulekh", download+".pdf");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("-----error--" + e);
                                }

                                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                if (dm != null) {
                                    dm.enqueue(request);
                                }
                                Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                                        Toast.LENGTH_LONG).show();

                            }
                        });
                        inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = inputAlert.create();
                        alertDialog.show();

                    }
                    else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }}


        });

        pd=ProgressDialog.show(WebViewActivity.this, "Loading", "Please wait...", true);
        pd.setCancelable(true);
        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {


                try {

                    pd.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            public void onPageFinished(WebView view, String url) {


                try {
                    pd.dismiss();


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        myWebView.loadUrl(site);
        adView.loadAd();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }



    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String blockCharacterSet = "/~#^|$%&*!";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public static Bitmap getBitmapForVisibleRegion(WebView webview) {
        Bitmap returnedBitmap;
        webview.setDrawingCacheEnabled(true);
        returnedBitmap = Bitmap.createBitmap(webview.getDrawingCache());
        webview.setDrawingCacheEnabled(false);
        return returnedBitmap;
    }
    @Override
    protected void onDestroy() {
        if (adView != null ) {
            adView.destroy();
        }


        super.onDestroy();



    }



}
