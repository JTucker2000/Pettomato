package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pettomato.R
import com.example.pettomato.activities.MainActivity
import com.example.pettomato.roomentities.PetEntity

class OwnedPetsGridViewAdapter(context: Context, private var petList: List<PetEntity>): BaseAdapter() {
    private val curContext: Context

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return petList.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(curContext)
        val layout = layoutInflater.inflate(R.layout.owned_pets_grid_view_item, parent, false)

        val ownedPetNameText: TextView = layout.findViewById<TextView>(R.id.ownedPetName_text)
        ownedPetNameText.text = petList[position].pet_name

        val ownedPetLevelText: TextView = layout.findViewById<TextView>(R.id.ownedPetLevel_text)
        ownedPetLevelText.text = "LVL ${petList[position].pet_level}"

        val ownedPetImage: ImageView = layout.findViewById<ImageView>(R.id.ownedPet_image)
        petList[position].setImageFromPet(ownedPetImage)

        val selectOwnedPetBtn: Button = layout.findViewById<Button>(R.id.selectOwnedPet_btn)
        if(position == 0) {
            selectOwnedPetBtn.text = "SELECTED"
            selectOwnedPetBtn.textSize = 10f
            selectOwnedPetBtn.alpha = .5f
            selectOwnedPetBtn.isClickable = false
        }
        selectOwnedPetBtn.setOnClickListener { (curContext as MainActivity).onSelectOwnedPetBtnPress(position) }

        return layout
    }

    fun updatePetList(newPetList: List<PetEntity>) {
        petList = newPetList
        notifyDataSetChanged()
    }
}
