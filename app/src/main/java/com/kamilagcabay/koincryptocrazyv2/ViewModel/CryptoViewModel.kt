package com.kamilagcabay.koincryptocrazyv2.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamilagcabay.koincryptocrazyv2.Model.CryptoModel
import com.kamilagcabay.koincryptocrazyv2.Repository.CryptoDownload
import com.kamilagcabay.koincryptocrazyv2.Util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(private val cryptoDownloadRepository : CryptoDownload) : ViewModel() {


    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()

    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "Error",true)
    }

    fun getDataFromAPI() {
        cryptoLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()

            withContext(Dispatchers.Main) {
                resource.data?.let {
                    cryptoList.value = resource
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}