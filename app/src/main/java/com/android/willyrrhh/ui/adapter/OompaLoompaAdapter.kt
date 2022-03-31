package com.android.willyrrhh.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.willyrrhh.R
import com.android.willyrrhh.data.model.OompaLoompaPageItem
import com.android.willyrrhh.databinding.ItemOompaLoompaBinding

class OompaLoompaAdapter(
    private val viewBusinessClickListener: (OompaLoompaPageItem) -> Unit,
) : ListAdapter<OompaLoompaPageItem, OompaLoompaAdapter.BusinessViewHolder>(ListDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BusinessViewHolder {
        return BusinessViewHolder(
            binding = ItemOompaLoompaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            viewBusinessClickListener = viewBusinessClickListener
        )
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BusinessViewHolder(
        private val binding: ItemOompaLoompaBinding,
        private val viewBusinessClickListener: (OompaLoompaPageItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var uiModel: OompaLoompaPageItem

        init {
            binding.root.setOnClickListener {
                viewBusinessClickListener.invoke(uiModel)
            }
        }

        fun bind(item: OompaLoompaPageItem) {
            uiModel = item

            binding.tvName.text = ("${uiModel.id} ${uiModel.first_name} ${uiModel.last_name}").toUpperCase()
            binding.ivImage.load(uiModel.image) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                fallback(R.drawable.ic_launcher_foreground)
                error(R.drawable.ic_launcher_foreground)
            }
            binding.tvAge.text = uiModel.age.toString()+"y/o"
            binding.ivGender.setImageResource(if (uiModel.gender == "F") R.drawable.ic_female else R.drawable.ic_male)
            binding.tvProfession.text = uiModel.profession
        }

    }

    object ListDiffUtil : DiffUtil.ItemCallback<OompaLoompaPageItem>() {
        override fun areItemsTheSame(
            oldItem: OompaLoompaPageItem,
            newItem: OompaLoompaPageItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OompaLoompaPageItem,
            newItem: OompaLoompaPageItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
