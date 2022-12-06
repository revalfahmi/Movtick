package com.example.movtick.home.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movtick.R
import com.example.movtick.utils.Preferences

class SettingFragment : Fragment() {
    private lateinit var ivProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView

    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ivProfile = requireView().findViewById(R.id.iv_profile)
        tvName = requireView().findViewById(R.id.tv_name)
        tvEmail = requireView().findViewById(R.id.tv_email)

        preferences = Preferences(requireContext())

        tvName.text = preferences.getValue("nama")
        tvEmail.text = preferences.getValue("email")

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(ivProfile)

    }

}