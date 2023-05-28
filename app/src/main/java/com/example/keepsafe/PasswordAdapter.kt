package com.example.keepsafe

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.keepsafe.PasswordAdapter.ViewHolder2
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.RelativeLayout
import android.content.Intent
import android.view.View
import java.util.ArrayList

class PasswordAdapter(passList: MutableList<Password?>, context: Context) :
    RecyclerView.Adapter<ViewHolder2>() {
    var mPasswordList: List<Password> = ArrayList()
    var mContext: Context

    init {
        mPasswordList = passList
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.password_layout, parent, false)
        return ViewHolder2(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.account.text = mPasswordList[position].account
    }

    override fun getItemCount(): Int {
        return mPasswordList.size
    }

    internal inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var account: TextView
        var passwordRelLayout: RelativeLayout

        init {
            account = itemView.findViewById(R.id.passwordTag)
            passwordRelLayout = itemView.findViewById(R.id.passwordView)
            itemView.setOnClickListener {
                val passData = mPasswordList[adapterPosition]
                val genPassIntent = Intent(mContext, ViewOrDeletePassword::class.java)
                genPassIntent.putExtra("pId", passData.pId)
                genPassIntent.putExtra("account", passData.account)
                genPassIntent.putExtra("password", passData.pass)
                mContext.startActivity(genPassIntent)
            }
        }
    }
}