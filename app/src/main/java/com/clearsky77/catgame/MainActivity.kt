package com.clearsky77.catgame
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

/** Main Activity. Inflates main activity xml and child fragments.  */
class MainActivity : AppCompatActivity() {

    private lateinit var adView: AdView

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111" // 아이디 넣기(구글 테스트용 ID)
    }

    private val adSize: AdSize // 화면 가로 사이즈
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = ad_view_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) { } // 애드몹 초기화

        adView = AdView(this)
        ad_view_container.addView(adView) // 레이아웃에 에드뷰 연결

        loadBanner()
    }

    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID // 광고 유닛 아이디
        adView.adSize = adSize // 사이즈 넣기

        val adRequest = AdRequest.Builder().build()

        adView.loadAd(adRequest) // 애드뷰 로드
    }


}