package biz.belcorp.salesforce.core.utils.notification

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.serialization.json.Json

object NotificationDataParser {

    private const val DELIMITER = "|"
    private val REGEX_DELIMITER = "\\|".toRegex()

    private const val CLEAN_DATA_POSITION = 1

    // POSTULANTS - UNETE

    private const val POSTULANT_CODE_POSITION = 1
    private const val POSTULANT_STATE_POSITION = 2
    private const val POSTULANT_STEP_POSITION = 3
    private const val POSTULANT_IMAGE_POSITION = 4

    private const val POSTULANT_STEP_ONE = 1
    private const val POSTULANT_STEP_FOUR = 4

    private const val POSTULANT_NEW_POSTULANT_CODE = "3"
    private const val POSTULANT_NEW_CONSULTANT_CODE = "5"

    // RDD RECOGNITION

    private const val RECOGNIZED_CODE_POSITION = 2
    private const val RECOGNIZED_ROLE_POSITION = 3
    private const val RECOGNIZED_NAME_POSITION = 4
    private const val RECOGNITION_CODE_POSITION = 5
    private const val RECOGNITION_ROLE_POSITION = 6
    private const val RECOGNITION_ID = 7
    private const val CAMPANIGN = 8

    // TOPICS

    private const val TOPIC_RDD_RECOGNITION = "RDD"
    private const val TOPIC_RDD_EVENT = "RDD_EVENTOS"
    private const val TOPIC_POSTULANTS = "UNETE"
    private const val TOPIC_NEWS = "GestorContenidosPush"

    fun parse(data: String): Data? {
        return try {
            val cleanData = cleanData(data)
            when {
                cleanData.data.contains(TOPIC_NEWS) -> {
                    cleanData.also {
                        it.topic = TOPIC_NEWS
                    }
                }
                cleanData.data.contains(TOPIC_POSTULANTS) -> {
                    cleanData.also {
                        it.topic = TOPIC_POSTULANTS
                        it.data = parsePostulants(cleanData.data)
                    }
                }
                cleanData.data.contains(TOPIC_RDD_EVENT) -> {
                    cleanData.also {
                        it.topic = TOPIC_RDD_EVENT
                        it.data = Constant.EMPTY_OBJECT
                    }
                }
                cleanData.data.contains(TOPIC_RDD_RECOGNITION) -> {
                    cleanData.also {
                        it.topic = TOPIC_RDD_RECOGNITION
                        it.data = parseRddRecognition(cleanData.data)
                    }
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parsePostulants(data: String): String {

        val array = data.split(REGEX_DELIMITER).dropLastWhile { it.isEmpty() }.toTypedArray()

        val postulantCode = array[POSTULANT_CODE_POSITION]
        val postultantState = array[POSTULANT_STATE_POSITION]
        val postultantStep = array[POSTULANT_STEP_POSITION]
        val image = array[POSTULANT_IMAGE_POSITION]

        val isStepForPostulant = postultantStep.toInt() < POSTULANT_STEP_FOUR
        val idStepToApproved = postultantStep.toInt() >= POSTULANT_STEP_FOUR
        val hasDocuments = image.toInt() == POSTULANT_STEP_ONE

        val notification = PostulantData()
            .apply {
                code = postulantCode
                state = postultantState
                step = postultantStep
                newConsultant = state == POSTULANT_NEW_CONSULTANT_CODE
                newPostulant = state == POSTULANT_NEW_POSTULANT_CODE && isStepForPostulant
                newPostulantToAprove =
                    state == POSTULANT_NEW_POSTULANT_CODE && idStepToApproved && hasDocuments
            }

        return Json.encodeToString(PostulantData.serializer(), notification)
    }

    private fun parseRddRecognition(data: String): String {

        val datos = data.split(REGEX_DELIMITER).dropLastWhile { it.isEmpty() }.toTypedArray()

        val notification = RddRecognitionData().apply {
            id = datos[RECOGNITION_ID].toInt()
            personId = datos[RECOGNITION_CODE_POSITION]
            personRol = datos[RECOGNITION_ROLE_POSITION]
            personRecognizedId = datos[RECOGNIZED_CODE_POSITION]
            personRecognizedRole = datos[RECOGNIZED_ROLE_POSITION]
            personRecognizedName = datos[RECOGNIZED_NAME_POSITION]
            campaign = datos[CAMPANIGN]
        }

        return Json.encodeToString(RddRecognitionData.serializer(), notification)
    }

    private fun cleanData(data: String): Data {
        var dataArray = data.split(REGEX_DELIMITER)
        val notificationId = dataArray.firstOrNull()?.toLongOrNull()
        if (notificationId != null) {
            dataArray = dataArray.subList(CLEAN_DATA_POSITION, dataArray.size)
        }
        val cleanData = dataArray.joinToString(DELIMITER)
        return Data(id = notificationId, data = cleanData)
    }

    data class Data(
        var id: Long? = null,
        var topic: String? = Constant.EMPTY_STRING,
        var data: String
    )

}
