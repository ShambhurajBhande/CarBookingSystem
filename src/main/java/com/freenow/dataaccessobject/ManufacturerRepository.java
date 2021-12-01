package com.freenow.dataaccessobject;

import com.freenow.domainobject.ManufacturerDo;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDo,Long>
{
}
