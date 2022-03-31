package com.android.willyrrhh.ui.viewmodel

import androidx.lifecycle.*
import com.android.willyrrhh.data.model.OompaLoompaPageItem
import com.android.willyrrhh.data.repository.OompaLoompaRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber


class OompaLoompasViewModel (
    private val oompaLoompaRepository: OompaLoompaRepository,
    ) : BaseViewModel() {

    private val _oompaLoompa: MutableLiveData<List<OompaLoompaPageItem>> = MutableLiveData(listOf())
    val oompaLoompa: LiveData<List<OompaLoompaPageItem>> = _oompaLoompa

    private val _oompaLoompas: MutableLiveData<List<OompaLoompaPageItem>> = MutableLiveData(listOf())
    val oompaLoompas: LiveData<List<OompaLoompaPageItem>> = _oompaLoompas

    private val _loadedFilter: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadedFilter: MutableLiveData<Boolean> = _loadedFilter

    private var oompaPage = 1


    fun loadOompaLoompa(id: Int) {

        viewModelScope.launch {
            val result = runCatching {
                val response =
                oompaLoompaRepository.fetchOneOompaLoompa(id)

                if (response != null) {
                    _oompaLoompa.value = mutableListOf(
                        OompaLoompaPageItem(
                            id,
                            response.first_name,
                            response.last_name,
                            response.height,
                            response.country,
                            response.age,
                            response.email,
                            response.profession,
                            response.image,
                            response.gender,
                            response.favorite
                        )
                    )
                }
            }

            val exception = result.exceptionOrNull()
            if (exception != null && exception !is CancellationException) {
                Timber.e(exception.message.toString())

            }
        }

    }


    fun loadOompaLoompas() {

        viewModelScope.launch {
            val result = runCatching {
                val response =
                oompaLoompaRepository.fetchAllOompaLoompas(oompaPage)?.results

                val list = _oompaLoompas.value?.toMutableList() ?: mutableListOf()
                if (response != null) {
                    list.addAll(response.map { it })

                    oompaPage += PAGE
                    _oompaLoompas.value = list
                }
            }

            val exception = result.exceptionOrNull()
            if (exception != null && exception !is CancellationException) {
                Timber.e(exception.message.toString())
            }
        }
    }

    private fun searchProfesion(oompaSearch: String) {

        viewModelScope.launch {

            val filteredList = _oompaLoompas.value?.filter { it.profession?.lowercase() == oompaSearch.lowercase() }

            if (filteredList != null) {
                if (filteredList.isNotEmpty()){

                    _oompaLoompas.value = filteredList!!

                }
            }

        }
    }

    private fun searchGenders(oompaSearch: String) {

        viewModelScope.launch {

            val filteredList = _oompaLoompas.value?.filter { it.gender?.lowercase() == oompaSearch.lowercase() }

            if (filteredList != null) {
                if (filteredList.isNotEmpty()){

                    _oompaLoompas.value = filteredList!!

                }
            }

        }
    }

    fun searchProfession(oompaSearch: String){
        searchProfesion(oompaSearch)
    }

    fun searchGender(oompaSearch: String){
        searchGenders(oompaSearch)
    }

    fun loadMoreOompas(loaded: Boolean, searchText: String, searchGender: String){
        _loadedFilter.value = loaded

        if(_loadedFilter.value == true &&
            searchText.isNotEmpty()){

            searchProfession(searchText)
        }
        if(_loadedFilter.value == true &&
            searchText.isEmpty() &&
            (searchGender.lowercase() == MALE || searchGender.lowercase() == FEMALE)){

            searchGender(searchGender)
        }
        if (searchGender.lowercase() != MALE && searchGender.lowercase() != FEMALE && searchText.length < MIN_SEARCH){
            loadOompaLoompas()
        }
    }

    companion object{
        const val PAGE = 1
        const val MALE = "m"
        const val FEMALE = "f"
        const val MIN_SEARCH = 4
    }

}