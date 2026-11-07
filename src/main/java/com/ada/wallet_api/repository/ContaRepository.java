package com.ada.wallet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ada.wallet_api.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
