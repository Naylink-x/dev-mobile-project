package fr.epsi.ted_app_2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AccountCreationActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation)
        setHeaderTitle(getString(R.string.new_account_title))
        showBack()

        val lastNameAccount = findViewById<EditText>(R.id.editTextAccountLastName)
        val firstNameAccount = findViewById<EditText>(R.id.editTextAccountFirstName)
        val emailAccount = findViewById<EditText>(R.id.editTextAccountEmail)
        val addressAccount = findViewById<EditText>(R.id.editTextAccountAddress)
        val zipCodeAccount = findViewById<EditText>(R.id.editTextAccountZipCode)
        val cityAccount = findViewById<EditText>(R.id.editTextAccountCity)
        val cardRefAccount = findViewById<EditText>(R.id.editTextAccountCardRef)
        val buttonCreateAccount = findViewById<Button>(R.id.buttonCreateAccount)

        val account = intent.getSerializableExtra("accountObject") as Account?

        /*val lastName = intent.getStringExtra("lastName")
        val firstName = intent.getStringExtra("firstName")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val zipCode = intent.getStringExtra("zipCode")
        val city = intent.getStringExtra("city")
        val cardRef = intent.getStringExtra("cardRef")*/

        lastNameAccount.setText(account?.lastName)
        firstNameAccount.setText(account?.firstName)
        emailAccount.setText(account?.email)
        addressAccount.setText(account?.address)
        zipCodeAccount.setText(account?.zipCode)
        cityAccount.setText(account?.city)
        cardRefAccount.setText(account?.cardRef)


        buttonCreateAccount.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(lastNameAccount.text.toString()) ||
                TextUtils.isEmpty(firstNameAccount.text.toString()) ||
                TextUtils.isEmpty(emailAccount.text.toString()) ||
                TextUtils.isEmpty(addressAccount.text.toString()) ||
                TextUtils.isEmpty(zipCodeAccount.text.toString()) ||
                TextUtils.isEmpty(cityAccount.text.toString()) ||
                TextUtils.isEmpty(cardRefAccount.text.toString()))
            {
                Toast.makeText(this, "Empty fields not allowed !",
                    Toast.LENGTH_SHORT).show()
            }
            else
            {
                // Save data
                writeSharedPref("lastName", lastNameAccount.text.toString())
                writeSharedPref("firstName", firstNameAccount.text.toString())
                writeSharedPref("email", emailAccount.text.toString())
                writeSharedPref("address", addressAccount.text.toString())
                writeSharedPref("zipCode", zipCodeAccount.text.toString())
                writeSharedPref("city", cityAccount.text.toString())
                writeSharedPref("cardRef", cardRefAccount.text.toString())

                // Show home
                //TODO: call HomeActivity
            }
        })

        /*lastNameAccount.setText(readSharedPref("lastName"))
        firstNameAccount.setText(readSharedPref("firstName"))
        emailAccount.setText(readSharedPref("email"))
        addressAccount.setText(readSharedPref("address"))
        zipCodeAccount.setText(readSharedPref("zipCode"))
        cityAccount.setText(readSharedPref("city"))
        cardRefAccount.setText(readSharedPref("cardRef"))*/
    }

    fun writeSharedPref(key:String,value:String){
        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    /*fun readSharedPref(key:String):String{
        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"not found").toString()
    }*/
}