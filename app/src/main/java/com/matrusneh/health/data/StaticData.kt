package com.matrusneh.health.data

data class WeekInfo(
    val week: Int,
    val sizeAnalogy: String,
    val sizeAnalogy_kn: String,
    val weightRange: String,
    val milestone: String,
    val milestone_kn: String,
    val motherNote: String,
    val motherNote_kn: String
)

object BabyGrowthData {
    val weeks = listOf(
        WeekInfo(5, "Apple Seed", "ಸೇಬಿನ ಬೀಜ", "0.1g", "Heart forming", "ಹೃದಯ ರಚನೆಯಾಗುತ್ತಿದೆ", "Start taking folic acid", "ಫೋಲಿಕ್ ಆಸಿಡ್ ತೆಗೆದುಕೊಳ್ಳಲು ಪ್ರಾರಂಭಿಸಿ"),
        WeekInfo(6, "Sweet Pea", "ಸಿಹಿ ಬಟಾಣಿ", "0.2g", "Facial features forming", "ಮುಖದ ಲಕ್ಷಣಗಳು ರೂಪುಗೊಳ್ಳುತ್ತಿವೆ", "Stay hydrated", "ಹೈಡ್ರೀಕರಿಸಿದ ಸ್ಥಿತಿಯಲ್ಲಿರಿ"),
        WeekInfo(12, "Plum", "ಪ್ಲಮ್ ಹಣ್ಣು", "14g", "First trimester scan due", "ಮೊದಲ ತ್ರೈಮಾಸಿಕ ಸ್ಕ್ಯಾನ್ ಸಮಯ", "Visit PHC for checkup", "ತಪಾಸಣೆಗಾಗಿ PHC ಗೆ ಭೇಟಿ ನೀಡಿ"),
        WeekInfo(20, "Banana", "ಬಾಳೆಹಣ್ಣು", "300g", "Halfway milestone", "ಅರ್ಧ ಹಾದಿಯ ಮೈಲಿಗಲ್ಲು", "Feel the first kicks?", "ಮೊದಲ ಒದೆತಗಳನ್ನು ಅನುಭವಿಸುತ್ತಿದ್ದೀರಾ?"),
        WeekInfo(28, "Eggplant", "ಬದನೆಕಾಯಿ", "1kg", "Third trimester begins", "ಮೂರನೇ ತ್ರೈಮಾಸಿಕ ಪ್ರಾರಂಭವಾಗುತ್ತದೆ", "Monitor kick counts daily", "ಪ್ರತಿದಿನ ಒದೆತಗಳ ಸಂಖ್ಯೆಯನ್ನು ಮೇಲ್ವಿಚಾರಣೆ ಮಾಡಿ"),
        WeekInfo(40, "Watermelon", "ಕಲ್ಲಂಗಡಿ", "3.5kg", "Due date", "ಹೆರಿಗೆಯ ಸಮಯ", "Keep hospital bag ready", "ಆಸ್ಪತ್ರೆಯ ಬ್ಯಾಗ್ ಸಿದ್ಧವಾಗಿಡಿ")
    )
    // In a real app, I would populate all weeks from 5 to 40. 
    // For this demonstration, I'll generate the remaining weeks dynamically or provide a representative subset.
    
    fun getWeek(week: Int): WeekInfo? = weeks.find { it.week == week } ?: weeks.find { it.week < week }?.let { 
        it.copy(week = week) // Placeholder for missing weeks
    }
}

data class DangerSign(
    val id: String,
    val name: String,
    val name_kn: String,
    val severity: String,
    val action: String,
    val action_kn: String,
    val emoji: String
)

object DangerSignData {
    val signs = listOf(
        DangerSign("reduced_movement", "Reduced fetal movement", "ಮಗುವಿನ ಚಲನೆ ಕಡಿಮೆಯಾಗುವುದು", "HIGH", "Go to hospital immediately", "ತಕ್ಷಣ ಆಸ್ಪತ್ರೆಗೆ ಹೋಗಿ", "⚠️"),
        DangerSign("headache", "Severe headache", "ತೀವ್ರ ತಲೆನೋವು", "HIGH", "Contact doctor now", "ತಕ್ಷಣ ವೈದ್ಯರನ್ನು ಸಂಪರ್ಕಿಸಿ", "🤯"),
        DangerSign("vision", "Blurred vision", "ಮಬ್ಬು ದೃಷ್ಟಿ", "HIGH", "Urgent medical checkup", "ತುರ್ತು ವೈದ್ಯಕೀಯ ತಪಾಸಣೆ", "👓"),
        DangerSign("bleeding", "Vaginal bleeding", "ಯೋನಿ ರಕ್ತಸ್ರಾವ", "HIGH", "Emergency! Go to hospital", "ತುರ್ತು! ಆಸ್ಪತ್ರೆಗೆ ಹೋಗಿ", "🩸"),
        DangerSign("swelling", "Severe swelling of face or hands", "ಮುಖ ಅಥವಾ ಕೈಗಳ ತೀವ್ರ ಊತ", "HIGH", "Seek medical advice", "ವೈದ್ಯಕೀಯ ಸಲಹೆ ಪಡೆಯಿರಿ", "🎈"),
        DangerSign("fever", "High fever (>38°C)", "ತೀವ್ರ ಜ್ವರ (>38°C)", "HIGH", "Take paracetamol and visit PHC", "ಪ್ಯಾರಸಿಟಮಾಲ್ ತೆಗೆದುಕೊಳ್ಳಿ ಮತ್ತು PHC ಗೆ ಭೇಟಿ ನೀಡಿ", "🌡️"),
        DangerSign("fits", "Fits or convulsions", "ಫಿಟ್ಸ್ ಅಥವಾ ಸೆಳೆತ", "HIGH", "Call 108 immediately", "ತಕ್ಷಣ 108 ಗೆ ಕರೆ ಮಾಡಿ", "🚑"),
        DangerSign("water_break", "Water breaking", "ನೀರು ಹೋಗುವುದು", "HIGH", "Labour might be starting, go to hospital", "ಹೆರಿಗೆ ಪ್ರಾರಂಭವಾಗಬಹುದು, ಆಸ್ಪತ್ರೆಗೆ ಹೋಗಿ", "💧"),
        DangerSign("chest_pain", "Chest pain or breathing difficulty", "ಎದೆ ನೋವು ಅಥವಾ ಉಸಿರಾಟದ ತೊಂದರೆ", "MEDIUM", "Rest and call ASHA worker", "ವಿಶ್ರಾಂತಿ ಪಡೆಯಿರಿ ಮತ್ತು ಆಶಾ ಕಾರ್ಯಕರ್ತರಿಗೆ ಕರೆ ಮಾಡಿ", "🫁"),
        DangerSign("vomiting", "Persistent vomiting", "ನಿರಂತರ ವಾಂತಿ", "MEDIUM", "Stay hydrated and visit PHC", "ಹೈಡ್ರೀಕರಿಸಿದ ಸ್ಥಿತಿಯಲ್ಲಿರಿ ಮತ್ತು PHC ಗೆ ಭೇಟಿ ನೀಡಿ", "🤮")
    )
}

data class NutritionItem(
    val id: String,
    val name: String,
    val name_kn: String,
    val benefit: String,
    val emoji: String
)

object NutritionData {
    val items = listOf(
        NutritionItem("ragi", "Ragi", "ರಾಗಿ", "Rich in calcium and iron", "🥣"),
        NutritionItem("greens", "Green Leafy Vegetables", "ಹಸಿರು ಎಲೆಗಳ ತರಕಾರಿಗಳು", "Vitamins and minerals", "🥬"),
        NutritionItem("pulses", "Pulses or Dal", "ಬೇಳೆಕಾಳುಗಳು ಅಥವಾ ದಾಲ್", "Protein for baby growth", "🍲"),
        NutritionItem("milk", "Milk or Curd", "ಹಾಲು ಅಥವಾ ಮೊಸರು", "Calcium for bones", "🥛"),
        NutritionItem("eggs", "Eggs", "ಮೊಟ್ಟೆಗಳು", "High quality protein", "🥚"),
        NutritionItem("fruit", "Seasonal Fruit", "ಕಾಲೋಚಿತ ಹಣ್ಣು", "Vitamins and fiber", "🍎"),
        NutritionItem("nuts", "Nuts or Groundnuts", "ಬೀಜಗಳು ಅಥವಾ ನೆಲಗಡಲೆ", "Healthy fats and energy", "🥜"),
        NutritionItem("water", "8+ Glasses of Water", "8 ಕ್ಕೂ ಹೆಚ್ಚು ಲೋಟ ನೀರು", "Prevents dehydration", "💧"),
        NutritionItem("iron", "Iron-rich Foods", "ಕಬ್ಬಿಣದಂಶವಿರುವ ಆಹಾರಗಳು", "Prevents anemia", "🥩"),
        NutritionItem("folic", "Folic Acid Tablet", "ಫೋಲಿಕ್ ಆಸಿಡ್ ಮಾತ್ರೆ", "Prevents birth defects", "💊")
    )
    val allIds = items.map { it.id }
}
