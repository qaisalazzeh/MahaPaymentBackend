package com.maha.payment.services.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maha.payment.services.mysql.entities.MerchantEntity;

/**
 * 
 * @author Qais Azzeh
 *
 */
@Repository
public interface MerchantsRepo extends JpaRepository<MerchantEntity, Long> {

	/**
	 * 
	 * @param identifier
	 * @param accessCode
	 * @param mahaCode
	 * @return
	 */
	MerchantEntity findByIdentifierAndAccessCodeAndMahaCode(String identifier, String accessCode, String mahaCode);
}
