package com.kamilagcabay.koincryptocrazyv2.Repository

import com.kamilagcabay.koincryptocrazyv2.Model.CryptoModel
import com.kamilagcabay.koincryptocrazyv2.Util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}