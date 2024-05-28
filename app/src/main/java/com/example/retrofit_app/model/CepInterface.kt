package com.example.retrofit_app.model

import com.example.retrofit_app.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepInterface {

    @GET("{cep}/json/")
    fun getAddress(@Path("cep") cep: String): Call<Endereco>

}