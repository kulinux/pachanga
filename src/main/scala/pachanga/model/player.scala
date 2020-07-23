package pachanga.model

case class ScheduleTypeWeek(dayOfWeek: Int)

case class Hour(hour: Integer, min: Int)
case class HourRange(start: Hour, end: Hour)

case class ScheduleItem(tpe: ScheduleTypeWeek, range: HourRange)
case class Schedule(schs: Seq[ScheduleItem])
case class Player(id: String, name: String, schedule: Schedule)
