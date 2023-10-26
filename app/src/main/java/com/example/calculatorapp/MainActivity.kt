package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.calculatorapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
            binding.buttonCalculate.setOnClickListener {
                val input = binding.editTextInput.text.toString()
                if (input.startsWith('0') || input.isEmpty()) {
                    Snackbar.make(binding.root, "შეიყვანეთ ვალიდური ციფრი!!!", Snackbar.LENGTH_SHORT).show()
                } else {
                    val inputInt = input.toInt()
                    val result = convertToGeorgianWords(inputInt)
                    binding.textView.text = result.toString()
                }
            }



    }


    private fun convertToGeorgianWords(number: Int): Any {
        val units = arrayOf("", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა", "ათი",
            "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")
        val tens = arrayOf("ოცდა", "ორმოცდა", "სამოცდა", "ოთხმოცდა")
        val roundedTens = arrayOf("", "", "ოცი", "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი")
        val roundedHundreds = arrayOf("", "ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი")
        val hundreds = arrayOf("", "ას", "ორას", "სამას", "ოთხას", "ხუთას", "ექვსას", "შვიდას", "რვაას", "ცხრაას")
        return when {
            number == 1000 -> "ათასი"
            number < 0 || number > 1000 -> "შეიყვანეთ ვალიდური ციფრი!!!"
            number == 0 -> "ნული"
            number <= 20 -> units[number]
            number % 10 == 0 && number < 100 -> roundedTens[number / 10]
            number in 21..39 -> tens[0] + units[number - 20]
            number in 41..59 -> tens[1] + units[number - 40]
            number in 61..79 -> tens[2] + units[number - 60]
            number in 81..99 -> tens[3] + units[number - 80]
            number % 100 == 0 -> roundedHundreds[number / 100]
            else -> {
                val hundred = hundreds[number / 100]
                val remainder = number % 100
                val remainderInWords =
                    if (remainder > 0)
                        convertToGeorgianWords(remainder)
                    else ""
                        "$hundred $remainderInWords"
            }
        }
    }
}