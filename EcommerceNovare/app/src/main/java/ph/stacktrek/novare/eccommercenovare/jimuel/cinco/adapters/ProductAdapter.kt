package ph.stacktrek.novare.eccommercenovare.jimuel.cinco.adapters



import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.R
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.databinding.ProductItemBinding
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.Product
import java.io.File
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList


class ProductAdapter(private val context:Context,
                     private var productList:ArrayList<Product>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {





//    fun deleteProduct(position:Int){
//        productList.removeAt(position)
//        notifyItemRemoved(position)
//    }

    fun deleteProduct(position: Int) {
        if (position < productList.size) {
            productList.removeAt(position)
            notifyItemRemoved(position)
        } else {
            Log.e(TAG, "Index out of bounds: $position")
        }
    }

    fun getProductAtPosition(position: Int): Product {
        return productList[position]
    }

    fun addProduct(product: Product){
        productList.add(0, product)
        notifyItemInserted(0)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val productItemBinding = ProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(productItemBinding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItems(productList[position])

    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val productItemBinding: ProductItemBinding):
        RecyclerView.ViewHolder(productItemBinding.root){

        fun bindItems(product: Product){
            productItemBinding.productName.text = product.name
            productItemBinding.viewProductButton.text = product.name


            val imagePath = product.imagePath
            println("hesrses the path ${imagePath}")
            val file = File(imagePath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                productItemBinding.productImage.setImageBitmap(bitmap)
            } else {
                Log.e(TAG, "File not found: $imagePath")
            }

            productItemBinding.viewProductButton.setOnClickListener {
                Toast.makeText(context, "${product.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


