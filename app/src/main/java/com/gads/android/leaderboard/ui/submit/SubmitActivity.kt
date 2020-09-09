package com.gads.android.leaderboard.ui.submit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.gads.android.leaderboard.LearnerHttpClient
import com.gads.android.leaderboard.R
import com.gads.android.leaderboard.network.LeaderboardService
import kotlinx.android.synthetic.main.activity_submit.*
import kotlinx.android.synthetic.main.submit_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class SubmitActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        initToolbar()
        dialog = Dialog(this)
        submit_button.setOnClickListener(this)
    }

    private fun initToolbar() {
        setSupportActionBar(submit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.submit_button -> {
                if (!isEmpty()) {
                    showSubmitDialog()
                } else{
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showSubmitDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this)
            .inflate(R.layout.confirm_dialog, findViewById(android.R.id.content), false)
        dialogBuilder.setView(view)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        view.findViewById<Button>(R.id.okay_button).setOnClickListener{
            submitProject()
            alertDialog.dismiss()
        }
        view.findViewById<ImageView>(R.id.cancel).setOnClickListener{
            form_layout.visibility = View.VISIBLE
            alertDialog.dismiss()
        }
        alertDialog.show()
        form_layout.visibility = View.INVISIBLE
    }

    private fun isEmpty(): Boolean{
        val firstName = et_first_name.text.trim().toString()
        val lastName = et_last_name.text.trim().toString()
        val email = et_email.text.trim().toString()
        val projectLink = et_project_link.text.trim().toString()
        return (TextUtils.isEmpty(firstName)
            || TextUtils.isEmpty(lastName)
            || TextUtils.isEmpty(email)
            || TextUtils.isEmpty(projectLink)
        )
    }

    private fun submitProject(){
        progressBar.visibility = View.VISIBLE
        val firstName = et_first_name.text.trim().toString()
        val lastName = et_last_name.text.trim().toString()
        val email = et_email.text.trim().toString()
        val projectLink = et_project_link.text.trim().toString()

        val retrofit =
            LearnerHttpClient.getSubmitRetrofitService()
        val service = retrofit.create(LeaderboardService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val response =
                service.submitProject(firstName, lastName, email, projectLink).awaitResponse()

            if (response.isSuccessful) {
                Log.d("tag", "Response: ${response.code()}")
                withContext(Dispatchers.Main) {
                    displaySuccessDialog()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.d("tag", "Response: ${response.code()}")
                    displayErrorDialog()
                }
            }
        }

        et_first_name.setText("")
        et_last_name.setText("")
        et_email.setText("")
        et_project_link.setText("")
    }

    private fun displaySuccessDialog(){
        progressBar.visibility = View.INVISIBLE
        dialog.setContentView(R.layout.success_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        Handler().postDelayed({
            form_layout.visibility = View.VISIBLE
            dialog.dismiss()
        }, 2000)
    }

    private fun displayErrorDialog(){
        progressBar.visibility = View.INVISIBLE
        dialog.setContentView(R.layout.error_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        Handler().postDelayed({
            form_layout.visibility = View.VISIBLE
            dialog.dismiss()
        }, 2000)
    }
}
