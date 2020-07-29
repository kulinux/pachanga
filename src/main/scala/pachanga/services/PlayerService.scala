package pachanga.services


import pachanga.model.Player
import pachanga.model.Match
import pachanga.model.Schedule
import pachanga.model.ScheduleItem
import pachanga.model.HourRange
import pachanga.model.Hour

class PlayerService {

    def findMatch(players: Seq[Player]): Option[Match] = {
        val cp: Seq[Seq[ScheduleItem]] = cartesianProduct(players.map(_.schedule.schs))
        val si: Seq[Option[ScheduleItem]] = cp.map(findDate(_))
        val schedules = si.filter(_.isDefined)
        if(schedules.isEmpty) return None
        return schedules.head.map(Match(players, _))
    }

    def cartesianProduct[T](in: Seq[Seq[T]]): Seq[Seq[T]] = {
        def loop(acc: Seq[T], rest: Seq[Seq[T]]): Seq[Seq[T]] = {
            rest match {
                case seq :: Nil => seq.map(acc :+ _)
                case seq :: remainingSeqs => seq.flatMap(i => loop(acc :+ i, remainingSeqs))
            }
        }
        loop(Seq(), in)
    }

    def findDate(sch: Seq[ScheduleItem]): Option[ScheduleItem] = 
        sch match {
            case Nil => None
            case one if(one.size == 1) => Some(one(0))
            case two if(two.size == 2) => joinScheduler(two(0), two(1))
            case head :: next => findDate(next).flatMap(si => joinScheduler(si, head))
        }

    def joinScheduler(sch1: ScheduleItem, sch2: ScheduleItem): Option[ScheduleItem] = {
        if(sch1.tpe.dayOfWeek != sch2.tpe.dayOfWeek) return None
        var start1Win = false
        var end1Win = false
        var startHour = sch2.range.start.hour
        var startMin = sch2.range.start.min
        if(sch1.range.start.hour > sch2.range.start.hour ||
            ( sch1.range.start.hour == sch2.range.start.hour &&
             sch1.range.start.min > sch2.range.start.min ) ) {
            start1Win = true
            startHour = sch1.range.start.hour
            startMin = sch1.range.start.min
        }

        var endHour = sch2.range.end.hour
        var endMin = sch2.range.end.min
        if(sch1.range.end.hour < sch2.range.end.hour ||
            ( sch1.range.end.hour == sch2.range.end.hour &&
             sch1.range.end.min < sch2.range.end.min ) ) {
            end1Win = true
            endHour = sch1.range.end.hour
            endMin = sch1.range.end.min
        }

        if( startHour < endHour  || ( startHour == endHour && startMin < endMin ) ) {
           return Some(ScheduleItem(sch1.tpe, HourRange(Hour(startHour, startMin), Hour(endHour, endMin))))
        }
        return None
    }
}