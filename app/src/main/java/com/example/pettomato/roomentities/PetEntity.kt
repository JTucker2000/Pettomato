package com.example.pettomato.roomentities

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var pet_name: String,
    var normal_image_id: Int,
    var happy_image_id: Int,
    var sad_image_id: Int,
    var angry_image_id: Int,
    var pet_level: Int,
    var pet_health: Int,
    val pet_maxhp: Int,
    var hunger_level: Int,
    var thirst_level: Int,
    var happiness_level: Int,
    var fitness_level: Int
) {
    // Sets the image resource of 'imageView' to a pet image from this pet.
    // Image is different depending on the pet's mood.
    fun setImageFromPet(imageView: ImageView) {
        val happinessScore: Int = hunger_level + thirst_level + happiness_level + fitness_level

        when {
            happinessScore > 300 -> imageView.setImageResource(happy_image_id)
            happinessScore > 200 -> imageView.setImageResource(normal_image_id)
            happinessScore > 100 -> imageView.setImageResource(angry_image_id)
            else -> imageView.setImageResource(sad_image_id)
        }
    }
}