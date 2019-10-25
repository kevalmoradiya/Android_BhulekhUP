package com.kmcoder.maharashtrabhulekh.AppActivity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.kmcoder.maharashtrabhulekh.R;


public class ExtraService extends AppCompatActivity {
    Button btnAdharCard,btnPanCard,btnPassport,btnTrain;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extraservice);

        // Instantiate an AdView view
        adView = new AdView(this, getString(R.string.FB_ExtraService_Banner), AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Set Listener
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


        btnAdharCard=findViewById(R.id.button11);
        btnPanCard=findViewById(R.id.button22);
        btnPassport=findViewById(R.id.button33);
        btnTrain=findViewById(R.id.button44);

        btnAdharCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ExtraService.this, WebViewActivity.class);

                i1.putExtra("address",getString(R.string.AdharCard_URL));
                startActivity(i1);

            }
        });
        btnPanCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ExtraService.this, WebViewActivity.class);

                i1.putExtra("address",getString(R.string.PanCard_URL));
                startActivity(i1);

            }
        });
        btnPassport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ExtraService.this, WebViewActivity.class);

                i1.putExtra("address",getString(R.string.Passport_URL));
                startActivity(i1);

            }
        });
        btnTrain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ExtraService.this, TrainActivity.class);
                startActivity(i1);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {

        if (menuItem.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);

    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}
