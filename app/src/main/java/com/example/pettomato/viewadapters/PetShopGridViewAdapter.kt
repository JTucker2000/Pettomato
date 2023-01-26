package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pettomato.PET_PRICES
import com.example.pettomato.PURCHASABLE_PETS
import com.example.pettomato.R
import com.example.pettomato.activities.MainActivity

class PetShopGridViewAdapter(context: Context): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder(val newPetNameText: TextView, val newPetPriceText: TextView, val newPetImage: ImageView, val buyPetBtn: Button)

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return PURCHASABLE_PETS.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layout: View
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(curContext)
            layout = layoutInflater.inflate(R.layout.pet_shop_grid_view_item, parent, false)

            val newPetNameText = layout.findViewById<TextView>(R.id.newPetName_text)
            val newPetPriceText = layout.findViewById<TextView>(R.id.newPetPrice_text)
            val newPetImage = layout.findViewById<ImageView>(R.id.newPet_image)
            val buyPetBtn = layout.findViewById<Button>(R.id.buyPet_btn)
            val viewHolder = ViewHolder(newPetNameText, newPetPriceText, newPetImage, buyPetBtn)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify new pet name text
        viewHolder.newPetNameText.text = PURCHASABLE_PETS[position].pet_name

        // Modify new pet price text
        viewHolder.newPetPriceText.text = PET_PRICES[position].toString()

        // Modify new pet image
        PURCHASABLE_PETS[position].setImageFromPet(viewHolder.newPetImage)

        // Modify buy pet button
        viewHolder.buyPetBtn.setOnClickListener { (curContext as MainActivity).onBuyPetBtnPress(position) }

        return layout
    }
}