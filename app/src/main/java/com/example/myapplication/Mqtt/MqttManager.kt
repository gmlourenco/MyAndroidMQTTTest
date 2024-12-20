package com.example.myapplication.Mqtt

import MqttConnection

class MqttManager {
    var mqttConnections: MutableMap<String, MqttConnection> = mutableMapOf()

    fun addConnection(host: String, port: Int, topic: String? = null) {

        if (mqttConnections.containsKey(host)) {
            val connection = MqttConnection(host)
            connection.connect()
            mqttConnections[host] = connection

        }
        
        if (topic != null) {
            mqttConnections[host]?.subscribe(topic)
        }


    }
}
