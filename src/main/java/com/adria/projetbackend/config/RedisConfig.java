package com.adria.projetbackend.config;

import com.adria.projetbackend.utils.storage.MessagePublisher;
import com.adria.projetbackend.utils.storage.MessagePublisherImpl;
import com.adria.projetbackend.utils.storage.MessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan({ "com.adria.projetbackend.utils" })
@EnableRedisRepositories(basePackages = "com.adria.projetbackend.utils.storage")
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("topic");
    }

    @Bean
    MessagePublisher redisPublisher() {
        return (MessagePublisher) new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }
}
