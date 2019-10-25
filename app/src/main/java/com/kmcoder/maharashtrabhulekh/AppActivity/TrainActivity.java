package com.kmcoder.maharashtrabhulekh.AppActivity;

import android.content.Intent;
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
import com.kmcoder.maharashtrabhulekh.R;

public class TrainActivity extends AppCompatActivity {
   
    private @Nullable
    AdView adView;
    Button btnTBtwn,btnPnrEnq,btnTSchedule,btnTLiveSta,btnTSeatAva,btnFairEnq,btnLiveStsInfo,btnCancTra,btnDivrtTra;
    Intent i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Instantiate an AdView view
        adView = new AdView(this, getString(R.string.FB_Train_BannerAd), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer =findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        //Set ad listener
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

        btnTBtwn=findViewById(R.id.button1);
        btnPnrEnq=findViewById(R.id.button2);
        btnTSchedule=findViewById(R.id.button3);
        btnTLiveSta=findViewById(R.id.button4);
        btnTSeatAva=findViewById(R.id.button5);
        btnFairEnq=findViewById(R.id.button6);
        btnLiveStsInfo=findViewById(R.id.button7);
        btnCancTra=findViewById(R.id.button8);
        btnDivrtTra=findViewById(R.id.button9);

        btnTBtwn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Train_Between_Status_URL));
                startActivity(i1);
//Train Between Station
            }
        });
        btnPnrEnq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Pnr_Status_URL));
                startActivity(i1);
//PNR
            }
        });
        btnTSchedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Train_Schedule_URL));
                startActivity(i1);
//TRAIN SVHEDULE
            }
        });
        btnTLiveSta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Live_Train_URL));
                startActivity(i1);
//Live Train Status
            }
        });
        btnTSeatAva.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Seat_Availability_URL));
                startActivity(i1);
//Seat Availability
            }
        });
        btnFairEnq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Fair_Enquiry_URL));
                startActivity(i1);
//Fair Enquiry
            }
        });

        btnLiveStsInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Live_Station_URL));
                startActivity(i1);
//Live Station Info
            }
        });

        btnCancTra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Cancelled_Train_URL));
                startActivity(i1);
//Canceled Trains
            }
        });
        btnDivrtTra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i1 = new Intent(TrainActivity.this, WebViewActivity.class);
                i1.putExtra("address",getString(R.string.Diverted_Train_URL));
                startActivity(i1);
//Diverted Trains
            }
        });






    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }




}



