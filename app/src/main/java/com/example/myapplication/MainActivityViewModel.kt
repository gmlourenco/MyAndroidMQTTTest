package com.example.myapplication

import MqttConnection
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val mqttManager = MqttConnection("tcp://broker.hivemq.com:1883")

    init {
        initializeMqtt()
    }

    private fun initializeMqtt() {
        mqttManager.initialize()
        mqttManager.connect(
            onSuccess = {
                println("MQTT connected successfully")
                mqttManager.subscribe("test/topic")
            },
            onFailure = { error ->
                println("MQTT connection failed: ${error.message}")
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        mqttManager.disconnect()
    }
}