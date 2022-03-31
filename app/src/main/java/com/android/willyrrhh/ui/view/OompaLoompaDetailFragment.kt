package com.android.willyrrhh.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import coil.load
import com.android.willyrrhh.R
import com.android.willyrrhh.data.model.OompaLoompaPageItem
import com.android.willyrrhh.databinding.DetailFragmentOompaloompaBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class OompaLoompaDetailFragment : Fragment() {

    private var _binding: DetailFragmentOompaloompaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var oompasList: MutableList<OompaLoompaPageItem?> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DetailFragmentOompaloompaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments().getParcelable<OompaLoompaPageItem>(PARCELABLE_ARGS_OOMPA)

        args.let {
            val words: MutableList<OompaLoompaPageItem?> = mutableListOf(it)
            oompasList = words
        }

        args?.let { oompa ->

            binding.tvName.text = (oompa.first_name+" "+oompa.last_name).toUpperCase()
            binding.ivImage.load(oompa.image) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                fallback(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }
            binding.tvId.text = oompa.id.toString()
            binding.tvAge.text = oompa.age.toString()
            binding.ivGender.setImageResource(if (oompa.gender == "F") R.drawable.ic_female else R.drawable.ic_male)
            binding.tvGender.text = oompa.gender
            binding.tvHeight.text = "${oompa.height}cm"
            binding.tvProfession.text = oompa.profession
            binding.tvCountry.text = oompa.country
            binding.tvEmail.text = oompa.email
            binding.tvFavColor.text = oompa.favorite?.color?.capitalize(java.util.Locale.getDefault()) ?: "Not defined"
            binding.tvFavFood.text = oompa.favorite?.food?.capitalize(java.util.Locale.getDefault()) ?: "Not defined"
            binding.tvFavRandomString.text = oompa.favorite?.random_string ?: "Not defined"
            binding.tvFavSong.text = oompa.favorite?.song ?: "Not defined"

            var isFavRandomStringClicked = false
            binding.tvFavRandomString.setOnClickListener {
                if (isFavRandomStringClicked) {
                    (it as TextView).maxLines = 3
                    isFavRandomStringClicked = false
                } else {
                    (it as TextView).maxLines = Integer.MAX_VALUE
                    isFavRandomStringClicked = true
                }
            }

            var isFavSongStringClicked = false
            binding.tvFavSong.setOnClickListener {
                if (isFavSongStringClicked) {
                    (it as TextView).maxLines = 3
                    isFavSongStringClicked = false
                } else {
                    (it as TextView).maxLines = Integer.MAX_VALUE
                    isFavSongStringClicked = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PARCELABLE_ARGS_OOMPA = "argsOompa"
    }
}