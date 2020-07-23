package pachanga.services

import scala.math.{min, max}

import pachanga.model.Player
import pachanga.model.Match
import pachanga.model.Schedule
import pachanga.model.ScheduleItem
import pachanga.model.HourRange
import pachanga.model.Hour

class PlayerService {
    case class TryMatch(mtch: Match, weight: Int)

    def matches(players: Seq[Player]): Seq[Match] = {
        val matches = for {
            player1 <- players
            player2 <- players.filter(_ != player1)
            (dateTime, weight) <- findDate(player1.schedule, player2.schedule)
        } yield TryMatch( Match(player1, player2, dateTime), weight )

        matches
            .groupBy( tm => Seq(tm.mtch.p1.id, tm.mtch.p2.id).sortBy(s => s).mkString("-") )
            .map( tm => tm._2.head )
            .map( tm => tm.mtch )
            .toSeq
    }

    def findDate(sch1: Schedule, sch2: Schedule): Seq[(ScheduleItem, Int)] = {
        for {
            schItem1 <- sch1.schs
            schItem2 <- sch2.schs
            sch <- joinScheduler(schItem1, schItem2)
            wght1 = weight(schItem1, sch)
            wght2 = weight(schItem1, sch)
        } yield (sch, (wght1 + wght2) / 2)
    }

    def joinScheduler(sch1: ScheduleItem, sch2: ScheduleItem): Seq[ScheduleItem] = {
        if(sch1.tpe.dayOfWeek != sch2.tpe.dayOfWeek) return Seq()
        val startHour = max(sch1.range.start.hour, sch2.range.start.hour)
        val endHour = min(sch1.range.end.hour, sch2.range.end.hour)

        val startMin = max(sch1.range.start.min, sch2.range.start.min)
        val endMin = min(sch1.range.end.min, sch2.range.end.min)

        if( startHour < endHour  || ( startHour == endHour && startMin < endMin ) ) {
           return Seq(ScheduleItem(sch1.tpe, HourRange(Hour(startHour, startMin), Hour(endHour, endMin))))
        }
        return Seq()
    }

    def weight(sch1: ScheduleItem, sch2: ScheduleItem): Int = 1
}