package pachanga.repository.memory

import cats._
import cats.implicits._

import pachanga.repository.PlayerRepository
import pachanga.model._


class PlayerRepositoryInMemory[F[_]: Applicative] 
    extends PlayerRepository[F] {

    private val memory =
        new InMemoryRepository[Player](_.id, (p, id) => p.copy(id = id))

    memory.createWithId(
          Player(
            "p1",
            "Juan",
            Schedule(Seq(
                ScheduleItem(ScheduleTypeWeek(1), HourRange( Hour(1, 30), Hour(2, 30)))
            ))
        )
    )
        

    def create(player: Player): F[Player] = 
        memory.create(player).pure[F]

    def get(id: String): F[Option[Player]] =
        memory.get(id).pure[F]

    def update(player: Player): F[Player] = 
        memory.update(player).pure[F]
  
}
