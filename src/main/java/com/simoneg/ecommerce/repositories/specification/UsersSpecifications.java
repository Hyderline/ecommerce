package com.simoneg.ecommerce.repositories.specification;

import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.model.Users;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UsersSpecifications {

    public static Specification<Users> getUserFilters(GetUsersRequestDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(hasText(dto.getUsername())) {
                predicates.add(
                        cb.equal(root.get("username"), dto.getUsername())
                );
            }
            if(hasText(dto.getEmail())) {
                predicates.add(
                        cb.equal(root.get("email"), dto.getEmail())
                );
            }

            addRange(predicates, cb, root.<Instant>get("createdAt"), dto.getCreatedFrom(), dto.getCreatedTo());
            addRange(predicates, cb, root.<Instant>get("updatedAt"), dto.getUpdatedFrom(), dto.getUpdatedTo());

            if (hasText(dto.getRoleName())) {
                predicates.add(
                        cb.equal(root.join("role", JoinType.INNER).get("roleName"), dto.getRoleName()));
            }

            if (hasText(dto.getCompanyName())) {
                predicates.add(
                        cb.equal(root.join("company", JoinType.INNER).get("companyName"), dto.getCompanyName()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addRange(List<Predicate> p, CriteriaBuilder cb,
                                 Expression<Instant> campo, Instant from, Instant to) {
        if (from != null && to != null) {
            p.add(cb.between(campo, from, to));
        } else if (from != null) {
            p.add(cb.greaterThanOrEqualTo(campo, from));
        } else if (to != null) {
            p.add(cb.lessThanOrEqualTo(campo, to));
        }
    }

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }
}
