package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class AddMQTTConnectionFragment {

    @Composable
    fun AddMqttConnection(pad: PaddingValues) {
        Box(modifier = Modifier.padding(pad)) {
            NewConnectionBox()
        }
    }

    @Composable
    private fun NewConnectionBox() {
        val context = LocalContext.current
        var host by remember { mutableStateOf("192.168.0.0") }
        var topic by remember { mutableStateOf("dummyTopic") }

        Column {
            OutlinedTextField(
                value = host,
                onValueChange = { host = it },
                label = { Text("Hostname") },
                modifier = Modifier.fillMaxWidth(),
                isError = host.isEmpty()
            )
            if (host.isEmpty()) {
                Text(
                    text = "Hostname is obrigatório",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("topic") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                println("Host: $host")
                println("Topic: $topic")
            }) {
                Text("Add Connection")
            }
        }
    }

}