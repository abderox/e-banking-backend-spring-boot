package com.adria.projetbackend.services.Jobs;

import com.adria.projetbackend.utils.storage.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class SchedRedisOps implements IRedisOperations {

    @Autowired
    RedisRepository redisRepository;

    @Scheduled(cron = "0 15 11 * * ?", zone = "Africa/Casablanca")
    @Override
    public void deleteAllUnusedOtp() {
        redisRepository.deleteAllUnusedOtp();
    }

    @Scheduled(cron = "0 15 2 * * ?", zone = "Africa/Casablanca")
    @Override
    public void verifyJwtExpiration() {
        redisRepository.checkValidJwts();
    }
}
