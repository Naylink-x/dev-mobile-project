package fr.epsi.ted_app_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class OffersAdapter(val offers: ArrayList<Offer>): RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    class ViewHolder(private val context: Context, view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val nameOffers = view.findViewById<TextView>(R.id.textViewOffersName)
        val descriptionOffers = view.findViewById<TextView>(R.id.textViewOffersDescription)
        val imageViewOffers = view.findViewById<ImageView>(R.id.imageViewOffers)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_offers, viewGroup, false)
        return ViewHolder(viewGroup.context, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = offers.get(position)
        holder.nameOffers.text = offer.name
        holder.descriptionOffers.text = offer.description
        Picasso.get().load(offer.picture_url).into(holder.imageViewOffers)
    }

    override fun getItemCount(): Int {
        return offers.size
    }
}