package com.example.workmanager


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.example.workmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var request: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //text view içerisindeki saat sayısını azaltmak için bakıyoruz
        binding.downButton.setOnClickListener {
            var hour = binding.hourTextView.text.toString().toInt()
            if (hour > 1)
                hour--
            else
                Toast.makeText(
                    applicationContext,
                    "Saat aralığı 1'den az olamaz!",
                    Toast.LENGTH_SHORT
                ).show()
            binding.hourTextView.text = hour.toString()
        }

        //text view içerisindeki saat sayısını arttırıyoruz
        binding.upButton.setOnClickListener {
            var hour = binding.hourTextView.text.toString().toInt()
            hour++
            binding.hourTextView.text = hour.toString()
        }

        //WorkManagerı set ediyoruz, BookWorker çağırılacak
        binding.okeyButton.setOnClickListener {
            val hour = binding.hourTextView.text.toString().toInt()

            val workCondition = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            //Periyodik bir yapı oluşturmak istediğimiz için PeriodicWorkRequestBuilder kullanıyoruz.
            //Tek seferlik bir hatırtıcı kurmak isteseydik, OneTimeWorkRequest kullanırdık.
            //BookWorker classımızı dahil ederek zamanladık
            request = PeriodicWorkRequestBuilder<BookWorker>(hour.toLong(), TimeUnit.HOURS)
                .setConstraints(workCondition)
                .build()

            //WorkManager classı yardımıyla requestimizi attık
            WorkManager.getInstance(this@MainActivity)
                .enqueue(request)
        }
    }
}