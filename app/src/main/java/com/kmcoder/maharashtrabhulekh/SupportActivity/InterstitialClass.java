package com.kmcoder.maharashtrabhulekh.SupportActivity;

import android.content.Context;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.kmcoder.maharashtrabhulekh.R;

public class InterstitialClass {
    private static InterstitialClass instance;
    public InterstitialAd interstitialAd;


    public static InterstitialClass getInstance() {
        if (instance == null) {
            instance = new InterstitialClass();
            return instance;
        }
        return instance;
    }

    public void AdShow(Context context) {
        interstitialAd = new InterstitialAd(context, context.getString(R.string.FBInterstitialAd));
        interstitialAd.loadAd();
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                interstitialAd.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback



            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }


        });

    }

}