package fi.evident.fab.proq2

import fi.evident.fab.rew.FilterPlacement

import java.io.IOException

enum class BandPlacement constructor(private val value: Float) {

    Left(0f),
    Right(1f),
    Stereo(2f);

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(value)
    }

    companion object {

        fun of(placement: FilterPlacement): BandPlacement {
            when (placement) {
                FilterPlacement.Left -> return Left
                FilterPlacement.Right -> return Right
                FilterPlacement.Stereo -> return Stereo
                else -> throw IllegalArgumentException("Unsupported placement: " + placement)
            }
        }
    }
}
