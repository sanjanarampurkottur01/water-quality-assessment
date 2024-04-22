package com.example.b00957180.network
//
//            import okhttp3.ResponseBody
//            import retrofit2.Call
//            import retrofit2.Response
//            import retrofit2.http.Body
//            import retrofit2.http.POST
//
//            interface ApiService {
//                @POST("/query")
//                fun fetchData(@Body body: Map<String, String>): Call<Any>
//            }
//
import com.example.b00957180.model.FetchDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/query")
    fun fetchData(@Body body: Map<String, String>): Call<FetchDataResponse>
}
