package fr.epsi.ted_app_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setHeaderTitle(getString(R.string.new_account_title))
        val buttonQRCodeScan = findViewById<ImageButton>(R.id.button_qrcode_scan)
        val buttonManualFill = findViewById<Button>(R.id.button_fill_manually)

        buttonQRCodeScan.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application,QrcodeScannerActivity::class.java)
            startActivity(newIntent)
        })

        buttonManualFill.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application,AccountCreationActivity::class.java)
            startActivity(newIntent)
        })
    }
}