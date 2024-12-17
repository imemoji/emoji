package com.imvc.emoji

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
//import com.bumptech.glide.Glide
import com.imvc.emoji.databinding.FragmentSecondBinding
import java.io.File

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var folder = arguments?.getString("folder")
        if(folder!= null && !folder.isEmpty())
        {
            val files = Utility.listAssetsFiles(requireContext(),folder)
            val imagesPerRow = 3  // 3 images per row
            var currentRow: LinearLayout? = null
            var index = 0
            for (file in files) {
                // Create a new row every 3 images
                if (index % imagesPerRow == 0) {
                    currentRow = LinearLayout(requireContext())
                    currentRow.orientation = LinearLayout.HORIZONTAL
                    currentRow.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    binding.linearLayoutContainer.addView(currentRow)
                }
                val imageView = ImageView(requireContext())
                // Set layout parameters for proportional scaling
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                params.setMargins(8, 8, 8, 8)
                imageView.layoutParams = params

                // Set scaleType to fit the image within the available width while maintaining aspect ratio
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER  // Ensures proportional scaling without distortion


                Glide.with(requireContext())
                    .load(Uri.parse("file:///android_asset/"+file))
                    .into(imageView)

                currentRow?.addView(imageView)
                index++
            }
            adjustLastRowIfNeeded(binding.linearLayoutContainer,imagesPerRow)

        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    // Function to adjust the last row to evenly distribute images if there are fewer than `imagesPerRow`
    private fun adjustLastRowIfNeeded(container: LinearLayout, imagesPerRow: Int) {
        val rows = container.children.toList() // Get all rows in the container
        val lastRow = rows.lastOrNull() as LinearLayout?

        // If the last row has fewer images than `imagesPerRow`, adjust the width of each image in that row
        if (lastRow != null && lastRow.childCount < imagesPerRow) {
            val remainingImages = lastRow.childCount
            val newWeight = 1f / remainingImages

            for (i in 0 until remainingImages) {
                val imageView = lastRow.getChildAt(i) as ImageView
                val params = imageView.layoutParams as LinearLayout.LayoutParams
                params.weight = newWeight
                imageView.layoutParams = params
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}