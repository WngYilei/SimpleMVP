import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset


class EncoderGSonRequestBodyConverter<T>(private val gSon: Gson, private val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), Charset.forName("UTF-8"))
        val jsonWriter = gSon.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), buffer.readByteString())
    }
}