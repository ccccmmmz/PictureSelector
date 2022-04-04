package kotlinImpl.recycler_view

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.luck.pictureselector.R
import kotlinImpl.ext.ViewExtention.Companion.inflate

/**
 * ViewHolder
 */
abstract class ModelViewHolder<T> constructor ( var itemLayout: Int, parent: ViewGroup? = null) : RecyclerView.ViewHolder(itemLayout.inflate(parent)) {
    abstract fun bindAdapter(t: T, position: Int)

}

class PersonVh : ModelViewHolder<Person>(R.layout.item_person){
    override fun bindAdapter(t: Person, position: Int) {

    }

}

class StudentVh: ModelViewHolder<Student>(R.layout.item_person){
    override fun bindAdapter(t: Student, position: Int) {

    }

}