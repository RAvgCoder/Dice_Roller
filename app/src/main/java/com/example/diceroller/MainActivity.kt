package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var numberOfDice  by remember { mutableStateOf(1) }
    var diceIndices by remember {
        mutableStateOf(List(numberOfDice) { randomNum() })
    }
    var totalNumberOfRolls by remember { mutableStateOf(0) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total Roll number is: $totalNumberOfRolls",
            fontWeight = FontWeight.Bold
        )
        if (totalNumberOfRolls == numberOfDice*6) {
            Text(
                text = stringResource(id = R.string.perfect_roll),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Blue,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(10.dp)
            )
        }
        var numDice = 0
        Row {
            diceIndices.forEach {result ->
                Image(
                    painter = painterResource(id = rollDice(result)),
                    contentDescription = null
                )
                numDice += result
            }
        }
        totalNumberOfRolls = numDice
        Spacer(modifier = Modifier.height(16.dp))

        // Roll button
        Button(onClick = {
            diceIndices = List(numberOfDice) { randomNum() }
            totalNumberOfRolls = 0
        }) {
            Text(
                text = stringResource(R.string.roll)
            )
        }
        Spacer(modifier = Modifier.height(150.dp))

        // Button for choosing the amount of dice you have
        DiceAmount(){ changedAmount ->
            numberOfDice = changedAmount
        }
    }
}

@Composable
fun DiceAmount(onDiceAmountChange: (Int) -> Unit) {
    var numOfDice by remember { mutableStateOf(1) }
    Row {
        Button(
            onClick = {
                numOfDice = 1
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (numOfDice == 1) Color(0xFF1471c8) else Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(text = "1 Dice")
        }

        Spacer(modifier = Modifier.width(70.dp))

        Button(
            onClick = {
                numOfDice = 2
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (numOfDice != 1) Color(0xFF1471c8) else Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text(text = "2 Dice")
        }
    }
    onDiceAmountChange(numOfDice)
    // Kept for making multiple dice
//    TextField(
//        value = numOfDice,
//        singleLine = true,
//        onValueChange = {diceAmount ->
//            numOfDice = diceAmount
//            val dice = numOfDice.toIntOrNull() ?: 1
//            onDiceAmountChange(
//                if (dice in (1..2)) dice
//                else 1
//            )
//        },
//        placeholder = {
//            Text( text = "Input dice amount" )
//        }
//    )
}

@Preview(showBackground = true)
@Preview(showSystemUi = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

fun randomNum():Int = (1..6).random()

fun rollDice(index: Int): Int {
    return arrayOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )[index-1]
}