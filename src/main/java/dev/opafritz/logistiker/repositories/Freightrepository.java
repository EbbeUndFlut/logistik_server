package dev.opafritz.logistiker.repositories;

import dev.opafritz.logistiker.entities.Freight;
import org.springframework.data.repository.CrudRepository;

public interface Freightrepository extends CrudRepository<Freight, Integer> {
}
