package com.calseto.firebasedefinitivo.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.datosemociones.EmployeModel
import com.calseto.firebasedefinitivo.R

class EmployeeAdapter(private val empList: ArrayList<EmployeModel>): RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener



    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
        val currenEmp = empList[position]
        holder.tvEmpNombre.text = currenEmp.nombreEmpleado
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tvEmpNombre : TextView = itemView.findViewById(R.id.txtNombreEmpleado)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = empList.size
}





