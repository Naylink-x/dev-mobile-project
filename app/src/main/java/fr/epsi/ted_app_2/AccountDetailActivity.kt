package fr.epsi.ted_app_2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class AccountDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        setHeaderTitle(getString(R.string.account))
        showBack()

        val lastNameAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesLastName)
        val firstNameAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesFirstName)
        val emailAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesEmail)
        val addressAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesAddress)
        val zipCodeAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesZipCode)
        val cityAccountChanges = findViewById<EditText>(R.id.editTextAccountChangesCity)
        val buttonModifyAccount = findViewById<Button>(R.id.buttonModifyAccount)

        lastNameAccountChanges.setText(readSharedPref("lastName"))
        firstNameAccountChanges.setText(readSharedPref("firstName"))
        emailAccountChanges.setText(readSharedPref("email"))
        addressAccountChanges.setText(readSharedPref("address"))
        zipCodeAccountChanges.setText(readSharedPref("zipCode"))
        cityAccountChanges.setText(readSharedPref("cardRef"))

        buttonModifyAccount.setOnClickListener(View.OnClickListener {
            writeSharedPref("lastName", lastNameAccountChanges.text.toString())
            writeSharedPref("firstName", firstNameAccountChanges.text.toString())
            writeSharedPref("email", emailAccountChanges.text.toString())
            writeSharedPref("address", addressAccountChanges.text.toString())
            writeSharedPref("zipCode", zipCodeAccountChanges.text.toString())
            writeSharedPref("city", cityAccountChanges.text.toString())

            val newIntent = Intent(application, FragmentActivity::class.java)
            startActivity(newIntent)
        })
    }

    fun writeSharedPref(key:String,value:String){
        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun readSharedPref(key:String):String{
        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"not found").toString()
    }
}