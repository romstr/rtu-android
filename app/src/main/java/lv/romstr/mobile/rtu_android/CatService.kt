package lv.romstr.mobile.rtu_android

import retrofit2.Call
import retrofit2.http.GET

interface CatService {

    @GET("images/search")
    fun getImage(): Call<List<CatImage>>

}