package fr.epsi.ted_app_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class StoreDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)
        intent.getStringExtra("name")?.let { setHeaderTitle(it) }
        showBack()

        val imageViewStoreDetail = findViewById<ImageView>(R.id.imageViewStoreDetail)
        val pictureStore = intent.getStringExtra("pictureStore")
        val textViewStoreAddress = findViewById<TextView>(R.id.textViewStoreAddress)
        val addressStore = intent.getStringExtra("address")
        val textViewStoreZipCodeCity = findViewById<TextView>(R.id.textViewStoreZipCodeCity)
        val zipCodeCityStore = intent.getStringExtra("zipCodeCity")
        val textViewStoreDescription = findViewById<TextView>(R.id.textViewStoreDescription)
        val descriptionStore = intent.getStringExtra("description")

        Picasso.get().load(pictureStore).into(imageViewStoreDetail)
        textViewStoreAddress.text = addressStore
        textViewStoreZipCodeCity.text = zipCodeCityStore
        textViewStoreDescription.text = descriptionStore

    }
}