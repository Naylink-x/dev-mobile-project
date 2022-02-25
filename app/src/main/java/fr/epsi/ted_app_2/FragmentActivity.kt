package fr.epsi.ted_app_2

import android.os.Bundle
import android.view.View
import android.widget.TextView

class FragmentActivity: BaseActivity() {

    val cardFragment = CardFragment.newInstance("", "")
    val offersFragment = OffersFragment.newInstance("", "")
    val storesFragment = StoresFragment.newInstance("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val card = findViewById<TextView>(R.id.textViewCard)
        val offers = findViewById<TextView>(R.id.textViewOffers)
        val stores = findViewById<TextView>(R.id.textViewStores)
        setHeaderImage()
        setAccountDetail()

        card.setOnClickListener(View.OnClickListener {
            showCard()
        })

        offers.setOnClickListener(View.OnClickListener {
            showOffers()
        })

        stores.setOnClickListener(View.OnClickListener {
            showStores()
        })

        showCard()
    }

    private fun showCard() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack("CardFragment")
        fragmentTransaction.replace(R.id.fragment_container, cardFragment, null)
        fragmentTransaction.commit()
    }

    private fun showOffers() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack("OffersFragment")
        fragmentTransaction.replace(R.id.fragment_container, offersFragment, null)
        fragmentTransaction.commit()
    }

    private fun showStores() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack("StoresFragment")
        fragmentTransaction.replace(R.id.fragment_container, storesFragment, null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>1)
            super.onBackPressed()
        else
            finish()
    }
}