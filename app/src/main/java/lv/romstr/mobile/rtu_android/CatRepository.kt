package lv.romstr.mobile.rtu_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(CatService::class.java)

    fun getImage(): LiveData<CatImage> {
        val data = MutableLiveData<CatImage>()

        service.getImage().enqueue(object : Callback<List<CatImage>> {
            override fun onResponse(
                call: Call<List<CatImage>>,
                response: Response<List<CatImage>>
            ) {
                println(response.body())
                data.postValue(response.body()?.first())
            }

            override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                println(t.message)
                //I'm feeling lucky!
            }
        })

        return data
    }

}