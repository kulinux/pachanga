package pachanga.repository.memory

import cats._
import cats.implicits._

import pachanga.repository.PachangaRepository
import pachanga.model.Pachanga


class PachangaRepositoryInMemory[F[_]: Applicative] 
    extends PachangaRepository[F] {

    private val memory =
        new InMemoryRepository[Pachanga](_.id, (p, id) => p.copy(id = id))

    memory.createWithId(Pachanga("uno", "xxxx", Seq("uno", "dos")))
    memory.createWithId(Pachanga("dos", "xxxx", Seq()))


    def create(pachanga: Pachanga): F[Pachanga] = 
        memory.create(pachanga).pure[F]

    def get(id: String): F[Option[Pachanga]] =
        memory.get(id).pure[F]
  
}
