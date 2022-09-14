package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pettomato.PET_PRICES
import com.example.pettomato.PURCHASEABLE_PETS
import com.example.pettomato.R

class PetShopGridViewAdapter(context: Context): BaseAdapter() {
    private val curContext: Context

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return PURCHASEABLE_PETS.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(curContext)
        val layout = layoutInflater.inflate(R.layout.pet_shop_grid_view_item, parent, false)

        val newPetNameText: TextView = layout.findViewById<TextView>(R.id.newPetName_text)
        newPetNameText.text = PURCHASEABLE_PETS[position].pet_name

        val newPetPriceText: TextView = layout.findViewById<TextView>(R.id.newPetPrice_text)
        newPetPriceText.text = PET_PRICES[position].toString()

        val newPetImage: ImageView = layout.findViewById<ImageView>(R.id.newPet_image)
        PURCHASEABLE_PETS[position].setImageFromPet(newPetImage)

        return layout
    }
}