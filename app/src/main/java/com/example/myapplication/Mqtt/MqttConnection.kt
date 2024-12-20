import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

enum class MqttStatus {
    CREATED,
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    FAILED
}

class MqttConnection(
    private val serverUri: String,
    private val clientId: String = MqttClient.generateClientId()
) {

    var status: MqttStatus = MqttStatus.CREATED
    private var mqttClient: MqttClient? = null

    fun initialize() {
        mqttClient = MqttClient(serverUri, clientId, null)

        mqttClient?.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                println("Connection lost: ${cause?.message}")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("Message arrived on topic $topic: ${message?.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                println("Delivery complete: ${token?.message}")
            }
        })
    }

    fun connect(onSuccess: () -> Unit = {
        status = MqttStatus.CONNECTED
    }, onFailure: (Throwable) -> Unit = {
        status = MqttStatus.FAILED
    }) {
        status = MqttStatus.CONNECTING

        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
        }

        try {
            mqttClient?.connect(options) // Correctly passing MqttConnectOptions
            println("Connected successfully")
            onSuccess()
        } catch (e: Exception) {
            println("Connection failed: ${e.message}")
            onFailure(e)
        }
    }

    fun subscribe(topic: String, qos: Int = 1) {
        mqttClient?.subscribe(topic, qos) { t, message ->
            println("Message received on $t: ${message.toString()}")
        } ?: println("Subscribe failed: MQTT client is not initialized or connected")
    }

    fun disconnect() {
        mqttClient?.disconnect()
        status = MqttStatus.DISCONNECTED
        println("Disconnected from MQTT broker")
    }
}