package pachanga.repository

import pachanga.model.Pachanga

trait PachangaRepository[F[_]] {
    def create(pachanga: Pachanga): F[Pachanga]
    def get(id: String): F[Option[Pachanga]]
    def update(pachanga: Pachanga): F[Pachanga]
}
