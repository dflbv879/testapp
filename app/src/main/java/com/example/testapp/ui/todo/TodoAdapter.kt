package com.example.testapp.ui.todo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testapp.R
import com.example.testapp.data.entities.TodoUserItem
import com.example.testapp.databinding.ItemTodoBinding

class TodoAdapter(private val listener: TodoItemListener) : RecyclerView.Adapter<TodoViewHolder>() {

    interface TodoItemListener {
        fun onClickedTodo(todoId: Int)
    }

    private val items = ArrayList<TodoUserItem>()

    fun setItems(items: ArrayList<TodoUserItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) = holder.bind(items[position])
}

class TodoViewHolder(private val itemBinding: ItemTodoBinding, private val listener: TodoAdapter.TodoItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var todoItem: TodoUserItem

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: TodoUserItem) {
        this.todoItem = item
        itemBinding.todoUsername.text = item.user?.username
        itemBinding.todoId.text = item.todo?.id.toString()
        itemBinding.todoStatus.text = item.todo?.completed.toString()
        itemBinding.todoTitle.text = item.todo?.title
        Glide.with(itemBinding.root)
            .load(R.drawable.icon)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        todoItem.user?.id?.let { listener.onClickedTodo(it) }
    }
}

