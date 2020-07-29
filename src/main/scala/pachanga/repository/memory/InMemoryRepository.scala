package pachanga.repository.memory

import scala.collection.concurrent.TrieMap
import java.util.Random
import java.util.UUID.randomUUID

class InMemoryRepository[T <: Product](getId: T => String, setId: (T, String) => T) {
    private val cache = new TrieMap[String, T]
    private val random = new Random

    def create(item: T) = {
        val stored = setId(item, randomUUID().toString())
        cache.put(getId(item), stored)
        stored
    }

    def get(id: String) = cache.get(id)

    def delete(id: String) = cache.remove(id)

}
