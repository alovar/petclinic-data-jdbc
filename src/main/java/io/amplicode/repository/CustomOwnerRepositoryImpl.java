package io.amplicode.repository;

import io.amplicode.api.dto.OwnerFilter;
import io.amplicode.model.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.util.StringUtils;

import static org.springframework.data.relational.core.query.Criteria.where;

@RequiredArgsConstructor
public class CustomOwnerRepositoryImpl implements CustomOwnerRepository {

    private final JdbcAggregateOperations jdbcAggregateOperations;

    @Override
    public Iterable<Owner> findAll(OwnerFilter filter) {
        Query query = toQuery(filter);
        return jdbcAggregateOperations.findAll(query, Owner.class);
    }

    @Override
    public Page<Owner> findAll(OwnerFilter filter, Pageable pageable) {
        Query query = toQuery(filter);
        return jdbcAggregateOperations.findAll(query, Owner.class, pageable);
    }

    private Query toQuery(OwnerFilter filter) {
        Criteria criteria = Criteria.empty();
        String address = filter.address();
        if (StringUtils.hasText(address)) {
            String addressExpr = "%" + address + "%";
            criteria = criteria.and(
                    where(Owner.Fields.address).like(addressExpr).ignoreCase(true)
            );
        }
        String city = filter.city();
        if (StringUtils.hasText(city)) {
            criteria = criteria.and(Owner.Fields.city).like("%" + city + "%").ignoreCase(true);
        }
        String telephone = filter.telephone();
        if (StringUtils.hasText(telephone)) {
            criteria = criteria.and(Owner.Fields.telephone).is(telephone);
        }
        return Query.query(criteria);
    }
}