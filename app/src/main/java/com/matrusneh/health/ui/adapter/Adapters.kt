package com.matrusneh.health.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.matrusneh.health.R
import com.matrusneh.health.data.DangerSign
import com.matrusneh.health.data.NutritionItem
import com.matrusneh.health.data.WeekInfo
import com.matrusneh.health.data.entity.CheckupEvent
import com.matrusneh.health.databinding.*
import com.matrusneh.health.util.DateUtils

class NutritionAdapter(
    private val items: List<NutritionItem>,
    private val onToggle: (String) -> Unit
) : RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {

    private var checkedItems = emptySet<String>()

    fun updateChecked(checked: Set<String>) {
        this.checkedItems = checked
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNutritionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, checkedItems.contains(item.id))
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemNutritionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NutritionItem, isChecked: Boolean) {
            binding.tvItemEmoji.text = item.emoji
            binding.tvItemName.text = "${item.name} / ${item.name_kn}"
            binding.tvItemBenefit.text = item.benefit
            binding.cbNutrition.isChecked = isChecked
            
            val color = if (isChecked) R.color.teal_light else R.color.white
            binding.cardNutritionItem.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, color))

            binding.root.setOnClickListener { onToggle(item.id) }
        }
    }
}

class CheckupAdapter(
    private val onMarkComplete: (Long) -> Unit,
    private val onDelete: (CheckupEvent) -> Unit
) : ListAdapter<CheckupEvent, CheckupAdapter.ViewHolder>(CheckupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCheckupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCheckupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: CheckupEvent) {
            binding.tvItemCheckupTitle.text = event.title
            binding.tvItemCheckupDate.text = DateUtils.toDisplayDate(event.checkupDateMs)
            
            if (event.isCompleted) {
                binding.tvItemCheckupBadge.text = "Completed"
                binding.tvItemCheckupBadge.setBackgroundResource(R.color.teal_light)
                binding.btnItemComplete.visibility = ViewGroup.GONE
            } else {
                val days = DateUtils.daysFromNow(event.checkupDateMs)
                binding.tvItemCheckupBadge.text = if (days == 0L) "Today" else "$days Days"
                binding.btnItemComplete.visibility = ViewGroup.VISIBLE
            }

            binding.btnItemComplete.setOnClickListener { onMarkComplete(event.id) }
            binding.btnItemDelete.setOnClickListener { onDelete(event) }
        }
    }

    class CheckupDiffCallback : DiffUtil.ItemCallback<CheckupEvent>() {
        override fun areItemsTheSame(oldItem: CheckupEvent, newItem: CheckupEvent): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CheckupEvent, newItem: CheckupEvent): Boolean = oldItem == newItem
    }
}

class GrowthAdapter(
    private val weeks: List<WeekInfo>,
    private val onWeekClick: (Int) -> Unit
) : RecyclerView.Adapter<GrowthAdapter.ViewHolder>() {

    private var currentWeek: Int = -1

    fun setCurrentWeek(week: Int) {
        this.currentWeek = week
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGrowthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weeks[position])
    }

    override fun getItemCount(): Int = weeks.size

    inner class ViewHolder(private val binding: ItemGrowthBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(week: WeekInfo) {
            binding.tvItemWeekNum.text = week.week.toString()
            binding.tvItemSizeAnalogy.text = "${week.sizeAnalogy} / ${week.sizeAnalogy_kn}"
            binding.tvItemWeightRange.text = week.weightRange
            
            val color = if (week.week == currentWeek) R.color.teal_light else android.R.color.transparent
            binding.cardGrowthItem.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, color))

            binding.root.setOnClickListener { onWeekClick(week.week) }
        }
    }
}

class DangerSignAdapter(
    private val signs: List<DangerSign>,
    private val onSignClick: (DangerSign) -> Unit
) : RecyclerView.Adapter<DangerSignAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDangerSignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(signs[position])
    }

    override fun getItemCount(): Int = signs.size

    inner class ViewHolder(private val binding: ItemDangerSignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sign: DangerSign) {
            binding.tvDangerEmoji.text = sign.emoji
            binding.tvDangerName.text = sign.name
            binding.tvDangerNameKn.text = sign.name_kn
            binding.tvDangerSeverityBadge.text = sign.severity
            binding.tvDangerSeverityBadge.setBackgroundResource(
                if (sign.severity == "HIGH") R.color.red_500 else R.color.amber_500
            )

            binding.root.setOnClickListener { onSignClick(sign) }
        }
    }
}

class DangerLogAdapter : ListAdapter<com.matrusneh.health.data.entity.DangerSignLog, DangerLogAdapter.ViewHolder>(DangerLogDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCheckupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(private val binding: ItemCheckupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(log: com.matrusneh.health.data.entity.DangerSignLog) {
            binding.tvItemCheckupTitle.text = log.signName
            binding.tvItemCheckupDate.text = DateUtils.toTime(log.loggedAt)
            binding.tvItemCheckupBadge.text = log.severity
            binding.btnItemComplete.visibility = ViewGroup.GONE
            binding.btnItemDelete.visibility = ViewGroup.GONE
        }
    }
    class DangerLogDiffCallback : DiffUtil.ItemCallback<com.matrusneh.health.data.entity.DangerSignLog>() {
        override fun areItemsTheSame(oldItem: com.matrusneh.health.data.entity.DangerSignLog, newItem: com.matrusneh.health.data.entity.DangerSignLog): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: com.matrusneh.health.data.entity.DangerSignLog, newItem: com.matrusneh.health.data.entity.DangerSignLog): Boolean = oldItem == newItem
    }
}

