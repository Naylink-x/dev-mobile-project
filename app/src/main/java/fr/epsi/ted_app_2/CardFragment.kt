package fr.epsi.ted_app_2

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat.getColor
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewLastName = view.findViewById<TextView>(R.id.textViewLastName)
        val textViewFirstName = view.findViewById<TextView>(R.id.textViewFirstName)
        val textViewCardRef = readSharedPref("cardRef")


        textViewLastName.text = readSharedPref("lastName")
        textViewFirstName.text = readSharedPref("firstName")
        displayBitmap(textViewCardRef)
    }

    // Source : https://github.com/TheKamranUllah/Perfect_Barcode_Generator_Code_128
    private fun createBarcodeBitmap(
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {

        try{

            val bitMatrix = Code128Writer().encode(
                barcodeValue,
                BarcodeFormat.CODE_128,
                widthPixels,
                heightPixels
            )

            val pixels = IntArray(bitMatrix.width * bitMatrix.height)
            for (y in 0 until bitMatrix.height) {
                val offset = y * bitMatrix.width
                for (x in 0 until bitMatrix.width) {
                    pixels[offset + x] =
                        if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
                }
            }

            bitmap = Bitmap.createBitmap(
                bitMatrix.width,
                bitMatrix.height,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(
                pixels,
                0,
                bitMatrix.width,
                0,
                0,
                bitMatrix.width,
                bitMatrix.height
            )

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
        catch (e: NullPointerException)
        {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            bitmap  = BitmapFactory.decodeResource(context?.resources,R.drawable.ic_launcher_foreground)
            return bitmap
        }
        return bitmap
    }

    // Source : https://github.com/TheKamranUllah/Perfect_Barcode_Generator_Code_128
    private fun displayBitmap(value: String) {

        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)
        val imageViewBarcode = view?.findViewById<ImageView>(R.id.imageViewBarcode)
        val textViewCardRef = view?.findViewById<TextView>(R.id.textViewCardRef)

        bitmap =  createBarcodeBitmap(
            barcodeValue = value,
            barcodeColor = getColor(resources, R.color.black,null),
            backgroundColor = getColor(resources, R.color.white, null),
            widthPixels = widthPixels,
            heightPixels = heightPixels
        )
        imageViewBarcode?.setImageBitmap(bitmap)

        textViewCardRef?.text = value
    }

    fun readSharedPref(key:String):String{
        activity?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences("account", Context.MODE_PRIVATE)
            return sharedPreferences.getString(key,"not found").toString()
        }
        return ""
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        lateinit var bitmap: Bitmap
    }
}