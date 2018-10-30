package com.zhao.cache.config;

import com.zhao.cache.bean.Department;
import com.zhao.cache.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class MyRedisConfig {
	@Bean
	public RedisTemplate<Object, Employee> empRedisTemplate(
			RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
		template.setDefaultSerializer(serializer);
		return template;
	}

	@Bean
	public RedisTemplate<Object, Department> dpetRedisTemplate(
			RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Department> serializer = new Jackson2JsonRedisSerializer<Department>(Department.class);
		template.setDefaultSerializer(serializer);
		return template;
	}

	//CacheManagerCustomizers可以定制缓存的一些规则
	@Primary	//将某个缓存管理器作为默认的
	@Bean
	public RedisCacheManager empolyeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate){
		RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
		//key多了一个前缀

		//使用前缀，默认会将CacheName作为key前缀
		cacheManager.setUsePrefix(true);

		return cacheManager;
	}

	@Bean
	public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> deptRedisTemplate){
		RedisCacheManager cacheManager = new RedisCacheManager(deptRedisTemplate);
		cacheManager.setUsePrefix(true);

		return cacheManager;
	}
}
