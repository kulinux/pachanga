package pachanga.repository

import pachanga.model.Player

trait PlayerRepository[F[_]] {
    def create(player: Player): F[Player]
    def get(id: String): F[Option[Player]]
    def update(player: Player): F[Player]
}
