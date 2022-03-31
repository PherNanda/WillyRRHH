package com.android.willyrrhh.ui.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.willyrrhh.R
import com.android.willyrrhh.databinding.FragmentOompaloompasBinding
import com.android.willyrrhh.ui.adapter.OompaLoompaAdapter
import com.android.willyrrhh.ui.util.RecyclerViewLoadMoreListener
import com.android.willyrrhh.ui.viewmodel.OompaLoompasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OompaLoompasFragment : Fragment() {

    private var _binding: FragmentOompaloompasBinding? = null
    private val viewModel by viewModel<OompaLoompasViewModel>()

    private lateinit var listAdapter: OompaLoompaAdapter
    private var searchProfession = ""
    private var searchGender = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOompaloompasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.oompasInput.isEnabled = false

        loadOompa(1)
        loadOompas()

        listAdapter =
            OompaLoompaAdapter(
                viewBusinessClickListener = {
                    bundleOf(OompaLoompaDetailFragment.PARCELABLE_ARGS_OOMPA to it).apply {
                        findNavController().navigate(
                            R.id.action_FirstFragment_to_SecondFragment,
                            this
                        )
                        it
                    }
                }
            )

        binding.rvOompas.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = listAdapter

            addOnScrollListener(
                RecyclerViewLoadMoreListener(
                    loadMore = {
                        viewModel.loadMoreOompas(
                            loaded = true,
                            searchText = searchProfession?: "",
                            searchGender = searchGender?: ""
                        )
                    }
                )
            )

            itemAnimator = null
        }
        listAdapter.hasObservers()

        viewModel.oompaLoompas.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
            return@observe
        }


        search()

        showMenuSearch()

}

    //search
    private fun search(){
        val inputProfession = binding.oompasInput.text
        binding.oompasLayout.editText?.text = inputProfession

        var value: String
        binding.oompasInput.doAfterTextChanged { input ->

            if(input != null && input.isNotEmpty()){
                value = binding.oompasInput.text.toString()

                if(value.isNotEmpty() && value.length > MIN_SEARCH) {
                    viewModel.searchProfession(value.lowercase())
                    searchProfession = value
                    searchGender = EMPTY
                }
                if(value.isNotEmpty() && value.length == GENDER_SEARCH &&
                    binding.titleTxt.text != getString(R.string.profession_menu)) {

                    viewModel.searchGender(value.lowercase())
                    searchGender = value
                    searchProfession = EMPTY

                }
                if(value.isEmpty() || value.length < MIN_SEARCH) {
                    loadOompas()

                }
                if(value.isEmpty()) {
                    binding.oompasInput.isEnabled = false

                }
            }
        }
    }

    private fun showMenuSearch() {
        binding.popMenuSearch.setOnClickListener {
            binding.oompasInput.isEnabled = true

            val popupMenu: PopupMenu = PopupMenu(requireContext(),binding.popMenuSearch)
            popupMenu.menuInflater.inflate(R.menu.menu_search,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.profession -> binding.titleTxt.text = item.title
                    R.id.gender -> binding.titleTxt.text = item.title
                }
                true
            })
            popupMenu.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadOompa(id: Int){
        viewModel.loadOompaLoompa(id)
    }

    private fun loadOompas(){
        viewModel.loadOompaLoompas()
    }

    companion object{
        const val MIN_SEARCH = 4
        const val GENDER_SEARCH = 1
        const val MALE = "m"
        const val FEMALE = "f"
        const val EMPTY = ""
    }

}