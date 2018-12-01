package io.aweris.roo.api;

import io.aweris.roo.domain.CustomerRepository;
import io.aweris.roo.domain.RoomOccupancy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.Math.max;

@Service
public class OccupancyService {

    private CustomerRepository repository;

    public OccupancyService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Flowable<RoomOccupancy> query(long premium, long economy, BigDecimal breakingPoint) {
        // getting customers fits to premium room criteria
        var premiumCustomers = repository.findByPaymentGreaterThan(breakingPoint).limit(premium);

        // getting customers fits to economy and possible premium upgrades
        var economyCustomers = repository.findByPaymentLessThan(breakingPoint).limit(premium + economy);

        // Economy customers can upgrade premium
        Single<Long> upgradeEconomy = economyCustomers.count().map(ec -> max(ec - economy, 0));

        // Premium rooms available for upgrade
        Single<Long> upgradePremium = premiumCustomers.count().map(pc -> premium - pc);

        // Count of customers needs to upgrade
        var upgradeCount = Single.zip(upgradeEconomy, upgradePremium, Math::min).toFlowable();

        // Merging premium customers with upgrades from economy
        var premiumOccupancy = premiumCustomers.mergeWith(upgradeCount.flatMap(economyCustomers::limit))
                                               .reduce(RoomOccupancy.premium(), RoomOccupancy::add);

        // Getting final customers economy
        var economyOccupancy = upgradeCount.flatMap(uc -> economyCustomers.skip(uc).limit(economy))
                                           .reduce(RoomOccupancy.economy(), RoomOccupancy::add);

        // Merging results
        return Flowable.merge(premiumOccupancy.toFlowable(), economyOccupancy.toFlowable());
    }
}
