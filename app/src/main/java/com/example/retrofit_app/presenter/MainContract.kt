package com.example.retrofit_app.presenter
import com.example.retrofit_app.model.Endereco

interface MainContract {
        interface View {
            fun showResult(endereco: String)
            fun showError(message: String)
            fun showInvalidCEPError()
        }

        interface Presenter {
            fun getAddress(cep: String)
            fun getAddressOrShowError(cep: String)
        }
}