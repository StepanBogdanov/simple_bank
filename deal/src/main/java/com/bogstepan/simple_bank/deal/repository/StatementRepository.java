package com.bogstepan.simple_bank.deal.repository;

import com.bogstepan.simple_bank.deal.model.entity.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatementRepository extends CrudRepository<Statement, UUID>{
}
