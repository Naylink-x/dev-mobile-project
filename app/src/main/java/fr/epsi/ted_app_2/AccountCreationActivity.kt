package fr.epsi.ted_app_2

import android.os.Bundle
import android.os.PersistableBundle

class AccountCreationActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_creation)
        setHeaderTitle(getString(R.string.new_account_title))
        showBack()
    }
}