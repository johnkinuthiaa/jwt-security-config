package com.jwttokens.jwttokensconfig.repository;

import com.jwttokens.jwttokensconfig.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products,Long> {

}
