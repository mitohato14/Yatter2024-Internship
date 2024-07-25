package com.dmm.bootcamp.yatter2024.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SplashScreenをインストール
        installSplashScreen()

        // スプラッシュスクリーンが表示される時間を模擬するために遅延処理を行う
        simulateSplashScreenDelay()

        // メイン画面に遷移
        //navigateToMainActivity()
    }

    private fun simulateSplashScreenDelay() {
        // ここでスプラッシュスクリーンが表示される時間を模擬する
        // 実際には必要な処理（データの読み込みや初期化など）を行う
        Thread.sleep(3000) // 3秒間スリープ
    }
}