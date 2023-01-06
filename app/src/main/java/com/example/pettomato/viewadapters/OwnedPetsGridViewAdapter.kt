package com.example.pettomato.viewadapters

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.pettomato.R
import com.example.pettomato.activities.MainActivity
import com.example.pettomato.roomentities.PetEntity

class OwnedPetsGridViewAdapter(context: Context, private var petList: List<PetEntity>): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder(val ownedPetNameText: TextView, val ownedPetLevelText: TextView, val ownedPetImage: ImageView, val selectOwnedPetBtn: Button, val editPetNameBtn: ImageButton, val changePetNameEditText: EditText)

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
        val layout: View
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(curContext)
            layout = layoutInflater.inflate(R.layout.owned_pets_grid_view_item, parent, false)

            val ownedPetNameText = layout.findViewById<TextView>(R.id.ownedPetName_text)
            val ownedPetLevelText = layout.findViewById<TextView>(R.id.ownedPetLevel_text)
            val ownedPetImage = layout.findViewById<ImageView>(R.id.ownedPet_image)
            val selectOwnedPetBtn = layout.findViewById<Button>(R.id.selectOwnedPet_btn)
            val editPetNameBtn = layout.findViewById<ImageButton>(R.id.editPetName_btn)
            val changePetNameEditText = layout.findViewById<EditText>(R.id.changePetName_editText)
            val viewHolder = ViewHolder(ownedPetNameText, ownedPetLevelText, ownedPetImage, selectOwnedPetBtn, editPetNameBtn, changePetNameEditText)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify owned pet name text
        viewHolder.ownedPetNameText.text = petList[position].pet_name

        // Modify owned pet level text
        viewHolder.ownedPetLevelText.text = "LVL ${petList[position].pet_level}"

        // Modify owned pet image
        petList[position].setImageFromPet(viewHolder.ownedPetImage)

        // Modify selected owned pet button
        if (position == 0) {
            viewHolder.selectOwnedPetBtn.text = "SELECTED"
            viewHolder.selectOwnedPetBtn.textSize = 9f
            viewHolder.selectOwnedPetBtn.alpha = .5f
            viewHolder.selectOwnedPetBtn.isClickable = false
        } else {
            viewHolder.selectOwnedPetBtn.text = "SELECT"
            viewHolder.selectOwnedPetBtn.textSize = 10f
            viewHolder.selectOwnedPetBtn.alpha = 1f
            viewHolder.selectOwnedPetBtn.isClickable = true
        }
        viewHolder.selectOwnedPetBtn.setOnClickListener { (curContext as MainActivity).onSelectOwnedPetBtnPress(position) }

        // Modify change pet name edit text
        if (viewHolder.ownedPetNameText.visibility == View.VISIBLE) {
            viewHolder.changePetNameEditText.visibility = View.INVISIBLE
        }

        // Modify edit pet name button
        viewHolder.editPetNameBtn.setOnClickListener {
            if (viewHolder.changePetNameEditText.visibility == View.INVISIBLE) {
                // Go to name editing layout
                viewHolder.changePetNameEditText.visibility = View.VISIBLE
                viewHolder.ownedPetNameText.visibility = View.INVISIBLE
                viewHolder.editPetNameBtn.setImageResource(R.drawable.check_mark_symbol)
            } else {
                // Reset back to default layout
                viewHolder.changePetNameEditText.visibility = View.INVISIBLE
                viewHolder.ownedPetNameText.visibility = View.VISIBLE
                viewHolder.editPetNameBtn.setImageResource(R.drawable.edit_pencil_symbol)

                // Set pet name to entered name
                val newName = viewHolder.changePetNameEditText.text.toString()
                (curContext as MainActivity).onEditPetNameBtnPress(position, newName)
                viewHolder.changePetNameEditText.setText("")

                // Hide the keyboard
                val imm: InputMethodManager = curContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(layout.windowToken, 0)
            }
        }

        return layout
    }

    fun updatePetList(newPetList: List<PetEntity>) {
        petList = newPetList
        notifyDataSetChanged()
    }
}
