package com.guillermocode.testpractico

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import java.lang.System.load

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val img = arrayOf("https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg",
        "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg", "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg", "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg",
        "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg", "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg", "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg",
        "https://cdni.russiatoday.com/actualidad/public_images/2019.02/article/5c76d3ed08f3d92b668b4567.jpg")

    private val kategori = arrayOf("Telefono1", "Telefono2",
        "Telefono3", "Telefono4",
        "Telefono5", "Telefono6",
        "Telefono7", "Telefono8")

    private val isi = arrayOf("Descripcion 9",
        "Descripcion 11", "Descripcion 17", "test forum",
        "Descripcion 12", "Descripcion 18", "Descripcion 20",
        "Descripcion 21")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImg: ImageView
        var itemKategori: TextView
        var itemIsi: TextView

        init {
            itemImg = itemView.findViewById(R.id.Img)
            itemKategori = itemView.findViewById(R.id.kategori)
            itemIsi = itemView.findViewById(R.id.isiPertanyaan)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, DetailArticulo::class.java).apply {
                    putExtra("NUMBER", position)
                    putExtra("IMG", img[position])
                    putExtra("CATEGORY", itemKategori.text)
                    putExtra("CONTENT", itemIsi.text)
                }
                context.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_articulos, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        Picasso.get().load(img[i]).into( viewHolder.itemImg)
        viewHolder.itemKategori.text = kategori[i]
        viewHolder.itemIsi.text = isi[i]

    }

    override fun getItemCount(): Int {
        return img.size
    }

}