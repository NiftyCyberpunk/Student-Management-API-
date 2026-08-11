package com.aryan.studentmanagementapi.specification;

import org.springframework.data.jpa.domain.Specification;

import com.aryan.studentmanagementapi.model.Student;

public class StudentSpecification {
    
    //starting point
    public static Specification<Student> alwaysTrue() {

        return (root, query, criteriaBuilder) ->
        criteriaBuilder.conjunction();
    }

    public static Specification<Student> hasBranch(String name){

        return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("branch").get("name"), name);
    }

    public static Specification<Student> hasYear(int year){

        return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<Student> hasNameLike(String name){

        return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("name"), "%" + name + "%");
    } 
}
