package pachanga.repository.memory

import scala.collection.concurrent.TrieMap
import java.util.Random
import java.util.UUID.randomUUID

class InMemoryRepository[T <: Product](getId: T => String, setId: (T, String) => T) {
    private val cache = new TrieMap[String, T]
    private val random = new Random

    def create(item: T) = 
        cache.put(getId(item), setId(item, randomUUID().toString())).get

    def get(id: String) = cache.get(id)

    def delete(id: String) = cache.remove(id)
  
}
