package expo.modules.blobmodule

import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.sharedobjects.SharedObject


class Blob() : SharedObject() {
//    var blob: List<String> = listOf()
    var blobParts: List<BlobPart> = listOf()
    var size : Int = 0
    var options : BlobOptions = BlobOptions()

//    constructor(blobParts: List<BlobPart>, options: BlobOptions = BlobOptions()) : this() {
//        this.blobParts = blobParts
//        this.options = options
//
//        for (bp in blobParts) {
//            size += bp.size()
//        }
//    }

    private fun List<String>.toBlobParts(): List<BlobPart> {
        val bp : MutableList<BlobPart> = mutableListOf()
        for (s in this) {
             bp.add(BlobPart(s))
        }
        return bp
    }

    constructor(strings: List<String>, options: BlobOptions = BlobOptions()) : this() {
        this.blobParts = strings.toBlobParts()
        this.options = options

        for (bp in blobParts) {
            size += bp.size()
        }
    }

    fun text(): String {
        var str = ""
        for (bp in blobParts) {
            str += bp.text()
        }
        return str
    }

//    fun String.sliceStr(start: Int, end: Int, strOffset: Int) : String{
//        var s : Int = start - strOffset
//        var e : Int = end - strOffset
//        if (s < 0) {
//            s = 0
//        }
//        if (e > this.length) {
//            e = this.length
//        }
//        return this.substring(s, e)
//    }

    private fun BlobPart.offsetSlice(start: Int, end: Int, offset: Int): BlobPart {
        var s : Int = start - offset
        var e : Int = end - offset
        if (s < 0) {
            s = 0
        }
        if (e > this.size()) {
            e = this.size()
        }
        return BlobPart(string?.substring(s, e) ?: "")
    }

    fun slice(start: Int, end: Int, contentType: String?): Blob {
        var i : Int = 0
        var strings : MutableList<BlobPart> = mutableListOf()

        for ( bp in blobParts ) {
            if (i + bp.size() <= start) {
                i += bp.size()
                continue
            }
            if (i >= end) {
                break
            }
            strings.add(bp.offsetSlice(start, end, i))
            i += bp.size()
        }

        return Blob())
    }
}

class BlobPart(@Field val string : String?) : Record {
    fun size(): Int = string?.length ?: 0
    fun text(): String = string ?: ""
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