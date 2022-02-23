package fr.epsi.ted_app_2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import org.json.JSONException
import org.json.JSONObject

class QrcodeScannerActivity: BaseActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)
        setHeaderTitle(getString(R.string.scanner_title))
        showBack()

        setupPermissions()
        codeScanner()
    }

    private fun codeScanner() {
        val scannerView = findViewById<CodeScannerView>(R.id.scannerView)
        val scannerTextView = findViewById<TextView>(R.id.scannerTextView)

        val accountsList = arrayListOf<Account>()
        var count = -1

        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                count++
                runOnUiThread {
                    try {
                        val obj = JSONObject(it.toString())
                        accountsList.add(
                            Account(
                                lastName = obj.optString("lastName", ""),
                                firstName = obj.optString("firstName", ""),
                                email = obj.optString("email", ""),
                                address = obj.optString("address", ""),
                                zipCode = obj.optString("zipCode", ""),
                                city = obj.optString("city", ""),
                                cardRef = obj.optString("cardRef", "")
                        ))

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    //scannerTextView.text = it.text

                    val newIntent = Intent(applicationContext, AccountCreationActivity::class.java)

                    /*newIntent.putExtra("lastName", accountsList[count].lastName)
                    newIntent.putExtra("firstName", accountsList[count].firstName)
                    newIntent.putExtra("email", accountsList[count].email)
                    newIntent.putExtra("address", accountsList[count].address)
                    newIntent.putExtra("zipCode", accountsList[count].zipCode)
                    newIntent.putExtra("city", accountsList[count].city)
                    newIntent.putExtra("cardRef", accountsList[count].cardRef)*/
                    newIntent.putExtra("accountObject", accountsList[count])
                    startActivity(newIntent)
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT).show()
                } else {
                    //successful
                }
            }
        }
    }
}