package be.fgov.sfpd.kata.aoc22.day6

class Handheld(private val comms: CommSystem) {
    
    fun startOfPacketMarkerPosition(input: Sequence<Char>) : Int {
        val (position, markerSymbol) = comms.findStartOfPacketMarker(input)
        return position
    }

    fun startOfMessageMarkerPosition(input: Sequence<Char>) : Int {
        val (position, markerSymbol) = comms.findStartOfMessageMarker(input)
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
    
    fun findStartOfMessageMarker(datastream: Sequence<Char>): Pair<Int, Char> {
        return datastream.windowed(14)
            .mapIndexedNotNull { index, chars -> 
                if (chars.toSet().size == 14)
                    index + 14 to chars.last()    
                else null
            }.firstOrNull() ?: error("no start of message marker found")
    }

}