package com.kamilagcabay.koincryptocrazyv2.Repository

import com.kamilagcabay.koincryptocrazyv2.Model.CryptoModel
import com.kamilagcabay.koincryptocrazyv2.Service.CryptoAPI
import com.kamilagcabay.koincryptocrazyv2.Util.Resource

class CryptoDownloadImpl(private val api : CryptoAPI): CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
        return try {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)

                } ?: Resource.error("No Data!!", null)
            } else {
                Resource.error("No Data Found!!", null)
            }

        }catch (e: Exception) {
            e.printStackTrace()
            Resource.error("No Data found",null)
        }
    }
}