package com.clearsky77.catgame
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clearsky77.catgame.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/** Main Activity. Inflates main activity xml and child fragments.  */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adView: AdView

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "태그"

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111" // 배너 아이디(구글 테스트용 ID)
        private val AD_REWARD_ID = "ca-app-pub-3940256099942544/5224354917" // 보상형 아이디(구글 테스트용 ID)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValues()
        setupEvents()
    }

    private val adSize: AdSize // 화면 가로 사이즈
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.adViewContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    private fun setValues() {

        MobileAds.initialize(this) { } // 애드몹 초기화
        adView = AdView(this)
        binding.adViewContainer.addView(adView) // 레이아웃에 에드뷰 연결
        loadBanner()
    }

    private fun setupEvents(){
        binding.btnGetReward.setOnClickListener {
            Log.d(TAG, "클릭 리스너 진입")
            loadInterstitialAd() // 광고 로드

        }
    }


    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID // 광고 유닛 아이디
        adView.adSize = adSize // 사이즈 넣기

        val adRequest = AdRequest.Builder().build()

        adView.loadAd(adRequest) // 애드뷰 로드
    }

    private fun loadInterstitialAd() {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(this,AD_REWARD_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })
    }


}