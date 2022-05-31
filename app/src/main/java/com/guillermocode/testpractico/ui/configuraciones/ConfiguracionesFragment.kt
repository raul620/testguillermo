package com.guillermocode.testpractico.ui.configuraciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guillermocode.testpractico.databinding.FragmentConfiguracionesBinding


class ConfiguracionesFragment : Fragment() {

    private lateinit var configuracionesViewModel: ConfiguracionesViewModel
    private var _binding: FragmentConfiguracionesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configuracionesViewModel =
            ViewModelProvider(this).get(ConfiguracionesViewModel::class.java)

        _binding = FragmentConfiguracionesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textConfiguraciones
        configuracionesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}