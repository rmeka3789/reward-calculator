package com.retailer.calc.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.calc.constants.Constants;
import com.retailer.calc.entity.Transaction;
import com.retailer.calc.model.Rewards;
import com.retailer.calc.repository.TransactionRepository;

@Service
public class RewardsService {

	@Autowired
	TransactionRepository transactionRepository;

	public Rewards getRewardsByCustomerId(Long customerId) {

		Timestamp oneMonthBackTime = getDateBasedOnOffSetDays(Constants.DAYS_CUTOFF);
		Timestamp twoMonthBackTime = getDateBasedOnOffSetDays(2 * Constants.DAYS_CUTOFF);
		Timestamp threeMonthBackTime = getDateBasedOnOffSetDays(3 * Constants.DAYS_CUTOFF);

		List<Transaction> lastMonthTxns = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId,
				oneMonthBackTime, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTxns = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, twoMonthBackTime, oneMonthBackTime);
		List<Transaction> lastThirdMonthTxns = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, threeMonthBackTime, twoMonthBackTime);

		// calculate each month rewards
		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTxns);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTxns);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTxns);
		long totalRewards = lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints;

		// Set the response/output object
		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards.setTotalRewards(totalRewards);

		return customerRewards;
	}

	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}

	private Long calculateRewards(Transaction txn) {
		if (txn.getTransactionAmount() > Constants.FIRST_REWARD_LIMIT
				&& txn.getTransactionAmount() <= Constants.SECOND_REWARD_LIMIT) {
			return Math.round(txn.getTransactionAmount() - Constants.FIRST_REWARD_LIMIT);
		} else if (txn.getTransactionAmount() > Constants.SECOND_REWARD_LIMIT) {
			return Math.round(txn.getTransactionAmount() - Constants.SECOND_REWARD_LIMIT) * 2
					+ (Constants.SECOND_REWARD_LIMIT - Constants.FIRST_REWARD_LIMIT);
		} else
			return 0l;

	}

	public Timestamp getDateBasedOnOffSetDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}

}
