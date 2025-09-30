package com.nassau.gerenciador_de_renda.client.repository;

import com.nassau.gerenciador_de_renda.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {


    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);
}
