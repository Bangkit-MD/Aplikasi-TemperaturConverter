package com.kevtech.mytemperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kevtech.mytemperatureconverter.ui.theme.MyTemperatureConverterTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTemperatureConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        StateFullTemperatureInput()
                        ConverterApp()
                        TwoWayConverterApp()
                    }

                }
            }
        }
    }
}
// Converter
private fun convertToFahrenheit(celsius: String) =
    celsius.toDoubleOrNull()?.let {
        (it * 9 / 5) + 32
    }.toString()
private fun convertToCelsius(fahrenheit: String) =
    fahrenheit.toDoubleOrNull()?.let {
        (it - 32) * 5 / 9
    }.toString()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateFullTemperatureInput(
    modifier: Modifier = Modifier
) {
    var input by rememberSaveable { mutableStateOf("") }
    var output by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = input,
            onValueChange = { data ->
                input = data
                output = convertToFahrenheit(data)
            },
            label = { Text(stringResource(R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Text(stringResource(R.string.temperature_fahrenheit, output))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessTemperatureInput(
    input: String,
    output: String,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit,
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.stateless_converter),
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = input,
            label = { Text(stringResource(R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onChange,
        )
        Text(stringResource(R.string.temperature_fahrenheit, output))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        OutlinedTextField(
            value = input,
            label = { Text(stringResource(R.string.enter_temperature, scale.scaleName)) },
            onValueChange = onChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )
    }
}

enum class Scale(val scaleName: String) {
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit")
}

@Composable
fun TwoWayConverterApp(modifier: Modifier = Modifier) {
    var celsius by rememberSaveable{ mutableStateOf("") }
    var fahrenheit by rememberSaveable{ mutableStateOf("") }

    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.two_way_converter),
            style = MaterialTheme.typography.headlineSmall
            )
        GeneralTemperatureInput(scale = Scale.CELSIUS, input = celsius,  onChange = { data ->
            celsius = data
            fahrenheit = convertToFahrenheit(data)

        })
        GeneralTemperatureInput(scale = Scale.FAHRENHEIT, input = fahrenheit,  onChange = { data ->
            fahrenheit = data
            celsius = convertToCelsius(data)

        })
    }
}

@Composable
fun ConverterApp() {
    var input by rememberSaveable { mutableStateOf("") }
    var output by rememberSaveable { mutableStateOf("") }
    StatelessTemperatureInput(input = input, output = output, onChange = { data ->
        input = data
        output = convertToFahrenheit(data)
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyTemperatureConverterTheme {
        StateFullTemperatureInput()
        ConverterApp()
    }
}