package com.guillermocode.testpractico.ui.articulos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guillermocode.testpractico.R
import com.guillermocode.testpractico.RecyclerAdapter
import com.guillermocode.testpractico.databinding.FragmentArticulosBinding
import kotlinx.android.synthetic.main.fragment_articulos.*


class ArticulosFragment : Fragment() {

    private lateinit var articulosViewModel: ArticulosViewModel
    private var _binding: FragmentArticulosBinding? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        _binding = FragmentArticulosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return  root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}