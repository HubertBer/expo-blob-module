package expo.modules.blobmodule

import android.util.Log
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.sharedobjects.SharedObject
import expo.modules.kotlin.types.Either

class Blob() : SharedObject() {
//    var blob: List<String> = listOf()
    var blobParts: List<InternalBlobPart> = listOf()
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

//    private fun List<String>.toBlobParts(): List<BlobPart> {
//        val bp : MutableList<BlobPart> = mutableListOf()
//        for (s in this) {
//             bp.add(BlobPart(s))
//        }
//        return bp
//    }
//
//    fun List<BlobPart>.toStrings(): List<String> {
//        val strings : MutableList<String> = mutableListOf()
//        for (bp in this) {
//            strings.add(bp.text())
//        }
//        return strings.toList()
//    }

    constructor(blobParts: List<InternalBlobPart>, options: BlobOptions = BlobOptions()) : this() {
        this.blobParts = blobParts
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

    private fun InternalBlobPart.offsetSlice(start: Int, end: Int, offset: Int): InternalBlobPart {
        var s : Int = start - offset
        var e : Int = end - offset
        if (s < 0) {
            s = 0
        }
        if (e > this.size()) {
            e = this.size()
        }

        if(this.type == BlobPartType.BLOB) {
            return InternalBlobPart(null, this.blob?.slice(s, e, null), BlobPartType.BLOB)
        } else {
            return InternalBlobPart(str?.substring(s, e) ?: "", null, BlobPartType.STRING)
        }
//        if (this.`is`(String::class)) {
//            this.get(String::class).let {
//                return Either(
//                    it.substring(s, e)
//
//                )
//            }
//        }
//        return this

//        return BlobPart(Blob(listOf("string")))
//        return BlobPart(string?.substring(s, e) ?: "")
    }

    fun slice(start: Int, end: Int, contentType: String?): Blob {
        var i : Int = 0
        var bps : MutableList<InternalBlobPart> = mutableListOf()

        for ( bp in blobParts ) {
            if (i + bp.size() <= start) {
                i += bp.size()
                continue
            }
            if (i >= end) {
                break
            }
            bps.add(bp.offsetSlice(start, end, i))
            i += bp.size()
        }

        return Blob(bps)
    }
}
//
//class BlobPart(@Field val string : String?) : Record {
//    fun size(): Int = string?.length ?: 0
//    fun text(): String = string ?: ""
//}

typealias BlobPart = Either<String, Blob>

fun BlobPart.internal(): InternalBlobPart {
    if (this.`is`(String::class)) {
        this.get(String::class).let {
            return InternalBlobPart(
                it,
                null,
                BlobPartType.STRING
            )
        }
    } else {
        this.get(Blob::class).let {
            Log.d("BP", "Internal blob cast")

            return InternalBlobPart(
                null,
                it,
                BlobPartType.BLOB
            )
        }
    }
}

fun InternalBlobPart.size() : Int {
    if (this.type == BlobPartType.BLOB) {
        return blob?.size ?: 0
    } else {
        return str?.length ?: 0
    }
}
fun InternalBlobPart.text() : String {
    if (this.type == BlobPartType.BLOB) {
        return blob?.text() ?: ""
    } else {
        return str ?: ""
    }
}
//class BlobPart(val string : String?) {
//    fun size(): Int = string?.length ?: 0
//    fun text(): String = string ?: ""
//}

enum class EndingType(val str : String = "transparent") {
    TRANSPARENT("transparent"),
    NATIVE("native"),
}


enum class BlobPartType {
    STRING,
    BLOB,
}

class InternalBlobPart(val str: String?, val blob: Blob?, val type: BlobPartType) {}

data class BlobOptions(val type: String = "", val endings : EndingType = EndingType.TRANSPARENT)

class BlobOptionsBag : Record {
    @Field
    val type: String = ""
    @Field
    val endings : EndingType = EndingType.TRANSPARENT
}