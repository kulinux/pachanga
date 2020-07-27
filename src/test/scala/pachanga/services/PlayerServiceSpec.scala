package pachanga.services

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import pachanga.model.Player
import pachanga.model.Schedule
import pachanga.model.ScheduleItem
import pachanga.model.ScheduleTypeWeek
import pachanga.model.HourRange
import pachanga.model.Hour

class PlayerServiceSpec extends AnyFlatSpec with Matchers {

    behavior of "ScheduleService"

    val srv = new PlayerService()


    it should "return same schedule" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2))

        assert( res.isDefined )
        assert(res.get.dateTime.tpe.dayOfWeek == 1)
        assert(res.get.dateTime.range.start.hour == 1)
        assert(res.get.dateTime.range.start.min == 30)
        assert(res.get.dateTime.range.end.hour == 2)
        assert(res.get.dateTime.range.end.min == 30)
    }

    
    it should "return min schedule" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 45), Hour(2, 0)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2))

        assert( res.isDefined )
        assert(res.get.dateTime.tpe.dayOfWeek == 1)
        assert(res.get.dateTime.range.start.hour == 1)
        assert(res.get.dateTime.range.start.min == 45)
        assert(res.get.dateTime.range.end.hour == 2)
        assert(res.get.dateTime.range.end.min == 0)
    }

    it should "return no schedule" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(4, 45), Hour(5, 0)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2))

        assert( res.isDefined == false)
    }

    it should "return one schedule in several scheduler" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 45), Hour(5, 0))),
                ScheduleItem(ScheduleTypeWeek(2), HourRange( Hour(4, 45), Hour(5, 0)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2))

        assert( res.isDefined)
        assert(res.get.dateTime.tpe.dayOfWeek == 1)
        assert(res.get.dateTime.range.start.hour == 1)
        assert(res.get.dateTime.range.start.min == 45)
        assert(res.get.dateTime.range.end.hour == 2)
        assert(res.get.dateTime.range.end.min == 30)
    }

    it should "return several schedules" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 45), Hour(5, 0))),
                ScheduleItem(ScheduleTypeWeek(2), HourRange( Hour(2, 0), Hour(5, 0)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2))

        assert( res.isDefined )
    }


    it should "works with four player" in {
        val p1 = Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30))),
                ScheduleItem(ScheduleTypeWeek(2), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )

        val p2 = Player(
            "p2",
            "Pepe",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 45), Hour(5, 0))),
                ScheduleItem(ScheduleTypeWeek(2), HourRange( Hour(4, 45), Hour(5, 0)))
            ))
        )

         val p3 = Player(
            "p3",
            "Manolo",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 50), Hour(2, 0)))
            ))
        )

         val p4 = Player(
            "p4",
            "Jorge",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 55), Hour(4, 0))),
                ScheduleItem(ScheduleTypeWeek(2), HourRange( Hour(4, 45), Hour(5, 0)))
            ))
        )

        val res = srv.findMatch(Seq(p1, p2, p3, p4))

        assert( res.size == 1)
        assert(res.get.dateTime.tpe.dayOfWeek == 1)
        assert(res.get.dateTime.range.start.hour == 1)
        assert(res.get.dateTime.range.start.min == 55)
        assert(res.get.dateTime.range.end.hour == 2)
        assert(res.get.dateTime.range.end.min == 0)
    }

  
}
