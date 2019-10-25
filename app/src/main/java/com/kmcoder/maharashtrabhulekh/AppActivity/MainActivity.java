package com.kmcoder.maharashtrabhulekh.AppActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.kmcoder.maharashtrabhulekh.R;
import com.kmcoder.maharashtrabhulekh.SupportActivity.Global;
import com.kmcoder.maharashtrabhulekh.SupportActivity.InterstitialClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity{

    private static final String TAG ="XYZ" ;

    // Instantiate an Banner AdView view
    private @Nullable
    AdView adView;
    Global g = Global.getInstance();
    Button btnBhulekh,btnOpenExtService,btnShareApp,btnRateApp,btnUpdateApp;
    String text,version;
    LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Facebook Ad SDK once
        if(g.getDataflag())
        {
            AudienceNetworkAds.initialize(this);
            g.setDataFlagfalse();
        }


        //Create Interstitial Ad if it's not created
        if(InterstitialClass.getInstance().interstitialAd==null)
        {
            InterstitialClass.getInstance().AdShow(getApplicationContext());
        }

        // Instantiate an Banner AdView view
        adView = new AdView(this, getString(R.string.MainBanner), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        //Banner Ad Listener
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
        // Request an ad
        adView.loadAd();

        btnBhulekh=findViewById(R.id.button1);
        btnOpenExtService=findViewById(R.id.button3);
        btnShareApp=findViewById(R.id.button5);
        btnRateApp=findViewById(R.id.button6);
        btnUpdateApp=findViewById(R.id.button7);


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            // Check app version code from our website page
            new AsyncCallWS().execute();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




        btnBhulekh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.WebViewAddress));
                startActivity(i1);

            }
        });

        btnOpenExtService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this, ExtraService.class);
                startActivity(i1);

            }
        });

        btnShareApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "MAHARASHTRA");
                    String sAux = "MAHARASHTRA BHULEKH\n";
                    sAux = sAux + "VIEW SAVE PRINT AND SHARE YOUR BHULEKH.\n";
                    sAux = sAux + "ADHARCARD,PANCARD,PASSPORT AND TRAIN INQUIRY. \n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id="+getPackageName()+"\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "SELECT"));
                } catch(Exception e) {
                    //e.toString();
                }

            }
        });
        btnRateApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });
        btnUpdateApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });



    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if(InterstitialClass.getInstance().interstitialAd!=null)
        {
            InterstitialClass.getInstance().interstitialAd.destroy();

        }
        super.onDestroy();
    }


    private class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {

                Document doc = Jsoup.connect(getString(R.string.AppVersionCheckUrl)).get();
                Elements el=doc.select("div[class=textt] > p");
                text=el.text();

            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (!text.equals(version) && !text.equals("")) {

                    btnUpdateApp.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }




}
