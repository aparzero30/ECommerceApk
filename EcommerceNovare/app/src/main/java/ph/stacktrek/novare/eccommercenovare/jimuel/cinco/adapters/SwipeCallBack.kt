package ph.stacktrek.novare.eccommercenovare.jimuel.cinco.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.ProductDAO
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.ProductDAOSQLLiteImplementation

class SwipeCallback (dragDirs: Int, swipeDirs: Int, private val context: Context) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    var productAdapter:ProductAdapter? = null
    private lateinit var productDAO: ProductDAO

    var background: ColorDrawable = ColorDrawable(Color.BLACK)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val productToDelete = productAdapter?.getProductAtPosition(position)
        productAdapter!!.deleteProduct(position)
        val productDAO = ProductDAOSQLLiteImplementation(context)
        if (productToDelete != null) {
            productDAO.deleteProduct(productToDelete.id)
        }





            }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        super.onChildDraw(
            canvas, recyclerView, viewHolder,
            dX, dY, actionState, isCurrentlyActive
        )

        val itemView = viewHolder.itemView

        if (dX > 0) { // Swiping to the right
            background = ColorDrawable(Color.parseColor("#212328"))
            background.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            background = ColorDrawable(Color.parseColor("#212328"))
            background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(canvas)
    }
}