package be.fgov.sfpd.kata.aoc22.day6

class Handheld(private val comms: CommSystem) {
    
    fun startingMarkerPosition(input: Sequence<Char>) : Int {
        val (position, markerSymbol) = comms.findStartOfPacketMarker(input)
        return position
    }
}

class CommSystem {
    fun findStartOfPacketMarker(datastream: Sequence<Char>): Pair<Int, Char> {
        return datastream.windowed(4)
            .mapIndexedNotNull { index, chars -> 
                if (chars.toSet().size == 4)
                    index + 4 to chars.last()    
                else null
            }.firstOrNull() ?: error("no start of packet marker found")
    }

}