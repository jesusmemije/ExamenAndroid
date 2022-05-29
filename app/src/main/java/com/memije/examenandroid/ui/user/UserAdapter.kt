package com.memije.examenandroid.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memije.examenandroid.R
import com.memije.examenandroid.databinding.UserItemBinding
import com.memije.examenandroid.room.entity.UserEntity

class UserAdapter(userList: List<UserEntity>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var mUserList: List<UserEntity> = userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        // Pasamos el objeto de UserResponse
        holder.user = mUserList[position]
        // Iniciamos la función data
        holder.initData(holder.user)
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vBinding = UserItemBinding.bind(itemView)
        lateinit var user: UserEntity

        @SuppressLint("SetTextI18n")
        fun initData(mUser: UserEntity) {
            vBinding.userName.text = mUser.name
            vBinding.userEmailPhone.text = mUser.email + " | " + mUser.phone
            vBinding.userAddress.text = mUser.address
        }
    }
}