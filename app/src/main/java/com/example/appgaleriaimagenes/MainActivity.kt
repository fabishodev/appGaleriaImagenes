package com.example.appgaleriaimagenes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appgaleriaimagenes.activities.FragmentsActivity
import com.example.appgaleriaimagenes.adapters.DogAdapter
import com.example.appgaleriaimagenes.fragments.DetailFragment
import com.example.appgaleriaimagenes.fragments.MainFragment
import com.example.appgaleriaimagenes.http.ApiInterface
import com.example.appgaleriaimagenes.models.Dog
import com.example.appgaleriaimagenes.models.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private lateinit var dogRecyclerView : RecyclerView
    private lateinit var dogAdapter : DogAdapter
    private var baseURL = "https://dog.ceo/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dogRecyclerView = findViewById(R.id.reclycles_dog)
        dogRecyclerView.setHasFixedSize(true)

        //dogRecyclerView.setLayoutManager(LinearLayoutManager(this))
        dogRecyclerView.layoutManager =  LinearLayoutManager(this)
        //val dogs = getData1()
        //setAdapter(dogs)
        getData()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fragment, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.menu_item_detail_fragment->{
               var intent = Intent(this, FragmentsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAdapter(dogList:MutableList<Dog>){
        dogAdapter =  DogAdapter(dogList){ dog ->
            Toast.makeText(this,"Dog Name: "+dog.name, Toast.LENGTH_SHORT).show()
        }
        dogRecyclerView.adapter = dogAdapter
    }

    fun getData(){

        val lst:MutableList<Dog> = mutableListOf()
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
            .create(ApiInterface::class.java)
        val builder = retrofitBuilder.getDogs()

        Log.d("DOG","getData")
        builder.enqueue(object: Callback<MessageResponse?> {
            override fun onResponse(
                call: Call<MessageResponse?>,
                response: Response<MessageResponse?>
            ) {
                Log.d("DOG","Here")
                val responseBody = response.body()!!
                for(imageUrl in responseBody.message)
                {
                    lst.add(Dog(imageUrl,imageUrl))
                }
                setAdapter(lst)
            }
            override fun onFailure(call: Call<MessageResponse?>, t: Throwable) {
                Log.d("DOG","Error")
            }
        })
    }



    private fun getData1():MutableList<Dog> {
        val lst: MutableList<Dog> = mutableListOf(
            Dog("Affenpinscher","https://images.dog.ceo/breeds/affenpinscher/n02110627_12431.jpg"),
            Dog("Redbone","https://images.dog.ceo/breeds/redbone/n02090379_1006.jpg"),
            Dog("Pug","https://images.dog.ceo/breeds/pug/n02110958_3644.jpg"),
            Dog("Affenpinscher","https://images.dog.ceo/breeds/affenpinscher/n02110627_12431.jpg"),
            Dog("Redbone","https://images.dog.ceo/breeds/redbone/n02090379_1006.jpg"),
            Dog("Pug","https://images.dog.ceo/breeds/pug/n02110958_3644.jpg"),
        )
        return lst
    }
}