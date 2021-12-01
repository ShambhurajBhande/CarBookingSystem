package com.freenow.util;

import com.freenow.datatransferobject.SearchQueryDTO;
import com.freenow.domainobject.DriverDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class DriverSpecification implements Specification<DriverDO>
{
    private SearchQueryDTO searchQueryDTO;

    @Autowired
    public DriverSpecification(SearchQueryDTO searchQueryDTO)
    {
        this.searchQueryDTO = searchQueryDTO;
    }



    @Override
    public Predicate toPredicate(Root<DriverDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Join<Object, Object> joinParent = root.join("carDO",JoinType.LEFT);

        List<Predicate> predicates = new LinkedList<>();

        if (!searchQueryDTO.getOnlineStatus().name().isEmpty()) {
            predicates.add(cb.equal(root.get("onlineStatus"), searchQueryDTO.getOnlineStatus()));
        }
        if (!searchQueryDTO.getUsername().isEmpty()) {
            predicates.add(cb.equal(root.get("username"), searchQueryDTO.getUsername()));
        }
        if (searchQueryDTO.getRating()>=0) {
            predicates.add(cb.equal(joinParent.get("rating"), searchQueryDTO.getRating()));
        }
        if (!searchQueryDTO.getLicensePlate().isEmpty()) {
            predicates.add(cb.equal(joinParent.get("licensePlate"), searchQueryDTO.getLicensePlate()));
        }


        return criteriaQuery
            .where(cb.or(predicates.toArray(new Predicate[0])))
            .distinct(true)
            .getRestriction();
    }
}
