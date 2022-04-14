package com.musnadil.chapter5retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.musnadil.chapter5retrofit.databinding.ActivityRegisterBinding
import com.musnadil.chapter5retrofit.model.PostRegisterResponse
import com.musnadil.chapter5retrofit.model.RegisterRequest
import com.musnadil.chapter5retrofit.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            btnDaftar.setOnClickListener{
                if (!etEmail.text.isNullOrEmpty() && !etPass.text.isNullOrEmpty()){
                    pbLoading.visibility = View.VISIBLE
                    registerAction(etEmail.text.toString(), etPass.text.toString())
                }else{
                    Toast.makeText(this@RegisterActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerAction(email : String, pass:String) {
        val request = RegisterRequest(email,pass,"admin")

        ApiClient.instance.postRegister(request)
            .enqueue(object : Callback<PostRegisterResponse>{
                override fun onResponse(
                    call: Call<PostRegisterResponse>,
                    response: Response<PostRegisterResponse>
                ) {
                    val code = response.code()
                    if (code == 201){
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                        finish()
                    }else if(code ==400){
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Email sudah digunakan", Toast.LENGTH_SHORT).show()
                    }else{
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Gagal registrasi", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostRegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }

            })
    }
}