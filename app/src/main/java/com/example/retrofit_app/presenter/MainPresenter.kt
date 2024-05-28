package com.example.retrofit_app.presenter

import com.example.retrofit_app.model.Endereco
import com.example.retrofit_app.model.CepInterface
import com.example.retrofit_app.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainActivity, private val service: CepInterface) :
    MainContract.Presenter {

    private fun isValidCEP(cep: String): Boolean {
        return cep.length >= 8
    }

    override fun getAddressOrShowError(cep: String) {
        if (isValidCEP(cep)) {
            getAddress(cep)
        } else {
            view.showInvalidCEPError()
        }
    }

    override fun getAddress(cep: String) {
        val call = service.getAddress(cep)

        call.enqueue(object : Callback<Endereco> {
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                if (response.isSuccessful) {
                    val address = response.body()
                    address?.let {
                        val enderecoString = "CEP: ${address.cep}\n" +
                                "Logradouro: ${address.logradouro}\n" +
                                "Bairro: ${address.bairro}\n" +
                                "Cidade: ${address.localidade}\n" +
                                "Estado: ${address.uf}"

                        view.showResult(enderecoString)
                    }
                } else {
                    view.showError("CEP não encontrado")
                }
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                view.showError("Erro ao buscar CEP: ${t.message}")
            }
        })
    }
}
