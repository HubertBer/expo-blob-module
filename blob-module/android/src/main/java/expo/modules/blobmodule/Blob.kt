package expo.modules.blobmodule

import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.sharedobjects.SharedObject


class Blob() : SharedObject() {
    var blob: List<String> = listOf()
    var size : Int = 0
    var options : BlobOptions = BlobOptions()

    constructor(blob: List<String>, options: BlobOptions = BlobOptions()) : this() {
        this.blob = blob
        this.options = options

        for (s in blob) {
            size += s.length
        }
    }

    fun text(): String {
        var str = ""
        for (s in blob) {
            str += s
        }
        return str
    }

    fun String.sliceStr(start: Int, end: Int, strOffset: Int) : String{
        var s : Int = start - strOffset
        var e : Int = end - strOffset
        if (s < 0) {
            s = 0
        }
        if (e > this.length) {
            e = this.length
        }
        return this.substring(s, e)
    }

    fun slice(start: Int, end: Int, contentType: String?): Blob {
        var i : Int = 0
        var strings : MutableList<String> = mutableListOf()

        for ( s in blob ) {
            if (i + s.length <= start) {
                i += s.length
                continue
            }
            if (i >= end) {
                break
            }
            strings.add(s.sliceStr(start, end, i))
            i += s.length
        }

        return Blob(strings)
    }
}

enum class EndingType(val str : String = "transparent") {
    TRANSPARENT("transparent"),
    NATIVE("native"),
}

data class BlobOptions(val type: String = "", val endings : EndingType = EndingType.TRANSPARENT)

class BlobOptionsBag : Record {
    @Field
    val type: String = ""
    @Field
    val endings : EndingType = EndingType.TRANSPARENT
}