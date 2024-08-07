package com.drinkwater.model

class CalcTotalWater {
    private val UP_T0_17 = 40.0
    private val BETWEEN_18_TO_55 = 35.0
    private val BETWEEN_56_TO_65 = 30.0
    private val MOR_66= 25.0

    private var resultML = 0.0
    private var totalResultML = 0.0

    fun calcTotalML(newWeight:Double, newAge: Int){
        if(newAge <= 17){
            resultML = newWeight * UP_T0_17
            totalResultML = resultML
        }else if (newAge <= 55){
            resultML = newWeight * BETWEEN_18_TO_55
            totalResultML = resultML
        }else if (newAge <= 65){
            resultML = newWeight * BETWEEN_56_TO_65
            totalResultML = resultML
        }else{
            resultML = newWeight * MOR_66
            totalResultML = resultML
        }
    }

    fun resultMl(): Double{
        return totalResultML
    }
}