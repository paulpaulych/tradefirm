package paulpaulych.tradefirm

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.utils.LoggerDelegate
import java.text.SimpleDateFormat
import java.util.*

class DateTest: FunSpec() {

    private val log by LoggerDelegate()

    init {

        test("serialize"){
            val date = Date()
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z")
            val serialized = format.format(date)
            log.info("serialized: $serialized")
            format.format(format.parse(serialized)) shouldBe serialized
        }

    }
}