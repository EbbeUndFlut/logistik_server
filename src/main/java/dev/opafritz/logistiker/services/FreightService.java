package dev.opafritz.logistiker.services;

import dev.opafritz.logistiker.entities.Freight;
import dev.opafritz.logistiker.repositories.Freightrepository;
import org.springframework.stereotype.Service;

@Service
public class FreightService {
    Freightrepository freightrepository;
    public FreightService(Freightrepository freightrepository){
        this.freightrepository = freightrepository;
    }
    public Freight savefreight(Freight freight){
        return freightrepository.save(freight);
    }

    public Iterable<Freight> getAllFreights(){
       return freightrepository.findAll();
    }
}
