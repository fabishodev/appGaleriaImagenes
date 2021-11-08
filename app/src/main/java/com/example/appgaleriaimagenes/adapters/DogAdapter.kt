package com.example.appgaleriaimagenes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appgaleriaimagenes.R
import com.example.appgaleriaimagenes.models.Dog
import com.squareup.picasso.Picasso

class DogAdapter(dogList : List<Dog>, private val listener:(Dog) -> Unit) : RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    private var  dogList:List<Dog> = dogList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v:View =
           LayoutInflater.from(parent.context).inflate(R.layout.item_dog,parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogList[position]
        //holder.imageDog
        holder.textDog.text = dog.name
        holder.itemView.setOnClickListener{
            listener(dog)
        }
        Picasso.get().load(dog.imageURL).into(holder.imageDog);
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val textDog: TextView = v.findViewById(R.id.text_dog)
        val imageDog:  ImageView = v.findViewById(R.id.img_dog)
    }
}