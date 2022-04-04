package kotlinImpl.recycler_view

import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class CommonAdapter<T> : RecyclerView.Adapter<ModelViewHolder<T>>() {

    private val TAG = "CommonAdapter"

    //adapter data source
    private val mDataSource by lazy { ArrayList<T>() }

    private val mRegistryMap by lazy { HashMap<Class<*>, Int>() }

    private val mItemViewTypeMap by lazy { SparseArray<Class<*>>() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<T> {
        val modelViewHolder = mItemViewTypeMap.get(viewType)
        modelViewHolder?.let {
            it::class.java.getDeclaredConstructor(
                Int::class.java,
                ViewGroup::class.java
            ).newInstance(viewType, parent)
        }

        return super.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ModelViewHolder<T>, position: Int) {
        holder.bindAdapter(mDataSource[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        //data type
        val data = mDataSource[position]
        //get registryType
        data?.apply {
            val javaClass = javaClass
            Log.d(TAG, "getItemViewType:  emmit $javaClass")
            val itemView = mRegistryMap[javaClass]
            itemView?.let {
                return@let
            }
        }

        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(
        holder: ModelViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.size != 0) {
            // pending payload
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return mDataSource.size
    }

    fun addData(data: T){
        mDataSource.add(data)
        notifyItemInserted(mDataSource.size - 1)
    }

    fun addData(data: Collection<T>?){
        val appendSize = data?.size?: return
        if (appendSize == 0) {
            return
        }
        mDataSource.addAll(data)
        notifyItemRangeInserted(mDataSource.size - appendSize, appendSize)
    }

    fun addData(data: Collection<T>?, position: Int){
        val appendSize = data?.size?: return
        if (appendSize == 0) {
            return
        }
        mDataSource.addAll(position, data)
        notifyItemRangeInserted(position, appendSize)
    }

    fun regisModelRegistry(clazz: Class<*>, @LayoutRes layoutRes: Int) {
        mRegistryMap[clazz] = layoutRes
        mItemViewTypeMap.append(layoutRes, clazz)
    }


    fun addHead(@LayoutRes layoutRes: Int){

    }

    fun addHead(view: View){

    }

}