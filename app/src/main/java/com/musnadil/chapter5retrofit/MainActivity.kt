package com.musnadil.chapter5retrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.musnadil.chapter5retrofit.adapter.CarAdapter
import com.musnadil.chapter5retrofit.databinding.ActivityMainBinding
import com.musnadil.chapter5retrofit.model.GetAllCarResponseItem
import com.musnadil.chapter5retrofit.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fetchAllData()
        setupView()
    }

    private fun setupView() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
    }

    private fun fetchAllData() {
        ApiClient.instance.getAllCar()
            .enqueue(object : Callback<List<GetAllCarResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllCarResponseItem>>,
                    response: Response<List<GetAllCarResponseItem>>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showList(body)
                        binding.pbLoading.visibility = View.GONE
                    }else if(code >= 503){
                        val alertDialog = AlertDialog.Builder(this@MainActivity)
                        alertDialog.apply {
                            setTitle("Pemberitahuan")
                            setMessage("Mohon maaf saat ini server sedang sibuk, coba lagi nanti")
                            setPositiveButton("Baiklah"){dialog,wich ->
                                dialog.dismiss()
                                binding.pbLoading.visibility = View.GONE
                                finish()
                            }
                        }
                        alertDialog.show()
                    }else {
                        binding.pbLoading.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<List<GetAllCarResponseItem>>, t: Throwable) {
                    binding.pbLoading.visibility = View.GONE
                }

            })
    }

    private fun showList(data: List<GetAllCarResponseItem>?) {
        val adapter = CarAdapter(object : CarAdapter.OnClickListener{
            override fun onClickItem(data: GetAllCarResponseItem) {

            }
        })
        adapter.submitData(data)
        binding.rvList.adapter = adapter
    }
}