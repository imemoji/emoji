package com.imvc.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.imvc.emoji.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var files = Utility.listAssetsFiles(requireContext(), recursive = false)
        val linearLayoutContainer =this.binding.linearLayoutContainer

        for (file in files) {
            // 添加 TextView
            // 添加 Button
            val button = Button(requireContext())
            button.text = "--- "+file +" ---"
            button.setOnClickListener {
                var bunddle = Bundle()
                bunddle.putString("folder",file)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bunddle)
            }
            linearLayoutContainer.addView(button)
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}