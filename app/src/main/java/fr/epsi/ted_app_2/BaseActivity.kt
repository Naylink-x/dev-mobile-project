package fr.epsi.ted_app_2

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity(){
    fun showBack(){
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.visibility= View.VISIBLE
        imageViewBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun setHeaderTitle(text:String){
        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        textViewTitle.text=text
        textViewTitle.isSelected = true;
        textViewTitle.ellipsize = TextUtils.TruncateAt.MARQUEE;
        textViewTitle.setHorizontallyScrolling(true);
        textViewTitle.isSingleLine = true;
        textViewTitle.setLines(1);
    }
}