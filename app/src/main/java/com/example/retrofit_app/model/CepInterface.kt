package com.example.retrofit_app.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CepInterface {

    @GET("{cep}/json/")
    fun getAddress(@Path("cep") cep: String): Single<Endereco>

}