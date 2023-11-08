package com.kamilagcabay.koincryptocrazyv2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kamilagcabay.koincryptocrazyv2.Adapter.CryptoAdapter
import com.kamilagcabay.koincryptocrazyv2.Model.CryptoModel
import com.kamilagcabay.koincryptocrazyv2.R
import com.kamilagcabay.koincryptocrazyv2.ViewModel.CryptoViewModel
import com.kamilagcabay.koincryptocrazyv2.databinding.FragmentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(),CryptoAdapter.Listener {
    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CryptoViewModel>()
    private var cryptoAdapter = CryptoAdapter(arrayListOf(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.cryptoList.layoutManager = layoutManager

        viewModel.getDataFromAPI()
        observeLiveData()

    }

    fun observeLiveData() {

        viewModel.cryptoList.observe(viewLifecycleOwner, Observer{crypto ->

            crypto?.let {
                binding.cryptoList.visibility = View.VISIBLE
                cryptoAdapter = CryptoAdapter(ArrayList(crypto.data ?: arrayListOf()), this@ListFragment)
                binding.cryptoList.adapter = cryptoAdapter
            }

        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading ->

            loading?.let {
                if (it.data == true) {
                    binding.cryptoErrorText.visibility = View.GONE
                    binding.cryptoList.visibility = View.GONE
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }

        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if (it.data == true) {
                    binding.cryptoList.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.VISIBLE
                }else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }

        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked On : ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}