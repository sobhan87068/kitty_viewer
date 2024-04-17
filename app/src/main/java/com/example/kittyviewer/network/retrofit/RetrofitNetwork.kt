package com.example.kittyviewer.network.retrofit

import com.example.kittyviewer.network.NetworkDataSource
import com.example.kittyviewer.network.model.RemoteKitty
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {

    @GET("v1/images/search")
    suspend fun getKittiesList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Result<List<RemoteKitty>>
}

private const val BASE_URL = "https://api.thecatapi.com/"

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
    resultCallAdapterFactory: ResultCallAdapterFactory
) : NetworkDataSource {

    private val kittiesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .addCallAdapterFactory(resultCallAdapterFactory)
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getKittiesList(page: Int): Result<List<RemoteKitty>> {
        return kittiesApi.getKittiesList(page)
    }
}