package pachanga.repository.memory

import cats._
import cats.implicits._

import pachanga.repository.PlayerRepository
import pachanga.model.Pachanga
import pachanga.model.Player


class PlayerRepositoryInMemory[F[_]: Applicative] 
    extends PlayerRepository[F] {

    private val memory =
        new InMemoryRepository[Player](_.id, (p, id) => p.copy(id = id))

    def create(player: Player): F[Player] = 
        memory.create(player).pure[F]

    def get(id: String): F[Option[Player]] =
        memory.get(id).pure[F]
  
}
