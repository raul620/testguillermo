package com.guillermocode.testpractico.ui.misproductos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guillermocode.testpractico.databinding.FragmentMisproductosBinding


class MisproductosFragment : Fragment() {

    private lateinit var misProductosViewModel: MisProductosViewModel
    private var _binding: FragmentMisproductosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        misProductosViewModel =
            ViewModelProvider(this).get(MisProductosViewModel::class.java)

        _binding = FragmentMisproductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMisproductos
        misProductosViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}