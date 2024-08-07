package com.drinkwater

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.renderscript.ScriptGroup.Binding
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.drinkwater.databinding.ActivityMainBinding
import com.drinkwater.model.CalcTotalWater
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private lateinit var edit_weight: EditText
    private lateinit var edit_age: EditText
    private lateinit var bt_calc: Button
    private lateinit var txt_result_ml: TextView
    private lateinit var ic_refresh: ImageView
    private lateinit var bt_reminder: Button
    private lateinit var bt_alarm : Button
    private lateinit var txt_hour: TextView
    private lateinit var txt_min: TextView

    private lateinit var calcTotalWaterDay: CalcTotalWater
    private var resultCurrentML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var checkCalendar: Calendar
    var currentHour = 0
    var currentMins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //startComponents()
        calcTotalWaterDay = CalcTotalWater()

        binding.btCalc.setOnClickListener {
            if(binding.editWeight.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_info_weight, Toast.LENGTH_SHORT).show()
            }else if(binding.editAge.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_info_age, Toast.LENGTH_SHORT).show()
            }else{
                val newWeight = binding.editWeight.text.toString().toDouble()
                val newAge= binding.editAge.text.toString().toInt()
                calcTotalWaterDay.calcTotalML(newWeight, newAge)
                resultCurrentML = calcTotalWaterDay.resultMl()
                val formatBR = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatBR.isGroupingUsed = false
                binding.txtResultMl.text = formatBR.format(resultCurrentML) + " " + "ml"
            }
        }

        binding.icClear.setOnClickListener{
            val refreshAlert = AlertDialog.Builder(this)
            refreshAlert.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("Ok", {dialogInterface, i ->
                    binding.editWeight.setText("")
                    binding.editAge.setText("")
                    binding.txtResultMl.text= ""
                })
            refreshAlert.setNegativeButton("CANCELAR", {dialogInterface, i ->

            })
            val dialog = refreshAlert.create()
            dialog.show()
        }

        binding.btReminder.setOnClickListener {
            checkCalendar = Calendar.getInstance()
            currentHour = checkCalendar.get(Calendar.HOUR_OF_DAY)
            currentMins = checkCalendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{ newTimePicker: TimePicker?, hourOfDay: Int, minutes: Int ->
                binding.txtHour.text = String.format("%02d",hourOfDay)
                binding.txtMin.text = String.format("%02d",minutes)
            },currentHour,currentMins, true)
            timePickerDialog.show()
        }

        binding.btAlarm.setOnClickListener {
            if(!binding.txtHour.text.toString().isEmpty() && ! binding.txtMin.text.toString().isEmpty()){
                val newIntent = Intent(AlarmClock.ACTION_SET_ALARM)
                newIntent.putExtra(AlarmClock.EXTRA_HOUR, binding.txtHour.text.toString().toInt())
                newIntent.putExtra(AlarmClock.EXTRA_MINUTES,binding.txtMin.text.toString().toInt())
                newIntent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.message_alarm))
                startActivity(newIntent)

                if(newIntent.resolveActivity(packageManager)!= null){
                    startActivity(newIntent)
                }

            }
        }
    }
   /* private fun startComponents(){
        edit_weight = findViewById(R.id.edit_weight)
        edit_age = findViewById(R.id.edit_age)
        bt_calc = findViewById(R.id.bt_calc)
        txt_result_ml = findViewById(R.id.txt_result_ml)
        ic_refresh = findViewById(R.id.ic_clear)
        bt_reminder = findViewById(R.id.bt_reminder)
        bt_alarm = findViewById(R.id.bt_alarm)
        txt_hour = findViewById(R.id.txt_hour)
        txt_min = findViewById(R.id.txt_min)
    }*/
}
