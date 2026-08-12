package com.aryan.studentmanagementapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.aryan.studentmanagementapi.dto.StudentSummaryDTO;
import com.aryan.studentmanagementapi.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentRepositoryImpl implements CustomStudentRepository {
    
    private final EntityManager entityManager;

    public StudentRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Page<StudentSummaryDTO> findAllProjected(Specification<Student> spec, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<StudentSummaryDTO> criteriaQuery = criteriaBuilder.createQuery(StudentSummaryDTO.class);

        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery.select(
            criteriaBuilder.construct(
                StudentSummaryDTO.class,
                root.get("name"),
                root.get("branch").get("name"),
                root.get("year")
            )
        );

        Predicate predicate = spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        criteriaQuery.where(predicate);

        TypedQuery<StudentSummaryDTO> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<StudentSummaryDTO> content = query.getResultList();

        CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);

        Root<Student> rootCount = criteriaQueryCount.from(Student.class);

        criteriaQueryCount.select(
            criteriaBuilder.count(rootCount)
        );

        Predicate counPredicate = spec.toPredicate(rootCount, criteriaQueryCount, criteriaBuilder);

        criteriaQueryCount.where(counPredicate);

        TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);

        Long totalElements = queryCount.getSingleResult();

        PageImpl<StudentSummaryDTO> dtoPageImpl = new PageImpl<>(content, pageable, totalElements);
        
        return dtoPageImpl;
    }
}
